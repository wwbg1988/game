package com.ssic.game.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ssic.game.admin.pojo.MenuAndRoles;

public interface TImsRoleMenuExMapper {
	List<String> findMenuBy(@Param("roleId")String roleId);

	List<MenuAndRoles> findMenuByRoleList(@Param("roleList")List<String> roles);

	void insertBy(@Param("id")String pkId,@Param("roleId")String roleId, @Param("menuId")String menuId);

	void updateRoleMenu(@Param("menuId")String id);
}
