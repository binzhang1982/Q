package com.cn.zbin.store.dto;

import java.util.ArrayList;
import java.util.List;

public class ProductImageExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table productimage
     *
     * @mbggenerated
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table productimage
     *
     * @mbggenerated
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table productimage
     *
     * @mbggenerated
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table productimage
     *
     * @mbggenerated
     */
    public ProductImageExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table productimage
     *
     * @mbggenerated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table productimage
     *
     * @mbggenerated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table productimage
     *
     * @mbggenerated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table productimage
     *
     * @mbggenerated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table productimage
     *
     * @mbggenerated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table productimage
     *
     * @mbggenerated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table productimage
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
     * This method corresponds to the database table productimage
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
     * This method corresponds to the database table productimage
     *
     * @mbggenerated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table productimage
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
     * This class corresponds to the database table productimage
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

        public Criteria andProductImgIdIsNull() {
            addCriterion("product_img_id is null");
            return (Criteria) this;
        }

        public Criteria andProductImgIdIsNotNull() {
            addCriterion("product_img_id is not null");
            return (Criteria) this;
        }

        public Criteria andProductImgIdEqualTo(String value) {
            addCriterion("product_img_id =", value, "productImgId");
            return (Criteria) this;
        }

        public Criteria andProductImgIdNotEqualTo(String value) {
            addCriterion("product_img_id <>", value, "productImgId");
            return (Criteria) this;
        }

        public Criteria andProductImgIdGreaterThan(String value) {
            addCriterion("product_img_id >", value, "productImgId");
            return (Criteria) this;
        }

        public Criteria andProductImgIdGreaterThanOrEqualTo(String value) {
            addCriterion("product_img_id >=", value, "productImgId");
            return (Criteria) this;
        }

        public Criteria andProductImgIdLessThan(String value) {
            addCriterion("product_img_id <", value, "productImgId");
            return (Criteria) this;
        }

        public Criteria andProductImgIdLessThanOrEqualTo(String value) {
            addCriterion("product_img_id <=", value, "productImgId");
            return (Criteria) this;
        }

        public Criteria andProductImgIdLike(String value) {
            addCriterion("product_img_id like", value, "productImgId");
            return (Criteria) this;
        }

        public Criteria andProductImgIdNotLike(String value) {
            addCriterion("product_img_id not like", value, "productImgId");
            return (Criteria) this;
        }

        public Criteria andProductImgIdIn(List<String> values) {
            addCriterion("product_img_id in", values, "productImgId");
            return (Criteria) this;
        }

        public Criteria andProductImgIdNotIn(List<String> values) {
            addCriterion("product_img_id not in", values, "productImgId");
            return (Criteria) this;
        }

        public Criteria andProductImgIdBetween(String value1, String value2) {
            addCriterion("product_img_id between", value1, value2, "productImgId");
            return (Criteria) this;
        }

        public Criteria andProductImgIdNotBetween(String value1, String value2) {
            addCriterion("product_img_id not between", value1, value2, "productImgId");
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

        public Criteria andFrontCoverFlagIsNull() {
            addCriterion("front_cover_flag is null");
            return (Criteria) this;
        }

        public Criteria andFrontCoverFlagIsNotNull() {
            addCriterion("front_cover_flag is not null");
            return (Criteria) this;
        }

        public Criteria andFrontCoverFlagEqualTo(Boolean value) {
            addCriterion("front_cover_flag =", value, "frontCoverFlag");
            return (Criteria) this;
        }

        public Criteria andFrontCoverFlagNotEqualTo(Boolean value) {
            addCriterion("front_cover_flag <>", value, "frontCoverFlag");
            return (Criteria) this;
        }

        public Criteria andFrontCoverFlagGreaterThan(Boolean value) {
            addCriterion("front_cover_flag >", value, "frontCoverFlag");
            return (Criteria) this;
        }

        public Criteria andFrontCoverFlagGreaterThanOrEqualTo(Boolean value) {
            addCriterion("front_cover_flag >=", value, "frontCoverFlag");
            return (Criteria) this;
        }

        public Criteria andFrontCoverFlagLessThan(Boolean value) {
            addCriterion("front_cover_flag <", value, "frontCoverFlag");
            return (Criteria) this;
        }

        public Criteria andFrontCoverFlagLessThanOrEqualTo(Boolean value) {
            addCriterion("front_cover_flag <=", value, "frontCoverFlag");
            return (Criteria) this;
        }

        public Criteria andFrontCoverFlagIn(List<Boolean> values) {
            addCriterion("front_cover_flag in", values, "frontCoverFlag");
            return (Criteria) this;
        }

        public Criteria andFrontCoverFlagNotIn(List<Boolean> values) {
            addCriterion("front_cover_flag not in", values, "frontCoverFlag");
            return (Criteria) this;
        }

        public Criteria andFrontCoverFlagBetween(Boolean value1, Boolean value2) {
            addCriterion("front_cover_flag between", value1, value2, "frontCoverFlag");
            return (Criteria) this;
        }

        public Criteria andFrontCoverFlagNotBetween(Boolean value1, Boolean value2) {
            addCriterion("front_cover_flag not between", value1, value2, "frontCoverFlag");
            return (Criteria) this;
        }

        public Criteria andSeqNoIsNull() {
            addCriterion("seq_no is null");
            return (Criteria) this;
        }

        public Criteria andSeqNoIsNotNull() {
            addCriterion("seq_no is not null");
            return (Criteria) this;
        }

        public Criteria andSeqNoEqualTo(Integer value) {
            addCriterion("seq_no =", value, "seqNo");
            return (Criteria) this;
        }

        public Criteria andSeqNoNotEqualTo(Integer value) {
            addCriterion("seq_no <>", value, "seqNo");
            return (Criteria) this;
        }

        public Criteria andSeqNoGreaterThan(Integer value) {
            addCriterion("seq_no >", value, "seqNo");
            return (Criteria) this;
        }

        public Criteria andSeqNoGreaterThanOrEqualTo(Integer value) {
            addCriterion("seq_no >=", value, "seqNo");
            return (Criteria) this;
        }

        public Criteria andSeqNoLessThan(Integer value) {
            addCriterion("seq_no <", value, "seqNo");
            return (Criteria) this;
        }

        public Criteria andSeqNoLessThanOrEqualTo(Integer value) {
            addCriterion("seq_no <=", value, "seqNo");
            return (Criteria) this;
        }

        public Criteria andSeqNoIn(List<Integer> values) {
            addCriterion("seq_no in", values, "seqNo");
            return (Criteria) this;
        }

        public Criteria andSeqNoNotIn(List<Integer> values) {
            addCriterion("seq_no not in", values, "seqNo");
            return (Criteria) this;
        }

        public Criteria andSeqNoBetween(Integer value1, Integer value2) {
            addCriterion("seq_no between", value1, value2, "seqNo");
            return (Criteria) this;
        }

        public Criteria andSeqNoNotBetween(Integer value1, Integer value2) {
            addCriterion("seq_no not between", value1, value2, "seqNo");
            return (Criteria) this;
        }

        public Criteria andIsImgIsNull() {
            addCriterion("is_img is null");
            return (Criteria) this;
        }

        public Criteria andIsImgIsNotNull() {
            addCriterion("is_img is not null");
            return (Criteria) this;
        }

        public Criteria andIsImgEqualTo(Boolean value) {
            addCriterion("is_img =", value, "isImg");
            return (Criteria) this;
        }

        public Criteria andIsImgNotEqualTo(Boolean value) {
            addCriterion("is_img <>", value, "isImg");
            return (Criteria) this;
        }

        public Criteria andIsImgGreaterThan(Boolean value) {
            addCriterion("is_img >", value, "isImg");
            return (Criteria) this;
        }

        public Criteria andIsImgGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_img >=", value, "isImg");
            return (Criteria) this;
        }

        public Criteria andIsImgLessThan(Boolean value) {
            addCriterion("is_img <", value, "isImg");
            return (Criteria) this;
        }

        public Criteria andIsImgLessThanOrEqualTo(Boolean value) {
            addCriterion("is_img <=", value, "isImg");
            return (Criteria) this;
        }

        public Criteria andIsImgIn(List<Boolean> values) {
            addCriterion("is_img in", values, "isImg");
            return (Criteria) this;
        }

        public Criteria andIsImgNotIn(List<Boolean> values) {
            addCriterion("is_img not in", values, "isImg");
            return (Criteria) this;
        }

        public Criteria andIsImgBetween(Boolean value1, Boolean value2) {
            addCriterion("is_img between", value1, value2, "isImg");
            return (Criteria) this;
        }

        public Criteria andIsImgNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_img not between", value1, value2, "isImg");
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
     * This class corresponds to the database table productimage
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
     * This class corresponds to the database table productimage
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