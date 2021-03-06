package com.ssic.game.common.mapper;

import com.ssic.game.common.pojo.Actions;
import com.ssic.game.common.pojo.ActionsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ActionsMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ims_actions
     *
     * @mbggenerated Fri Jun 26 21:19:17 CST 2015
     */
    int countByExample(ActionsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ims_actions
     *
     * @mbggenerated Fri Jun 26 21:19:17 CST 2015
     */
    int deleteByExample(ActionsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ims_actions
     *
     * @mbggenerated Fri Jun 26 21:19:17 CST 2015
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ims_actions
     *
     * @mbggenerated Fri Jun 26 21:19:17 CST 2015
     */
    int insert(Actions record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ims_actions
     *
     * @mbggenerated Fri Jun 26 21:19:17 CST 2015
     */
    int insertSelective(Actions record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ims_actions
     *
     * @mbggenerated Fri Jun 26 21:19:17 CST 2015
     */
    List<Actions> selectByExample(ActionsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ims_actions
     *
     * @mbggenerated Fri Jun 26 21:19:17 CST 2015
     */
    Actions selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ims_actions
     *
     * @mbggenerated Fri Jun 26 21:19:17 CST 2015
     */
    int updateByExampleSelective(@Param("record") Actions record, @Param("example") ActionsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ims_actions
     *
     * @mbggenerated Fri Jun 26 21:19:17 CST 2015
     */
    int updateByExample(@Param("record") Actions record, @Param("example") ActionsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ims_actions
     *
     * @mbggenerated Fri Jun 26 21:19:17 CST 2015
     */
    int updateByPrimaryKeySelective(Actions record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ims_actions
     *
     * @mbggenerated Fri Jun 26 21:19:17 CST 2015
     */
    int updateByPrimaryKey(Actions record);
}