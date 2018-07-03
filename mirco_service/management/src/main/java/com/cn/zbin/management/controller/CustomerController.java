package com.cn.zbin.management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cn.zbin.management.dto.CustomerInfo;
import com.cn.zbin.management.service.CustomerService;

@RestController
@RequestMapping("customer")
public class CustomerController {
	@Autowired
	private CustomerService customerService;
	
	@RequestMapping(value = "", method = { RequestMethod.POST })
	public String addWechatUser(@RequestParam("openid") String openid,
			@RequestParam("regtype") Integer registerType) {
		String ret = "";
		customerService.postCustomer(openid, registerType);
		return ret;
	}
	
	@RequestMapping(value = "/{regtype}/{refid}", produces = {"application/json;charset=UTF-8"}, method = { RequestMethod.GET })
	public CustomerInfo getCustomerByRefID(@PathVariable("regtype") Integer registerType,
			@PathVariable("refid") String refid) {
		return customerService.getCustomerByRefID(refid, registerType);
	}
	
	@RequestMapping(value = "/{customerid}", produces = {"application/json;charset=UTF-8"}, method = { RequestMethod.GET })
	public CustomerInfo getRefIdByCustId(@PathVariable("customerid") String customerid) {
		return customerService.getRefIdByCustId(customerid);
	}
}
