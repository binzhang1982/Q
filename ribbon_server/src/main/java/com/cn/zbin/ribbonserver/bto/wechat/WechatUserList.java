package com.cn.zbin.ribbonserver.bto.wechat;

public class WechatUserList {
	private Integer total;
	private Integer count;
	private WechatOpenIDList data;
	private String next_openid;
	
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public WechatOpenIDList getData() {
		return data;
	}
	public void setData(WechatOpenIDList data) {
		this.data = data;
	}
	public String getNext_openid() {
		return next_openid;
	}
	public void setNext_openid(String next_openid) {
		this.next_openid = next_openid;
	}
}
