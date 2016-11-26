package com.ssic.catering.base.mapper;

import com.ssic.catering.base.pojo.AvgScoreWarning;
import com.ssic.catering.base.pojo.AvgScoreWarningExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AvgScoreWarningMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_avg_score_warning
     *
     * @mbggenerated Tue Aug 11 10:42:47 CST 2015
     */
    int countByExample(AvgScoreWarningExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_avg_score_warning
     *
     * @mbggenerated Tue Aug 11 10:42:47 CST 2015
     */
    int deleteByExample(AvgScoreWarningExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_avg_score_warning
     *
     * @mbggenerated Tue Aug 11 10:42:47 CST 2015
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_avg_score_warning
     *
     * @mbggenerated Tue Aug 11 10:42:47 CST 2015
     */
    int insert(AvgScoreWarning record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_avg_score_warning
     *
     * @mbggenerated Tue Aug 11 10:42:47 CST 2015
     */
    int insertSelective(AvgScoreWarning record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_avg_score_warning
     *
     * @mbggenerated Tue Aug 11 10:42:47 CST 2015
     */
    List<AvgScoreWarning> selectByExample(AvgScoreWarningExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_avg_score_warning
     *
     * @mbggenerated Tue Aug 11 10:42:47 CST 2015
     */
    AvgScoreWarning selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_avg_score_warning
     *
     * @mbggenerated Tue Aug 11 10:42:47 CST 2015
     */
    int updateByExampleSelective(@Param("record") AvgScoreWarning record, @Param("example") AvgScoreWarningExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_avg_score_warning
     *
     * @mbggenerated Tue Aug 11 10:42:47 CST 2015
     */
    int updateByExample(@Param("record") AvgScoreWarning record, @Param("example") AvgScoreWarningExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_avg_score_warning
     *
     * @mbggenerated Tue Aug 11 10:42:47 CST 2015
     */
    int updateByPrimaryKeySelective(AvgScoreWarning record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_avg_score_warning
     *
     * @mbggenerated Tue Aug 11 10:42:47 CST 2015
     */
    int updateByPrimaryKey(AvgScoreWarning record);
}