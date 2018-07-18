package com.cn.zbin.ribbonserver.service;

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
        return restTemplate.postForObject("http://SERVICE-MGMT/customer?openid=" 
				+ openid + "&regtype=" + registerType, null, String.class);
    }
    public String updateCustomerError(String openid, Integer registerType) {
        return "failed";
    }

    @HystrixCommand(fallbackMethod = "getCustomerByRefIDError")
	public String getCustomerByRefID(Integer registerType, String refid) {
        return restTemplate.getForObject("http://SERVICE-MGMT/customer/" 
				+ registerType + "/" + refid, String.class);
	}
	public String getCustomerByRefIDError(Integer registerType, String refid) {
		return "failed";
	}

    @HystrixCommand(fallbackMethod = "getRefIdByCustIdError")
	public String getRefIdByCustId(String customerid) {
        return restTemplate.getForObject("http://SERVICE-MGMT/customer/" 
				+ customerid, String.class);
	}
	public String getRefIdByCustIdError(String customerid) {
		return "failed";
	}

    @HystrixCommand(fallbackMethod = "getCustomerAddressListError")
	public String getCustomerAddressList(String customerid, Boolean defaultflag) {
    	String url = "http://SERVICE-MGMT/customer/address/list/" + customerid;
    	url = url + "?defaultflag=";
    	if (defaultflag != null) url = url + defaultflag;
    	return restTemplate.getForObject(url, String.class);
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
    	String url = "http://SERVICE-MGMT/customer/invoice/list/" + customerid;
    	url = url + "?defaultflag=";
    	if (defaultflag != null) url = url + defaultflag;
    	return restTemplate.getForObject(url, String.class);
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
    	String url = "http://SERVICE-MGMT/master/city/list";
    	url = url + "?search=";
    	if (strSearch != null) url = url + strSearch;
    	url = url + "&pcode=";
    	if (strProvinceCode != null) url = url + strProvinceCode;
    	url = url + "&ccode=";
    	if (strCityCode != null) url = url + strCityCode;
    	url = url + "&offset=";
    	if (offset != null) url = url + offset;
    	url = url + "&limit=";
    	if (limit != null) url = url + limit;
    	return restTemplate.getForObject(url, String.class);
	}
	public String getCityListError(String strSearch, String strProvinceCode, 
			String strCityCode,	Integer offset, Integer limit) {
		return "failed";
	}
	
	@HystrixCommand(fallbackMethod = "getProvinceListError")
	public String getProvinceList(String strSearch, 
			String strProvinceCode, Integer offset, Integer limit) {
    	String url = "http://SERVICE-MGMT/master/province/list";
    	url = url + "?search=";
    	if (strSearch != null) url = url + strSearch;
    	url = url + "&pcode=";
    	if (strProvinceCode != null) url = url + strProvinceCode;
    	url = url + "&offset=";
    	if (offset != null) url = url + offset;
    	url = url + "&limit=";
    	if (limit != null) url = url + limit;
    	return restTemplate.getForObject(url, String.class);
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
}
