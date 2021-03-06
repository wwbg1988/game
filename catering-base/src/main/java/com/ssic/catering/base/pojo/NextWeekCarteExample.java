package com.ssic.catering.base.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NextWeekCarteExample {

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_ctr_next_week_carte
     *
     * @mbggenerated Mon Aug 17 13:12:49 CST 2015
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_ctr_next_week_carte
     *
     * @mbggenerated Mon Aug 17 13:12:49 CST 2015
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_ctr_next_week_carte
     *
     * @mbggenerated Mon Aug 17 13:12:49 CST 2015
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_next_week_carte
     *
     * @mbggenerated Mon Aug 17 13:12:49 CST 2015
     */
    public NextWeekCarteExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_next_week_carte
     *
     * @mbggenerated Mon Aug 17 13:12:49 CST 2015
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_next_week_carte
     *
     * @mbggenerated Mon Aug 17 13:12:49 CST 2015
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_next_week_carte
     *
     * @mbggenerated Mon Aug 17 13:12:49 CST 2015
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_next_week_carte
     *
     * @mbggenerated Mon Aug 17 13:12:49 CST 2015
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_next_week_carte
     *
     * @mbggenerated Mon Aug 17 13:12:49 CST 2015
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_next_week_carte
     *
     * @mbggenerated Mon Aug 17 13:12:49 CST 2015
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_next_week_carte
     *
     * @mbggenerated Mon Aug 17 13:12:49 CST 2015
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_next_week_carte
     *
     * @mbggenerated Mon Aug 17 13:12:49 CST 2015
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
     * This method corresponds to the database table t_ctr_next_week_carte
     *
     * @mbggenerated Mon Aug 17 13:12:49 CST 2015
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_next_week_carte
     *
     * @mbggenerated Mon Aug 17 13:12:49 CST 2015
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table t_ctr_next_week_carte
     *
     * @mbggenerated Mon Aug 17 13:12:49 CST 2015
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

        public Criteria andDishNameIsNull() {
            addCriterion("dish_name is null");
            return (Criteria) this;
        }

        public Criteria andDishNameIsNotNull() {
            addCriterion("dish_name is not null");
            return (Criteria) this;
        }

        public Criteria andDishNameEqualTo(String value) {
            addCriterion("dish_name =", value, "dishName");
            return (Criteria) this;
        }

        public Criteria andDishNameNotEqualTo(String value) {
            addCriterion("dish_name <>", value, "dishName");
            return (Criteria) this;
        }

        public Criteria andDishNameGreaterThan(String value) {
            addCriterion("dish_name >", value, "dishName");
            return (Criteria) this;
        }

        public Criteria andDishNameGreaterThanOrEqualTo(String value) {
            addCriterion("dish_name >=", value, "dishName");
            return (Criteria) this;
        }

        public Criteria andDishNameLessThan(String value) {
            addCriterion("dish_name <", value, "dishName");
            return (Criteria) this;
        }

        public Criteria andDishNameLessThanOrEqualTo(String value) {
            addCriterion("dish_name <=", value, "dishName");
            return (Criteria) this;
        }

        public Criteria andDishNameLike(String value) {
            addCriterion("dish_name like", value, "dishName");
            return (Criteria) this;
        }

        public Criteria andDishNameNotLike(String value) {
            addCriterion("dish_name not like", value, "dishName");
            return (Criteria) this;
        }

        public Criteria andDishNameIn(List<String> values) {
            addCriterion("dish_name in", values, "dishName");
            return (Criteria) this;
        }

        public Criteria andDishNameNotIn(List<String> values) {
            addCriterion("dish_name not in", values, "dishName");
            return (Criteria) this;
        }

        public Criteria andDishNameBetween(String value1, String value2) {
            addCriterion("dish_name between", value1, value2, "dishName");
            return (Criteria) this;
        }

        public Criteria andDishNameNotBetween(String value1, String value2) {
            addCriterion("dish_name not between", value1, value2, "dishName");
            return (Criteria) this;
        }

        public Criteria andCarteTypeIdIsNull() {
            addCriterion("carte_type_id is null");
            return (Criteria) this;
        }

        public Criteria andCarteTypeIdIsNotNull() {
            addCriterion("carte_type_id is not null");
            return (Criteria) this;
        }

        public Criteria andCarteTypeIdEqualTo(String value) {
            addCriterion("carte_type_id =", value, "carteTypeId");
            return (Criteria) this;
        }

        public Criteria andCarteTypeIdNotEqualTo(String value) {
            addCriterion("carte_type_id <>", value, "carteTypeId");
            return (Criteria) this;
        }

        public Criteria andCarteTypeIdGreaterThan(String value) {
            addCriterion("carte_type_id >", value, "carteTypeId");
            return (Criteria) this;
        }

        public Criteria andCarteTypeIdGreaterThanOrEqualTo(String value) {
            addCriterion("carte_type_id >=", value, "carteTypeId");
            return (Criteria) this;
        }

        public Criteria andCarteTypeIdLessThan(String value) {
            addCriterion("carte_type_id <", value, "carteTypeId");
            return (Criteria) this;
        }

        public Criteria andCarteTypeIdLessThanOrEqualTo(String value) {
            addCriterion("carte_type_id <=", value, "carteTypeId");
            return (Criteria) this;
        }

        public Criteria andCarteTypeIdLike(String value) {
            addCriterion("carte_type_id like", value, "carteTypeId");
            return (Criteria) this;
        }

        public Criteria andCarteTypeIdNotLike(String value) {
            addCriterion("carte_type_id not like", value, "carteTypeId");
            return (Criteria) this;
        }

        public Criteria andCarteTypeIdIn(List<String> values) {
            addCriterion("carte_type_id in", values, "carteTypeId");
            return (Criteria) this;
        }

        public Criteria andCarteTypeIdNotIn(List<String> values) {
            addCriterion("carte_type_id not in", values, "carteTypeId");
            return (Criteria) this;
        }

        public Criteria andCarteTypeIdBetween(String value1, String value2) {
            addCriterion("carte_type_id between", value1, value2, "carteTypeId");
            return (Criteria) this;
        }

        public Criteria andCarteTypeIdNotBetween(String value1, String value2) {
            addCriterion("carte_type_id not between", value1, value2, "carteTypeId");
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

        public Criteria andCarteWeekIsNull() {
            addCriterion("carte_week is null");
            return (Criteria) this;
        }

        public Criteria andCarteWeekIsNotNull() {
            addCriterion("carte_week is not null");
            return (Criteria) this;
        }

        public Criteria andCarteWeekEqualTo(String value) {
            addCriterion("carte_week =", value, "carteWeek");
            return (Criteria) this;
        }

        public Criteria andCarteWeekNotEqualTo(String value) {
            addCriterion("carte_week <>", value, "carteWeek");
            return (Criteria) this;
        }

        public Criteria andCarteWeekGreaterThan(String value) {
            addCriterion("carte_week >", value, "carteWeek");
            return (Criteria) this;
        }

        public Criteria andCarteWeekGreaterThanOrEqualTo(String value) {
            addCriterion("carte_week >=", value, "carteWeek");
            return (Criteria) this;
        }

        public Criteria andCarteWeekLessThan(String value) {
            addCriterion("carte_week <", value, "carteWeek");
            return (Criteria) this;
        }

        public Criteria andCarteWeekLessThanOrEqualTo(String value) {
            addCriterion("carte_week <=", value, "carteWeek");
            return (Criteria) this;
        }

        public Criteria andCarteWeekLike(String value) {
            addCriterion("carte_week like", value, "carteWeek");
            return (Criteria) this;
        }

        public Criteria andCarteWeekNotLike(String value) {
            addCriterion("carte_week not like", value, "carteWeek");
            return (Criteria) this;
        }

        public Criteria andCarteWeekIn(List<String> values) {
            addCriterion("carte_week in", values, "carteWeek");
            return (Criteria) this;
        }

        public Criteria andCarteWeekNotIn(List<String> values) {
            addCriterion("carte_week not in", values, "carteWeek");
            return (Criteria) this;
        }

        public Criteria andCarteWeekBetween(String value1, String value2) {
            addCriterion("carte_week between", value1, value2, "carteWeek");
            return (Criteria) this;
        }

        public Criteria andCarteWeekNotBetween(String value1, String value2) {
            addCriterion("carte_week not between", value1, value2, "carteWeek");
            return (Criteria) this;
        }

        public Criteria andCarteWeekDescIsNull() {
            addCriterion("carte_week_desc is null");
            return (Criteria) this;
        }

        public Criteria andCarteWeekDescIsNotNull() {
            addCriterion("carte_week_desc is not null");
            return (Criteria) this;
        }

        public Criteria andCarteWeekDescEqualTo(String value) {
            addCriterion("carte_week_desc =", value, "carteWeekDesc");
            return (Criteria) this;
        }

        public Criteria andCarteWeekDescNotEqualTo(String value) {
            addCriterion("carte_week_desc <>", value, "carteWeekDesc");
            return (Criteria) this;
        }

        public Criteria andCarteWeekDescGreaterThan(String value) {
            addCriterion("carte_week_desc >", value, "carteWeekDesc");
            return (Criteria) this;
        }

        public Criteria andCarteWeekDescGreaterThanOrEqualTo(String value) {
            addCriterion("carte_week_desc >=", value, "carteWeekDesc");
            return (Criteria) this;
        }

        public Criteria andCarteWeekDescLessThan(String value) {
            addCriterion("carte_week_desc <", value, "carteWeekDesc");
            return (Criteria) this;
        }

        public Criteria andCarteWeekDescLessThanOrEqualTo(String value) {
            addCriterion("carte_week_desc <=", value, "carteWeekDesc");
            return (Criteria) this;
        }

        public Criteria andCarteWeekDescLike(String value) {
            addCriterion("carte_week_desc like", value, "carteWeekDesc");
            return (Criteria) this;
        }

        public Criteria andCarteWeekDescNotLike(String value) {
            addCriterion("carte_week_desc not like", value, "carteWeekDesc");
            return (Criteria) this;
        }

        public Criteria andCarteWeekDescIn(List<String> values) {
            addCriterion("carte_week_desc in", values, "carteWeekDesc");
            return (Criteria) this;
        }

        public Criteria andCarteWeekDescNotIn(List<String> values) {
            addCriterion("carte_week_desc not in", values, "carteWeekDesc");
            return (Criteria) this;
        }

        public Criteria andCarteWeekDescBetween(String value1, String value2) {
            addCriterion("carte_week_desc between", value1, value2, "carteWeekDesc");
            return (Criteria) this;
        }

        public Criteria andCarteWeekDescNotBetween(String value1, String value2) {
            addCriterion("carte_week_desc not between", value1, value2, "carteWeekDesc");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeIsNull() {
            addCriterion("last_update_time is null");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeIsNotNull() {
            addCriterion("last_update_time is not null");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeEqualTo(Date value) {
            addCriterion("last_update_time =", value, "lastUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeNotEqualTo(Date value) {
            addCriterion("last_update_time <>", value, "lastUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeGreaterThan(Date value) {
            addCriterion("last_update_time >", value, "lastUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("last_update_time >=", value, "lastUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeLessThan(Date value) {
            addCriterion("last_update_time <", value, "lastUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("last_update_time <=", value, "lastUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeIn(List<Date> values) {
            addCriterion("last_update_time in", values, "lastUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeNotIn(List<Date> values) {
            addCriterion("last_update_time not in", values, "lastUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("last_update_time between", value1, value2, "lastUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("last_update_time not between", value1, value2, "lastUpdateTime");
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

        public Criteria andOpenIdIsNull() {
            addCriterion("open_id is null");
            return (Criteria) this;
        }

        public Criteria andOpenIdIsNotNull() {
            addCriterion("open_id is not null");
            return (Criteria) this;
        }

        public Criteria andOpenIdEqualTo(String value) {
            addCriterion("open_id =", value, "openId");
            return (Criteria) this;
        }

        public Criteria andOpenIdNotEqualTo(String value) {
            addCriterion("open_id <>", value, "openId");
            return (Criteria) this;
        }

        public Criteria andOpenIdGreaterThan(String value) {
            addCriterion("open_id >", value, "openId");
            return (Criteria) this;
        }

        public Criteria andOpenIdGreaterThanOrEqualTo(String value) {
            addCriterion("open_id >=", value, "openId");
            return (Criteria) this;
        }

        public Criteria andOpenIdLessThan(String value) {
            addCriterion("open_id <", value, "openId");
            return (Criteria) this;
        }

        public Criteria andOpenIdLessThanOrEqualTo(String value) {
            addCriterion("open_id <=", value, "openId");
            return (Criteria) this;
        }

        public Criteria andOpenIdLike(String value) {
            addCriterion("open_id like", value, "openId");
            return (Criteria) this;
        }

        public Criteria andOpenIdNotLike(String value) {
            addCriterion("open_id not like", value, "openId");
            return (Criteria) this;
        }

        public Criteria andOpenIdIn(List<String> values) {
            addCriterion("open_id in", values, "openId");
            return (Criteria) this;
        }

        public Criteria andOpenIdNotIn(List<String> values) {
            addCriterion("open_id not in", values, "openId");
            return (Criteria) this;
        }

        public Criteria andOpenIdBetween(String value1, String value2) {
            addCriterion("open_id between", value1, value2, "openId");
            return (Criteria) this;
        }

        public Criteria andOpenIdNotBetween(String value1, String value2) {
            addCriterion("open_id not between", value1, value2, "openId");
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
     * This class corresponds to the database table t_ctr_next_week_carte
     *
     * @mbggenerated do_not_delete_during_merge Mon Aug 17 13:12:49 CST 2015
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table t_ctr_next_week_carte
     *
     * @mbggenerated Mon Aug 17 13:12:49 CST 2015
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