package com.cn.zbin.ribbonserver.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class ManagementService {
    @Autowired
    RestTemplate restTemplate;
    
    @HystrixCommand(fallbackMethod = "updateCustomerError")
    public String updateCustomer(String openid, Integer registerType) {
    	Map<String, Object> uriVariables = new HashMap<String, Object>();
    	uriVariables.put("openid", openid);
    	uriVariables.put("regtype", registerType);
        return restTemplate.postForObject("http://SERVICE-MGMT/customer?openid={openid}" 
				+ "&regtype={regtype}", null, String.class, uriVariables);
    }
    public String updateCustomerError(String openid, Integer registerType) {
        return "failed";
    }

    @HystrixCommand(fallbackMethod = "getCustomerByRefIDError")
	public String getCustomerByRefID(Integer registerType, String refid) {
    	Map<String, Object> uriVariables = new HashMap<String, Object>();
    	uriVariables.put("registerType", registerType);
    	uriVariables.put("refid", refid);
        return restTemplate.getForObject("http://SERVICE-MGMT/customer/" 
				+ "{registerType}/{refid}", String.class, uriVariables);
	}
	public String getCustomerByRefIDError(Integer registerType, String refid) {
		return "failed";
	}

    @HystrixCommand(fallbackMethod = "getRefIdByCustIdError")
	public String getRefIdByCustId(String customerid) {
    	Map<String, Object> uriVariables = new HashMap<String, Object>();
    	uriVariables.put("customerid", customerid);
        return restTemplate.getForObject("http://SERVICE-MGMT/customer/" 
				+ "{customerid}", String.class, uriVariables);
	}
	public String getRefIdByCustIdError(String customerid) {
		return "failed";
	}

    @HystrixCommand(fallbackMethod = "getCustomerAddressListError")
	public String getCustomerAddressList(String customerid, Boolean defaultflag) {
    	Map<String, Object> uriVariables = new HashMap<String, Object>();
    	uriVariables.put("customerid", customerid);
    	uriVariables.put("defaultflag", defaultflag != null ? defaultflag : "");
    	String url = "http://SERVICE-MGMT/customer/address/list/{customerid}"
    				+ "?defaultflag={defaultflag}";
    	return restTemplate.getForObject(url, String.class, uriVariables);
	}
	public String getCustomerAddressListError(String customerid, Boolean defaultflag) {
		return "failed";
	}
	
	@HystrixCommand(fallbackMethod = "updateCustomerAddressError")
	public String updateCustomerAddress(String bean) {
    	String url = "http://SERVICE-MGMT/customer/address";
        HttpHeaders headers =new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        HttpEntity<String> request = new HttpEntity<String>(bean, headers);
    	return restTemplate.postForObject(url, request, String.class);
	}
	public String updateCustomerAddressError(String bean) {
		return "failed";
	}
	
    @HystrixCommand(fallbackMethod = "getCustomerInvoiceListError")
	public String getCustomerInvoiceList(String customerid, Boolean defaultflag) {
    	Map<String, Object> uriVariables = new HashMap<String, Object>();
    	uriVariables.put("customerid", customerid);
    	uriVariables.put("defaultflag", defaultflag != null ? defaultflag : "");
    	String url = "http://SERVICE-MGMT/customer/invoice/list/{customerid}"
    				+ "?defaultflag={defaultflag}";
    	return restTemplate.getForObject(url, String.class, uriVariables);
	}
	public String getCustomerInvoiceListError(String customerid, Boolean defaultflag) {
		return "failed";
	}
	
	@HystrixCommand(fallbackMethod = "updateCustomerInvoiceError")
	public String updateCustomerInvoice(String bean) {
    	String url = "http://SERVICE-MGMT/customer/invoice";
        HttpHeaders headers =new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        HttpEntity<String> request = new HttpEntity<String>(bean, headers);
    	return restTemplate.postForObject(url, request, String.class);
	}
	public String updateCustomerInvoiceError(String bean) {
		return "failed";
	}

	@HystrixCommand(fallbackMethod = "getCityListError")
	public String getCityList(String strSearch, String strProvinceCode, 
			String strCityCode,	Integer offset, Integer limit) {
    	Map<String, Object> uriVariables = new HashMap<String, Object>();
    	uriVariables.put("search", strSearch != null ? strSearch : "");
    	uriVariables.put("pcode", strProvinceCode != null ? strProvinceCode : "");
    	uriVariables.put("ccode", strCityCode != null ? strCityCode : "");
    	uriVariables.put("offset", offset != null ? offset : "");
    	uriVariables.put("limit", limit != null ? limit : "");
    	
    	String url = "http://SERVICE-MGMT/master/city/list"
    				+ "?search={search}"
    				+ "&pcode={pcode}"
    				+ "&ccode={ccode}"
    				+ "&offset={offset}"
    				+ "&limit={limit}";
    	return restTemplate.getForObject(url, String.class, uriVariables);
	}
	public String getCityListError(String strSearch, String strProvinceCode, 
			String strCityCode,	Integer offset, Integer limit) {
		return "failed";
	}
	
	@HystrixCommand(fallbackMethod = "getProvinceListError")
	public String getProvinceList(String strSearch, 
			String strProvinceCode, Integer offset, Integer limit) {
    	Map<String, Object> uriVariables = new HashMap<String, Object>();
    	uriVariables.put("search", strSearch != null ? strSearch : "");
    	uriVariables.put("pcode", strProvinceCode != null ? strProvinceCode : "");
    	uriVariables.put("offset", offset != null ? offset : "");
    	uriVariables.put("limit", limit != null ? limit : "");
    	
    	String url = "http://SERVICE-MGMT/master/province/list"
    				+ "?search={search}"
    				+ "&pcode={pcode}"
    				+ "&offset={offset}"
    				+ "&limit={limit}";
    	return restTemplate.getForObject(url, String.class, uriVariables);
	}
	public String getProvinceListError(String strSearch, 
			String strProvinceCode, Integer offset, Integer limit) {
		return "failed";
	}
	
	@HystrixCommand(fallbackMethod = "updateCustomerInfoError")
	public String updateCustomerInfo(String bean) {
    	String url = "http://SERVICE-MGMT/customer/info";
        HttpHeaders headers =new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        HttpEntity<String> request = new HttpEntity<String>(bean, headers);
    	return restTemplate.postForObject(url, request, String.class);
	}
	public String updateCustomerInfoError(String bean) {
		return "failed";
	}

	@HystrixCommand(fallbackMethod = "addPhoneNumError")
	public String addPhoneNum(String customerid, String phonenumber) {
    	Map<String, Object> uriVariables = new HashMap<String, Object>();
    	uriVariables.put("customerid", customerid);
    	uriVariables.put("phonenumber", phonenumber);
    	
    	String url = "http://SERVICE-MGMT/customer/phone/"
    			+ "{customerid}/{phonenumber}";
    	return restTemplate.getForObject(url, String.class, uriVariables);
	}
	public String addPhoneNumError(String customerid, String phonenumber) {
		return "failed";
	}

	@HystrixCommand(fallbackMethod = "comfirmValidCodeError")
	public String comfirmValidCode(String customerid, String validcode) {
    	Map<String, Object> uriVariables = new HashMap<String, Object>();
    	uriVariables.put("customerid", customerid);
    	uriVariables.put("validcode", validcode);
    	
    	String url = "http://SERVICE-MGMT/customer/valid/"
    			+ "{customerid}/{validcode}";
    	return restTemplate.getForObject(url, String.class, uriVariables);
	}
	public String comfirmValidCodeError(String customerid, String validcode) {
		return "failed";
	}

	@HystrixCommand(fallbackMethod = "updateTokenError")
	public String updateToken(String bean) {
    	String url = "http://SERVICE-MGMT/customer/oauthatk";
        HttpHeaders headers =new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        HttpEntity<String> request = new HttpEntity<String>(bean, headers);
    	return restTemplate.postForObject(url, request, String.class);
	}
	public String updateTokenError(String bean) {
		return "failed";
	}
}
