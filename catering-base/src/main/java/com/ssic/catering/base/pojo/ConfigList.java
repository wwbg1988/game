package com.ssic.catering.base.pojo;

import java.util.Date;

public class ConfigList {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ctr_config_list.id
     *
     * @mbggenerated Tue Aug 04 14:59:20 CST 2015
     */
    private String id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ctr_config_list.config_id
     *
     * @mbggenerated Tue Aug 04 14:59:20 CST 2015
     */
    private String configId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ctr_config_list.stat
     *
     * @mbggenerated Tue Aug 04 14:59:20 CST 2015
     */
    private Integer stat;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ctr_config_list.last_update_time
     *
     * @mbggenerated Tue Aug 04 14:59:20 CST 2015
     */
    private Date lastUpdateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ctr_config_list.create_time
     *
     * @mbggenerated Tue Aug 04 14:59:20 CST 2015
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ctr_config_list.cafetorium_id
     *
     * @mbggenerated Tue Aug 04 14:59:20 CST 2015
     */
    private String cafetoriumId;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ctr_config_list.id
     *
     * @return the value of t_ctr_config_list.id
     *
     * @mbggenerated Tue Aug 04 14:59:20 CST 2015
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ctr_config_list.id
     *
     * @param id the value for t_ctr_config_list.id
     *
     * @mbggenerated Tue Aug 04 14:59:20 CST 2015
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ctr_config_list.config_id
     *
     * @return the value of t_ctr_config_list.config_id
     *
     * @mbggenerated Tue Aug 04 14:59:20 CST 2015
     */
    public String getConfigId() {
        return configId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ctr_config_list.config_id
     *
     * @param configId the value for t_ctr_config_list.config_id
     *
     * @mbggenerated Tue Aug 04 14:59:20 CST 2015
     */
    public void setConfigId(String configId) {
        this.configId = configId == null ? null : configId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ctr_config_list.stat
     *
     * @return the value of t_ctr_config_list.stat
     *
     * @mbggenerated Tue Aug 04 14:59:20 CST 2015
     */
    public Integer getStat() {
        return stat;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ctr_config_list.stat
     *
     * @param stat the value for t_ctr_config_list.stat
     *
     * @mbggenerated Tue Aug 04 14:59:20 CST 2015
     */
    public void setStat(Integer stat) {
        this.stat = stat;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ctr_config_list.last_update_time
     *
     * @return the value of t_ctr_config_list.last_update_time
     *
     * @mbggenerated Tue Aug 04 14:59:20 CST 2015
     */
    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ctr_config_list.last_update_time
     *
     * @param lastUpdateTime the value for t_ctr_config_list.last_update_time
     *
     * @mbggenerated Tue Aug 04 14:59:20 CST 2015
     */
    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ctr_config_list.create_time
     *
     * @return the value of t_ctr_config_list.create_time
     *
     * @mbggenerated Tue Aug 04 14:59:20 CST 2015
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ctr_config_list.create_time
     *
     * @param createTime the value for t_ctr_config_list.create_time
     *
     * @mbggenerated Tue Aug 04 14:59:20 CST 2015
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ctr_config_list.cafetorium_id
     *
     * @return the value of t_ctr_config_list.cafetorium_id
     *
     * @mbggenerated Tue Aug 04 14:59:20 CST 2015
     */
    public String getCafetoriumId() {
        return cafetoriumId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ctr_config_list.cafetorium_id
     *
     * @param cafetoriumId the value for t_ctr_config_list.cafetorium_id
     *
     * @mbggenerated Tue Aug 04 14:59:20 CST 2015
     */
    public void setCafetoriumId(String cafetoriumId) {
        this.cafetoriumId = cafetoriumId == null ? null : cafetoriumId.trim();
    }
}