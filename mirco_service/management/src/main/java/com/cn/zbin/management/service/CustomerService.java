package com.cn.zbin.management.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cn.zbin.management.dto.CustomerInfo;
import com.cn.zbin.management.dto.CustomerInfoExample;
import com.cn.zbin.management.mapper.CustomerInfoMapper;
import com.cn.zbin.management.utils.Utils;

@Service
public class CustomerService {
	@Autowired
	private CustomerInfoMapper customerInfoMapper;
	
	public CustomerInfo getRefIdByCustId(String customerid) {
		return customerInfoMapper.selectByPrimaryKey(customerid);
	}
	
	public CustomerInfo getCustomerByRefID(String refid, Integer registerType) {
		CustomerInfo ret = new CustomerInfo();
		CustomerInfoExample example = new CustomerInfoExample();
		example.createCriteria().andRegisterIdEqualTo(refid)
								.andRegisterTypeEqualTo(registerType);
		List<CustomerInfo> custList = customerInfoMapper.selectByExample(example);
		if (Utils.listNotNull(custList)) {
			ret = custList.get(0);
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
		return ret;
		
	}
}
