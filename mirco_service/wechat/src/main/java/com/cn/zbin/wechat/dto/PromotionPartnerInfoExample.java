package com.cn.zbin.wechat.dto;

import java.util.ArrayList;
import java.util.List;

public class PromotionPartnerInfoExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table promotionpartner
     *
     * @mbggenerated
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table promotionpartner
     *
     * @mbggenerated
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table promotionpartner
     *
     * @mbggenerated
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table promotionpartner
     *
     * @mbggenerated
     */
    public PromotionPartnerInfoExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table promotionpartner
     *
     * @mbggenerated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table promotionpartner
     *
     * @mbggenerated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table promotionpartner
     *
     * @mbggenerated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table promotionpartner
     *
     * @mbggenerated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table promotionpartner
     *
     * @mbggenerated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table promotionpartner
     *
     * @mbggenerated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table promotionpartner
     *
     * @mbggenerated
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table promotionpartner
     *
     * @mbggenerated
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table promotionpartner
     *
     * @mbggenerated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table promotionpartner
     *
     * @mbggenerated
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table promotionpartner
     *
     * @mbggenerated
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andPartnerIdIsNull() {
            addCriterion("partner_id is null");
            return (Criteria) this;
        }

        public Criteria andPartnerIdIsNotNull() {
            addCriterion("partner_id is not null");
            return (Criteria) this;
        }

        public Criteria andPartnerIdEqualTo(String value) {
            addCriterion("partner_id =", value, "partnerId");
            return (Criteria) this;
        }

        public Criteria andPartnerIdNotEqualTo(String value) {
            addCriterion("partner_id <>", value, "partnerId");
            return (Criteria) this;
        }

        public Criteria andPartnerIdGreaterThan(String value) {
            addCriterion("partner_id >", value, "partnerId");
            return (Criteria) this;
        }

        public Criteria andPartnerIdGreaterThanOrEqualTo(String value) {
            addCriterion("partner_id >=", value, "partnerId");
            return (Criteria) this;
        }

        public Criteria andPartnerIdLessThan(String value) {
            addCriterion("partner_id <", value, "partnerId");
            return (Criteria) this;
        }

        public Criteria andPartnerIdLessThanOrEqualTo(String value) {
            addCriterion("partner_id <=", value, "partnerId");
            return (Criteria) this;
        }

        public Criteria andPartnerIdLike(String value) {
            addCriterion("partner_id like", value, "partnerId");
            return (Criteria) this;
        }

        public Criteria andPartnerIdNotLike(String value) {
            addCriterion("partner_id not like", value, "partnerId");
            return (Criteria) this;
        }

        public Criteria andPartnerIdIn(List<String> values) {
            addCriterion("partner_id in", values, "partnerId");
            return (Criteria) this;
        }

        public Criteria andPartnerIdNotIn(List<String> values) {
            addCriterion("partner_id not in", values, "partnerId");
            return (Criteria) this;
        }

        public Criteria andPartnerIdBetween(String value1, String value2) {
            addCriterion("partner_id between", value1, value2, "partnerId");
            return (Criteria) this;
        }

        public Criteria andPartnerIdNotBetween(String value1, String value2) {
            addCriterion("partner_id not between", value1, value2, "partnerId");
            return (Criteria) this;
        }

        public Criteria andPartnerNameIsNull() {
            addCriterion("partner_name is null");
            return (Criteria) this;
        }

        public Criteria andPartnerNameIsNotNull() {
            addCriterion("partner_name is not null");
            return (Criteria) this;
        }

        public Criteria andPartnerNameEqualTo(String value) {
            addCriterion("partner_name =", value, "partnerName");
            return (Criteria) this;
        }

        public Criteria andPartnerNameNotEqualTo(String value) {
            addCriterion("partner_name <>", value, "partnerName");
            return (Criteria) this;
        }

        public Criteria andPartnerNameGreaterThan(String value) {
            addCriterion("partner_name >", value, "partnerName");
            return (Criteria) this;
        }

        public Criteria andPartnerNameGreaterThanOrEqualTo(String value) {
            addCriterion("partner_name >=", value, "partnerName");
            return (Criteria) this;
        }

        public Criteria andPartnerNameLessThan(String value) {
            addCriterion("partner_name <", value, "partnerName");
            return (Criteria) this;
        }

        public Criteria andPartnerNameLessThanOrEqualTo(String value) {
            addCriterion("partner_name <=", value, "partnerName");
            return (Criteria) this;
        }

        public Criteria andPartnerNameLike(String value) {
            addCriterion("partner_name like", value, "partnerName");
            return (Criteria) this;
        }

        public Criteria andPartnerNameNotLike(String value) {
            addCriterion("partner_name not like", value, "partnerName");
            return (Criteria) this;
        }

        public Criteria andPartnerNameIn(List<String> values) {
            addCriterion("partner_name in", values, "partnerName");
            return (Criteria) this;
        }

        public Criteria andPartnerNameNotIn(List<String> values) {
            addCriterion("partner_name not in", values, "partnerName");
            return (Criteria) this;
        }

        public Criteria andPartnerNameBetween(String value1, String value2) {
            addCriterion("partner_name between", value1, value2, "partnerName");
            return (Criteria) this;
        }

        public Criteria andPartnerNameNotBetween(String value1, String value2) {
            addCriterion("partner_name not between", value1, value2, "partnerName");
            return (Criteria) this;
        }

        public Criteria andCorpTypeIsNull() {
            addCriterion("corp_type is null");
            return (Criteria) this;
        }

        public Criteria andCorpTypeIsNotNull() {
            addCriterion("corp_type is not null");
            return (Criteria) this;
        }

        public Criteria andCorpTypeEqualTo(String value) {
            addCriterion("corp_type =", value, "corpType");
            return (Criteria) this;
        }

        public Criteria andCorpTypeNotEqualTo(String value) {
            addCriterion("corp_type <>", value, "corpType");
            return (Criteria) this;
        }

        public Criteria andCorpTypeGreaterThan(String value) {
            addCriterion("corp_type >", value, "corpType");
            return (Criteria) this;
        }

        public Criteria andCorpTypeGreaterThanOrEqualTo(String value) {
            addCriterion("corp_type >=", value, "corpType");
            return (Criteria) this;
        }

        public Criteria andCorpTypeLessThan(String value) {
            addCriterion("corp_type <", value, "corpType");
            return (Criteria) this;
        }

        public Criteria andCorpTypeLessThanOrEqualTo(String value) {
            addCriterion("corp_type <=", value, "corpType");
            return (Criteria) this;
        }

        public Criteria andCorpTypeLike(String value) {
            addCriterion("corp_type like", value, "corpType");
            return (Criteria) this;
        }

        public Criteria andCorpTypeNotLike(String value) {
            addCriterion("corp_type not like", value, "corpType");
            return (Criteria) this;
        }

        public Criteria andCorpTypeIn(List<String> values) {
            addCriterion("corp_type in", values, "corpType");
            return (Criteria) this;
        }

        public Criteria andCorpTypeNotIn(List<String> values) {
            addCriterion("corp_type not in", values, "corpType");
            return (Criteria) this;
        }

        public Criteria andCorpTypeBetween(String value1, String value2) {
            addCriterion("corp_type between", value1, value2, "corpType");
            return (Criteria) this;
        }

        public Criteria andCorpTypeNotBetween(String value1, String value2) {
            addCriterion("corp_type not between", value1, value2, "corpType");
            return (Criteria) this;
        }

        public Criteria andCorpNameIsNull() {
            addCriterion("corp_name is null");
            return (Criteria) this;
        }

        public Criteria andCorpNameIsNotNull() {
            addCriterion("corp_name is not null");
            return (Criteria) this;
        }

        public Criteria andCorpNameEqualTo(String value) {
            addCriterion("corp_name =", value, "corpName");
            return (Criteria) this;
        }

        public Criteria andCorpNameNotEqualTo(String value) {
            addCriterion("corp_name <>", value, "corpName");
            return (Criteria) this;
        }

        public Criteria andCorpNameGreaterThan(String value) {
            addCriterion("corp_name >", value, "corpName");
            return (Criteria) this;
        }

        public Criteria andCorpNameGreaterThanOrEqualTo(String value) {
            addCriterion("corp_name >=", value, "corpName");
            return (Criteria) this;
        }

        public Criteria andCorpNameLessThan(String value) {
            addCriterion("corp_name <", value, "corpName");
            return (Criteria) this;
        }

        public Criteria andCorpNameLessThanOrEqualTo(String value) {
            addCriterion("corp_name <=", value, "corpName");
            return (Criteria) this;
        }

        public Criteria andCorpNameLike(String value) {
            addCriterion("corp_name like", value, "corpName");
            return (Criteria) this;
        }

        public Criteria andCorpNameNotLike(String value) {
            addCriterion("corp_name not like", value, "corpName");
            return (Criteria) this;
        }

        public Criteria andCorpNameIn(List<String> values) {
            addCriterion("corp_name in", values, "corpName");
            return (Criteria) this;
        }

        public Criteria andCorpNameNotIn(List<String> values) {
            addCriterion("corp_name not in", values, "corpName");
            return (Criteria) this;
        }

        public Criteria andCorpNameBetween(String value1, String value2) {
            addCriterion("corp_name between", value1, value2, "corpName");
            return (Criteria) this;
        }

        public Criteria andCorpNameNotBetween(String value1, String value2) {
            addCriterion("corp_name not between", value1, value2, "corpName");
            return (Criteria) this;
        }

        public Criteria andCorpAreaIsNull() {
            addCriterion("corp_area is null");
            return (Criteria) this;
        }

        public Criteria andCorpAreaIsNotNull() {
            addCriterion("corp_area is not null");
            return (Criteria) this;
        }

        public Criteria andCorpAreaEqualTo(String value) {
            addCriterion("corp_area =", value, "corpArea");
            return (Criteria) this;
        }

        public Criteria andCorpAreaNotEqualTo(String value) {
            addCriterion("corp_area <>", value, "corpArea");
            return (Criteria) this;
        }

        public Criteria andCorpAreaGreaterThan(String value) {
            addCriterion("corp_area >", value, "corpArea");
            return (Criteria) this;
        }

        public Criteria andCorpAreaGreaterThanOrEqualTo(String value) {
            addCriterion("corp_area >=", value, "corpArea");
            return (Criteria) this;
        }

        public Criteria andCorpAreaLessThan(String value) {
            addCriterion("corp_area <", value, "corpArea");
            return (Criteria) this;
        }

        public Criteria andCorpAreaLessThanOrEqualTo(String value) {
            addCriterion("corp_area <=", value, "corpArea");
            return (Criteria) this;
        }

        public Criteria andCorpAreaLike(String value) {
            addCriterion("corp_area like", value, "corpArea");
            return (Criteria) this;
        }

        public Criteria andCorpAreaNotLike(String value) {
            addCriterion("corp_area not like", value, "corpArea");
            return (Criteria) this;
        }

        public Criteria andCorpAreaIn(List<String> values) {
            addCriterion("corp_area in", values, "corpArea");
            return (Criteria) this;
        }

        public Criteria andCorpAreaNotIn(List<String> values) {
            addCriterion("corp_area not in", values, "corpArea");
            return (Criteria) this;
        }

        public Criteria andCorpAreaBetween(String value1, String value2) {
            addCriterion("corp_area between", value1, value2, "corpArea");
            return (Criteria) this;
        }

        public Criteria andCorpAreaNotBetween(String value1, String value2) {
            addCriterion("corp_area not between", value1, value2, "corpArea");
            return (Criteria) this;
        }

        public Criteria andCorpNumberIsNull() {
            addCriterion("corp_number is null");
            return (Criteria) this;
        }

        public Criteria andCorpNumberIsNotNull() {
            addCriterion("corp_number is not null");
            return (Criteria) this;
        }

        public Criteria andCorpNumberEqualTo(String value) {
            addCriterion("corp_number =", value, "corpNumber");
            return (Criteria) this;
        }

        public Criteria andCorpNumberNotEqualTo(String value) {
            addCriterion("corp_number <>", value, "corpNumber");
            return (Criteria) this;
        }

        public Criteria andCorpNumberGreaterThan(String value) {
            addCriterion("corp_number >", value, "corpNumber");
            return (Criteria) this;
        }

        public Criteria andCorpNumberGreaterThanOrEqualTo(String value) {
            addCriterion("corp_number >=", value, "corpNumber");
            return (Criteria) this;
        }

        public Criteria andCorpNumberLessThan(String value) {
            addCriterion("corp_number <", value, "corpNumber");
            return (Criteria) this;
        }

        public Criteria andCorpNumberLessThanOrEqualTo(String value) {
            addCriterion("corp_number <=", value, "corpNumber");
            return (Criteria) this;
        }

        public Criteria andCorpNumberLike(String value) {
            addCriterion("corp_number like", value, "corpNumber");
            return (Criteria) this;
        }

        public Criteria andCorpNumberNotLike(String value) {
            addCriterion("corp_number not like", value, "corpNumber");
            return (Criteria) this;
        }

        public Criteria andCorpNumberIn(List<String> values) {
            addCriterion("corp_number in", values, "corpNumber");
            return (Criteria) this;
        }

        public Criteria andCorpNumberNotIn(List<String> values) {
            addCriterion("corp_number not in", values, "corpNumber");
            return (Criteria) this;
        }

        public Criteria andCorpNumberBetween(String value1, String value2) {
            addCriterion("corp_number between", value1, value2, "corpNumber");
            return (Criteria) this;
        }

        public Criteria andCorpNumberNotBetween(String value1, String value2) {
            addCriterion("corp_number not between", value1, value2, "corpNumber");
            return (Criteria) this;
        }

        public Criteria andCorpAddressIsNull() {
            addCriterion("corp_address is null");
            return (Criteria) this;
        }

        public Criteria andCorpAddressIsNotNull() {
            addCriterion("corp_address is not null");
            return (Criteria) this;
        }

        public Criteria andCorpAddressEqualTo(String value) {
            addCriterion("corp_address =", value, "corpAddress");
            return (Criteria) this;
        }

        public Criteria andCorpAddressNotEqualTo(String value) {
            addCriterion("corp_address <>", value, "corpAddress");
            return (Criteria) this;
        }

        public Criteria andCorpAddressGreaterThan(String value) {
            addCriterion("corp_address >", value, "corpAddress");
            return (Criteria) this;
        }

        public Criteria andCorpAddressGreaterThanOrEqualTo(String value) {
            addCriterion("corp_address >=", value, "corpAddress");
            return (Criteria) this;
        }

        public Criteria andCorpAddressLessThan(String value) {
            addCriterion("corp_address <", value, "corpAddress");
            return (Criteria) this;
        }

        public Criteria andCorpAddressLessThanOrEqualTo(String value) {
            addCriterion("corp_address <=", value, "corpAddress");
            return (Criteria) this;
        }

        public Criteria andCorpAddressLike(String value) {
            addCriterion("corp_address like", value, "corpAddress");
            return (Criteria) this;
        }

        public Criteria andCorpAddressNotLike(String value) {
            addCriterion("corp_address not like", value, "corpAddress");
            return (Criteria) this;
        }

        public Criteria andCorpAddressIn(List<String> values) {
            addCriterion("corp_address in", values, "corpAddress");
            return (Criteria) this;
        }

        public Criteria andCorpAddressNotIn(List<String> values) {
            addCriterion("corp_address not in", values, "corpAddress");
            return (Criteria) this;
        }

        public Criteria andCorpAddressBetween(String value1, String value2) {
            addCriterion("corp_address between", value1, value2, "corpAddress");
            return (Criteria) this;
        }

        public Criteria andCorpAddressNotBetween(String value1, String value2) {
            addCriterion("corp_address not between", value1, value2, "corpAddress");
            return (Criteria) this;
        }

        public Criteria andCorpPhoneIsNull() {
            addCriterion("corp_phone is null");
            return (Criteria) this;
        }

        public Criteria andCorpPhoneIsNotNull() {
            addCriterion("corp_phone is not null");
            return (Criteria) this;
        }

        public Criteria andCorpPhoneEqualTo(String value) {
            addCriterion("corp_phone =", value, "corpPhone");
            return (Criteria) this;
        }

        public Criteria andCorpPhoneNotEqualTo(String value) {
            addCriterion("corp_phone <>", value, "corpPhone");
            return (Criteria) this;
        }

        public Criteria andCorpPhoneGreaterThan(String value) {
            addCriterion("corp_phone >", value, "corpPhone");
            return (Criteria) this;
        }

        public Criteria andCorpPhoneGreaterThanOrEqualTo(String value) {
            addCriterion("corp_phone >=", value, "corpPhone");
            return (Criteria) this;
        }

        public Criteria andCorpPhoneLessThan(String value) {
            addCriterion("corp_phone <", value, "corpPhone");
            return (Criteria) this;
        }

        public Criteria andCorpPhoneLessThanOrEqualTo(String value) {
            addCriterion("corp_phone <=", value, "corpPhone");
            return (Criteria) this;
        }

        public Criteria andCorpPhoneLike(String value) {
            addCriterion("corp_phone like", value, "corpPhone");
            return (Criteria) this;
        }

        public Criteria andCorpPhoneNotLike(String value) {
            addCriterion("corp_phone not like", value, "corpPhone");
            return (Criteria) this;
        }

        public Criteria andCorpPhoneIn(List<String> values) {
            addCriterion("corp_phone in", values, "corpPhone");
            return (Criteria) this;
        }

        public Criteria andCorpPhoneNotIn(List<String> values) {
            addCriterion("corp_phone not in", values, "corpPhone");
            return (Criteria) this;
        }

        public Criteria andCorpPhoneBetween(String value1, String value2) {
            addCriterion("corp_phone between", value1, value2, "corpPhone");
            return (Criteria) this;
        }

        public Criteria andCorpPhoneNotBetween(String value1, String value2) {
            addCriterion("corp_phone not between", value1, value2, "corpPhone");
            return (Criteria) this;
        }

        public Criteria andOfficeNameIsNull() {
            addCriterion("office_name is null");
            return (Criteria) this;
        }

        public Criteria andOfficeNameIsNotNull() {
            addCriterion("office_name is not null");
            return (Criteria) this;
        }

        public Criteria andOfficeNameEqualTo(String value) {
            addCriterion("office_name =", value, "officeName");
            return (Criteria) this;
        }

        public Criteria andOfficeNameNotEqualTo(String value) {
            addCriterion("office_name <>", value, "officeName");
            return (Criteria) this;
        }

        public Criteria andOfficeNameGreaterThan(String value) {
            addCriterion("office_name >", value, "officeName");
            return (Criteria) this;
        }

        public Criteria andOfficeNameGreaterThanOrEqualTo(String value) {
            addCriterion("office_name >=", value, "officeName");
            return (Criteria) this;
        }

        public Criteria andOfficeNameLessThan(String value) {
            addCriterion("office_name <", value, "officeName");
            return (Criteria) this;
        }

        public Criteria andOfficeNameLessThanOrEqualTo(String value) {
            addCriterion("office_name <=", value, "officeName");
            return (Criteria) this;
        }

        public Criteria andOfficeNameLike(String value) {
            addCriterion("office_name like", value, "officeName");
            return (Criteria) this;
        }

        public Criteria andOfficeNameNotLike(String value) {
            addCriterion("office_name not like", value, "officeName");
            return (Criteria) this;
        }

        public Criteria andOfficeNameIn(List<String> values) {
            addCriterion("office_name in", values, "officeName");
            return (Criteria) this;
        }

        public Criteria andOfficeNameNotIn(List<String> values) {
            addCriterion("office_name not in", values, "officeName");
            return (Criteria) this;
        }

        public Criteria andOfficeNameBetween(String value1, String value2) {
            addCriterion("office_name between", value1, value2, "officeName");
            return (Criteria) this;
        }

        public Criteria andOfficeNameNotBetween(String value1, String value2) {
            addCriterion("office_name not between", value1, value2, "officeName");
            return (Criteria) this;
        }

        public Criteria andTicketIsNull() {
            addCriterion("ticket is null");
            return (Criteria) this;
        }

        public Criteria andTicketIsNotNull() {
            addCriterion("ticket is not null");
            return (Criteria) this;
        }

        public Criteria andTicketEqualTo(String value) {
            addCriterion("ticket =", value, "ticket");
            return (Criteria) this;
        }

        public Criteria andTicketNotEqualTo(String value) {
            addCriterion("ticket <>", value, "ticket");
            return (Criteria) this;
        }

        public Criteria andTicketGreaterThan(String value) {
            addCriterion("ticket >", value, "ticket");
            return (Criteria) this;
        }

        public Criteria andTicketGreaterThanOrEqualTo(String value) {
            addCriterion("ticket >=", value, "ticket");
            return (Criteria) this;
        }

        public Criteria andTicketLessThan(String value) {
            addCriterion("ticket <", value, "ticket");
            return (Criteria) this;
        }

        public Criteria andTicketLessThanOrEqualTo(String value) {
            addCriterion("ticket <=", value, "ticket");
            return (Criteria) this;
        }

        public Criteria andTicketLike(String value) {
            addCriterion("ticket like", value, "ticket");
            return (Criteria) this;
        }

        public Criteria andTicketNotLike(String value) {
            addCriterion("ticket not like", value, "ticket");
            return (Criteria) this;
        }

        public Criteria andTicketIn(List<String> values) {
            addCriterion("ticket in", values, "ticket");
            return (Criteria) this;
        }

        public Criteria andTicketNotIn(List<String> values) {
            addCriterion("ticket not in", values, "ticket");
            return (Criteria) this;
        }

        public Criteria andTicketBetween(String value1, String value2) {
            addCriterion("ticket between", value1, value2, "ticket");
            return (Criteria) this;
        }

        public Criteria andTicketNotBetween(String value1, String value2) {
            addCriterion("ticket not between", value1, value2, "ticket");
            return (Criteria) this;
        }

        public Criteria andSceneStrIsNull() {
            addCriterion("scene_str is null");
            return (Criteria) this;
        }

        public Criteria andSceneStrIsNotNull() {
            addCriterion("scene_str is not null");
            return (Criteria) this;
        }

        public Criteria andSceneStrEqualTo(String value) {
            addCriterion("scene_str =", value, "sceneStr");
            return (Criteria) this;
        }

        public Criteria andSceneStrNotEqualTo(String value) {
            addCriterion("scene_str <>", value, "sceneStr");
            return (Criteria) this;
        }

        public Criteria andSceneStrGreaterThan(String value) {
            addCriterion("scene_str >", value, "sceneStr");
            return (Criteria) this;
        }

        public Criteria andSceneStrGreaterThanOrEqualTo(String value) {
            addCriterion("scene_str >=", value, "sceneStr");
            return (Criteria) this;
        }

        public Criteria andSceneStrLessThan(String value) {
            addCriterion("scene_str <", value, "sceneStr");
            return (Criteria) this;
        }

        public Criteria andSceneStrLessThanOrEqualTo(String value) {
            addCriterion("scene_str <=", value, "sceneStr");
            return (Criteria) this;
        }

        public Criteria andSceneStrLike(String value) {
            addCriterion("scene_str like", value, "sceneStr");
            return (Criteria) this;
        }

        public Criteria andSceneStrNotLike(String value) {
            addCriterion("scene_str not like", value, "sceneStr");
            return (Criteria) this;
        }

        public Criteria andSceneStrIn(List<String> values) {
            addCriterion("scene_str in", values, "sceneStr");
            return (Criteria) this;
        }

        public Criteria andSceneStrNotIn(List<String> values) {
            addCriterion("scene_str not in", values, "sceneStr");
            return (Criteria) this;
        }

        public Criteria andSceneStrBetween(String value1, String value2) {
            addCriterion("scene_str between", value1, value2, "sceneStr");
            return (Criteria) this;
        }

        public Criteria andSceneStrNotBetween(String value1, String value2) {
            addCriterion("scene_str not between", value1, value2, "sceneStr");
            return (Criteria) this;
        }

        public Criteria andUrlIsNull() {
            addCriterion("url is null");
            return (Criteria) this;
        }

        public Criteria andUrlIsNotNull() {
            addCriterion("url is not null");
            return (Criteria) this;
        }

        public Criteria andUrlEqualTo(String value) {
            addCriterion("url =", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlNotEqualTo(String value) {
            addCriterion("url <>", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlGreaterThan(String value) {
            addCriterion("url >", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlGreaterThanOrEqualTo(String value) {
            addCriterion("url >=", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlLessThan(String value) {
            addCriterion("url <", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlLessThanOrEqualTo(String value) {
            addCriterion("url <=", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlLike(String value) {
            addCriterion("url like", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlNotLike(String value) {
            addCriterion("url not like", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlIn(List<String> values) {
            addCriterion("url in", values, "url");
            return (Criteria) this;
        }

        public Criteria andUrlNotIn(List<String> values) {
            addCriterion("url not in", values, "url");
            return (Criteria) this;
        }

        public Criteria andUrlBetween(String value1, String value2) {
            addCriterion("url between", value1, value2, "url");
            return (Criteria) this;
        }

        public Criteria andUrlNotBetween(String value1, String value2) {
            addCriterion("url not between", value1, value2, "url");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table promotionpartner
     *
     * @mbggenerated do_not_delete_during_merge
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table promotionpartner
     *
     * @mbggenerated
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}