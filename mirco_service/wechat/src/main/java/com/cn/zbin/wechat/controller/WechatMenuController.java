package com.cn.zbin.wechat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.cn.zbin.wechat.utils.WechatConstants;

@RestController
@RequestMapping("menu")
public class WechatMenuController {
    @Autowired
    private RestTemplate restTemplate; 
    
	@RequestMapping(value = "", method = { RequestMethod.GET})
	public String createMenu(@RequestParam("atk") String atk) {
    	String url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=" + atk;
        HttpHeaders headers =new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<String>(WechatConstants.MENU_CONTEXT, headers);
    	return restTemplate.postForObject(url, request, String.class);
	}
    

}
