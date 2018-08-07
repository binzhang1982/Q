package com.cn.zbin.ribbonserver.task;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.cn.zbin.ribbonserver.bto.wechat.WechatUserList;
import com.cn.zbin.ribbonserver.utils.RibbonKeyConstants;

@Component
public class WechatUserTask {
	
	@Scheduled(cron="0 0/30 * * * ? ")
	public void getAllUser() throws RestClientException, UnsupportedEncodingException {
//        RestTemplate restTemplate = new RestTemplate();
//        Integer count = 10000;
//        String nextOpenId = "";
//        String url = "";
//        WechatUserList allUsers;
//    	Map<String, Object> uriVariables_atk;
//    	Map<String, Object> uriVariables_wechat;
//    	Map<String, Object> uriVariables_mgmt;
//        while(count == 10000) {
//        	uriVariables_atk = new HashMap<String, Object>();
//        	if ("".equals(nextOpenId)) {
//        		uriVariables_atk.put("atk", RibbonKeyConstants.APPTOKEN);
//        		url = "https://api.weixin.qq.com/cgi-bin/user/get?"
//        				+ "access_token={atk}";
//        	} else {
//        		uriVariables_atk.put("atk", RibbonKeyConstants.APPTOKEN);
//        		uriVariables_atk.put("nextOpenId", nextOpenId);
//        		url = "https://api.weixin.qq.com/cgi-bin/user/get?"
//        				+ "access_token={atk}"
//        				+ "&next_openid={nextOpenId}";
//        	}
//        	allUsers = restTemplate.getForObject(
//        			url, WechatUserList.class, uriVariables_atk);
//        	for (String openid : allUsers.getData().getOpenid()) {
//        		uriVariables_wechat = new HashMap<String, Object>();
//        		uriVariables_wechat.put("openid", openid);
//        		String url_wechat = "http://localhost/wechat/user?openid={openid}";
//        		restTemplate.postForObject(url_wechat, null, String.class, uriVariables_wechat);
//        		
//        		uriVariables_mgmt = new HashMap<String, Object>();
//        		uriVariables_mgmt.put("openid", openid);
//        		String url_mgmt = "http://localhost/mgmt//cust/regtype?openid={openid}&regtype=1";
//        		restTemplate.postForObject(url_mgmt, null, String.class, uriVariables_mgmt);
//        	}
//        	count = allUsers.getCount();
//        	nextOpenId = allUsers.getNext_openid();
//        }
	}
}
