package com.cn.zbin.ribbonserver.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.cn.zbin.ribbonserver.bto.wechat.AccessToken;
import com.cn.zbin.ribbonserver.utils.RibbonKeyConstants;

@Component
public class WechatTokenTask {
	
	@Scheduled(cron="0 0 0/1 * * ?")
	public void refreshToken() {
		RestTemplate restTemplate = new RestTemplate();
		AccessToken atk = restTemplate.getForObject(
				"https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential"
				+ "&appid=" + RibbonKeyConstants.APPID
				+ "&secret=" + RibbonKeyConstants.APPSECRET, 
				AccessToken.class);
		System.out.println("task:" + atk.getAccess_token());
		synchronized (RibbonKeyConstants.APPTOKEN) {
			RibbonKeyConstants.APPTOKEN = atk.getAccess_token();
		}
	}
}
