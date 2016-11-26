package com.ssic.shop.manage.mapper;

import com.ssic.shop.manage.pojo.LuckDraw;
import com.ssic.shop.manage.pojo.LuckDrawExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LuckDrawMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_lucky_draw
     *
     * @mbggenerated Mon Sep 21 14:36:44 CST 2015
     */
    int countByExample(LuckDrawExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_lucky_draw
     *
     * @mbggenerated Mon Sep 21 14:36:44 CST 2015
     */
    int deleteByExample(LuckDrawExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_lucky_draw
     *
     * @mbggenerated Mon Sep 21 14:36:44 CST 2015
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_lucky_draw
     *
     * @mbggenerated Mon Sep 21 14:36:44 CST 2015
     */
    int insert(LuckDraw record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_lucky_draw
     *
     * @mbggenerated Mon Sep 21 14:36:44 CST 2015
     */
    int insertSelective(LuckDraw record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_lucky_draw
     *
     * @mbggenerated Mon Sep 21 14:36:44 CST 2015
     */
    List<LuckDraw> selectByExample(LuckDrawExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_lucky_draw
     *
     * @mbggenerated Mon Sep 21 14:36:44 CST 2015
     */
    LuckDraw selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_lucky_draw
     *
     * @mbggenerated Mon Sep 21 14:36:44 CST 2015
     */
    int updateByExampleSelective(@Param("record") LuckDraw record, @Param("example") LuckDrawExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_lucky_draw
     *
     * @mbggenerated Mon Sep 21 14:36:44 CST 2015
     */
    int updateByExample(@Param("record") LuckDraw record, @Param("example") LuckDrawExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_lucky_draw
     *
     * @mbggenerated Mon Sep 21 14:36:44 CST 2015
     */
    int updateByPrimaryKeySelective(LuckDraw record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_lucky_draw
     *
     * @mbggenerated Mon Sep 21 14:36:44 CST 2015
     */
    int updateByPrimaryKey(LuckDraw record);
}