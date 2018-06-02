package com.cn.zbin.ribbonserver.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cn.zbin.ribbonserver.bto.wechat.WeChatMessage;
import com.cn.zbin.ribbonserver.service.WeChatService;

@RestController
@RequestMapping("wechat")
public class WechatController {
    @Autowired
    WeChatService wechatService;
    
	@GetMapping(value = "/hi")
	public String getHi(@RequestParam("signature") String signature, @RequestParam("timestamp") String timestamp,
			@RequestParam("nonce") String nonce, @RequestParam("echostr") String echostr) {
		// 如果检验成功原样返回echostr，微信服务器接收到此输出，才会确认检验完成。
		if (wechatService.getHiCheckService(signature, timestamp, nonce)) {
	        return echostr;
		} else {
			return "failed";
		}
	}
	
	@PostMapping(value = "/hi")
	@ResponseBody
	public String postHi(@RequestParam("signature") String signature, @RequestParam("timestamp") String timestamp,
			@RequestParam("nonce") String nonce, @RequestBody WeChatMessage msg) {
		return wechatService.postEventService(signature, timestamp, nonce, msg);
	}
	
	@GetMapping(value = "/create_partner")
	public String createPartner(@RequestParam("scenestr") String scenestr) {
		String ret = wechatService.createPartner(scenestr);
		return ret;
	}
	
	@PostMapping(value = "/update_user")
	public String updateUser(@RequestParam("openid") String openid) {
		String ret = wechatService.updateUser(openid);
		return ret;
	}
	
	@GetMapping(value = "/user_db")
	public String getOneUserFromDB(@RequestParam("openid") String openid) {
		String ret = wechatService.oneUserDB(openid);
		return ret;
	}
	
	@GetMapping(value = "/user_wx")
	public String getOneUserFromWX(@RequestParam("openid") String openid) {
		String ret = wechatService.oneUserWX(openid);
		return ret;
	}
}
