package com.cn.zbin.management.service;

import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.cn.zbin.management.bto.MessageHistoryMsgData;
import com.cn.zbin.management.bto.MsgData;
import com.cn.zbin.management.dto.CustomerInfo;
import com.cn.zbin.management.dto.CustomerInfoExample;
import com.cn.zbin.management.dto.MessageHistory;
import com.cn.zbin.management.dto.MessageHistoryExample;
import com.cn.zbin.management.mapper.CustomerInfoMapper;
import com.cn.zbin.management.mapper.MessageHistoryMapper;
import com.cn.zbin.management.utils.MgmtConstants;
import com.cn.zbin.management.utils.MgmtKeyConstants;

@Service
public class SmsService {
	@Autowired
	private CustomerInfoMapper customerInfoMapper;
	@Autowired
	private MessageHistoryMapper messageHistoryMapper;

	@Transactional
	public MessageHistoryMsgData addMessageHistory(String customerid, String phonenumber) {
        Date d_now = new Date();
        Date d_yesterday = DateUtils.addDays(d_now, -1);
		MessageHistoryMsgData smsMsgData = new MessageHistoryMsgData();
		CustomerInfo cust = customerInfoMapper.selectByPrimaryKey(customerid);
		if (cust != null) {
			CustomerInfoExample exam_ci = new CustomerInfoExample();
			exam_ci.createCriteria().andTelephoneEqualTo(phonenumber)
									.andCustomerIdNotEqualTo(customerid)
									.andValidFlagEqualTo(Boolean.TRUE);
			if (customerInfoMapper.countByExample(exam_ci)>0) {
				smsMsgData.setStatus(MsgData.status_ng);
				smsMsgData.setMessage(MgmtConstants.CHK_ERR_80007);
				return smsMsgData;
			}
			MessageHistoryExample exam_mh = new MessageHistoryExample();
			exam_mh.createCriteria().andCreateTimeBetween(d_yesterday, d_now)
									.andPhoneNumberEqualTo(phonenumber);
			if (messageHistoryMapper.countByExample(exam_mh) >= MgmtConstants.SMS_MAX) {
				smsMsgData.setStatus(MsgData.status_ng);
				smsMsgData.setMessage(MgmtConstants.CHK_ERR_80006);
				return smsMsgData;
			}

			CustomerInfo record = new CustomerInfo();
			record.setCustomerId(customerid);
			record.setTelephone(phonenumber);
			Integer type = MgmtConstants.PHONENUM_ADD_TYPE;
			if (cust.getValidFlag()) {
				type = MgmtConstants.PHONENUM_UPD_TYPE;
				record.setValidFlag(Boolean.FALSE);
			}
			record.setValidCode(String.valueOf((int)((Math.random()*9+1)*1000)));
			customerInfoMapper.updateByPrimaryKeySelective(record);

			MessageHistory sms = new MessageHistory();
			sms.setMessageId(UUID.randomUUID().toString());
			sms.setSignName(MgmtKeyConstants.SMS_SIGN_NAME);
			sms.setPhoneNumber(phonenumber);
			if (MgmtConstants.PHONENUM_ADD_TYPE.equals(type)) {
				sms.setTemplateCode(MgmtKeyConstants.SMS_NEWINFO_TEMPLATE_ID);
				sms.setTemplateParams("{\"code\":\"" + record.getValidCode() + "\"}");
			} else if (MgmtConstants.PHONENUM_UPD_TYPE.equals(type)) {
				sms.setTemplateCode(MgmtKeyConstants.SMS_UPDINFO_TEMPLATE_ID);
				sms.setTemplateParams("{\"code\":\"" + record.getValidCode() + "\"}");
			}
			messageHistoryMapper.insert(sms);
			
			smsMsgData.setSms(sms);
			return smsMsgData;
		} else {
			smsMsgData.setStatus(MsgData.status_ng);
			smsMsgData.setMessage(MgmtConstants.CHK_ERR_80005);
			return smsMsgData;
		}
	}
	
	@Async
	@Transactional
	public void sendSms(String customerid, MessageHistory sms) {
        if (sms != null) {
            sms.setReturnCode(sendShortMessage(sms));
            updateMessageHistory(customerid, sms);
        }
	}

	private void updateMessageHistory(String customerid, MessageHistory sms) {
		MessageHistory record = new MessageHistory();
		record.setMessageId(sms.getMessageId());
		record.setReturnCode(sms.getReturnCode());
		if ("OK".equals(record.getReturnCode()))
			record.setSentTime(new Date());
		messageHistoryMapper.updateByPrimaryKeySelective(record);
	}

	private String sendShortMessage(MessageHistory sms) {
		String msg = "发送中...";
		try {
		    //设置超时时间-可自行调整
		    System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
		    System.setProperty("sun.net.client.defaultReadTimeout", "10000");
		    //初始化ascClient需要的几个参数
		    final String product = "Dysmsapi";//短信API产品名称（短信产品名固定，无需修改）
		    final String domain = "dysmsapi.aliyuncs.com";//短信API产品域名（接口地址固定，无需修改）
		    //替换成自己的AK
		    final String accessKeyId = MgmtKeyConstants.SMS_ACCESS_KEY_ID;//你的accessKeyId(需要修改)
		    final String accessKeySecret = MgmtKeyConstants.SMS_ACCESS_KEY_SECRET;//你的accessKeySecret(需要修改)
		    //初始化ascClient,暂时阿里云不支持多region（就不要动了）
		    IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
			DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
	
		    IAcsClient acsClient = new DefaultAcsClient(profile);
		    //组装请求对象
		    SendSmsRequest request = new SendSmsRequest();
		    //使用post提交
		    request.setMethod(MethodType.POST);
		    //必填:待发送手机号。发送国际/港澳台消息时，接收号码格式为00+国际区号+号码，如“0085200000000”。
		    request.setPhoneNumbers(sms.getPhoneNumber());
		    //必填:短信签名-可在短信控制台中找到
		    request.setSignName(sms.getSignName()); //这个会是短信的那个提示，例如[吃货店铺]
		    //必填:短信模板-这个可以在短信控制台中找到，自己设定短信模版后，阿里云会分配给你一个模版码值
		    request.setTemplateCode(sms.getTemplateCode());
		    //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
		    //友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
		    request.setTemplateParam(sms.getTemplateParams()); //我这里只需要一个，因此，就只写了这个，而且写成了固定的
		    //可选-上行短信扩展码(扩展码字段控制在7位或以下，无特殊需求用户请忽略此字段)
	//	    request.setSmsUpExtendCode("90997");
		    //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者	     
		    request.setOutId("");  //你的outId
	 	    //请求失败这里会抛ClientException异常
		    SendSmsResponse sendSmsResponse;
		    sendSmsResponse = acsClient.getAcsResponse(request);
		    msg = sendSmsResponse.getCode();
		} catch (ClientException e) {
			// TODO Auto-generated catch block
			msg = "发送失败!";
		}
		return msg;
	}
}
