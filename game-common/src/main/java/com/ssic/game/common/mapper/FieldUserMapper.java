package com.ssic.game.common.mapper;

import com.ssic.game.common.pojo.FieldUser;
import com.ssic.game.common.pojo.FieldUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FieldUserMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ims_field_user
     *
     * @mbggenerated Mon Jun 29 10:02:58 CST 2015
     */
    int countByExample(FieldUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ims_field_user
     *
     * @mbggenerated Mon Jun 29 10:02:58 CST 2015
     */
    int deleteByExample(FieldUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ims_field_user
     *
     * @mbggenerated Mon Jun 29 10:02:58 CST 2015
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ims_field_user
     *
     * @mbggenerated Mon Jun 29 10:02:58 CST 2015
     */
    int insert(FieldUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ims_field_user
     *
     * @mbggenerated Mon Jun 29 10:02:58 CST 2015
     */
    int insertSelective(FieldUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ims_field_user
     *
     * @mbggenerated Mon Jun 29 10:02:58 CST 2015
     */
    List<FieldUser> selectByExample(FieldUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ims_field_user
     *
     * @mbggenerated Mon Jun 29 10:02:58 CST 2015
     */
    FieldUser selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ims_field_user
     *
     * @mbggenerated Mon Jun 29 10:02:58 CST 2015
     */
    int updateByExampleSelective(@Param("record") FieldUser record, @Param("example") FieldUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ims_field_user
     *
     * @mbggenerated Mon Jun 29 10:02:58 CST 2015
     */
    int updateByExample(@Param("record") FieldUser record, @Param("example") FieldUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ims_field_user
     *
     * @mbggenerated Mon Jun 29 10:02:58 CST 2015
     */
    int updateByPrimaryKeySelective(FieldUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ims_field_user
     *
     * @mbggenerated Mon Jun 29 10:02:58 CST 2015
     */
    int updateByPrimaryKey(FieldUser record);
}