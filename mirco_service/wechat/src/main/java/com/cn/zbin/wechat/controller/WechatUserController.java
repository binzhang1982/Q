package com.cn.zbin.wechat.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.cn.zbin.wechat.bto.MsgData;
import com.cn.zbin.wechat.bto.OAuthUserBaseInfo;
import com.cn.zbin.wechat.bto.OauthAccessToken;
import com.cn.zbin.wechat.bto.SendMsgReturn;
import com.cn.zbin.wechat.bto.WeChatUserBaseInfo;
import com.cn.zbin.wechat.dto.WeChatMessageHistory;
import com.cn.zbin.wechat.exception.BusinessException;
import com.cn.zbin.wechat.service.WechatUserService;
import com.cn.zbin.wechat.utils.Utils;
import com.cn.zbin.wechat.utils.WechatConstants;
import com.cn.zbin.wechat.utils.WechatKeyConstants;
import com.google.gson.Gson;

@RestController
@RequestMapping("user")
public class WechatUserController {
	protected static final Logger logger = LoggerFactory.getLogger(WechatUserController.class);
    @Autowired
    private RestTemplate restTemplate; 
    @Autowired
    private WechatUserService wechatUserService;
    
	@RequestMapping(value = "/update", 
			method = { RequestMethod.POST })
	public MsgData addWechatUser(@RequestParam("atk") String atk,
			@RequestParam("openid") String openid) throws UnsupportedEncodingException {
		logger.info("post api: /user/update || atk: " + atk
				+ " || openid: " + openid);
		MsgData ret = new MsgData();
		try {
	    	Map<String, Object> uriVariables = new HashMap<String, Object>();
	    	uriVariables.put("atk", URLEncoder.encode(atk, "UTF-8"));
	    	uriVariables.put("openid", openid);
	    	
			String url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token={atk}"
					+ "&openid={openid}&lang=zh_CN";
			WeChatUserBaseInfo userBaseInfo = restTemplate.getForObject(
					url, WeChatUserBaseInfo.class, uriVariables);
			wechatUserService.postUser(userBaseInfo);
		} catch (BusinessException be) {
			ret.setStatus(MsgData.status_ng);
			ret.setMessage(be.getMessage());
		} catch (Exception e) {
			ret.setStatus(MsgData.status_ng);
			ret.setMessage(WechatConstants.CHK_ERR_99999);
		}
		return ret;
	}
	
	@RequestMapping(value = "/one/wx",  
			produces = {"application/json;charset=UTF-8"}, 
			method = { RequestMethod.GET })
	public WeChatUserBaseInfo getOneFromWX(@RequestParam("atk") String atk,
			@RequestParam("openid") String openid) throws UnsupportedEncodingException {
		logger.info("get api: /user/one/wx || atk: " + atk
				+ " || openid: " + openid);

    	Map<String, Object> uriVariables = new HashMap<String, Object>();
    	uriVariables.put("atk", URLEncoder.encode(atk, "UTF-8"));
    	uriVariables.put("openid", openid);
    	
		String url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token={atk}"
				 + "&openid={openid}&lang=zh_CN";
		return restTemplate.getForObject(url, WeChatUserBaseInfo.class, uriVariables); 
	}

	@RequestMapping(value = "/one/db", 
			produces = {"application/json;charset=UTF-8"}, 
			method = { RequestMethod.GET })
	public WeChatUserBaseInfo getOneFromDB(@RequestParam("openid") String openid) 
			throws UnsupportedEncodingException {
		logger.info("get api: /user/one/db || openid: " + openid);
		return wechatUserService.getOneUser(openid);
	}
	
	@RequestMapping(value = "/oauthatk", 
			consumes = {"application/json;charset=UTF-8"}, 
			produces = {"application/json;charset=UTF-8"}, 
			method = { RequestMethod.POST})
	public MsgData authWechatUser(@RequestParam("atk") String atk,
			@RequestBody OauthAccessToken oatk) throws UnsupportedEncodingException {
		logger.info("post api: /user/oauthatk || atk: " + atk
				+ " || oatk: " + oatk.toString());
		MsgData ret = new MsgData();
		try {
	    	Map<String, Object> uriVariables = new HashMap<String, Object>();
	    	uriVariables.put("atk", URLEncoder.encode(oatk.getAccess_token(), "UTF-8"));
	    	uriVariables.put("openid", oatk.getOpenid());
	    	
			String url = "https://api.weixin.qq.com/sns/userinfo?access_token={atk}"
					 + "&openid={openid}&lang=zh_CN";
			String strUser = restTemplate.getForObject(url, String.class, uriVariables);
			Gson gson = new Gson();
			OAuthUserBaseInfo oauthUser = gson.fromJson(strUser, OAuthUserBaseInfo.class);
			WeChatUserBaseInfo userBaseInfo = new WeChatUserBaseInfo();
			userBaseInfo.setOpenid(oauthUser.getOpenid());
			userBaseInfo.setNickname(oauthUser.getNickname());
			userBaseInfo.setSex(oauthUser.getSex());
			userBaseInfo.setLanguage(oauthUser.getLanguage());
			userBaseInfo.setCity(oauthUser.getCity());
			userBaseInfo.setProvince(oauthUser.getProvince());
			userBaseInfo.setCountry(oauthUser.getCountry());;
			userBaseInfo.setHeadimgurl(oauthUser.getHeadimgurl());
			wechatUserService.postUser(userBaseInfo);
		} catch (BusinessException be) {
			ret.setStatus(MsgData.status_ng);
			ret.setMessage(be.getMessage());
		} catch (Exception e) {
			ret.setStatus(MsgData.status_ng);
			ret.setMessage(WechatConstants.CHK_ERR_99999);
		}
		return ret;
	}

	@RequestMapping(value = "/msg/notify", 
			method = { RequestMethod.GET })
	public MsgData sendTextMsg(
			@RequestParam("atk") String atk) {
		MsgData ret = new MsgData();
    	
		try {
	    	Map<String, Object> uriVariables = new HashMap<String, Object>();
	    	uriVariables.put("atk", URLEncoder.encode(atk, "UTF-8"));
			String url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token={atk}";
			
			List<WeChatMessageHistory> msgList = wechatUserService.getUnsendMessageList();
			for (WeChatMessageHistory msg : msgList) {
				try {
					HttpHeaders headers = new HttpHeaders();
					MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
					headers.setContentType(type);
					String requestJson = "{\"touser\": \"" + msg.getRecvOpenId() + "\", "
							+ "\"msgtype\": \""+ WechatKeyConstants.MSG_TYPE_TEXT + "\", "
							+ "\"text\": {\"content\": \"" + msg.getMessageContent() + "\"}}";
					HttpEntity<String> entity = new HttpEntity<String>(requestJson,headers);
					String result = restTemplate.postForObject(url, entity, String.class, uriVariables);
					msg.setReturnCode(result);
					msg.setSentTime(Utils.getChinaCurrentTime());
					Gson gson = new Gson();
					SendMsgReturn msgRes = gson.fromJson(result, SendMsgReturn.class);
					if ("0".equals(msgRes.getErrcode())) {
						msg.setSendFlag(Boolean.TRUE);
					}
					wechatUserService.updateMessage(msg);
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ret;
	}
}
