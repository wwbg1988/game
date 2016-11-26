/**
 * 
 */
package com.ssic.catering.manage.controller;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssic.catering.admin.pageModel.DataGrid;
import com.ssic.catering.admin.pageModel.Json;
import com.ssic.catering.admin.pageModel.PageHelper;
import com.ssic.catering.admin.service.UserServiceI;
import com.ssic.catering.base.service.ITransportDestService;
import com.ssic.catering.lbs.dto.TransportDestDto;
import com.ssic.game.common.dao.ProjectDao;
import com.ssic.game.common.dto.PageHelperDto;
import com.ssic.game.common.dto.ProjectDto;
import com.ssic.util.BeanUtils;
import com.ssic.util.StringUtils;

/**		
 * <p>Title: TransportDestController </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 刘博	
 * @date 2015年11月25日 下午1:37:07	
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年11月25日 下午1:37:07</p>
 * <p>修改备注：</p>
 */
@Controller
@RequestMapping("/transportDestController")
public class TransportDestController
{
    @Autowired
    private ITransportDestService transportDestService;

    @Autowired
    private UserServiceI userServiceI;

    @Autowired
    private ProjectDao projectDao;

    /**
     * 菜品管理页面
     * 
     * @return
     */
    @RequestMapping("/manager")
    public String manager(HttpServletRequest request)
    {

        return "carte/lbs/transportDest";
    }

    /**
     * 
     * dataGrid：目的地信息管理
     * @param transportDestDto
     * @param ph
     * @return
     * @exception   
     * @author 刘博
     * @date 2015年10月21日 上午11:55:22
     */
    @RequestMapping("/dataGrid")
    @ResponseBody
    public DataGrid dataGrid(TransportDestDto transportDestDto, PageHelper ph, HttpSession session)
    {
        //获取用户拥有的项目权限
        List<ProjectDto> listProject = userServiceI.getProjectBySession(session);
        if (!CollectionUtils.isEmpty(listProject))
        {
            if (listProject.size() > 1)
            {//超管
                transportDestDto.setProjectId(null);
            }
            else
            {
                transportDestDto.setProjectId(listProject.get(0).getId());
            }
        }
        DataGrid dataGrid = new DataGrid();
        PageHelperDto phDto = new PageHelperDto();
        Integer beginRow = (ph.getPage() - 1) * ph.getRows();

        BeanUtils.copyProperties(ph, phDto);
        phDto.setBeginRow(beginRow);
        List<TransportDestDto> list = transportDestService.findALL(transportDestDto, phDto);
        for (int i = 0; i < list.size(); i++)
        {
            //根据projectId查询出projectName
            ProjectDto dto = projectDao.findById(list.get(i).getProjectId());
            if (dto != null)
            {
                list.get(i).setProjectName(dto.getProjName());
            }
            else
            {
                list.get(i).setProjectName("");
            }

        }
        int count = transportDestService.findCount(transportDestDto);
        dataGrid.setRows(list);
        dataGrid.setTotal(Long.valueOf(count));
        return dataGrid;
    }

    /**
     * 跳转到添加目的地页面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/addPage")
    public String addPage(HttpServletRequest request, HttpSession session)
    {
        // u.setCarteWeek(localWeek());
        try
        {

            TransportDestDto transportDestDto = new TransportDestDto();
            //获取用户拥有的项目权限
            List<ProjectDto> listProject = userServiceI.getProjectBySession(session);
            if (!CollectionUtils.isEmpty(listProject))
            {
                if (listProject.size() > 1)
                {//超管
                    transportDestDto.setProjectId(null);
                }
                else
                {
                    transportDestDto.setProjectId(listProject.get(0).getId());
                }
            }
            transportDestDto.setId(UUID.randomUUID().toString());
            request.setCharacterEncoding("utf-8");
            request.setAttribute("transportDestDto", transportDestDto);
        }
        catch (UnsupportedEncodingException e)
        {
            //  对异常进行简要描述
        }
        return "carte/lbs/transportDestAdd";
    }

    /**
     * 添加目的地
     * 
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public Json add(TransportDestDto transportDestDto, HttpSession session)
    {
        Json j = new Json();

        try
        {
            if (transportDestDto.getLongitude() == null || transportDestDto.getLongitude() == "")
            {
                j.setMsg("经度不能为空!");
                j.setSuccess(false);
                return j;
            }
            if (transportDestDto.getLatitude() == null || transportDestDto.getLatitude() == "")
            {
                j.setMsg("纬度不能为空!");
                j.setSuccess(false);
                return j;
            }

            //获取用户拥有的项目权限
            List<ProjectDto> listProject = userServiceI.getProjectBySession(session);
            if (!CollectionUtils.isEmpty(listProject))
            {
                if (listProject.size() > 1)
                {//超管
                    transportDestDto.setProjectId(null);
                }
                else
                {
                    transportDestDto.setProjectId(listProject.get(0).getId());
                }
            }
       
            List<TransportDestDto> list =
                transportDestService.findByLongitudeAndLatitude(transportDestDto.getLongitude(),
                    transportDestDto.getLatitude());

            if (!CollectionUtils.isEmpty(list))
            {
                j.setSuccess(false);
                j.setMsg("该目的地已经存在,请勿重复添加！");
                return j;
            }
            transportDestService.add(transportDestDto);
            j.setSuccess(true);
            j.setMsg("添加目的地成功！");
            j.setObj(transportDestDto);
        }
        catch (Exception e)
        {
            // e.printStackTrace();
            j.setMsg(e.getMessage());
        }
        return j;
    }

    /**
     * 编辑目的地页面
     * 
     * @return
     */
    @RequestMapping("/editPage")
    public String editPage(HttpServletRequest request, String id)
    {

        TransportDestDto r = transportDestService.findById(id);

        request.setAttribute("transportDestDto", r);
        return "carte/lbs/transportDestEdit";
    }

    /**
     * 编辑目的地
     * 
     * @param resource
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    public Json edit(TransportDestDto transportDestDto)
    {
        Json j = new Json();
        TransportDestDto r = transportDestService.findById(transportDestDto.getId());
        r.setAddress(transportDestDto.getAddress());
        r.setLastUpdateTime(new Date());
        transportDestService.update(r);
        j.setSuccess(true);
        j.setMsg("编辑成功！");
        return j;
    }

    /**
     * 删除目的地
     * 
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Json delete(String id)
    {
        Json j = new Json();
        TransportDestDto r = transportDestService.findById(id);
        transportDestService.delete(r);
        j.setMsg("删除目的地成功！");
        j.setSuccess(true);
        return j;
    }
    
    /**
     * 
     * getTransportDestDtosBy：下拉列表
     * @param req
     * @param resp
     * @param transportDestDto
     * @return
     * @exception   
     * @author zhuzhen
     * @date 2015年11月26日 下午2:28:56
     */
    @RequestMapping("/getTransportDestDtosBy")
    @ResponseBody
    public List<TransportDestDto> getTransportDestDtosBy(HttpServletRequest req, HttpServletResponse resp, TransportDestDto transportDestDto)
    {
        String projectIds = userServiceI.getProjectIdsBySession(req.getSession());
        //用户是否拥有要查看的项目的权限
        if(!StringUtils.isEmpty(transportDestDto.getProjectId()) && projectIds.contains(transportDestDto.getProjectId()))
        {
            transportDestDto.setProjectId(transportDestDto.getProjectId());//以传过来的参数为准
        }
        else
        {
            transportDestDto.setProjectId(projectIds);//查看该用户所拥有的所有项目
        }
        return transportDestService.findBy(transportDestDto);
    }

}
