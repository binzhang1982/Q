package com.cn.zbin.ribbonserver.task;

import java.util.HashMap;
import java.util.Map;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.cn.zbin.ribbonserver.bto.wechat.AccessToken;
import com.cn.zbin.ribbonserver.utils.RibbonKeyConstants;

@Component
public class WechatTokenTask {
	
	@Scheduled(cron="0 0 0/1 * * ?")
	public void refreshToken() {
//    	Map<String, Object> uriVariables = new HashMap<String, Object>();
//    	uriVariables.put("appid", RibbonKeyConstants.APPID);
//    	uriVariables.put("secret", RibbonKeyConstants.APPSECRET);
//		
//		RestTemplate restTemplate = new RestTemplate();
//    	String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential"
//					+ "&appid={appid}"
//					+ "&secret={secret}";
//		AccessToken atk = restTemplate.getForObject(url, AccessToken.class, uriVariables);
//		System.out.println("atk: " + atk.getAccess_token());
//		synchronized (RibbonKeyConstants.APPTOKEN) {
//			RibbonKeyConstants.APPTOKEN = atk.getAccess_token();
//		}
	}
}
