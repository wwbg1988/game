package com.ssic.catering.base.mapper;

import com.ssic.catering.base.pojo.Meeting;
import com.ssic.catering.base.pojo.MeetingExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MeetingMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_meeting
     *
     * @mbggenerated Tue Oct 20 14:10:00 CST 2015
     */
    int countByExample(MeetingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_meeting
     *
     * @mbggenerated Tue Oct 20 14:10:00 CST 2015
     */
    int deleteByExample(MeetingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_meeting
     *
     * @mbggenerated Tue Oct 20 14:10:00 CST 2015
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_meeting
     *
     * @mbggenerated Tue Oct 20 14:10:00 CST 2015
     */
    int insert(Meeting record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_meeting
     *
     * @mbggenerated Tue Oct 20 14:10:00 CST 2015
     */
    int insertSelective(Meeting record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_meeting
     *
     * @mbggenerated Tue Oct 20 14:10:00 CST 2015
     */
    List<Meeting> selectByExample(MeetingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_meeting
     *
     * @mbggenerated Tue Oct 20 14:10:00 CST 2015
     */
    Meeting selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_meeting
     *
     * @mbggenerated Tue Oct 20 14:10:00 CST 2015
     */
    int updateByExampleSelective(@Param("record") Meeting record, @Param("example") MeetingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_meeting
     *
     * @mbggenerated Tue Oct 20 14:10:00 CST 2015
     */
    int updateByExample(@Param("record") Meeting record, @Param("example") MeetingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_meeting
     *
     * @mbggenerated Tue Oct 20 14:10:00 CST 2015
     */
    int updateByPrimaryKeySelective(Meeting record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_meeting
     *
     * @mbggenerated Tue Oct 20 14:10:00 CST 2015
     */
    int updateByPrimaryKey(Meeting record);
}