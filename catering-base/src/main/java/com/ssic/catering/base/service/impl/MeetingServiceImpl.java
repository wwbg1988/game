package com.ssic.catering.base.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssic.catering.base.dao.MeetingDao;
import com.ssic.catering.base.dto.AddressDto;
import com.ssic.catering.base.dto.AppGroupDeptDto;
import com.ssic.catering.base.dto.AppGroupDeptUserDto;
import com.ssic.catering.base.dto.MeetingAddressDto;
import com.ssic.catering.base.dto.MeetingDto;
import com.ssic.catering.base.dto.MeetingRecordDto;
import com.ssic.catering.base.dto.PageHelperDto;
import com.ssic.catering.base.pojo.Meeting;
import com.ssic.catering.base.pojo.PageHelper;
import com.ssic.catering.base.service.IMeetingService;
import com.ssic.game.common.dto.DeptDto;
import com.ssic.game.common.dto.DeptUsersDto;
import com.ssic.game.common.dto.ImsUsersDto;
import com.ssic.util.BeanUtils;


@Service
public class MeetingServiceImpl implements IMeetingService{

	@Autowired
	private MeetingDao meetingDao;
	
	@Override
	public List<MeetingDto> findBy(MeetingDto meetingDto) {
		// TODO Auto-generated method stub
		Meeting meeting = new Meeting();
		BeanUtils.copyProperties(meetingDto, meeting);
		List<Meeting> list=  meetingDao.findBy(meeting);
		List<MeetingDto> listdto = BeanUtils.createBeanListByTarget(list, MeetingDto.class);
		return listdto;
	}

	@Override
	public List<MeetingDto> findBy(MeetingDto param, PageHelper ph) {
		// TODO Auto-generated method stub
		PageHelperDto phdto = new PageHelperDto();
		 phdto.setOrder(ph.getOrder());
		 phdto.setPage(ph.getPage());
		 phdto.setRows(ph.getRows());
		 phdto.setSort(ph.getSort());
		 phdto.setBeginRow((ph.getPage()-1)*ph.getRows());
		 Meeting meeting = new Meeting();
	     BeanUtils.copyProperties(meeting, param);
	     List<Meeting> list = meetingDao.findBy(meeting,phdto);
	     List<MeetingDto> listdto = BeanUtils.createBeanListByTarget(list, MeetingDto.class);
         return listdto;
	}

	@Override
	public int count(MeetingDto meetingDto) {
		// TODO Auto-generated method stub
		Meeting meeting = new Meeting();
		BeanUtils.copyProperties(meetingDto, meeting);
		return meetingDao.count(meeting);
	}

	@Override
	public void insertMeeting(MeetingDto meetingDto) {
		// TODO Auto-generated method stub
		Meeting meeting = new Meeting();
		BeanUtils.copyProperties(meetingDto, meeting);
		meetingDao.insertMeeting(meeting);
	}

	@Override
	public void updateMeeting(MeetingDto meetingDto) {
		// TODO Auto-generated method stub
		Meeting meeting = new Meeting();
		BeanUtils.copyProperties(meetingDto, meeting);
		meetingDao.updateMeeting(meeting);
	}

	@Override
	public void deleteMeeting(MeetingDto meetingDto) {
		// TODO Auto-generated method stub
		Meeting meeting = new Meeting();
		BeanUtils.copyProperties(meetingDto, meeting);
		meetingDao.updateMeeting(meeting);
	}

	@Override
	public MeetingDto findById(String id) {
		// TODO Auto-generated method stub
		MeetingDto mDto = new MeetingDto();
	     Meeting meeting= meetingDao.findById(id);
		 BeanUtils.copyProperties(meeting, mDto);
		return mDto;
	}

	public int findStarEndTime(Date mstrDate,Date mendDate,String addressId,Date dateNYR,String projId){
		return meetingDao.findStarEndTime(mstrDate,mendDate,addressId,dateNYR,projId);
	}

	@Override
	public int findNYR(String addressId, Date dateNYR) {
		// TODO Auto-generated method stub
		return meetingDao.findNYR(addressId,dateNYR);
	}

	@Override
	public List<MeetingDto> findNYRMeet(MeetingDto meetingDto) {
		// TODO Auto-generated method stub
		return meetingDao.findNYRMeet(meetingDto);
	}

	@Override
	public List<MeetingRecordDto> findMyMeet(MeetingDto meetingDto) {
		// TODO Auto-generated method stub
		return meetingDao.findMyMeet(meetingDto);
	}

	@Override
	public List<MeetingDto> findTodayM(MeetingDto meetingDto) {
		// TODO Auto-generated method stub
		return meetingDao.findTodayM(meetingDto);
	}

	@Override
	public List<DeptDto> findDeptAll(DeptDto deptDto) {
		// TODO Auto-generated method stub
		return meetingDao.findDeptAll(deptDto);
	}

	@Override
	public List<DeptUsersDto> findUserByDept(DeptUsersDto deptUsersDto) {
		// TODO Auto-generated method stub
		return meetingDao.findUserByDept(deptUsersDto);
	}

	@Override
	public ImsUsersDto findByUserId(String id) {
		// TODO Auto-generated method stub
		return meetingDao.findByUserId(id);
	}

	@Override
	public List<AddressDto> findLargeArea() {
		// TODO Auto-generated method stub
		return meetingDao.findLargeArea();
	}

	@Override
	public List<MeetingAddressDto> findGroupArea(String projId) {
		// TODO Auto-generated method stub
		return meetingDao.findGroupArea(projId);
	}

	@Override
	public List<MeetingAddressDto> findGroupPer(String areaId) {
		// TODO Auto-generated method stub
		return meetingDao.findGroupPer(areaId);
	}

	@Override
	public List<MeetingAddressDto> findGroupCity(String perId) {
		// TODO Auto-generated method stub
		return meetingDao.findGroupCity(perId);
	}

	@Override
	public List<ImsUsersDto> findUserByProj(String projId) {
		// TODO Auto-generated method stub
		return meetingDao.findUserByProj(projId);
	}

	@Override
	public List<MeetingRecordDto> findDeptMeeting(String deptId, Date date) {
		// TODO Auto-generated method stub
		return meetingDao.findDeptMeeting(deptId,date);
	}

	@Override
	public List<AppGroupDeptDto> getdept(AppGroupDeptDto appGroupDeptDto) {
		// TODO Auto-generated method stub
		return meetingDao.getdept(appGroupDeptDto.getProjId());
	}

	@Override
	public List<AppGroupDeptUserDto> getGroupDeptUser(String deptId,String projId) {
		// TODO Auto-generated method stub
		return meetingDao.getGroupDeptUser(deptId,projId);
	}

	@Override
	public List<MeetingDto> findAddByStarEndTime(String projId,
			Date startime, Date endtime) {
		// TODO Auto-generated method stub
		return meetingDao.findAddByStarEndTime(projId,startime,endtime);
	}

}
