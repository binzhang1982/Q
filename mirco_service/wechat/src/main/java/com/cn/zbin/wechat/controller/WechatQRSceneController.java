package com.cn.zbin.wechat.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.cn.zbin.wechat.bto.QrScene;
import com.cn.zbin.wechat.dto.PromotionPartnerInfo;
import com.cn.zbin.wechat.service.WechatUserService;

@RestController
@RequestMapping("qr")
public class WechatQRSceneController {
    @Autowired
    private WechatUserService wechatUserService;
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
	
	@RequestMapping(value = "/partner", 
			consumes = {"application/json;charset=UTF-8"}, 
			produces = {"application/json;charset=UTF-8"}, 
			method = { RequestMethod.POST})
	public String createPartner(@RequestParam("atk") String atk,
			@RequestBody List<PromotionPartnerInfo> partnerList) throws UnsupportedEncodingException {
		String url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token="
				 + URLEncoder.encode(atk, "UTF-8");
		for (PromotionPartnerInfo partner : partnerList) {
			HttpHeaders headers = new HttpHeaders();
			MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
			headers.setContentType(type);
			String requestJson = "{\"action_name\": \"QR_LIMIT_STR_SCENE\", \"action_info\": {\"scene\": {\"scene_str\": \"" + partner.getSceneStr() + "\"}}}";
			HttpEntity<String> entity = new HttpEntity<String>(requestJson,headers);
			QrScene res = restTemplate.postForObject(url, entity, QrScene.class);
			if (res != null) {
				partner.setTicket(res.getTicket());
				partner.setUrl("https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=" + res.getTicket());
			}
			wechatUserService.createPartner(partner);
		}
		return "";
	}
}
 