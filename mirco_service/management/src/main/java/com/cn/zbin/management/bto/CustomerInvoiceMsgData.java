package com.cn.zbin.management.bto;

import com.cn.zbin.management.dto.CustomerInvoice;

public class CustomerInvoiceMsgData extends MsgData {
	private CustomerInvoice invoice;

	public CustomerInvoice getInvoice() {
		return invoice;
	}

	public void setInvoice(CustomerInvoice invoice) {
		this.invoice = invoice;
	}
}
