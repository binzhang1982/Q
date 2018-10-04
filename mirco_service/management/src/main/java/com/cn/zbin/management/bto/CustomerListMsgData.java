package com.cn.zbin.management.bto;

import java.util.List;

import com.cn.zbin.management.dto.CustomerInfo;

public class CustomerListMsgData extends MsgData {
	private Long totalCount = 0L;
	private List<CustomerInfo> customerList;

	public Long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}

	public List<CustomerInfo> getCustomerList() {
		return customerList;
	}

	public void setCustomerList(List<CustomerInfo> customerList) {
		this.customerList = customerList;
	}
}
