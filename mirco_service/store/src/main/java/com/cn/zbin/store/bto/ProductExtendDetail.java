package com.cn.zbin.store.bto;

import java.util.List;

import com.cn.zbin.store.dto.ProductImage;
import com.cn.zbin.store.dto.ProductInfo;
import com.cn.zbin.store.dto.ProductPrice;

public class ProductExtendDetail {
	private ProductInfo prodInfo;
	private List<ProductPrice> prodPrice;
	private ProductImage frontCoverImage;
	public ProductInfo getProdInfo() {
		return prodInfo;
	}
	public void setProdInfo(ProductInfo prodInfo) {
		this.prodInfo = prodInfo;
	}
	public List<ProductPrice> getProdPrice() {
		return prodPrice;
	}
	public void setProdPrice(List<ProductPrice> prodPrice) {
		this.prodPrice = prodPrice;
	}
	public ProductImage getFrontCoverImage() {
		return frontCoverImage;
	}
	public void setFrontCoverImage(ProductImage frontCoverImage) {
		this.frontCoverImage = frontCoverImage;
	}
}
