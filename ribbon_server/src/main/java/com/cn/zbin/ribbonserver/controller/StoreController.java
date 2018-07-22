package com.cn.zbin.ribbonserver.controller;

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
    @Autowired
    StoreService storeService;

    @GetMapping(value = "/slide/list")
    @CrossOrigin
    public String getSlideList() {
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
    	return storeService.getProductList(strSearch, strScope, strCate, offset, limit);
    }
    
    @GetMapping(value = "/prod/detail/{id}")
    @CrossOrigin
    public String getProductDetail(@PathVariable("id") String prodID,
    		@RequestParam("customerid") String customerid) {
    	storeService.addProductViewHistory(prodID, customerid);
    	return storeService.getProductDetail(prodID);
    }
    
    @GetMapping(value = "/prod/favorite")
    @CrossOrigin
    public String getProductFavorite(
    		@RequestParam(value = "customerid", required = true) String customerid,
    		@RequestParam(value = "limit", required = false) Integer limit) {
    	return storeService.getProductFavorite(customerid, limit);
    }
    
    @GetMapping(value = "/prod/comment")
    @CrossOrigin
    public String getProductComment(
			@RequestParam(value = "prodid", required = true) String prodID,
			@RequestParam(value = "offset", required = false) Integer offset, 
			@RequestParam(value = "limit", required = false) Integer limit) {
    	return storeService.getProductComment(prodID, offset, limit);
    }

    @GetMapping(value = "/trolley/list")
    @CrossOrigin
    public String getTrolleyList(
			@RequestParam(value = "customerid", required = true) String custid,
			@RequestParam(value = "scope", required = false) String strScope) {
    	return storeService.getTrolleyList(custid, strScope);
    }

    @PostMapping(value = "/trolley")
    @CrossOrigin
    public String add2Trolley(@RequestBody String bean) {
    	return storeService.add2Trolley(bean);
    }
    
    @PostMapping(value = "/trolley/salecount")
    @CrossOrigin
    public String updateTrolley(@RequestBody String bean) {
    	return storeService.updateTrolley(bean);
    }
    
    
    @PostMapping(value = "/order/init")
    @CrossOrigin
    public String initOrder(
    		@RequestParam(value = "customerid", required = true) String custid,
			@RequestParam(value = "type", required = true) String type,
			@RequestBody String bean) {
    	return storeService.initOrder(custid, type, bean);
    }

    @PostMapping(value = "/order/create")
    @CrossOrigin
    public String insertOrder(@RequestBody String bean) {
    	return storeService.insertOrder(bean);
    }
	
    @GetMapping(value = "/order/list")
    @CrossOrigin
	public String getGuestOrderList(
			@RequestParam(value = "customerid", required = true) String customerid,
			@RequestParam(value = "status", required = false) String status,
			@RequestParam(value = "offset", required = false) Integer offset, 
			@RequestParam(value = "limit", required = false) Integer limit) {
		return storeService.getGuestOrderList(customerid, status, offset, limit);
	}
	
    @GetMapping(value = "/order")
	public String getGuestOrder(@RequestParam("customerid") String customerid,
			@RequestParam("orderid") String orderid) {
		return storeService.getGuestOrder(customerid, orderid);
	}
    
    @GetMapping(value = "/prod/lease/calc")
    public String calcPendingCount(@RequestParam("startdate") String pendingStartDate, 
    		@RequestParam("enddate") String pendingEndDate) {
    	return storeService.calcPendingCount(pendingStartDate, pendingEndDate);
    }
}
