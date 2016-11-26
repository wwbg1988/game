package com.ssic.catering.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ssic.catering.admin.dto.TImsUsersDto;
import com.ssic.catering.admin.pageModel.PageHelper;
import com.ssic.catering.admin.pojo.Users;
import com.ssic.game.common.dto.ProjectDto;

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
	
	/**
	 * 通过userId获取项目信息	 
	 * @author 朱振	
	 * @date 2015年10月26日 下午5:48:21	
	 * @version 1.0
	 * @param userId
	 * @return
	 * <p>修改人：朱振</p>
	 * <p>修改时间：2015年10月26日 下午5:48:21</p>
	 * <p>修改备注：</p>
	 */
	List<ProjectDto> findByUserId(String userId);
}
