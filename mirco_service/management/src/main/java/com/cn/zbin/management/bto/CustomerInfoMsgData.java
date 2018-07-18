package com.cn.zbin.management.bto;

import com.cn.zbin.management.dto.CustomerInfo;

public class CustomerInfoMsgData extends MsgData {
	private CustomerInfo customer;

	public CustomerInfo getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerInfo customer) {
		this.customer = customer;
	}
}
