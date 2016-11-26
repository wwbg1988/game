package com.ssic.game.admin.service;

import java.util.List;

import com.ssic.game.admin.dto.TImsMenuTypeDto;
import com.ssic.game.admin.pageModel.ResourceType;


/**
 * 资源类型服务
 * 
 * @author 刘博
 * 
 */
public interface ResourceTypeServiceI {

	/**
	 * 获取资源类型
	 * 
	 * @return
	 */
	public List<ResourceType> getResourceTypeList();
	

	/**
	 * 获取资源类型(新)
	 * 
	 * @return
	 */
	public List<TImsMenuTypeDto> getMenuTypeList();

}
