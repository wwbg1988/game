package com.ssic.catering.base.mapper;

import com.ssic.catering.base.pojo.CorkContractor;
import com.ssic.catering.base.pojo.CorkContractorExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CorkContractorMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_fork_contractor
     *
     * @mbggenerated Thu Aug 06 16:36:50 CST 2015
     */
    int countByExample(CorkContractorExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_fork_contractor
     *
     * @mbggenerated Thu Aug 06 16:36:50 CST 2015
     */
    int deleteByExample(CorkContractorExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_fork_contractor
     *
     * @mbggenerated Thu Aug 06 16:36:50 CST 2015
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_fork_contractor
     *
     * @mbggenerated Thu Aug 06 16:36:50 CST 2015
     */
    int insert(CorkContractor record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_fork_contractor
     *
     * @mbggenerated Thu Aug 06 16:36:50 CST 2015
     */
    int insertSelective(CorkContractor record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_fork_contractor
     *
     * @mbggenerated Thu Aug 06 16:36:50 CST 2015
     */
    List<CorkContractor> selectByExample(CorkContractorExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_fork_contractor
     *
     * @mbggenerated Thu Aug 06 16:36:50 CST 2015
     */
    CorkContractor selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_fork_contractor
     *
     * @mbggenerated Thu Aug 06 16:36:50 CST 2015
     */
    int updateByExampleSelective(@Param("record") CorkContractor record, @Param("example") CorkContractorExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_fork_contractor
     *
     * @mbggenerated Thu Aug 06 16:36:50 CST 2015
     */
    int updateByExample(@Param("record") CorkContractor record, @Param("example") CorkContractorExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_fork_contractor
     *
     * @mbggenerated Thu Aug 06 16:36:50 CST 2015
     */
    int updateByPrimaryKeySelective(CorkContractor record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_fork_contractor
     *
     * @mbggenerated Thu Aug 06 16:36:50 CST 2015
     */
    int updateByPrimaryKey(CorkContractor record);
}