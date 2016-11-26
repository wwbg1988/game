package com.ssic.catering.base.dto;

import java.io.Serializable;
import java.util.List;

import com.ssic.game.common.dto.ImsUsersDto;

import lombok.Getter;
import lombok.Setter;

public class MeetUserDeptDto implements Serializable{
     @Getter
     @Setter
	 private  String deptId;
     @Getter
     @Setter
	 private  String deptName;
     @Getter
     @Setter
	 private  String deptCode;
     @Getter
     @Setter
     private  List<MeetingPerCDto>  listUsers;
     
     
}
