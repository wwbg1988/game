package com.ssic.catering.base.service;

import java.util.List;

import com.ssic.catering.base.dto.GroupUserDto;

public interface IGroupUserService {

	List<GroupUserDto> findBy(GroupUserDto groupUserDto);
	
	void insertGroupUser(GroupUserDto groupUserDto);
	
	void updateGroupUser(GroupUserDto groupUserDto);
	
	GroupUserDto findById(String id);
	
	String findByGroupUser(String infoid);
	
	void grantUser(String userId,String rolesId);

}
