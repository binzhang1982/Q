package com.cn.zbin.store.bto;

import java.util.List;

public class ShoppingTrolleyOverView {
	private List<ShoppingProductDetail> leaseTrolley;
	private List<ShoppingProductDetail> sellTrolley;
	public List<ShoppingProductDetail> getLeaseTrolley() {
		return leaseTrolley;
	}
	public void setLeaseTrolley(List<ShoppingProductDetail> leaseTrolley) {
		this.leaseTrolley = leaseTrolley;
	}
	public List<ShoppingProductDetail> getSellTrolley() {
		return sellTrolley;
	}
	public void setSellTrolley(List<ShoppingProductDetail> sellTrolley) {
		this.sellTrolley = sellTrolley;
	}
}
