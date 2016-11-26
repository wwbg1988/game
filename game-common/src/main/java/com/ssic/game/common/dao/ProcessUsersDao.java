package com.ssic.game.common.dao;

import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.ssic.game.common.dto.ProcessUsersDto;
import com.ssic.game.common.mapper.ProcessUsersExMapper;
import com.ssic.game.common.mapper.ProcessUsersMapper;
import com.ssic.game.common.pojo.ProcessUsers;
import com.ssic.game.common.pojo.ProcessUsersExample;
import com.ssic.game.common.pojo.ProcessUsersExample.Criteria;
import com.ssic.util.base.MyBatisBaseDao;

@Repository
public class ProcessUsersDao extends MyBatisBaseDao<ProcessUsers>{
	
	@Autowired
	private ProcessUsersExMapper processUsersExMapper;
	@Autowired
	@Getter
	private ProcessUsersMapper mapper;
	
	public List<ProcessUsers> findBy(ProcessUsers param){
	    ProcessUsersExample example = new ProcessUsersExample();
	    Criteria criteria = example.createCriteria();
	    if(!StringUtils.isEmpty(param.getUserId())){
	        criteria.andUserIdEqualTo(param.getUserId());
	    }
	    criteria.andStatEqualTo(1);
	   return  mapper.selectByExample(example);
	}
	public List<ProcessUsersDto> findAll(){
		
		return processUsersExMapper.findAll();
	} 
	
	public void insertProcU(ProcessUsersDto processUsersDto) {
		processUsersExMapper.insertProcU(processUsersDto);
	}

	public void updateProU(ProcessUsersDto processUsersDto) {
		processUsersExMapper.updateProU(processUsersDto);
	}

	public void deleteProU(ProcessUsersDto processUsersDto) {
		processUsersExMapper.deleteProU(processUsersDto);
	}
	
	public List<ProcessUsersDto> findById(ProcessUsersDto processUsersDto){
	   return 	processUsersExMapper.findById(processUsersDto);
	}
	
	public void deleteByProId(ProcessUsersDto processUsersDto){
		processUsersExMapper.deleteByProId(processUsersDto);
	}
	

}
