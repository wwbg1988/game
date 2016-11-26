package com.ssic.catering.base.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class MeetingUserDto implements Serializable{
	@Getter
	@Setter
	private String id;
	@Getter
	@Setter
    private String userId;
	@Getter
	@Setter
    private String meetingId;
	@Getter
	@Setter
    private Integer state;   //状态：0参加   1拒绝   2待定   3创建者
	@Getter
	@Setter
    private Integer stat;
	@Getter
	@Setter
    private Date lastUpdateTime;
	@Getter
	@Setter
    private Date createTime;
	@Getter
	@Setter
	private String userName;
}
