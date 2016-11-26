package com.ssic.catering.manage.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssic.catering.admin.pageModel.DataGrid;
import com.ssic.catering.admin.service.UserServiceI;
import com.ssic.catering.base.service.ITransportTaskAdminService;
import com.ssic.catering.lbs.documents.TransportTrail;
import com.ssic.catering.lbs.dto.TransportDriverDto;
import com.ssic.catering.lbs.dto.TransportTaskAdminDto;
import com.ssic.catering.lbs.dto.TransportTrailMongoDto;
import com.ssic.catering.lbs.service.TransportDriverService;
import com.ssic.catering.lbs.service.TransportTrailService;
import com.ssic.game.common.dto.PageHelperDto;
import com.ssic.game.common.dto.ProjectDto;

/**
 * 		
 * <p>Title: TransportTrailController </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author zhuzhen	
 * @date 2015年11月27日 下午3:37:04	
 * @version 1.0
 * <p>修改人：zhuzhen</p>
 * <p>修改时间：2015年11月27日 下午3:37:04</p>
 * <p>修改备注：</p>
 */
@RequestMapping("/transportTrailController")
@Controller
public class TransportTrailController
{
    @Autowired
    private TransportTrailService transportTrailService;
    
    @Autowired
    private UserServiceI userService;
    
    @Autowired
    private TransportDriverService transportDriverService;
    
    @Autowired
    private ITransportTaskAdminService transportTaskAdminService;
    
    /**
     * 
     * manager：一句话描述方法功能
     * @param req
     * @param resp
     * @param transportTrail
     * @return
     * @exception	
     * @author zhuzhen
     * @date 2015年11月27日 下午3:41:17
     */
    @RequestMapping("/manager")
    public String manager(HttpServletRequest req, HttpServletResponse resp,
        TransportTrail transportTrail)
    {
        List<ProjectDto> projects = userService.getProjectBySession(req.getSession());
        if(!CollectionUtils.isEmpty(projects))
        {
            req.setAttribute("projects", projects);
        }
        return "carte/lbs/trail/transportTrail";
    }
    
    
    /**
     * 
     * dataGrid：一句话描述方法功能
     * @param req
     * @param resp
     * @param transportTrail
     * @param pageHelperDto
     * @return
     * @exception	
     * @author zhuzhen
     * @date 2015年11月27日 下午3:43:11
     */
    @RequestMapping("/dataGrid")
    @ResponseBody
    public DataGrid dataGrid(HttpServletRequest req, HttpServletResponse resp,
        TransportTrailMongoDto transportTrail, PageHelperDto pageHelperDto)
    {
        DataGrid response = new DataGrid();
        
        List<ProjectDto> projects = userService.getProjectBySession(req.getSession());

        if (CollectionUtils.isEmpty(projects))
        {
            return null;
        }
        
        // 构建projectIds,每个projectId用逗号隔开
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < projects.size(); i++)
        {
            sb.append(projects.get(i).getId());
            if (i != projects.size() - 1)
            {
                sb.append(",");
            }
        }
        
        String projectIds = sb.toString();

        //用户是否拥有要查看的项目的权限
        if(!StringUtils.isEmpty(transportTrail.getProjectId()) && projectIds.contains(transportTrail.getProjectId()))
        {
            transportTrail.setProjectId(transportTrail.getProjectId());//以传过来的参数为准
        }
        else
        {
            transportTrail.setProjectId(projectIds);//查看该用户所拥有的所有项目
        }
        
      
        long count = transportTrailService.findCountBy(transportTrail);
        if(count > 0)
        {
            List<TransportTrailMongoDto> rows = transportTrailService.findBy(transportTrail, pageHelperDto);
            if(!CollectionUtils.isEmpty(rows))
            {   
                List<TransportDriverDto> drivers = transportDriverService.findTransportDriverDtosBy(new TransportDriverDto(), null);
                List<TransportTaskAdminDto> tasks = transportTaskAdminService.getTransportTaskAdminDtoBy(new TransportTaskAdminDto(), null);
                if(!CollectionUtils.isEmpty(drivers) && !CollectionUtils.isEmpty(tasks))
                {
                    for(TransportTrailMongoDto trail:rows)
                    {
                        //project
                        for(ProjectDto project:projects)
                        {
                            if(project.getId() != null && project.getId().equals(trail.getProjectId()))
                            {
                                trail.setProjectName(project.getProjName());
                            }
                        }
                        
                        //driver
                        for(TransportDriverDto driver:drivers)
                        {
                            if(driver.getId() != null && driver.getId().equals(trail.getDriverId()))
                            {
                                trail.setDriverName(driver.getName());
                            }
                        }
                        
                        //tasks
                        for(TransportTaskAdminDto task:tasks)
                        {
                            if(task.getId() != null && task.getId().equals(trail.getTransportTaskId()))
                            {
                                trail.setTaskName(task.getTaskName());
                            }
                        }
                    }
                }
                   
                response.setRows(rows);
                response.setTotal(count);
                return response;
                   
            }
        }
        
        response.setTotal(0L);
        return response;
    }
}

