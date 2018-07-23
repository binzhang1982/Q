package com.cn.zbin.ribbonserver.service;

import com.cn.zbin.ribbonserver.bto.wechat.WeChatMessage;
import com.cn.zbin.ribbonserver.utils.RibbonConstants;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class WeChatService {
    @Autowired
    RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "postEventError")
    public String postEventService(String signature, String timestamp, String nonce, 
    		WeChatMessage msg) {
    	HttpEntity<WeChatMessage> request = new HttpEntity<>(msg);
        return restTemplate.postForObject("http://SERVICE-WECHAT/recv/event?signature=" + signature + 
        		"&timestamp=" + timestamp+"&nonce=" + nonce, request, String.class);
    }
    public String postEventError(String signature, String timestamp, String nonce, 
    		WeChatMessage msg) {
        return "failed";
    }
    
    @HystrixCommand(fallbackMethod = "getHiCheckError")
    public Boolean getHiCheckService(String signature, String timestamp, String nonce) {
        return restTemplate.getForObject("http://SERVICE-WECHAT/recv/hicheck?signature=" + signature +
        			"&timestamp=" + timestamp+"&nonce=" + nonce, Boolean.class);
    }
    public Boolean getHiCheckError(String signature, String timestamp, String nonce) {
        return Boolean.FALSE;
    }
    
    @HystrixCommand(fallbackMethod = "createPartnerError")
    public String createPartner(String scenestr) {
        String ret = "";
		try {
			ret = restTemplate.getForObject("http://SERVICE-WECHAT/qr/qr_limit_scene?atk=" 
					+ URLEncoder.encode(RibbonConstants.APPTOKEN, "UTF-8")
					+ "&scenestr=" + scenestr, String.class);
		} catch (RestClientException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return ret;
    }
    public String createPartnerError(String scenestr) {
        return "failed";
    }

    @HystrixCommand(fallbackMethod = "updateUserError")
    public String updateUser(String openid) {
    	String ret = "";
		try {
    		ret = restTemplate.postForObject("http://SERVICE-WECHAT/user/update?atk=" 
					+ URLEncoder.encode(RibbonConstants.APPTOKEN, "UTF-8")
					+ "&openid=" + openid, null, String.class);
		} catch (RestClientException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return ret;
    }
    public String updateUserError(String openid) {
        return "failed";
    }
    
    @HystrixCommand(fallbackMethod = "oneUserWXError")
    public String oneUserWX(String openid) {
    	String ret = "";
		try {
    		ret = restTemplate.getForObject("http://SERVICE-WECHAT/user/one/wx?atk=" 
					+ URLEncoder.encode(RibbonConstants.APPTOKEN, "UTF-8")
					+ "&openid=" + openid, String.class);
		} catch (RestClientException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return ret;
    }
    public String oneUserWXError(String openid) {
        return "failed";
    }

    @HystrixCommand(fallbackMethod = "oneUserDBError")
    public String oneUserDB(String openid) {
		return restTemplate.getForObject("http://SERVICE-WECHAT/user/one/db?openid="
    				+ openid, String.class);
    }
    public String oneUserDBError(String openid) {
        return "failed";
    }

    @HystrixCommand(fallbackMethod = "getMenuError")
    public String getMenu() {
    	String ret = "";
		try {
			ret = restTemplate.getForObject("http://SERVICE-WECHAT/menu?atk="
					+ URLEncoder.encode(RibbonConstants.APPTOKEN, "UTF-8")
					, String.class);
		} catch (RestClientException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
    }
    public String getMenuError() {
    	return "failed";
    }

    @HystrixCommand(fallbackMethod = "getOpenIdByCodeError")
    public String getOpenIdByCode(String code) {
    	RestTemplate rest = new RestTemplate();
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token" + 
        				"?appid=" + RibbonConstants.APPID + 
        				"&secret=" + RibbonConstants.APPSECRET +
        				"&code=" + code +
        				"&grant_type=authorization_code";
        return rest.getForObject(url, String.class);
    }
    public String getOpenIdByCodeError(String code) {
    	return "failed";
    }
}
