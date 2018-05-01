package com.cn.zbin.wechat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cn.zbin.wechat.bto.WeChatMessage;
import com.cn.zbin.wechat.service.WechatPostSecurityService;
import com.cn.zbin.wechat.utils.WechatConstants;

@RestController
public class WechatReciverController {

	@Autowired
	private WechatPostSecurityService securityService;
	
	@RequestMapping(value = "/hicheck", method = { RequestMethod.GET })
	public Boolean getHi(@RequestParam("signature") String signature, @RequestParam("timestamp") String timestamp,
			@RequestParam("nonce") String nonce) {
		return securityService.checkSignature(signature, timestamp, nonce);
	}

	@RequestMapping(value = "/event", method = { RequestMethod.POST })
	@ResponseBody
	public String subscribe(@RequestParam("signature") String signature, @RequestParam("timestamp") String timestamp,
			@RequestParam("nonce") String nonce,@RequestBody WeChatMessage msg) {
		System.out.println("hi");
		if (WechatConstants.MSG_TYPE_EVENT.equals(msg.getMsgType())) {
			if (WechatConstants.EVENT_CLICK.equals(msg.getEvent())) {
				if (WechatConstants.EVENT_CLICK_KEY_CUSTOMER_SERVICE.equals(msg.getEventKey())) {
					WeChatMessage cust = new WeChatMessage();
					cust.setToUserName(msg.getFromUserName());
					cust.setFromUserName(msg.getToUserName());
					cust.setCreateTime(msg.getCreateTime());
					cust.setMsgType(WechatConstants.MSG_TYPE_TEXT);
					cust.setContent(WechatConstants.EVENT_CLICK_MSGTXT_CUSTOMER_SERVICE);
					System.out.println(cust.toTextString());
					return cust.toTextString();
				}
			}
		}
		return null;
	}
}
