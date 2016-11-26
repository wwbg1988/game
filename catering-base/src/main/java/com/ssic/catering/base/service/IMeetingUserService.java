package com.ssic.catering.base.service;

import java.util.List;

import com.ssic.catering.base.dto.MeetUserDeptDto;
import com.ssic.catering.base.dto.MeetingUserDto;

public interface IMeetingUserService {

	List<MeetingUserDto>  findBy(MeetingUserDto meetingUserDto);
	
	MeetingUserDto findById(String id);
	
	void insertMeetingU(MeetingUserDto meetingUserDto);
	
	void updateMeetingU(MeetingUserDto meetingUserDto);
	
	List<MeetUserDeptDto> findUserDept(String userId,String projId);
	
	void updateInMeeting(MeetingUserDto meetingUserDto);
}
