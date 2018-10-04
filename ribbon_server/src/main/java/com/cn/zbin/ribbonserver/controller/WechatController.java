package com.cn.zbin.ribbonserver.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cn.zbin.ribbonserver.bto.wechat.WeChatMessage;
import com.cn.zbin.ribbonserver.service.ManagementService;
import com.cn.zbin.ribbonserver.service.WeChatService;

@RestController
@RequestMapping("wechat")
@CrossOrigin
public class WechatController {
	protected static final Logger logger = LoggerFactory.getLogger(WechatController.class);
    @Autowired
    WeChatService wechatService;
    @Autowired
    ManagementService managementService;
    
	@GetMapping(value = "/hi")
	@CrossOrigin
	public String getHi(@RequestParam("signature") String signature, @RequestParam("timestamp") String timestamp,
			@RequestParam("nonce") String nonce, @RequestParam("echostr") String echostr) {
		// 如果检验成功原样返回echostr，微信服务器接收到此输出，才会确认检验完成。
//		System.out.println(echostr);
//		return echostr;
		logger.info("get api: /wechat/hi || signature: " + signature + " || timestamp: " + timestamp
				+ " || nonce: " + nonce + " || echostr: " + echostr);
		if (wechatService.getHiCheckService(signature, timestamp, nonce)) {
	        return echostr;
		} else {
			return "failed";
		}
	}
	
	@PostMapping(value = "/hi")
	@CrossOrigin
	@ResponseBody
	public String postHi(@RequestParam("signature") String signature, @RequestParam("timestamp") String timestamp,
			@RequestParam("nonce") String nonce, @RequestBody WeChatMessage msg) {
		logger.info("post api: /wechat/hi || signature: " + signature + " || timestamp: " + timestamp
				+ " || nonce: " + nonce + " || bean: " + msg.toString());
		return wechatService.postEventService(signature, timestamp, nonce, msg);
	}
	
	@PostMapping(value = "/user")
	@CrossOrigin
	public String updateUser(@RequestParam("openid") String openid) {
		logger.info("post api: /wechat/user || openid: " + openid);
		return wechatService.updateUser(openid);
	}
	
	@GetMapping(value = "/user_db")
	@CrossOrigin
	public String getOneUserFromDB(@RequestParam("openid") String openid) {
		logger.info("get api: /wechat/user_db || openid: " + openid);
		return wechatService.oneUserDB(openid);
	}
	
	@GetMapping(value = "/user_wx")
	@CrossOrigin
	public String getOneUserFromWX(@RequestParam("openid") String openid) {
		logger.info("get api: /wechat/user_wx || openid: " + openid);
		return wechatService.oneUserWX(openid);
	}
	
	@GetMapping(value = "/menu")
	@CrossOrigin
	public String getMenu() {
		logger.info("get api: /wechat/menu");
		return wechatService.getMenu();
	}

	@GetMapping(value = "/oauth2/{code}")
	@CrossOrigin
	public String getOpenIdByCode(@PathVariable("code") String code) {
		logger.info("get api: /wechat/oauth2/{code} || code: " + code);
		String ret = wechatService.getOpenIdByCode(code);
		if (ret.indexOf("errcode") < 0) {
			wechatService.authWechatUser(ret);
			managementService.updateToken(ret);
		}
		return ret;
	}
}
