package com.ssic.game.admin.controller.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssic.game.admin.pageModel.Json;
import com.ssic.game.admin.pageModel.PageHelper;
import com.ssic.game.admin.pageModel.Role;
import com.ssic.game.admin.pageModel.SessionInfo;
import com.ssic.game.admin.pageModel.Tree;
import com.ssic.game.admin.util.ConfigUtil;
import com.ssic.game.common.dto.ProjectDto;
import com.ssic.game.common.dto.ProjectUsersDto;
import com.ssic.game.manage.service.IProjectUsersService;


@Controller
@RequestMapping("/projectUsersController")
public class ProjectUsersController {
    @Autowired
	private IProjectUsersService iprojectUsersService;
	
    
	@RequestMapping("/manager")
	public String manager(){
		return "ims/projectUsers";
	}
	
	@RequestMapping("/addprojectU")
	public String addprojectU(){
		
		return "ims/projectUsersAdd";
	}
	
	
	@RequestMapping("/editProjectUsers")
	public String editProjectUsers(HttpServletRequest request,String id){
		
		ProjectUsersDto projectUsersDto = new ProjectUsersDto();
		ProjectUsersDto puDto = new ProjectUsersDto();
		projectUsersDto.setId(id);
	    List<ProjectUsersDto> list=	iprojectUsersService.findAll(projectUsersDto);
		if(list!=null && list.size()>0){
			puDto=list.get(0);
		}
		
		request.setAttribute("puDto", puDto);
		
		return "ims/projectUsersEdit";
	}
	
    
    @RequestMapping("/findAll")
    @ResponseBody
	public List<ProjectUsersDto> findAll(ProjectUsersDto projectUsersDto){
		List<ProjectUsersDto> list = iprojectUsersService.findAll(projectUsersDto);
		
		return list;
		
	}
    
    @RequestMapping("/findDept")
    @ResponseBody
    public List<ProjectUsersDto> findDept(ProjectUsersDto projectUsersDto){
    	List<ProjectUsersDto> list = iprojectUsersService.findDept(projectUsersDto);
    	return list;
    }
    
    @RequestMapping("/findProDept")
    @ResponseBody
    public List<ProjectUsersDto> findProDept(ProjectUsersDto projectUsersDto){
    	List<ProjectUsersDto> list = iprojectUsersService.findProDept(projectUsersDto);
    	return list;
    }
    
	
    @RequestMapping("/insertPUser")
    @ResponseBody
	public Json insertPUser(ProjectUsersDto projectUsersDto){
		Json j = new Json();
		iprojectUsersService.insertPUser(projectUsersDto);
		j.setMsg("创建项目用户成功");
		j.setSuccess(true);
		return j;
	}
	
    @RequestMapping("/updatePUser")
    @ResponseBody
	public Json updatePUser(ProjectUsersDto projectUsersDto){
		Json j = new Json();
		iprojectUsersService.updatePUser(projectUsersDto);
		j.setMsg("更新项目用户成功");
	    j.setSuccess(true);
		return j;
	}
	
	@RequestMapping("/deletePUser")
	@ResponseBody
	public Json deletePUser(ProjectUsersDto projectUsersDto){
		Json j = new Json();
		iprojectUsersService.deletePUser(projectUsersDto);
		j.setMsg("删除项目用户成功");
		j.setSuccess(true);
		return j;
	}
	
	@ResponseBody
	@RequestMapping("/dataGrid")
    public List<ProjectUsersDto> dataGrid(ProjectUsersDto projectUsersDto, PageHelper ph){
    	
    	return iprojectUsersService.findAll(projectUsersDto);
    }
	
	
	/**
	 * 跳转到角色授权页面
	 * 
	 * @return
	 */
	@RequestMapping("/grantPage")
	public String grantPage(HttpServletRequest request, String id) {
		
	//	Role r = roleService.get(id);
	//	request.setAttribute("role", r);
		return "ims/projectUGrant";
	}
	
//	@RequestMapping("/allTree")
//	@ResponseBody
//	public List<Tree> allTree(HttpSession session) {
//		SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.SESSIONINFONAME);
//		return iprojectUsersService.allTree(sessionInfo);
//	}
	
	
}
