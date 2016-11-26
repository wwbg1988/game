package com.ssic.game.admin.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssic.game.admin.dto.DeptLevelDto;
import com.ssic.game.admin.mapper.TImsDeptLevelExMapper;
import com.ssic.game.admin.pojo.DeptLevel;
import com.ssic.game.common.dto.PageHelperDto;
import com.ssic.util.BeanUtils;

@Repository
public class DeptLevelDao {

	
	@Autowired
	private TImsDeptLevelExMapper mapper;
	
	public List<DeptLevelDto> findAll(DeptLevelDto deptLevelDto) {
		List<DeptLevel> list = mapper.findAll(deptLevelDto);
		List<DeptLevelDto> result = new ArrayList<DeptLevelDto>();
		if(list!=null&&list.size()>0){
			 result  = BeanUtils.createBeanListByTarget(list, DeptLevelDto.class);
			return result;
		}
		return result;
	}
	public void insert(DeptLevelDto deptLevelDto){
		mapper.insert(deptLevelDto);
	}
	public void del(DeptLevelDto deptLevelDto){
		mapper.updateDel(deptLevelDto);
	}
	
	public void updateFun(DeptLevelDto deptLevelDto){
		mapper.updateFun(deptLevelDto);
	}

	public List<DeptLevelDto> findLevelAll(DeptLevelDto deptLevelDto,PageHelperDto ph){
		List<DeptLevel> list = mapper.findLevelAll(deptLevelDto,ph);
		List<DeptLevelDto> result = new ArrayList<DeptLevelDto>();
		if(list!=null&&list.size()>0){
			 result  = BeanUtils.createBeanListByTarget(list, DeptLevelDto.class);
			return result;
		}
		return result;
	}
	
	public int findLevelCount(DeptLevelDto deptLevelDto){
		return mapper.findLevelCount(deptLevelDto);
	}
}
