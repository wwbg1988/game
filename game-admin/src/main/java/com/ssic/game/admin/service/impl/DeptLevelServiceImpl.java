package com.ssic.game.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssic.game.admin.dao.DeptLevelDao;
import com.ssic.game.admin.dto.DeptLevelDto;
import com.ssic.game.admin.pageModel.PageHelper;
import com.ssic.game.admin.service.DeptLevelService;
import com.ssic.game.common.dto.PageHelperDto;

@Service
public class DeptLevelServiceImpl implements DeptLevelService {

	@Autowired
	private DeptLevelDao deptLevelDao;
	
	
	public List<DeptLevelDto> findAll(DeptLevelDto deptLevelDto) {
		return deptLevelDao.findAll(deptLevelDto);
	}


	public void insert(DeptLevelDto deptLevelDto) {
		deptLevelDao.insert(deptLevelDto);
	}


	public void del(DeptLevelDto deptLevelDto) {
		deptLevelDao.del(deptLevelDto);
	}


	public void update(DeptLevelDto deptLevelDto) {
		deptLevelDao.updateFun(deptLevelDto);
	}


	@Override
	public List<DeptLevelDto> findLevelAll(DeptLevelDto deptLevelDto,
			PageHelper ph) {
	      PageHelperDto pdto = new PageHelperDto();
	      pdto.setOrder(ph.getOrder());
	      pdto.setPage(ph.getPage());
	      pdto.setRows(ph.getRows());
	      pdto.setSort(ph.getSort());
	      pdto.setBeginRow((ph.getPage()-1)*ph.getRows());
		return deptLevelDao.findLevelAll(deptLevelDto,pdto);
	}


	@Override
	public int findLevelCount(DeptLevelDto deptLevelDto) {
		
		return deptLevelDao.findLevelCount(deptLevelDto);
	}
	


}
