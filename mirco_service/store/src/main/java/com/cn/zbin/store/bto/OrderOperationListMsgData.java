package com.cn.zbin.store.bto;

import java.util.List;

public class OrderOperationListMsgData extends MsgData {
	private Long totalCount = 0L;
	private List<OrderOperationOverView> orderOperationList;
	public Long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}
	public List<OrderOperationOverView> getOrderOperationList() {
		return orderOperationList;
	}
	public void setOrderOperationList(
			List<OrderOperationOverView> orderOperationList) {
		this.orderOperationList = orderOperationList;
	}
}
