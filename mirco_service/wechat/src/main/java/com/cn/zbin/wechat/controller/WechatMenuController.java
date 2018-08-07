package com.cn.zbin.wechat.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.cn.zbin.wechat.utils.WechatKeyConstants;

@RestController
@RequestMapping("menu")
public class WechatMenuController {
	protected static final Logger logger = LoggerFactory.getLogger(WechatMenuController.class);
    @Autowired
    private RestTemplate restTemplate; 
    
	@RequestMapping(value = "", 
			method = { RequestMethod.GET})
	public String createMenu(@RequestParam("atk") String atk) {
		logger.info("get api: /menu || atk: " + atk);
		
    	Map<String, Object> uriVariables = new HashMap<String, Object>();
    	uriVariables.put("atk", atk);
		
    	String url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token={atk}";
        HttpHeaders headers =new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<String>(WechatKeyConstants.MENU_CONTEXT, headers);
    	return restTemplate.postForObject(url, request, String.class, uriVariables);
	}
    

}
