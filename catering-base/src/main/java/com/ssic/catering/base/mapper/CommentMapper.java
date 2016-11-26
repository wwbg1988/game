package com.ssic.catering.base.mapper;

import com.ssic.catering.base.pojo.Comment;
import com.ssic.catering.base.pojo.CommentExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CommentMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_comment
     *
     * @mbggenerated Thu Aug 06 20:13:05 CST 2015
     */
    int countByExample(CommentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_comment
     *
     * @mbggenerated Thu Aug 06 20:13:05 CST 2015
     */
    int deleteByExample(CommentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_comment
     *
     * @mbggenerated Thu Aug 06 20:13:05 CST 2015
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_comment
     *
     * @mbggenerated Thu Aug 06 20:13:05 CST 2015
     */
    int insert(Comment record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_comment
     *
     * @mbggenerated Thu Aug 06 20:13:05 CST 2015
     */
    int insertSelective(Comment record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_comment
     *
     * @mbggenerated Thu Aug 06 20:13:05 CST 2015
     */
    List<Comment> selectByExample(CommentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_comment
     *
     * @mbggenerated Thu Aug 06 20:13:05 CST 2015
     */
    Comment selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_comment
     *
     * @mbggenerated Thu Aug 06 20:13:05 CST 2015
     */
    int updateByExampleSelective(@Param("record") Comment record, @Param("example") CommentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_comment
     *
     * @mbggenerated Thu Aug 06 20:13:05 CST 2015
     */
    int updateByExample(@Param("record") Comment record, @Param("example") CommentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_comment
     *
     * @mbggenerated Thu Aug 06 20:13:05 CST 2015
     */
    int updateByPrimaryKeySelective(Comment record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_comment
     *
     * @mbggenerated Thu Aug 06 20:13:05 CST 2015
     */
    int updateByPrimaryKey(Comment record);
}