package com.ssic.catering.base.pojo;

import java.util.Date;

public class Sensitive {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ctr_sensitive.id
     *
     * @mbggenerated Fri Oct 30 09:41:31 CST 2015
     */
    private String id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ctr_sensitive.sensitive_name
     *
     * @mbggenerated Fri Oct 30 09:41:31 CST 2015
     */
    private String sensitiveName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ctr_sensitive.stat
     *
     * @mbggenerated Fri Oct 30 09:41:31 CST 2015
     */
    private Integer stat;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ctr_sensitive.create_time
     *
     * @mbggenerated Fri Oct 30 09:41:31 CST 2015
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ctr_sensitive.warning
     *
     * @mbggenerated Fri Oct 30 09:41:31 CST 2015
     */
    private Integer warning;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ctr_sensitive.valve_id
     *
     * @mbggenerated Fri Oct 30 09:41:31 CST 2015
     */
    private String valveId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ctr_sensitive.cafetorium_id
     *
     * @mbggenerated Fri Oct 30 09:41:31 CST 2015
     */
    private String cafetoriumId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ctr_sensitive.last_update_time
     *
     * @mbggenerated Fri Oct 30 09:41:31 CST 2015
     */
    private Date lastUpdateTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ctr_sensitive.id
     *
     * @return the value of t_ctr_sensitive.id
     *
     * @mbggenerated Fri Oct 30 09:41:31 CST 2015
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ctr_sensitive.id
     *
     * @param id the value for t_ctr_sensitive.id
     *
     * @mbggenerated Fri Oct 30 09:41:31 CST 2015
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ctr_sensitive.sensitive_name
     *
     * @return the value of t_ctr_sensitive.sensitive_name
     *
     * @mbggenerated Fri Oct 30 09:41:31 CST 2015
     */
    public String getSensitiveName() {
        return sensitiveName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ctr_sensitive.sensitive_name
     *
     * @param sensitiveName the value for t_ctr_sensitive.sensitive_name
     *
     * @mbggenerated Fri Oct 30 09:41:31 CST 2015
     */
    public void setSensitiveName(String sensitiveName) {
        this.sensitiveName = sensitiveName == null ? null : sensitiveName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ctr_sensitive.stat
     *
     * @return the value of t_ctr_sensitive.stat
     *
     * @mbggenerated Fri Oct 30 09:41:31 CST 2015
     */
    public Integer getStat() {
        return stat;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ctr_sensitive.stat
     *
     * @param stat the value for t_ctr_sensitive.stat
     *
     * @mbggenerated Fri Oct 30 09:41:31 CST 2015
     */
    public void setStat(Integer stat) {
        this.stat = stat;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ctr_sensitive.create_time
     *
     * @return the value of t_ctr_sensitive.create_time
     *
     * @mbggenerated Fri Oct 30 09:41:31 CST 2015
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ctr_sensitive.create_time
     *
     * @param createTime the value for t_ctr_sensitive.create_time
     *
     * @mbggenerated Fri Oct 30 09:41:31 CST 2015
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ctr_sensitive.warning
     *
     * @return the value of t_ctr_sensitive.warning
     *
     * @mbggenerated Fri Oct 30 09:41:31 CST 2015
     */
    public Integer getWarning() {
        return warning;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ctr_sensitive.warning
     *
     * @param warning the value for t_ctr_sensitive.warning
     *
     * @mbggenerated Fri Oct 30 09:41:31 CST 2015
     */
    public void setWarning(Integer warning) {
        this.warning = warning;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ctr_sensitive.valve_id
     *
     * @return the value of t_ctr_sensitive.valve_id
     *
     * @mbggenerated Fri Oct 30 09:41:31 CST 2015
     */
    public String getValveId() {
        return valveId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ctr_sensitive.valve_id
     *
     * @param valveId the value for t_ctr_sensitive.valve_id
     *
     * @mbggenerated Fri Oct 30 09:41:31 CST 2015
     */
    public void setValveId(String valveId) {
        this.valveId = valveId == null ? null : valveId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ctr_sensitive.cafetorium_id
     *
     * @return the value of t_ctr_sensitive.cafetorium_id
     *
     * @mbggenerated Fri Oct 30 09:41:31 CST 2015
     */
    public String getCafetoriumId() {
        return cafetoriumId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ctr_sensitive.cafetorium_id
     *
     * @param cafetoriumId the value for t_ctr_sensitive.cafetorium_id
     *
     * @mbggenerated Fri Oct 30 09:41:31 CST 2015
     */
    public void setCafetoriumId(String cafetoriumId) {
        this.cafetoriumId = cafetoriumId == null ? null : cafetoriumId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ctr_sensitive.last_update_time
     *
     * @return the value of t_ctr_sensitive.last_update_time
     *
     * @mbggenerated Fri Oct 30 09:41:31 CST 2015
     */
    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ctr_sensitive.last_update_time
     *
     * @param lastUpdateTime the value for t_ctr_sensitive.last_update_time
     *
     * @mbggenerated Fri Oct 30 09:41:31 CST 2015
     */
    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
}