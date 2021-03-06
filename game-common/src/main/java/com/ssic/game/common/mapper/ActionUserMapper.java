package com.ssic.game.common.mapper;

import com.ssic.game.common.pojo.ActionUser;
import com.ssic.game.common.pojo.ActionUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ActionUserMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ims_action_user
     *
     * @mbggenerated Fri Jun 26 21:19:17 CST 2015
     */
    int countByExample(ActionUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ims_action_user
     *
     * @mbggenerated Fri Jun 26 21:19:17 CST 2015
     */
    int deleteByExample(ActionUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ims_action_user
     *
     * @mbggenerated Fri Jun 26 21:19:17 CST 2015
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ims_action_user
     *
     * @mbggenerated Fri Jun 26 21:19:17 CST 2015
     */
    int insert(ActionUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ims_action_user
     *
     * @mbggenerated Fri Jun 26 21:19:17 CST 2015
     */
    int insertSelective(ActionUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ims_action_user
     *
     * @mbggenerated Fri Jun 26 21:19:17 CST 2015
     */
    List<ActionUser> selectByExample(ActionUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ims_action_user
     *
     * @mbggenerated Fri Jun 26 21:19:17 CST 2015
     */
    ActionUser selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ims_action_user
     *
     * @mbggenerated Fri Jun 26 21:19:17 CST 2015
     */
    int updateByExampleSelective(@Param("record") ActionUser record, @Param("example") ActionUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ims_action_user
     *
     * @mbggenerated Fri Jun 26 21:19:17 CST 2015
     */
    int updateByExample(@Param("record") ActionUser record, @Param("example") ActionUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ims_action_user
     *
     * @mbggenerated Fri Jun 26 21:19:17 CST 2015
     */
    int updateByPrimaryKeySelective(ActionUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ims_action_user
     *
     * @mbggenerated Fri Jun 26 21:19:17 CST 2015
     */
    int updateByPrimaryKey(ActionUser record);
}