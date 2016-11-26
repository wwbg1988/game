package com.ssic.catering.base.dao;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.ssic.catering.base.dto.AddressDto;
import com.ssic.catering.base.dto.AppGroupDeptDto;
import com.ssic.catering.base.dto.AppGroupDeptUserDto;
import com.ssic.catering.base.dto.MeetingAddressDto;
import com.ssic.catering.base.dto.MeetingDto;
import com.ssic.catering.base.dto.MeetingRecordDto;
import com.ssic.catering.base.dto.PageHelperDto;
import com.ssic.catering.base.mapper.MeetingExMapper;
import com.ssic.catering.base.mapper.MeetingMapper;
import com.ssic.catering.base.pojo.Meeting;
import com.ssic.catering.base.pojo.MeetingExample;
import com.ssic.catering.base.pojo.MeetingExample.Criteria;
import com.ssic.game.common.dto.DeptDto;
import com.ssic.game.common.dto.DeptUsersDto;
import com.ssic.game.common.dto.ImsUsersDto;
import com.ssic.util.constants.DataStatus;

@Repository
public class MeetingDao {

	@Autowired
	private MeetingMapper mapper;
	
	@Autowired
	private MeetingExMapper exmapper;
	
	public List<Meeting> findBy(Meeting param){		
		MeetingExample example = new MeetingExample();
		Criteria criteria = example.createCriteria();
		if(!StringUtils.isEmpty(param.getId())){
			criteria.andIdEqualTo(param.getId());
		}
	    criteria.andStatEqualTo(DataStatus.ENABLED);
	    return   mapper.selectByExample(example);
	}
	
	public List<Meeting> findBy(Meeting param,PageHelperDto phdto){
		MeetingExample example = new MeetingExample();
		Criteria criteria = example.createCriteria();
		if(!StringUtils.isEmpty(param.getId())){
			criteria.andIdEqualTo(param.getId());
		}
	    criteria.andStatEqualTo(DataStatus.ENABLED);
	    example.setOrderByClause("  create_time desc  limit "+ phdto.getBeginRow()+ ","+phdto.getRows());
	    return   mapper.selectByExample(example);
	}
	
	public int count(Meeting param){
		MeetingExample example = new MeetingExample();
		Criteria criteria = example.createCriteria();
		if(!StringUtils.isEmpty(param.getId())){
			criteria.andIdEqualTo(param.getId());
		}
		return mapper.countByExample(example);
	}
	
	public void insertMeeting(Meeting param){
		mapper.insert(param);
	}
	
	public void updateMeeting(Meeting param){
		mapper.updateByPrimaryKeySelective(param);
	}
	
	public Meeting findById(String id){
		return  mapper.selectByPrimaryKey(id);
	}
	
	public int findStarEndTime(Date mstrDate,Date mendDate,String addressId,Date dateNYR,String projId){
		return exmapper.findStarEndTime(mstrDate, mendDate,addressId,dateNYR,projId);
	}
	
	public int findNYR(String addressId, Date dateNYR){
		return exmapper.findNYR(addressId,dateNYR);
	}
	
	public List<MeetingDto> findNYRMeet(MeetingDto meetingDto){
		return exmapper.findNYRMeet(meetingDto);
	}
	
	public List<MeetingRecordDto> findMyMeet(MeetingDto meetingDto){
		return exmapper.findMyMeet(meetingDto);
	}
	
	public List<MeetingDto> findTodayM(MeetingDto meetingDto){
		return exmapper.findTodayM(meetingDto);
	}
	
	public List<DeptDto> findDeptAll(DeptDto deptDto){
		return exmapper.findDeptAll(deptDto);
	}
	
	public List<DeptUsersDto> findUserByDept(DeptUsersDto deptUsersDto) {
		return exmapper.findUserByDept(deptUsersDto);
	}
	
	public ImsUsersDto findByUserId(String id) {
		return exmapper.findByUserId(id);
	}
	
	public List<AddressDto> findLargeArea(){
		return exmapper.findLargeArea();
	}
	
	public List<MeetingAddressDto> findGroupArea(String projid){
		return exmapper.findGroupArea(projid);
	}
	
	public List<MeetingAddressDto> findGroupPer(String areaId){
		return exmapper.findGroupPer(areaId);
	}
	
	public List<MeetingAddressDto> findGroupCity(String perId){
		return exmapper.findGroupCity(perId);
	}
	
	public List<ImsUsersDto> findUserByProj(String projId){
		return exmapper.findUserByProj(projId);
	}
	
	public List<MeetingRecordDto> findDeptMeeting(String deptId,Date date){
		return exmapper.findDeptMeeting(deptId,date);
	}
	
	public List<AppGroupDeptDto> getdept(String projId){
		return exmapper.getdept(projId);
	}
	
	public List<AppGroupDeptUserDto> getGroupDeptUser(String deptId,String projId){
		return exmapper.getGroupDeptUser(deptId,projId);
	}
	
	public List<MeetingDto> findAddByStarEndTime(String projId,
			Date startime, Date endtime ){
		return exmapper.findAddByStarEndTime(projId,startime,endtime);
	}
	
}
