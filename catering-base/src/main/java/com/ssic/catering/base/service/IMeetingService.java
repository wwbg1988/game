package com.ssic.catering.base.service;

import java.util.Date;
import java.util.List;

import com.ssic.catering.base.dto.AddressDto;
import com.ssic.catering.base.dto.AppGroupDeptDto;
import com.ssic.catering.base.dto.AppGroupDeptUserDto;
import com.ssic.catering.base.dto.MeetingAddressDto;
import com.ssic.catering.base.dto.MeetingDto;
import com.ssic.catering.base.dto.MeetingRecordDto;
import com.ssic.catering.base.pojo.PageHelper;
import com.ssic.game.common.dto.DeptDto;
import com.ssic.game.common.dto.DeptUsersDto;
import com.ssic.game.common.dto.ImsUsersDto;

public interface IMeetingService {

	List<MeetingDto> findBy(MeetingDto meetingDto);
	
	List<MeetingDto> findBy(MeetingDto meetingDto,PageHelper ph);
	
	int count(MeetingDto meetingDto);
	
	void insertMeeting(MeetingDto meetingDto);
	
	void updateMeeting(MeetingDto meetingDto);
	
	void deleteMeeting(MeetingDto meetingDto);
	
	MeetingDto findById(String id);
	
    int findStarEndTime(Date mstrDate,Date mendDate,String addressId,Date dateNYR,String projID);
    
    int findNYR(String addressId , Date dateNYR);
	
    List<MeetingDto> findNYRMeet(MeetingDto meetingDto);
    
    List<MeetingRecordDto> findMyMeet(MeetingDto meetingDto);
    
    List<MeetingDto> findTodayM(MeetingDto meetingDto);
    
    List<DeptDto>  findDeptAll(DeptDto deptDto );
    
    List<DeptUsersDto>  findUserByDept(DeptUsersDto deptUsersDto);
    
    ImsUsersDto findByUserId(String id);
    
    List<AddressDto> findLargeArea();
    
    List<MeetingAddressDto> findGroupArea(String projId);
    
    List<MeetingAddressDto> findGroupPer(String areaId);
    
    List<MeetingAddressDto> findGroupCity(String perId);
    
    List<ImsUsersDto>   findUserByProj(String projId);
    
    List<MeetingRecordDto> findDeptMeeting(String deptId,Date date);
    
    List<AppGroupDeptDto> getdept(AppGroupDeptDto appGroupDeptDto);
    
    List<AppGroupDeptUserDto> getGroupDeptUser(String deptId,String projId);
    
    List<MeetingDto> findAddByStarEndTime(String projId,Date startime,Date endtime);
    
}
