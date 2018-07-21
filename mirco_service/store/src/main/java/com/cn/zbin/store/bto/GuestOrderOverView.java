package com.cn.zbin.store.bto;

import java.util.List;

import com.cn.zbin.store.dto.GuestOrderInfo;

public class GuestOrderOverView extends MsgData {
	private GuestOrderInfo guestOrderInfo;
	private CustomerAddressOverView customerAddress;
	private CustomerInvoiceOverView customerInvoice;
	private List<OrderProductOverView> orderProductList;
	
	public GuestOrderInfo getGuestOrderInfo() {
		return guestOrderInfo;
	}
	public void setGuestOrderInfo(GuestOrderInfo guestOrderInfo) {
		this.guestOrderInfo = guestOrderInfo;
	}
	public CustomerAddressOverView getCustomerAddress() {
		return customerAddress;
	}
	public void setCustomerAddress(CustomerAddressOverView customerAddress) {
		this.customerAddress = customerAddress;
	}
	public CustomerInvoiceOverView getCustomerInvoice() {
		return customerInvoice;
	}
	public void setCustomerInvoice(CustomerInvoiceOverView customerInvoice) {
		this.customerInvoice = customerInvoice;
	}
	public List<OrderProductOverView> getOrderProductList() {
		return orderProductList;
	}
	public void setOrderProductList(List<OrderProductOverView> orderProductList) {
		this.orderProductList = orderProductList;
	}	
}
