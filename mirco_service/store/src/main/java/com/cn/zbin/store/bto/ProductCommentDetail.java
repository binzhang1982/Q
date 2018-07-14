package com.cn.zbin.store.bto;

import com.cn.zbin.store.dto.ProductComment;

public class ProductCommentDetail {
	private ProductComment comment;
	private String nickName;
	private String headImgurl;
	public ProductComment getComment() {
		return comment;
	}
	public void setComment(ProductComment comment) {
		this.comment = comment;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getHeadImgurl() {
		return headImgurl;
	}
	public void setHeadImgurl(String headImgurl) {
		this.headImgurl = headImgurl;
	}
}
