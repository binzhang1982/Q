package com.cn.zbin.ribbonserver.service;

import com.cn.zbin.ribbonserver.bto.wechat.WeChatMessage;
import com.cn.zbin.ribbonserver.utils.RibbonKeyConstants;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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
    	Map<String, Object> uriVariables = new HashMap<String, Object>();
    	uriVariables.put("signature", signature);
    	uriVariables.put("timestamp", timestamp);
    	uriVariables.put("nonce", nonce);
    	String url = "http://SERVICE-WECHAT/recv/event?signature={signature}"
        			+ "&timestamp={timestamp}&nonce={nonce}";
    	HttpEntity<WeChatMessage> request = new HttpEntity<>(msg);
        return restTemplate.postForObject(url, request, String.class, uriVariables);
    }
    public String postEventError(String signature, String timestamp, String nonce, 
    		WeChatMessage msg) {
        return "failed";
    }
    
    @HystrixCommand(fallbackMethod = "getHiCheckError")
    public Boolean getHiCheckService(String signature, String timestamp, String nonce) {
    	Map<String, Object> uriVariables = new HashMap<String, Object>();
    	uriVariables.put("signature", signature);
    	uriVariables.put("timestamp", timestamp);
    	uriVariables.put("nonce", nonce);
    	String url = "http://SERVICE-WECHAT/recv/hicheck?signature={signature}"
    				+ "&timestamp={timestamp}&nonce={nonce}"; 
        return restTemplate.getForObject(url, Boolean.class, uriVariables);
    }
    public Boolean getHiCheckError(String signature, String timestamp, String nonce) {
        return Boolean.FALSE;
    }
    
    @HystrixCommand(fallbackMethod = "createPartnerError")
    public String createPartner(String scenestr) {
        String ret = "";
		try {
	    	Map<String, Object> uriVariables = new HashMap<String, Object>();
	    	uriVariables.put("atk", URLEncoder.encode(RibbonKeyConstants.APPTOKEN, "UTF-8"));
	    	uriVariables.put("scenestr", scenestr);
	    	String url = "http://SERVICE-WECHAT/qr/qr_limit_scene?atk={atk}" 
						+ "&scenestr={scenestr}";
			ret = restTemplate.getForObject(url, String.class, uriVariables);
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
	    	Map<String, Object> uriVariables = new HashMap<String, Object>();
	    	uriVariables.put("atk", URLEncoder.encode(RibbonKeyConstants.APPTOKEN, "UTF-8"));
	    	uriVariables.put("openid", openid);
	    	String url = "http://SERVICE-WECHAT/user/update?atk={atk}"
	    				+ "&openid={openid}";
    		ret = restTemplate.postForObject(url, null, String.class, uriVariables);
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
	    	Map<String, Object> uriVariables = new HashMap<String, Object>();
	    	uriVariables.put("atk", URLEncoder.encode(RibbonKeyConstants.APPTOKEN, "UTF-8"));
	    	uriVariables.put("openid", openid);
	    	String url = "http://SERVICE-WECHAT/user/one/wx?atk={atk}"
					+ "&openid={openid}";
    		ret = restTemplate.getForObject(url, String.class, uriVariables);
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
    	Map<String, Object> uriVariables = new HashMap<String, Object>();
    	uriVariables.put("openid", openid);
    	String url = "http://SERVICE-WECHAT/user/one/db?openid={openid}";
		return restTemplate.getForObject(url, String.class, uriVariables);
    }
    public String oneUserDBError(String openid) {
        return "failed";
    }

    @HystrixCommand(fallbackMethod = "getMenuError")
    public String getMenu() {
    	String ret = "";
		try {
	    	Map<String, Object> uriVariables = new HashMap<String, Object>();
	    	uriVariables.put("atk", URLEncoder.encode(RibbonKeyConstants.APPTOKEN, "UTF-8"));
	    	String url = "http://SERVICE-WECHAT/menu?atk={atk}";
			ret = restTemplate.getForObject(url, String.class, uriVariables);
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
    	Map<String, Object> uriVariables = new HashMap<String, Object>();
    	uriVariables.put("appid", RibbonKeyConstants.APPID);
    	uriVariables.put("secret", RibbonKeyConstants.APPSECRET);
    	uriVariables.put("code", code);
    	
    	RestTemplate rest = new RestTemplate();
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token"
        		 	+ "?appid={appid}"
        			+ "&secret={secret}"
        			+ "&code={code}"
        			+ "&grant_type=authorization_code";
        return rest.getForObject(url, String.class, uriVariables);
    }
    public String getOpenIdByCodeError(String code) {
    	return "failed";
    }

    @HystrixCommand(fallbackMethod = "authWechatUserError")
    public String authWechatUser(String bean) {
    	String ret = "";
		try {
	    	Map<String, Object> uriVariables = new HashMap<String, Object>();
	    	uriVariables.put("atk", URLEncoder.encode(RibbonKeyConstants.APPTOKEN, "UTF-8"));
	    	
			String url = "http://SERVICE-WECHAT/user/oauthatk?atk={atk}";
	        HttpHeaders headers =new HttpHeaders();
	        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
	        headers.setContentType(type);
	        HttpEntity<String> request = new HttpEntity<String>(bean, headers);
    		ret = restTemplate.postForObject(url, request, String.class, uriVariables);
		} catch (RestClientException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return ret;
    }
    public String authWechatUserError(String bean) {
    	return "failed";
    }
}
