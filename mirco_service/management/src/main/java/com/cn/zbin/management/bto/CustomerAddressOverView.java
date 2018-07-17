package com.cn.zbin.management.bto;

import com.cn.zbin.management.dto.CustomerAddress;

public class CustomerAddressOverView {
	private CustomerAddress addr;
	
	private String provinceName;
	
	private String cityName;

	public CustomerAddress getAddr() {
		return addr;
	}

	public void setAddr(CustomerAddress addr) {
		this.addr = addr;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
}
