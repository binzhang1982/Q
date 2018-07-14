package com.cn.zbin.store.bto;

import java.util.List;

import com.cn.zbin.store.dto.ProductComment;
import com.cn.zbin.store.dto.ProductExtend;
import com.cn.zbin.store.dto.ProductImage;
import com.cn.zbin.store.dto.ProductInfo;
import com.cn.zbin.store.dto.ProductPrice;

public class ProductDetail {
	private ProductInfo productInfo;
	private Integer commentCount;
//	private ProductCommentDetail lastestComment;
	private ProductComment lastestComment;
	private List<ProductExtend> extendList;
	private List<ProductImage> imageList;
	private List<ProductPrice> priceList;
	public ProductInfo getProductInfo() {
		return productInfo;
	}
	public void setProductInfo(ProductInfo productInfo) {
		this.productInfo = productInfo;
	}
	public Integer getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}
	public ProductComment getLastestComment() {
		return lastestComment;
	}
	public void setLastestComment(ProductComment lastestComment) {
		this.lastestComment = lastestComment;
	}
//	public ProductCommentDetail getLastestComment() {
//		return lastestComment;
//	}
//	public void setLastestComment(ProductCommentDetail lastestComment) {
//		this.lastestComment = lastestComment;
//	}
	public List<ProductExtend> getExtendList() {
		return extendList;
	}
	public void setExtendList(List<ProductExtend> extendList) {
		this.extendList = extendList;
	}
	public List<ProductImage> getImageList() {
		return imageList;
	}
	public void setImageList(List<ProductImage> imageList) {
		this.imageList = imageList;
	}
	public List<ProductPrice> getPriceList() {
		return priceList;
	}
	public void setPriceList(List<ProductPrice> priceList) {
		this.priceList = priceList;
	}
}
