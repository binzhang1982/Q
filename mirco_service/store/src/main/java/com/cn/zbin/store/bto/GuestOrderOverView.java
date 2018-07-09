package com.cn.zbin.store.bto;

import java.math.BigDecimal;
import java.util.List;

import com.cn.zbin.store.dto.GuestOrderInfo;

public class GuestOrderOverView extends MsgData {
	private GuestOrderInfo guestOrderInfo;
	private List<OrderProductOverView> orderProductList;
	
	public GuestOrderInfo getGuestOrderInfo() {
		return guestOrderInfo;
	}
	public void setGuestOrderInfo(GuestOrderInfo guestOrderInfo) {
		this.guestOrderInfo = guestOrderInfo;
	}
	public List<OrderProductOverView> getOrderProductList() {
		return orderProductList;
	}
	public void setOrderProductList(List<OrderProductOverView> orderProductList) {
		this.orderProductList = orderProductList;
	}	
}
