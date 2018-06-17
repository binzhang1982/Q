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
}
