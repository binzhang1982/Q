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

import com.cn.zbin.ribbonserver.service.StoreService;

@RestController
@RequestMapping("store")
@CrossOrigin
public class StoreController {
	protected static final Logger logger = LoggerFactory.getLogger(StoreController.class);
    @Autowired
    StoreService storeService;

    @GetMapping(value = "/slide/list")
    @CrossOrigin
    public String getSlideList() {
		logger.info("get api: /store/slide/list");
    	return storeService.getSlideList();
    }
    
    @GetMapping(value = "/prod/list")
    @CrossOrigin
    public String getProductList(
    		@RequestParam(value = "search", required = false) String strSearch, 
			@RequestParam(value = "scope", required = false) String strScope,
			@RequestParam(value = "cate", required = false) String strCate,
			@RequestParam(value = "offset", required = false) Integer offset, 
			@RequestParam(value = "limit", required = false) Integer limit) {
		logger.info("get api: /store/prod/list || search: " + strSearch + " || scope: " + strScope
					+ " || cate: " + strCate + " || offset: " + offset 
					+ " || limit: " + limit);
    	return storeService.getProductList(strSearch, strScope, strCate, offset, limit);
    }
    
    @GetMapping(value = "/prod/detail/{id}")
    @CrossOrigin
    public String getProductDetail(@PathVariable("id") String prodID,
    		@RequestParam("customerid") String customerid) {
		logger.info("get api: /store/prod/detail/{id} || id: " + prodID
				+ " || customerid: " + customerid);
    	storeService.addProductViewHistory(prodID, customerid);
    	return storeService.getProductDetail(prodID);
    }
    
    @GetMapping(value = "/prod/favorite")
    @CrossOrigin
    public String getProductFavorite(
    		@RequestParam(value = "customerid", required = true) String customerid,
    		@RequestParam(value = "limit", required = false) Integer limit) {
		logger.info("get api: /store/prod/favorite || customerid: " + customerid
				+ " || limit: " + limit);
    	return storeService.getProductFavorite(customerid, limit);
    }
    
    @GetMapping(value = "/prod/comment")
    @CrossOrigin
    public String getProductComment(
			@RequestParam(value = "prodid", required = true) String prodID,
			@RequestParam(value = "offset", required = false) Integer offset, 
			@RequestParam(value = "limit", required = false) Integer limit) {
		logger.info("get api: /store/prod/comment || prodid: " + prodID
				+ " || offset: " + offset + " || limit: " + limit);
    	return storeService.getProductComment(prodID, offset, limit);
    }

    @GetMapping(value = "/trolley/list")
    @CrossOrigin
    public String getTrolleyList(
			@RequestParam(value = "customerid", required = true) String custid,
			@RequestParam(value = "scope", required = false) String strScope) {
		logger.info("get api: /store/trolley/list || customerid: " + custid
				+ " || scope: " + strScope);
    	return storeService.getTrolleyList(custid, strScope);
    }

    @PostMapping(value = "/trolley")
    @CrossOrigin
    public String add2Trolley(@RequestBody String bean) {
		logger.info("post api: /store/trolley || bean: " + bean);
    	return storeService.add2Trolley(bean);
    }
    
    @PostMapping(value = "/trolley/salecount")
    @CrossOrigin
    public String updateTrolley(@RequestBody String bean) {
		logger.info("post api: /store/trolley/salecount || bean: " + bean);
    	return storeService.updateTrolley(bean);
    }
    
    @PostMapping(value = "/order/init")
    @CrossOrigin
    public String initOrder(
    		@RequestParam(value = "customerid", required = true) String custid,
			@RequestParam(value = "type", required = true) String type,
			@RequestBody String bean) {
		logger.info("post api: /store/order/init"
				+ " || customerid: " + custid +  " || type: " + type
				+ " || bean: " + bean);
    	return storeService.initOrder(custid, type, bean);
    }

    @PostMapping(value = "/order/create")
    @CrossOrigin
    public String insertOrder(@RequestBody String bean) {
		logger.info("post api: /store/order/create || bean: " + bean);
    	return storeService.insertOrder(bean);
    }
	
    @GetMapping(value = "/order/list")
    @CrossOrigin
	public String getGuestOrderList(
			@RequestParam(value = "customerid", required = true) String customerid,
			@RequestParam(value = "status", required = false) String status,
			@RequestParam(value = "offset", required = false) Integer offset, 
			@RequestParam(value = "limit", required = false) Integer limit) {
		logger.info("get api: /store/order/list || customerid: " + customerid
				+ " || status: " + status
				+ " || offset: " + offset + " || limit: " + limit);
		return storeService.getGuestOrderList(customerid, status, offset, limit);
	}
	
    @GetMapping(value = "/order")
    @CrossOrigin
	public String getGuestOrder(@RequestParam("customerid") String customerid,
			@RequestParam("orderid") String orderid) {
		logger.info("get api: /store/order || customerid: " + customerid
				+ " || orderid: " + orderid);
		return storeService.getGuestOrder(customerid, orderid);
	}
    
    @GetMapping(value = "/prod/lease/calc")
    @CrossOrigin
    public String calcPendingCount(@RequestParam("startdate") String pendingStartDate, 
    		@RequestParam("enddate") String pendingEndDate) {
		logger.info("get api: /store/prod/lease/calc || startdate: " + pendingStartDate
				+ " || enddate: " + pendingEndDate);
    	return storeService.calcPendingCount(pendingStartDate, pendingEndDate);
    }
    
    @PostMapping(value = "/order/cancel/cust/{id}")
    @CrossOrigin
	public String cancelOrderByCustomer(
			@RequestParam(value = "id", required = true) String customerid,
			@RequestBody String bean) {
    	return storeService.cancelOrderByCustomer(customerid, bean);
    }
}
