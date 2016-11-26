package com.ssic.catering.admin.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssic.catering.admin.service.UserServiceI;
import com.ssic.catering.base.dto.AdminRoleDto;
import com.ssic.catering.base.service.IAdminRoleService;
import com.ssic.catering.base.service.IAdminUsersService;
import com.ssic.game.common.service.IParamConfigService;

/**
 * 		
 * <p>Title: AdminRoleController </p>
 * <p>Description: 团餐后台角色</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author zhuzhen	
 * @date 2015年11月25日 下午3:03:30	
 * @version 1.0
 * <p>修改人：zhuzhen</p>
 * <p>修改时间：2015年11月25日 下午3:03:30</p>
 * <p>修改备注：</p>
 */
@Controller
@RequestMapping("/carteAdminRoleController")
public class CarteAdminRoleController
{   
    @Autowired
    private IAdminRoleService adminRoleService;
    
    @Autowired
    private UserServiceI userService;
    
    @Autowired
    private IParamConfigService paramConfigService;
    
    @Autowired
    private IAdminUsersService adminUsersService;
    
    /**
     * 
     * getAdminRolesBy：查询后台角色表
     * @param req
     * @param resp
     * @param adminRoleDto
     * @return
     * @exception   
     * @author zhuzhen
     * @date 2015年11月25日 下午2:51:27
     */
    @RequestMapping("/getAdminRolesBy")
    @ResponseBody
    public List<AdminRoleDto> getAdminRolesBy(HttpServletRequest req, HttpServletResponse resp, AdminRoleDto adminRoleDto)
    {
        String projectIds = userService.getProjectIdsBySession(req.getSession());
        
        //没有权限
        if(StringUtils.isEmpty(projectIds))
        {
            return null;
        }

        //用户是否拥有要查看的项目的权限
        if(!StringUtils.isEmpty(adminRoleDto.getProjectId()) && projectIds.contains(adminRoleDto.getProjectId()))
        {
            adminRoleDto.setProjectId(adminRoleDto.getProjectId());//以传过来的参数为准
        }
        else
        {
            adminRoleDto.setProjectId(projectIds);//查看该用户所拥有的所有项目
        }
       
       return  adminRoleService.getAdminRoleDtosBy(adminRoleDto);
    }
}

