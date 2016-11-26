package com.ssic.game.admin.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssic.game.admin.mapper.TImsUserRoleExMapper;


@Repository
public class TImsUsersRolesDao {
	
	@Autowired
	private TImsUserRoleExMapper mapper;

	public List<String> findBy(String userId) {
		return mapper.findRoleBy(userId);
	}

}
