package com.cn.zbin.management.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class CustomerInfo {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column customer.customer_id
     *
     * @mbggenerated
     */
    private String customerId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column customer.register_type
     *
     * @mbggenerated
     */
    private Integer registerType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column customer.register_id
     *
     * @mbggenerated
     */
    private String registerId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column customer.customer_name
     *
     * @mbggenerated
     */
    private String customerName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column customer.birthday
     *
     * @mbggenerated
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date birthday;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column customer.telephone
     *
     * @mbggenerated
     */
    private String telephone;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column customer.valid_flag
     *
     * @mbggenerated
     */
    private Boolean validFlag;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column customer.valid_code
     *
     * @mbggenerated
     */
    private String validCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column customer.token
     *
     * @mbggenerated
     */
    private String token;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column customer.expried_time
     *
     * @mbggenerated
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date expriedTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column customer.disease_text
     *
     * @mbggenerated
     */
    private String diseaseText;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column customer.create_time
     *
     * @mbggenerated
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column customer.update_time
     *
     * @mbggenerated
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column customer.customer_id
     *
     * @return the value of customer.customer_id
     *
     * @mbggenerated
     */
    public String getCustomerId() {
        return customerId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column customer.customer_id
     *
     * @param customerId the value for customer.customer_id
     *
     * @mbggenerated
     */
    public void setCustomerId(String customerId) {
        this.customerId = customerId == null ? null : customerId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column customer.register_type
     *
     * @return the value of customer.register_type
     *
     * @mbggenerated
     */
    public Integer getRegisterType() {
        return registerType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column customer.register_type
     *
     * @param registerType the value for customer.register_type
     *
     * @mbggenerated
     */
    public void setRegisterType(Integer registerType) {
        this.registerType = registerType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column customer.register_id
     *
     * @return the value of customer.register_id
     *
     * @mbggenerated
     */
    public String getRegisterId() {
        return registerId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column customer.register_id
     *
     * @param registerId the value for customer.register_id
     *
     * @mbggenerated
     */
    public void setRegisterId(String registerId) {
        this.registerId = registerId == null ? null : registerId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column customer.customer_name
     *
     * @return the value of customer.customer_name
     *
     * @mbggenerated
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column customer.customer_name
     *
     * @param customerName the value for customer.customer_name
     *
     * @mbggenerated
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName == null ? null : customerName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column customer.birthday
     *
     * @return the value of customer.birthday
     *
     * @mbggenerated
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column customer.birthday
     *
     * @param birthday the value for customer.birthday
     *
     * @mbggenerated
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column customer.telephone
     *
     * @return the value of customer.telephone
     *
     * @mbggenerated
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column customer.telephone
     *
     * @param telephone the value for customer.telephone
     *
     * @mbggenerated
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone == null ? null : telephone.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column customer.valid_flag
     *
     * @return the value of customer.valid_flag
     *
     * @mbggenerated
     */
    public Boolean getValidFlag() {
        return validFlag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column customer.valid_flag
     *
     * @param validFlag the value for customer.valid_flag
     *
     * @mbggenerated
     */
    public void setValidFlag(Boolean validFlag) {
        this.validFlag = validFlag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column customer.valid_code
     *
     * @return the value of customer.valid_code
     *
     * @mbggenerated
     */
    public String getValidCode() {
        return validCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column customer.valid_code
     *
     * @param validCode the value for customer.valid_code
     *
     * @mbggenerated
     */
    public void setValidCode(String validCode) {
        this.validCode = validCode == null ? null : validCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column customer.token
     *
     * @return the value of customer.token
     *
     * @mbggenerated
     */
    public String getToken() {
        return token;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column customer.token
     *
     * @param token the value for customer.token
     *
     * @mbggenerated
     */
    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column customer.expried_time
     *
     * @return the value of customer.expried_time
     *
     * @mbggenerated
     */
    public Date getExpriedTime() {
        return expriedTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column customer.expried_time
     *
     * @param expriedTime the value for customer.expried_time
     *
     * @mbggenerated
     */
    public void setExpriedTime(Date expriedTime) {
        this.expriedTime = expriedTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column customer.disease_text
     *
     * @return the value of customer.disease_text
     *
     * @mbggenerated
     */
    public String getDiseaseText() {
        return diseaseText;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column customer.disease_text
     *
     * @param diseaseText the value for customer.disease_text
     *
     * @mbggenerated
     */
    public void setDiseaseText(String diseaseText) {
        this.diseaseText = diseaseText == null ? null : diseaseText.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column customer.create_time
     *
     * @return the value of customer.create_time
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column customer.create_time
     *
     * @param createTime the value for customer.create_time
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column customer.update_time
     *
     * @return the value of customer.update_time
     *
     * @mbggenerated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column customer.update_time
     *
     * @param updateTime the value for customer.update_time
     *
     * @mbggenerated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}