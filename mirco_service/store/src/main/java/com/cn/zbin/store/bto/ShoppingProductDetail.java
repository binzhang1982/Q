package com.cn.zbin.store.bto;

import com.cn.zbin.store.dto.ProductImage;
import com.cn.zbin.store.dto.ProductInfo;
import com.cn.zbin.store.dto.ProductPrice;
import com.cn.zbin.store.dto.ShoppingTrolleyInfo;

public class ShoppingProductDetail {
	private ShoppingTrolleyInfo trolley;
	private ProductInfo prodInfo;
	private ProductImage frontCoverImage;
	private ProductPrice unitPrice;
	public ShoppingTrolleyInfo getTrolley() {
		return trolley;
	}
	public void setTrolley(ShoppingTrolleyInfo trolley) {
		this.trolley = trolley;
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
	public ProductPrice getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(ProductPrice unitPrice) {
		this.unitPrice = unitPrice;
	}
}
