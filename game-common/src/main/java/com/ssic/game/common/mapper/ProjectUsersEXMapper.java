package com.ssic.game.common.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ssic.game.common.dto.ProjectUsersDto;
import com.ssic.game.common.pojo.Project;

public interface ProjectUsersEXMapper {

	public List<ProjectUsersDto> findAll(@Param("projectusers")ProjectUsersDto projectUsersDto);
	
	public void insertPUser(@Param("projectusers")ProjectUsersDto projectUsersDto);
	
	public void updatePUser(@Param("projectusers")ProjectUsersDto projectUsersDto );
	
	public void deletePUser(@Param("projectusers")ProjectUsersDto projectUsersDto);
	
	public void deleteByProjid(@Param("projectusers")ProjectUsersDto projectUsersDto);
	
	public List<Map<Object,Object>> findAllUserRole(@Param("projId")String projId,@Param("searchName")String searchName);
	
	public List<Map<Object,Object>>findAllUserNotExist(@Param("projId")String projId,@Param("searchName")String searchName,@Param("userId")String userId);
	public List<ProjectUsersDto> findDept(@Param("projectusers")ProjectUsersDto projectUsersDto);
	
	public List<ProjectUsersDto> findUsers(@Param("projectusers")ProjectUsersDto projectUsersDto);
	
	public List<ProjectUsersDto> findProDept(@Param("projectusers")ProjectUsersDto projectUsersDto);
	
	/**
	 * @author 朱振	
	 * @date 2015年10月29日 上午11:41:33	
	 * @version 1.0
	 * @param userId
	 * @return
	 * <p>修改人：朱振</p>
	 * <p>修改时间：2015年10月29日 上午11:41:33</p>
	 * <p>修改备注：</p>
	 */
	Project findProjectByIMSUserId(String userId);
}
