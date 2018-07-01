package com.cn.zbin.store.dto;

import java.util.Date;

public class ShoppingTrolleyInfo {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column shoppingtrolley.trolley_id
     *
     * @mbggenerated
     */
    private String trolleyId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column shoppingtrolley.customer_id
     *
     * @mbggenerated
     */
    private String customerId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column shoppingtrolley.product_id
     *
     * @mbggenerated
     */
    private String productId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column shoppingtrolley.sale_count
     *
     * @mbggenerated
     */
    private Integer saleCount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column shoppingtrolley.reserve_pending_date
     *
     * @mbggenerated
     */
    private Date reservePendingDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column shoppingtrolley.pending_count
     *
     * @mbggenerated
     */
    private Integer pendingCount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column shoppingtrolley.is_delete
     *
     * @mbggenerated
     */
    private Byte isDelete;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column shoppingtrolley.delete_code
     *
     * @mbggenerated
     */
    private String deleteCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column shoppingtrolley.create_time
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column shoppingtrolley.update_time
     *
     * @mbggenerated
     */
    private Date updateTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column shoppingtrolley.trolley_id
     *
     * @return the value of shoppingtrolley.trolley_id
     *
     * @mbggenerated
     */
    public String getTrolleyId() {
        return trolleyId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column shoppingtrolley.trolley_id
     *
     * @param trolleyId the value for shoppingtrolley.trolley_id
     *
     * @mbggenerated
     */
    public void setTrolleyId(String trolleyId) {
        this.trolleyId = trolleyId == null ? null : trolleyId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column shoppingtrolley.customer_id
     *
     * @return the value of shoppingtrolley.customer_id
     *
     * @mbggenerated
     */
    public String getCustomerId() {
        return customerId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column shoppingtrolley.customer_id
     *
     * @param customerId the value for shoppingtrolley.customer_id
     *
     * @mbggenerated
     */
    public void setCustomerId(String customerId) {
        this.customerId = customerId == null ? null : customerId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column shoppingtrolley.product_id
     *
     * @return the value of shoppingtrolley.product_id
     *
     * @mbggenerated
     */
    public String getProductId() {
        return productId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column shoppingtrolley.product_id
     *
     * @param productId the value for shoppingtrolley.product_id
     *
     * @mbggenerated
     */
    public void setProductId(String productId) {
        this.productId = productId == null ? null : productId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column shoppingtrolley.sale_count
     *
     * @return the value of shoppingtrolley.sale_count
     *
     * @mbggenerated
     */
    public Integer getSaleCount() {
        return saleCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column shoppingtrolley.sale_count
     *
     * @param saleCount the value for shoppingtrolley.sale_count
     *
     * @mbggenerated
     */
    public void setSaleCount(Integer saleCount) {
        this.saleCount = saleCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column shoppingtrolley.reserve_pending_date
     *
     * @return the value of shoppingtrolley.reserve_pending_date
     *
     * @mbggenerated
     */
    public Date getReservePendingDate() {
        return reservePendingDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column shoppingtrolley.reserve_pending_date
     *
     * @param reservePendingDate the value for shoppingtrolley.reserve_pending_date
     *
     * @mbggenerated
     */
    public void setReservePendingDate(Date reservePendingDate) {
        this.reservePendingDate = reservePendingDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column shoppingtrolley.pending_count
     *
     * @return the value of shoppingtrolley.pending_count
     *
     * @mbggenerated
     */
    public Integer getPendingCount() {
        return pendingCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column shoppingtrolley.pending_count
     *
     * @param pendingCount the value for shoppingtrolley.pending_count
     *
     * @mbggenerated
     */
    public void setPendingCount(Integer pendingCount) {
        this.pendingCount = pendingCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column shoppingtrolley.is_delete
     *
     * @return the value of shoppingtrolley.is_delete
     *
     * @mbggenerated
     */
    public Byte getIsDelete() {
        return isDelete;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column shoppingtrolley.is_delete
     *
     * @param isDelete the value for shoppingtrolley.is_delete
     *
     * @mbggenerated
     */
    public void setIsDelete(Byte isDelete) {
        this.isDelete = isDelete;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column shoppingtrolley.delete_code
     *
     * @return the value of shoppingtrolley.delete_code
     *
     * @mbggenerated
     */
    public String getDeleteCode() {
        return deleteCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column shoppingtrolley.delete_code
     *
     * @param deleteCode the value for shoppingtrolley.delete_code
     *
     * @mbggenerated
     */
    public void setDeleteCode(String deleteCode) {
        this.deleteCode = deleteCode == null ? null : deleteCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column shoppingtrolley.create_time
     *
     * @return the value of shoppingtrolley.create_time
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column shoppingtrolley.create_time
     *
     * @param createTime the value for shoppingtrolley.create_time
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column shoppingtrolley.update_time
     *
     * @return the value of shoppingtrolley.update_time
     *
     * @mbggenerated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column shoppingtrolley.update_time
     *
     * @param updateTime the value for shoppingtrolley.update_time
     *
     * @mbggenerated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}