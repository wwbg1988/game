package com.ssic.game.manage.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssic.game.admin.pageModel.Json;
import com.ssic.game.common.dto.ProcessUsersDto;
import com.ssic.game.manage.service.IProcessUsersService;

@Controller
@RequestMapping("/processUsersController")
public class ProcessUsersController {

	@Autowired
	private IProcessUsersService processUsersService;
	
	@RequestMapping("/findAll")
	@ResponseBody
	public List<ProcessUsersDto> findAll(){
		List<ProcessUsersDto> list = processUsersService.findAll();
		return list;
	}
	
	@RequestMapping("/insertProcU")
	@ResponseBody
	public Json insertProcU(ProcessUsersDto processUsersDto){
		Json j =new Json();
		processUsersService.insertProcU(processUsersDto);
		j.setMsg("创建流程成功");
		j.setSuccess(true);
		return j;
	}
	
	@RequestMapping("/updateProU")
	@ResponseBody
	public Json updateProU(ProcessUsersDto processUsersDto){
		
		Json j = new Json();
		processUsersService.updateProU(processUsersDto);
		j.setMsg("更新流程成功");
		j.setSuccess(true);
		return j;
		
	}
	
	@RequestMapping("/deleteProU")
	@ResponseBody
	public Json deleteProU(ProcessUsersDto processUsersDto){
		Json j = new Json();
		processUsersService.deleteProU(processUsersDto);
		j.setMsg("删除流程成功");
		j.setSuccess(true);
		return j;
	}
}
