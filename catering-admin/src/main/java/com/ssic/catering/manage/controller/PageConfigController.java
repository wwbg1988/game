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
import com.ssic.catering.admin.pageModel.Json;
import com.ssic.catering.admin.service.UserServiceI;
import com.ssic.catering.base.dto.PageConfigDto;
import com.ssic.catering.base.dto.PageHelperDto;
import com.ssic.catering.base.service.ICafetoriumService;
import com.ssic.catering.base.service.IPageConfigService;
import com.ssic.game.common.dto.ProjectDto;

/**
 * <p>Title: pageConfigController </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author zhuzhen	
 * @date 2015年11月23日 下午4:49:59	
 * @version 1.0
 * <p>修改人：zhuzhen</p>
 * <p>修改时间：2015年11月23日 下午4:49:59</p>
 * <p>修改备注：</p>
 */
@Controller
@RequestMapping("/pageConfigController")
public class PageConfigController
{
    @Autowired
    private IPageConfigService pageConfigService;
    
    @Autowired
    private UserServiceI userService;
    
    @Autowired
    private ICafetoriumService cafetoriumService;
    
    /**
     * 
     * manager：管理
     * @return
     * @exception	
     * @author zhuzhen
     * @date 2015年11月23日 下午4:58:08
     */
    @RequestMapping("/manager")
    public String manager(HttpServletRequest req, HttpServletResponse resp)
    {
        List<ProjectDto> projects = userService.getProjectBySession(req.getSession());
        if(!CollectionUtils.isEmpty(projects))
        {
            req.setAttribute("projects", projects);
            return "carteMall/pageConfig/pageConfig";

        }
        req.setAttribute("errorMsg", "该用户没有所属项目");
        return "error/errorMsg";
    }
    
    /**
     * 
     * dataGrid：列表
     * @param req
     * @param resp
     * @param pageConfigDto
     * @param ph
     * @return
     * @exception	
     * @author zhuzhen
     * @date 2015年11月23日 下午5:10:54
     */
    @RequestMapping("/dataGrid")
    @ResponseBody
    public DataGrid dataGrid(HttpServletRequest req, HttpServletResponse resp, 
        PageConfigDto pageConfigDto, PageHelperDto ph)
    {
       DataGrid response = new DataGrid();
       
       List<ProjectDto> projects = userService.getProjectBySession(req.getSession());
       if(CollectionUtils.isEmpty(projects))
       {
           response.setTotal(0L);
           return response;
       }
       
       //构建projectIds,每个projectId用逗号隔开
       StringBuilder sb = new StringBuilder();
       for(int i=0; i<projects.size(); i++)
       {
           sb.append(projects.get(i).getId());
           if(i != projects.size() -1)
           {
               sb.append(",");
           }
       }
       
       String projectIds = sb.toString();
       
       //用户是否拥有要查看的项目的权限
       if(!StringUtils.isEmpty(pageConfigDto.getProjectId()) && projectIds.contains(pageConfigDto.getProjectId()))
       {
           pageConfigDto.setProjectId(pageConfigDto.getProjectId());//以传过来的参数为准
       }
       else
       {
           pageConfigDto.setProjectId(projectIds);//查看该用户所拥有的所有项目
       }
       
       
       long count = pageConfigService.getPageConfigDtoCountBy(pageConfigDto);
       if(count <= 0)
       {
           response.setTotal(0L);
       }
       else
       {
           List<PageConfigDto> rows = pageConfigService.getPageConfigDtoBy(pageConfigDto, ph);
           if(!CollectionUtils.isEmpty(rows))
           {
               response.setTotal(count);
               response.setRows(rows);
           }
       }
       
       return response;
    }
    
    /**
     * 
     * showAddPageConfig：跳转到添加配置项的页面
     * @param req
     * @param resp
     * @return
     * @exception	
     * @author zhuzhen
     * @date 2015年11月23日 下午5:47:05
     */
    @RequestMapping("/addPage")
    public String showAddPageConfig(HttpServletRequest req, HttpServletResponse resp)
    {
        List<ProjectDto> projects = userService.getProjectBySession(req.getSession());
        if(!CollectionUtils.isEmpty(projects))
        {
            req.setAttribute("projects", projects);
        }
        return "carteMall/pageConfig/pageConfigAdd";
    }
    
    /**
     * 
     * showEditPageConfig：一句话描述方法功能
     * @param req
     * @param resp
     * @return
     * @exception	
     * @author zhuzhen
     * @date 2015年11月30日 下午6:10:56
     */
    @RequestMapping("/editPage")
    public String showEditPageConfig(HttpServletRequest req, HttpServletResponse resp, String id)
    {
        if(StringUtils.isEmpty(id))
        {
            req.setAttribute("errorMsg", "未选中配置项");
            return "error/errorMsg";
        }
        
        List<ProjectDto> projects = userService.getProjectBySession(req.getSession());
        if(!CollectionUtils.isEmpty(projects))
        {
            PageConfigDto pageConfig = pageConfigService.findPageConfigDtoById(id);
            if(pageConfig != null)
            {
                req.setAttribute("projects", projects);
                req.setAttribute("pageConfig", pageConfig);
                return "carteMall/pageConfig/pageConfigEdit";
            }
            
            req.setAttribute("errorMsg", "配置项不存在");
            return "error/errorMsg";
        }
        
        req.setAttribute("errorMsg", "用户没有所有项目");
        return "error/errorMsg";
    }
    
    /**
     * 
     * updatePageConfig：一句话描述方法功能
     * @param req
     * @param resp
     * @param pageConfigDto
     * @return
     * @exception	
     * @author zhuzhen
     * @date 2015年11月30日 下午6:11:47
     */
    @ResponseBody
    @RequestMapping("/edit")
    public Json updatePageConfig(HttpServletRequest req, HttpServletResponse resp,PageConfigDto pageConfigDto)
    {
        Json response = new Json();
        
        if(StringUtils.isEmpty(pageConfigDto.getId()))
        {
            response.setSuccess(false);
            response.setMsg("id不能为空");
            return response;
        }
        
        //配置项描述
        if(StringUtils.isEmpty(pageConfigDto.getName()))
        {
            response.setSuccess(false);
            response.setMsg("配置项描述不能为空");
            return response;
        }
        
        //项目
        if(StringUtils.isEmpty(pageConfigDto.getProjectId()))
        {
            response.setSuccess(false);
            response.setMsg("项目不能为空");
            return response;
        }
        
        //食堂
        if(StringUtils.isEmpty(pageConfigDto.getCafetoriumId()))
        {
            response.setSuccess(false);
            response.setMsg("食堂不能为空");
            return response;
        }
        
        //启用url
        if(StringUtils.isEmpty(pageConfigDto.getEnabledUrl()))
        {
            response.setSuccess(false);
            response.setMsg("启用url不能为空");
            return response;
        }
        
        //非启用url
        if(StringUtils.isEmpty(pageConfigDto.getNotEnabledUrl()))
        {
            response.setSuccess(false);
            response.setMsg("非启用url不能为空");
            return response;
        }
        
        //是否启用
        if(StringUtils.isEmpty(pageConfigDto.getIsEnabled()))
        {
            response.setSuccess(false);
            response.setMsg("是否启用不能为空");
            return response;
        }
        
        if(pageConfigService.findPageConfigDtoById(pageConfigDto.getId()) == null)
        {
            response.setSuccess(false);
            response.setMsg("配置不存在");
            return response;
        }
        
        
        PageConfigDto newRecord = new PageConfigDto();
        newRecord.setId(pageConfigDto.getId());//id
        newRecord.setName(pageConfigDto.getName());//描述
        newRecord.setProjectId(pageConfigDto.getProjectId());//项目id
        newRecord.setCafetoriumId(pageConfigDto.getCafetoriumId());//餐厅id
        newRecord.setIsEnabled(pageConfigDto.getIsEnabled());//是否可用
        newRecord.setEnabledUrl(pageConfigDto.getEnabledUrl());
        newRecord.setNotEnabledUrl(pageConfigDto.getNotEnabledUrl());
        
        
        int count = pageConfigService.updatePageConfig(newRecord);
        if(count > 0)
        {
            response.setSuccess(true);
            response.setMsg("编辑成功");
        }
        else
        {
            response.setSuccess(false);
            response.setMsg("编辑失败");
        }
        
        return response;

    }
    
    /**
     * 
     * showEnabledPage：一句话描述方法功能
     * @param req
     * @param resp
     * @param id
     * @return
     * @exception	
     * @author zhuzhen
     * @date 2015年12月1日 上午8:53:11
     */
    @RequestMapping("/editEnabledPage")
    public String showEnabledPage(HttpServletRequest req, HttpServletResponse resp, String id)
    {
        if(StringUtils.isEmpty(id))
        {
            req.setAttribute("errorMsg", "未选中配置项");
            return "error/errorMsg";
        }
        
        List<ProjectDto> projects = userService.getProjectBySession(req.getSession());
        if(!CollectionUtils.isEmpty(projects))
        {
            PageConfigDto pageConfig = pageConfigService.findPageConfigDtoById(id);
            if(pageConfig != null)
            {
                req.setAttribute("projects", projects);
                req.setAttribute("pageConfig", pageConfig);
                return "carteMall/pageConfig/pageConfigEnabled";
            }
            
            req.setAttribute("errorMsg", "配置项不存在");
            return "error/errorMsg";
        }
        
        req.setAttribute("errorMsg", "用户没有所有项目");
        return "error/errorMsg";
    }
    
    /**
     * 
     * enabledPageConfig：一句话描述方法功能
     * @param req
     * @param resp
     * @param pageConfigDto
     * @return
     * @exception	
     * @author zhuzhen
     * @date 2015年12月1日 上午8:54:50
     */
    @ResponseBody
    @RequestMapping("/editEnabled")
    public Json enabledPageConfig(HttpServletRequest req, HttpServletResponse resp,PageConfigDto pageConfigDto)
    {
        Json response = new Json();
        
        if(StringUtils.isEmpty(pageConfigDto.getId()))
        {
            response.setSuccess(false);
            response.setMsg("id不能为空");
            return response;
        }
        
        //启用url
        if(StringUtils.isEmpty(pageConfigDto.getEnabledUrl()))
        {
            response.setSuccess(false);
            response.setMsg("启用url不能为空");
            return response;
        }
        
        //非启用url
        if(StringUtils.isEmpty(pageConfigDto.getNotEnabledUrl()))
        {
            response.setSuccess(false);
            response.setMsg("非启用url不能为空");
            return response;
        }
        
        //是否启用
        if(StringUtils.isEmpty(pageConfigDto.getIsEnabled()))
        {
            response.setSuccess(false);
            response.setMsg("是否启用不能为空");
            return response;
        }
        
        if(pageConfigService.findPageConfigDtoById(pageConfigDto.getId()) == null)
        {
            response.setSuccess(false);
            response.setMsg("配置不存在");
            return response;
        }
        
        
        PageConfigDto newRecord = new PageConfigDto();
        newRecord.setId(pageConfigDto.getId());
        newRecord.setIsEnabled(pageConfigDto.getIsEnabled());//是否可用
        newRecord.setEnabledUrl(pageConfigDto.getEnabledUrl());
        newRecord.setNotEnabledUrl(pageConfigDto.getNotEnabledUrl());
        
        
        int count = pageConfigService.updatePageConfig(newRecord);
        if(count > 0)
        {
            response.setSuccess(true);
            response.setMsg("保存成功");
        }
        else
        {
            response.setSuccess(false);
            response.setMsg("保存失败");
        }
        
        return response;

    }
    
    /**
     * 
     * addPageConfig：添加记录
     * @param req
     * @param resp
     * @param pageConfigDto
     * @return
     * @exception	
     * @author zhuzhen
     * @date 2015年11月23日 下午5:47:28
     */
    @ResponseBody
    @RequestMapping("/add")
    public Json addPageConfig(HttpServletRequest req, HttpServletResponse resp,PageConfigDto pageConfigDto)
    {
        Json response = new Json();
        
        //配置项描述
        if(StringUtils.isEmpty(pageConfigDto.getName()))
        {
            response.setSuccess(false);
            response.setMsg("配置项描述不能为空");
            return response;
        }
        
        //项目
        if(StringUtils.isEmpty(pageConfigDto.getProjectId()))
        {
            response.setSuccess(false);
            response.setMsg("项目不能为空");
            return response;
        }
        
        //食堂
        if(StringUtils.isEmpty(pageConfigDto.getCafetoriumId()))
        {
            response.setSuccess(false);
            response.setMsg("食堂不能为空");
            return response;
        }
        
        //启用url
        if(StringUtils.isEmpty(pageConfigDto.getEnabledUrl()))
        {
            response.setSuccess(false);
            response.setMsg("启用url不能为空");
            return response;
        }
        
        //非启用url
        if(StringUtils.isEmpty(pageConfigDto.getNotEnabledUrl()))
        {
            response.setSuccess(false);
            response.setMsg("非启用url不能为空");
            return response;
        }
        
        //是否启用
        if(StringUtils.isEmpty(pageConfigDto.getIsEnabled()))
        {
            response.setSuccess(false);
            response.setMsg("是否启用不能为空");
            return response;
        }
        
        PageConfigDto newRecord = new PageConfigDto();
        newRecord.setName(pageConfigDto.getName());//描述
        newRecord.setProjectId(pageConfigDto.getProjectId());//项目id
        newRecord.setCafetoriumId(pageConfigDto.getCafetoriumId());//餐厅id
        newRecord.setIsEnabled(pageConfigDto.getIsEnabled());//是否可用
        newRecord.setEnabledUrl(pageConfigDto.getEnabledUrl());
        newRecord.setNotEnabledUrl(pageConfigDto.getNotEnabledUrl());
        
        int count = pageConfigService.addPageConfig(newRecord);
        if(count > 0)
        {
            response.setSuccess(true);
            response.setMsg("添加成功");
        }
        else
        {
            response.setSuccess(false);
            response.setMsg("添加失败");
        }
        
        return response;

    }
    
    /**
     * 
     * enabledPageConfig：一句话描述方法功能
     * @param req
     * @param resp
     * @param pageConfigDto
     * @return
     * @exception	
     * @author zhuzhen
     * @date 2015年12月1日 上午9:20:41
     */
    @ResponseBody
    @RequestMapping("/delete")
    public Json deletePageConfig(HttpServletRequest req, HttpServletResponse resp,String id)
    {
        Json response = new Json();
        
        if(StringUtils.isEmpty(id))
        {
            response.setSuccess(false);
            response.setMsg("id不能为空");
            return response;
        }
        
        
        if(pageConfigService.findPageConfigDtoById(id) == null)
        {
            response.setSuccess(false);
            response.setMsg("配置不存在");
            return response;
        }
        
        
        PageConfigDto newRecord = new PageConfigDto();
        newRecord.setId(id);
        newRecord.setStat(0);//更改为无效
        
        
        int count = pageConfigService.updatePageConfig(newRecord);
        if(count > 0)
        {
            response.setSuccess(true);
            response.setMsg("删除成功");
        }
        else
        {
            response.setSuccess(false);
            response.setMsg("删除失败");
        }
        
        return response;

    }
}

