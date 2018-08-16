package com.cn.zbin.ribbonserver.task;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.cn.zbin.ribbonserver.bto.wechat.WechatUserList;
import com.cn.zbin.ribbonserver.service.AsyncService;
import com.cn.zbin.ribbonserver.utils.RibbonKeyConstants;

@Component
public class WechatUserTask {
	protected static final Logger logger = LoggerFactory.getLogger(WechatUserTask.class);
	
	@Autowired
	private AsyncService asyncService;
	
	@Scheduled(cron="0 0/5 * * * ? ")
	public void getAllUser() throws RestClientException, UnsupportedEncodingException {
		RestTemplate restTemplate = new RestTemplate();
		Integer count = 10000;
		String nextOpenId = "";
		String url = "";
		WechatUserList allUsers;
    	Map<String, Object> uriVariables_atk;
    	while(count == 10000) {
        	uriVariables_atk = new HashMap<String, Object>();
        	if ("".equals(nextOpenId)) {
            	uriVariables_atk.put("atk", RibbonKeyConstants.APPTOKEN);
            	url = "https://api.weixin.qq.com/cgi-bin/user/get?"
            	    	+ "access_token={atk}";
            } else {
                uriVariables_atk.put("atk", RibbonKeyConstants.APPTOKEN);
        		uriVariables_atk.put("nextOpenId", nextOpenId);
        		url = "https://api.weixin.qq.com/cgi-bin/user/get?"
        				+ "access_token={atk}"
        				+ "&next_openid={nextOpenId}";
        	}
        	allUsers = restTemplate.getForObject(
        	    	url, WechatUserList.class, uriVariables_atk);
        	for (String openid : allUsers.getData().getOpenid()) {
        		logger.info("openid: {}", openid);
        		asyncService.executeWechatUserAsync(openid);
        		asyncService.executeMgmtCustAsync(openid);
        	}
        	count = allUsers.getCount();
        	nextOpenId = allUsers.getNext_openid();
        }
	}
}
