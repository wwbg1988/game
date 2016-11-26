package com.ssic.game.common.mapper;

import com.ssic.game.common.pojo.ImsQuery;
import com.ssic.game.common.pojo.ImsQueryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ImsQueryMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ims_query
     *
     * @mbggenerated Tue Jul 28 16:09:32 CST 2015
     */
    int countByExample(ImsQueryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ims_query
     *
     * @mbggenerated Tue Jul 28 16:09:32 CST 2015
     */
    int deleteByExample(ImsQueryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ims_query
     *
     * @mbggenerated Tue Jul 28 16:09:32 CST 2015
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ims_query
     *
     * @mbggenerated Tue Jul 28 16:09:32 CST 2015
     */
    int insert(ImsQuery record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ims_query
     *
     * @mbggenerated Tue Jul 28 16:09:32 CST 2015
     */
    int insertSelective(ImsQuery record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ims_query
     *
     * @mbggenerated Tue Jul 28 16:09:32 CST 2015
     */
    List<ImsQuery> selectByExample(ImsQueryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ims_query
     *
     * @mbggenerated Tue Jul 28 16:09:32 CST 2015
     */
    ImsQuery selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ims_query
     *
     * @mbggenerated Tue Jul 28 16:09:32 CST 2015
     */
    int updateByExampleSelective(@Param("record") ImsQuery record, @Param("example") ImsQueryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ims_query
     *
     * @mbggenerated Tue Jul 28 16:09:32 CST 2015
     */
    int updateByExample(@Param("record") ImsQuery record, @Param("example") ImsQueryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ims_query
     *
     * @mbggenerated Tue Jul 28 16:09:32 CST 2015
     */
    int updateByPrimaryKeySelective(ImsQuery record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ims_query
     *
     * @mbggenerated Tue Jul 28 16:09:32 CST 2015
     */
    int updateByPrimaryKey(ImsQuery record);
}