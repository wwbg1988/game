package com.ssic.catering.base.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.ssic.catering.base.dto.PageHelperDto;
import com.ssic.catering.base.mapper.GroupInfoMapper;
import com.ssic.catering.base.pojo.GroupInfo;
import com.ssic.catering.base.pojo.GroupInfoExample;
import com.ssic.catering.base.pojo.GroupInfoExample.Criteria;
import com.ssic.util.constants.DataStatus;

@Repository
public class GroupInfoDao {

	@Autowired
	private GroupInfoMapper mapper;
	
	
	public List<GroupInfo> findBy(GroupInfo param){
		GroupInfoExample example = new GroupInfoExample();
		Criteria criteria = example.createCriteria();
		if(!StringUtils.isEmpty(param.getId())){
			criteria.andIdEqualTo(param.getId());
		}
		if(!StringUtils.isEmpty(param.getParentId())){
			criteria.andParentIdEqualTo(param.getParentId());
		}
		if(!StringUtils.isEmpty(param.getProjId())){
			criteria.andProjIdEqualTo(param.getProjId());
		}
		if(!StringUtils.isEmpty(param.getGroupId())){
			criteria.andGroupIdEqualTo(param.getGroupId());
		}
		
		criteria.andStatEqualTo(DataStatus.ENABLED);
		return mapper.selectByExample(example);
	}
	
	public List<GroupInfo> findBy(GroupInfo param,PageHelperDto phdto){
		GroupInfoExample example = new GroupInfoExample();
		Criteria criteria = example.createCriteria();
		if(!StringUtils.isEmpty(param.getId())){
			criteria.andIdEqualTo(param.getId());
		}
		criteria.andStatEqualTo(DataStatus.ENABLED);
		example.setOrderByClause("  create_time desc  limit "+ phdto.getBeginRow()+ ","+phdto.getRows());
		return mapper.selectByExample(example);
	}
	
	public List<GroupInfo> findFirst(GroupInfo param){
		GroupInfoExample example = new GroupInfoExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdIsNull();
		criteria.andStatEqualTo(DataStatus.ENABLED);
		return mapper.selectByExample(example);
	}
	
	
	public int findCount(GroupInfo param){
		GroupInfoExample example = new GroupInfoExample();
		Criteria criteria = example.createCriteria();
		if(!StringUtils.isEmpty(param.getId())){
			criteria.andIdEqualTo(param.getId());
		}
		if(!StringUtils.isEmpty(param.getParentId())){
			criteria.andParentIdEqualTo(param.getParentId());
		}
		if(!StringUtils.isEmpty(param.getGroupId())){
			criteria.andGroupIdEqualTo(param.getGroupId());
		}
		criteria.andStatEqualTo(DataStatus.ENABLED);
		return mapper.countByExample(example);
	}
	
	
	public GroupInfo findById(String id){
		return mapper.selectByPrimaryKey(id);
	}
	
	public void insertGroupInfo(GroupInfo param){
		mapper.insert(param);
	}
	
	public void updateGroupInfo(GroupInfo param){
		mapper.updateByPrimaryKeySelective(param);
	}
	
	
}
