package com.cn.zbin.store.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class MessageHistory {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column messagehistory.message_id
     *
     * @mbggenerated
     */
    private String messageId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column messagehistory.template_code
     *
     * @mbggenerated
     */
    private String templateCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column messagehistory.sign_name
     *
     * @mbggenerated
     */
    private String signName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column messagehistory.template_params
     *
     * @mbggenerated
     */
    private String templateParams;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column messagehistory.phone_number
     *
     * @mbggenerated
     */
    private String phoneNumber;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column messagehistory.return_code
     *
     * @mbggenerated
     */
    private String returnCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column messagehistory.create_time
     *
     * @mbggenerated
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column messagehistory.sent_time
     *
     * @mbggenerated
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date sentTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column messagehistory.message_id
     *
     * @return the value of messagehistory.message_id
     *
     * @mbggenerated
     */
    public String getMessageId() {
        return messageId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column messagehistory.message_id
     *
     * @param messageId the value for messagehistory.message_id
     *
     * @mbggenerated
     */
    public void setMessageId(String messageId) {
        this.messageId = messageId == null ? null : messageId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column messagehistory.template_code
     *
     * @return the value of messagehistory.template_code
     *
     * @mbggenerated
     */
    public String getTemplateCode() {
        return templateCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column messagehistory.template_code
     *
     * @param templateCode the value for messagehistory.template_code
     *
     * @mbggenerated
     */
    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode == null ? null : templateCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column messagehistory.sign_name
     *
     * @return the value of messagehistory.sign_name
     *
     * @mbggenerated
     */
    public String getSignName() {
        return signName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column messagehistory.sign_name
     *
     * @param signName the value for messagehistory.sign_name
     *
     * @mbggenerated
     */
    public void setSignName(String signName) {
        this.signName = signName == null ? null : signName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column messagehistory.template_params
     *
     * @return the value of messagehistory.template_params
     *
     * @mbggenerated
     */
    public String getTemplateParams() {
        return templateParams;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column messagehistory.template_params
     *
     * @param templateParams the value for messagehistory.template_params
     *
     * @mbggenerated
     */
    public void setTemplateParams(String templateParams) {
        this.templateParams = templateParams == null ? null : templateParams.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column messagehistory.phone_number
     *
     * @return the value of messagehistory.phone_number
     *
     * @mbggenerated
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column messagehistory.phone_number
     *
     * @param phoneNumber the value for messagehistory.phone_number
     *
     * @mbggenerated
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber == null ? null : phoneNumber.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column messagehistory.return_code
     *
     * @return the value of messagehistory.return_code
     *
     * @mbggenerated
     */
    public String getReturnCode() {
        return returnCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column messagehistory.return_code
     *
     * @param returnCode the value for messagehistory.return_code
     *
     * @mbggenerated
     */
    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode == null ? null : returnCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column messagehistory.create_time
     *
     * @return the value of messagehistory.create_time
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column messagehistory.create_time
     *
     * @param createTime the value for messagehistory.create_time
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column messagehistory.sent_time
     *
     * @return the value of messagehistory.sent_time
     *
     * @mbggenerated
     */
    public Date getSentTime() {
        return sentTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column messagehistory.sent_time
     *
     * @param sentTime the value for messagehistory.sent_time
     *
     * @mbggenerated
     */
    public void setSentTime(Date sentTime) {
        this.sentTime = sentTime;
    }
}