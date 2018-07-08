package com.cn.zbin.store.bto;

import java.math.BigDecimal;

import com.cn.zbin.store.dto.OrderProduct;

public class OrderProductOverView {
	private OrderProduct orderProduct;
	private BigDecimal realUnitPrice;
	private Boolean isLease;
	public OrderProduct getOrderProduct() {
		return orderProduct;
	}
	public void setOrderProduct(OrderProduct orderProduct) {
		this.orderProduct = orderProduct;
	}
	public BigDecimal getRealUnitPrice() {
		return realUnitPrice;
	}
	public void setRealUnitPrice(BigDecimal realUnitPrice) {
		this.realUnitPrice = realUnitPrice;
	}
	public Boolean getIsLease() {
		return isLease;
	}
	public void setIsLease(Boolean isLease) {
		this.isLease = isLease;
	}
}
