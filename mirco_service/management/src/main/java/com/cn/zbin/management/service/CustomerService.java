package com.cn.zbin.management.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cn.zbin.management.bto.CustomerAddressMsgData;
import com.cn.zbin.management.bto.CustomerAddressOverView;
import com.cn.zbin.management.bto.CustomerInfoMsgData;
import com.cn.zbin.management.bto.CustomerInvoiceMsgData;
import com.cn.zbin.management.bto.MsgData;
import com.cn.zbin.management.dto.CustomerAddress;
import com.cn.zbin.management.dto.CustomerAddressExample;
import com.cn.zbin.management.dto.CustomerInfo;
import com.cn.zbin.management.dto.CustomerInfoExample;
import com.cn.zbin.management.dto.CustomerInvoice;
import com.cn.zbin.management.dto.CustomerInvoiceExample;
import com.cn.zbin.management.dto.MasterCity;
import com.cn.zbin.management.dto.MasterProvince;
import com.cn.zbin.management.mapper.CustomerAddressMapper;
import com.cn.zbin.management.mapper.CustomerInfoMapper;
import com.cn.zbin.management.mapper.CustomerInvoiceMapper;
import com.cn.zbin.management.mapper.MasterCityMapper;
import com.cn.zbin.management.mapper.MasterProvinceMapper;
import com.cn.zbin.management.utils.MgmtConstants;
import com.cn.zbin.management.utils.Utils;

@Service
public class CustomerService {
	@Autowired
	private MasterProvinceMapper masterProvinceMapper;
	@Autowired
	private MasterCityMapper masterCityMapper;
	@Autowired
	private CustomerInfoMapper customerInfoMapper;
	@Autowired
	private CustomerAddressMapper customerAddressMapper;
	@Autowired
	private CustomerInvoiceMapper customerInvoiceMapper;
	
	@Transactional
	public MsgData comfirmValidCode(String customerid, String validcode) {
		MsgData msg = new MsgData();
		CustomerInfo cust = customerInfoMapper.selectByPrimaryKey(customerid);
		if (cust != null) {
			if (cust.getValidCode() != null) {
				if (validcode.equals(cust.getValidCode())) {
					CustomerInfo record = new CustomerInfo();
					record.setCustomerId(customerid);
					record.setValidFlag(Boolean.TRUE);
					customerInfoMapper.updateByPrimaryKeySelective(record);
				} else {
					msg.setStatus(MsgData.status_ng);
					msg.setMessage(MgmtConstants.CHK_ERR_80008);
				}
			} else {
				msg.setStatus(MsgData.status_ng);
				msg.setMessage(MgmtConstants.CHK_ERR_80008);
			}
		} else {
			msg.setStatus(MsgData.status_ng);
			msg.setMessage(MgmtConstants.CHK_ERR_80005);
		}
		
		return msg;
	}
	
	public CustomerInfoMsgData updateCustomerInfo(CustomerInfo customer) {
		CustomerInfoMsgData ret = new CustomerInfoMsgData();
		
		if (StringUtils.isBlank(customer.getCustomerId())) {
			ret.setStatus(MsgData.status_ng);
			ret.setMessage(MgmtConstants.CHK_ERR_80005);
			return ret;
		}
		
		customer.setExpriedTime(null);
		customer.setCreateTime(null);
		customer.setRegisterId(null);
		customer.setRegisterType(null);
		customer.setToken(null);
		customer.setUpdateTime(null);
		customer.setValidCode(null);
		customer.setValidFlag(null);
		
		if (customerInfoMapper.updateByPrimaryKeySelective(customer) == 0) {
			ret.setStatus(MsgData.status_ng);
			ret.setMessage(MgmtConstants.CHK_ERR_80005);
			return ret;
		}
		CustomerInfo cust = customerInfoMapper.selectByPrimaryKey(customer.getCustomerId());
		if (cust != null) cust.setValidCode(null);
		ret.setCustomer(cust);
		return ret;
	}
	
	public CustomerInvoiceMsgData updateCustomerInvoice(CustomerInvoice invoice) {
		CustomerInvoiceMsgData ret = new CustomerInvoiceMsgData();
		if (invoice.getDeleteFlag()) {
			//删除
			CustomerInvoice record = new CustomerInvoice();
			record.setCustInvoiceId(invoice.getCustInvoiceId());
			record.setSeqNo(-1);
			record.setDefaultFlag(Boolean.FALSE);
			record.setDeleteFlag(invoice.getDeleteFlag());
			customerInvoiceMapper.updateByPrimaryKeySelective(record);
		} else {
			if (invoice.getCustInvoiceId() != null) {
				//更新
				CustomerInvoice record = new CustomerInvoice();
				record.setCustInvoiceId(invoice.getCustInvoiceId());
				record.setSeqNo(-1);
				record.setDefaultFlag(Boolean.FALSE);
				record.setDeleteFlag(Boolean.TRUE);
				customerInvoiceMapper.updateByPrimaryKeySelective(record);
				
				if (invoice.getDefaultFlag()) {
					CustomerInvoiceExample exam_ci = new CustomerInvoiceExample();
					exam_ci.createCriteria().andDeleteFlagEqualTo(Boolean.FALSE)
											.andCustomerIdEqualTo(invoice.getCustomerId())
											.andDefaultFlagEqualTo(Boolean.TRUE);
					List<CustomerInvoice> defaultInvoiceList = 
							customerInvoiceMapper.selectByExample(exam_ci);
					if (Utils.listNotNull(defaultInvoiceList)) {
						for (CustomerInvoice defaultInvoice : defaultInvoiceList) {
							CustomerInvoice invoiceRec = new CustomerInvoice();
							invoiceRec.setCustInvoiceId(defaultInvoice.getCustInvoiceId());
							invoiceRec.setDefaultFlag(Boolean.FALSE);
							customerInvoiceMapper.updateByPrimaryKeySelective(invoiceRec);
						}
					}
				}
				
				invoice.setCustInvoiceId(UUID.randomUUID().toString());
				customerInvoiceMapper.insert(invoice);
				ret.setInvoice(invoice);
			} else {
				//新增
				CustomerInvoiceExample exam_ci = new CustomerInvoiceExample();
				exam_ci.createCriteria().andDeleteFlagEqualTo(Boolean.FALSE)
										.andCustomerIdEqualTo(invoice.getCustomerId());
				exam_ci.setOrderByClause("seq_no desc");
				List<CustomerInvoice> invoiceList = customerInvoiceMapper.selectByExample(exam_ci);
				
				Integer seqNo = 1;
				if (Utils.listNotNull(invoiceList)) {
					if (invoiceList.size() >= MgmtConstants.INVOICE_MAX) {
						ret.setMessage(MgmtConstants.CHK_ERR_80003);
						ret.setStatus(MsgData.status_ng);
						return ret;
					}
					seqNo = invoiceList.get(0).getSeqNo() + 1;
				}

				if (invoice.getDefaultFlag()) {
					exam_ci.getOredCriteria().get(0)
											.andDefaultFlagEqualTo(Boolean.TRUE);
					List<CustomerInvoice> defaultInvoiceList = 
							customerInvoiceMapper.selectByExample(exam_ci);
					if (Utils.listNotNull(defaultInvoiceList)) {
						for (CustomerInvoice defaultInvoice : defaultInvoiceList) {
							CustomerInvoice invoiceRec = new CustomerInvoice();
							invoiceRec.setCustInvoiceId(defaultInvoice.getCustInvoiceId());
							invoiceRec.setDefaultFlag(Boolean.FALSE);
							customerInvoiceMapper.updateByPrimaryKeySelective(invoiceRec);
						}
					}
				}
				invoice.setCustInvoiceId(UUID.randomUUID().toString());
				invoice.setSeqNo(seqNo);
				customerInvoiceMapper.insert(invoice);
				ret.setInvoice(invoice);
			}
		}
		return ret;
	}
	
	public CustomerAddressMsgData updateCustomerAddress(CustomerAddress address) {
		CustomerAddressMsgData ret = new CustomerAddressMsgData();
		if (address.getDeleteFlag()) {
			//删除
			CustomerAddress record = new CustomerAddress();
			record.setCustAddressId(address.getCustAddressId());
			record.setSeqNo(-1);
			record.setDefaultFlag(Boolean.FALSE);
			record.setDeleteFlag(address.getDeleteFlag());
			customerAddressMapper.updateByPrimaryKeySelective(record);
		} else {
			if (address.getCustAddressId() != null) {
				//更新
				CustomerAddress record = new CustomerAddress();
				record.setCustAddressId(address.getCustAddressId());
				record.setSeqNo(-1);
				record.setDefaultFlag(Boolean.FALSE);
				record.setDeleteFlag(Boolean.TRUE);
				customerAddressMapper.updateByPrimaryKeySelective(record);
				
				if (address.getDefaultFlag()) {
					CustomerAddressExample exam_ca = new CustomerAddressExample();
					exam_ca.createCriteria().andDeleteFlagEqualTo(Boolean.FALSE)
											.andCustomerIdEqualTo(address.getCustomerId())
											.andDefaultFlagEqualTo(Boolean.TRUE);
					List<CustomerAddress> defaultAddressList = 
							customerAddressMapper.selectByExample(exam_ca);
					if (Utils.listNotNull(defaultAddressList)) {
						for (CustomerAddress defaultAddress : defaultAddressList) {
							CustomerAddress addressRec = new CustomerAddress();
							addressRec.setCustAddressId(defaultAddress.getCustAddressId());
							addressRec.setDefaultFlag(Boolean.FALSE);
							customerAddressMapper.updateByPrimaryKeySelective(addressRec);
						}
					}
					
					if (customerAddressMapper.countByExample(exam_ca) > 0) {
						ret.setMessage(MgmtConstants.CHK_ERR_80002);
						ret.setStatus(MsgData.status_ng);
						return ret;
					}
				}
				
				address.setCustAddressId(UUID.randomUUID().toString());
				customerAddressMapper.insert(address);
				CustomerAddressOverView addrOV = new CustomerAddressOverView();
				MasterCity city = masterCityMapper.selectByPrimaryKey(
						address.getCityCode());
				MasterProvince province = masterProvinceMapper.selectByPrimaryKey(
						address.getProvinceCode());
				addrOV.setAddr(address);
				if (city != null) addrOV.setCityName(city.getCityName());
				if (province != null) addrOV.setProvinceName(province.getProvinceName());
				ret.setAddr(addrOV);
			} else {
				CustomerAddressExample exam_ca = new CustomerAddressExample();
				exam_ca.createCriteria().andDeleteFlagEqualTo(Boolean.FALSE)
										.andCustomerIdEqualTo(address.getCustomerId());
				exam_ca.setOrderByClause("seq_no desc");
				List<CustomerAddress> addrList = customerAddressMapper.selectByExample(exam_ca);
				
				Integer seqNo = 1;
				if (Utils.listNotNull(addrList)) {
					if (addrList.size() >= MgmtConstants.ADDR_MAX) {
						ret.setMessage(MgmtConstants.CHK_ERR_80001);
						ret.setStatus(MsgData.status_ng);
						return ret;
					}
					seqNo = addrList.get(0).getSeqNo() + 1;
				}

				if (address.getDefaultFlag()) {
					exam_ca.getOredCriteria().get(0)
											.andDefaultFlagEqualTo(Boolean.TRUE);
					List<CustomerAddress> defaultAddressList = 
							customerAddressMapper.selectByExample(exam_ca);
					if (Utils.listNotNull(defaultAddressList)) {
						for (CustomerAddress defaultAddress : defaultAddressList) {
							CustomerAddress addressRec = new CustomerAddress();
							addressRec.setCustAddressId(defaultAddress.getCustAddressId());
							addressRec.setDefaultFlag(Boolean.FALSE);
							customerAddressMapper.updateByPrimaryKeySelective(addressRec);
						}
					}
				}
				//新增
				address.setCustAddressId(UUID.randomUUID().toString());
				address.setSeqNo(seqNo);
				customerAddressMapper.insert(address);
				CustomerAddressOverView addrOV = new CustomerAddressOverView();
				MasterCity city = masterCityMapper.selectByPrimaryKey(
						address.getCityCode());
				MasterProvince province = masterProvinceMapper.selectByPrimaryKey(
						address.getProvinceCode());
				addrOV.setAddr(address);
				if (city != null) addrOV.setCityName(city.getCityName());
				if (province != null) addrOV.setProvinceName(province.getProvinceName());
				ret.setAddr(addrOV);
			}
		}
		return ret;
	}
	
	public List<CustomerInvoice> getCustomerInvoiceList(String customerid,
			Boolean defaultflag) {
		CustomerInvoiceExample exam_ci = new CustomerInvoiceExample();
		exam_ci.createCriteria().andCustomerIdEqualTo(customerid)
								.andDeleteFlagEqualTo(Boolean.FALSE);
		exam_ci.setOrderByClause("seq_no asc");
		if (defaultflag != null) 
			exam_ci.getOredCriteria().get(0).andDefaultFlagEqualTo(defaultflag);
		List<CustomerInvoice> ret = customerInvoiceMapper.selectByExample(exam_ci);
		if (!Utils.listNotNull(ret)) ret = new ArrayList<CustomerInvoice>();
		return ret;
	}

	public List<CustomerAddressOverView> getCustomerAddressList(String customerid,
			Boolean defaultflag) {
		List<CustomerAddressOverView> ret = new ArrayList<CustomerAddressOverView>();
		
		CustomerAddressExample exam_ca = new CustomerAddressExample();
		exam_ca.createCriteria().andCustomerIdEqualTo(customerid)
								.andDeleteFlagEqualTo(Boolean.FALSE);
		exam_ca.setOrderByClause("seq_no asc");
		if (defaultflag != null) 
			exam_ca.getOredCriteria().get(0).andDefaultFlagEqualTo(defaultflag);
		List<CustomerAddress> addrList = customerAddressMapper.selectByExample(exam_ca);
		if (Utils.listNotNull(addrList)) {
			for (CustomerAddress addr : addrList) {
				CustomerAddressOverView addrOV = new CustomerAddressOverView();
				MasterCity city = masterCityMapper.selectByPrimaryKey(
						addr.getCityCode());
				MasterProvince province = masterProvinceMapper.selectByPrimaryKey(
						addr.getProvinceCode());
				addrOV.setAddr(addr);
				if (city != null) addrOV.setCityName(city.getCityName());
				if (province != null) addrOV.setProvinceName(province.getProvinceName());
				ret.add(addrOV);
			}
		}
		return ret;
	}
	
	public CustomerInfo getRefIdByCustId(String customerid) {
		CustomerInfo ret = customerInfoMapper.selectByPrimaryKey(customerid);
		if (ret != null) ret.setValidCode(null);
		return ret;
	}
	
	public CustomerInfo getCustomerByRefID(String refid, Integer registerType) {
		CustomerInfo ret = new CustomerInfo();
		CustomerInfoExample example = new CustomerInfoExample();
		example.createCriteria().andRegisterIdEqualTo(refid)
								.andRegisterTypeEqualTo(registerType);
		List<CustomerInfo> custList = customerInfoMapper.selectByExample(example);
		if (Utils.listNotNull(custList)) {
			ret = custList.get(0);
			ret.setValidCode(null);
		}
		return ret;
	}

	@Transactional
	public void postCustomer(String openid, Integer registerType) {
		CustomerInfo cust = convert2CustomerDto(openid, registerType);
		CustomerInfoExample example = new CustomerInfoExample();
		example.createCriteria().andRegisterIdEqualTo(openid)
								.andRegisterTypeEqualTo(registerType);
		if (customerInfoMapper.countByExample(example) == 0) {
			cust.setCustomerId(UUID.randomUUID().toString());
			customerInfoMapper.insertSelective(cust);
		}
	}
	
	private CustomerInfo convert2CustomerDto(String openid, Integer registerType) {
		CustomerInfo ret = new CustomerInfo();
		ret.setRegisterId(openid);
		ret.setRegisterType(registerType);
		ret.setValidFlag(Boolean.FALSE);
		return ret;
	}
}
