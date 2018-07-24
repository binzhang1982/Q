package com.cn.zbin.wechat.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.cn.zbin.wechat.bto.OauthAccessToken;
import com.cn.zbin.wechat.bto.WeChatUserBaseInfo;
import com.cn.zbin.wechat.service.WechatUserService;

@RestController
@RequestMapping("user")
public class WechatUserController {
    @Autowired
    private RestTemplate restTemplate; 
    @Autowired
    private WechatUserService wechatUserService;
    
	@RequestMapping(value = "/update", method = { RequestMethod.POST })
	public String addWechatUser(@RequestParam("atk") String atk,
			@RequestParam("openid") String openid) throws UnsupportedEncodingException {
		String ret = "";
		String url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token="
				 + URLEncoder.encode(atk, "UTF-8")
				 + "&openid=" + openid + "&lang=zh_CN";
		WeChatUserBaseInfo userBaseInfo = restTemplate.getForObject(url, WeChatUserBaseInfo.class);
		wechatUserService.postUser(userBaseInfo);
		return ret;
	}
	
	@RequestMapping(value = "/one/wx",  produces = {"application/json;charset=UTF-8"}, method = { RequestMethod.GET })
	public WeChatUserBaseInfo getOneFromWX(@RequestParam("atk") String atk,
			@RequestParam("openid") String openid) throws UnsupportedEncodingException {
		String url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token="
				 + URLEncoder.encode(atk, "UTF-8")
				 + "&openid=" + openid + "&lang=zh_CN";
		return restTemplate.getForObject(url, WeChatUserBaseInfo.class); 
	}

	@RequestMapping(value = "/one/db",  produces = {"application/json;charset=UTF-8"}, method = { RequestMethod.GET })
	public WeChatUserBaseInfo getOneFromDB(@RequestParam("openid") String openid) 
			throws UnsupportedEncodingException {
		return wechatUserService.getOneUser(openid);
	}
	
	@RequestMapping(value = "/oauthatk", 
			consumes = {"application/json;charset=UTF-8"}, 
			produces = {"application/json;charset=UTF-8"}, 
			method = { RequestMethod.POST})
	public String authWechatUser(@RequestParam("atk") String atk,
			@RequestBody OauthAccessToken oatk) throws UnsupportedEncodingException {
		String ret = "";
//		https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN
		String url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token="
//				 + URLEncoder.encode(atk, "UTF-8")
				 + URLEncoder.encode(oatk.getAccess_token(), "UTF-8")
				 + "&openid=" + oatk.getOpenid() + "&lang=zh_CN";
		WeChatUserBaseInfo userBaseInfo = restTemplate.getForObject(url, WeChatUserBaseInfo.class);
		wechatUserService.postUser(userBaseInfo);
		return ret;
	}
}
