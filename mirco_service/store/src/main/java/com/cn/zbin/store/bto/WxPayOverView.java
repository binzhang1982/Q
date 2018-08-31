package com.cn.zbin.store.bto;

import com.cn.zbin.store.dto.WxPayHistory;

public class WxPayOverView extends MsgData {
	private WxPayH5Param param;
	private WxPayHistory pay;

	public WxPayH5Param getParam() {
		return param;
	}

	public void setParam(WxPayH5Param param) {
		this.param = param;
	}

	public WxPayHistory getPay() {
		return pay;
	}

	public void setPay(WxPayHistory pay) {
		this.pay = pay;
	}
	
}
