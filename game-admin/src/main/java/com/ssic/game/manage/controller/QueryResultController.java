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
import com.ssic.game.admin.service.ITasksService;
import com.ssic.game.common.dto.FieldsDto;
import com.ssic.game.common.dto.TasksDto;
import com.ssic.game.common.dto.query.QueryDto;
import com.ssic.game.common.dto.query.QueryResultDto;
import com.ssic.game.manage.service.IFieldsService;
import com.ssic.game.manage.service.IQueryResultService;
import com.ssic.game.manage.service.IQueryService;
import com.ssic.util.UUIDGenerator;

@Controller
@RequestMapping("/queryResultController")
public class QueryResultController {

	@Autowired
	private IQueryResultService queryResultService ;
	@Autowired
	private IQueryService queryService;
	@Autowired
	private IFieldsService  fieldsService;
	@Autowired
	private ITasksService  tasksService;
	
	
	public List<QueryResultDto> findBy(QueryResultDto queryResultDto){
		return queryResultService.findBy(queryResultDto);
		}
	
	@RequestMapping("/manager")
	public String manager(String queryId, HttpServletRequest request){
		request.setAttribute("queryId", queryId);
		return "ims/queryResult";
	}
	
	
	@ResponseBody
	@RequestMapping("/dataGrid")
	public DataGrid dataGrid(QueryResultDto queryResultDto, PageHelper ph){
			DataGrid dataGrid = new DataGrid();
			List<QueryResultDto> list = queryResultService.findAllByPH(queryResultDto, ph);
			int count = queryResultService.findAllCount(queryResultDto);
			dataGrid.setRows(list);
			dataGrid.setTotal(Long.valueOf(count));
	    	return dataGrid;
	    }
	
	@RequestMapping("/insertResult")
	@ResponseBody
	public Json insertResult(QueryResultDto queryResultDto){
		Json j = new Json();
		if(queryResultDto.getFieldsId()==null || "".equals(queryResultDto.getFieldsId())){
			j.setMsg("查询结果字段不能为空！");
			j.setSuccess(false);
			return j;
		}
		if(queryResultDto.getSerialNum()==null){
			j.setMsg("查询结果排序不能为空！");
			j.setSuccess(false);
			return j;
		}
		//根据queryid查询返回列，如果已经存在这个字段，返回报错
		
		QueryResultDto queryResultDto2 = new QueryResultDto();
		queryResultDto2.setQueryId(queryResultDto.getQueryId());
		List<QueryResultDto> list2= queryResultService.findBy(queryResultDto2);
		if(list2!=null && list2.size()>0){
			for(QueryResultDto queryResult2:list2){
				String fieldsid2 = queryResult2.getFieldsId();
				if(fieldsid2.equals(queryResultDto.getFieldsId())){
				    FieldsDto fieldsDto2 =fieldsService.findById(fieldsid2);
					j.setMsg("字段("+fieldsDto2.getParamDesc()+")已经存在不能再次添加！" );
					j.setSuccess(false);
					return j;
				}
			}
		}
		//根据queryid查询projectID
	    QueryDto queryDto3=	queryService.findById(queryResultDto.getQueryId());
	    queryResultDto.setProjectId(queryDto3.getProjectId());
	    
		queryResultDto.setId(UUIDGenerator.getUUID());
		queryResultDto.setCreateTime(new Date());
		queryResultDto.setStat(1);
		queryResultService.insertResult(queryResultDto);
		j.setMsg("插入查询结果成功！");
		j.setSuccess(true);
		return j;
	}
	
	@RequestMapping("/addPage")
	public String addPage(String queryId, HttpServletRequest request){
		request.setAttribute("queryId", queryId);
         QueryDto queryDto=	queryService.findById(queryId);
         String tasksid =queryDto.getTaskId();
         //  查询formid 查询fieldsid
         TasksDto tasksDto = tasksService.findById(tasksid);
         FieldsDto fieldsDto = new FieldsDto();
         fieldsDto.setFormId(tasksDto.getFormId());
         List<FieldsDto> listFields=    fieldsService.findAllBy(fieldsDto);
         request.setAttribute("listFields", listFields);
		return "ims/queryResultAdd";
	}
	
	
	@RequestMapping("/updateResult")
	@ResponseBody
	public Json updateResult(QueryResultDto queryResultDto){
		Json j = new Json();
		queryResultService.updateResult(queryResultDto);
		j.setMsg("更新查询结果成功！");
		j.setSuccess(true);
		return j;
	}
	
	@RequestMapping("/deleteResult")
	@ResponseBody
	public Json deleteResult(QueryResultDto queryResultDto){
		Json j = new Json();
		queryResultDto.setStat(0);
		queryResultService.updateResult(queryResultDto);
		j.setMsg("删除查询结果成功！");
		j.setSuccess(true);
		return j;
	}
	
	
}
