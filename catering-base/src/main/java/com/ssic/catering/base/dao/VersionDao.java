package com.ssic.catering.base.dao;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.ssic.catering.base.mapper.VersionMapper;
import com.ssic.catering.base.pojo.Version;
import com.ssic.catering.base.pojo.VersionExample;
import com.ssic.catering.base.pojo.VersionExample.Criteria;
import com.ssic.util.constants.DataStatus;

@Repository
public class VersionDao {

	@Autowired
	private VersionMapper mapper;
	
	
	public List<Version> findBy(Version param){
		
		VersionExample example = new VersionExample();
		Criteria criteria =  example.createCriteria();
		if(!StringUtils.isEmpty(param.getId())){
			criteria.andIdEqualTo(param.getId());
		}
		if(!StringUtils.isEmpty(param.getVersionname())){
			criteria.andVersionnameEqualTo(param.getVersionname());
		}
		if(param.getVersionnum()!=null){
			criteria.andVersionnumEqualTo(param.getVersionnum());
		}
		if(param.getVersiontype()!=null){
			criteria.andVersiontypeEqualTo(param.getVersiontype());
		}
		criteria.andStatEqualTo(DataStatus.ENABLED);
		example.setOrderByClause("  create_time DESC  ");
		return mapper.selectByExample(example);
	}
	
	
	
	
}
