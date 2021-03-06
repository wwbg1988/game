package com.ssic.catering.base.pojo;

import java.util.Date;

public class ConfigScore {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ctr_config_score.id
     *
     * @mbggenerated Thu Aug 06 16:36:50 CST 2015
     */
    private String id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ctr_config_score.config_id
     *
     * @mbggenerated Thu Aug 06 16:36:50 CST 2015
     */
    private String configId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ctr_config_score.score
     *
     * @mbggenerated Thu Aug 06 16:36:50 CST 2015
     */
    private String score;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ctr_config_score.cafetorium_id
     *
     * @mbggenerated Thu Aug 06 16:36:50 CST 2015
     */
    private String cafetoriumId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ctr_config_score.create_time
     *
     * @mbggenerated Thu Aug 06 16:36:50 CST 2015
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ctr_config_score.stat
     *
     * @mbggenerated Thu Aug 06 16:36:50 CST 2015
     */
    private Integer stat;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ctr_config_score.id
     *
     * @return the value of t_ctr_config_score.id
     *
     * @mbggenerated Thu Aug 06 16:36:50 CST 2015
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ctr_config_score.id
     *
     * @param id the value for t_ctr_config_score.id
     *
     * @mbggenerated Thu Aug 06 16:36:50 CST 2015
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ctr_config_score.config_id
     *
     * @return the value of t_ctr_config_score.config_id
     *
     * @mbggenerated Thu Aug 06 16:36:50 CST 2015
     */
    public String getConfigId() {
        return configId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ctr_config_score.config_id
     *
     * @param configId the value for t_ctr_config_score.config_id
     *
     * @mbggenerated Thu Aug 06 16:36:50 CST 2015
     */
    public void setConfigId(String configId) {
        this.configId = configId == null ? null : configId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ctr_config_score.score
     *
     * @return the value of t_ctr_config_score.score
     *
     * @mbggenerated Thu Aug 06 16:36:50 CST 2015
     */
    public String getScore() {
        return score;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ctr_config_score.score
     *
     * @param score the value for t_ctr_config_score.score
     *
     * @mbggenerated Thu Aug 06 16:36:50 CST 2015
     */
    public void setScore(String score) {
        this.score = score == null ? null : score.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ctr_config_score.cafetorium_id
     *
     * @return the value of t_ctr_config_score.cafetorium_id
     *
     * @mbggenerated Thu Aug 06 16:36:50 CST 2015
     */
    public String getCafetoriumId() {
        return cafetoriumId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ctr_config_score.cafetorium_id
     *
     * @param cafetoriumId the value for t_ctr_config_score.cafetorium_id
     *
     * @mbggenerated Thu Aug 06 16:36:50 CST 2015
     */
    public void setCafetoriumId(String cafetoriumId) {
        this.cafetoriumId = cafetoriumId == null ? null : cafetoriumId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ctr_config_score.create_time
     *
     * @return the value of t_ctr_config_score.create_time
     *
     * @mbggenerated Thu Aug 06 16:36:50 CST 2015
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ctr_config_score.create_time
     *
     * @param createTime the value for t_ctr_config_score.create_time
     *
     * @mbggenerated Thu Aug 06 16:36:50 CST 2015
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ctr_config_score.stat
     *
     * @return the value of t_ctr_config_score.stat
     *
     * @mbggenerated Thu Aug 06 16:36:50 CST 2015
     */
    public Integer getStat() {
        return stat;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ctr_config_score.stat
     *
     * @param stat the value for t_ctr_config_score.stat
     *
     * @mbggenerated Thu Aug 06 16:36:50 CST 2015
     */
    public void setStat(Integer stat) {
        this.stat = stat;
    }
}