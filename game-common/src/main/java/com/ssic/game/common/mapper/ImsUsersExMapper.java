package com.ssic.game.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ssic.game.common.dao.ImsUserDao;
import com.ssic.game.common.dto.DeptUsersDto;
import com.ssic.game.common.dto.ImsUsersDto;
import com.ssic.game.common.dto.PageHelperDto;
import com.ssic.game.common.dto.RoleUsersDto;

public interface ImsUsersExMapper {

	List<ImsUsersDto> findUsers(@Param("user")ImsUsersDto imsUsersDto);
	
	ImsUsersDto userAccountByUser(@Param("userAccount")String userAccount);
	
	void insertUser(@Param("user")ImsUsersDto imsUsersDto);
	
	void updateUser(@Param("user")ImsUsersDto imsUsersDto);
	
	void updateUserByuserId(@Param("user")ImsUsersDto imsUsersDto);
	
	void deleteUser(@Param("user")ImsUsersDto imsUsersDto);
	
	List<ImsUsersDto> findUsersAll(@Param("user")ImsUsersDto imsUsersDto,@Param("page")PageHelperDto ph);
	
	int findCount(@Param("user")ImsUsersDto imsUsersDto);
	
	int vailUserAccount(@Param("user")ImsUsersDto imsUsersDto);
	
	List<String> findRoleId(@Param("userId")String userId);
	
	void insertRoleUsers(@Param("roleUser")RoleUsersDto roleUsersDto);
	
	void deleteRole(@Param("userId")String userId);
	
	void upPasswod(@Param("user")ImsUsersDto imsUsersDto);
	
	void deleteproUser(@Param("user")ImsUsersDto imsUsersDto);
	
	void updateDept(@Param("userDept") DeptUsersDto deptUsersDto);
	
	void deleteDept(@Param("userDept") DeptUsersDto deptUsersDto);
	
	List<ImsUsersDto> findByProjId(@Param("projId") String projId);
	
	List<ImsUsersDto> findBySearchName(@Param("user")ImsUsersDto imsUsersDto);
	
	
}
