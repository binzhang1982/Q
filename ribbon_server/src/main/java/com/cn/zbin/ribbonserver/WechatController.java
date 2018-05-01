package com.cn.zbin.ribbonserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cn.zbin.ribbonserver.dto.WeChatMessage;
import com.cn.zbin.ribbonserver.service.WeChatService;

@RestController
public class WechatController {
    @Autowired
    WeChatService helloService;
    
	@GetMapping(value = "/hi")
	public String getHi(@RequestParam("signature") String signature, @RequestParam("timestamp") String timestamp,
			@RequestParam("nonce") String nonce, @RequestParam("echostr") String echostr) {
		// 如果检验成功原样返回echostr，微信服务器接收到此输出，才会确认检验完成。
		if (helloService.getHiCheckService(signature, timestamp, nonce)) {
	        return echostr;
		} else {
			return "failed";
		}
	}
	
	@PostMapping(value = "/hi")
	@ResponseBody
	public String postHi(@RequestParam("signature") String signature, @RequestParam("timestamp") String timestamp,
			@RequestParam("nonce") String nonce, @RequestBody WeChatMessage msg) {
		System.out.println("hi");
		return helloService.postEventService(signature, timestamp, nonce, msg);
	}
}
