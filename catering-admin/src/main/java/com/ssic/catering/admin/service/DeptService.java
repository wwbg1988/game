package com.ssic.catering.admin.service;

import java.util.List;

import com.ssic.game.common.dto.DeptDto;

public interface DeptService {

	public List<DeptDto> treeGrid(DeptDto deptDto);
	
	/**
	 * 
	 * combobox：一句话描述方法功能
	 * @param deptDto
	 * @return
	 * @exception	
	 * @author zhuzhen
	 * @date 2016年2月2日 下午8:28:57
	 */
	public List<DeptDto> combobox(DeptDto deptDto);
}
