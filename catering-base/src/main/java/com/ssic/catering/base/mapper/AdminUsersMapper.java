package com.ssic.catering.base.mapper;

import com.ssic.catering.base.pojo.AdminUsers;
import com.ssic.catering.base.pojo.AdminUsersExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AdminUsersMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_admin_users
     *
     * @mbggenerated Tue Aug 04 14:59:20 CST 2015
     */
    int countByExample(AdminUsersExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_admin_users
     *
     * @mbggenerated Tue Aug 04 14:59:20 CST 2015
     */
    int deleteByExample(AdminUsersExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_admin_users
     *
     * @mbggenerated Tue Aug 04 14:59:20 CST 2015
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_admin_users
     *
     * @mbggenerated Tue Aug 04 14:59:20 CST 2015
     */
    int insert(AdminUsers record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_admin_users
     *
     * @mbggenerated Tue Aug 04 14:59:20 CST 2015
     */
    int insertSelective(AdminUsers record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_admin_users
     *
     * @mbggenerated Tue Aug 04 14:59:20 CST 2015
     */
    List<AdminUsers> selectByExample(AdminUsersExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_admin_users
     *
     * @mbggenerated Tue Aug 04 14:59:20 CST 2015
     */
    AdminUsers selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_admin_users
     *
     * @mbggenerated Tue Aug 04 14:59:20 CST 2015
     */
    int updateByExampleSelective(@Param("record") AdminUsers record, @Param("example") AdminUsersExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_admin_users
     *
     * @mbggenerated Tue Aug 04 14:59:20 CST 2015
     */
    int updateByExample(@Param("record") AdminUsers record, @Param("example") AdminUsersExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_admin_users
     *
     * @mbggenerated Tue Aug 04 14:59:20 CST 2015
     */
    int updateByPrimaryKeySelective(AdminUsers record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_admin_users
     *
     * @mbggenerated Tue Aug 04 14:59:20 CST 2015
     */
    int updateByPrimaryKey(AdminUsers record);
}