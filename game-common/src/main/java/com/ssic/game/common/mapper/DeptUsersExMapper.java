package com.ssic.game.common.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ssic.game.common.dto.DeptUsersDto;
import com.ssic.game.common.dto.ImsUsersDto;

public interface DeptUsersExMapper {

	public List<Map<Object,Object>> findAllDeptUsr(@Param("searchName")String searchName,@Param("projId")String projId);
	
	
	public List<Map<Object,Object>>findAllUserNotExist(@Param("projId")String projId,@Param("searchName")String searchName,@Param("userId")String userId);

	  public List<DeptUsersDto> findDeptUser(@Param("deptUser")DeptUsersDto deptUsersDto);
	  
	  public void insertDeptUser(@Param("deptUser")DeptUsersDto deptUsersDto);
	  
	  public void deleteDeptUser(@Param("deptUser")DeptUsersDto deptUsersDto);
	  
	  public List<DeptUsersDto> findDept(@Param("deptUser")DeptUsersDto deptUsersDto);
	  
	  public List<DeptUsersDto> findUser(@Param("deptUser")DeptUsersDto deptUsersDto);
	  
	  public List<ImsUsersDto> searchUser(@Param("deptId")String deptId,@Param("userName") String userName);
}
