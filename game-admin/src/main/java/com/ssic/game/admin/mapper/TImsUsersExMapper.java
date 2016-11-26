package com.ssic.game.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ssic.game.admin.dto.TImsUsersDto;
import com.ssic.game.admin.pageModel.PageHelper;
import com.ssic.game.admin.pojo.Users;

public interface TImsUsersExMapper {

	 List<TImsUsersDto> findBy(@Param("user")TImsUsersDto user);
	 
	 void insertBy(@Param("users")Users users);
	 
	 int findCountBy(@Param("user")TImsUsersDto user);
	 
	 List<TImsUsersDto> findPageBy(@Param("user")TImsUsersDto user,@Param("ph") PageHelper ph);

	void updateBy(@Param("user")TImsUsersDto user);
	void updateDelBy(@Param("userId")String id);
	void updatePwd(@Param("user")TImsUsersDto user);
	
	void updateDelByDept(@Param("deptId")String id);
	
	void addImsUsers(@Param("users")Users users);
}
