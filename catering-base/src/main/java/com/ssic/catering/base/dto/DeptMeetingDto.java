package com.ssic.catering.base.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.ssic.game.common.dto.ImsUsersDto;

import lombok.Getter;
import lombok.Setter;

public class DeptMeetingDto implements Serializable{
    @Getter
    @Setter
	private String deptId;
    @Getter
    @Setter
	private String deptName;

    @Getter
    @Setter
	private Map<Object, Object> listallm ;  //  该部门每个用户当天的对应的会议记录列表
    @Getter
    @Setter
    private List<MeetingRecordDto> listmrdto ; //该部门下全部用户的会议记录列表
    @Getter
    @Setter
    private List<MeetRecordUserDto> listusers;   // 部门下的全部用户
    
	
}
