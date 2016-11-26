package com.ssic.catering.base.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

public class MeetingPerCDto implements Serializable{

	    @Getter
	    @Setter
	    private String id;
	    @Getter
	    @Setter
	    private Integer age;  //年龄
	    @Getter
	    @Setter
	    private String name;  //姓名
	    @Getter
	    @Setter
	    private String qjyAccount; //亲家云账号
	    @Getter
	    @Setter
	    private String userAccount; //登录账号
	    @Getter
	    @Setter
	    private String userNo;  //用户编号,当员工编号适用;
	
	
}
