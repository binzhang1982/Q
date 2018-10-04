package com.cn.zbin.management.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cn.zbin.management.bto.CountMsgData;
import com.cn.zbin.management.bto.CustomerAddressMsgData;
import com.cn.zbin.management.bto.CustomerAddressOverView;
import com.cn.zbin.management.bto.CustomerInfoMsgData;
import com.cn.zbin.management.bto.CustomerInvoiceMsgData;
import com.cn.zbin.management.bto.CustomerListMsgData;
import com.cn.zbin.management.bto.MessageHistoryMsgData;
import com.cn.zbin.management.bto.MsgData;
import com.cn.zbin.management.bto.OauthAccessToken;
import com.cn.zbin.management.dto.CustomerAddress;
import com.cn.zbin.management.dto.CustomerDiseaseHistory;
import com.cn.zbin.management.dto.CustomerInfo;
import com.cn.zbin.management.dto.CustomerInvoice;
import com.cn.zbin.management.dto.MessageHistory;
import com.cn.zbin.management.exception.BusinessException;
import com.cn.zbin.management.service.CustomerService;
import com.cn.zbin.management.service.SmsService;
import com.cn.zbin.management.utils.MgmtConstants;

@RestController
@RequestMapping("customer")
public class CustomerController {
	protected static final Logger logger = LoggerFactory.getLogger(CustomerController.class);
	@Autowired
	private CustomerService customerService;
	@Autowired
	private SmsService smsService;

	@RequestMapping(value = "/list/sys", 
			produces = {"application/json;charset=UTF-8"}, 
			method = { RequestMethod.GET })
	public CustomerListMsgData getCustomerList(
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "telno", required = false) String telno,
			@RequestParam(value = "offset", required = false) Integer offset, 
			@RequestParam(value = "limit", required = false) Integer limit) {
		logger.info("get api: /customer/list/sys");
		CustomerListMsgData ret = new CustomerListMsgData();
		try {
			ret.setTotalCount(customerService.countCustomerList(name, telno));
			ret.setCustomerList(customerService.getCustomerList(name, telno, offset, limit));
		} catch (BusinessException be) {
			ret.setStatus(MsgData.status_ng);
			ret.setMessage(be.getMessage());
		} catch (Exception e) {
			ret.setStatus(MsgData.status_ng);
			ret.setMessage(MgmtConstants.CHK_ERR_99999);
		}
		return ret;
	}

	@RequestMapping(value = "/count", 
			produces = {"application/json;charset=UTF-8"}, 
			method = { RequestMethod.GET })
	public CountMsgData countCustomer() {
		logger.info("get api: /customer/count");
		CountMsgData ret = new CountMsgData();
		try {
			ret.setCount(customerService.countCustomer());
		} catch (BusinessException be) {
			ret.setStatus(MsgData.status_ng);
			ret.setMessage(be.getMessage());
		} catch (Exception e) {
			ret.setStatus(MsgData.status_ng);
			ret.setMessage(MgmtConstants.CHK_ERR_99999);
		}
		return ret;
	}
	
	@RequestMapping(value = "",
			method = { RequestMethod.POST })
	public MsgData addWechatUser(@RequestParam("openid") String openid,
			@RequestParam("regtype") Integer registerType) {
		logger.info("post api: /customer || openid: " + openid 
					+ " || regtype: " + registerType);
		MsgData ret = new MsgData();
		try {
			customerService.postCustomer(openid, registerType);
		} catch (BusinessException be) {
			ret.setStatus(MsgData.status_ng);
			ret.setMessage(be.getMessage());
		} catch (Exception e) {
			ret.setStatus(MsgData.status_ng);
			ret.setMessage(MgmtConstants.CHK_ERR_99999);
		}
		return ret;
	}
	
	@RequestMapping(value = "/{regtype}/{refid}", 
			produces = {"application/json;charset=UTF-8"}, 
			method = { RequestMethod.GET })
	public CustomerInfo getCustomerByRefID(@PathVariable("regtype") Integer registerType,
			@PathVariable("refid") String refid) {
		logger.info("get api: /customer/{regtype}/{refid} || regtype: " + registerType 
				+ " || refid: " + refid);
		return customerService.getCustomerByRefID(refid, registerType);
	}
	
	@RequestMapping(value = "/{customerid}", 
			produces = {"application/json;charset=UTF-8"}, 
			method = { RequestMethod.GET })
	public CustomerInfo getRefIdByCustId(@PathVariable("customerid") String customerid) {
		logger.info("get api: /customer/{customerid} || customerid: " + customerid);
		return customerService.getRefIdByCustId(customerid);
	}
	
	@RequestMapping(value = "/address/list/{customerid}", 
			produces = {"application/json;charset=UTF-8"}, 
			method = { RequestMethod.GET })
	public List<CustomerAddressOverView> getCustomerAddressList(
			@PathVariable("customerid") String customerid,
			@RequestParam(value = "defaultflag", required = false) Boolean defaultflag) {
		logger.info("get api: /customer/address/list/{customerid}"
					+ " || customerid: " + customerid + " || defaultflag: " + defaultflag);
		return customerService.getCustomerAddressList(customerid, defaultflag);
	}
	
	@RequestMapping(value = "/address", 
			consumes = {"application/json;charset=UTF-8"}, 
			produces = {"application/json;charset=UTF-8"}, 
			method = { RequestMethod.POST})
	public CustomerAddressMsgData updateCustomerAddress(
			@RequestBody CustomerAddress address) {
		logger.info("post api: /customer/address"
				+ " || address: " + address.toString());
		return customerService.updateCustomerAddress(address);
	}

	@RequestMapping(value = "/invoice/list/{customerid}", 
			produces = {"application/json;charset=UTF-8"}, 
			method = { RequestMethod.GET })
	public List<CustomerInvoice> getCustomerInvoiceList(
			@PathVariable("customerid") String customerid,
			@RequestParam(value = "defaultflag", required = false) Boolean defaultflag) {
		logger.info("get api: /customer/invoice/list/{customerid}"
				+ " || customerid: " + customerid + " || defaultflag: " + defaultflag);
		return customerService.getCustomerInvoiceList(customerid, defaultflag);
	}
	
	@RequestMapping(value = "/invoice", 
			consumes = {"application/json;charset=UTF-8"}, 
			produces = {"application/json;charset=UTF-8"}, 
			method = { RequestMethod.POST})
	public CustomerInvoiceMsgData updateCustomerInvoice(
			@RequestBody CustomerInvoice invoice) {
		logger.info("post api: /customer/invoice"
				+ " || invoice: " + invoice.toString());
		return customerService.updateCustomerInvoice(invoice);
	}

	@RequestMapping(value = "/info", 
			consumes = {"application/json;charset=UTF-8"}, 
			produces = {"application/json;charset=UTF-8"}, 
			method = { RequestMethod.POST})
	public CustomerInfoMsgData updateCustomerInfo(
			@RequestBody CustomerInfo customer) {
		logger.info("post api: /customer/info"
				+ " || customer: " + customer.toString());
		return customerService.updateCustomerInfo(customer);
	}
	
	@RequestMapping(value = "/phone/{customerid}/{phonenumber}", 
			produces = {"application/json;charset=UTF-8"}, 
			method = { RequestMethod.GET })
    public MsgData addPhoneNum(@PathVariable("customerid") String customerid, 
    		@PathVariable("phonenumber") String phonenumber) {
		logger.info("get api: /customer/phone/{customerid}/{phonenumber}"
				+ " || customerid: " + customerid + " || phonenumber: " + phonenumber);
		MsgData msg = new MsgData();
		MessageHistoryMsgData smsMsgData = smsService.addMessageHistory(customerid, phonenumber);
		msg.setMessage(smsMsgData.getMessage());
		msg.setStatus(smsMsgData.getStatus());
		smsService.sendSms(smsMsgData.getSms());
    	return msg;
    }

	@RequestMapping(value = "/valid/{customerid}/{validcode}", 
			produces = {"application/json;charset=UTF-8"}, 
			method = { RequestMethod.GET })
	public MsgData comfirmValidCode(
			@PathVariable("customerid") String customerid, 
			@PathVariable("validcode") String validcode) {
		logger.info("get api: /customer/valid/{customerid}/{validcode}"
				+ " || customerid: " + customerid + " || validcode: " + validcode);
		MsgData ret = new MsgData();
		try {
			customerService.comfirmValidCode(customerid, validcode);
		} catch (BusinessException be) {
			ret.setStatus(MsgData.status_ng);
			ret.setMessage(be.getMessage());
		} catch (Exception e) {
			ret.setStatus(MsgData.status_ng);
			ret.setMessage(MgmtConstants.CHK_ERR_99999);
		}
		return ret;
	}
	
	@RequestMapping(value = "/oauthatk", 
			consumes = {"application/json;charset=UTF-8"}, 
			produces = {"application/json;charset=UTF-8"}, 
			method = { RequestMethod.POST})
	public MsgData updateToken(@RequestBody OauthAccessToken oatk) {
		logger.info("post api: /customer/oauthatk"
				+ " || oatk: " + oatk.toString());
		MsgData ret = new MsgData();
		try {
			customerService.updateToken(oatk);
		} catch (BusinessException be) {
			ret.setStatus(MsgData.status_ng);
			ret.setMessage(be.getMessage());
		} catch (Exception e) {
			ret.setStatus(MsgData.status_ng);
			ret.setMessage(MgmtConstants.CHK_ERR_99999);
		}
		return ret;
	}
	
	@RequestMapping(value = "/disease/list/{customerid}", 
			produces = {"application/json;charset=UTF-8"}, 
			method = { RequestMethod.GET })
	public List<CustomerDiseaseHistory> getDiseaseHistory(
			@PathVariable("customerid") String customerid) {
		logger.info("get api: /customer/disease/list/{customerid}"
				+ " || customerid: " + customerid);
		return customerService.getDiseaseHistory(customerid);
	}
	
	@RequestMapping(value = "/disease/list/{customerid}", 
			consumes = {"application/json;charset=UTF-8"}, 
			produces = {"application/json;charset=UTF-8"}, 
			method = { RequestMethod.POST })
	public MsgData updDiseaseHistory(@PathVariable("customerid") String customerid,
			@RequestBody List<CustomerDiseaseHistory> diseaseList) {
		logger.info("post api: /customer/disease/list/{customerid}"
				+ " || customerid: " + customerid + " || diseaseList: " + diseaseList.toString());
		MsgData ret = new MsgData();
		try {
			customerService.updDiseaseHistory(customerid, diseaseList);
		} catch (BusinessException be) {
			ret.setStatus(MsgData.status_ng);
			ret.setMessage(be.getMessage());
		} catch (Exception e) {
			ret.setStatus(MsgData.status_ng);
			ret.setMessage(MgmtConstants.CHK_ERR_99999);
		}
		return ret;
	}
	
	@RequestMapping(value = "/due/notify/sys", 
			method = { RequestMethod.GET })
	public MsgData notifyDueMsg() {
		logger.info("get api: /customer/due/notify/sys");
		MsgData ret = new MsgData();
		try {
			List<MessageHistory> smsLst = smsService.getDueMessageList();
			for (MessageHistory sms : smsLst) {
				try {
					smsService.sendSms(sms);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}
}
