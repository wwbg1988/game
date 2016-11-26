package com.ssic.catering.manage.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssic.catering.admin.pageModel.Json;
import com.ssic.catering.base.dto.GroupUserDto;
import com.ssic.catering.base.service.IGroupUserService;
import com.ssic.util.UUIDGenerator;

@Controller
@RequestMapping("/groupUserController")
public class GroupUserController {

	@Autowired
	private IGroupUserService groupUserService;
	
	public List<GroupUserDto> findBy(GroupUserDto groupUserDto){
		return 	groupUserService.findBy(groupUserDto);
	}
	
	public GroupUserDto findById(String id){
		return groupUserService.findById(id);
	}
	
	
	@RequestMapping("/insertGroupUser")
	@ResponseBody
	public Json insertGroupUser(GroupUserDto groupUserDto){
		Json j = new Json();
		if(groupUserDto.getUserId()==null || "".equals(groupUserDto.getUserId())){
			j.setMsg("用户ID不能为空!");
			j.setSuccess(false);
			return j;
		}
		if(groupUserDto.getInfoId()==null || "".equals(groupUserDto.getInfoId())){
			j.setMsg("群ID不能为空!");
			j.setSuccess(false);
			return j;
		}
		groupUserDto.setId(UUIDGenerator.getUUID());
		groupUserDto.setCreateTime(new Date());
		groupUserDto.setStat(1);
		groupUserService.insertGroupUser(groupUserDto);
		j.setMsg("插入群人员表成功!");
		j.setSuccess(true);
		return j;
	}
	
	@RequestMapping("/updateGroupUser")
	@ResponseBody
	public Json  updateGroupUser(GroupUserDto groupUserDto){
		Json j = new Json();
		if(groupUserDto.getId()==null || "".equals(groupUserDto.getId())){
			j.setMsg("群用户ID不能为空!");
			j.setSuccess(false);
			return j;
		}
		groupUserDto.setLastUpdateTime(new Date());
		groupUserService.updateGroupUser(groupUserDto);
		j.setMsg("更新群成员成功！");
		j.setSuccess(true);
		return j;
	}
	
	@RequestMapping("/deleteGroupUser")
	@ResponseBody
	public Json deleteGroupUser(GroupUserDto groupUserDto){
		Json j = new Json();
		if(groupUserDto.getId()==null || "".equals(groupUserDto.getId())){
			j.setMsg("群用户ID不能为空!");
			j.setSuccess(false);
			return j;
		}
		groupUserDto.setStat(0);
		groupUserDto.setLastUpdateTime(new Date());
		groupUserService.updateGroupUser(groupUserDto);
		j.setMsg("删除群成员成功！");
		j.setSuccess(true);
		return j;
	}
	
	
	
}
