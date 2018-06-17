package com.cn.zbin.store.bto;

import java.util.List;

public class ProductOverView {
	List<ProductCategory> leaseProdCate;
	List<ProductCategory> sellProdCate;
	
	public List<ProductCategory> getLeaseProdCate() {
		return leaseProdCate;
	}
	public void setLeaseProdCate(List<ProductCategory> leaseProdCate) {
		this.leaseProdCate = leaseProdCate;
	}
	public List<ProductCategory> getSellProdCate() {
		return sellProdCate;
	}
	public void setSellProdCate(List<ProductCategory> sellProdCate) {
		this.sellProdCate = sellProdCate;
	}
}
