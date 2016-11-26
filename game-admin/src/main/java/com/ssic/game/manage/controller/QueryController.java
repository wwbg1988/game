package com.ssic.game.manage.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssic.game.admin.pageModel.DataGrid;
import com.ssic.game.admin.pageModel.Json;
import com.ssic.game.admin.pageModel.PageHelper;
import com.ssic.game.admin.service.IActionsService;
import com.ssic.game.admin.service.ITasksService;
import com.ssic.game.admin.service.ProjectService;
import com.ssic.game.common.dto.ActionsDto;
import com.ssic.game.common.dto.ProcessDto;
import com.ssic.game.common.dto.ProjectDto;
import com.ssic.game.common.dto.TasksDto;
import com.ssic.game.common.dto.query.QueryDto;
import com.ssic.game.manage.service.IQueryService;
import com.ssic.game.manage.service.ITImsProcessService;
import com.ssic.util.UUIDGenerator;

@Controller
@RequestMapping("/queryController")
public class QueryController {

	   @Autowired
	   private IQueryService queryService;
       @Autowired
	   private IActionsService actionsService;
       @Autowired
   	   private ITImsProcessService iTImsProcessService;
       @Autowired
       private ITasksService taskService;
       @Autowired
       private ProjectService projectService;
	   
	   @RequestMapping("/manager")
	   public String manager(HttpServletRequest request){
		   //查询所有的项目
		   List<ProjectDto> listproject =  projectService.findAll();
		   request.setAttribute("listproject", listproject);
		   return "ims/query";
	   }
	   
	   @RequestMapping("/addPage")
	    public String addPage(HttpServletRequest request)
	    {
	        return "ims/queryAdd";
	    }
	   
	   @RequestMapping("/editPage")
	   public String editPage(HttpServletRequest request,String id){
		   QueryDto queryDto= queryService.findById(id);
		   request.setAttribute("qDto", queryDto);
		   //查询项目下的流程
		   ProcessDto processDto = new ProcessDto();
		   processDto.setProjId(queryDto.getProjectId());
		   List<ProcessDto> listProcess=  iTImsProcessService.findProcess(processDto);
		   request.setAttribute("listProcess", listProcess);
		   //查询流程下的节点
		   TasksDto tasksDto = new TasksDto();
		   tasksDto.setProcId(queryDto.getProcessId());
		   List<TasksDto> listtasks=  taskService.findAllBy(tasksDto);
		   request.setAttribute("listtasks", listtasks);
		   //查询节点下的动作
		   List<ActionsDto> listaction = findAction(queryDto.getTaskId());
		   request.setAttribute("listaction", listaction);
		   
		   return "ims/queryEdit";
	   }
	   
	   @RequestMapping("/updateQuery")
	   @ResponseBody
	   public Json updateQuery(QueryDto queryDto){
		   Json j=new Json();
		   if(queryDto.getProjectId()==null || "".equals(queryDto.getProjectId())){
			   j.setMsg("项目不能为空！");
			   j.setSuccess(false);
			   return j;
		   }
		   if(queryDto.getProcessId()==null || "".equals(queryDto.getProcessId()) ){
			   j.setMsg("流程不能为空！");
			   j.setSuccess(false);
			   return j;
		   }
		   if(queryDto.getTaskId()==null || "".equals(queryDto.getTaskId())){
			   j.setMsg("任务节点不能为空！");
			   j.setSuccess(false);
			   return j;
		   }
		 
		   if(queryDto.getName()==null || "".equals(queryDto.getName())){
			   j.setMsg("名称不能为空！");
			   j.setSuccess(false);
			   return j;
		   }
		   queryDto.setStat(1);
		   queryDto.setLastUpdateTime(new Date());
		   queryService.updateQuery(queryDto);
		   j.setMsg("更新查询成功！");
		   j.setSuccess(true);
		   return j;
		   
	   }
	   
	   
	   
	   public List<QueryDto> findAll(QueryDto queryDto){
		 return  queryService.findAll(queryDto);
	   }
	   
	   @ResponseBody
	   @RequestMapping("/dataGrid")
	   public DataGrid dataGrid(QueryDto queryDto, PageHelper ph){
			DataGrid dataGrid = new DataGrid();
			String projectid = queryDto.getProjectId();
			String processid = queryDto.getProcessId();
			String taskid = queryDto.getTaskId();
			String actionid = queryDto.getActionId();
			if(projectid!=null && projectid.length()<15){
				queryDto.setProjectId("");
			}
			if(processid!=null && processid.length()<15){
				queryDto.setProcessId("");
			}
			if(taskid!=null && taskid.length()<15){
				queryDto.setTaskId("");
			}
			if(actionid!=null && actionid.length()<15){
				queryDto.setActionId("");
			}
			List<QueryDto> list = queryService.findAllByPH(queryDto, ph);
			int count = queryService.findAllCount(queryDto);
			dataGrid.setRows(list);
			dataGrid.setTotal(Long.valueOf(count));
	    	return dataGrid;
	    }
	   
	   @RequestMapping("/insertQuery")
	   @ResponseBody
	   public Json insertQuery(QueryDto queryDto){
		   Json j = new Json();
		   
		   if(queryDto.getProjectId()==null || "".equals(queryDto.getProjectId())){
			   j.setMsg("项目不能为空！");
			   j.setSuccess(false);
			   return j;
		   }
		   if(queryDto.getProcessId()==null || "".equals(queryDto.getProcessId()) ){
			   j.setMsg("流程不能为空！");
			   j.setSuccess(false);
			   return j;
		   }
		   if(queryDto.getTaskId()==null || "".equals(queryDto.getTaskId())){
			   j.setMsg("任务节点不能为空！");
			   j.setSuccess(false);
			   return j;
		   }

		   if(queryDto.getName()==null || "".equals(queryDto.getName())){
			   j.setMsg("名称不能为空！");
			   j.setSuccess(false);
			   return j;
		   }
		   
		   queryDto.setId(UUIDGenerator.getUUID());
		   queryDto.setCreateTime(new Date());
		   queryDto.setStat(1);
		   queryDto.setFormStat(0);
		   queryService.insertQuery(queryDto);
		   
		   j.setMsg("添加查询成功！");
		   j.setSuccess(true);
		   
		   return j;
	   }
	   
	   @RequestMapping("/findAction")
	   @ResponseBody
	   public List<ActionsDto> findAction(String taskId){
		   ActionsDto actionsDto = new ActionsDto();
		   actionsDto.setTaskId(taskId);
		   return actionsService.findAllBy(actionsDto);
	   }
	   
	   @RequestMapping("/deleteQuery")
	   @ResponseBody
	   public Json deleteQuery(QueryDto queryDto){
		   Json j = new Json();
		   if(queryDto.getId()==null || queryDto.getId().equals("")){
			   j.setMsg("删除查询id不能为空！");
			   j.setSuccess(false);
			   return j;
		   }
		   queryDto.setStat(0);
		   queryDto.setLastUpdateTime(new Date());
		   queryService.updateQuery(queryDto);
		   j.setMsg("删除查询成功！");
		   j.setSuccess(true);
		   return j;
	   }
	   
	   
	   
}
