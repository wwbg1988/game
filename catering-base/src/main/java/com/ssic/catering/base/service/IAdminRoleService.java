package com.ssic.catering.base.service;

import java.util.List;

import com.ssic.catering.base.dto.AdminRoleDto;

/**
 * 		
 * <p>Title: AdminRoleService </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author zhuzhen	
 * @date 2015年11月25日 下午3:59:26	
 * @version 1.0
 * <p>修改人：zhuzhen</p>
 * <p>修改时间：2015年11月25日 下午3:59:26</p>
 * <p>修改备注：</p>
 */
public interface IAdminRoleService
{
    /**
     * 
     * getAdminRoleDtosBy：查询团餐角色表
     * @param adminRoleDto
     * @return
     * @exception	
     * @author zhuzhen
     * @date 2015年11月25日 下午4:00:42
     */
    List<AdminRoleDto> getAdminRoleDtosBy(AdminRoleDto adminRoleDto);
}

