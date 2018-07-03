package com.cn.zbin.ribbonserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

	@PostMapping(value = "/cust/regtype")
	@CrossOrigin
    public String updateCustomer(@RequestParam("openid") String openid,
    		@RequestParam("regtype") Integer registerType) {
		return managementService.updateCustomer(openid, registerType);
	}

	@GetMapping(value = "/cust/{customerid}")
	@CrossOrigin
	public String getRefIdByCustId(@PathVariable("customerid") String customerid) {
		return managementService.getRefIdByCustId(customerid);
	}

	@GetMapping(value = "/cust/{regtype}/{refid}")
	@CrossOrigin
	public String getCustomerByRefID(@PathVariable("regtype") Integer registerType,
			@PathVariable("refid") String refid) {
		return managementService.getCustomerByRefID(registerType, refid);
	}
}
