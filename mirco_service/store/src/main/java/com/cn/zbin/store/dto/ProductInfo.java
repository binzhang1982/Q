package com.cn.zbin.store.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ProductInfo {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column product.product_id
     *
     * @mbggenerated
     */
    private String productId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column product.product_name
     *
     * @mbggenerated
     */
    private String productName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column product.product_category_code
     *
     * @mbggenerated
     */
    private String productCategoryCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column product.product_brand_code
     *
     * @mbggenerated
     */
    private String productBrandCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column product.product_type_code
     *
     * @mbggenerated
     */
    private String productTypeCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column product.stock
     *
     * @mbggenerated
     */
    private Integer stock;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column product.carriage
     *
     * @mbggenerated
     */
    private BigDecimal carriage;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column product.service
     *
     * @mbggenerated
     */
    private BigDecimal service;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column product.weight
     *
     * @mbggenerated
     */
    private Integer weight;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column product.description
     *
     * @mbggenerated
     */
    private String description;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column product.lease_flag
     *
     * @mbggenerated
     */
    private Boolean leaseFlag;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column product.lease_min_days
     *
     * @mbggenerated
     */
    private Integer leaseMinDays;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column product.bail
     *
     * @mbggenerated
     */
    private BigDecimal bail;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column product.listing_flag
     *
     * @mbggenerated
     */
    private Boolean listingFlag;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column product.sale_start_id
     *
     * @mbggenerated
     */
    private String saleStartId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column product.sale_start_time
     *
     * @mbggenerated
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date saleStartTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column product.sale_end_id
     *
     * @mbggenerated
     */
    private String saleEndId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column product.sale_end_time
     *
     * @mbggenerated
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date saleEndTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column product.introduce_url
     *
     * @mbggenerated
     */
    private String introduceUrl;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column product.description_url
     *
     * @mbggenerated
     */
    private String descriptionUrl;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column product.product_id
     *
     * @return the value of product.product_id
     *
     * @mbggenerated
     */
    public String getProductId() {
        return productId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column product.product_id
     *
     * @param productId the value for product.product_id
     *
     * @mbggenerated
     */
    public void setProductId(String productId) {
        this.productId = productId == null ? null : productId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column product.product_name
     *
     * @return the value of product.product_name
     *
     * @mbggenerated
     */
    public String getProductName() {
        return productName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column product.product_name
     *
     * @param productName the value for product.product_name
     *
     * @mbggenerated
     */
    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column product.product_category_code
     *
     * @return the value of product.product_category_code
     *
     * @mbggenerated
     */
    public String getProductCategoryCode() {
        return productCategoryCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column product.product_category_code
     *
     * @param productCategoryCode the value for product.product_category_code
     *
     * @mbggenerated
     */
    public void setProductCategoryCode(String productCategoryCode) {
        this.productCategoryCode = productCategoryCode == null ? null : productCategoryCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column product.product_brand_code
     *
     * @return the value of product.product_brand_code
     *
     * @mbggenerated
     */
    public String getProductBrandCode() {
        return productBrandCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column product.product_brand_code
     *
     * @param productBrandCode the value for product.product_brand_code
     *
     * @mbggenerated
     */
    public void setProductBrandCode(String productBrandCode) {
        this.productBrandCode = productBrandCode == null ? null : productBrandCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column product.product_type_code
     *
     * @return the value of product.product_type_code
     *
     * @mbggenerated
     */
    public String getProductTypeCode() {
        return productTypeCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column product.product_type_code
     *
     * @param productTypeCode the value for product.product_type_code
     *
     * @mbggenerated
     */
    public void setProductTypeCode(String productTypeCode) {
        this.productTypeCode = productTypeCode == null ? null : productTypeCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column product.stock
     *
     * @return the value of product.stock
     *
     * @mbggenerated
     */
    public Integer getStock() {
        return stock;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column product.stock
     *
     * @param stock the value for product.stock
     *
     * @mbggenerated
     */
    public void setStock(Integer stock) {
        this.stock = stock;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column product.carriage
     *
     * @return the value of product.carriage
     *
     * @mbggenerated
     */
    public BigDecimal getCarriage() {
        return carriage;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column product.carriage
     *
     * @param carriage the value for product.carriage
     *
     * @mbggenerated
     */
    public void setCarriage(BigDecimal carriage) {
        this.carriage = carriage;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column product.service
     *
     * @return the value of product.service
     *
     * @mbggenerated
     */
    public BigDecimal getService() {
        return service;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column product.service
     *
     * @param service the value for product.service
     *
     * @mbggenerated
     */
    public void setService(BigDecimal service) {
        this.service = service;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column product.weight
     *
     * @return the value of product.weight
     *
     * @mbggenerated
     */
    public Integer getWeight() {
        return weight;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column product.weight
     *
     * @param weight the value for product.weight
     *
     * @mbggenerated
     */
    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column product.description
     *
     * @return the value of product.description
     *
     * @mbggenerated
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column product.description
     *
     * @param description the value for product.description
     *
     * @mbggenerated
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column product.lease_flag
     *
     * @return the value of product.lease_flag
     *
     * @mbggenerated
     */
    public Boolean getLeaseFlag() {
        return leaseFlag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column product.lease_flag
     *
     * @param leaseFlag the value for product.lease_flag
     *
     * @mbggenerated
     */
    public void setLeaseFlag(Boolean leaseFlag) {
        this.leaseFlag = leaseFlag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column product.lease_min_days
     *
     * @return the value of product.lease_min_days
     *
     * @mbggenerated
     */
    public Integer getLeaseMinDays() {
        return leaseMinDays;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column product.lease_min_days
     *
     * @param leaseMinDays the value for product.lease_min_days
     *
     * @mbggenerated
     */
    public void setLeaseMinDays(Integer leaseMinDays) {
        this.leaseMinDays = leaseMinDays;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column product.bail
     *
     * @return the value of product.bail
     *
     * @mbggenerated
     */
    public BigDecimal getBail() {
        return bail;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column product.bail
     *
     * @param bail the value for product.bail
     *
     * @mbggenerated
     */
    public void setBail(BigDecimal bail) {
        this.bail = bail;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column product.listing_flag
     *
     * @return the value of product.listing_flag
     *
     * @mbggenerated
     */
    public Boolean getListingFlag() {
        return listingFlag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column product.listing_flag
     *
     * @param listingFlag the value for product.listing_flag
     *
     * @mbggenerated
     */
    public void setListingFlag(Boolean listingFlag) {
        this.listingFlag = listingFlag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column product.sale_start_id
     *
     * @return the value of product.sale_start_id
     *
     * @mbggenerated
     */
    public String getSaleStartId() {
        return saleStartId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column product.sale_start_id
     *
     * @param saleStartId the value for product.sale_start_id
     *
     * @mbggenerated
     */
    public void setSaleStartId(String saleStartId) {
        this.saleStartId = saleStartId == null ? null : saleStartId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column product.sale_start_time
     *
     * @return the value of product.sale_start_time
     *
     * @mbggenerated
     */
    public Date getSaleStartTime() {
        return saleStartTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column product.sale_start_time
     *
     * @param saleStartTime the value for product.sale_start_time
     *
     * @mbggenerated
     */
    public void setSaleStartTime(Date saleStartTime) {
        this.saleStartTime = saleStartTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column product.sale_end_id
     *
     * @return the value of product.sale_end_id
     *
     * @mbggenerated
     */
    public String getSaleEndId() {
        return saleEndId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column product.sale_end_id
     *
     * @param saleEndId the value for product.sale_end_id
     *
     * @mbggenerated
     */
    public void setSaleEndId(String saleEndId) {
        this.saleEndId = saleEndId == null ? null : saleEndId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column product.sale_end_time
     *
     * @return the value of product.sale_end_time
     *
     * @mbggenerated
     */
    public Date getSaleEndTime() {
        return saleEndTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column product.sale_end_time
     *
     * @param saleEndTime the value for product.sale_end_time
     *
     * @mbggenerated
     */
    public void setSaleEndTime(Date saleEndTime) {
        this.saleEndTime = saleEndTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column product.introduce_url
     *
     * @return the value of product.introduce_url
     *
     * @mbggenerated
     */
    public String getIntroduceUrl() {
        return introduceUrl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column product.introduce_url
     *
     * @param introduceUrl the value for product.introduce_url
     *
     * @mbggenerated
     */
    public void setIntroduceUrl(String introduceUrl) {
        this.introduceUrl = introduceUrl == null ? null : introduceUrl.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column product.description_url
     *
     * @return the value of product.description_url
     *
     * @mbggenerated
     */
    public String getDescriptionUrl() {
        return descriptionUrl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column product.description_url
     *
     * @param descriptionUrl the value for product.description_url
     *
     * @mbggenerated
     */
    public void setDescriptionUrl(String descriptionUrl) {
        this.descriptionUrl = descriptionUrl == null ? null : descriptionUrl.trim();
    }
}