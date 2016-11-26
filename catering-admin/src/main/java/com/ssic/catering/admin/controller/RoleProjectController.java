/**
 * 
 */
package com.ssic.catering.admin.controller;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssic.catering.admin.pageModel.Json;
import com.ssic.catering.admin.pageModel.Role;
import com.ssic.catering.admin.service.RoleServiceI;
import com.ssic.catering.base.dto.RoleProjectDto;
import com.ssic.catering.base.service.IRoleProjectService;

/**		
 * <p>Title: RoleProjectController </p>
 * <p>Description: 角色项目关系控制器</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 刘博	
 * @date 2015年10月26日 下午1:58:27	
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年10月26日 下午1:58:27</p>
 * <p>修改备注：</p>
 */
@Controller
@RequestMapping("/roleProjectController")
public class RoleProjectController
{

    @Autowired
    private IRoleProjectService roleProjectService;
    @Autowired
    private RoleServiceI roleService;

    /**
     * 跳转到角色授权页面
     * 
     * @return
     */
    @RequestMapping("/grantProjectPage")
    public String grantPage(HttpServletRequest request, String id)
    {
        Role r = roleService.get(id);
        RoleProjectDto roleProjectDtos = new RoleProjectDto();
        roleProjectDtos.setRoleId(r.getId());
        List<RoleProjectDto> roleProjects = roleProjectService.findAllBy(roleProjectDtos);
        RoleProjectDto roleProjectDto = new RoleProjectDto();
        if (!CollectionUtils.isEmpty(roleProjects))
        {
            int i = 0;
            String proj_id = "";
            for (RoleProjectDto roleProject : roleProjects)
            {
                if (i > 0)
                {
                    proj_id += ",";
                }
                proj_id += roleProject.getProjId();
                i++;
            }
            roleProjectDto.setProjId(proj_id);
        }

        roleProjectDto.setRoleId(r.getId());
        roleProjectDto.setRoleName(r.getName());
        roleProjectDto.setRoleMark(r.getRemark());
        request.setAttribute("roleProjectDto", roleProjectDto);
        return "admin/projectGrant";
    }

    /**
     * 授权
     * 
     * @param role 角色dto;
     * @return
     */
    @RequestMapping("/grant")
    @ResponseBody
    public Json grant(RoleProjectDto roleProjectDto)
    {
        Json j = new Json();
        if (!StringUtils.isEmpty(roleProjectDto.getRoleId())
            && !StringUtils.isEmpty(roleProjectDto.getProjId()))
        {
            RoleProjectDto roleProjectDto2 = new RoleProjectDto();
            roleProjectDto2.setRoleId(roleProjectDto.getRoleId());
            List<RoleProjectDto> list2 = roleProjectService.findAllBy(roleProjectDto2);
            if (!CollectionUtils.isEmpty(list2))
            {
                for (RoleProjectDto delete_dto : list2)
                {
                    roleProjectService.delete(delete_dto);
                }
            }
            for (String projId : roleProjectDto.getProjId().split(","))
            {
                RoleProjectDto role_ProjectDto = new RoleProjectDto();
                role_ProjectDto.setId(UUID.randomUUID().toString());
                role_ProjectDto.setRoleId(roleProjectDto.getRoleId());
                role_ProjectDto.setProjId(projId);
                roleProjectService.grantProject(role_ProjectDto);
            }

            j.setMsg("授权成功！");
            j.setSuccess(true);
        }
        else
        {
            j.setMsg("授权失败！");
            j.setSuccess(false);
        }
        return j;
    }
}
