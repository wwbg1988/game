package com.ssic.catering.manage.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssic.catering.admin.pageModel.DataGrid;
import com.ssic.catering.admin.pageModel.Json;
import com.ssic.catering.base.dto.MeetingDto;
import com.ssic.catering.base.pojo.PageHelper;
import com.ssic.catering.base.service.IMeetingService;
import com.ssic.util.UUIDGenerator;

@Controller
@RequestMapping("/meetingController")
public class MeetingController {

	@Autowired
	private IMeetingService meetingService;
	
	@RequestMapping("/findBy")
	@ResponseBody
	public List<MeetingDto> findBy(MeetingDto meetingDto){
		return meetingService.findBy(meetingDto);
	}
	
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request){
	   return "carte/meeting/meeting";
	}
	
	@RequestMapping("/addMeeting")
	public String addMeeting(HttpServletRequest request){
		return "carte/meeting/meetingAdd";
	}
	
	@RequestMapping("/editMeeting")
	public String editMeeting(MeetingDto meetingDto,HttpServletRequest request){
		String id = meetingDto.getId();
		MeetingDto mDto=  meetingService.findById(id);
		request.setAttribute("mDto", mDto);
		return "carte/meeting/meetingEdit";
	}
	
	
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(MeetingDto meetingDto, PageHelper ph){
		DataGrid dataGrid = new DataGrid();
		List<MeetingDto> list = meetingService.findBy(meetingDto,ph);
		int count = meetingService.count(meetingDto);
		dataGrid.setRows(list);
		dataGrid.setTotal(Long.valueOf(count));
		return dataGrid;
	} 
	
	@RequestMapping("/insertMeeting")
	@ResponseBody
	public Json insertMeeting(MeetingDto meetingDto){
		Json j = new Json();
		meetingDto.setId(UUIDGenerator.getUUID());
		meetingDto.setCreateTime(new Date());
		meetingDto.setStat(1);
		meetingService.insertMeeting(meetingDto);
		j.setMsg("添加会议成功！");
		j.setSuccess(true);
		
		return j;
	}
	
	@RequestMapping("/updateMeeting")
	@ResponseBody
	public Json  updateMeeting(MeetingDto meetingDto){
		Json j = new Json();
		meetingDto.setLastUpdateTime(new Date());
		meetingService.updateMeeting(meetingDto);
		j.setMsg("更新会议成功！");
		j.setSuccess(true);
		return j;
	}
	
	@RequestMapping("/deleteMeeting")
	@ResponseBody
	public Json deleteMeeting(MeetingDto meetingDto){
		Json j = new Json();
		meetingDto.setStat(0);
		meetingService.deleteMeeting(meetingDto);
		j.setMsg("删除会议成功！");
		j.setSuccess(true);
		return j;
	}
	
	//根据USERID，projectid查询该用户展示“我的，部门，全部”
    //每个人都可以看我的
	//只有部门管理员能看部门
	//只有人事能看全部
	
	public Json checkMeeting(String UserId,String projId,String procId){
		Json j = new Json();
		return j;
	}
	
	
	
}
