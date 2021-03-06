package com.ssic.catering.lbs.mapper;

import com.ssic.catering.lbs.pojo.TransportDriver;
import com.ssic.catering.lbs.pojo.TransportDriverExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TransportDriverMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_transport_driver
     *
     * @mbggenerated Thu Nov 26 11:25:16 CST 2015
     */
    int countByExample(TransportDriverExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_transport_driver
     *
     * @mbggenerated Thu Nov 26 11:25:16 CST 2015
     */
    int deleteByExample(TransportDriverExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_transport_driver
     *
     * @mbggenerated Thu Nov 26 11:25:16 CST 2015
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_transport_driver
     *
     * @mbggenerated Thu Nov 26 11:25:16 CST 2015
     */
    int insert(TransportDriver record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_transport_driver
     *
     * @mbggenerated Thu Nov 26 11:25:16 CST 2015
     */
    int insertSelective(TransportDriver record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_transport_driver
     *
     * @mbggenerated Thu Nov 26 11:25:16 CST 2015
     */
    List<TransportDriver> selectByExample(TransportDriverExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_transport_driver
     *
     * @mbggenerated Thu Nov 26 11:25:16 CST 2015
     */
    TransportDriver selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_transport_driver
     *
     * @mbggenerated Thu Nov 26 11:25:16 CST 2015
     */
    int updateByExampleSelective(@Param("record") TransportDriver record, @Param("example") TransportDriverExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_transport_driver
     *
     * @mbggenerated Thu Nov 26 11:25:16 CST 2015
     */
    int updateByExample(@Param("record") TransportDriver record, @Param("example") TransportDriverExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_transport_driver
     *
     * @mbggenerated Thu Nov 26 11:25:16 CST 2015
     */
    int updateByPrimaryKeySelective(TransportDriver record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_transport_driver
     *
     * @mbggenerated Thu Nov 26 11:25:16 CST 2015
     */
    int updateByPrimaryKey(TransportDriver record);
}