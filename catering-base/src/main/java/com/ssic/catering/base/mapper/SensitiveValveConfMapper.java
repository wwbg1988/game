package com.ssic.catering.base.mapper;

import com.ssic.catering.base.pojo.SensitiveValveConf;
import com.ssic.catering.base.pojo.SensitiveValveConfExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SensitiveValveConfMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_sensitive_valve_conf
     *
     * @mbggenerated Fri Oct 30 09:41:31 CST 2015
     */
    int countByExample(SensitiveValveConfExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_sensitive_valve_conf
     *
     * @mbggenerated Fri Oct 30 09:41:31 CST 2015
     */
    int deleteByExample(SensitiveValveConfExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_sensitive_valve_conf
     *
     * @mbggenerated Fri Oct 30 09:41:31 CST 2015
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_sensitive_valve_conf
     *
     * @mbggenerated Fri Oct 30 09:41:31 CST 2015
     */
    int insert(SensitiveValveConf record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_sensitive_valve_conf
     *
     * @mbggenerated Fri Oct 30 09:41:31 CST 2015
     */
    int insertSelective(SensitiveValveConf record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_sensitive_valve_conf
     *
     * @mbggenerated Fri Oct 30 09:41:31 CST 2015
     */
    List<SensitiveValveConf> selectByExample(SensitiveValveConfExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_sensitive_valve_conf
     *
     * @mbggenerated Fri Oct 30 09:41:31 CST 2015
     */
    SensitiveValveConf selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_sensitive_valve_conf
     *
     * @mbggenerated Fri Oct 30 09:41:31 CST 2015
     */
    int updateByExampleSelective(@Param("record") SensitiveValveConf record, @Param("example") SensitiveValveConfExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_sensitive_valve_conf
     *
     * @mbggenerated Fri Oct 30 09:41:31 CST 2015
     */
    int updateByExample(@Param("record") SensitiveValveConf record, @Param("example") SensitiveValveConfExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_sensitive_valve_conf
     *
     * @mbggenerated Fri Oct 30 09:41:31 CST 2015
     */
    int updateByPrimaryKeySelective(SensitiveValveConf record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_sensitive_valve_conf
     *
     * @mbggenerated Fri Oct 30 09:41:31 CST 2015
     */
    int updateByPrimaryKey(SensitiveValveConf record);
}