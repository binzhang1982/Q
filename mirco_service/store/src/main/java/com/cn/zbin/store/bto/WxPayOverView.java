package com.cn.zbin.store.bto;

import com.cn.zbin.store.dto.WxPayHistory;

public class WxPayOverView extends MsgData {
	private WxPayHistory pay;

	public WxPayHistory getPay() {
		return pay;
	}

	public void setPay(WxPayHistory pay) {
		this.pay = pay;
	}
	
}
