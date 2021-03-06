package com.ssic.catering.base.pojo;

import java.util.Date;

public class AvgScoreWarning {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ctr_avg_score_warning.id
     *
     * @mbggenerated Tue Aug 11 10:42:47 CST 2015
     */
    private String id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ctr_avg_score_warning.avg_score
     *
     * @mbggenerated Tue Aug 11 10:42:47 CST 2015
     */
    private Float avgScore;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ctr_avg_score_warning.cafetorium_id
     *
     * @mbggenerated Tue Aug 11 10:42:47 CST 2015
     */
    private String cafetoriumId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ctr_avg_score_warning.create_time
     *
     * @mbggenerated Tue Aug 11 10:42:47 CST 2015
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ctr_avg_score_warning.stat
     *
     * @mbggenerated Tue Aug 11 10:42:47 CST 2015
     */
    private Integer stat;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ctr_avg_score_warning.user_id
     *
     * @mbggenerated Tue Aug 11 10:42:47 CST 2015
     */
    private String userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ctr_avg_score_warning.message
     *
     * @mbggenerated Tue Aug 11 10:42:47 CST 2015
     */
    private String message;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ctr_avg_score_warning.parent_id
     *
     * @mbggenerated Tue Aug 11 10:42:47 CST 2015
     */
    private String parentId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ctr_avg_score_warning.address_name
     *
     * @mbggenerated Tue Aug 11 10:42:47 CST 2015
     */
    private String addressName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ctr_avg_score_warning.qjy_account
     *
     * @mbggenerated Tue Aug 11 10:42:47 CST 2015
     */
    private String qjyAccount;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ctr_avg_score_warning.id
     *
     * @return the value of t_ctr_avg_score_warning.id
     *
     * @mbggenerated Tue Aug 11 10:42:47 CST 2015
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ctr_avg_score_warning.id
     *
     * @param id the value for t_ctr_avg_score_warning.id
     *
     * @mbggenerated Tue Aug 11 10:42:47 CST 2015
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ctr_avg_score_warning.avg_score
     *
     * @return the value of t_ctr_avg_score_warning.avg_score
     *
     * @mbggenerated Tue Aug 11 10:42:47 CST 2015
     */
    public Float getAvgScore() {
        return avgScore;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ctr_avg_score_warning.avg_score
     *
     * @param avgScore the value for t_ctr_avg_score_warning.avg_score
     *
     * @mbggenerated Tue Aug 11 10:42:47 CST 2015
     */
    public void setAvgScore(Float avgScore) {
        this.avgScore = avgScore;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ctr_avg_score_warning.cafetorium_id
     *
     * @return the value of t_ctr_avg_score_warning.cafetorium_id
     *
     * @mbggenerated Tue Aug 11 10:42:47 CST 2015
     */
    public String getCafetoriumId() {
        return cafetoriumId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ctr_avg_score_warning.cafetorium_id
     *
     * @param cafetoriumId the value for t_ctr_avg_score_warning.cafetorium_id
     *
     * @mbggenerated Tue Aug 11 10:42:47 CST 2015
     */
    public void setCafetoriumId(String cafetoriumId) {
        this.cafetoriumId = cafetoriumId == null ? null : cafetoriumId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ctr_avg_score_warning.create_time
     *
     * @return the value of t_ctr_avg_score_warning.create_time
     *
     * @mbggenerated Tue Aug 11 10:42:47 CST 2015
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ctr_avg_score_warning.create_time
     *
     * @param createTime the value for t_ctr_avg_score_warning.create_time
     *
     * @mbggenerated Tue Aug 11 10:42:47 CST 2015
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ctr_avg_score_warning.stat
     *
     * @return the value of t_ctr_avg_score_warning.stat
     *
     * @mbggenerated Tue Aug 11 10:42:47 CST 2015
     */
    public Integer getStat() {
        return stat;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ctr_avg_score_warning.stat
     *
     * @param stat the value for t_ctr_avg_score_warning.stat
     *
     * @mbggenerated Tue Aug 11 10:42:47 CST 2015
     */
    public void setStat(Integer stat) {
        this.stat = stat;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ctr_avg_score_warning.user_id
     *
     * @return the value of t_ctr_avg_score_warning.user_id
     *
     * @mbggenerated Tue Aug 11 10:42:47 CST 2015
     */
    public String getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ctr_avg_score_warning.user_id
     *
     * @param userId the value for t_ctr_avg_score_warning.user_id
     *
     * @mbggenerated Tue Aug 11 10:42:47 CST 2015
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ctr_avg_score_warning.message
     *
     * @return the value of t_ctr_avg_score_warning.message
     *
     * @mbggenerated Tue Aug 11 10:42:47 CST 2015
     */
    public String getMessage() {
        return message;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ctr_avg_score_warning.message
     *
     * @param message the value for t_ctr_avg_score_warning.message
     *
     * @mbggenerated Tue Aug 11 10:42:47 CST 2015
     */
    public void setMessage(String message) {
        this.message = message == null ? null : message.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ctr_avg_score_warning.parent_id
     *
     * @return the value of t_ctr_avg_score_warning.parent_id
     *
     * @mbggenerated Tue Aug 11 10:42:47 CST 2015
     */
    public String getParentId() {
        return parentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ctr_avg_score_warning.parent_id
     *
     * @param parentId the value for t_ctr_avg_score_warning.parent_id
     *
     * @mbggenerated Tue Aug 11 10:42:47 CST 2015
     */
    public void setParentId(String parentId) {
        this.parentId = parentId == null ? null : parentId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ctr_avg_score_warning.address_name
     *
     * @return the value of t_ctr_avg_score_warning.address_name
     *
     * @mbggenerated Tue Aug 11 10:42:47 CST 2015
     */
    public String getAddressName() {
        return addressName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ctr_avg_score_warning.address_name
     *
     * @param addressName the value for t_ctr_avg_score_warning.address_name
     *
     * @mbggenerated Tue Aug 11 10:42:47 CST 2015
     */
    public void setAddressName(String addressName) {
        this.addressName = addressName == null ? null : addressName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ctr_avg_score_warning.qjy_account
     *
     * @return the value of t_ctr_avg_score_warning.qjy_account
     *
     * @mbggenerated Tue Aug 11 10:42:47 CST 2015
     */
    public String getQjyAccount() {
        return qjyAccount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ctr_avg_score_warning.qjy_account
     *
     * @param qjyAccount the value for t_ctr_avg_score_warning.qjy_account
     *
     * @mbggenerated Tue Aug 11 10:42:47 CST 2015
     */
    public void setQjyAccount(String qjyAccount) {
        this.qjyAccount = qjyAccount == null ? null : qjyAccount.trim();
    }
}