package com.ssic.game.admin.controller.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssic.game.admin.pageModel.DataGrid;
import com.ssic.game.admin.pageModel.Json;
import com.ssic.game.admin.pageModel.PageHelper;
import com.ssic.game.admin.pageModel.Role;
import com.ssic.game.admin.pageModel.SessionInfo;
import com.ssic.game.admin.pageModel.Tree;
import com.ssic.game.admin.service.ProjectService;
import com.ssic.game.admin.util.ConfigUtil;
import com.ssic.game.common.dto.ActionsDto;
import com.ssic.game.common.dto.ProjectDto;
import com.ssic.game.common.dto.TasksDto;
import com.ssic.util.UUIDGenerator;

@Controller
@RequestMapping("/ProjectController")
public class ProjectController
{

    @Autowired
    private ProjectService projectService;

    @ResponseBody
    @RequestMapping("/findAll")
    public List<ProjectDto> findAllDept()
    {
        List<ProjectDto> dtoList = projectService.findAll();

        return dtoList;
    }

    @RequestMapping("/manager")
    public String manager()
    {
        return "ims/project";
        //return "ims/projectTest";
    }

    @RequestMapping("/addPage")
    public String addPage(HttpServletRequest request)
    {

        return "ims/projectAdd";
    }

    @RequestMapping("/editProject")
    public String editTasks(HttpServletRequest request, String id)
    {
        ProjectDto projectDto = new ProjectDto();
        ProjectDto pDto = new ProjectDto();
        projectDto.setId(id);
        List<ProjectDto> listproject = projectService.findByIdName(projectDto);
        if (listproject != null && listproject.size() > 0)
        {
            pDto = listproject.get(0);
        }
        request.setAttribute("pDto", pDto);

        return "ims/projectEdit";
    }

    @ResponseBody
    @RequestMapping("/dataGrid")
    public DataGrid dataGrid(ProjectDto projectDto, PageHelper ph)
    {
        DataGrid dataGrid = new DataGrid();
        List<ProjectDto> list = projectService.findByIdNameAll(projectDto, ph);
        int count = projectService.findCount(projectDto);
        dataGrid.setRows(list);
        dataGrid.setTotal(Long.valueOf(count));
        return dataGrid;
    }

    /**
     * 获得角色列表
     * 
     * @return
     */
    @RequestMapping("/treeGrid")
    @ResponseBody
    public List<Role> treeGrid(ProjectDto projectDto)
    {
        //SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.SESSIONINFONAME);
        return projectService.treeGrid(projectDto);
    }

    @RequestMapping("/insert")
    @ResponseBody
    public Json insert(ProjectDto projectDto)
    {
        Json j = new Json();

        if (projectDto == null)
        {
            j.setMsg("项目对象不存在");
            j.setSuccess(false);
            return j;
        }
        if (projectDto.getProjName() == null || projectDto.getProjName().equals(""))
        {
            j.setMsg("项目名称不能为空");
            j.setSuccess(false);
            return j;
        }
        if (projectDto.getProjName().length() > 30)
        {
            j.setMsg("项目名称长度不能大于30");
            j.setSuccess(false);
            return j;
        }
        if (projectDto.getDescribes() != null)
        {
            if (projectDto.getDescribes().length() > 150)
            {
                j.setMsg("描述长度不能大于150");
                j.setSuccess(false);
                return j;
            }
        }

        projectDto.setStat(true);
        projectDto.setId(UUIDGenerator.getUUID());
        projectDto.setCreateTime(new Date());
        projectService.insert(projectDto);
        j.setMsg("创建项目成功");
        j.setSuccess(true);
        return j;
    }

    @RequestMapping("/update")
    @ResponseBody
    public Json update(ProjectDto projectDto)
    {
        Json j = new Json();

        if (projectDto == null)
        {
            j.setMsg("项目对象不能为空");
            j.setSuccess(false);
            return j;
        }
        if (projectDto.getProjName() == null || projectDto.getProjName().equals(""))
        {
            j.setMsg("项目名称不能为空");
            j.setSuccess(false);
            return j;
        }
        if (projectDto.getProjName().length() > 30)
        {
            j.setMsg("项目名称长度不能大于30");
            j.setSuccess(false);
            return j;
        }
        if (projectDto.getDescribes() != null)
        {
            if (projectDto.getDescribes().length() > 150)
            {
                j.setMsg("描述长度不能大于150");
                j.setSuccess(false);
                return j;
            }
        }

        projectDto.setLastUpdateTime(new Date());
        projectService.update(projectDto);
        j.setMsg("更新项目成功");
        j.setSuccess(true);
        return j;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Json delete(ProjectDto projectDto)
    {
        Json j = new Json();
        //String id = projectDto.getId();
        projectService.deleteById(projectDto);
        j.setMsg("删除项目成功");
        j.setSuccess(true);
        return j;
    }

    @RequestMapping("/grantUserPage")
    public String grantUserPage(String ids, HttpServletRequest request, HttpSession session)
    {
        request.setAttribute("ids", ids);
        session.setAttribute("grantUserPerss", ids);
        Role u = new Role();
        if (ids != null && !ids.equalsIgnoreCase(""))
        {

            String result = projectService.findUserPers(ids);
            //	            String userPer = actionService.findUserPers(ids, projId);
            u.setResourceIds(result);
            //	            u.setUserIds(userPer);
            request.setAttribute("role", u);
        }
        return "ims/ProjectGrant";
    }

    /**
     * 角色树(只能看到自己拥有的角色)
     * 
     * @return
     */
    @RequestMapping("/userTree")
    @ResponseBody
    public List<Tree> userTree(String searchName, String projId)
    {
        return projectService.userTree(searchName, projId);
        //	return null;
    }

    /**
     * 用户授权
     * 
     * @param ids
     * @return
     */
    @RequestMapping("/grantUsers")
    @ResponseBody
    public Json grantUsers(String resourceIds, HttpSession session)
    {
        Json j = new Json();

        if (session.getAttribute("grantUserPerss") != null)
        {
            projectService.grantUser(session.getAttribute("grantUserPerss").toString(), resourceIds);
            j.setSuccess(true);
            j.setMsg("授权成功！");
            return j;
        }
        else
        {
            j.setSuccess(false);
            j.setMsg("寻找不到动作id，请重新赋用户权限!");
            return j;
        }

    }

    /**
     * 获得项目树
     * @return
     */
    @RequestMapping("/allTree")
    @ResponseBody
    public List<Tree> allTree()
    {
        return projectService.allTree();
    }

}
