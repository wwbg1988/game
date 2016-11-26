package com.ssic.catering.admin.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssic.catering.admin.pageModel.Tree;
import com.ssic.catering.admin.service.ProjectService;
import com.ssic.catering.admin.service.UserServiceI;
import com.ssic.game.common.dto.ProjectDto;

@Controller
@RequestMapping("/ProjectController")
public class ProjectController
{

    @Autowired
    private ProjectService projectService;
    @Autowired
    private UserServiceI userServiceI;

    /**
    * 获得项目树
    * @return
    */
    @RequestMapping("/allTree")
    @ResponseBody
    public List<Tree> allTree(HttpSession session)
    {
        //获取用户拥有的项目权限
      //  List<ProjectDto> listProject = userServiceI.getProjectBySession(session);
    	//新用户新项目，展示所有的项目给角色
    	 List<ProjectDto> listProject =projectService.findAll();
    	
        if (!CollectionUtils.isEmpty(listProject))
        {
            if (listProject.size() > 1)
            {//超管
                return projectService.allTree();
            }
            else
            {
                return projectService.allTreeByProjId(listProject.get(0).getId());
            }
        }
        List<Tree> listtr = new ArrayList<Tree>();
        return listtr;

    }

    @ResponseBody
    @RequestMapping("/findAll")
    public List<ProjectDto> findAllDept()
    {
        List<ProjectDto> dtoList = projectService.findAll();

        return dtoList;
    }
    
    
    /**
     * 
     * getProjectsBy：一句话描述方法功能
     * @param req
     * @param resp
     * @param project
     * @return
     * @exception   
     * @author zhuzhen
     * @date 2015年11月30日 上午11:23:43
     */
    @RequestMapping("/getProjectsBy")
    @ResponseBody
    public List<ProjectDto> getProjectsBy(HttpServletRequest req, HttpServletResponse resp,
        ProjectDto project)
    {
        List<ProjectDto> projects = userServiceI.getProjectBySession(req.getSession());
        if(CollectionUtils.isEmpty(projects))
        {
            return null;
        }
        
        return projects;
    }
}
