package com.cn.zbin.management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cn.zbin.management.service.CustomerService;

@RestController
@RequestMapping("customer")
public class CustomerController {
	@Autowired
	private CustomerService customerService;
	
	@RequestMapping(value = "/update", method = { RequestMethod.POST })
	public String addWechatUser(@RequestParam("openid") String openid,
			@RequestParam("regtype") Integer registerType) {
		String ret = "";
		customerService.postCustomer(openid, registerType);
		return ret;
	}
}
