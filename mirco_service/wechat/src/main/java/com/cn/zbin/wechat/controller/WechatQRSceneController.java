package com.cn.zbin.wechat.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("qr")
public class WechatQRSceneController {
    @Autowired
    private RestTemplate restTemplate; 
	
	@RequestMapping(value = "/qr_limit_scene", method = { RequestMethod.GET })
	public String getQRLimitScene(@RequestParam("atk") String atk, 
			@RequestParam("scenestr") String scenestr) throws UnsupportedEncodingException {
		System.out.println("qrlimitscene:" + atk + " qrlimitscene:" + scenestr);
		String url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token="
				 + URLEncoder.encode(atk, "UTF-8");
		HttpHeaders headers = new HttpHeaders();
		MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
		headers.setContentType(type);
		String requestJson = "{\"action_name\": \"QR_LIMIT_STR_SCENE\", \"action_info\": {\"scene\": {\"scene_str\": \"" + scenestr + "\"}}}";
		HttpEntity<String> entity = new HttpEntity<String>(requestJson,headers);
		String result = restTemplate.postForObject(url, entity, String.class);
		return result;
	}
}
