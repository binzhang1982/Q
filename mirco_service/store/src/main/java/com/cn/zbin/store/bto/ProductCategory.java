package com.cn.zbin.store.bto;

import java.util.List;

public class ProductCategory {
	private String productCategoryCode;
	private String productCategoryName;
	private List<ProductOutline> prodList;
	public String getProductCategoryCode() {
		return productCategoryCode;
	}
	public void setProductCategoryCode(String productCategoryCode) {
		this.productCategoryCode = productCategoryCode;
	}
	public String getProductCategoryName() {
		return productCategoryName;
	}
	public void setProductCategoryName(String productCategoryName) {
		this.productCategoryName = productCategoryName;
	}
	public List<ProductOutline> getProdList() {
		return prodList;
	}
	public void setProdList(List<ProductOutline> prodList) {
		this.prodList = prodList;
	}
}
