package com.ssic.catering.base.mapper;

import com.ssic.catering.base.pojo.MainContractor;
import com.ssic.catering.base.pojo.MainContractorExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MainContractorMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_main_contractor
     *
     * @mbggenerated Thu Aug 06 16:36:50 CST 2015
     */
    int countByExample(MainContractorExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_main_contractor
     *
     * @mbggenerated Thu Aug 06 16:36:50 CST 2015
     */
    int deleteByExample(MainContractorExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_main_contractor
     *
     * @mbggenerated Thu Aug 06 16:36:50 CST 2015
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_main_contractor
     *
     * @mbggenerated Thu Aug 06 16:36:50 CST 2015
     */
    int insert(MainContractor record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_main_contractor
     *
     * @mbggenerated Thu Aug 06 16:36:50 CST 2015
     */
    int insertSelective(MainContractor record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_main_contractor
     *
     * @mbggenerated Thu Aug 06 16:36:50 CST 2015
     */
    List<MainContractor> selectByExample(MainContractorExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_main_contractor
     *
     * @mbggenerated Thu Aug 06 16:36:50 CST 2015
     */
    MainContractor selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_main_contractor
     *
     * @mbggenerated Thu Aug 06 16:36:50 CST 2015
     */
    int updateByExampleSelective(@Param("record") MainContractor record, @Param("example") MainContractorExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_main_contractor
     *
     * @mbggenerated Thu Aug 06 16:36:50 CST 2015
     */
    int updateByExample(@Param("record") MainContractor record, @Param("example") MainContractorExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_main_contractor
     *
     * @mbggenerated Thu Aug 06 16:36:50 CST 2015
     */
    int updateByPrimaryKeySelective(MainContractor record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_main_contractor
     *
     * @mbggenerated Thu Aug 06 16:36:50 CST 2015
     */
    int updateByPrimaryKey(MainContractor record);
}