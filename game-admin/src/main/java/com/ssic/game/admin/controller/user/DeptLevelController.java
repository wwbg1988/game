package com.ssic.game.admin.controller.user;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssic.game.admin.dto.DeptLevelDto;
import com.ssic.game.admin.dto.TImsUsersDto;
import com.ssic.game.admin.pageModel.DataGrid;
import com.ssic.game.admin.pageModel.Json;
import com.ssic.game.admin.pageModel.PageHelper;
import com.ssic.game.admin.service.DeptLevelService;
import com.ssic.util.UUIDGenerator;



@Controller
@RequestMapping("/deptLevelController")
public class DeptLevelController {
	
	
	@Autowired
	private DeptLevelService deptLevelService;
	
	@ResponseBody
	@RequestMapping("/findAll")
	public List<DeptLevelDto> findAllDept(HttpSession session,HttpServletRequest request) {
		DeptLevelDto deptLevelDto = new DeptLevelDto();
		return deptLevelService.findAll(deptLevelDto);
	}
	
	@ResponseBody
	@RequestMapping("/dataGrid")
	public DataGrid dataGrid(DeptLevelDto deptLevelDto, PageHelper ph) {
		//DeptLevelDto deptLevelDto = new DeptLevelDto();
		DataGrid dataGrid = new DataGrid();
		List<DeptLevelDto> list = deptLevelService.findLevelAll(deptLevelDto,ph);
		int count = deptLevelService.findLevelCount(deptLevelDto);
		dataGrid.setRows(list);
		dataGrid.setTotal(Long.valueOf(count));
		return  dataGrid;
	}
	
	@ResponseBody
	@RequestMapping("/insert")
	public Json insert(DeptLevelDto deptLevelDto){
		Json j = new Json();
		if(deptLevelDto.getLevelName().length()>30){
			j.setMsg("部门名称不能大于30位");
			j.setSuccess(false);
			return j;
		}
		DeptLevelDto dldto = new DeptLevelDto();
		dldto.setLevel(deptLevelDto.getLevel());
		List<DeptLevelDto> temp = deptLevelService.findAll(dldto);
		
		if(temp!=null){
			if(temp.size()>0){
				j.setMsg("该部门等级已存在，请修改");
				j.setSuccess(false);
				return j;
			}
		}
		
		deptLevelDto.setId(UUIDGenerator.getUUID());
		deptLevelDto.setCreateTime(new Date());
		deptLevelDto.setStat(1);
		deptLevelService.insert(deptLevelDto);
		j.setMsg("创建部门等级成功!");
		j.setSuccess(true);
		return j;
	}
	@ResponseBody
	@RequestMapping("/del")
	public Json insert(String id){
		DeptLevelDto deptLevelDto = new DeptLevelDto();
		Json j = new Json();
		deptLevelDto.setId(id);
		deptLevelService.del(deptLevelDto);;
		j.setMsg("删除部门等级成功!");
		j.setSuccess(true);
		return j;
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public Json update(DeptLevelDto deptLevelDto){
		Json j = new Json();
		
		if(deptLevelDto.getLevelName().length()>30){
			j.setMsg("部门名称不能大于30位");
			j.setSuccess(false);
			return j;
		}
		DeptLevelDto dldto = new DeptLevelDto();
	//	dldto.setLevel(deptLevelDto.getLevel());
		List<DeptLevelDto> temp = deptLevelService.findAll(dldto);
		if(temp!=null){
			int k_star = 0;
			for(int i=0;i<temp.size();i++){
				DeptLevelDto dlevel = temp.get(i);
				if(!dlevel.getId().equals(deptLevelDto.getId())){
					int dlevell = dlevel.getLevel();
					if(dlevell==deptLevelDto.getLevel()){
						k_star = k_star+1;
					}
				}
			}
			
			if(k_star>0){
				j.setMsg("部门名称或部门等级已存在，请修改");
				j.setSuccess(false);
				return j;
			}
		}
		
		deptLevelDto.setId(deptLevelDto.getId());
		deptLevelDto.setCreateTime(new Date());
		deptLevelDto.setLastTime(new Date());
		deptLevelDto.setStat(1);
		deptLevelService.update(deptLevelDto);
		j.setMsg("创建部门等级成功!");
		j.setSuccess(true);
		return j;
	}
	
	/**
	 * 跳转到部门等级管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager() {
		return "admin/deptLevel";
	}
	
	/**
	 * 跳转到添加部门等级页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		//User u = new User();
		//u.setId(UUID.randomUUID().toString());
		//request.setAttribute("user", u);
		return "admin/deptLevelAdd";
	}
	
	/**
	 * 跳转到部门等级修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, String id) {
		DeptLevelDto deptLevelDto = new DeptLevelDto();
		deptLevelDto.setId(id);
		List<DeptLevelDto> temp = deptLevelService.findAll(deptLevelDto);
		DeptLevelDto dldto = temp.get(0);
		
		
	//	TImsUsersDto u = userService.getUser(id);
	//	List<DeptDto> dept = deptService.findAll();
		request.setAttribute("dldto", dldto);
	//	request.setAttribute("dept", dept);
		return "admin/deptLevelEdit";
	}
}
