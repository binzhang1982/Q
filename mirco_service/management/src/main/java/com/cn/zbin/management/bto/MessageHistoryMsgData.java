package com.cn.zbin.management.bto;

import com.cn.zbin.management.dto.MessageHistory;

public class MessageHistoryMsgData extends MsgData {
	private MessageHistory sms;

	public MessageHistory getSms() {
		return sms;
	}

	public void setSms(MessageHistory sms) {
		this.sms = sms;
	}
}
