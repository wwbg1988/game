package com.ssic.catering.base.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.ssic.catering.base.mapper.GroupUserMapper;
import com.ssic.catering.base.pojo.GroupUser;
import com.ssic.catering.base.pojo.GroupUserExample;
import com.ssic.catering.base.pojo.GroupUserExample.Criteria;
import com.ssic.util.constants.DataStatus;

@Repository
public class GroupUserDao {

	@Autowired
	private GroupUserMapper mapper;
	
	
	public  List<GroupUser>  findBy(GroupUser param){
		GroupUserExample example = new GroupUserExample();
		Criteria criteria = example.createCriteria();
		if(!StringUtils.isEmpty(param.getId())){
			criteria.andIdEqualTo(param.getId());
		}
		if(!StringUtils.isEmpty(param.getInfoId())){
			criteria.andInfoIdEqualTo(param.getInfoId());
		}
		if(!StringUtils.isEmpty(param.getUserAccount())){
			criteria.andUserAccountEqualTo(param.getUserAccount());
		}
		if(!StringUtils.isEmpty(param.getUserId())){
			criteria.andUserIdEqualTo(param.getUserId());
		}
		criteria.andStatEqualTo(DataStatus.ENABLED);
		return mapper.selectByExample(example);
	}
	
	public GroupUser findById(String id){
		return mapper.selectByPrimaryKey(id);
	}
	
	public void insertGroupUser(GroupUser param){
		 mapper.insert(param);
	}
	
	public void updateGroupUser(GroupUser param){
	    mapper.updateByPrimaryKeySelective(param);
	}
	
	
	
	
	
}
