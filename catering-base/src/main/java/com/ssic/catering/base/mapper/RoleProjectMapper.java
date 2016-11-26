package com.ssic.catering.base.mapper;

import com.ssic.catering.base.pojo.RoleProject;
import com.ssic.catering.base.pojo.RoleProjectExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RoleProjectMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_admin_role_project
     *
     * @mbggenerated Tue Oct 27 18:08:27 CST 2015
     */
    int countByExample(RoleProjectExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_admin_role_project
     *
     * @mbggenerated Tue Oct 27 18:08:27 CST 2015
     */
    int deleteByExample(RoleProjectExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_admin_role_project
     *
     * @mbggenerated Tue Oct 27 18:08:27 CST 2015
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_admin_role_project
     *
     * @mbggenerated Tue Oct 27 18:08:27 CST 2015
     */
    int insert(RoleProject record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_admin_role_project
     *
     * @mbggenerated Tue Oct 27 18:08:27 CST 2015
     */
    int insertSelective(RoleProject record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_admin_role_project
     *
     * @mbggenerated Tue Oct 27 18:08:27 CST 2015
     */
    List<RoleProject> selectByExample(RoleProjectExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_admin_role_project
     *
     * @mbggenerated Tue Oct 27 18:08:27 CST 2015
     */
    RoleProject selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_admin_role_project
     *
     * @mbggenerated Tue Oct 27 18:08:27 CST 2015
     */
    int updateByExampleSelective(@Param("record") RoleProject record, @Param("example") RoleProjectExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_admin_role_project
     *
     * @mbggenerated Tue Oct 27 18:08:27 CST 2015
     */
    int updateByExample(@Param("record") RoleProject record, @Param("example") RoleProjectExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_admin_role_project
     *
     * @mbggenerated Tue Oct 27 18:08:27 CST 2015
     */
    int updateByPrimaryKeySelective(RoleProject record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_admin_role_project
     *
     * @mbggenerated Tue Oct 27 18:08:27 CST 2015
     */
    int updateByPrimaryKey(RoleProject record);
}