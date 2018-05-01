package com.cn.zbin.wechat.bto;

import java.io.Serializable;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement
public class WeChatMessage implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//开发者微信号
	@JacksonXmlProperty(localName = "ToUserName")
    private String toUserName;

	//发送方帐号（一个OpenID）
	@JacksonXmlProperty(localName = "FromUserName")
    private String fromUserName;

	//消息创建时间 （整型）
	@JacksonXmlProperty(localName = "CreateTime")
    private long createTime;
	
	//消息类型
	//事件:event
	//消息:text
	@JacksonXmlProperty(localName = "MsgType")
    private String msgType;

	//事件类型事件类型
	//关注,取消关注事件:subscribe(订阅)、unsubscribe(取消订阅)
	//扫描带参数二维码事件:未关注 subscribe、已关注 SCAN
	//地理位置上报事件:LOCATION
	//自定义菜单事件:点击 CLICK 跳转 VIEW
	@JacksonXmlProperty(localName = "Event")
    private String event;
	
	//事件KEY值
	//带参二维码:qrscene_为前缀，后面为二维码的参数值
	//自定义菜单点击:接口中KEY值对应
	//自定义菜单跳转:设置的跳转URL
	@JacksonXmlProperty(localName = "EventKey")
	private String eventKey;
	
	//二维码的ticket
	//可用来换取二维码图片事件KEY值，qrscene_为前缀
	@JacksonXmlProperty(localName = "Ticket")
	private String ticket;
	
	//地理位置纬度
	@JacksonXmlProperty(localName = "Latitude")
	private String latitude;
	
	//地理位置经度
	@JacksonXmlProperty(localName = "Longitude")
	private String longitude;
	
	//地理位置精度
	@JacksonXmlProperty(localName = "Precision")
	private String precision;

	//消息id，64位整型
	@JacksonXmlProperty(localName = "MsgId")
	private String msgId;
	//文本消息内容
	@JacksonXmlProperty(localName = "Content")
	private String content;
	//图片链接（由系统生成）
	@JacksonXmlProperty(localName = "PicUrl")
	private String picUrl;
	//图片消息媒体id，可以调用多媒体文件下载接口拉取数据。
	@JacksonXmlProperty(localName = "MediaId")
	private String mediaId;

	
	public String toTextString() {
		String xml = new String();
		xml = "<xml>" + 
			"<ToUserName><![CDATA[" + this.toUserName + "]]></ToUserName>" + 
			"<FromUserName><![CDATA[" + this.fromUserName + "]]></FromUserName>" + 
			"<CreateTime>" + this.createTime + "</CreateTime>" + 
			"<MsgType><![CDATA[" + this.msgType + "]]></MsgType>" + 
			"<Content><![CDATA[" + this.content + "]]></Content>" +
			"<FuncFlag>0</FuncFlag>" + 
			"</xml>";
		return xml;
	}

	public String getToUserName() {
		return toUserName;
	}

	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}

	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getEventKey() {
		return eventKey;
	}

	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getPrecision() {
		return precision;
	}

	public void setPrecision(String precision) {
		this.precision = precision;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
    
}
