package com.cn.zbin.store.bto;

import java.math.BigDecimal;
import java.util.List;

import com.cn.zbin.store.dto.ProductComment;

public class ProductCommentOverView {
	private List<ProductComment> commentList;
//	private List<ProductCommentDetail> commentList;
	private BigDecimal avgCommentLevel;
	private Integer commentCount;
//	public List<ProductCommentDetail> getCommentList() {
//		return commentList;
//	}
//	public void setCommentList(List<ProductCommentDetail> commentList) {
//		this.commentList = commentList;
//	}
	public List<ProductComment> getCommentList() {
		return commentList;
	}
	public void setCommentList(List<ProductComment> commentList) {
		this.commentList = commentList;
	}
	public BigDecimal getAvgCommentLevel() {
		return avgCommentLevel;
	}
	public void setAvgCommentLevel(BigDecimal avgCommentLevel) {
		this.avgCommentLevel = avgCommentLevel;
	}
	public Integer getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}
}
