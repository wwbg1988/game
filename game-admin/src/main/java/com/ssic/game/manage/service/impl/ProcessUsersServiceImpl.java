package com.ssic.game.manage.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssic.game.common.dao.ProcessUsersDao;
import com.ssic.game.common.dto.ProcessUsersDto;
import com.ssic.game.manage.service.IProcessUsersService;

@Service
public class ProcessUsersServiceImpl implements IProcessUsersService{
   
	@Autowired
	private ProcessUsersDao processUsersDao;
	
	public List<ProcessUsersDto> findAll() {
		
		return processUsersDao.findAll();
	}

	public void insertProcU(ProcessUsersDto processUsersDto) {
		
		processUsersDao.insertProcU(processUsersDto);
	}

	public void updateProU(ProcessUsersDto processUsersDto) {

		processUsersDao.updateProU(processUsersDto);
	}

	public void deleteProU(ProcessUsersDto processUsersDto) {

		processUsersDao.deleteProU(processUsersDto);
	}

}
