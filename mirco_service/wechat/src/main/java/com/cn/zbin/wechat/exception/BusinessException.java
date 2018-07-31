package com.cn.zbin.wechat.exception;

public class BusinessException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	/**
	 * 错误消息代码
	 */
	private Integer errorCode;
	/**
	 * 错误消息
	 */
	private String errorMsg;

	/**
	 * 传给错误消息的参数
	 */
	private String[] errorMsgParam;

	public BusinessException() {

	}
	public BusinessException(Integer errorCode, String errorMsg) {
		super();
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}
	/**
	 * Simple wrapper of Throwable Object.
	 * 
	 * @param e
	 */
	public BusinessException(Throwable e) {

		super(e);
	}

	/**
	 * Constructor, exception contents the user-defined error information.
	 * 
	 * @param msg
	 * @param e
	 */
	public BusinessException(String msg,Throwable e) {

		super(msg, e);
	}

	public BusinessException(String msg) {

		super(msg);
	}

	/**
	 * @return the errorCode
	 */
	public Integer getErrorCode() {

		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {

		this.errorCode = errorCode;
	}
	
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public String toString() {

		return "APPLICATION ERROR-" + this.errorCode + " : " + this.errorMsg;
	}

	public void setErrorMsgParam(String[] errorMsgParam) {

		this.errorMsgParam = errorMsgParam;
	}

	public String[] getErrorMsgParam() {

		return errorMsgParam;
	}
	
}
