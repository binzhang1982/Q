package com.cn.zbin.store.dto;

import java.math.BigDecimal;
import java.util.Date;

public class OrderProduct {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orderproduct.order_product_id
     *
     * @mbggenerated
     */
    private String orderProductId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orderproduct.ref_order_product_id
     *
     * @mbggenerated
     */
    private String refOrderProductId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orderproduct.ref_type_code
     *
     * @mbggenerated
     */
    private String refTypeCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orderproduct.order_id
     *
     * @mbggenerated
     */
    private String orderId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orderproduct.product_id
     *
     * @mbggenerated
     */
    private String productId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orderproduct.is_delete
     *
     * @mbggenerated
     */
    private Boolean isDelete;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orderproduct.sale_count
     *
     * @mbggenerated
     */
    private Integer saleCount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orderproduct.pending_count
     *
     * @mbggenerated
     */
    private Integer pendingCount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orderproduct.reserve_pending_date
     *
     * @mbggenerated
     */
    private Date reservePendingDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orderproduct.actual_pending_date
     *
     * @mbggenerated
     */
    private Date actualPendingDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orderproduct.reserve_send_date
     *
     * @mbggenerated
     */
    private Date reserveSendDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orderproduct.actual_send_date
     *
     * @mbggenerated
     */
    private Date actualSendDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orderproduct.paid_amount
     *
     * @mbggenerated
     */
    private BigDecimal paidAmount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orderproduct.pre_pay_amount
     *
     * @mbggenerated
     */
    private BigDecimal prePayAmount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orderproduct.refund_amount
     *
     * @mbggenerated
     */
    private BigDecimal refundAmount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orderproduct.bail
     *
     * @mbggenerated
     */
    private BigDecimal bail;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orderproduct.refund_bail
     *
     * @mbggenerated
     */
    private BigDecimal refundBail;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orderproduct.refund_code
     *
     * @mbggenerated
     */
    private String refundCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orderproduct.remark
     *
     * @mbggenerated
     */
    private String remark;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orderproduct.create_emp_id
     *
     * @mbggenerated
     */
    private String createEmpId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orderproduct.create_time
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orderproduct.update_emp_id
     *
     * @mbggenerated
     */
    private String updateEmpId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orderproduct.update_time
     *
     * @mbggenerated
     */
    private Date updateTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orderproduct.order_product_id
     *
     * @return the value of orderproduct.order_product_id
     *
     * @mbggenerated
     */
    public String getOrderProductId() {
        return orderProductId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orderproduct.order_product_id
     *
     * @param orderProductId the value for orderproduct.order_product_id
     *
     * @mbggenerated
     */
    public void setOrderProductId(String orderProductId) {
        this.orderProductId = orderProductId == null ? null : orderProductId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orderproduct.ref_order_product_id
     *
     * @return the value of orderproduct.ref_order_product_id
     *
     * @mbggenerated
     */
    public String getRefOrderProductId() {
        return refOrderProductId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orderproduct.ref_order_product_id
     *
     * @param refOrderProductId the value for orderproduct.ref_order_product_id
     *
     * @mbggenerated
     */
    public void setRefOrderProductId(String refOrderProductId) {
        this.refOrderProductId = refOrderProductId == null ? null : refOrderProductId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orderproduct.ref_type_code
     *
     * @return the value of orderproduct.ref_type_code
     *
     * @mbggenerated
     */
    public String getRefTypeCode() {
        return refTypeCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orderproduct.ref_type_code
     *
     * @param refTypeCode the value for orderproduct.ref_type_code
     *
     * @mbggenerated
     */
    public void setRefTypeCode(String refTypeCode) {
        this.refTypeCode = refTypeCode == null ? null : refTypeCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orderproduct.order_id
     *
     * @return the value of orderproduct.order_id
     *
     * @mbggenerated
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orderproduct.order_id
     *
     * @param orderId the value for orderproduct.order_id
     *
     * @mbggenerated
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orderproduct.product_id
     *
     * @return the value of orderproduct.product_id
     *
     * @mbggenerated
     */
    public String getProductId() {
        return productId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orderproduct.product_id
     *
     * @param productId the value for orderproduct.product_id
     *
     * @mbggenerated
     */
    public void setProductId(String productId) {
        this.productId = productId == null ? null : productId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orderproduct.is_delete
     *
     * @return the value of orderproduct.is_delete
     *
     * @mbggenerated
     */
    public Boolean getIsDelete() {
        return isDelete;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orderproduct.is_delete
     *
     * @param isDelete the value for orderproduct.is_delete
     *
     * @mbggenerated
     */
    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orderproduct.sale_count
     *
     * @return the value of orderproduct.sale_count
     *
     * @mbggenerated
     */
    public Integer getSaleCount() {
        return saleCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orderproduct.sale_count
     *
     * @param saleCount the value for orderproduct.sale_count
     *
     * @mbggenerated
     */
    public void setSaleCount(Integer saleCount) {
        this.saleCount = saleCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orderproduct.pending_count
     *
     * @return the value of orderproduct.pending_count
     *
     * @mbggenerated
     */
    public Integer getPendingCount() {
        return pendingCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orderproduct.pending_count
     *
     * @param pendingCount the value for orderproduct.pending_count
     *
     * @mbggenerated
     */
    public void setPendingCount(Integer pendingCount) {
        this.pendingCount = pendingCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orderproduct.reserve_pending_date
     *
     * @return the value of orderproduct.reserve_pending_date
     *
     * @mbggenerated
     */
    public Date getReservePendingDate() {
        return reservePendingDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orderproduct.reserve_pending_date
     *
     * @param reservePendingDate the value for orderproduct.reserve_pending_date
     *
     * @mbggenerated
     */
    public void setReservePendingDate(Date reservePendingDate) {
        this.reservePendingDate = reservePendingDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orderproduct.actual_pending_date
     *
     * @return the value of orderproduct.actual_pending_date
     *
     * @mbggenerated
     */
    public Date getActualPendingDate() {
        return actualPendingDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orderproduct.actual_pending_date
     *
     * @param actualPendingDate the value for orderproduct.actual_pending_date
     *
     * @mbggenerated
     */
    public void setActualPendingDate(Date actualPendingDate) {
        this.actualPendingDate = actualPendingDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orderproduct.reserve_send_date
     *
     * @return the value of orderproduct.reserve_send_date
     *
     * @mbggenerated
     */
    public Date getReserveSendDate() {
        return reserveSendDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orderproduct.reserve_send_date
     *
     * @param reserveSendDate the value for orderproduct.reserve_send_date
     *
     * @mbggenerated
     */
    public void setReserveSendDate(Date reserveSendDate) {
        this.reserveSendDate = reserveSendDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orderproduct.actual_send_date
     *
     * @return the value of orderproduct.actual_send_date
     *
     * @mbggenerated
     */
    public Date getActualSendDate() {
        return actualSendDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orderproduct.actual_send_date
     *
     * @param actualSendDate the value for orderproduct.actual_send_date
     *
     * @mbggenerated
     */
    public void setActualSendDate(Date actualSendDate) {
        this.actualSendDate = actualSendDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orderproduct.paid_amount
     *
     * @return the value of orderproduct.paid_amount
     *
     * @mbggenerated
     */
    public BigDecimal getPaidAmount() {
        return paidAmount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orderproduct.paid_amount
     *
     * @param paidAmount the value for orderproduct.paid_amount
     *
     * @mbggenerated
     */
    public void setPaidAmount(BigDecimal paidAmount) {
        this.paidAmount = paidAmount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orderproduct.pre_pay_amount
     *
     * @return the value of orderproduct.pre_pay_amount
     *
     * @mbggenerated
     */
    public BigDecimal getPrePayAmount() {
        return prePayAmount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orderproduct.pre_pay_amount
     *
     * @param prePayAmount the value for orderproduct.pre_pay_amount
     *
     * @mbggenerated
     */
    public void setPrePayAmount(BigDecimal prePayAmount) {
        this.prePayAmount = prePayAmount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orderproduct.refund_amount
     *
     * @return the value of orderproduct.refund_amount
     *
     * @mbggenerated
     */
    public BigDecimal getRefundAmount() {
        return refundAmount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orderproduct.refund_amount
     *
     * @param refundAmount the value for orderproduct.refund_amount
     *
     * @mbggenerated
     */
    public void setRefundAmount(BigDecimal refundAmount) {
        this.refundAmount = refundAmount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orderproduct.bail
     *
     * @return the value of orderproduct.bail
     *
     * @mbggenerated
     */
    public BigDecimal getBail() {
        return bail;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orderproduct.bail
     *
     * @param bail the value for orderproduct.bail
     *
     * @mbggenerated
     */
    public void setBail(BigDecimal bail) {
        this.bail = bail;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orderproduct.refund_bail
     *
     * @return the value of orderproduct.refund_bail
     *
     * @mbggenerated
     */
    public BigDecimal getRefundBail() {
        return refundBail;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orderproduct.refund_bail
     *
     * @param refundBail the value for orderproduct.refund_bail
     *
     * @mbggenerated
     */
    public void setRefundBail(BigDecimal refundBail) {
        this.refundBail = refundBail;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orderproduct.refund_code
     *
     * @return the value of orderproduct.refund_code
     *
     * @mbggenerated
     */
    public String getRefundCode() {
        return refundCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orderproduct.refund_code
     *
     * @param refundCode the value for orderproduct.refund_code
     *
     * @mbggenerated
     */
    public void setRefundCode(String refundCode) {
        this.refundCode = refundCode == null ? null : refundCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orderproduct.remark
     *
     * @return the value of orderproduct.remark
     *
     * @mbggenerated
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orderproduct.remark
     *
     * @param remark the value for orderproduct.remark
     *
     * @mbggenerated
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orderproduct.create_emp_id
     *
     * @return the value of orderproduct.create_emp_id
     *
     * @mbggenerated
     */
    public String getCreateEmpId() {
        return createEmpId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orderproduct.create_emp_id
     *
     * @param createEmpId the value for orderproduct.create_emp_id
     *
     * @mbggenerated
     */
    public void setCreateEmpId(String createEmpId) {
        this.createEmpId = createEmpId == null ? null : createEmpId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orderproduct.create_time
     *
     * @return the value of orderproduct.create_time
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orderproduct.create_time
     *
     * @param createTime the value for orderproduct.create_time
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orderproduct.update_emp_id
     *
     * @return the value of orderproduct.update_emp_id
     *
     * @mbggenerated
     */
    public String getUpdateEmpId() {
        return updateEmpId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orderproduct.update_emp_id
     *
     * @param updateEmpId the value for orderproduct.update_emp_id
     *
     * @mbggenerated
     */
    public void setUpdateEmpId(String updateEmpId) {
        this.updateEmpId = updateEmpId == null ? null : updateEmpId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orderproduct.update_time
     *
     * @return the value of orderproduct.update_time
     *
     * @mbggenerated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orderproduct.update_time
     *
     * @param updateTime the value for orderproduct.update_time
     *
     * @mbggenerated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}