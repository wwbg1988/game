package com.ssic.game.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ssic.game.common.dto.PageHelperDto;
import com.ssic.game.common.dto.RoleUsersDto;
import com.ssic.game.common.dto.TmsRoleDto;

public interface TimsRoleExMapper {

	public List<TmsRoleDto> findBy(@Param("role") TmsRoleDto tmsRoleDto);

	public void insertRole(@Param("role") TmsRoleDto tmsRoleDto);
	
	public void updateRole(@Param("role") TmsRoleDto tmsRoleDto);
	
	public void deleteRole(@Param("role") TmsRoleDto tmsRoleDto);
	
	public List<RoleUsersDto> findRoleUser(@Param("roleUser") RoleUsersDto  roleUsersDto);
	
	public void delRoleUseById(@Param("roleUser") RoleUsersDto  roleUsersDto);
	
	public void insertRoleUser(@Param("roleUser") RoleUsersDto  roleUsersDto);
	
	public List<TmsRoleDto> findByAll(@Param("role") TmsRoleDto tmsRoleDto,@Param("page")PageHelperDto ph);
	
	public int findCount(@Param("role") TmsRoleDto tmsRoleDto);
	
	public List<TmsRoleDto> findByUserId(@Param("userId")String userId);
	
	public List<TmsRoleDto> findTreeRoles(@Param("userId")String userId);
	
	public List<RoleUsersDto> findUserNames(@Param("roleUser") RoleUsersDto  roleUsersDto);
}
