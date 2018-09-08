package com.cn.zbin.ribbonserver.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class DesktopService {
    @Autowired
    RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "checkTokenError")
    public String checkToken(String empid, String token) {
    	Map<String, Object> uriVariables = new HashMap<String, Object>();
    	uriVariables.put("empid", empid);
    	uriVariables.put("token", token);
        return restTemplate.getForObject("http://SERVICE-MGMT/emp/token" 
				+ "?empid={empid}&token={token}", String.class, uriVariables);
    }
    public String checkTokenError(String empid, String token) {
    	return "failed";
    }
    @HystrixCommand(fallbackMethod = "checkAuthError")
    public String checkAuth(String empid, String auth) {
    	Map<String, Object> uriVariables = new HashMap<String, Object>();
    	uriVariables.put("empid", empid);
    	uriVariables.put("auth", auth);
        return restTemplate.getForObject("http://SERVICE-MGMT/emp/auth" 
				+ "?empid={empid}&auth={auth}", String.class, uriVariables);
    }
    public String checkAuthError(String empid, String auth) {
    	return "failed";
    }
    
    
    @HystrixCommand(fallbackMethod = "getOrderListError")
    public String getOrderList(String status, Integer offset, Integer limit) {
    	return "";
    }
    public String getOrderListError(String status, Integer offset, Integer limit) {
    	return "failed";
    }
}
