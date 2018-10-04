package com.cn.zbin.wechat.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.cn.zbin.wechat.bto.MsgData;
import com.cn.zbin.wechat.bto.QrScene;
import com.cn.zbin.wechat.dto.PromotionPartnerInfo;
import com.cn.zbin.wechat.exception.BusinessException;
import com.cn.zbin.wechat.service.WechatUserService;
import com.cn.zbin.wechat.utils.WechatConstants;

@RestController
@RequestMapping("qr")
public class WechatQRSceneController {
	protected static final Logger logger = LoggerFactory.getLogger(WechatQRSceneController.class);
    @Autowired
    private WechatUserService wechatUserService;
    @Autowired
    private RestTemplate restTemplate; 
	
	@RequestMapping(value = "/qr_limit_scene", 
			method = { RequestMethod.GET })
	public String getQRLimitScene(@RequestParam("atk") String atk, 
			@RequestParam("scenestr") String scenestr) throws UnsupportedEncodingException {
		logger.info("get api: /qr/qr_limit_scene || atk: " + atk
				+ " || scenestr: " + scenestr);

    	Map<String, Object> uriVariables = new HashMap<String, Object>();
    	uriVariables.put("atk", URLEncoder.encode(atk, "UTF-8"));
    	
		String url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token={atk}";
		
		HttpHeaders headers = new HttpHeaders();
		MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
		headers.setContentType(type);
		String requestJson = "{\"action_name\": \"QR_LIMIT_STR_SCENE\", \"action_info\": {\"scene\": {\"scene_str\": \"" + scenestr + "\"}}}";
		HttpEntity<String> entity = new HttpEntity<String>(requestJson,headers);
		String result = restTemplate.postForObject(url, entity, String.class, uriVariables);
		return result;
	}
	
	@RequestMapping(value = "/partner/create", 
			consumes = {"application/json;charset=UTF-8"}, 
			produces = {"application/json;charset=UTF-8"}, 
			method = { RequestMethod.POST})
	public MsgData createPartner(@RequestParam("atk") String atk,
			@RequestBody PromotionPartnerInfo partner) throws UnsupportedEncodingException {
		logger.info("post api: /qr/partner/create || atk: " + atk
				+ " || partner: " + partner.toString());
		MsgData ret = new MsgData();
		try {
			if (wechatUserService.checkQrSceneStr(partner) > 0)
				throw new BusinessException(StringUtils.replace(WechatConstants.CHK_ERR_70001, 
						"{1}", partner.getSceneStr()));
			
	    	Map<String, Object> uriVariables = new HashMap<String, Object>();
	    	uriVariables.put("atk", URLEncoder.encode(atk, "UTF-8"));
	    	
			String url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token={atk}";
			HttpHeaders headers = new HttpHeaders();
			MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
			headers.setContentType(type);
			String requestJson = "{\"action_name\": \"QR_LIMIT_STR_SCENE\", \"action_info\": "
					+ "{\"scene\": {\"scene_str\": \"" + partner.getSceneStr() + "\"}}}";
			HttpEntity<String> entity = new HttpEntity<String>(requestJson,headers);
			QrScene res = restTemplate.postForObject(url, entity, QrScene.class, uriVariables);
			if (res != null) {
				if (res.getTicket() == null) 
					throw new BusinessException(WechatConstants.CHK_ERR_70002);
				partner.setTicket(res.getTicket());
				partner.setUrl("https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=" + res.getTicket());
				wechatUserService.createPartner(partner);
			} else {
				throw new BusinessException(WechatConstants.CHK_ERR_70002);
			}
		} catch (BusinessException be) {
			ret.setStatus(MsgData.status_ng);
			ret.setMessage(be.getMessage());
		} catch (Exception e) {
			ret.setStatus(MsgData.status_ng);
			ret.setMessage(WechatConstants.CHK_ERR_99999);
		}
		return ret;
	}
}
 