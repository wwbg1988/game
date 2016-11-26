package com.ssic.game.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;


public interface TImsUserRoleExMapper {
	
	void deleteRoleByUserId(@Param("userId")String id);
	
	void insertBy(@Param("id")String pkId,@Param("userId")String id,@Param("roleId")String roleId);
	
	List<String> findRoleBy(@Param("userId")String userId);
	
	int findCountRoleBy(@Param("userId")String userId,@Param("roleId")String roleId);
	void updateBy(@Param("userId")String id,@Param("roleId")String roleId);

}
