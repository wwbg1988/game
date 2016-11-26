package com.ssic.catering.base.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import com.ssic.game.common.dto.SignDto;

public class SignDeptUserDto implements Serializable{

	@Getter
	@Setter
	private List<SignDto> signlist;    //当前部门下全部的考勤签到信息
	@Getter
	@Setter
    private List<MeetRecordUserDto> listusers;   // 部门下的全部用户 
	
	
	
}
