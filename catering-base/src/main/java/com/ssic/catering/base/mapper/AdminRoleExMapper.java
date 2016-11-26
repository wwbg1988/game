package com.ssic.catering.base.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ssic.catering.base.dto.AdminRoleDto;
import com.ssic.catering.base.pojo.AdminRoleExample;


public interface AdminRoleExMapper 
{
    /**
     * 
     * selectAdminRoleDtoByExample：一句话描述方法功能
     * @param example
     * @param projectId
     * @return
     * @exception	
     * @author zhuzhen
     * @date 2015年11月25日 下午3:44:06
     */
    List<AdminRoleDto> selectAdminRoleDtoByExample(@Param("example")AdminRoleExample example, @Param("projectId")String projectId);
    
    /**
     * 
     * selectMaxSeq：一句话描述方法功能
     * @return
     * @exception	
     * @author zhuzhen
     * @date 2015年12月2日 下午2:32:35
     */
    int selectMaxSeq();
}