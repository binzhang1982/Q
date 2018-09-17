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

    @HystrixCommand(fallbackMethod = "setOrderCourierNumberError")
    public String setOrderCourierNumber(String empid, String orderid, String courierno) {
    	Map<String, Object> uriVariables = new HashMap<String, Object>();
    	uriVariables.put("empid", empid);
    	uriVariables.put("orderid", orderid);
    	uriVariables.put("courierno", courierno);

        return restTemplate.postForObject("http://SERVICE-STORE/order/courier" 
    			+ "?empid={empid}&orderid={orderid}&courierno={courierno}", null, String.class, 
    			uriVariables);
    }
    public String setOrderCourierNumberError(String empid, String orderid, String courierno) {
    	return "failed";
    }
    
    @HystrixCommand(fallbackMethod = "confirmDeliveryError")
    public String confirmDelivery(String orderid, String empid) {
    	Map<String, Object> uriVariables = new HashMap<String, Object>();
    	uriVariables.put("orderid", orderid);
    	uriVariables.put("id", empid);
    	uriVariables.put("type", 2);

        return restTemplate.postForObject("http://SERVICE-STORE/order/delivery/confirm" 
				+ "?orderid={orderid}&id={id}&type={type}", null, String.class, 
				uriVariables);
    }
    public String confirmDeliveryError(String orderId, String customerId) {
    	return "failed";
    }
    
    @HystrixCommand(fallbackMethod = "calcRecycleAmountError")
    public String calcRecycleAmount(String orderOperId, String recycleDate) {
    	Map<String, Object> uriVariables = new HashMap<String, Object>();
    	uriVariables.put("orderoperid", orderOperId);
    	uriVariables.put("recycledate", recycleDate);

        return restTemplate.getForObject("http://SERVICE-STORE/order/lease/recycle/calc" 
				+ "?orderoperid={orderoperid}&recycledate={recycledate}", String.class, 
				uriVariables);
    }
    public String calcRecycleAmountError(String orderOperId, String recycleDate) {
    	return "failed";
    }
}
