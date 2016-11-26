package com.ssic.shop.manage.pojo;

import java.util.Date;

public class OrderConsignee {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ctr_order_consignee.id
     *
     * @mbggenerated Thu Aug 27 09:25:08 CST 2015
     */
    private String id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ctr_order_consignee.order_id
     *
     * @mbggenerated Thu Aug 27 09:25:08 CST 2015
     */
    private String orderId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ctr_order_consignee.consignee_id
     *
     * @mbggenerated Thu Aug 27 09:25:08 CST 2015
     */
    private String consigneeId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ctr_order_consignee.goods_type_id
     *
     * @mbggenerated Thu Aug 27 09:25:08 CST 2015
     */
    private String goodsTypeId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ctr_order_consignee.create_time
     *
     * @mbggenerated Thu Aug 27 09:25:08 CST 2015
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ctr_order_consignee.last_update_time
     *
     * @mbggenerated Thu Aug 27 09:25:08 CST 2015
     */
    private Date lastUpdateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ctr_order_consignee.stat
     *
     * @mbggenerated Thu Aug 27 09:25:08 CST 2015
     */
    private Integer stat;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ctr_order_consignee.id
     *
     * @return the value of t_ctr_order_consignee.id
     *
     * @mbggenerated Thu Aug 27 09:25:08 CST 2015
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ctr_order_consignee.id
     *
     * @param id the value for t_ctr_order_consignee.id
     *
     * @mbggenerated Thu Aug 27 09:25:08 CST 2015
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ctr_order_consignee.order_id
     *
     * @return the value of t_ctr_order_consignee.order_id
     *
     * @mbggenerated Thu Aug 27 09:25:08 CST 2015
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ctr_order_consignee.order_id
     *
     * @param orderId the value for t_ctr_order_consignee.order_id
     *
     * @mbggenerated Thu Aug 27 09:25:08 CST 2015
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ctr_order_consignee.consignee_id
     *
     * @return the value of t_ctr_order_consignee.consignee_id
     *
     * @mbggenerated Thu Aug 27 09:25:08 CST 2015
     */
    public String getConsigneeId() {
        return consigneeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ctr_order_consignee.consignee_id
     *
     * @param consigneeId the value for t_ctr_order_consignee.consignee_id
     *
     * @mbggenerated Thu Aug 27 09:25:08 CST 2015
     */
    public void setConsigneeId(String consigneeId) {
        this.consigneeId = consigneeId == null ? null : consigneeId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ctr_order_consignee.goods_type_id
     *
     * @return the value of t_ctr_order_consignee.goods_type_id
     *
     * @mbggenerated Thu Aug 27 09:25:08 CST 2015
     */
    public String getGoodsTypeId() {
        return goodsTypeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ctr_order_consignee.goods_type_id
     *
     * @param goodsTypeId the value for t_ctr_order_consignee.goods_type_id
     *
     * @mbggenerated Thu Aug 27 09:25:08 CST 2015
     */
    public void setGoodsTypeId(String goodsTypeId) {
        this.goodsTypeId = goodsTypeId == null ? null : goodsTypeId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ctr_order_consignee.create_time
     *
     * @return the value of t_ctr_order_consignee.create_time
     *
     * @mbggenerated Thu Aug 27 09:25:08 CST 2015
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ctr_order_consignee.create_time
     *
     * @param createTime the value for t_ctr_order_consignee.create_time
     *
     * @mbggenerated Thu Aug 27 09:25:08 CST 2015
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ctr_order_consignee.last_update_time
     *
     * @return the value of t_ctr_order_consignee.last_update_time
     *
     * @mbggenerated Thu Aug 27 09:25:08 CST 2015
     */
    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ctr_order_consignee.last_update_time
     *
     * @param lastUpdateTime the value for t_ctr_order_consignee.last_update_time
     *
     * @mbggenerated Thu Aug 27 09:25:08 CST 2015
     */
    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ctr_order_consignee.stat
     *
     * @return the value of t_ctr_order_consignee.stat
     *
     * @mbggenerated Thu Aug 27 09:25:08 CST 2015
     */
    public Integer getStat() {
        return stat;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ctr_order_consignee.stat
     *
     * @param stat the value for t_ctr_order_consignee.stat
     *
     * @mbggenerated Thu Aug 27 09:25:08 CST 2015
     */
    public void setStat(Integer stat) {
        this.stat = stat;
    }
}