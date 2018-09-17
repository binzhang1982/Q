package com.cn.zbin.store.bto;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class LeaseCalcAmountMsgData extends MsgData {
	private String orderProductId;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	private Date startDate;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	private Date orginEndDate;
	private BigDecimal paid;
	private BigDecimal bail;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	private Date recycleDate;
	private BigDecimal unitPrice;
	private Long pendingCount;
	private BigDecimal amount;
	private String type;
	public String getOrderProductId() {
		return orderProductId;
	}
	public void setOrderProductId(String orderProductId) {
		this.orderProductId = orderProductId;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getOrginEndDate() {
		return orginEndDate;
	}
	public void setOrginEndDate(Date orginEndDate) {
		this.orginEndDate = orginEndDate;
	}
	public BigDecimal getPaid() {
		return paid;
	}
	public void setPaid(BigDecimal paid) {
		this.paid = paid;
	}
	public BigDecimal getBail() {
		return bail;
	}
	public void setBail(BigDecimal bail) {
		this.bail = bail;
	}
	public Date getRecycleDate() {
		return recycleDate;
	}
	public void setRecycleDate(Date recycleDate) {
		this.recycleDate = recycleDate;
	}
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}
	public Long getPendingCount() {
		return pendingCount;
	}
	public void setPendingCount(Long pendingCount) {
		this.pendingCount = pendingCount;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
