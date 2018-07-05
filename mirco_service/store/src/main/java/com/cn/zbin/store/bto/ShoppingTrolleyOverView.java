package com.cn.zbin.store.bto;

import java.util.List;

import com.cn.zbin.store.dto.ShoppingTrolleyInfo;

public class ShoppingTrolleyOverView {
	private List<ShoppingTrolleyInfo> leaseTrolley;
	private List<ShoppingTrolleyInfo> sellTrolley;
	public List<ShoppingTrolleyInfo> getLeaseTrolley() {
		return leaseTrolley;
	}
	public void setLeaseTrolley(List<ShoppingTrolleyInfo> leaseTrolley) {
		this.leaseTrolley = leaseTrolley;
	}
	public List<ShoppingTrolleyInfo> getSellTrolley() {
		return sellTrolley;
	}
	public void setSellTrolley(List<ShoppingTrolleyInfo> sellTrolley) {
		this.sellTrolley = sellTrolley;
	}
}
