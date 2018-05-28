package com.cn.zbin.ribbonserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class ManagementService {
    @Autowired
    RestTemplate restTemplate;
    
    @HystrixCommand(fallbackMethod = "getAllSlidesError")
    public String getAllSlides() {
    	return restTemplate.getForObject("http://SERVICE-MGMT/slides/all", String.class);
    }
    public String getAllSlidesError() {
    	return "failed";
    }
}
