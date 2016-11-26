package com.ssic.game.common.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ssic.game.common.dto.PageHelperDto;
import com.ssic.game.common.dto.ProcessDto;
import com.ssic.game.common.pojo.Process;

public interface ProcessExMapper {

	

	List<ProcessDto> findProcess(@Param("process")ProcessDto processdto);
	
	void insertPro(@Param("process") Process process);
	
	void updatePro(@Param("process") Process process);
	
	void deletePro(@Param("process") Process process);
	
	List<Process> findInst(@Param("process") Process process);
	
	void insertImageUrl(@Param("process") ProcessDto processDto);
	
	void updateStarTask(@Param("process") ProcessDto processDto);
	
	List<ProcessDto> findProcessALL(@Param("process") ProcessDto processDto,@Param("page")PageHelperDto phdto);
	
	int findCount(@Param("process")ProcessDto processdto);
	
	List<Map<Object,Object>> getLoadProcess(@Param("userId")String userId);
}
