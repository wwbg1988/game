package com.ssic.game.manage.controller;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssic.game.common.dao.DeptDao;
import com.ssic.game.admin.dto.DeptLevelDto;
import com.ssic.game.admin.pageModel.DataGrid;
import com.ssic.game.admin.pageModel.Json;
import com.ssic.game.admin.pageModel.PageHelper;
import com.ssic.game.admin.pageModel.Tree;
import com.ssic.game.admin.service.DeptLevelService;
import com.ssic.game.admin.service.ProjectService;
import com.ssic.game.common.dto.DeptDto;
import com.ssic.game.common.dto.DeptUsersDto;
import com.ssic.game.common.dto.ProjectDto;
import com.ssic.game.common.service.DeptUserService;
import com.ssic.game.manage.service.DeptService;
import com.ssic.game.manage.service.IProjectUsersService;

@Controller
@RequestMapping("/deptController")
public class DeptController
{
    @Autowired
    private DeptService deptService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private DeptLevelService deptLevelService;

    @Autowired
    private DeptUserService deptUserService;

    @Autowired
    private IProjectUsersService iprojectUsersService;

    @Autowired
    private DeptDao deptDao;

    @ResponseBody
    @RequestMapping("/findAll")
    public List<DeptDto> findAllDept()
    {
        return deptService.findAll();
    }

    /**
     * 跳转到部门管理页面
     * 
     * @return
     */
    @RequestMapping("/manager")
    public String manager()
    {
        return "ims/dept/dept";
    }


    @RequestMapping("/treeGrid")
    @ResponseBody
    public List<DeptDto> treeGrid(DeptDto deptDto)
    {

        return deptService.treeGrid(deptDto);
    }

    @RequestMapping("/tree")
    @ResponseBody
    public List<Tree> tree(String deptId, String projId)
    {
        return deptService.tree(deptId, projId);
    }

    /**
     * 跳转到添加用户页面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/addPage")
    public String addPage(HttpServletRequest request, String projId)
    {
        ProjectDto projDto = projectService.findById(projId);
        DeptDto u = new DeptDto();
        u.setProjName(projDto.getProjName());
        u.setProjId(projDto.getId());
        u.setId(UUID.randomUUID().toString());
        request.setAttribute("deptDto", u);
        return "ims/dept/deptAdd";
    }

    /**
     * 添加部门
     * 
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public Json add(DeptDto dept)
    {
        Json j = new Json();
        DeptDto tempDept = new DeptDto();
        tempDept.setDeptName(dept.getDeptName());
        tempDept.setStat(1);
        if (StringUtils.isEmpty(dept.getPid()))
        { //如果添加的部门的没有父节点,则去数据库查找有没有一条父节点为空的记录，有的话，则提示不能添加。根节点只能有一个.
            DeptDto dto = deptService.validDeptRootExists(dept.getId(), dept.getProjId());
            if (dto != null)
            {
                j.setSuccess(false);
                j.setMsg("根节点部门是唯一的，添加失败!");
                return j;
            }
        }
        tempDept.setProjId( dept.getProjId());
        int counts = deptService.vailDeptName(tempDept);
        if (counts > 0)
        {
            j.setSuccess(false);
            j.setMsg("部门已存在");
            return j;
        }
        try
        {
            dept.setStat(1);
            dept.setCreateTime(new Date());
            deptService.setDeptCode(dept);
            deptService.add(dept);
            j.setSuccess(true);
            j.setMsg("添加部门成功！");
            j.setObj(dept);
        }
        catch (Exception e)
        {
            // e.printStackTrace();
            j.setMsg(e.getMessage());
        }
        return j;
    }

    /**
     * 删除部门
     * 
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Json delete(String id)
    {
        Json j = new Json();
        List<DeptDto> listDepts = deptService.findChildListByParentId(id);
        //部门子集合是否存在用户判断;
        if (!CollectionUtils.isEmpty(listDepts))
        {
            for (DeptDto deptDto : listDepts)
            {
             
                DeptUsersDto deptUserDto = new DeptUsersDto();
                deptUserDto.setDeptId(deptDto.getId());
                List<DeptUsersDto> users = deptUserService.findDeptUser(deptUserDto);
                if (!CollectionUtils.isEmpty(users))
                {
                    j.setSuccess(false);
                    j.setMsg("删除失败,该部门的下级部门还存在用户，不能删除!");
                    return j;
                }
            }
        }
        //当前部门是否存在用户判断;
        DeptUsersDto deptUserDtos = new DeptUsersDto();
        deptUserDtos.setDeptId(id);
        List<DeptUsersDto> deptUsers = deptUserService.findDeptUser(deptUserDtos);
        if (!CollectionUtils.isEmpty(deptUsers))
        {
            j.setSuccess(false);
            j.setMsg("删除失败,该部门存在用户，不能删除!");
            return j;
        }
        else
        {
            
            DeptDto tempDept = new DeptDto();
            tempDept.setId(id);
            deptService.setDeptCode(tempDept);
            deptService.delete(tempDept);
            j.setMsg("删除成功！");
            j.setSuccess(true);
            return j;
        }
    }

    /**
     * 跳转到部门编辑页面
     * 
     * @return
     */
    @RequestMapping("/editPage")
    public String editPage(HttpServletRequest request, String id)
    {

        DeptDto r = deptService.findById(id);
        List<ProjectDto> projects = projectService.findAll();
        DeptLevelDto d = new DeptLevelDto();
        List<DeptLevelDto> deptLevels = deptLevelService.findAll(d);
        request.setAttribute("deptDto", r);
        request.setAttribute("projects", projects);
        request.setAttribute("deptLevels", deptLevels);

        return "ims/dept/deptEdit";
    }

    /**
     * 编辑部门
     * 
     * @param resource
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    public Json edit(DeptDto deptDto)
    {
        Json j = new Json();
        DeptDto tempDept = new DeptDto();
        tempDept.setDeptName(deptDto.getDeptName());
        tempDept.setId(deptDto.getId());
        tempDept.setPid(deptDto.getPid());
        tempDept.setStat(1);
        int counts = deptService.vailEditDeptName(tempDept);

        if (counts > 0)
        {
            j.setSuccess(false);
            j.setMsg("部门已存在,请重新修改，亲.");
            return j;
        }
        if (StringUtils.isEmpty(deptDto.getPid()))
        {
            //如果添加的部门的没有父节点,则去数据库查找有没有一条父节点为空的记录，有的话，则提示不能添加。根节点只能有一个.
            DeptDto dto = deptService.validDeptRootExists(deptDto.getId(), deptDto.getProjId());
            if (dto != null)
            {
                j.setSuccess(false);
                j.setMsg("根节点部门是唯一的，修改无效!");
                return j;
            }
        }
        DeptDto dd = deptService.findById(deptDto.getId());
        if (!dd.getPid().equals(deptDto.getPid()))
        {
            deptService.setDeptCode(deptDto);
        }
        deptService.update(deptDto);
        j.setSuccess(true);
        j.setMsg("编辑成功！");
        return j;
    }
}
