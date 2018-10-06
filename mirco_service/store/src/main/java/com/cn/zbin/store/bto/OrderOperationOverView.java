package com.cn.zbin.store.bto;

import com.cn.zbin.store.dto.OrderOperationHistory;

public class OrderOperationOverView {
	private OrderOperationHistory orderOperation;
	private GuestOrderOverView guestOrder;
	public OrderOperationHistory getOrderOperation() {
		return orderOperation;
	}
	public void setOrderOperation(OrderOperationHistory orderOperation) {
		this.orderOperation = orderOperation;
	}
	public GuestOrderOverView getGuestOrder() {
		return guestOrder;
	}
	public void setGuestOrder(GuestOrderOverView guestOrder) {
		this.guestOrder = guestOrder;
	}
}
