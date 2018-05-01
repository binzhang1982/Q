package com.cn.zbin.wechat;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.cn.zbin.wechat.bto.AccessToken;
import com.cn.zbin.wechat.utils.WechatConstants;

@RestController
public class WechatSenderController {
    @Autowired
    private RestTemplate restTemplate; 

	@RequestMapping("/atk")
	public String getAccessToken() {
		AccessToken atk = this.restTemplate.getForObject(
				"https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + WechatConstants.APPID
				+"&secret=" + WechatConstants.APPSECRET, AccessToken.class);
        System.out.println(atk.getAccess_token()+ " " +String.valueOf(atk.getExpires_in()));  
        return atk.getAccess_token(); 
	}
	

	@RequestMapping("/qr_limit_scene")
	public String getQRLimitScene() throws UnsupportedEncodingException {
		 String url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token="
				 + URLEncoder.encode("8_v1e7N55D7i90I5jQan24oPxq_Zx7zCHOEDdjcJcawHKG0QhA5iWW8ITEfYqyZccjRNi0KOztF3CnBK5acrsX6PmK3uYtd1Db4XETz-mm3F_hNV1Qq7FJ5WG9vJD6nEqHGNJFwfR0zGFvY0i1ZKUiAIAMXH", "UTF-8");
		 HttpHeaders headers = new HttpHeaders();
		 MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
		 headers.setContentType(type);
		 String requestJson = "{\"action_name\": \"QR_LIMIT_STR_SCENE\", \"action_info\": {\"scene\": {\"scene_str\": \"zhangbin\"}}}";
		 HttpEntity<String> entity = new HttpEntity<String>(requestJson,headers);
		 String result = restTemplate.postForObject(url, entity, String.class);
		 return result;
	}


//	@RequestMapping("/qr_limit_scene")
}
