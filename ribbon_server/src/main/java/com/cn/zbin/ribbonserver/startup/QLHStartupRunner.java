package com.cn.zbin.ribbonserver.startup;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.cn.zbin.ribbonserver.bto.wechat.AccessToken;
import com.cn.zbin.ribbonserver.utils.RibbonConstants;

@Component
@Order(value=1)
public class QLHStartupRunner implements CommandLineRunner {

	@Override
	public void run(String... arg0) throws Exception {
		RestTemplate restTemplate = new RestTemplate();
		AccessToken atk = restTemplate.getForObject(
				"https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential"
				+ "&appid=" + RibbonConstants.APPID
				+ "&secret=" + RibbonConstants.APPSECRET, 
				AccessToken.class);
		System.out.println(atk.getAccess_token());
		synchronized (RibbonConstants.APPTOKEN) {
			RibbonConstants.APPTOKEN = atk.getAccess_token();
		}
	}

}
