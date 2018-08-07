package com.cn.zbin.ribbonserver.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	protected static final Logger logger = LoggerFactory.getLogger(ManagementController.class);
	
    @Autowired
    ManagementService managementService;

	@PostMapping(value = "/cust/regtype")
	@CrossOrigin
    public String updateCustomer(@RequestParam("openid") String openid,
    		@RequestParam("regtype") Integer registerType) {
		logger.info("post api: /mgmt/cust/regtype || openid: " + openid + " || regtype: " + registerType);
		return managementService.updateCustomer(openid, registerType);
	}

	@GetMapping(value = "/cust/{customerid}")
	@CrossOrigin
	public String getRefIdByCustId(@PathVariable("customerid") String customerid) {
		logger.info("get api: /mgmt/cust/{customerid} || customerid: " + customerid);
		return managementService.getRefIdByCustId(customerid);
	}

	@GetMapping(value = "/cust/{regtype}/{refid}")
	@CrossOrigin
	public String getCustomerByRefID(@PathVariable("regtype") Integer registerType,
			@PathVariable("refid") String refid) {
		logger.info("get api: /mgmt/cust/{regtype}/{refid} || regtype: " + registerType
					+ " || refid: " + refid);
		return managementService.getCustomerByRefID(registerType, refid);
	}

	@GetMapping(value = "/cust/address/list/{customerid}")
	@CrossOrigin
	public String getCustomerAddressList(
			@PathVariable("customerid") String customerid,
			@RequestParam(value = "defaultflag", required = false) Boolean defaultflag) {
		logger.info("get api: /mgmt/cust/address/list/{customerid} || customerid: " + customerid
				+ " || defaultflag: " + defaultflag);
		return managementService.getCustomerAddressList(customerid, defaultflag);
	}

    @PostMapping(value = "/cust/address")
    @CrossOrigin
    public String updateCustomerAddress(@RequestBody String bean) {
		logger.info("post api: /mgmt/cust/address || bean: " + bean);
    	return managementService.updateCustomerAddress(bean);
    }
	
	@GetMapping(value = "/cust/invoice/list/{customerid}")
	@CrossOrigin
	public String getCustomerInvoiceList(
			@PathVariable("customerid") String customerid,
			@RequestParam(value = "defaultflag", required = false) Boolean defaultflag) {
		logger.info("get api: /mgmt/cust/invoice/list/{customerid} || customerid: " + customerid
				+ " || defaultflag: " + defaultflag);
		return managementService.getCustomerInvoiceList(customerid, defaultflag);
	}

    @PostMapping(value = "/cust/invoice")
    @CrossOrigin
    public String updateCustomerInvoice(@RequestBody String bean) {
		logger.info("post api: mgmt/cust/invoice || bean: " + bean);
    	return managementService.updateCustomerInvoice(bean);
    }

	@GetMapping(value = "/mst/province/list")
	@CrossOrigin
	public String getProvinceList(
			@RequestParam(value = "search", required = false) String strSearch, 
			@RequestParam(value = "pcode", required = false) String strProvinceCode,
			@RequestParam(value = "offset", required = false) Integer offset, 
			@RequestParam(value = "limit", required = false) Integer limit) {
		logger.info("get api: /mgmt/mst/province/list || search: " + strSearch
				+ " || pcode: " + strProvinceCode + " || offset: " + offset
				+ " || limit: " + limit);
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
		logger.info("get api: /mgmt/mst/city/list || search: " + strSearch
				+ " || pcode: " + strProvinceCode + " || ccode: " + strCityCode 
				+ " || offset: " + offset + " || limit: " + limit);
		return managementService.getCityList(strSearch, strProvinceCode, strCityCode, offset, limit);
	}

    @PostMapping(value = "/cust/info")
    @CrossOrigin
    public String updateCustomerInfo(@RequestBody String bean) {
		logger.info("post api: /mgmt/cust/info || bean: " + bean);
    	return managementService.updateCustomerInfo(bean);
    }

    @GetMapping(value = "/cust/phone/{customerid}/{phonenumber}")
    @CrossOrigin
    public String addPhoneNum(
    		@PathVariable("customerid") String customerid, 
    		@PathVariable("phonenumber") String phonenumber) {
		logger.info("get api: /mgmt/cust/phone/{customerid}/{phonenumber}"
				+ " || customerid: " + customerid
				+ " || phonenumber: " + phonenumber); 
    	return managementService.addPhoneNum(customerid, phonenumber);
    }
    
    @GetMapping(value = "/cust/valid/{customerid}/{validcode}")
    @CrossOrigin
	public String comfirmValidCode(
			@PathVariable("customerid") String customerid, 
			@PathVariable("validcode") String validcode) {
		logger.info("get api: /mgmt/cust/valid/{customerid}/{validcode}"
				+ " || customerid: " + customerid
				+ " || validcode: " + validcode); 
    	return managementService.comfirmValidCode(customerid, validcode);
    }
}
