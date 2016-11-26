package com.ssic.catering.manage.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssic.catering.admin.pageModel.Json;
import com.ssic.catering.base.dto.MeetUserDeptDto;
import com.ssic.catering.base.dto.MeetingUserDto;
import com.ssic.catering.base.service.IMeetingUserService;
import com.ssic.game.common.service.IDeptService;

@Controller
@RequestMapping("/meetingUserController")
public class MeetingUserController {

	@Autowired
	private IMeetingUserService meetingUserService;
//	@Autowired
//	private IDeptService deptService;
	
	@RequestMapping("/findBy")
	@ResponseBody
	public List<MeetingUserDto> findBy(MeetingUserDto meetingUserDto){
		return meetingUserService.findBy(meetingUserDto);
	}
	
	@RequestMapping("/findById")
	@ResponseBody
	public MeetingUserDto findById(String id){
		return meetingUserService.findById(id);
	}
	
	@RequestMapping("/insertMeetingU")
	@ResponseBody
	public Json insertMeetingU(MeetingUserDto meetingUserDto){
		Json j = new Json();
		meetingUserService.insertMeetingU(meetingUserDto);
		j.setMsg("新增用户成功！");
		j.setSuccess(true);
		return j;
	}
	
	@RequestMapping("/updateMeetingU")
	@ResponseBody
	public Json updateMeetingU(MeetingUserDto meetingUserDto){
		Json j = new Json();
		meetingUserService.updateMeetingU(meetingUserDto);
		j.setMsg("更新用户成功！");
		j.setSuccess(true);
		return j;
	}
	
	@RequestMapping("/deleteMeetingU")
	@ResponseBody
	public Json deleteMeetingU(MeetingUserDto meetingUserDto){
		Json j = new Json();
		meetingUserDto.setStat(0);
		meetingUserDto.setLastUpdateTime(new Date());
		meetingUserService.updateMeetingU(meetingUserDto);
		j.setMsg("删除会议成功！");
		j.setSuccess(true);
		return j;
	}
	
	
	//在团餐项目下  查询团餐的部门和对应部门下的用户
	@RequestMapping("/findUserDept")
	@ResponseBody
    public List<MeetUserDeptDto> findUserDept(MeetUserDeptDto meetUserDeptDto){
		String userId="";
		String projId = "1959a627-1a89-4497-b1e4-144d019d6687";
    	return meetingUserService.findUserDept(userId,projId);
    }
	
	
	
	
}
