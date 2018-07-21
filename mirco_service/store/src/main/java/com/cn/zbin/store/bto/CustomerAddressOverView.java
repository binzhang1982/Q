package com.cn.zbin.store.bto;

import com.cn.zbin.store.dto.CustomerAddress;

public class CustomerAddressOverView {
	private CustomerAddress address;
	private String provinceName;
	private String cityName;
	public CustomerAddress getAddress() {
		return address;
	}
	public void setAddress(CustomerAddress address) {
		this.address = address;
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
