package com.ssic.catering.base.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ssic.catering.base.dto.AddressDto;
import com.ssic.catering.base.dto.AppGroupDeptDto;
import com.ssic.catering.base.dto.AppGroupDeptUserDto;
import com.ssic.catering.base.dto.MeetingAddressDto;
import com.ssic.catering.base.dto.MeetingDto;
import com.ssic.catering.base.dto.MeetingRecordDto;
import com.ssic.game.common.dto.DeptDto;
import com.ssic.game.common.dto.DeptUsersDto;
import com.ssic.game.common.dto.ImsUsersDto;

public interface MeetingExMapper {

	int findStarEndTime(@Param("mstarDate")Date MstarDate ,@Param("mEndDate")Date MEndDate,@Param("addressId")String addressId,@Param("dateNYR")Date dateNYR,@Param("projId") String projId);
	
	int findNYR(@Param("addressId")String addressId,@Param("dateNYR") Date dateNYR);
	
	List<MeetingDto> findNYRMeet(@Param("meetingDto")MeetingDto meetingDto);
	
	List<MeetingRecordDto> findMyMeet(@Param("meetingDto")MeetingDto meetingDto);
	
	List<MeetingDto> findTodayM(@Param("meetingDto")MeetingDto meetingDto);
	
	List<DeptDto> findDeptAll(@Param("dept") DeptDto deptDto);
	
	List<DeptUsersDto> findUserByDept(@Param("deptUser")DeptUsersDto deptUsersDto);
	
	ImsUsersDto findByUserId(@Param("id")String id) ;
	
	List<AddressDto> findLargeArea();
	
	List<MeetingAddressDto> findGroupArea(@Param("projId") String projId);
	
	List<MeetingAddressDto> findGroupPer(@Param("areaId")String areaId);
	
	List<MeetingAddressDto> findGroupCity(@Param("perId")String perId);
	
	List<ImsUsersDto> findUserByProj(@Param("projId")String projId);
	
	List<MeetingRecordDto> findDeptMeeting(@Param("deptId")String deptId,@Param("date")Date date);
	
	List<AppGroupDeptDto> getdept(@Param("projId")String projId);
	
	List<AppGroupDeptUserDto> getGroupDeptUser(@Param("deptId")String deptId,@Param("projId")String projId);
	
	List<MeetingDto> findAddByStarEndTime(@Param("projId") String projId,@Param("startime") Date startime,@Param("endtime") Date endtime );
}
