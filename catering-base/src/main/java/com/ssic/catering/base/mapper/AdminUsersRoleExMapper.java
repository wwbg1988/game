package com.ssic.catering.base.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ssic.catering.base.dto.AdminUsersDto;
import com.ssic.catering.base.pojo.AdminUsersRoleExample;

public interface AdminUsersRoleExMapper {
   
    /**
     * 
     * selectAdminUsersDtosBy：一句话描述方法功能
     * @param example
     * @return
     * @exception	
     * @author zhuzhen
     * @date 2015年12月2日 下午4:11:35
     */
    List<AdminUsersDto> selectAdminUsersDtosBy(@Param("example")AdminUsersRoleExample example);
    
    /**
     * 
     * selectAdminUsersDtosBy：一句话描述方法功能
     * @param example
     * @return
     * @exception   
     * @author zhuzhen
     * @date 2015年12月2日 下午4:11:35
     */
    long selectAdminUsersDtoCountBy(@Param("example")AdminUsersRoleExample example);
}