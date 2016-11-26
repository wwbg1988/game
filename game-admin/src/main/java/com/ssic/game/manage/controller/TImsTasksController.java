package com.ssic.game.manage.controller;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssic.game.admin.pageModel.DataGrid;
import com.ssic.game.admin.pageModel.Json;
import com.ssic.game.admin.pageModel.PageHelper;
import com.ssic.game.admin.pageModel.Tree;
import com.ssic.game.admin.pageModel.User;
import com.ssic.game.admin.service.IActionsService;
import com.ssic.game.admin.service.ITasksService;
import com.ssic.game.admin.service.ProjectService;
import com.ssic.game.admin.service.UserServiceI;
import com.ssic.game.common.dao.TaskRoleDao;
import com.ssic.game.common.dto.ActionsDto;
import com.ssic.game.common.dto.FormsDto;
import com.ssic.game.common.dto.ProcessDto;
import com.ssic.game.common.dto.TasksDto;
import com.ssic.game.common.pojo.TaskRole;
import com.ssic.game.manage.service.IFormsService;
import com.ssic.game.manage.service.ITImsProcessService;
import com.ssic.util.UUIDGenerator;

@Controller
@RequestMapping("/tImsTasksController")
public class TImsTasksController
{

    @Autowired
    private IActionsService actionService;

    @Autowired
    private ITasksService taskService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ITImsProcessService iTImsProcessService;

    @Autowired
    private IFormsService formsService;
    @Autowired
    private UserServiceI userService;

    @Autowired
    private TaskRoleDao taskRoleDao;

    @RequestMapping("/manager")
    public String manager()
    {

        return "ims/tasks";
    }

    /**
     * 跳转到任务节点角色授权页面
     * 
     * @return
     */
    @RequestMapping("/grantRolePage")
    public String grantRolePage(String ids, HttpServletRequest request, HttpSession session)
    {
        request.setAttribute("ids", ids);
        session.setAttribute("taskIds", ids);
        User u = new User();
        if (ids != null && !ids.equalsIgnoreCase(""))
        {
            TasksDto tasksDto = new TasksDto();
            tasksDto.setId(ids);
            List<TasksDto> list = taskService.findAllBy(tasksDto);
            String projId = list.get(0).getProjId();
            String result = taskService.findTaskRole(ids, projId);
            u.setRoleIds(result);
            request.setAttribute("user", u);
        }
        return "ims/taskGrant";
    }

    /**
     * 角色授权
     * 
     * @param ids
     * @return
     */
    @RequestMapping("/grant")
    @ResponseBody
    public Json grant(String roleIds, String ids)
    {
        Json j = new Json();
        //        userService.grant(ids, user);
        String result = taskService.grant(ids, roleIds);
        if (StringUtils.isEmpty(result))
        {
            j.setSuccess(false);
            j.setMsg("授权失败");
            return j;
        }
        else
        {
            j.setSuccess(true);
            j.setMsg("授权成功！");
            return j;
        }

    }

    @ResponseBody
    @RequestMapping("/findAll")
    public List<TasksDto> findAll(TasksDto tasksDto, HttpServletRequest request)
    {
        List<TasksDto> list = taskService.findAllBy(tasksDto);
        request.setAttribute("tasks", list);
        return list;
    }

    @RequestMapping("/addPage")
    public String addPage(HttpServletRequest request, String id)
    {
        request.setAttribute("proid", id);
        //如果流程的状态已经是有效的，start_task存在，返回1否则返回0
        ProcessDto processDto = new ProcessDto();
        processDto.setId(id);
        String star_flag = "0";
        List<ProcessDto> list = iTImsProcessService.findProcess(processDto);
        if (list != null && list.size() > 0)
        {
            int isDefine = list.get(0).getIsdefine();
            String starTask = list.get(0).getStartTask();
            if (isDefine == 1 && starTask != null)
            {
                star_flag = "1";
            }
        }
        request.setAttribute("star_flag", star_flag);
        return "ims/tasksAdd";
    }

    @ResponseBody
    @RequestMapping("/dataGrid")
    public DataGrid dataGrid(TasksDto tasksDto, PageHelper ph)
    {
        DataGrid dataGrid = new DataGrid();
        List<TasksDto> list = taskService.findTasksALL(tasksDto, ph);
        int count = taskService.findCount(tasksDto);
        dataGrid.setRows(list);
        dataGrid.setTotal(Long.valueOf(count));

        return dataGrid;
    }

    @RequestMapping("/insertTask")
    @ResponseBody
    public Json insertTask(TasksDto tasksDto, String proid)
    {
        Json j = new Json();

        if (proid == null || proid.equals(""))
        {
            j.setMsg("流程ID不能为空");
            j.setSuccess(false);
            return j;
        }

        if (tasksDto == null)
        {
            j.setMsg("插入对象不存在");
            j.setSuccess(false);
            return j;
        }
        if (tasksDto.getName() == null || tasksDto.getName().equals(""))
        {
            j.setMsg("任务节点名称不能为空");
            j.setSuccess(false);
            return j;
        }
        if (tasksDto.getName().length() > 30)
        {
            j.setMsg("任务节点名称长度不能大于30");
            j.setSuccess(false);
            return j;
        }

        if (tasksDto.getType() == null)
        {
            j.setMsg("任务节点类型不能为空");
            j.setSuccess(false);
            return j;
        }

        if (tasksDto.getCountersign() == null)
        {
            j.setMsg("是否会签不能为空");
            j.setSuccess(false);
            return j;
        }

        ProcessDto processdto = new ProcessDto();
        processdto.setId(proid);
        processdto.setStat(1);
        List<ProcessDto> prolist = iTImsProcessService.findProcess(processdto);
        String projid = "";
        if (prolist != null && prolist.size() > 0)
        {
            ProcessDto pdto = prolist.get(0);
            projid = pdto.getProjId();
        }
        else
        {
            j.setMsg("流程不存在");
            j.setSuccess(false);
            return j;
        }
        tasksDto.setState(0);
        tasksDto.setProjId(projid);
        tasksDto.setProcId(proid);
        String taskId = UUIDGenerator.getUUID();
        tasksDto.setId(taskId);
        tasksDto.setStat(1);
        tasksDto.setCreateTime(new Date());
        tasksDto.setPreTaskId("");
        taskService.insert(tasksDto);
        //如果节点的type为1 ，更新开始节点和有效
        if (tasksDto.getType() == 1)
        {
            ProcessDto proDto = new ProcessDto();
            proDto.setIsdefine(1);
            proDto.setStartTask(taskId);
            proDto.setId(proid);
            proDto.setLastUpdateTime(new Date());
            iTImsProcessService.updateStarTask(proDto);
        }

        j.setMsg("创建任务节点成功");
        j.setSuccess(true);
        return j;
    }

    @RequestMapping("/editTasks")
    public String editTasks(HttpServletRequest request, String id)
    {
        TasksDto tasksdto = new TasksDto();
        TasksDto tDto = new TasksDto();
        tasksdto.setId(id);
        List<TasksDto> listtasks = taskService.findAllBy(tasksdto);
        if (listtasks != null && listtasks.size() > 0)
        {
            tDto = listtasks.get(0);
        }

        //  List<ProjectDto> projects = projectService.findAll();
        //   ProcessDto process = new ProcessDto();
        //    process.setStat(1);
        //    process.setId(tDto.getProcId());
        // List<ProcessDto> processlist =  iTImsProcessService.findProcess(process);

        FormsDto formsDto = new FormsDto();
        formsDto.setStat(1);
        List<FormsDto> forms = formsService.findAll(formsDto);

        request.setAttribute("forms", forms);
        request.setAttribute("tDto", tDto);
        // request.setAttribute("projects", projects);
        // request.setAttribute("processlist", processlist);

        return "ims/tasksEdit";
    }

    @RequestMapping("/updateTasks")
    @ResponseBody
    public Json updateTasks(TasksDto tasksDto)
    {
        Json j = new Json();
        if (tasksDto == null)
        {
            j.setMsg("插入对象不存在");
            j.setSuccess(false);
            return j;
        }
        if (tasksDto.getName() == null || tasksDto.getName().equals(""))
        {
            j.setMsg("任务节点名称不能为空");
            j.setSuccess(false);
            return j;
        }
        if (tasksDto.getName().length() > 30)
        {
            j.setMsg("任务节点名称长度不能大于30");
            j.setSuccess(false);
            return j;
        }

        if (tasksDto.getType() == null)
        {
            j.setMsg("任务节点类型不能为空");
            j.setSuccess(false);
            return j;
        }

        if (tasksDto.getCountersign() == null)
        {
            j.setMsg("是否会签不能为空");
            j.setSuccess(false);
            return j;
        }

        tasksDto.setStat(1);
        tasksDto.setLastUpdateTime(new Date());
        taskService.update(tasksDto);
        j.setMsg("更新任务节点成功");
        j.setSuccess(true);
        return j;
    }

    @RequestMapping("/deleteTask")
    @ResponseBody
    public Json deleteTask(TasksDto tasksdto)
    {
        Json j = new Json();
        if (tasksdto == null)
        {
            j.setMsg("删除对象不存在");
            j.setSuccess(false);
            return j;
        }
        if (tasksdto.getId() == null || tasksdto.getId().equals(""))
        {
            j.setMsg("删除对象ID不能为空");
            j.setSuccess(false);
            return j;
        }

        //查询动作表中使用的节点，如果该节点正在使用则不能删除
        List<ActionsDto> listact = taskService.findActions();
        Set<String> set = new HashSet<String>();

        if (listact != null && listact.size() > 0)
        {
            for (int i = 0; i < listact.size(); i++)
            {
                ActionsDto acdto = listact.get(i);
                String taskid = acdto.getTaskId();
                String ntaskid = acdto.getNextTaskId();
                set.add(taskid);
                set.add(ntaskid);
            }
        }
        if (set.contains(tasksdto.getId()))
        {
            j.setMsg("该节点的动作已经启动，不能删除该节点");
            j.setSuccess(false);
            return j;
        }

        //删除任务节点，也要删除该节点下的表单信息和字段信息,字段角色信息，字段字典信息;
        if (!StringUtils.isEmpty(tasksdto.getId()))
        {
            TasksDto dto = new TasksDto();
            dto.setId(tasksdto.getId());
            List<TasksDto> taskDtos = taskService.findAllBy(dto);
            TasksDto taskDto = taskDtos.get(0);
            //如果任务节点的formId存在 ，则直接根据formId删除表单字段等关联表信息 ；如果fomrId不存在，则说明没有表单;
            if (!StringUtils.isEmpty(taskDto.getFormId()))
            {
                FormsDto forms = new FormsDto();
                forms.setId(taskDto.getFormId());
                formsService.delete(forms);
            }
            //删除taskRole关系
            if (!StringUtils.isEmpty(taskDto.getProjId()))
            {
                TaskRole param = new TaskRole();
                param.setTaskId(tasksdto.getId());
                param.setProjId(taskDto.getProjId());
                taskRoleDao.delRoles(param);
            }
        }

        taskService.delete(tasksdto.getId());

        j.setMsg("任务节点删除成功");
        j.setSuccess(true);
        return j;

    }

    /**
     * 角色树(只能看到自己拥有的角色)
     * 
     * @return
     */
    @RequestMapping("/allTree")
    @ResponseBody
    public List<Tree> allTree(HttpSession session)
    {
        if (session.getAttribute("taskIds") != null)
        {
            String taskIds = session.getAttribute("taskIds").toString();

            TasksDto taskDto = taskService.findById(taskIds);
            if (taskDto != null)
            {
                String projId = taskDto.getProjId();
                return actionService.allTree(projId);
            }
        }
        return null;

    }

}
