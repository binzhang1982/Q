package com.cn.zbin.ribbonserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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
    	url = url + "&limit=";
    	if (limit != null) url = url + limit;
    	return restTemplate.getForObject(url, String.class);
    }
    public String getProductFavoriteError(String openid, Integer limit) {
    	return "failed";
    }

    @HystrixCommand(fallbackMethod = "getProductCommentError")
    public String getProductComment(String prodID, Integer offset, Integer limit) {
    	String url = "http://SERVICE-STORE/prod/comment";
    	url = url + "?prodid=" + prodID;
    	url = url + "&offset=";
    	if (limit != null) url = url + offset;
    	url = url + "&limit=";
    	if (limit != null) url = url + limit;
    	return restTemplate.getForObject(url, String.class);
    }
    public String getProductCommentError(String prodID, Integer offset, Integer limit) {
    	return "failed";
    }

    @HystrixCommand(fallbackMethod = "getTrolleyListError")
    public String getTrolleyList(String custid, String strScope) {
    	String url = "http://SERVICE-STORE/trolley/list";
    	url = url + "?customerid=" + custid;
    	url = url + "&scope=";
    	if (strScope != null) url = url + strScope;
    	return restTemplate.getForObject(url, String.class);
    }
    public String getTrolleyListError(String custid, String strScope) {
    	return "failed";
    }

    @HystrixCommand(fallbackMethod = "add2TrolleyError")
    public String add2Trolley(String bean) {
    	String url = "http://SERVICE-STORE/trolley";
        HttpHeaders headers =new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        HttpEntity<String> request = new HttpEntity<String>(bean, headers);
    	return restTemplate.postForObject(url, request, String.class);
    }
    public String add2TrolleyError(String bean) {
    	return "failed";
    }

    @HystrixCommand(fallbackMethod = "initOrderError")
    public String initOrder(String custid, String type, String bean) {
    	String url = "http://SERVICE-STORE/order/init";
    	url = url + "?customerid=" + custid;
    	url = url + "&type=" + type;
        HttpHeaders headers =new HttpHeaders();
        MediaType mtype = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(mtype);
        HttpEntity<String> request = new HttpEntity<String>(bean, headers);
    	return restTemplate.postForObject(url, request, String.class);
    }
    public String initOrderError(String custid, String type, String bean) {
    	return "failed";
    }
}
