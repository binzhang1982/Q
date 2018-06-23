package com.cn.zbin.ribbonserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    		@RequestParam("openid") String openid) {
    	storeService.addProductViewHistory(prodID, openid);
    	return storeService.getProductDetail(prodID);
    }
    
    @GetMapping(value = "/prod/favorite")
    @CrossOrigin
    public String getProductFavorite(
    		@RequestParam(value = "openid", required = true) String openid,
    		@RequestParam(value = "limit", required = false) Integer limit) {
    	return storeService.getProductFavorite(openid, limit);
    }
    
    @GetMapping(value = "/prod/comment")
    @CrossOrigin
    public String getProductComment(
			@RequestParam(value = "prodid", required = true) String prodID,
			@RequestParam(value = "offset", required = false) Integer offset, 
			@RequestParam(value = "limit", required = false) Integer limit) {
    	return storeService.getProductComment(prodID, offset, limit);
    }
}
