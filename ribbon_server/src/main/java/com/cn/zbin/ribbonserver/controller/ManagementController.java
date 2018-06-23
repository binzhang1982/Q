package com.cn.zbin.ribbonserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cn.zbin.ribbonserver.service.ManagementService;

@RestController
@RequestMapping("mgmt")
@CrossOrigin
public class ManagementController {
    @Autowired
    ManagementService managementService;

	@PostMapping(value = "/update_customer")
	@CrossOrigin
    public String updateCustomer(@RequestParam("openid") String openid,
    		@RequestParam("regtype") Integer registerType) {
		String ret = managementService.updateCustomer(openid, registerType);
		return ret;
	}
}
