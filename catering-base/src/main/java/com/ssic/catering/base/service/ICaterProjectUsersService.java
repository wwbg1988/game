package com.ssic.catering.base.service;

import java.util.List;

import com.ssic.game.common.dto.ProjectUsersDto;

public interface ICaterProjectUsersService {

public List<ProjectUsersDto> findAll(ProjectUsersDto projectUsersDto);
	
	public void insertPUser(ProjectUsersDto projectUsersDto);
	
	public void updatePUser(ProjectUsersDto projectUsersDto );
	
	public void deletePUser(ProjectUsersDto projectUsersDto);
	
	public List<ProjectUsersDto> findDept(ProjectUsersDto projectUsersDto);
	
	public List<ProjectUsersDto> findProDept(ProjectUsersDto projectUsersDto);
	
}
