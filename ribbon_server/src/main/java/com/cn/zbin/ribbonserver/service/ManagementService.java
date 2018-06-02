package com.cn.zbin.ribbonserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class ManagementService {
    @Autowired
    RestTemplate restTemplate;
    
    @HystrixCommand(fallbackMethod = "updateCustomerError")
    public String updateCustomer(String openid, Integer registerType) {
    	String ret = "";
		try {
    		ret = restTemplate.postForObject("http://SERVICE-MGMT/customer/update?openid=" 
					 + openid + "&regtype=" + registerType, null, String.class);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return ret;
    }

    public String updateCustomerError(String openid, Integer registerType) {
        return "failed";
    }
}
