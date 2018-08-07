package com.cn.zbin.ribbonserver.startup;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.cn.zbin.ribbonserver.bto.wechat.AccessToken;
import com.cn.zbin.ribbonserver.utils.RibbonKeyConstants;

@Component
@Order(value=1)
public class QLHStartupRunner implements CommandLineRunner {

	@Override
	public void run(String... arg0) throws Exception {
//    	Map<String, Object> uriVariables = new HashMap<String, Object>();
//    	uriVariables.put("appid", RibbonKeyConstants.APPID);
//    	uriVariables.put("secret", RibbonKeyConstants.APPSECRET);
//    	
//    	String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential"
//					+ "&appid={appid}"
//					+ "&secret={secret}";
//		RestTemplate restTemplate = new RestTemplate();
//		AccessToken atk = restTemplate.getForObject(url, AccessToken.class, uriVariables);
//		System.out.println("atk: " + atk.getAccess_token());
//		synchronized (RibbonKeyConstants.APPTOKEN) {
//			RibbonKeyConstants.APPTOKEN = atk.getAccess_token();
//		}
	}

}
