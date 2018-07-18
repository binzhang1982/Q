package com.cn.zbin.ribbonserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

	@GetMapping(value = "/cust/address/list/{customerid}")
	@CrossOrigin
	public String getCustomerAddressList(
			@PathVariable("customerid") String customerid,
			@RequestParam(value = "defaultflag", required = false) Boolean defaultflag) {
		return managementService.getCustomerAddressList(customerid, defaultflag);
	}

    @PostMapping(value = "/cust/address")
    @CrossOrigin
    public String updateCustomerAddress(@RequestBody String bean) {
    	return managementService.updateCustomerAddress(bean);
    }
	
	@GetMapping(value = "/cust/invoice/list/{customerid}")
	@CrossOrigin
	public String getCustomerInvoiceList(
			@PathVariable("customerid") String customerid,
			@RequestParam(value = "defaultflag", required = false) Boolean defaultflag) {
		return managementService.getCustomerInvoiceList(customerid, defaultflag);
	}

    @PostMapping(value = "/cust/invoice")
    @CrossOrigin
    public String updateCustomerInvoice(@RequestBody String bean) {
    	return managementService.updateCustomerInvoice(bean);
    }

	@GetMapping(value = "/mst/province/list")
	@CrossOrigin
	public String getProvinceList(
			@RequestParam(value = "search", required = false) String strSearch, 
			@RequestParam(value = "pcode", required = false) String strProvinceCode,
			@RequestParam(value = "offset", required = false) Integer offset, 
			@RequestParam(value = "limit", required = false) Integer limit) {
		return managementService.getProvinceList(strSearch, strProvinceCode, offset, limit);
	}
	
	@GetMapping(value = "/mst/city/list")
	@CrossOrigin
	public String getCityList(
			@RequestParam(value = "search", required = false) String strSearch, 
			@RequestParam(value = "pcode", required = false) String strProvinceCode,
			@RequestParam(value = "ccode", required = false) String strCityCode,
			@RequestParam(value = "offset", required = false) Integer offset, 
			@RequestParam(value = "limit", required = false) Integer limit) {
		return managementService.getCityList(strSearch, strProvinceCode, strCityCode, offset, limit);
	}
}
