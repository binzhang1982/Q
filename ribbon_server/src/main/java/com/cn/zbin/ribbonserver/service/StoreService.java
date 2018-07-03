package com.cn.zbin.ribbonserver.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class StoreService {
    @Autowired
    RestTemplate restTemplate;
    
    @HystrixCommand(fallbackMethod = "getSlideListError")
    public String getSlideList() {
    	return restTemplate.getForObject("http://SERVICE-STORE/slide/list", String.class);
    }
    public String getSlideListError() {
    	return "failed";
    }

    @HystrixCommand(fallbackMethod = "getProductListError")
    public String getProductList(String strSearch, String strScope, 
    		String strCate, Integer offset, Integer limit) {
    	String url = "http://SERVICE-STORE/prod/list";
    	url = url + "?search=";
    	if (strSearch != null) url = url + strSearch;
    	url = url + "&scope=";
    	if (strScope != null) url = url + strScope;
    	url = url + "&cate=";
    	if (strCate != null) url = url + strCate;
    	url = url + "&offset=";
    	if (offset != null) url = url + offset;
    	url = url + "&limit=";
    	if (limit != null) url = url + limit;
    	return restTemplate.getForObject(url, String.class);
    }
    public String getProductListError(String strSearch, String strScope, 
    		String strCate,Integer offset, Integer limit) {
    	return "failed";
    }
    
    @HystrixCommand(fallbackMethod = "getProductDetailError")
    public String getProductDetail(String prodID) {
    	String url = "http://SERVICE-STORE/prod/detail/" + prodID;
    	return restTemplate.getForObject(url, String.class);
    }
    public String getProductDetailError(String prodID) {
    	return "failed";
    }

    @HystrixCommand(fallbackMethod = "addProductViewHistoryError")
    public String addProductViewHistory(String prodID, String customerid) {
    	String url = "http://SERVICE-STORE/prod/viewhist/" + prodID + "?customerid=" + customerid;
    	return restTemplate.postForObject(url, null, String.class);
    }
    public String addProductViewHistoryError(String prodID, String openid) {
    	return "failed";
    }

    @HystrixCommand(fallbackMethod = "getProductFavoriteError")
    public String getProductFavorite(String customerid, Integer limit) {
    	String url = "http://SERVICE-STORE/prod/favorite";
    	url = url + "?customerid=" + customerid;   	
    	Map<String, Object> urlVariables = new HashMap<String, Object>();
    	if (limit != null) urlVariables.put("limit", limit);
    	return restTemplate.getForObject(url, String.class, urlVariables);
    }
    public String getProductFavoriteError(String openid, Integer limit) {
    	return "failed";
    }

    @HystrixCommand(fallbackMethod = "getProductCommentError")
    public String getProductComment(String prodID, Integer offset, Integer limit) {
    	String url = "http://SERVICE-STORE/prod/comment";
    	url = url + "?prodid=" + prodID;   	
    	Map<String, Object> urlVariables = new HashMap<String, Object>();
    	if (offset != null) urlVariables.put("offset", offset);
    	if (limit != null) urlVariables.put("limit", limit);
    	return restTemplate.getForObject(url, String.class, urlVariables);
    }
    public String getProductCommentError(String prodID, Integer offset, Integer limit) {
    	return "failed";
    }
}
