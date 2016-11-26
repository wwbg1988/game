package com.ssic.catering.base.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SensitiveWarningReportExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_ctr_sensitive_warning_report
     *
     * @mbggenerated Wed Aug 12 09:43:39 CST 2015
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_ctr_sensitive_warning_report
     *
     * @mbggenerated Wed Aug 12 09:43:39 CST 2015
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_ctr_sensitive_warning_report
     *
     * @mbggenerated Wed Aug 12 09:43:39 CST 2015
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_sensitive_warning_report
     *
     * @mbggenerated Wed Aug 12 09:43:39 CST 2015
     */
    public SensitiveWarningReportExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_sensitive_warning_report
     *
     * @mbggenerated Wed Aug 12 09:43:39 CST 2015
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_sensitive_warning_report
     *
     * @mbggenerated Wed Aug 12 09:43:39 CST 2015
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_sensitive_warning_report
     *
     * @mbggenerated Wed Aug 12 09:43:39 CST 2015
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_sensitive_warning_report
     *
     * @mbggenerated Wed Aug 12 09:43:39 CST 2015
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_sensitive_warning_report
     *
     * @mbggenerated Wed Aug 12 09:43:39 CST 2015
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_sensitive_warning_report
     *
     * @mbggenerated Wed Aug 12 09:43:39 CST 2015
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_sensitive_warning_report
     *
     * @mbggenerated Wed Aug 12 09:43:39 CST 2015
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_sensitive_warning_report
     *
     * @mbggenerated Wed Aug 12 09:43:39 CST 2015
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
     * This method corresponds to the database table t_ctr_sensitive_warning_report
     *
     * @mbggenerated Wed Aug 12 09:43:39 CST 2015
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_sensitive_warning_report
     *
     * @mbggenerated Wed Aug 12 09:43:39 CST 2015
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table t_ctr_sensitive_warning_report
     *
     * @mbggenerated Wed Aug 12 09:43:39 CST 2015
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

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(String value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(String value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(String value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(String value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(String value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(String value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLike(String value) {
            addCriterion("id like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotLike(String value) {
            addCriterion("id not like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<String> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<String> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(String value1, String value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(String value1, String value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andCafetoriumIdIsNull() {
            addCriterion("cafetorium_id is null");
            return (Criteria) this;
        }

        public Criteria andCafetoriumIdIsNotNull() {
            addCriterion("cafetorium_id is not null");
            return (Criteria) this;
        }

        public Criteria andCafetoriumIdEqualTo(String value) {
            addCriterion("cafetorium_id =", value, "cafetoriumId");
            return (Criteria) this;
        }

        public Criteria andCafetoriumIdNotEqualTo(String value) {
            addCriterion("cafetorium_id <>", value, "cafetoriumId");
            return (Criteria) this;
        }

        public Criteria andCafetoriumIdGreaterThan(String value) {
            addCriterion("cafetorium_id >", value, "cafetoriumId");
            return (Criteria) this;
        }

        public Criteria andCafetoriumIdGreaterThanOrEqualTo(String value) {
            addCriterion("cafetorium_id >=", value, "cafetoriumId");
            return (Criteria) this;
        }

        public Criteria andCafetoriumIdLessThan(String value) {
            addCriterion("cafetorium_id <", value, "cafetoriumId");
            return (Criteria) this;
        }

        public Criteria andCafetoriumIdLessThanOrEqualTo(String value) {
            addCriterion("cafetorium_id <=", value, "cafetoriumId");
            return (Criteria) this;
        }

        public Criteria andCafetoriumIdLike(String value) {
            addCriterion("cafetorium_id like", value, "cafetoriumId");
            return (Criteria) this;
        }

        public Criteria andCafetoriumIdNotLike(String value) {
            addCriterion("cafetorium_id not like", value, "cafetoriumId");
            return (Criteria) this;
        }

        public Criteria andCafetoriumIdIn(List<String> values) {
            addCriterion("cafetorium_id in", values, "cafetoriumId");
            return (Criteria) this;
        }

        public Criteria andCafetoriumIdNotIn(List<String> values) {
            addCriterion("cafetorium_id not in", values, "cafetoriumId");
            return (Criteria) this;
        }

        public Criteria andCafetoriumIdBetween(String value1, String value2) {
            addCriterion("cafetorium_id between", value1, value2, "cafetoriumId");
            return (Criteria) this;
        }

        public Criteria andCafetoriumIdNotBetween(String value1, String value2) {
            addCriterion("cafetorium_id not between", value1, value2, "cafetoriumId");
            return (Criteria) this;
        }

        public Criteria andSensitiveIdIsNull() {
            addCriterion("sensitive_id is null");
            return (Criteria) this;
        }

        public Criteria andSensitiveIdIsNotNull() {
            addCriterion("sensitive_id is not null");
            return (Criteria) this;
        }

        public Criteria andSensitiveIdEqualTo(String value) {
            addCriterion("sensitive_id =", value, "sensitiveId");
            return (Criteria) this;
        }

        public Criteria andSensitiveIdNotEqualTo(String value) {
            addCriterion("sensitive_id <>", value, "sensitiveId");
            return (Criteria) this;
        }

        public Criteria andSensitiveIdGreaterThan(String value) {
            addCriterion("sensitive_id >", value, "sensitiveId");
            return (Criteria) this;
        }

        public Criteria andSensitiveIdGreaterThanOrEqualTo(String value) {
            addCriterion("sensitive_id >=", value, "sensitiveId");
            return (Criteria) this;
        }

        public Criteria andSensitiveIdLessThan(String value) {
            addCriterion("sensitive_id <", value, "sensitiveId");
            return (Criteria) this;
        }

        public Criteria andSensitiveIdLessThanOrEqualTo(String value) {
            addCriterion("sensitive_id <=", value, "sensitiveId");
            return (Criteria) this;
        }

        public Criteria andSensitiveIdLike(String value) {
            addCriterion("sensitive_id like", value, "sensitiveId");
            return (Criteria) this;
        }

        public Criteria andSensitiveIdNotLike(String value) {
            addCriterion("sensitive_id not like", value, "sensitiveId");
            return (Criteria) this;
        }

        public Criteria andSensitiveIdIn(List<String> values) {
            addCriterion("sensitive_id in", values, "sensitiveId");
            return (Criteria) this;
        }

        public Criteria andSensitiveIdNotIn(List<String> values) {
            addCriterion("sensitive_id not in", values, "sensitiveId");
            return (Criteria) this;
        }

        public Criteria andSensitiveIdBetween(String value1, String value2) {
            addCriterion("sensitive_id between", value1, value2, "sensitiveId");
            return (Criteria) this;
        }

        public Criteria andSensitiveIdNotBetween(String value1, String value2) {
            addCriterion("sensitive_id not between", value1, value2, "sensitiveId");
            return (Criteria) this;
        }

        public Criteria andSensitiveNameIsNull() {
            addCriterion("sensitive_name is null");
            return (Criteria) this;
        }

        public Criteria andSensitiveNameIsNotNull() {
            addCriterion("sensitive_name is not null");
            return (Criteria) this;
        }

        public Criteria andSensitiveNameEqualTo(String value) {
            addCriterion("sensitive_name =", value, "sensitiveName");
            return (Criteria) this;
        }

        public Criteria andSensitiveNameNotEqualTo(String value) {
            addCriterion("sensitive_name <>", value, "sensitiveName");
            return (Criteria) this;
        }

        public Criteria andSensitiveNameGreaterThan(String value) {
            addCriterion("sensitive_name >", value, "sensitiveName");
            return (Criteria) this;
        }

        public Criteria andSensitiveNameGreaterThanOrEqualTo(String value) {
            addCriterion("sensitive_name >=", value, "sensitiveName");
            return (Criteria) this;
        }

        public Criteria andSensitiveNameLessThan(String value) {
            addCriterion("sensitive_name <", value, "sensitiveName");
            return (Criteria) this;
        }

        public Criteria andSensitiveNameLessThanOrEqualTo(String value) {
            addCriterion("sensitive_name <=", value, "sensitiveName");
            return (Criteria) this;
        }

        public Criteria andSensitiveNameLike(String value) {
            addCriterion("sensitive_name like", value, "sensitiveName");
            return (Criteria) this;
        }

        public Criteria andSensitiveNameNotLike(String value) {
            addCriterion("sensitive_name not like", value, "sensitiveName");
            return (Criteria) this;
        }

        public Criteria andSensitiveNameIn(List<String> values) {
            addCriterion("sensitive_name in", values, "sensitiveName");
            return (Criteria) this;
        }

        public Criteria andSensitiveNameNotIn(List<String> values) {
            addCriterion("sensitive_name not in", values, "sensitiveName");
            return (Criteria) this;
        }

        public Criteria andSensitiveNameBetween(String value1, String value2) {
            addCriterion("sensitive_name between", value1, value2, "sensitiveName");
            return (Criteria) this;
        }

        public Criteria andSensitiveNameNotBetween(String value1, String value2) {
            addCriterion("sensitive_name not between", value1, value2, "sensitiveName");
            return (Criteria) this;
        }

        public Criteria andCountIsNull() {
            addCriterion("count is null");
            return (Criteria) this;
        }

        public Criteria andCountIsNotNull() {
            addCriterion("count is not null");
            return (Criteria) this;
        }

        public Criteria andCountEqualTo(Integer value) {
            addCriterion("count =", value, "count");
            return (Criteria) this;
        }

        public Criteria andCountNotEqualTo(Integer value) {
            addCriterion("count <>", value, "count");
            return (Criteria) this;
        }

        public Criteria andCountGreaterThan(Integer value) {
            addCriterion("count >", value, "count");
            return (Criteria) this;
        }

        public Criteria andCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("count >=", value, "count");
            return (Criteria) this;
        }

        public Criteria andCountLessThan(Integer value) {
            addCriterion("count <", value, "count");
            return (Criteria) this;
        }

        public Criteria andCountLessThanOrEqualTo(Integer value) {
            addCriterion("count <=", value, "count");
            return (Criteria) this;
        }

        public Criteria andCountIn(List<Integer> values) {
            addCriterion("count in", values, "count");
            return (Criteria) this;
        }

        public Criteria andCountNotIn(List<Integer> values) {
            addCriterion("count not in", values, "count");
            return (Criteria) this;
        }

        public Criteria andCountBetween(Integer value1, Integer value2) {
            addCriterion("count between", value1, value2, "count");
            return (Criteria) this;
        }

        public Criteria andCountNotBetween(Integer value1, Integer value2) {
            addCriterion("count not between", value1, value2, "count");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andWarningIsNull() {
            addCriterion("warning is null");
            return (Criteria) this;
        }

        public Criteria andWarningIsNotNull() {
            addCriterion("warning is not null");
            return (Criteria) this;
        }

        public Criteria andWarningEqualTo(Integer value) {
            addCriterion("warning =", value, "warning");
            return (Criteria) this;
        }

        public Criteria andWarningNotEqualTo(Integer value) {
            addCriterion("warning <>", value, "warning");
            return (Criteria) this;
        }

        public Criteria andWarningGreaterThan(Integer value) {
            addCriterion("warning >", value, "warning");
            return (Criteria) this;
        }

        public Criteria andWarningGreaterThanOrEqualTo(Integer value) {
            addCriterion("warning >=", value, "warning");
            return (Criteria) this;
        }

        public Criteria andWarningLessThan(Integer value) {
            addCriterion("warning <", value, "warning");
            return (Criteria) this;
        }

        public Criteria andWarningLessThanOrEqualTo(Integer value) {
            addCriterion("warning <=", value, "warning");
            return (Criteria) this;
        }

        public Criteria andWarningIn(List<Integer> values) {
            addCriterion("warning in", values, "warning");
            return (Criteria) this;
        }

        public Criteria andWarningNotIn(List<Integer> values) {
            addCriterion("warning not in", values, "warning");
            return (Criteria) this;
        }

        public Criteria andWarningBetween(Integer value1, Integer value2) {
            addCriterion("warning between", value1, value2, "warning");
            return (Criteria) this;
        }

        public Criteria andWarningNotBetween(Integer value1, Integer value2) {
            addCriterion("warning not between", value1, value2, "warning");
            return (Criteria) this;
        }

        public Criteria andMessageIsNull() {
            addCriterion("message is null");
            return (Criteria) this;
        }

        public Criteria andMessageIsNotNull() {
            addCriterion("message is not null");
            return (Criteria) this;
        }

        public Criteria andMessageEqualTo(String value) {
            addCriterion("message =", value, "message");
            return (Criteria) this;
        }

        public Criteria andMessageNotEqualTo(String value) {
            addCriterion("message <>", value, "message");
            return (Criteria) this;
        }

        public Criteria andMessageGreaterThan(String value) {
            addCriterion("message >", value, "message");
            return (Criteria) this;
        }

        public Criteria andMessageGreaterThanOrEqualTo(String value) {
            addCriterion("message >=", value, "message");
            return (Criteria) this;
        }

        public Criteria andMessageLessThan(String value) {
            addCriterion("message <", value, "message");
            return (Criteria) this;
        }

        public Criteria andMessageLessThanOrEqualTo(String value) {
            addCriterion("message <=", value, "message");
            return (Criteria) this;
        }

        public Criteria andMessageLike(String value) {
            addCriterion("message like", value, "message");
            return (Criteria) this;
        }

        public Criteria andMessageNotLike(String value) {
            addCriterion("message not like", value, "message");
            return (Criteria) this;
        }

        public Criteria andMessageIn(List<String> values) {
            addCriterion("message in", values, "message");
            return (Criteria) this;
        }

        public Criteria andMessageNotIn(List<String> values) {
            addCriterion("message not in", values, "message");
            return (Criteria) this;
        }

        public Criteria andMessageBetween(String value1, String value2) {
            addCriterion("message between", value1, value2, "message");
            return (Criteria) this;
        }

        public Criteria andMessageNotBetween(String value1, String value2) {
            addCriterion("message not between", value1, value2, "message");
            return (Criteria) this;
        }

        public Criteria andAddressNameIsNull() {
            addCriterion("address_name is null");
            return (Criteria) this;
        }

        public Criteria andAddressNameIsNotNull() {
            addCriterion("address_name is not null");
            return (Criteria) this;
        }

        public Criteria andAddressNameEqualTo(String value) {
            addCriterion("address_name =", value, "addressName");
            return (Criteria) this;
        }

        public Criteria andAddressNameNotEqualTo(String value) {
            addCriterion("address_name <>", value, "addressName");
            return (Criteria) this;
        }

        public Criteria andAddressNameGreaterThan(String value) {
            addCriterion("address_name >", value, "addressName");
            return (Criteria) this;
        }

        public Criteria andAddressNameGreaterThanOrEqualTo(String value) {
            addCriterion("address_name >=", value, "addressName");
            return (Criteria) this;
        }

        public Criteria andAddressNameLessThan(String value) {
            addCriterion("address_name <", value, "addressName");
            return (Criteria) this;
        }

        public Criteria andAddressNameLessThanOrEqualTo(String value) {
            addCriterion("address_name <=", value, "addressName");
            return (Criteria) this;
        }

        public Criteria andAddressNameLike(String value) {
            addCriterion("address_name like", value, "addressName");
            return (Criteria) this;
        }

        public Criteria andAddressNameNotLike(String value) {
            addCriterion("address_name not like", value, "addressName");
            return (Criteria) this;
        }

        public Criteria andAddressNameIn(List<String> values) {
            addCriterion("address_name in", values, "addressName");
            return (Criteria) this;
        }

        public Criteria andAddressNameNotIn(List<String> values) {
            addCriterion("address_name not in", values, "addressName");
            return (Criteria) this;
        }

        public Criteria andAddressNameBetween(String value1, String value2) {
            addCriterion("address_name between", value1, value2, "addressName");
            return (Criteria) this;
        }

        public Criteria andAddressNameNotBetween(String value1, String value2) {
            addCriterion("address_name not between", value1, value2, "addressName");
            return (Criteria) this;
        }

        public Criteria andWarningproportionIsNull() {
            addCriterion("warningproportion is null");
            return (Criteria) this;
        }

        public Criteria andWarningproportionIsNotNull() {
            addCriterion("warningproportion is not null");
            return (Criteria) this;
        }

        public Criteria andWarningproportionEqualTo(Float value) {
            addCriterion("warningproportion =", value, "warningproportion");
            return (Criteria) this;
        }

        public Criteria andWarningproportionNotEqualTo(Float value) {
            addCriterion("warningproportion <>", value, "warningproportion");
            return (Criteria) this;
        }

        public Criteria andWarningproportionGreaterThan(Float value) {
            addCriterion("warningproportion >", value, "warningproportion");
            return (Criteria) this;
        }

        public Criteria andWarningproportionGreaterThanOrEqualTo(Float value) {
            addCriterion("warningproportion >=", value, "warningproportion");
            return (Criteria) this;
        }

        public Criteria andWarningproportionLessThan(Float value) {
            addCriterion("warningproportion <", value, "warningproportion");
            return (Criteria) this;
        }

        public Criteria andWarningproportionLessThanOrEqualTo(Float value) {
            addCriterion("warningproportion <=", value, "warningproportion");
            return (Criteria) this;
        }

        public Criteria andWarningproportionIn(List<Float> values) {
            addCriterion("warningproportion in", values, "warningproportion");
            return (Criteria) this;
        }

        public Criteria andWarningproportionNotIn(List<Float> values) {
            addCriterion("warningproportion not in", values, "warningproportion");
            return (Criteria) this;
        }

        public Criteria andWarningproportionBetween(Float value1, Float value2) {
            addCriterion("warningproportion between", value1, value2, "warningproportion");
            return (Criteria) this;
        }

        public Criteria andWarningproportionNotBetween(Float value1, Float value2) {
            addCriterion("warningproportion not between", value1, value2, "warningproportion");
            return (Criteria) this;
        }

        public Criteria andAddressIdIsNull() {
            addCriterion("address_id is null");
            return (Criteria) this;
        }

        public Criteria andAddressIdIsNotNull() {
            addCriterion("address_id is not null");
            return (Criteria) this;
        }

        public Criteria andAddressIdEqualTo(String value) {
            addCriterion("address_id =", value, "addressId");
            return (Criteria) this;
        }

        public Criteria andAddressIdNotEqualTo(String value) {
            addCriterion("address_id <>", value, "addressId");
            return (Criteria) this;
        }

        public Criteria andAddressIdGreaterThan(String value) {
            addCriterion("address_id >", value, "addressId");
            return (Criteria) this;
        }

        public Criteria andAddressIdGreaterThanOrEqualTo(String value) {
            addCriterion("address_id >=", value, "addressId");
            return (Criteria) this;
        }

        public Criteria andAddressIdLessThan(String value) {
            addCriterion("address_id <", value, "addressId");
            return (Criteria) this;
        }

        public Criteria andAddressIdLessThanOrEqualTo(String value) {
            addCriterion("address_id <=", value, "addressId");
            return (Criteria) this;
        }

        public Criteria andAddressIdLike(String value) {
            addCriterion("address_id like", value, "addressId");
            return (Criteria) this;
        }

        public Criteria andAddressIdNotLike(String value) {
            addCriterion("address_id not like", value, "addressId");
            return (Criteria) this;
        }

        public Criteria andAddressIdIn(List<String> values) {
            addCriterion("address_id in", values, "addressId");
            return (Criteria) this;
        }

        public Criteria andAddressIdNotIn(List<String> values) {
            addCriterion("address_id not in", values, "addressId");
            return (Criteria) this;
        }

        public Criteria andAddressIdBetween(String value1, String value2) {
            addCriterion("address_id between", value1, value2, "addressId");
            return (Criteria) this;
        }

        public Criteria andAddressIdNotBetween(String value1, String value2) {
            addCriterion("address_id not between", value1, value2, "addressId");
            return (Criteria) this;
        }

        public Criteria andStatIsNull() {
            addCriterion("stat is null");
            return (Criteria) this;
        }

        public Criteria andStatIsNotNull() {
            addCriterion("stat is not null");
            return (Criteria) this;
        }

        public Criteria andStatEqualTo(Integer value) {
            addCriterion("stat =", value, "stat");
            return (Criteria) this;
        }

        public Criteria andStatNotEqualTo(Integer value) {
            addCriterion("stat <>", value, "stat");
            return (Criteria) this;
        }

        public Criteria andStatGreaterThan(Integer value) {
            addCriterion("stat >", value, "stat");
            return (Criteria) this;
        }

        public Criteria andStatGreaterThanOrEqualTo(Integer value) {
            addCriterion("stat >=", value, "stat");
            return (Criteria) this;
        }

        public Criteria andStatLessThan(Integer value) {
            addCriterion("stat <", value, "stat");
            return (Criteria) this;
        }

        public Criteria andStatLessThanOrEqualTo(Integer value) {
            addCriterion("stat <=", value, "stat");
            return (Criteria) this;
        }

        public Criteria andStatIn(List<Integer> values) {
            addCriterion("stat in", values, "stat");
            return (Criteria) this;
        }

        public Criteria andStatNotIn(List<Integer> values) {
            addCriterion("stat not in", values, "stat");
            return (Criteria) this;
        }

        public Criteria andStatBetween(Integer value1, Integer value2) {
            addCriterion("stat between", value1, value2, "stat");
            return (Criteria) this;
        }

        public Criteria andStatNotBetween(Integer value1, Integer value2) {
            addCriterion("stat not between", value1, value2, "stat");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table t_ctr_sensitive_warning_report
     *
     * @mbggenerated do_not_delete_during_merge Wed Aug 12 09:43:39 CST 2015
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table t_ctr_sensitive_warning_report
     *
     * @mbggenerated Wed Aug 12 09:43:39 CST 2015
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