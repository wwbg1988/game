package com.ssic.catering.base.mapper;

import com.ssic.catering.base.pojo.GroupUser;
import com.ssic.catering.base.pojo.GroupUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GroupUserMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_group_user
     *
     * @mbggenerated Tue Sep 15 13:55:10 CST 2015
     */
    int countByExample(GroupUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_group_user
     *
     * @mbggenerated Tue Sep 15 13:55:10 CST 2015
     */
    int deleteByExample(GroupUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_group_user
     *
     * @mbggenerated Tue Sep 15 13:55:10 CST 2015
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_group_user
     *
     * @mbggenerated Tue Sep 15 13:55:10 CST 2015
     */
    int insert(GroupUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_group_user
     *
     * @mbggenerated Tue Sep 15 13:55:10 CST 2015
     */
    int insertSelective(GroupUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_group_user
     *
     * @mbggenerated Tue Sep 15 13:55:10 CST 2015
     */
    List<GroupUser> selectByExample(GroupUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_group_user
     *
     * @mbggenerated Tue Sep 15 13:55:10 CST 2015
     */
    GroupUser selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_group_user
     *
     * @mbggenerated Tue Sep 15 13:55:10 CST 2015
     */
    int updateByExampleSelective(@Param("record") GroupUser record, @Param("example") GroupUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_group_user
     *
     * @mbggenerated Tue Sep 15 13:55:10 CST 2015
     */
    int updateByExample(@Param("record") GroupUser record, @Param("example") GroupUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_group_user
     *
     * @mbggenerated Tue Sep 15 13:55:10 CST 2015
     */
    int updateByPrimaryKeySelective(GroupUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_group_user
     *
     * @mbggenerated Tue Sep 15 13:55:10 CST 2015
     */
    int updateByPrimaryKey(GroupUser record);
}