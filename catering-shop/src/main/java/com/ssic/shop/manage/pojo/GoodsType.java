package com.ssic.shop.manage.pojo;

import java.util.Date;

public class GoodsType {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ctr_goods_type.id
     *
     * @mbggenerated Tue Oct 20 14:43:00 CST 2015
     */
    private String id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ctr_goods_type.type_name
     *
     * @mbggenerated Tue Oct 20 14:43:00 CST 2015
     */
    private String typeName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ctr_goods_type.icon
     *
     * @mbggenerated Tue Oct 20 14:43:00 CST 2015
     */
    private String icon;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ctr_goods_type.create_time
     *
     * @mbggenerated Tue Oct 20 14:43:00 CST 2015
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ctr_goods_type.last_update_time
     *
     * @mbggenerated Tue Oct 20 14:43:00 CST 2015
     */
    private Date lastUpdateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ctr_goods_type.stat
     *
     * @mbggenerated Tue Oct 20 14:43:00 CST 2015
     */
    private Integer stat;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ctr_goods_type.cafetorium_id
     *
     * @mbggenerated Tue Oct 20 14:43:00 CST 2015
     */
    private String cafetoriumId;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ctr_goods_type.id
     *
     * @return the value of t_ctr_goods_type.id
     *
     * @mbggenerated Tue Oct 20 14:43:00 CST 2015
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ctr_goods_type.id
     *
     * @param id the value for t_ctr_goods_type.id
     *
     * @mbggenerated Tue Oct 20 14:43:00 CST 2015
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ctr_goods_type.type_name
     *
     * @return the value of t_ctr_goods_type.type_name
     *
     * @mbggenerated Tue Oct 20 14:43:00 CST 2015
     */
    public String getTypeName() {
        return typeName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ctr_goods_type.type_name
     *
     * @param typeName the value for t_ctr_goods_type.type_name
     *
     * @mbggenerated Tue Oct 20 14:43:00 CST 2015
     */
    public void setTypeName(String typeName) {
        this.typeName = typeName == null ? null : typeName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ctr_goods_type.icon
     *
     * @return the value of t_ctr_goods_type.icon
     *
     * @mbggenerated Tue Oct 20 14:43:00 CST 2015
     */
    public String getIcon() {
        return icon;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ctr_goods_type.icon
     *
     * @param icon the value for t_ctr_goods_type.icon
     *
     * @mbggenerated Tue Oct 20 14:43:00 CST 2015
     */
    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ctr_goods_type.create_time
     *
     * @return the value of t_ctr_goods_type.create_time
     *
     * @mbggenerated Tue Oct 20 14:43:00 CST 2015
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ctr_goods_type.create_time
     *
     * @param createTime the value for t_ctr_goods_type.create_time
     *
     * @mbggenerated Tue Oct 20 14:43:00 CST 2015
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ctr_goods_type.last_update_time
     *
     * @return the value of t_ctr_goods_type.last_update_time
     *
     * @mbggenerated Tue Oct 20 14:43:00 CST 2015
     */
    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ctr_goods_type.last_update_time
     *
     * @param lastUpdateTime the value for t_ctr_goods_type.last_update_time
     *
     * @mbggenerated Tue Oct 20 14:43:00 CST 2015
     */
    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ctr_goods_type.stat
     *
     * @return the value of t_ctr_goods_type.stat
     *
     * @mbggenerated Tue Oct 20 14:43:00 CST 2015
     */
    public Integer getStat() {
        return stat;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ctr_goods_type.stat
     *
     * @param stat the value for t_ctr_goods_type.stat
     *
     * @mbggenerated Tue Oct 20 14:43:00 CST 2015
     */
    public void setStat(Integer stat) {
        this.stat = stat;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ctr_goods_type.cafetorium_id
     *
     * @return the value of t_ctr_goods_type.cafetorium_id
     *
     * @mbggenerated Tue Oct 20 14:43:00 CST 2015
     */
    public String getCafetoriumId() {
        return cafetoriumId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ctr_goods_type.cafetorium_id
     *
     * @param cafetoriumId the value for t_ctr_goods_type.cafetorium_id
     *
     * @mbggenerated Tue Oct 20 14:43:00 CST 2015
     */
    public void setCafetoriumId(String cafetoriumId) {
        this.cafetoriumId = cafetoriumId == null ? null : cafetoriumId.trim();
    }
}