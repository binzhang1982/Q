package com.cn.zbin.ribbonserver.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.cn.zbin.ribbonserver.utils.RibbonKeyConstants;

@Service
public class AsyncService {
	@Autowired
	RestTemplate restTemplate;
	
	@Async("asyncServiceExecutor")
    public void executeWechatUserAsync(String openid) {
		try {
	    	Map<String, Object> uriVariablesWechat = new HashMap<String, Object>();
	    	uriVariablesWechat.put("atk", URLEncoder.encode(RibbonKeyConstants.APPTOKEN, "UTF-8"));
	    	uriVariablesWechat.put("openid", openid);
	    	String urlWechat = "http://SERVICE-WECHAT/user/update?atk={atk}"
	    				+ "&openid={openid}";
    		restTemplate.postForObject(urlWechat, null, String.class, uriVariablesWechat);
		} catch (RestClientException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	@Async("asyncServiceExecutor")
	public void executeMgmtCustAsync(String openid) {
		try {
	    	Map<String, Object> uriVariablesMgmt = new HashMap<String, Object>();
	    	uriVariablesMgmt.put("openid", openid);
	    	String urlMgmt = "http://SERVICE-MGMT/customer?openid={openid}&regtype=1";
	        restTemplate.postForObject(urlMgmt, null, String.class, uriVariablesMgmt);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
	}
}
