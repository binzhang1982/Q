package com.cn.zbin.store.bto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class PendingDate {
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	private Date pendingStartDate;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	private Date pendingEndDate;
	private Long pendingCount;
	public Date getPendingStartDate() {
		return pendingStartDate;
	}
	public void setPendingStartDate(Date pendingStartDate) {
		this.pendingStartDate = pendingStartDate;
	}
	public Date getPendingEndDate() {
		return pendingEndDate;
	}
	public void setPendingEndDate(Date pendingEndDate) {
		this.pendingEndDate = pendingEndDate;
	}
	public Long getPendingCount() {
		return pendingCount;
	}
	public void setPendingCount(Long pendingCount) {
		this.pendingCount = pendingCount;
	}
}
