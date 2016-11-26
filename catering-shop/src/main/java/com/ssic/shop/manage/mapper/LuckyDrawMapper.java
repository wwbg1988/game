package com.ssic.shop.manage.mapper;

import com.ssic.shop.manage.pojo.LuckyDraw;
import com.ssic.shop.manage.pojo.LuckyDrawExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LuckyDrawMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_lucky_draw
     *
     * @mbggenerated Tue Oct 20 14:43:00 CST 2015
     */
    int countByExample(LuckyDrawExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_lucky_draw
     *
     * @mbggenerated Tue Oct 20 14:43:00 CST 2015
     */
    int deleteByExample(LuckyDrawExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_lucky_draw
     *
     * @mbggenerated Tue Oct 20 14:43:00 CST 2015
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_lucky_draw
     *
     * @mbggenerated Tue Oct 20 14:43:00 CST 2015
     */
    int insert(LuckyDraw record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_lucky_draw
     *
     * @mbggenerated Tue Oct 20 14:43:00 CST 2015
     */
    int insertSelective(LuckyDraw record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_lucky_draw
     *
     * @mbggenerated Tue Oct 20 14:43:00 CST 2015
     */
    List<LuckyDraw> selectByExample(LuckyDrawExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_lucky_draw
     *
     * @mbggenerated Tue Oct 20 14:43:00 CST 2015
     */
    LuckyDraw selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_lucky_draw
     *
     * @mbggenerated Tue Oct 20 14:43:00 CST 2015
     */
    int updateByExampleSelective(@Param("record") LuckyDraw record, @Param("example") LuckyDrawExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_lucky_draw
     *
     * @mbggenerated Tue Oct 20 14:43:00 CST 2015
     */
    int updateByExample(@Param("record") LuckyDraw record, @Param("example") LuckyDrawExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_lucky_draw
     *
     * @mbggenerated Tue Oct 20 14:43:00 CST 2015
     */
    int updateByPrimaryKeySelective(LuckyDraw record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_lucky_draw
     *
     * @mbggenerated Tue Oct 20 14:43:00 CST 2015
     */
    int updateByPrimaryKey(LuckyDraw record);
}