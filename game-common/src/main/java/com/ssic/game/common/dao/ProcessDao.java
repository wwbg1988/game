package com.ssic.game.common.dao;

import java.util.List;
import java.util.Map;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssic.game.common.dto.PageHelperDto;
import com.ssic.game.common.dto.ProcessDto;
import com.ssic.game.common.mapper.AccountTaskExMapper;
import com.ssic.game.common.mapper.ProcessExMapper;
import com.ssic.game.common.mapper.ProcessMapper;
import com.ssic.game.common.pojo.Process;
import com.ssic.util.base.MyBatisBaseDao;

@Repository
public class ProcessDao extends MyBatisBaseDao<Process>{

	@Autowired
	@Getter
	private ProcessExMapper processExMapper;
	
	@Autowired
	@Getter
	private ProcessMapper mapper;
	
	@Autowired
	@Getter
	private AccountTaskExMapper accountMapper;
	


	public List<ProcessDto> findProcess(ProcessDto processdto){
		return processExMapper.findProcess(processdto);
	}
	public Process findById(String procId){
	    return mapper.selectByPrimaryKey(procId);
	}
	
	public void insertPro(Process process){
		processExMapper.insertPro(process);
	}
	
	public void updatePro(Process process){
		processExMapper.updatePro(process);
	}
	
	public void deletePro(Process process){
		processExMapper.deletePro(process);
	}
	
	public List<Process> findInst(Process process){
		return processExMapper.findInst(process);
	}
	
	public void insertImageUrl(ProcessDto processDto){
		processExMapper.insertImageUrl(processDto);
	}
	public void updateStarTask(ProcessDto processDto){
		processExMapper.updateStarTask(processDto);
	}
	
	public List<ProcessDto> findProcessALL(ProcessDto processDto,PageHelperDto ph){
		return  processExMapper.findProcessALL(processDto,ph);
	}
	
	public int findCount(ProcessDto processDto){
		return  processExMapper.findCount(processDto);
	}
	
	public List<Map<Object,Object>> getLoadProcess(String userId){
	    return processExMapper.getLoadProcess(userId);
	}
	public List<Map<Object,Object>>getLoadAgencyTasks(String userId,String projId,String procId,String procInstId){
	    return accountMapper.getAngncy(userId, projId,procId,procInstId);
	}
	public List<Map<Object,Object>>getLoadAgencyNotProc(String userId,String projId,int beginRows,int endRows,String searchDate){
	    return accountMapper.getAngncyNotProcess(userId, projId,beginRows,endRows,searchDate);
	}
}
