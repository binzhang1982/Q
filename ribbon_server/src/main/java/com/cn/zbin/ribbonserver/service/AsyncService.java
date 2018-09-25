package com.cn.zbin.ribbonserver.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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

	@Async("asyncServiceExecutor")
	public void executeCancelUnpaidOrderAsync() {
		try {
	    	String urlOrder = "http://SERVICE-STORE/order/cancel/sys";
	        restTemplate.postForObject(urlOrder, null, String.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
	}
	
	@Async("asyncServiceExecutor")
	public void executeCommentDefaultOrderAsync() {
		try {
	    	String urlOrder = "http://SERVICE-STORE/order/comment/sys";
	        restTemplate.postForObject(urlOrder, null, String.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
	}
	
	@Async("asyncServiceExecutor")
	public void executDueLeaseOrderAsync() {
		try {
	    	String urlOrder = "http://SERVICE-STORE/order/lease/due/sys";
	        restTemplate.postForObject(urlOrder, null, String.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
	}

	@Async("asyncServiceExecutor")
	public void executDueNotifyAsync() {
		try {
	    	String urlMgmt = "http://SERVICE-MGMT/customer/due/notify/sys";
	        restTemplate.getForObject(urlMgmt, String.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
	}
	
	@Async("asyncServiceExecutor")
	public void executUnifiedRefundAsync() {
		try {
	    	String urlStore = "http://SERVICE-STORE/order/wxrefund/unified/sys?appid={appid}";
	    	Map<String, Object> uriVariables = new HashMap<String, Object>();
	    	uriVariables.put("appid", RibbonKeyConstants.APPID);
	        restTemplate.postForObject(urlStore, null, String.class, uriVariables);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
	}
	
	@Async("asyncServiceExecutor")
	public void executRefundQueryAsync() {
		try {
	    	String urlStore = "http://SERVICE-STORE/order/wxrefund/query/sys?appid={appid}";
	    	Map<String, Object> uriVariables = new HashMap<String, Object>();
	    	uriVariables.put("appid", RibbonKeyConstants.APPID);
	        restTemplate.postForObject(urlStore, null, String.class, uriVariables);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
	}

	@Async("asyncSingtonServiceExecutor")
	public void executeScanPayOrderAsync() {
		try {
	    	Map<String, Object> uriVariables = new HashMap<String, Object>();
	    	uriVariables.put("interval", 15);
	    	uriVariables.put("expiredhour", 1);
	    	uriVariables.put("appid", RibbonKeyConstants.APPID);
	    	
	    	String urlOrder = "http://SERVICE-STORE/order/pay/scan?interval={interval}" +
	    			"&expiredhour={expiredhour}&appid={appid}";
	        restTemplate.getForObject(urlOrder, String.class, uriVariables);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
	}

	@Async("asyncPayNotifyExecutor")
    public void executeNotifyOrderPay(String bean) {
		try {
	    	String url = "http://SERVICE-STORE/order/wxpay/notify";
	    	
	        HttpHeaders headers =new HttpHeaders();
	        MediaType mtype = MediaType.parseMediaType("text/html; charset=utf-8");
	        headers.setContentType(mtype);
	        HttpEntity<String> request = new HttpEntity<String>(bean, headers);
	    	restTemplate.postForObject(url, request, String.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
    }
}
