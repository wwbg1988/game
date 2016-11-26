package com.ssic.catering.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssic.catering.admin.service.DeptService;
import com.ssic.game.common.dao.DeptDao;
import com.ssic.game.common.dto.DeptDto;

@Service
public class DeptServiceImpl implements DeptService{

	@Autowired
	private DeptDao deptDao;
	
	 @Override
	    public List<DeptDto> treeGrid(DeptDto deptDto)
	    {
	        List<DeptDto> lr = deptDao.treeGrid(deptDto);
	        return lr;
	    }
	 
	 @Override
	public List<DeptDto> combobox(DeptDto deptDto)
	{
	    // TODO 添加方法注释
	    return deptDao.combobox(deptDto);
	}

}
