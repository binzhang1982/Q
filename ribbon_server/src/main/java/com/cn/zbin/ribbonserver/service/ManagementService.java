package com.cn.zbin.ribbonserver.service;

import org.springframework.beans.factory.annotation.Autowired;
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
}
