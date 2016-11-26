package com.ssic.catering.base.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class MeetingDto implements Serializable{
	    @Getter
	    @Setter
	    private String id;
	    @Getter
	    @Setter
	    private String name;
	    @Getter
	    @Setter
	    private String title;
	    @Getter
	    @Setter
	    private Date mStarTime;
	    @Getter
	    @Setter
	    private Date mEndTime;
	    @Getter
	    @Setter
	    private String addressId;
	    @Getter
	    @Setter
	    private Integer state;
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
	    private String addressName;
	    @Getter
	    @Setter
	    private Date nyrDate; 
	    @Getter
	    @Setter
	    private String  musers;
	    @Getter
	    @Setter
	    private String createuserid;
	    @Getter
	    @Setter
	    private String muserId;
	    @Getter
	    @Setter
	    private String createuserName;
	    @Getter
	    @Setter
	    private Integer beginRow;
	    @Getter
	    @Setter
	    private Integer endRow; 
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
	    private Integer flagis;
	    @Getter
	    @Setter
	    private String projId;
	    @Getter
	    @Setter
	    private Date nowDate;
}
