package com.ssic.catering.base.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssic.catering.base.service.ICaterProjectUsersService;
import com.ssic.game.common.dao.ProjectUsersDao;
import com.ssic.game.common.dto.ProjectUsersDto;

@Service
public class CaterProjectUsersServiceImpl implements ICaterProjectUsersService{

    @Autowired
	private ProjectUsersDao projectUsersDao;
	
	
	public List<ProjectUsersDto> findAll(ProjectUsersDto projectUsersDto) {
		// TODO Auto-generated method stub
		return projectUsersDao.findAll(projectUsersDto);
	}

	public void insertPUser(ProjectUsersDto projectUsersDto) {
		// TODO Auto-generated method stub
		projectUsersDao.insertPUser(projectUsersDto);
	}

	public void updatePUser(ProjectUsersDto projectUsersDto) {
		// TODO Auto-generated method stub
		projectUsersDao.updatePUser(projectUsersDto);
	}

	public void deletePUser(ProjectUsersDto projectUsersDto) {
		// TODO Auto-generated method stub
		projectUsersDao.deletePUser(projectUsersDto);
	}

	@Override
	public List<ProjectUsersDto> findDept(ProjectUsersDto projectUsersDto) {
		// TODO Auto-generated method stub
		return projectUsersDao.findDept(projectUsersDto);
	}

	@Override
	public List<ProjectUsersDto> findProDept(ProjectUsersDto projectUsersDto) {
		// TODO Auto-generated method stub
		return projectUsersDao.findProDept(projectUsersDto);
	}
	
}
