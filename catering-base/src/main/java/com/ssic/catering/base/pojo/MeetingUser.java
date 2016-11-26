package com.ssic.catering.base.pojo;

import java.util.Date;

public class MeetingUser {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ctr_meeting_user.id
     *
     * @mbggenerated Tue Aug 04 14:59:20 CST 2015
     */
    private String id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ctr_meeting_user.user_id
     *
     * @mbggenerated Tue Aug 04 14:59:20 CST 2015
     */
    private String userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ctr_meeting_user.meeting_id
     *
     * @mbggenerated Tue Aug 04 14:59:20 CST 2015
     */
    private String meetingId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ctr_meeting_user.state
     *
     * @mbggenerated Tue Aug 04 14:59:20 CST 2015
     */
    private Integer state;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ctr_meeting_user.stat
     *
     * @mbggenerated Tue Aug 04 14:59:20 CST 2015
     */
    private Integer stat;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ctr_meeting_user.last_update_time
     *
     * @mbggenerated Tue Aug 04 14:59:20 CST 2015
     */
    private Date lastUpdateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ctr_meeting_user.create_time
     *
     * @mbggenerated Tue Aug 04 14:59:20 CST 2015
     */
    private Date createTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ctr_meeting_user.id
     *
     * @return the value of t_ctr_meeting_user.id
     *
     * @mbggenerated Tue Aug 04 14:59:20 CST 2015
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ctr_meeting_user.id
     *
     * @param id the value for t_ctr_meeting_user.id
     *
     * @mbggenerated Tue Aug 04 14:59:20 CST 2015
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ctr_meeting_user.user_id
     *
     * @return the value of t_ctr_meeting_user.user_id
     *
     * @mbggenerated Tue Aug 04 14:59:20 CST 2015
     */
    public String getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ctr_meeting_user.user_id
     *
     * @param userId the value for t_ctr_meeting_user.user_id
     *
     * @mbggenerated Tue Aug 04 14:59:20 CST 2015
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ctr_meeting_user.meeting_id
     *
     * @return the value of t_ctr_meeting_user.meeting_id
     *
     * @mbggenerated Tue Aug 04 14:59:20 CST 2015
     */
    public String getMeetingId() {
        return meetingId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ctr_meeting_user.meeting_id
     *
     * @param meetingId the value for t_ctr_meeting_user.meeting_id
     *
     * @mbggenerated Tue Aug 04 14:59:20 CST 2015
     */
    public void setMeetingId(String meetingId) {
        this.meetingId = meetingId == null ? null : meetingId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ctr_meeting_user.state
     *
     * @return the value of t_ctr_meeting_user.state
     *
     * @mbggenerated Tue Aug 04 14:59:20 CST 2015
     */
    public Integer getState() {
        return state;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ctr_meeting_user.state
     *
     * @param state the value for t_ctr_meeting_user.state
     *
     * @mbggenerated Tue Aug 04 14:59:20 CST 2015
     */
    public void setState(Integer state) {
        this.state = state;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ctr_meeting_user.stat
     *
     * @return the value of t_ctr_meeting_user.stat
     *
     * @mbggenerated Tue Aug 04 14:59:20 CST 2015
     */
    public Integer getStat() {
        return stat;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ctr_meeting_user.stat
     *
     * @param stat the value for t_ctr_meeting_user.stat
     *
     * @mbggenerated Tue Aug 04 14:59:20 CST 2015
     */
    public void setStat(Integer stat) {
        this.stat = stat;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ctr_meeting_user.last_update_time
     *
     * @return the value of t_ctr_meeting_user.last_update_time
     *
     * @mbggenerated Tue Aug 04 14:59:20 CST 2015
     */
    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ctr_meeting_user.last_update_time
     *
     * @param lastUpdateTime the value for t_ctr_meeting_user.last_update_time
     *
     * @mbggenerated Tue Aug 04 14:59:20 CST 2015
     */
    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ctr_meeting_user.create_time
     *
     * @return the value of t_ctr_meeting_user.create_time
     *
     * @mbggenerated Tue Aug 04 14:59:20 CST 2015
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ctr_meeting_user.create_time
     *
     * @param createTime the value for t_ctr_meeting_user.create_time
     *
     * @mbggenerated Tue Aug 04 14:59:20 CST 2015
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}