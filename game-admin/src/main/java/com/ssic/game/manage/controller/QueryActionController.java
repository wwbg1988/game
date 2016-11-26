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
import com.ssic.game.common.dto.query.QueryActionDto;
import com.ssic.game.common.dto.query.QueryDto;
import com.ssic.game.manage.service.IQueryActionService;
import com.ssic.game.manage.service.IQueryService;
import com.ssic.util.UUIDGenerator;


@Controller
@RequestMapping("/queryActionController")
public class QueryActionController {

	@Autowired
	private IQueryActionService queryActionService;
	@Autowired
	private IQueryService queryService;
	
	
	public List<QueryActionDto> findBy(QueryActionDto queryActionDto){
		return  queryActionService.findBy(queryActionDto);
	}
	
	@RequestMapping("/manager")
	public String manager(String queryId, HttpServletRequest request){
		request.setAttribute("queryId", queryId);
		return "ims/queryAction";
	}
	
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request,String queryId){
		request.setAttribute("queryId", queryId);
		return "ims/queryActionAdd";
	}
	
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request,String id){
		QueryActionDto queryActionDto = queryActionService.findById(id);
		request.setAttribute("qad", queryActionDto);
		return "ims/queryActionEdit";
	}
	
	
	@ResponseBody
	@RequestMapping("/dataGrid")
	public DataGrid dataGrid(QueryActionDto queryActionDto, PageHelper ph){
			DataGrid dataGrid = new DataGrid();
			List<QueryActionDto> list = queryActionService.findAllByPH(queryActionDto, ph);
			int count = queryActionService.findAllCount(queryActionDto);
			dataGrid.setRows(list);
			dataGrid.setTotal(Long.valueOf(count));
	    	return dataGrid;
	} 
	
	@RequestMapping("/insertAction")
	@ResponseBody
	public Json insertAction(QueryActionDto queryActionDto){
		Json j = new Json();
		
		if(queryActionDto.getName()==null || queryActionDto.getName().equals("")){
			j.setMsg("动作名称不能为空！");
			j.setSuccess(false);
			return j;
		}
		if(queryActionDto.getUrl()==null || queryActionDto.getUrl().equals("")){
			j.setMsg("动作URL地址不能为空！");
			j.setSuccess(false);
			return j;
		}
		if(queryActionDto.getQueryId()==null || queryActionDto.getQueryId().equals("")){
			j.setMsg("查询ID不能为空!");
			j.setSuccess(false);
			return j;
		}
		//查询projectid
		String queryId = queryActionDto.getQueryId();
		QueryDto  queryDto= queryService.findById(queryId);
		queryActionDto.setProjectId(queryDto.getProjectId());
		queryActionDto.setCreateTime(new Date());
		queryActionDto.setStat(1);
		queryActionDto.setId(UUIDGenerator.getUUID());
		queryActionService.insertAction(queryActionDto);
		j.setMsg("添加自定义动作成功！");
		j.setSuccess(true);
		return j;
	}
	
	@RequestMapping("/updateAction")
	@ResponseBody
	public Json updateAction(QueryActionDto queryActionDto){
		Json j = new Json();
		if(queryActionDto.getName()==null || queryActionDto.getName().equals("")){
			j.setMsg("动作名称不能为空！");
			j.setSuccess(false);
			return j;
		}
		if(queryActionDto.getUrl()==null || queryActionDto.getUrl().equals("")){
			j.setMsg("动作URL地址不能为空！");
			j.setSuccess(false);
			return j;
		}
		if(queryActionDto.getQueryId()==null || queryActionDto.getQueryId().equals("")){
			j.setMsg("查询ID不能为空!");
			j.setSuccess(false);
			return j;
		}
		if(queryActionDto.getProjectId()==null || queryActionDto.getProjectId().equals("")){
			j.setMsg("项目ID不能为空！");
			j.setSuccess(false);
			return j;
		}
		queryActionDto.setLastUpdateTime(new Date());
		queryActionDto.setStat(1);
        queryActionService.updateAction(queryActionDto);
		j.setMsg("更新动作成功！");
		j.setSuccess(true);
		return j;
	}
	
	
	@RequestMapping("/deleteAction")
	@ResponseBody
	public Json deleteAction(QueryActionDto queryActionDto){
		Json j = new Json();
		if(queryActionDto.getId()==null || queryActionDto.getId().equals("")){
			j.setMsg("动作ID不能为空！");
			j.setSuccess(false);
			return j;
		}
		queryActionService.updateAction(queryActionDto);
		j.setMsg("删除动作成功！");
		j.setSuccess(true);
		return j;
	}
	
	
}
