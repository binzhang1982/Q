package com.cn.zbin.store.bto;

import java.math.BigDecimal;

import com.cn.zbin.store.dto.OrderProduct;
import com.cn.zbin.store.dto.ProductImage;
import com.cn.zbin.store.dto.ProductInfo;

public class OrderProductOverView {
	private OrderProduct orderProduct;
	private ProductInfo prodInfo;
	private ProductImage frontCoverImage;
	private BigDecimal realUnitPrice;
	private Boolean isLease;
	public OrderProduct getOrderProduct() {
		return orderProduct;
	}
	public void setOrderProduct(OrderProduct orderProduct) {
		this.orderProduct = orderProduct;
	}
	public ProductInfo getProdInfo() {
		return prodInfo;
	}
	public void setProdInfo(ProductInfo prodInfo) {
		this.prodInfo = prodInfo;
	}
	public ProductImage getFrontCoverImage() {
		return frontCoverImage;
	}
	public void setFrontCoverImage(ProductImage frontCoverImage) {
		this.frontCoverImage = frontCoverImage;
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
