package com.cn.zbin.wechat.controller;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.cn.zbin.wechat.bto.WeChatMessage;
import com.cn.zbin.wechat.service.WechatPostSecurityService;
import com.cn.zbin.wechat.utils.WechatKeyConstants;

@RestController
@RequestMapping("recv")
public class WechatReciverController {
	protected static final Logger logger = LoggerFactory.getLogger(WechatReciverController.class);
    @Autowired
    RestTemplate restTemplate;
	@Autowired
	private WechatPostSecurityService securityService;
	
	@RequestMapping(value = "/hicheck", 
			method = { RequestMethod.GET })
	public Boolean getHi(@RequestParam("signature") String signature, 
			@RequestParam("timestamp") String timestamp,
			@RequestParam("nonce") String nonce) {
		logger.info("get api: /recv/hicheck || signature: " + signature
				+ " || timestamp: " + timestamp + " || nonce: " + nonce);
		return securityService.checkSignature(signature, timestamp, nonce);
	}

	@RequestMapping(value = "/event", 
			method = { RequestMethod.POST })
	@ResponseBody
	public String subscribe(@RequestParam("signature") String signature, @RequestParam("timestamp") String timestamp,
			@RequestParam("nonce") String nonce,@RequestBody WeChatMessage msg) {
		logger.info("post api: /recv/event || signature: " + signature
				+ " || timestamp: " + timestamp + " || nonce: " + nonce
				+ " || msg: " + msg.toTextString());
		
		if (WechatKeyConstants.MSG_TYPE_EVENT.equals(msg.getMsgType())) {
//			if (WechatKeyConstants.EVENT_CLICK.equals(msg.getEvent())) {
//				if (WechatKeyConstants.EVENT_CLICK_KEY_CUSTOMER_SERVICE.equals(msg.getEventKey())) {
//					WeChatMessage cust = new WeChatMessage();
//					cust.setToUserName(msg.getFromUserName());
//					cust.setFromUserName(msg.getToUserName());
//					cust.setCreateTime(msg.getCreateTime());
//					cust.setMsgType(WechatKeyConstants.MSG_TYPE_TEXT);
//					cust.setContent(WechatKeyConstants.EVENT_CLICK_MSGTXT_CUSTOMER_SERVICE);
//					return cust.toTextString();
//				}
//			}
			if (WechatKeyConstants.EVENT_SUBSCRIBE.equals(msg.getEvent())) {
				createUser(msg.getFromUserName());
				WeChatMessage cust = new WeChatMessage();
				cust.setToUserName(msg.getFromUserName());
				cust.setFromUserName(msg.getToUserName());
				cust.setCreateTime(msg.getCreateTime());
				cust.setMsgType(WechatKeyConstants.MSG_TYPE_TEXT);
				cust.setContent(WechatKeyConstants.REPLY_MSGTXT_SUBSCRIBE);
				return cust.toTextString();
			}
		}

//		if (WechatKeyConstants.MSG_TYPE_TEXT.equals(msg.getMsgType())) {
//			if (StringUtils.upperCase(msg.getContent()).contains(WechatKeyConstants.MSG_TEXT_CONTENT_RLP)) {
//				WeChatMessage cust = new WeChatMessage();
//				cust.setToUserName(msg.getFromUserName());
//				cust.setFromUserName(msg.getToUserName());
//				cust.setCreateTime(msg.getCreateTime());
//				cust.setMsgType(WechatKeyConstants.MSG_TYPE_NEWS);
//				cust.setArticleCount(1);
//				cust.setArticles(WechatKeyConstants.REPLY_MEDIAID_SENDMSG_RLP);
//				return cust.toTextString();
//			} else {
//				WeChatMessage cust = new WeChatMessage();
//				cust.setToUserName(msg.getFromUserName());
//				cust.setFromUserName(msg.getToUserName());
//				cust.setCreateTime(msg.getCreateTime());
//				cust.setMsgType(WechatKeyConstants.MSG_TYPE_TEXT);
//				cust.setContent(WechatKeyConstants.REPLY_MSGTXT_SENDMSG_EXCEPT_RLP);
//				return cust.toTextString();
//			}
//		}
		return null;
	}
	
	public void createUser(String openid) {
    	Map<String, Object> uriVariables = new HashMap<String, Object>();
    	uriVariables.put("openid", openid);
		restTemplate.postForObject("http://localhost/wechat/user?openid={openid}", 
				null, String.class, uriVariables);
		restTemplate.postForObject("http://localhost/mgmt//cust/regtype?openid={openid}&regtype=1",
				null, String.class, uriVariables);
	}
}
