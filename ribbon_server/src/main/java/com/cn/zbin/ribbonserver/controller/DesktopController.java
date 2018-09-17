package com.cn.zbin.ribbonserver.controller;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cn.zbin.ribbonserver.bto.MsgData;
import com.cn.zbin.ribbonserver.service.DesktopService;
import com.cn.zbin.ribbonserver.utils.RibbonKeyConstants;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

@RestController
@RequestMapping("desktop")
@CrossOrigin
public class DesktopController {
	protected static final Logger logger = LoggerFactory.getLogger(DesktopController.class);
    @Autowired
    private DesktopService desktopService;

    @GetMapping(value = "/order/list")
    @CrossOrigin
	public String getOrderList(
			@RequestParam(value = "empid", required = true) String empid,
			@RequestParam(value = "token", required = true) String token,
			@RequestParam(value = "status", required = false) String status,
			@RequestParam(value = "offset", required = false) Integer offset, 
			@RequestParam(value = "limit", required = false) Integer limit) {
    	logger.info("get api /desktop/order/list || empid: " + empid
				+ " || token: " + token + " || status: " + status
				+ " || offset: " + offset + " || limit: " + limit);
    	String strToken = checkToken(empid, token);
    	if (!"".equals(strToken)) return strToken;
    	String strAuth = checkAuth(empid, RibbonKeyConstants.AUTH_ORDER);
    	if (!"".equals(strAuth)) return strAuth;
    	
    	return "";
    }
    
    @PostMapping(value = "/order/courier")
    @CrossOrigin
    public String setOrderCourierNumber(
			@RequestParam(value = "empid", required = true) String empid,
			@RequestParam(value = "token", required = true) String token,
			@RequestParam(value = "orderid", required = true) String orderid,
			@RequestParam(value = "courierno", required = true) String courierno) {
    	logger.info("post api /desktop/order/courier || empid: " + empid
				+ " || token: " + token + " || orderid: " + orderid
				+ " || courierno: " + courierno);
    	String strToken = checkToken(empid, token);
    	if (!"".equals(strToken)) return strToken;
    	String strAuth = checkAuth(empid, RibbonKeyConstants.AUTH_ORDER);
    	if (!"".equals(strAuth)) return strAuth;
    	
    	return desktopService.setOrderCourierNumber(empid, orderid, courierno);
    }

    @PostMapping(value = "/order/delivery/confirm")
    @CrossOrigin
    public String confirmDelivery(
			@RequestParam(value = "empid", required = true) String empid,
			@RequestParam(value = "token", required = true) String token,
			@RequestParam(value = "orderid", required = true) String orderid) {
    	logger.info("post api /desktop/order/delivery/confirm || empid: " + empid
				+ " || token: " + token + " || orderid: " + orderid);
    	String strToken = checkToken(empid, token);
    	if (!"".equals(strToken)) return strToken;
    	String strAuth = checkAuth(empid, RibbonKeyConstants.AUTH_ORDER);
    	if (!"".equals(strAuth)) return strAuth;
    	
    	return desktopService.confirmDelivery(orderid, empid);
    }
    
    @GetMapping(value = "/order/lease/recycle/calc")
    @CrossOrigin
    public String calcRecycleAmount(
			@RequestParam(value = "empid", required = true) String empid,
			@RequestParam(value = "token", required = true) String token,
    		@RequestParam(value = "orderoperid", required = true) String orderOperId,
    		@RequestParam(value = "recycledate", required = true) String recycleDate) {
    	logger.info("get api /desktop/order/lease/recycle/calc || empid: " + empid
				+ " || token: " + token + " || orderoperid: " + orderOperId
				+ " || recycledate: " + recycleDate);
    	String strToken = checkToken(empid, token);
    	if (!"".equals(strToken)) return strToken;
    	String strAuth = checkAuth(empid, RibbonKeyConstants.AUTH_ORDER);
    	if (!"".equals(strAuth)) return strAuth;
    	
    	return desktopService.calcRecycleAmount(orderOperId, recycleDate);
    }
    
    
    
    private String checkToken(String empid, String token) {
    	Gson gson = new Gson();
    	String json = desktopService.checkToken(empid, token);
    	if (!"failed".equals(json)) {
        	MsgData ret = gson.fromJson(json, MsgData.class);
        	if (ret.getStatus() != MsgData.status_ng) {
        		json = StringUtils.EMPTY;
        	}
    	}
    	return json;
    }
    private String checkAuth(String empid, String auth) {
    	Gson gson = new Gson();
    	String json = desktopService.checkAuth(empid, auth);
    	if (!"failed".equals(json)) {
        	MsgData ret = gson.fromJson(json, MsgData.class);
        	if (ret.getStatus() != MsgData.status_ng) {
        		json = StringUtils.EMPTY;
        	}
    	}
    	return json;
    }
}
