package com.ssic.game.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ssic.game.common.dto.ProcessDto;
import com.ssic.game.common.dto.ProcessUsersDto;

public interface ProcessUsersExMapper {
	public List<ProcessUsersDto> findAll();
	public void insertProcU(@Param("processUsers")ProcessUsersDto processUsersDto);
	public void updateProU(@Param("processUsers")ProcessUsersDto processUsersDto);
	public void deleteProU(@Param("processUsers")ProcessUsersDto processUsersDto);
	
	public List<ProcessUsersDto> findById(@Param("processUsers")ProcessUsersDto processUsersDto);
	
	public void deleteByProId(@Param("processUsers")ProcessUsersDto processUsersDto);
	
	
}
