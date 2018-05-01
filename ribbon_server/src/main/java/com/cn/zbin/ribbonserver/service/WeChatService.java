package com.cn.zbin.ribbonserver.service;

import com.cn.zbin.ribbonserver.dto.WeChatMessage;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeChatService {
    @Autowired
    RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "postEventError")
    public String postEventService(String signature, String timestamp, String nonce, 
    		WeChatMessage msg) {
    	HttpEntity<WeChatMessage> request = new HttpEntity<>(msg);
        return restTemplate.postForObject("http://SERVICE-HI/event?signature=" + signature + 
        		"&timestamp=" + timestamp+"&nonce=" + nonce, request, String.class);
    }

    public String postEventError(String signature, String timestamp, String nonce, 
    		WeChatMessage msg) {
        return "failed";
    }
    
    @HystrixCommand(fallbackMethod = "getHiCheckError")
    public Boolean getHiCheckService(String signature, String timestamp, String nonce) {
        return restTemplate.getForObject("http://SERVICE-HI/hicheck?signature=" + signature +
        			"&timestamp=" + timestamp+"&nonce=" + nonce, Boolean.class);
    }
    
    public Boolean getHiCheckError(String signature, String timestamp, String nonce) {
        return Boolean.FALSE;
    }
}
