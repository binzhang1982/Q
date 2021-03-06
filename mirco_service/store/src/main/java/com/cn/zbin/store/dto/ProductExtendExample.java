package com.cn.zbin.store.dto;

import java.util.ArrayList;
import java.util.List;

public class ProductExtendExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table productextend
     *
     * @mbggenerated
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table productextend
     *
     * @mbggenerated
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table productextend
     *
     * @mbggenerated
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table productextend
     *
     * @mbggenerated
     */
    public ProductExtendExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table productextend
     *
     * @mbggenerated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table productextend
     *
     * @mbggenerated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table productextend
     *
     * @mbggenerated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table productextend
     *
     * @mbggenerated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table productextend
     *
     * @mbggenerated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table productextend
     *
     * @mbggenerated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table productextend
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
     * This method corresponds to the database table productextend
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
     * This method corresponds to the database table productextend
     *
     * @mbggenerated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table productextend
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
     * This class corresponds to the database table productextend
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

        public Criteria andProductExtendIdIsNull() {
            addCriterion("product_extend_id is null");
            return (Criteria) this;
        }

        public Criteria andProductExtendIdIsNotNull() {
            addCriterion("product_extend_id is not null");
            return (Criteria) this;
        }

        public Criteria andProductExtendIdEqualTo(String value) {
            addCriterion("product_extend_id =", value, "productExtendId");
            return (Criteria) this;
        }

        public Criteria andProductExtendIdNotEqualTo(String value) {
            addCriterion("product_extend_id <>", value, "productExtendId");
            return (Criteria) this;
        }

        public Criteria andProductExtendIdGreaterThan(String value) {
            addCriterion("product_extend_id >", value, "productExtendId");
            return (Criteria) this;
        }

        public Criteria andProductExtendIdGreaterThanOrEqualTo(String value) {
            addCriterion("product_extend_id >=", value, "productExtendId");
            return (Criteria) this;
        }

        public Criteria andProductExtendIdLessThan(String value) {
            addCriterion("product_extend_id <", value, "productExtendId");
            return (Criteria) this;
        }

        public Criteria andProductExtendIdLessThanOrEqualTo(String value) {
            addCriterion("product_extend_id <=", value, "productExtendId");
            return (Criteria) this;
        }

        public Criteria andProductExtendIdLike(String value) {
            addCriterion("product_extend_id like", value, "productExtendId");
            return (Criteria) this;
        }

        public Criteria andProductExtendIdNotLike(String value) {
            addCriterion("product_extend_id not like", value, "productExtendId");
            return (Criteria) this;
        }

        public Criteria andProductExtendIdIn(List<String> values) {
            addCriterion("product_extend_id in", values, "productExtendId");
            return (Criteria) this;
        }

        public Criteria andProductExtendIdNotIn(List<String> values) {
            addCriterion("product_extend_id not in", values, "productExtendId");
            return (Criteria) this;
        }

        public Criteria andProductExtendIdBetween(String value1, String value2) {
            addCriterion("product_extend_id between", value1, value2, "productExtendId");
            return (Criteria) this;
        }

        public Criteria andProductExtendIdNotBetween(String value1, String value2) {
            addCriterion("product_extend_id not between", value1, value2, "productExtendId");
            return (Criteria) this;
        }

        public Criteria andProductIdIsNull() {
            addCriterion("product_id is null");
            return (Criteria) this;
        }

        public Criteria andProductIdIsNotNull() {
            addCriterion("product_id is not null");
            return (Criteria) this;
        }

        public Criteria andProductIdEqualTo(String value) {
            addCriterion("product_id =", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdNotEqualTo(String value) {
            addCriterion("product_id <>", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdGreaterThan(String value) {
            addCriterion("product_id >", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdGreaterThanOrEqualTo(String value) {
            addCriterion("product_id >=", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdLessThan(String value) {
            addCriterion("product_id <", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdLessThanOrEqualTo(String value) {
            addCriterion("product_id <=", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdLike(String value) {
            addCriterion("product_id like", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdNotLike(String value) {
            addCriterion("product_id not like", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdIn(List<String> values) {
            addCriterion("product_id in", values, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdNotIn(List<String> values) {
            addCriterion("product_id not in", values, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdBetween(String value1, String value2) {
            addCriterion("product_id between", value1, value2, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdNotBetween(String value1, String value2) {
            addCriterion("product_id not between", value1, value2, "productId");
            return (Criteria) this;
        }

        public Criteria andPartIdIsNull() {
            addCriterion("part_id is null");
            return (Criteria) this;
        }

        public Criteria andPartIdIsNotNull() {
            addCriterion("part_id is not null");
            return (Criteria) this;
        }

        public Criteria andPartIdEqualTo(String value) {
            addCriterion("part_id =", value, "partId");
            return (Criteria) this;
        }

        public Criteria andPartIdNotEqualTo(String value) {
            addCriterion("part_id <>", value, "partId");
            return (Criteria) this;
        }

        public Criteria andPartIdGreaterThan(String value) {
            addCriterion("part_id >", value, "partId");
            return (Criteria) this;
        }

        public Criteria andPartIdGreaterThanOrEqualTo(String value) {
            addCriterion("part_id >=", value, "partId");
            return (Criteria) this;
        }

        public Criteria andPartIdLessThan(String value) {
            addCriterion("part_id <", value, "partId");
            return (Criteria) this;
        }

        public Criteria andPartIdLessThanOrEqualTo(String value) {
            addCriterion("part_id <=", value, "partId");
            return (Criteria) this;
        }

        public Criteria andPartIdLike(String value) {
            addCriterion("part_id like", value, "partId");
            return (Criteria) this;
        }

        public Criteria andPartIdNotLike(String value) {
            addCriterion("part_id not like", value, "partId");
            return (Criteria) this;
        }

        public Criteria andPartIdIn(List<String> values) {
            addCriterion("part_id in", values, "partId");
            return (Criteria) this;
        }

        public Criteria andPartIdNotIn(List<String> values) {
            addCriterion("part_id not in", values, "partId");
            return (Criteria) this;
        }

        public Criteria andPartIdBetween(String value1, String value2) {
            addCriterion("part_id between", value1, value2, "partId");
            return (Criteria) this;
        }

        public Criteria andPartIdNotBetween(String value1, String value2) {
            addCriterion("part_id not between", value1, value2, "partId");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table productextend
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
     * This class corresponds to the database table productextend
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