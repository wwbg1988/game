package com.ssic.game.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ssic.game.common.dto.MenuDto;


public interface RoleMenuExMapper {
    
	List<MenuDto> findByroleId(@Param("roleId")String roleId);

	
	/**     
	 * findByProjectId：一句话描述方法功能
	 * @param projectId
	 * @return
	 * @exception	
	 * @author yuanbin
	 * @date 2015年8月14日 上午10:08:34	 
	 */
	List<MenuDto> findByProjectId(String projectId);
	
}