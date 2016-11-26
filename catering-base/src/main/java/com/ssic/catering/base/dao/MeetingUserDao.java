package com.ssic.catering.base.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.ssic.catering.base.mapper.MeetingUserMapper;
import com.ssic.catering.base.pojo.MeetingUser;
import com.ssic.catering.base.pojo.MeetingUserExample;
import com.ssic.catering.base.pojo.MeetingUserExample.Criteria;

@Repository
public class MeetingUserDao {

	@Autowired
	private MeetingUserMapper mapper;
	
	public List<MeetingUser> findBy(MeetingUser param){
		MeetingUserExample example = new MeetingUserExample();
		Criteria criteria = example.createCriteria();
		if(!StringUtils.isEmpty(param.getId())){
			criteria.andIdEqualTo(param.getId());
		}
		if(!StringUtils.isEmpty(param.getMeetingId())){
			criteria.andMeetingIdEqualTo(param.getMeetingId());
		}
		if(!StringUtils.isEmpty(param.getUserId())){
			criteria.andUserIdEqualTo(param.getUserId());
		}
		criteria.andStatEqualTo(param.getStat());
	
		return   mapper.selectByExample(example); 
	}
	
	public MeetingUser findById(String id){
		return mapper.selectByPrimaryKey(id);
	}
	
	public void  insertMeetingU(MeetingUser param){
		 mapper.insert(param);
	}
	
	public void updateMeetingU(MeetingUser param){
		mapper.updateByPrimaryKeySelective(param);
	}
	
	public void updateInMeeting(MeetingUser param){
		MeetingUserExample example = new MeetingUserExample();
		Criteria criteria = example.createCriteria();
		if(!StringUtils.isEmpty(param.getId())){
			criteria.andIdEqualTo(param.getId());
		}
		if(!StringUtils.isEmpty(param.getMeetingId())){
			criteria.andMeetingIdEqualTo(param.getMeetingId());
		}
		if(!StringUtils.isEmpty(param.getUserId())){
			criteria.andUserIdEqualTo(param.getUserId());
		}
		mapper.updateByExampleSelective(param, example);
	}
	
}
