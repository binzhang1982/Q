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
    	Map<String, Object> urlVariables = new HashMap<String, Object>();
    	if (strSearch != null) urlVariables.put("search", strSearch);
    	if (strScope != null) urlVariables.put("scope", strScope);
    	if (strCate != null) urlVariables.put("cate", strCate);
    	if (offset != null) urlVariables.put("offset", offset);
    	if (limit != null) urlVariables.put("limit", limit);
    	return restTemplate.getForObject(url, String.class, urlVariables);
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
    public String addProductViewHistory(String prodID, String openid) {
    	String url = "http://SERVICE-STORE/prod/viewhist/" + prodID + "?openid=" + openid;
    	return restTemplate.postForObject(url, null, String.class);
    }
    public String addProductViewHistoryError(String prodID, String openid) {
    	return "failed";
    }

    @HystrixCommand(fallbackMethod = "getProductFavoriteError")
    public String getProductFavorite(String openid, Integer limit) {
    	String url = "http://SERVICE-STORE/prod/favorite";
    	url = url + "?openid=" + openid;   	
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
