package com.cn.zbin.management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cn.zbin.management.bto.CustomerAddressMsgData;
import com.cn.zbin.management.bto.CustomerAddressOverView;
import com.cn.zbin.management.bto.CustomerInfoMsgData;
import com.cn.zbin.management.bto.CustomerInvoiceMsgData;
import com.cn.zbin.management.bto.MessageHistoryMsgData;
import com.cn.zbin.management.bto.MsgData;
import com.cn.zbin.management.dto.CustomerAddress;
import com.cn.zbin.management.dto.CustomerInfo;
import com.cn.zbin.management.dto.CustomerInvoice;
import com.cn.zbin.management.service.CustomerService;
import com.cn.zbin.management.service.SmsService;

@RestController
@RequestMapping("customer")
public class CustomerController {
	@Autowired
	private CustomerService customerService;
	@Autowired
	private SmsService smsService;
	
	@RequestMapping(value = "", method = { RequestMethod.POST })
	public String addWechatUser(@RequestParam("openid") String openid,
			@RequestParam("regtype") Integer registerType) {
		String ret = "";
		customerService.postCustomer(openid, registerType);
		return ret;
	}
	
	@RequestMapping(value = "/{regtype}/{refid}", 
			produces = {"application/json;charset=UTF-8"}, 
			method = { RequestMethod.GET })
	public CustomerInfo getCustomerByRefID(@PathVariable("regtype") Integer registerType,
			@PathVariable("refid") String refid) {
		return customerService.getCustomerByRefID(refid, registerType);
	}
	
	@RequestMapping(value = "/{customerid}", 
			produces = {"application/json;charset=UTF-8"}, 
			method = { RequestMethod.GET })
	public CustomerInfo getRefIdByCustId(@PathVariable("customerid") String customerid) {
		return customerService.getRefIdByCustId(customerid);
	}
	
	@RequestMapping(value = "/address/list/{customerid}", 
			produces = {"application/json;charset=UTF-8"}, 
			method = { RequestMethod.GET })
	public List<CustomerAddressOverView> getCustomerAddressList(
			@PathVariable("customerid") String customerid,
			@RequestParam(value = "defaultflag", required = false) Boolean defaultflag) {
		return customerService.getCustomerAddressList(customerid, defaultflag);
	}
	
	@RequestMapping(value = "/address", 
			consumes = {"application/json;charset=UTF-8"}, 
			produces = {"application/json;charset=UTF-8"}, 
			method = { RequestMethod.POST})
	public CustomerAddressMsgData updateCustomerAddress(
			@RequestBody CustomerAddress address) {
		return customerService.updateCustomerAddress(address);
	}

	@RequestMapping(value = "/invoice/list/{customerid}", 
			produces = {"application/json;charset=UTF-8"}, 
			method = { RequestMethod.GET })
	public List<CustomerInvoice> getCustomerInvoiceList(
			@PathVariable("customerid") String customerid,
			@RequestParam(value = "defaultflag", required = false) Boolean defaultflag) {
		return customerService.getCustomerInvoiceList(customerid, defaultflag);
	}
	
	@RequestMapping(value = "/invoice", 
			consumes = {"application/json;charset=UTF-8"}, 
			produces = {"application/json;charset=UTF-8"}, 
			method = { RequestMethod.POST})
	public CustomerInvoiceMsgData updateCustomerInvoice(
			@RequestBody CustomerInvoice invoice) {
		return customerService.updateCustomerInvoice(invoice);
	}

	@RequestMapping(value = "/info", 
			consumes = {"application/json;charset=UTF-8"}, 
			produces = {"application/json;charset=UTF-8"}, 
			method = { RequestMethod.POST})
	public CustomerInfoMsgData updateCustomerInfo(
			@RequestBody CustomerInfo customer) {
		return customerService.updateCustomerInfo(customer);
	}
	
	@RequestMapping(value = "/phone/{customerid}/{phonenumber}", 
			produces = {"application/json;charset=UTF-8"}, 
			method = { RequestMethod.GET })
    public MsgData addPhoneNum(@PathVariable("customerid") String customerid, 
    		@PathVariable("phonenumber") String phonenumber) {
		MsgData msg = new MsgData();
		MessageHistoryMsgData smsMsgData = smsService.addMessageHistory(customerid, phonenumber);
		msg.setMessage(smsMsgData.getMessage());
		msg.setStatus(smsMsgData.getStatus());
		smsService.sendSms(customerid, smsMsgData.getSms());
    	return msg;
    }

	@RequestMapping(value = "/valid/{customerid}/{validcode}", 
			produces = {"application/json;charset=UTF-8"}, 
			method = { RequestMethod.GET })
	public MsgData comfirmValidCode(
			@PathVariable("customerid") String customerid, 
			@PathVariable("validcode") String validcode) {
		return customerService.comfirmValidCode(customerid, validcode);
	}
}
