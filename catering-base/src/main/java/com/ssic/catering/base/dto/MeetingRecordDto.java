package com.ssic.catering.base.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class MeetingRecordDto implements Serializable{

	@Getter
	@Setter
	private String mName;  //会议名称
	@Getter
	@Setter
	private String mid;   //会议ID
	@Getter
	@Setter
	private String title;   //会议标题
	@Getter
	@Setter
	private Date  mStarTime;  //会议开始时间
	@Getter
	@Setter
	private Date mEndTime;   //会议结束时间
	@Getter
	@Setter
	private String starEndTime;   //会议时间段 
	@Getter
	@Setter
	private String addressId;  //会议室ID
	@Getter
	@Setter
	private String dName;   //会议室名称
	@Getter
	@Setter
	private String userId;   //会议参与者ID
	@Getter
	@Setter
	private String uName;  //会议参与者姓名
	@Getter
	@Setter
	private String uNames; //会议参与者姓名集合
	@Getter
	@Setter
	private String createUserID;   //会议创建者id
	@Getter
	@Setter
	private String creatUName;   //会议创建者姓名
	@Getter
	@Setter
	private Date nyrDate;   //会议创建时间年月日
	@Getter
	@Setter
	private Integer ustate; //会议参与者是否参加  0参加   1拒绝   2待定
	@Getter
	@Setter
	private Integer isFinish;  //会议是否结束   0结束   1进行中  2未开始
	@Getter
	@Setter
	private String toStarTime;  //离会议开始还有多久
	@Getter
	@Setter
	private String deptId;   //会议发起者部门编号
	@Getter
	@Setter
	private String deptName; //会议发起这部门名称
	@Getter
	@Setter
	private Date createTime;  //会议创建时间
	@Getter
	@Setter
	private String yearDate;
	@Getter
	@Setter
	private String monthDate;
	@Getter
	@Setter
	private String dayDate;
	@Getter
	@Setter
	private Integer isInMeeting;  //当前用户能否参加这个会议     0 不能参加，不显示参加拒绝界面   1能参加，显示参加拒绝界面
	
}
