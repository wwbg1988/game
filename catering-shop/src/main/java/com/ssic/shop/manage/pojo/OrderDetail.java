package com.ssic.shop.manage.pojo;

import java.util.Date;

public class OrderDetail {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ctr_order_detail.id
     *
     * @mbggenerated Mon Oct 12 11:11:08 CST 2015
     */
    private String id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ctr_order_detail.goods_id
     *
     * @mbggenerated Mon Oct 12 11:11:08 CST 2015
     */
    private String goodsId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ctr_order_detail.goods_name
     *
     * @mbggenerated Mon Oct 12 11:11:08 CST 2015
     */
    private String goodsName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ctr_order_detail.goods_price
     *
     * @mbggenerated Mon Oct 12 11:11:08 CST 2015
     */
    private Double goodsPrice;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ctr_order_detail.goods_count
     *
     * @mbggenerated Mon Oct 12 11:11:08 CST 2015
     */
    private Integer goodsCount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ctr_order_detail.goods_type_id
     *
     * @mbggenerated Mon Oct 12 11:11:08 CST 2015
     */
    private String goodsTypeId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ctr_order_detail.order_id
     *
     * @mbggenerated Mon Oct 12 11:11:08 CST 2015
     */
    private String orderId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ctr_order_detail.create_time
     *
     * @mbggenerated Mon Oct 12 11:11:08 CST 2015
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ctr_order_detail.last_update_time
     *
     * @mbggenerated Mon Oct 12 11:11:08 CST 2015
     */
    private Date lastUpdateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ctr_order_detail.stat
     *
     * @mbggenerated Mon Oct 12 11:11:08 CST 2015
     */
    private Integer stat;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ctr_order_detail.id
     *
     * @return the value of t_ctr_order_detail.id
     *
     * @mbggenerated Mon Oct 12 11:11:08 CST 2015
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ctr_order_detail.id
     *
     * @param id the value for t_ctr_order_detail.id
     *
     * @mbggenerated Mon Oct 12 11:11:08 CST 2015
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ctr_order_detail.goods_id
     *
     * @return the value of t_ctr_order_detail.goods_id
     *
     * @mbggenerated Mon Oct 12 11:11:08 CST 2015
     */
    public String getGoodsId() {
        return goodsId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ctr_order_detail.goods_id
     *
     * @param goodsId the value for t_ctr_order_detail.goods_id
     *
     * @mbggenerated Mon Oct 12 11:11:08 CST 2015
     */
    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId == null ? null : goodsId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ctr_order_detail.goods_name
     *
     * @return the value of t_ctr_order_detail.goods_name
     *
     * @mbggenerated Mon Oct 12 11:11:08 CST 2015
     */
    public String getGoodsName() {
        return goodsName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ctr_order_detail.goods_name
     *
     * @param goodsName the value for t_ctr_order_detail.goods_name
     *
     * @mbggenerated Mon Oct 12 11:11:08 CST 2015
     */
    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName == null ? null : goodsName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ctr_order_detail.goods_price
     *
     * @return the value of t_ctr_order_detail.goods_price
     *
     * @mbggenerated Mon Oct 12 11:11:08 CST 2015
     */
    public Double getGoodsPrice() {
        return goodsPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ctr_order_detail.goods_price
     *
     * @param goodsPrice the value for t_ctr_order_detail.goods_price
     *
     * @mbggenerated Mon Oct 12 11:11:08 CST 2015
     */
    public void setGoodsPrice(Double goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ctr_order_detail.goods_count
     *
     * @return the value of t_ctr_order_detail.goods_count
     *
     * @mbggenerated Mon Oct 12 11:11:08 CST 2015
     */
    public Integer getGoodsCount() {
        return goodsCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ctr_order_detail.goods_count
     *
     * @param goodsCount the value for t_ctr_order_detail.goods_count
     *
     * @mbggenerated Mon Oct 12 11:11:08 CST 2015
     */
    public void setGoodsCount(Integer goodsCount) {
        this.goodsCount = goodsCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ctr_order_detail.goods_type_id
     *
     * @return the value of t_ctr_order_detail.goods_type_id
     *
     * @mbggenerated Mon Oct 12 11:11:08 CST 2015
     */
    public String getGoodsTypeId() {
        return goodsTypeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ctr_order_detail.goods_type_id
     *
     * @param goodsTypeId the value for t_ctr_order_detail.goods_type_id
     *
     * @mbggenerated Mon Oct 12 11:11:08 CST 2015
     */
    public void setGoodsTypeId(String goodsTypeId) {
        this.goodsTypeId = goodsTypeId == null ? null : goodsTypeId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ctr_order_detail.order_id
     *
     * @return the value of t_ctr_order_detail.order_id
     *
     * @mbggenerated Mon Oct 12 11:11:08 CST 2015
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ctr_order_detail.order_id
     *
     * @param orderId the value for t_ctr_order_detail.order_id
     *
     * @mbggenerated Mon Oct 12 11:11:08 CST 2015
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ctr_order_detail.create_time
     *
     * @return the value of t_ctr_order_detail.create_time
     *
     * @mbggenerated Mon Oct 12 11:11:08 CST 2015
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ctr_order_detail.create_time
     *
     * @param createTime the value for t_ctr_order_detail.create_time
     *
     * @mbggenerated Mon Oct 12 11:11:08 CST 2015
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ctr_order_detail.last_update_time
     *
     * @return the value of t_ctr_order_detail.last_update_time
     *
     * @mbggenerated Mon Oct 12 11:11:08 CST 2015
     */
    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ctr_order_detail.last_update_time
     *
     * @param lastUpdateTime the value for t_ctr_order_detail.last_update_time
     *
     * @mbggenerated Mon Oct 12 11:11:08 CST 2015
     */
    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ctr_order_detail.stat
     *
     * @return the value of t_ctr_order_detail.stat
     *
     * @mbggenerated Mon Oct 12 11:11:08 CST 2015
     */
    public Integer getStat() {
        return stat;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ctr_order_detail.stat
     *
     * @param stat the value for t_ctr_order_detail.stat
     *
     * @mbggenerated Mon Oct 12 11:11:08 CST 2015
     */
    public void setStat(Integer stat) {
        this.stat = stat;
    }
}