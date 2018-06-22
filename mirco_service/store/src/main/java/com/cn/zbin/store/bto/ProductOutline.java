package com.cn.zbin.store.bto;

import com.cn.zbin.store.dto.ProductImage;
import com.cn.zbin.store.dto.ProductInfo;
import com.cn.zbin.store.dto.ProductPrice;

public class ProductOutline {
	private ProductInfo prodInfo;
	private ProductPrice minProdPrice;
	private ProductImage frontCoverImage;
	public ProductInfo getProdInfo() {
		return prodInfo;
	}
	public void setProdInfo(ProductInfo prodInfo) {
		this.prodInfo = prodInfo;
	}
	public ProductPrice getMinProdPrice() {
		return minProdPrice;
	}
	public void setMinProdPrice(ProductPrice minProdPrice) {
		this.minProdPrice = minProdPrice;
	}
	public ProductImage getFrontCoverImage() {
		return frontCoverImage;
	}
	public void setFrontCoverImage(ProductImage frontCoverImage) {
		this.frontCoverImage = frontCoverImage;
	}
}
