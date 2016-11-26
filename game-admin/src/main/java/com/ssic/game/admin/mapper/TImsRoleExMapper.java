package com.ssic.game.admin.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ssic.game.admin.dto.RoleMenuDto;
import com.ssic.game.admin.dto.TImsMenuDto;
import com.ssic.game.admin.dto.TImsRoleDto;
import com.ssic.game.admin.dto.TImsRoleMenuDto;
import com.ssic.game.admin.dto.TImsUsersRoleDto;
import com.ssic.game.admin.pojo.MenuAndRoles;
import com.ssic.game.admin.pojo.Roles;


public interface TImsRoleExMapper {
	
	List<Roles> findBy(@Param("role")TImsRoleDto role);
	
	List<MenuAndRoles> findGreedTree(@Param("role")TImsRoleDto role);
	
	Roles findById(@Param("roleId")String roleId);
	
	Roles findByPid(@Param("pid")String pid);
	
	void inUserRole(@Param("userRole")TImsUsersRoleDto tImsUsersRoleDto);
	
	void insertRole(@Param("role")TImsRoleDto role);
	
	void updateRole(@Param("role")TImsRoleDto role);
	
	List<Roles> findByUserId(@Param("user_id")String  user_id);
	
	void updateUserRole(@Param("userRole")TImsUsersRoleDto tImsUsersRoleDto);
	
	void updateRoleStat(@Param("role")TImsRoleDto role);
	
	List<RoleMenuDto> findRoleMenu(@Param("role")TImsRoleDto role);
	
	void updateRoleMenu(@Param("roleMenu")TImsRoleMenuDto roleMenu);
	
	void insertRoleMenu(@Param("roleMenu")TImsRoleMenuDto roleMenu);
	
	List<TImsMenuDto> finSubMenus(@Param("pid")String pid );

}
