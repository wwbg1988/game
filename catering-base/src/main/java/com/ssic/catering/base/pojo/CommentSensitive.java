package com.ssic.catering.base.pojo;

import java.util.Date;

public class CommentSensitive {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ctr_comment_sensitive.id
     *
     * @mbggenerated Thu Aug 06 13:04:52 CST 2015
     */
    private String id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ctr_comment_sensitive.comment_id
     *
     * @mbggenerated Thu Aug 06 13:04:52 CST 2015
     */
    private String commentId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ctr_comment_sensitive.sensitive_id
     *
     * @mbggenerated Thu Aug 06 13:04:52 CST 2015
     */
    private String sensitiveId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ctr_comment_sensitive.create_time
     *
     * @mbggenerated Thu Aug 06 13:04:52 CST 2015
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ctr_comment_sensitive.stat
     *
     * @mbggenerated Thu Aug 06 13:04:52 CST 2015
     */
    private Integer stat;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ctr_comment_sensitive.id
     *
     * @return the value of t_ctr_comment_sensitive.id
     *
     * @mbggenerated Thu Aug 06 13:04:52 CST 2015
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ctr_comment_sensitive.id
     *
     * @param id the value for t_ctr_comment_sensitive.id
     *
     * @mbggenerated Thu Aug 06 13:04:52 CST 2015
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ctr_comment_sensitive.comment_id
     *
     * @return the value of t_ctr_comment_sensitive.comment_id
     *
     * @mbggenerated Thu Aug 06 13:04:52 CST 2015
     */
    public String getCommentId() {
        return commentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ctr_comment_sensitive.comment_id
     *
     * @param commentId the value for t_ctr_comment_sensitive.comment_id
     *
     * @mbggenerated Thu Aug 06 13:04:52 CST 2015
     */
    public void setCommentId(String commentId) {
        this.commentId = commentId == null ? null : commentId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ctr_comment_sensitive.sensitive_id
     *
     * @return the value of t_ctr_comment_sensitive.sensitive_id
     *
     * @mbggenerated Thu Aug 06 13:04:52 CST 2015
     */
    public String getSensitiveId() {
        return sensitiveId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ctr_comment_sensitive.sensitive_id
     *
     * @param sensitiveId the value for t_ctr_comment_sensitive.sensitive_id
     *
     * @mbggenerated Thu Aug 06 13:04:52 CST 2015
     */
    public void setSensitiveId(String sensitiveId) {
        this.sensitiveId = sensitiveId == null ? null : sensitiveId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ctr_comment_sensitive.create_time
     *
     * @return the value of t_ctr_comment_sensitive.create_time
     *
     * @mbggenerated Thu Aug 06 13:04:52 CST 2015
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ctr_comment_sensitive.create_time
     *
     * @param createTime the value for t_ctr_comment_sensitive.create_time
     *
     * @mbggenerated Thu Aug 06 13:04:52 CST 2015
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ctr_comment_sensitive.stat
     *
     * @return the value of t_ctr_comment_sensitive.stat
     *
     * @mbggenerated Thu Aug 06 13:04:52 CST 2015
     */
    public Integer getStat() {
        return stat;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ctr_comment_sensitive.stat
     *
     * @param stat the value for t_ctr_comment_sensitive.stat
     *
     * @mbggenerated Thu Aug 06 13:04:52 CST 2015
     */
    public void setStat(Integer stat) {
        this.stat = stat;
    }
}