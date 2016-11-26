package com.ssic.game.admin.service;

import java.util.List;

import com.ssic.game.admin.dto.DeptLevelDto;
import com.ssic.game.admin.pageModel.PageHelper;

public interface DeptLevelService {

	List<DeptLevelDto> findAll(DeptLevelDto deptLevelDto);
	
	void insert(DeptLevelDto deptLevelDto);
	
	void del(DeptLevelDto deptLevelDto);
	
	void update(DeptLevelDto deptLevelDto);
	
	List<DeptLevelDto> findLevelAll(DeptLevelDto deptLevelDto,PageHelper ph);
	
	int findLevelCount(DeptLevelDto deptLevelDto);

}
