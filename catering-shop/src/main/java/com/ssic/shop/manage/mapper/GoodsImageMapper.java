package com.ssic.shop.manage.mapper;

import com.ssic.shop.manage.pojo.GoodsImage;
import com.ssic.shop.manage.pojo.GoodsImageExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GoodsImageMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_goods_image
     *
     * @mbggenerated Mon Sep 21 11:44:48 CST 2015
     */
    int countByExample(GoodsImageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_goods_image
     *
     * @mbggenerated Mon Sep 21 11:44:48 CST 2015
     */
    int deleteByExample(GoodsImageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_goods_image
     *
     * @mbggenerated Mon Sep 21 11:44:48 CST 2015
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_goods_image
     *
     * @mbggenerated Mon Sep 21 11:44:48 CST 2015
     */
    int insert(GoodsImage record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_goods_image
     *
     * @mbggenerated Mon Sep 21 11:44:48 CST 2015
     */
    int insertSelective(GoodsImage record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_goods_image
     *
     * @mbggenerated Mon Sep 21 11:44:48 CST 2015
     */
    List<GoodsImage> selectByExample(GoodsImageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_goods_image
     *
     * @mbggenerated Mon Sep 21 11:44:48 CST 2015
     */
    GoodsImage selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_goods_image
     *
     * @mbggenerated Mon Sep 21 11:44:48 CST 2015
     */
    int updateByExampleSelective(@Param("record") GoodsImage record, @Param("example") GoodsImageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_goods_image
     *
     * @mbggenerated Mon Sep 21 11:44:48 CST 2015
     */
    int updateByExample(@Param("record") GoodsImage record, @Param("example") GoodsImageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_goods_image
     *
     * @mbggenerated Mon Sep 21 11:44:48 CST 2015
     */
    int updateByPrimaryKeySelective(GoodsImage record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_goods_image
     *
     * @mbggenerated Mon Sep 21 11:44:48 CST 2015
     */
    int updateByPrimaryKey(GoodsImage record);
}