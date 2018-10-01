package com.cn.zbin.store.bto;

import java.util.List;

public class GuestOrderListMsgData extends MsgData {
	private Long totalCount = 0L;
	private List<GuestOrderOverView> guestOrderList;
	public Long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}
	public List<GuestOrderOverView> getGuestOrderList() {
		return guestOrderList;
	}
	public void setGuestOrderList(List<GuestOrderOverView> guestOrderList) {
		this.guestOrderList = guestOrderList;
	}
}
