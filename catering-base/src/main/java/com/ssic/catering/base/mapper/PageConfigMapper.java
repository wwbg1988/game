package com.ssic.catering.base.mapper;

import com.ssic.catering.base.pojo.PageConfig;
import com.ssic.catering.base.pojo.PageConfigExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PageConfigMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_page_config
     *
     * @mbggenerated Mon Nov 23 14:47:10 CST 2015
     */
    int countByExample(PageConfigExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_page_config
     *
     * @mbggenerated Mon Nov 23 14:47:10 CST 2015
     */
    int deleteByExample(PageConfigExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_page_config
     *
     * @mbggenerated Mon Nov 23 14:47:10 CST 2015
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_page_config
     *
     * @mbggenerated Mon Nov 23 14:47:10 CST 2015
     */
    int insert(PageConfig record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_page_config
     *
     * @mbggenerated Mon Nov 23 14:47:10 CST 2015
     */
    int insertSelective(PageConfig record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_page_config
     *
     * @mbggenerated Mon Nov 23 14:47:10 CST 2015
     */
    List<PageConfig> selectByExample(PageConfigExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_page_config
     *
     * @mbggenerated Mon Nov 23 14:47:10 CST 2015
     */
    PageConfig selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_page_config
     *
     * @mbggenerated Mon Nov 23 14:47:10 CST 2015
     */
    int updateByExampleSelective(@Param("record") PageConfig record, @Param("example") PageConfigExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_page_config
     *
     * @mbggenerated Mon Nov 23 14:47:10 CST 2015
     */
    int updateByExample(@Param("record") PageConfig record, @Param("example") PageConfigExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_page_config
     *
     * @mbggenerated Mon Nov 23 14:47:10 CST 2015
     */
    int updateByPrimaryKeySelective(PageConfig record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_page_config
     *
     * @mbggenerated Mon Nov 23 14:47:10 CST 2015
     */
    int updateByPrimaryKey(PageConfig record);
}