package com.ssic.game.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ssic.game.admin.dto.DeptLevelDto;
import com.ssic.game.admin.pojo.DeptLevel;
import com.ssic.game.common.dto.PageHelperDto;

public interface TImsDeptLevelExMapper {
	
	List<DeptLevel> findAll(@Param("dept")DeptLevelDto deptLevelDto);
	
	void insert(@Param("dept")DeptLevelDto deptLevelDto);
	
	void updateDel(@Param("dept")DeptLevelDto deptLevelDto);
	
	void updateFun(@Param("dept")DeptLevelDto deptLevelDto);
	
	List<DeptLevel> findLevelAll(@Param("dept")DeptLevelDto deptLevelDto,@Param("page") PageHelperDto ph);
	
	int findLevelCount(@Param("dept")DeptLevelDto deptLevelDto);

}
