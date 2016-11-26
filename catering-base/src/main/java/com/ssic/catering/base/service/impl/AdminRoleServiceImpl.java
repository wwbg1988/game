package com.ssic.catering.base.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssic.catering.base.dao.AdminRoleDao;
import com.ssic.catering.base.dto.AdminRoleDto;
import com.ssic.catering.base.service.IAdminRoleService;

@Service
public class AdminRoleServiceImpl implements IAdminRoleService
{
    /**
     * 团餐后台角色
     */
    @Autowired
    private AdminRoleDao adminRoleDao;
    
    /**
     * 
     * (non-Javadoc)   
     * @see com.ssic.catering.base.service.IAdminRoleService#getAdminRoleDtosBy(com.ssic.catering.base.dto.AdminRoleDto)
     */
    @Override
    public List<AdminRoleDto> getAdminRoleDtosBy(AdminRoleDto adminRoleDto)
    {
        return adminRoleDao.findAdminRoleDtosBy(adminRoleDto);
    }
}

