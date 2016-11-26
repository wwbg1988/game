/**
 * 
 */
package com.ssic.game.manage.controller;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssic.game.admin.pageModel.DataGrid;
import com.ssic.game.admin.pageModel.Json;
import com.ssic.game.admin.pageModel.PageHelper;
import com.ssic.game.admin.service.ITasksService;
import com.ssic.game.admin.service.ProjectService;
import com.ssic.game.common.dto.FormsDto;
import com.ssic.game.common.dto.ProcInstantsDto;
import com.ssic.game.common.dto.ProcessDto;
import com.ssic.game.common.dto.ProjectDto;
import com.ssic.game.common.dto.TasksDto;
import com.ssic.game.common.service.IProcInstanceService;
import com.ssic.game.manage.service.IFormsService;
import com.ssic.game.manage.service.ITImsProcessService;

/**		
 * <p>Title: FormsController </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 刘博	
 * @date 2015年6月24日 上午10:27:40	
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年6月24日 上午10:27:40</p>
 * <p>修改备注：</p>
 */
@Controller
@RequestMapping("/formsController")
public class FormsController
{

    @Autowired
    private ProjectService projectService;

    @Autowired
    private IFormsService formsService;

    @Autowired
    private ITasksService taskService;

    @Autowired
    private IProcInstanceService proceInstantsService;

    @Autowired
    private ITImsProcessService processService;

    /**
     * 跳转到资源管理页面
     * 
     * @return
     */
    @RequestMapping("/manager")
    public String manager(HttpServletRequest request)
    {
        List<ProjectDto> projectList = projectService.findAll();
        //放入项目集合到前台页面
        request.setAttribute("projectList", projectList);
        ProcessDto processDto = new ProcessDto();
        List<ProcessDto> processList = processService.findProcess(processDto);
        //放入流程集合到前台页面
        request.setAttribute("processList", processList);
        TasksDto tasksDto = new TasksDto();
        List<TasksDto> taskList=  taskService.findAllBy(tasksDto);
        //放入任务节点集合到前台页面
        request.setAttribute("taskList", taskList);
        return "ims/form/forms";
    }

    /*    @ResponseBody
        @RequestMapping("/findAll")
        public List<FormsDto> findAll(String formId,HttpServletRequest request){
            List<FormsDto> list = formsService.findAllByNotInclude(formId);
            request.setAttribute("forms", list);
            return list;
        }
        */

    /**
     * 获取字段grid表单
     * 
     * @param fieldsDto
     * @return
     */
    @RequestMapping("/dataGrid")
    @ResponseBody
    public DataGrid dataGrid(FormsDto formsDto, PageHelper ph)
    {
        return formsService.dataGrid(formsDto, ph);
    }

    /**
     * 跳转到添加用户页面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/addPage")
    public String addPage(HttpServletRequest request, String taskId, String projId, String procId)
    {
        FormsDto u = new FormsDto();
        u.setId(UUID.randomUUID().toString());
        TasksDto tasksDto = new TasksDto();
        tasksDto.setId(taskId);
        List<TasksDto> tasks = taskService.findAllBy(tasksDto);
        u.setTaskId(taskId);
        if (tasks.size() > 0)
        {
            String taskName = tasks.get(0).getName();
            u.setTaskName(taskName);
        }
        else
        {
            u.setTaskName("任务节点异常");
        }
        u.setProjId(projId);
        u.setProcId(procId);
        request.setAttribute("formsDto", u);
        return "ims/form/formsAdd";
    }

    /**
     * 添加表单
     * 
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public Json add(FormsDto formDto)
    {
        Json j = new Json();
        FormsDto forms = new FormsDto();
        forms.setName(formDto.getName());
        forms.setStat(1);
        int counts = formsService.vaildForms(forms);
        if (counts > 0)
        {
            j.setSuccess(false);
            j.setMsg("该表单已存在");
            return j;
        }

        try
        {
            formDto.setStat(1);
            formDto.setCreateTime(new Date());
            formsService.add(formDto);
            //更新t_ims_tasks的form_id
            FormsDto fDto = new FormsDto();
            fDto.setTaskId(formDto.getTaskId());
            List<FormsDto> listforms =   formsService.findAll(fDto);
            if(listforms!= null && listforms.size()>0){
            	FormsDto fDto2 = listforms.get(0);
            	TasksDto tDto = new TasksDto();
            	tDto.setId(fDto2.getTaskId());
            	tDto.setFormId(fDto2.getId());
            	taskService.updateForm(tDto);
            }
            
            j.setSuccess(true);
            j.setMsg("添加表单成功！");
            j.setObj(formDto);
        }
        catch (Exception e)
        {
            // e.printStackTrace();
            j.setMsg(e.getMessage());
        }
        return j;
    }

    /**
     * 删除字段
     * 
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Json delete(String id)
    {
        Json j = new Json();
        FormsDto dto = formsService.findById(id);
        //根据taskId获取taskType;
        TasksDto tasksDto = new TasksDto();
        tasksDto.setId(dto.getTaskId());
        List<TasksDto> taskDtos = taskService.findAllBy(tasksDto);
        
        int taskType = taskDtos.get(0).getType();
        //根据form表单的procId可以查询对应的流程实例数据;
        ProcInstantsDto procInstantsDto = new ProcInstantsDto();
        procInstantsDto.setProcId(dto.getProcId());
        List<ProcInstantsDto> proceInstList = proceInstantsService.findAllBy(procInstantsDto);
        //赛事进程实例存在且任务节点类型为3(结束),可以删除表单;
        boolean canDelete = (proceInstList != null && proceInstList.size() > 0 && taskType == 3);
        //赛事进程实例不存在,可以删除;
        boolean canDelete2 = (proceInstList == null);
        if (canDelete || canDelete2)
        {
        
            //根据formId查找对应的task对象
            TasksDto tDto = new TasksDto();
            tDto.setFormId(id);
            List<TasksDto> tasks= taskService.findAllBy(tDto);
            //将查出的task对象的fromId设置为null,然后更新 ;
            tasks.get(0).setFormId(null);
            taskService.updateForm(tasks.get(0));
            //删除表单
            FormsDto forms = new FormsDto();
            forms.setId(id);
            formsService.delete(forms);
            
            j.setMsg("删除成功！");
            j.setSuccess(true);
        }
        else
        {
            j.setSuccess(false);
            j.setMsg("表单所属的流程实例已存在，无法删除!");
            return j;
        }
        return j;
    }

    /**
     * 跳转到资源编辑页面
     * 
     * @return
     */
    @RequestMapping("/editPage")
    public String editPage(HttpServletRequest request, String id)
    {

        FormsDto r = formsService.findById(id);
        List<ProjectDto> projects = projectService.findAll();
        TasksDto tasksDto = new TasksDto();
        tasksDto.setStat(1);
        List<TasksDto> tasks = taskService.findAllBy(tasksDto);
        request.setAttribute("formsDto", r);
        request.setAttribute("projects", projects);
        request.setAttribute("tasks", tasks);
        return "ims/form/formsEdit";
    }

    /**
     * 编辑字段
     * 
     * @param resource
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    public Json edit(FormsDto formsDto)
    {
        Json j = new Json();

        formsService.update(formsDto);
        j.setSuccess(true);
        j.setMsg("编辑成功！");
        return j;
    }

    @RequestMapping("/findAll")
    @ResponseBody
    public List<FormsDto> findAll(FormsDto formsDto)
    {
        return formsService.findAll(formsDto);
    }
}
