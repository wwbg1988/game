package com.ssic.catering.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssic.catering.admin.service.DeptService;
import com.ssic.game.common.dto.DeptDto;

@Controller
@RequestMapping("/deptController")
public class DeptController {

	   @Autowired
	   private DeptService deptService;
	
	   @RequestMapping("/treeGrid")
	    @ResponseBody
	    public List<DeptDto> treeGrid(DeptDto deptDto)
	    {

	        return deptService.treeGrid(deptDto);
	    }
	   
	   @RequestMapping("/combobox")
       @ResponseBody
       public List<DeptDto> combobox(DeptDto deptDto)
       {

           return deptService.combobox(deptDto);
       }
	   
	   
}
