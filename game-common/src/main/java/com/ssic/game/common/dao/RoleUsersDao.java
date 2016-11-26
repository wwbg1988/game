package com.ssic.game.common.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.ssic.game.common.dto.RoleUsersDto;
import com.ssic.game.common.mapper.RoleUsersMapper;
import com.ssic.game.common.pojo.RoleUsersExample;
import com.ssic.game.common.pojo.RoleUsersExample.Criteria;


@Repository
public class RoleUsersDao {

	@Autowired
	private RoleUsersMapper mapper;
	
	
	public void deleteRoleUser(RoleUsersDto param){
		RoleUsersExample example = new RoleUsersExample();
		Criteria criteria = example.createCriteria();
		if(!StringUtils.isEmpty(param.getUserId())){
			criteria.andUserIdEqualTo(param.getUserId());
		}
		mapper.deleteByExample(example);
	}
	
}
