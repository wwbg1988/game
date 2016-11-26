package com.ssic.game.manage.service;

import java.util.List;

import com.ssic.game.common.dto.ImsUsersDto;
import com.ssic.game.common.dto.PageHelperDto;

public interface IOperateUserService {

	List<ImsUsersDto> dataGrid(ImsUsersDto imsUsersDto,PageHelperDto phdto);
	
	void insertDept(ImsUsersDto user,String isExistManager);
	
	void updateDept(ImsUsersDto user);
	
	String insertUser(ImsUsersDto user);
	
	void insertUserDept2(ImsUsersDto user,String isExistManager);
	
}
