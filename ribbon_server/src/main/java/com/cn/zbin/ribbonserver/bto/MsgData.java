package com.cn.zbin.ribbonserver.bto;

public class MsgData {
	public static Integer status_ng = -1; 
	private Integer status;
	private String message;
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
