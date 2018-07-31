package com.cn.zbin.ribbonserver.task;

import java.io.UnsupportedEncodingException;

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
        RestTemplate restTemplate = new RestTemplate();
        Integer count = 10000;
        String nextOpenId = "";
        String url = "";
        WechatUserList allUsers;
        while(count == 10000) {
        	if ("".equals(nextOpenId)) {
        		url = "https://api.weixin.qq.com/cgi-bin/user/get?"
        				+ "access_token=" + RibbonKeyConstants.APPTOKEN;
        	} else {
        		url = "https://api.weixin.qq.com/cgi-bin/user/get?"
        				+ "access_token=" + RibbonKeyConstants.APPTOKEN
        				+ "&next_openid=" + nextOpenId;
        	}
        	allUsers = restTemplate.getForObject(
        			url, WechatUserList.class);
        	for (String openid : allUsers.getData().getOpenid()) {
        		restTemplate.postForObject("http://localhost/wechat/user?openid="
        				+ openid, null, String.class);
        		restTemplate.postForObject("http://localhost/mgmt//cust/regtype?openid="
        				+ openid + "&regtype=1", null, String.class);
        	}
        	count = allUsers.getCount();
        	nextOpenId = allUsers.getNext_openid();
        }
	}
}
