package com.cn.zbin.ribbonserver.controller;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cn.zbin.ribbonserver.bto.MsgData;
import com.cn.zbin.ribbonserver.service.DesktopService;
import com.cn.zbin.ribbonserver.utils.RibbonKeyConstants;
import com.google.gson.Gson;

@RestController
@RequestMapping("desktop")
@CrossOrigin
public class DesktopController {
	protected static final Logger logger = LoggerFactory.getLogger(DesktopController.class);
    @Autowired
    private DesktopService desktopService;
    
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
    
    @PostMapping(value = "/order/lease/recycle/agree")
    @CrossOrigin
    public String agreeRecycleLeaseProduct(
    		@RequestParam(value = "empid", required = true) String empid,
			@RequestParam(value = "token", required = true) String token,
			@RequestBody String bean) {
    	logger.info("post api /desktop/order/lease/recycle/agree || empid: " + empid
				+ " || token: " + token + " || bean: " + bean);
    	String strToken = checkToken(empid, token);
    	if (!"".equals(strToken)) return strToken;
    	String strAuth = checkAuth(empid, RibbonKeyConstants.AUTH_ORDER);
    	if (!"".equals(strAuth)) return strAuth;

    	return desktopService.agreeRecycleLeaseProduct(empid, bean);
    }
    
    @PostMapping(value = "/order/lease/recycle/reject")
    @CrossOrigin
    public String rejectRecycleLeaseProduct(
    		@RequestParam(value = "empid", required = true) String empid,
			@RequestParam(value = "token", required = true) String token,
			@RequestBody String bean) {
    	logger.info("post api /desktop/order/lease/recycle/reject || empid: " + empid
				+ " || token: " + token + " || bean: " + bean);
    	String strToken = checkToken(empid, token);
    	if (!"".equals(strToken)) return strToken;
    	String strAuth = checkAuth(empid, RibbonKeyConstants.AUTH_ORDER);
    	if (!"".equals(strAuth)) return strAuth;
    	
    	return desktopService.rejectRecycleLeaseProduct(empid, bean);
    }
    
    @PostMapping(value = "/order/change/agree")
    @CrossOrigin
    public String agreeChangeAllProduct(
    		@RequestParam(value = "empid", required = true) String empid,
			@RequestParam(value = "token", required = true) String token,
			@RequestBody String bean) {
    	logger.info("post api /desktop/order/change/agree || empid: " + empid
				+ " || token: " + token + " || bean: " + bean);
    	String strToken = checkToken(empid, token);
    	if (!"".equals(strToken)) return strToken;
    	String strAuth = checkAuth(empid, RibbonKeyConstants.AUTH_ORDER);
    	if (!"".equals(strAuth)) return strAuth;

    	return desktopService.agreeChangeAllProduct(empid, bean);
    }
    
    @PostMapping(value = "/order/change/reject")
    @CrossOrigin
    public String rejectChangeAllProduct(
    		@RequestParam(value = "empid", required = true) String empid,
			@RequestParam(value = "token", required = true) String token,
			@RequestBody String bean) {
    	logger.info("post api /desktop/order/change/reject || empid: " + empid
				+ " || token: " + token + " || bean: " + bean);
    	String strToken = checkToken(empid, token);
    	if (!"".equals(strToken)) return strToken;
    	String strAuth = checkAuth(empid, RibbonKeyConstants.AUTH_ORDER);
    	if (!"".equals(strAuth)) return strAuth;
    	
    	return desktopService.rejectChangeAllProduct(empid, bean);
    }
    
    @PostMapping(value = "/order/sales/return/agree")
    @CrossOrigin
    public String agreeReturnSalesProduct(
    		@RequestParam(value = "empid", required = true) String empid,
			@RequestParam(value = "token", required = true) String token,
			@RequestBody String bean) {
    	logger.info("post api /desktop/order/sales/return/agree || empid: " + empid
				+ " || token: " + token + " || bean: " + bean);
    	String strToken = checkToken(empid, token);
    	if (!"".equals(strToken)) return strToken;
    	String strAuth = checkAuth(empid, RibbonKeyConstants.AUTH_ORDER);
    	if (!"".equals(strAuth)) return strAuth;

    	return desktopService.agreeReturnSalesProduct(empid, bean);
    }
    
    @PostMapping(value = "/order/sales/return/reject")
    @CrossOrigin
    public String rejectReturnSalesProduct(
    		@RequestParam(value = "empid", required = true) String empid,
			@RequestParam(value = "token", required = true) String token,
			@RequestBody String bean) {
    	logger.info("post api /desktop/order/sales/return/reject || empid: " + empid
				+ " || token: " + token + " || bean: " + bean);
    	String strToken = checkToken(empid, token);
    	if (!"".equals(strToken)) return strToken;
    	String strAuth = checkAuth(empid, RibbonKeyConstants.AUTH_ORDER);
    	if (!"".equals(strAuth)) return strAuth;
    	
    	return desktopService.rejectReturnSalesProduct(empid, bean);
    }
    
    @GetMapping(value = "/customer/count")
    @CrossOrigin
    public String countCustomer(
    		@RequestParam(value = "empid", required = true) String empid,
			@RequestParam(value = "token", required = true) String token) {
    	logger.info("get api /desktop/customer/count || empid: " + empid
				+ " || token: " + token);
    	String strToken = checkToken(empid, token);
    	if (!"".equals(strToken)) return strToken;
    	String strAuth = checkAuth(empid, RibbonKeyConstants.AUTH_MEMBER);
    	if (!"".equals(strAuth)) return strAuth;
    	
    	return desktopService.countCustomer();
    }
    
    @GetMapping(value = "/customer/list")
    @CrossOrigin
    public String getCustomerList(
    		@RequestParam(value = "empid", required = true) String empid,
			@RequestParam(value = "token", required = true) String token,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "telno", required = false) String telno,
			@RequestParam(value = "offset", required = false) Integer offset,
			@RequestParam(value = "limit", required = false) Integer limit) {
    	logger.info("get api /desktop/customer/list || empid: " + empid
				+ " || token: " + token);
    	String strToken = checkToken(empid, token);
    	if (!"".equals(strToken)) return strToken;
    	String strAuth = checkAuth(empid, RibbonKeyConstants.AUTH_MEMBER);
    	if (!"".equals(strAuth)) return strAuth;
    	
    	return desktopService.getCustomerList(name, telno, offset, limit);
    }

    @GetMapping(value = "/order/count")
    @CrossOrigin
    public String countOrder(
    		@RequestParam(value = "empid", required = true) String empid,
			@RequestParam(value = "token", required = true) String token,
			@RequestParam(value = "lease", required = true) Integer lease) {
    	logger.info("get api /desktop/order/count || empid: " + empid
				+ " || token: " + token + " || lease: " + lease);
    	String strToken = checkToken(empid, token);
    	if (!"".equals(strToken)) return strToken;
    	String strAuth = checkAuth(empid, RibbonKeyConstants.AUTH_ORDER);
    	if (!"".equals(strAuth)) return strAuth;
    	
    	return desktopService.countOrder(lease);
    }

    @GetMapping(value = "/order/list")
    @CrossOrigin
    public String getOrderList(
    		@RequestParam(value = "empid", required = true) String empid,
			@RequestParam(value = "token", required = true) String token,
			@RequestParam(value = "status", required = false) String status,
			@RequestParam(value = "createdate", required = false) String createdate,
			@RequestParam(value = "customerid", required = false) String customerid,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "telno", required = false) String telno,
			@RequestParam(value = "offset", required = false) Integer offset,
			@RequestParam(value = "limit", required = false) Integer limit) {
    	logger.info("get api /desktop/order/list || empid: " + empid
				+ " || token: " + token + " || status: " + status
				+ " || createdate: " + createdate + " || name: " + name
				+ " || telno: " + telno + " || offset: " + offset + " || limit: " + limit);
    	String strToken = checkToken(empid, token);
    	if (!"".equals(strToken)) return strToken;
    	String strAuth = checkAuth(empid, RibbonKeyConstants.AUTH_ORDER);
    	if (!"".equals(strAuth)) return strAuth;
    	
    	return desktopService.getOrderList(status, createdate, customerid, 
    			name, telno, offset, limit);
    }

    @GetMapping(value = "/order/oper/list")
    @CrossOrigin
    public String getOrderOperationList(
    		@RequestParam(value = "empid", required = true) String empid,
			@RequestParam(value = "token", required = true) String token,
			@RequestParam(value = "askcode", required = false) String askcode,
			@RequestParam(value = "anscode", required = false) String anscode,
			@RequestParam(value = "offset", required = false) Integer offset,
			@RequestParam(value = "limit", required = false) Integer limit) {
    	logger.info("get api /desktop/order/list || empid: " + empid
				+ " || token: " + token + " || askcode: " + askcode
				+ " || anscode: " + anscode + " || offset: " + offset + " || limit: " + limit);
    	String strToken = checkToken(empid, token);
    	if (!"".equals(strToken)) return strToken;
    	String strAuth = checkAuth(empid, RibbonKeyConstants.AUTH_ORDER);
    	if (!"".equals(strAuth)) return strAuth;
    	
    	return desktopService.getOrderOperationList(askcode, anscode, offset, limit);
    }
    
    @GetMapping(value = "/order/due/list")
    @CrossOrigin
    public String getDueOrderList(
    		@RequestParam(value = "empid", required = true) String empid,
			@RequestParam(value = "token", required = true) String token,
			@RequestParam(value = "offset", required = false) Integer offset,
			@RequestParam(value = "limit", required = false) Integer limit) {
    	logger.info("get api /desktop/order/list || empid: " + empid
				+ " || token: " + token + " || offset: " + offset + " || limit: " + limit);
    	String strToken = checkToken(empid, token);
    	if (!"".equals(strToken)) return strToken;
    	String strAuth = checkAuth(empid, RibbonKeyConstants.AUTH_ORDER);
    	if (!"".equals(strAuth)) return strAuth;
    	
    	return desktopService.getDueOrderList(offset, limit);
    }

	@PostMapping(value = "/partner/create")
	@CrossOrigin
	public String createPartner(
    		@RequestParam(value = "empid", required = true) String empid,
			@RequestParam(value = "token", required = true) String token,
			@RequestBody String bean) {
		logger.info("post api: /desktop/partner/create || empid: " + empid
				+ " || token: " + token + " || bean: " + bean);
    	String strToken = checkToken(empid, token);
    	if (!"".equals(strToken)) return strToken;
    	String strAuth = checkAuth(empid, RibbonKeyConstants.AUTH_PARTNER);
    	if (!"".equals(strAuth)) return strAuth;
    	
		return desktopService.createPartner(bean);
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
