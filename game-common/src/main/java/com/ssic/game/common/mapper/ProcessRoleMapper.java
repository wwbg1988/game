package com.ssic.game.common.mapper;

import com.ssic.game.common.pojo.ProcessRole;
import com.ssic.game.common.pojo.ProcessRoleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProcessRoleMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ims_process_role
     *
     * @mbggenerated Fri Jun 26 18:52:38 CST 2015
     */
    int countByExample(ProcessRoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ims_process_role
     *
     * @mbggenerated Fri Jun 26 18:52:38 CST 2015
     */
    int deleteByExample(ProcessRoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ims_process_role
     *
     * @mbggenerated Fri Jun 26 18:52:38 CST 2015
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ims_process_role
     *
     * @mbggenerated Fri Jun 26 18:52:38 CST 2015
     */
    int insert(ProcessRole record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ims_process_role
     *
     * @mbggenerated Fri Jun 26 18:52:38 CST 2015
     */
    int insertSelective(ProcessRole record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ims_process_role
     *
     * @mbggenerated Fri Jun 26 18:52:38 CST 2015
     */
    List<ProcessRole> selectByExample(ProcessRoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ims_process_role
     *
     * @mbggenerated Fri Jun 26 18:52:38 CST 2015
     */
    ProcessRole selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ims_process_role
     *
     * @mbggenerated Fri Jun 26 18:52:38 CST 2015
     */
    int updateByExampleSelective(@Param("record") ProcessRole record, @Param("example") ProcessRoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ims_process_role
     *
     * @mbggenerated Fri Jun 26 18:52:38 CST 2015
     */
    int updateByExample(@Param("record") ProcessRole record, @Param("example") ProcessRoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ims_process_role
     *
     * @mbggenerated Fri Jun 26 18:52:38 CST 2015
     */
    int updateByPrimaryKeySelective(ProcessRole record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ims_process_role
     *
     * @mbggenerated Fri Jun 26 18:52:38 CST 2015
     */
    int updateByPrimaryKey(ProcessRole record);
}