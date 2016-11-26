package com.ssic.catering.manage.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssic.catering.admin.pageModel.DataGrid;
import com.ssic.catering.admin.pageModel.Json;
import com.ssic.catering.admin.pageModel.SessionInfo;
import com.ssic.catering.admin.service.ISupplierAdminUsersAdminService;
import com.ssic.catering.admin.service.UserServiceI;
import com.ssic.catering.admin.util.ConfigUtil;
import com.ssic.catering.base.service.ISupplierService;
import com.ssic.catering.base.service.ITransportTaskAdminService;
import com.ssic.catering.lbs.dto.SupplierAdminUsersDto;
import com.ssic.catering.lbs.dto.SupplierDto;
import com.ssic.catering.lbs.dto.TransportDriverDto;
import com.ssic.catering.lbs.dto.TransportTaskAdminDto;
import com.ssic.catering.lbs.dto.TransportTaskDto;
import com.ssic.catering.lbs.service.TransportDriverService;
import com.ssic.game.common.dto.PageHelperDto;
import com.ssic.game.common.dto.ProjectDto;

/**
 * 
 * 		
 * <p>Title: TransportTaskController </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author zhuzhen	
 * @date 2015年11月26日 上午9:36:09	
 * @version 1.0
 * <p>修改人：zhuzhen</p>
 * <p>修改时间：2015年11月26日 上午9:36:09</p>
 * <p>修改备注：</p>
 */
@Controller
@RequestMapping("/transportTaskController")
public class TransportTaskController
{
    @Autowired
    private ITransportTaskAdminService transportTaskAdminService;
    
    @Autowired
    private UserServiceI userService;
    
    @Autowired
    private TransportDriverService transportDriverService;
    
    @Autowired
    private ISupplierAdminUsersAdminService supplierAdminUsersAdminService;
   
    @Autowired
    private ISupplierService supplierService;
    
    @RequestMapping("/manager")
    public String manager(HttpServletRequest req, HttpServletResponse resp)
    {
        List<ProjectDto> projects = userService.getProjectBySession(req.getSession());
        if(!CollectionUtils.isEmpty(projects))
        {
            req.setAttribute("projects", projects);
        }
        return "carte/lbs/task/transportTask";
    }
    
    /**
     * 
     * dataGrid：一句话描述方法功能
     * @param req
     * @param resp
     * @param transportTaskDto
     * @return
     * @exception	
     * @author zhuzhen
     * @date 2015年11月26日 上午9:45:59
     */
    @RequestMapping("/dataGrid")
    @ResponseBody
    public DataGrid dataGrid(HttpServletRequest req, HttpServletResponse resp, 
        TransportTaskAdminDto transportTaskDto, PageHelperDto pageHelperDto)
    {
        DataGrid response = new DataGrid();
        
        String supplierIds = this.findSuppliersBySession(req.getSession());
        
        if(StringUtils.isEmpty(supplierIds))
        {
            response.setTotal(0L);
            return response;
        }
        
        if(StringUtils.isEmpty(transportTaskDto.getAdminSupplierUserId()))
        {
            transportTaskDto.setAdminSupplierUserId(supplierIds);
        }
        else if(!supplierIds.contains(transportTaskDto.getAdminSupplierUserId()))
        {
            response.setTotal(0L);
            return response;
        }
        long count = transportTaskAdminService.getTransportTaskAdminDtoCountBy(transportTaskDto);
        if(count <= 0)
        {
            response.setTotal(0L);
            return response;
        }
        
        response.setTotal(count);
        response.setRows(transportTaskAdminService.getTransportTaskAdminDtoBy(transportTaskDto, pageHelperDto));
        return response;
    }
    
    /**
     * 
     * showPublishPage：显示发布页面
     * @param req
     * @param resp
     * @return
     * @exception	
     * @author zhuzhen
     * @date 2015年11月26日 上午11:57:34
     */
    @RequestMapping("/addPage")
    public String showPublishPage(HttpServletRequest req, HttpServletResponse resp)
    {
        List<ProjectDto> projects = userService.getProjectBySession(req.getSession());
        if(!CollectionUtils.isEmpty(projects))
        {
            req.setAttribute("projects", projects);
        }
        return "carte/lbs/task/transportTaskAdd";
    }
    
    /**
     * 
     * addTransportTask：发布功能
     * @param req
     * @param resp
     * @return
     * @exception	
     * @author zhuzhen
     * @date 2015年11月26日 上午11:58:54
     */
    @RequestMapping("/add")
    @ResponseBody
    public Json addTransportTask(HttpServletRequest req, HttpServletResponse resp, TransportTaskDto transportTaskDto)
    {
        Json response = new Json();
        
        if(StringUtils.isEmpty(transportTaskDto.getProjectId()))
        {
            response.setSuccess(false);
            response.setMsg("项目不能为空");
            return response;            
        }
        
        
        if(StringUtils.isEmpty(transportTaskDto.getAdminSupplierUserId()))
        {
            response.setSuccess(false);
            response.setMsg("供应商不能为空");
            return response;            
        }
        
        if(StringUtils.isEmpty(transportTaskDto.getSupplierName()))
        {
            response.setSuccess(false);
            response.setMsg("供应商名字不能为空");
            return response;            
        }
        
        if(StringUtils.isEmpty(transportTaskDto.getTaskName()))
        {
            response.setSuccess(false);
            response.setMsg("任务名称不能为空");
            return response;            
        }
        
        if(StringUtils.isEmpty(transportTaskDto.getTransportDestId()))
        {
            response.setSuccess(false);
            response.setMsg("目的地不能为空");
            return response;            
        }
        
        
        if(StringUtils.isEmpty(transportTaskDto.getMerchandise()))
        {
            response.setSuccess(false);
            response.setMsg("货物不能为空");
            return response;            
        }
        
        
        
        HttpSession session = req.getSession();
        SessionInfo sessionInfo = (SessionInfo)session.getAttribute(ConfigUtil.SESSIONINFONAME);
        if(sessionInfo == null)
        {
            response.setMsg("请先登录");
            response.setSuccess(false);
            return response;
        }
        
        TransportTaskAdminDto newRecord = new TransportTaskAdminDto();
        newRecord.setProjectId(transportTaskDto.getProjectId());//项目id
        newRecord.setAdminSupplierUserId(transportTaskDto.getAdminSupplierUserId());//供应商id
        newRecord.setSupplierName(transportTaskDto.getSupplierName());//供应商名字
        newRecord.setTaskName(transportTaskDto.getTaskName());//任务名称
        newRecord.setTransportDestId(transportTaskDto.getTransportDestId());//目的地
        newRecord.setMerchandise(transportTaskDto.getMerchandise());//货物
        //将登录用户作为发布人
        newRecord.setAdminUserId(sessionInfo.getId());
        newRecord.setState(1);//未开始
    
        int count = transportTaskAdminService.addTransportTask(newRecord);
        if(count > 0)
        {
            response.setMsg("发布成功");
            response.setSuccess(true);
            return response;
        }
        
        response.setMsg("发布失败");
        response.setSuccess(false);
        return response;
    }
    
    /**
     * 
     * showPublishPage：一句话描述方法功能
     * @param req
     * @param resp
     * @return
     * @exception	
     * @author zhuzhen
     * @date 2015年11月30日 上午10:40:08
     */
    @RequestMapping("/editPage")
    public String showEditPage(HttpServletRequest req, HttpServletResponse resp,
        TransportTaskAdminDto transportTaskDto)
    {
        if(StringUtils.isEmpty(transportTaskDto.getId()))
        {
            req.setAttribute("errorMsg", "未选中任务");
            return "error/errorMsg";//跳转到错误页面
        }
        
        List<TransportTaskAdminDto> tasks = transportTaskAdminService.getTransportTaskAdminDtoBy(transportTaskDto,null);
        
        if(!CollectionUtils.isEmpty(tasks))
        {
            List<ProjectDto> projects = userService.getProjectBySession(req.getSession());
            if(!CollectionUtils.isEmpty(projects))
            {
                req.setAttribute("projects", projects);
                
                req.setAttribute("task", tasks.get(0));
                return "carte/lbs/task/transportTaskEdit";
            }
            
            req.setAttribute("errorMsg", "该用户没有所属项目");
            return "error/errorMsg";
        }
        
        req.setAttribute("errorMsg", "该任务不存在");
        return "error/errorMsg";
    }
    
   /**
    * 
    * updateTransportTask：一句话描述方法功能
    * @param req
    * @param resp
    * @param transportTaskDto
    * @return
    * @exception	
    * @author zhuzhen
    * @date 2015年11月30日 上午10:41:37
    */
    @RequestMapping("/edit")
    @ResponseBody
    public Json updateTransportTask(HttpServletRequest req, HttpServletResponse resp, TransportTaskDto transportTaskDto)
    {
        Json response = new Json();
        
        if(StringUtils.isEmpty(transportTaskDto.getId()))
        {
            response.setSuccess(false);
            response.setMsg("未选中任务");
            return response;            
        }
        else
        {
            //判断任务是否存在
            if(transportTaskAdminService.findById(transportTaskDto.getId()) == null)
            {
                response.setSuccess(false);
                response.setMsg("任务不存在");
                return response; 
            }
        }
       
        
        TransportTaskAdminDto param = new TransportTaskAdminDto();
        param.setId(transportTaskDto.getId());
        param.setState(2);//正在进行
        
        //是否存在
        if(!CollectionUtils.isEmpty(transportTaskAdminService.getTransportTaskAdminDtoBy(param, null)))
        {
            response.setSuccess(false);
            response.setMsg("选中的任务正在进行，不能进行修改");
            return response;        
        }
        
        if(StringUtils.isEmpty(transportTaskDto.getProjectId()))
        {
            response.setSuccess(false);
            response.setMsg("项目不能为空");
            return response;            
        }
        
        
        if(StringUtils.isEmpty(transportTaskDto.getAdminSupplierUserId()))
        {
            response.setSuccess(false);
            response.setMsg("供应商不能为空");
            return response;            
        }
        
        if(StringUtils.isEmpty(transportTaskDto.getSupplierName()))
        {
            response.setSuccess(false);
            response.setMsg("供应商名字不能为空");
            return response;            
        }
        
        if(StringUtils.isEmpty(transportTaskDto.getTaskName()))
        {
            response.setSuccess(false);
            response.setMsg("任务名字不能为空");
            return response;            
        }
        
        
        if(StringUtils.isEmpty(transportTaskDto.getTransportDestId()))
        {
            response.setSuccess(false);
            response.setMsg("目的地不能为空");
            return response;            
        }
        
        
        if(StringUtils.isEmpty(transportTaskDto.getMerchandise()))
        {
            response.setSuccess(false);
            response.setMsg("货物不能为空");
            return response;            
        }
        
        TransportTaskAdminDto newRecord = new TransportTaskAdminDto();
        newRecord.setId(transportTaskDto.getId());//id
        newRecord.setProjectId(transportTaskDto.getProjectId());//项目id
        newRecord.setAdminSupplierUserId(transportTaskDto.getAdminSupplierUserId());//供应商id
        newRecord.setSupplierName(transportTaskDto.getSupplierName());
        newRecord.setTransportDestId(transportTaskDto.getTransportDestId());//目的地id
        newRecord.setMerchandise(transportTaskDto.getMerchandise());//货物
        newRecord.setTaskName(transportTaskDto.getTaskName());// 任务名称
    
        int count = transportTaskAdminService.updateTransportTask(newRecord);
        if(count > 0)
        {
            response.setMsg("编辑成功");
            response.setSuccess(true);
            return response;
        }
        
        response.setMsg("编辑失败");
        response.setSuccess(false);
        return response;
    }
    
    /**
     * 
     * deleteTransportTask：一句话描述方法功能
     * @param req
     * @param resp
     * @param transportTaskDto
     * @return
     * @exception	
     * @author zhuzhen
     * @date 2015年11月30日 上午10:47:24
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Json deleteTransportTask(HttpServletRequest req, HttpServletResponse resp, TransportTaskDto transportTaskDto)
    {
        Json response = new Json();
        
        if(StringUtils.isEmpty(transportTaskDto.getId()))
        {
            response.setSuccess(false);
            response.setMsg("未选中任务");
            return response;            
        }
       
        
        TransportTaskAdminDto param = new TransportTaskAdminDto();
        param.setId(transportTaskDto.getId());
        param.setState(2);//正在进行
        
        //是否存在
        if(!CollectionUtils.isEmpty(transportTaskAdminService.getTransportTaskAdminDtoBy(param, null)))
        {
            response.setSuccess(false);
            response.setMsg("选中的任务正在进行，不能删除");
            return response;        
        }
        
        TransportTaskAdminDto newRecord = new TransportTaskAdminDto();
        newRecord.setId(transportTaskDto.getId());
        newRecord.setStat(0);//更改为无效状态
        int count = transportTaskAdminService.updateTransportTask(newRecord);
        if(count > 0)
        {
            response.setMsg("删除成功");
            response.setSuccess(true);
            return response;
        }
        
        response.setMsg("删除失败");
        response.setSuccess(false);
        return response;
    }
    
    /**
     * 
     * addTransportTask：配送功能
     * @param req
     * @param resp
     * @return
     * @exception   
     * @author zhuzhen
     * @date 2015年11月26日 上午11:58:54
     */
    @RequestMapping("/dispatching/add")
    @ResponseBody
    public Json dispatchingTransportTask(HttpServletRequest req, HttpServletResponse resp, TransportTaskDto transportTaskDto)
    {
        Json response = new Json();
        
        if(StringUtils.isEmpty(transportTaskDto.getId()))
        {
            response.setSuccess(false);
            response.setMsg("未选中任务");
            return response;            
        }
        
        if(StringUtils.isEmpty(transportTaskDto.getDriverId()))
        {
            response.setSuccess(false);
            response.setMsg("配送人不能为空");
            return response;     
        }
        
        TransportTaskAdminDto param = new TransportTaskAdminDto();
        param.setId(transportTaskDto.getId());
        param.setState(1);//未开始
        
        //是否存在
        if(CollectionUtils.isEmpty(transportTaskAdminService.getTransportTaskAdminDtoBy(param, null)))
        {
            response.setSuccess(false);
            response.setMsg("选中的任务已处理");
            return response;        
        }
        
        TransportDriverDto transportDriverDto = new TransportDriverDto();
        transportDriverDto.setId(transportTaskDto.getDriverId());
        List<TransportDriverDto> drivers = transportDriverService.findTransportDriverDtosBy(transportDriverDto, null);
        if(CollectionUtils.isEmpty(drivers))
        {
            response.setSuccess(false);
            response.setMsg("配送人不存在");
            return response;    
        }
        else if(drivers.size() > 1)
        {
            response.setSuccess(false);
            response.setMsg("配送人重复");
            return response;  
        }
        
        TransportTaskAdminDto newTaskRecord = new TransportTaskAdminDto();
        newTaskRecord.setId(transportTaskDto.getId());
        newTaskRecord.setDriverId(transportTaskDto.getDriverId());
        newTaskRecord.setDepartPlace(transportTaskDto.getDepartPlace());
        
        int count = transportTaskAdminService.dispatchingTask(newTaskRecord);
        if(count > 0)
        {
            response.setMsg("配送成功");
            response.setSuccess(true);
            return response;
        }
        
        response.setMsg("配送失败");
        response.setSuccess(false);
        return response;
    }
    
    /**
     * 
     * dataGrid：一句话描述方法功能
     * @param req
     * @param resp
     * @param transportTaskDto
     * @param pageHelperDto
     * @return
     * @exception	
     * @author zhuzhen
     * @date 2015年11月26日 下午5:16:24
     */
    @RequestMapping("dispatching/dataGrid")
    @ResponseBody
    public DataGrid dispatchingDataGrid(HttpServletRequest req, HttpServletResponse resp, 
        TransportTaskAdminDto transportTaskDto, PageHelperDto pageHelperDto)
    {
        DataGrid response = new DataGrid();
        
        String supplierIds = this.findSuppliersBySession(req.getSession());
        
        if(StringUtils.isEmpty(supplierIds))
        {
            response.setTotal(0L);
            return response;
        }
        
        if(StringUtils.isEmpty(transportTaskDto.getAdminSupplierUserId()))
        {
            transportTaskDto.setAdminSupplierUserId(supplierIds);
        }
        else if(!supplierIds.contains(transportTaskDto.getAdminSupplierUserId()))
        {
            response.setTotal(0L);
            return response;
        }
        
        
        long count = transportTaskAdminService.getTransportTaskAdminDtoCountBy(transportTaskDto);
        if(count <= 0)
        {
            response.setTotal(0L);
            return response;
        }
        
        response.setTotal(count);
        response.setRows(transportTaskAdminService.getTransportTaskAdminDtoBy(transportTaskDto, pageHelperDto));
        return response;
    }
    
    /**
     * 
     * addTransportTask：配送页面
     * @param req
     * @param resp
     * @return
     * @exception   
     * @author zhuzhen
     * @date 2015年11月26日 上午11:58:54
     */
    @RequestMapping("/dispatching/addPage")
    public String showDispatchingPage(HttpServletRequest req, HttpServletResponse resp, TransportTaskAdminDto transportTaskDto)
    {
        if(StringUtils.isEmpty(transportTaskDto.getId()))
        {
            req.setAttribute("errorMsg", "未选中任务");
            return "error/errorMsg";//跳转到错误页面
        }
        
        List<TransportTaskAdminDto> tasks = transportTaskAdminService.getTransportTaskAdminDtoBy(transportTaskDto,null);
        
        if(!CollectionUtils.isEmpty(tasks))
        {
            TransportDriverDto param = new TransportDriverDto();
            param.setAdminUserId(tasks.get(0).getAdminSupplierUserId());
            List<TransportDriverDto> drivers = transportDriverService.findTransportDriverDtosBy(param, null);
            if(!CollectionUtils.isEmpty(drivers))
            {
                req.setAttribute("task", tasks.get(0));
                req.setAttribute("drivers", drivers);
                return "carte/lbs/task/transportTaskDispatchingAdd";
            }
            
            req.setAttribute("errorMsg", "该配送人不存在");
            return "error/errorMsg";//跳转到错误页面
        }
        
       
        
        req.setAttribute("errorMsg", "该任务不存在");
        return "error/errorMsg";//跳转到错误页面
    }
    
    
    /**
     * 
     * showDispatchingPage：一句话描述方法功能
     * @param req
     * @param resp
     * @param transportTaskDto
     * @return
     * @exception	
     * @author zhuzhen
     * @date 2015年11月26日 下午5:20:31
     */
    @RequestMapping("/dispatching/manager")
    public String showDispatchingManager(HttpServletRequest req, HttpServletResponse resp, TransportTaskDto transportTaskDto)
    {
        List<ProjectDto> projects = userService.getProjectBySession(req.getSession());
        if(!CollectionUtils.isEmpty(projects))
        {
            req.setAttribute("projects", projects);
        }
        return "carte/lbs/task/transportTaskDispatching";
    }
    
    /**
     * 
     * findTransportTaskDtoBy：一句话描述方法功能
     * @param req
     * @param resp
     * @param transportTaskDto
     * @return
     * @exception	
     * @author zhuzhen
     * @date 2015年12月1日 下午1:52:27
     */
    @RequestMapping("/findTransportTaskDtoBy")
    @ResponseBody
    public List<TransportTaskAdminDto> findTransportTaskDtoBy(HttpServletRequest req, HttpServletResponse resp, 
        TransportTaskAdminDto transportTaskDto)
    {
        String projectIds = userService.getProjectIdsBySession(req.getSession());
        
      //用户是否拥有要查看的项目的权限
        if(!StringUtils.isEmpty(transportTaskDto.getProjectId()) && projectIds.contains(transportTaskDto.getProjectId()))
        {
            transportTaskDto.setProjectId(transportTaskDto.getProjectId());//以传过来的参数为准
        }
        else
        {
            transportTaskDto.setProjectId(projectIds);//查看该用户所拥有的所有项目
        }
        
        return transportTaskAdminService.getTransportTaskAdminDtoBy(transportTaskDto, null);
    }
    
    
    /**
     * 
     * findSessionInfoBySession：一句话描述方法功能
     * @param session
     * @return
     * @exception   
     * @author zhuzhen
     * @date 2015年12月8日 上午11:36:33
     */
    private SessionInfo findSessionInfoBySession(HttpSession session)
    {
        if(session == null)
        {
            return null;
        }
        return (SessionInfo)session.getAttribute(ConfigUtil.SESSIONINFONAME);
    }
    
    
    private String findSuppliersBySession(HttpSession session)
    {
        if(session == null)
        {
            return null;
        }
        
        SessionInfo sessionInfo = this.findSessionInfoBySession(session);
        if(sessionInfo == null)
        {
            return null;
        }
        
        
        SupplierAdminUsersDto supplierAdminUsersDto = new SupplierAdminUsersDto();
        supplierAdminUsersDto.setAdminUsersId(sessionInfo.getId());
        List<SupplierDto> suppliers = supplierAdminUsersAdminService.findSuppliersBy(supplierAdminUsersDto);

        if(CollectionUtils.isEmpty(suppliers))
        {
           return null;
        }
        
        StringBuilder supplierIds = new StringBuilder();
        for(int i=0; i<suppliers.size(); i++)
        {
            SupplierDto supplier = suppliers.get(i);
            if(supplier != null)
            {
                supplierIds.append(supplier.getId());
                if(i != (suppliers.size()-1))
                {
                    supplierIds.append(",");
                }
            }
            
        }
        
        return supplierIds.toString();
    }
}

