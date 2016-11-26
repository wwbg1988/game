package com.ssic.game.manage.service;

import java.util.List;

import com.ssic.game.common.dto.ProcessUsersDto;

public interface IProcessUsersService {
	public List<ProcessUsersDto> findAll();
	public void insertProcU(ProcessUsersDto processUsersDto);
	public void updateProU(ProcessUsersDto processUsersDto);
	public void deleteProU(ProcessUsersDto processUsersDto);
}
