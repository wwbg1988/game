package com.ssic.catering.common.image;

import java.util.Date;

public class ImageInfo {
	  /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column juju_image_info.id
     *
     * @mbggenerated Wed Apr 15 13:18:17 CST 2015
     */
    private String id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column juju_image_info.name
     *
     * @mbggenerated Wed Apr 15 13:18:17 CST 2015
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column juju_image_info.url
     *
     * @mbggenerated Wed Apr 15 13:18:17 CST 2015
     */
    private String url;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column juju_image_info.create_time
     *
     * @mbggenerated Wed Apr 15 13:18:17 CST 2015
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column juju_image_info.last_update_time
     *
     * @mbggenerated Wed Apr 15 13:18:17 CST 2015
     */
    private Date lastUpdateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column juju_image_info.stat
     *
     * @mbggenerated Wed Apr 15 13:18:17 CST 2015
     */
    private Integer stat;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column juju_image_info.id
     *
     * @return the value of juju_image_info.id
     *
     * @mbggenerated Wed Apr 15 13:18:17 CST 2015
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column juju_image_info.id
     *
     * @param id the value for juju_image_info.id
     *
     * @mbggenerated Wed Apr 15 13:18:17 CST 2015
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column juju_image_info.name
     *
     * @return the value of juju_image_info.name
     *
     * @mbggenerated Wed Apr 15 13:18:17 CST 2015
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column juju_image_info.name
     *
     * @param name the value for juju_image_info.name
     *
     * @mbggenerated Wed Apr 15 13:18:17 CST 2015
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column juju_image_info.url
     *
     * @return the value of juju_image_info.url
     *
     * @mbggenerated Wed Apr 15 13:18:17 CST 2015
     */
    public String getUrl() {
        return url;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column juju_image_info.url
     *
     * @param url the value for juju_image_info.url
     *
     * @mbggenerated Wed Apr 15 13:18:17 CST 2015
     */
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column juju_image_info.create_time
     *
     * @return the value of juju_image_info.create_time
     *
     * @mbggenerated Wed Apr 15 13:18:17 CST 2015
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column juju_image_info.create_time
     *
     * @param createTime the value for juju_image_info.create_time
     *
     * @mbggenerated Wed Apr 15 13:18:17 CST 2015
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column juju_image_info.last_update_time
     *
     * @return the value of juju_image_info.last_update_time
     *
     * @mbggenerated Wed Apr 15 13:18:17 CST 2015
     */
    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column juju_image_info.last_update_time
     *
     * @param lastUpdateTime the value for juju_image_info.last_update_time
     *
     * @mbggenerated Wed Apr 15 13:18:17 CST 2015
     */
    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column juju_image_info.stat
     *
     * @return the value of juju_image_info.stat
     *
     * @mbggenerated Wed Apr 15 13:18:17 CST 2015
     */
    public Integer getStat() {
        return stat;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column juju_image_info.stat
     *
     * @param stat the value for juju_image_info.stat
     *
     * @mbggenerated Wed Apr 15 13:18:17 CST 2015
     */
    public void setStat(Integer stat) {
        this.stat = stat;
    }
}
