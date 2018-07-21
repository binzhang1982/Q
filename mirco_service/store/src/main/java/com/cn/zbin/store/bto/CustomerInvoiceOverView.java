package com.cn.zbin.store.bto;

import com.cn.zbin.store.dto.CustomerInvoice;

public class CustomerInvoiceOverView {
	private CustomerInvoice invoice;
	private String invoiceTypeName;
	public CustomerInvoice getInvoice() {
		return invoice;
	}
	public void setInvoice(CustomerInvoice invoice) {
		this.invoice = invoice;
	}
	public String getInvoiceTypeName() {
		return invoiceTypeName;
	}
	public void setInvoiceTypeName(String invoiceTypeName) {
		this.invoiceTypeName = invoiceTypeName;
	}
}
