package com.cn.zbin.ribbonserver.service;

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

}
