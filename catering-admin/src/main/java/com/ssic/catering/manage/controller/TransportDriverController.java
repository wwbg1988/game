package com.ssic.catering.manage.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssic.catering.admin.pageModel.DataGrid;
import com.ssic.catering.admin.pageModel.Json;
import com.ssic.catering.admin.pageModel.SessionInfo;
import com.ssic.catering.admin.service.ISupplierAdminUsersAdminService;
import com.ssic.catering.admin.service.UserServiceI;
import com.ssic.catering.admin.util.ConfigUtil;
import com.ssic.catering.base.service.IAdminUsersService;
import com.ssic.catering.base.service.ISupplierAdminUsersService;
import com.ssic.catering.base.service.ISupplierService;
import com.ssic.catering.common.util.MD5Util;
import com.ssic.catering.lbs.dto.SupplierAdminUsersDto;
import com.ssic.catering.lbs.dto.SupplierDto;
import com.ssic.catering.lbs.dto.TransportDriverDto;
import com.ssic.catering.lbs.service.TransportDriverService;
import com.ssic.game.common.dto.PageHelperDto;
import com.ssic.game.common.dto.ProjectDto;
import com.ssic.game.common.service.IParamConfigService;
import com.ssic.util.StringUtils;

/**
 * 		
 * <p>Title: TransportDriverController </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author zhuzhen	
 * @date 2015年11月26日 下午6:51:27	
 * @version 1.0
 * <p>修改人：zhuzhen</p>
 * <p>修改时间：2015年11月26日 下午6:51:27</p>
 * <p>修改备注：</p>
 */
@Controller
@RequestMapping("/transportDriverController")
public class TransportDriverController
{
    @Autowired
    private TransportDriverService transportDriverService;
    
    @Autowired
    private UserServiceI userService;
    
    @Autowired
    private ISupplierAdminUsersService supplierAdminUsersService;
    
    /*//团餐用户角色
    @Autowired
    private IAdminRoleService adminRoleService;*/
    @Autowired
    private ISupplierAdminUsersAdminService supplierAdminUsersAdminService;
    
    @Autowired
    private IAdminUsersService adminUsersService;
    
    @Autowired
    private IParamConfigService paramConfigService;
    
    @Autowired
    private ISupplierService supplierService;
    
    @RequestMapping("/dataGrid")
    @ResponseBody
    public DataGrid dataGrid(HttpServletRequest req, HttpServletResponse resp,
        TransportDriverDto transportDriverDto,PageHelperDto pageHelperDto)
    {
        DataGrid response = new DataGrid();
        
        String supplierIds = this.findSuppliersBySession(req.getSession());
        
        if(StringUtils.isEmpty(supplierIds))
        {
            response.setTotal(0L);
            return response;
        }
        
        if(StringUtils.isEmpty(transportDriverDto.getAdminUserId()))
        {
            transportDriverDto.setAdminUserId(supplierIds.toString());
        }
        else if(!supplierIds.toString().contains(transportDriverDto.getAdminUserId()))
        {
            response.setTotal(0L);
            return response;
        }
        
      
        long count = transportDriverService.findTransportDriverDtoCountBy(transportDriverDto);
        if(count > 0)
        {
            List<TransportDriverDto> rows = transportDriverService.findTransportDriverDtosBy(transportDriverDto, pageHelperDto);
            if(!CollectionUtils.isEmpty(rows))
            {
                response.setTotal(count);
                response.setRows(rows);
                return response;
            }
        }
        
        
        response.setTotal(0L);
        return response;
        
       
    }
    
    /**
     * 
     * manager：一句话描述方法功能
     * @param req
     * @param resp
     * @param transportDriverDto
     * @param pageHelperDto
     * @return
     * @exception	
     * @author zhuzhen
     * @date 2015年11月26日 下午8:30:06
     */
    @RequestMapping("/manager")
    public String manager(HttpServletRequest req, HttpServletResponse resp,
        TransportDriverDto transportDriverDto,PageHelperDto pageHelperDto)
    {
        List<ProjectDto> projects = userService.getProjectBySession(req.getSession());
        if(!CollectionUtils.isEmpty(projects))
        {
            req.setAttribute("projects", projects);
        }
        
        return "carte/lbs/driver/transportDriver";
    }
    
    /**
     * 
     * showTransportDiverDtoAddPage：一句话描述方法功能
     * @param req
     * @param resp
     * @param transportDriverDto
     * @param pageHelperDto
     * @return
     * @exception	
     * @author zhuzhen
     * @date 2015年11月26日 下午8:18:04
     */
    @RequestMapping("/addPage")
    public String showTransportDiverDtoAddPage(HttpServletRequest req, HttpServletResponse resp,
        TransportDriverDto transportDriverDto,PageHelperDto pageHelperDto)
    {
        List<ProjectDto> projects = userService.getProjectBySession(req.getSession());
        if(!CollectionUtils.isEmpty(projects))
        {
            req.setAttribute("projects", projects);
        }
        return "carte/lbs/driver/transportDriverAdd";
    }
    
    
    @RequestMapping("/add")
    @ResponseBody
    public Json addTransportDiverDto(HttpServletRequest req, HttpServletResponse resp,
        TransportDriverDto transportDriverDto)
    {
       Json response = new Json();
       
       if(StringUtils.isEmpty(transportDriverDto.getAccount()))
       {
           response.setSuccess(false);
           response.setMsg("账号不能为空");
           return response;
       }
       
       if(StringUtils.isEmpty(transportDriverDto.getAdminUserId()))
       {
           response.setSuccess(false);
           response.setMsg("供应商不能为空");
           return response;
       }
       
       
       if(StringUtils.isEmpty(transportDriverDto.getName()))
       {
           response.setSuccess(false);
           response.setMsg("姓名不能为空");
           return response;
       }
       
       if(StringUtils.isEmpty(transportDriverDto.getPassword()))
       {
           response.setSuccess(false);
           response.setMsg("密码不能为空");
           return response;
       }
       
       if(StringUtils.isEmpty(transportDriverDto.getPhone()))
       {
           response.setSuccess(false);
           response.setMsg("手机号码不能为空");
           return response;
       }
       
       TransportDriverDto result = transportDriverService.findTransportDiverByAccount(transportDriverDto.getAccount());
       if(result != null)
       {
           response.setSuccess(false);
           response.setMsg("账号已存在");
           return response;
       }
       
       TransportDriverDto newRecord = new TransportDriverDto();
       newRecord.setAccount(transportDriverDto.getAccount());//account
       newRecord.setAdminUserId(transportDriverDto.getAdminUserId());//供应商
       newRecord.setName(transportDriverDto.getName());//名称
       newRecord.setNickName(transportDriverDto.getNickName());//昵称
       newRecord.setPassword(MD5Util.md5(transportDriverDto.getPassword()));//密码
       newRecord.setPhone(transportDriverDto.getPhone());//手机号码
       newRecord.setState(0);//表示空闲
       
       int count = transportDriverService.addTransportDiver(newRecord);
       if(count <= 0)
       {
           response.setSuccess(false);
           response.setMsg("添加失败");
           return response;
       }
       
       response.setSuccess(true);
       response.setMsg("添加成功");
       return response;
    }
    
    /**
     * 
     * showTransportDiverDtoEditPage：一句话描述方法功能
     * @param req
     * @param resp
     * @param transportDriverDto
     * @param pageHelperDto
     * @return
     * @exception	
     * @author zhuzhen
     * @date 2015年11月30日 下午1:39:05
     */
    @RequestMapping("/editPage")
    public String showTransportDiverDtoEditPage(HttpServletRequest req, HttpServletResponse resp,
        TransportDriverDto transportDriverDto,PageHelperDto pageHelperDto)
    {
        if(StringUtils.isEmpty(transportDriverDto.getId()))
        {
            req.setAttribute("errorMsg", "没有选中配送人");
            return "error/errorMsg";//跳转到错误页面
        }
        
        
        List<TransportDriverDto> drivers = transportDriverService.findTransportDriverDtosBy(transportDriverDto, null);
        if(!CollectionUtils.isEmpty(drivers))
        {
            List<ProjectDto> projects = userService.getProjectBySession(req.getSession());
            if(!CollectionUtils.isEmpty(projects))
            {
                
                TransportDriverDto driver = drivers.get(0);
                if(driver != null)
                {
                    SupplierDto supplier = supplierService.findById(driver.getAdminUserId());
                    if(supplier != null)
                    {
                        req.setAttribute("projects", projects);
                        req.setAttribute("projectId", supplier.getProjectId());
                        req.setAttribute("driver", driver);
                        return "carte/lbs/driver/transportDriverEdit";

                    }
                    
                    req.setAttribute("errorMsg", "该供应商不存在"+driver.getAdminUserId());
                    return "error/errorMsg";
                    
                }
                
                req.setAttribute("errorMsg", "该配送人不存在"+transportDriverDto.getId());
                return "error/errorMsg";
                
            }
            
            req.setAttribute("errorMsg", "该用户没有所属项目");
            return "error/errorMsg";
        }
        
        req.setAttribute("errorMsg", "该配送人不存在");
        return "error/errorMsg";
        
    }
    
    /**
     * 
     * updateTransportDiverDto：一句话描述方法功能
     * @param req
     * @param resp
     * @param transportDriverDto
     * @param pageHelperDto
     * @return
     * @exception	
     * @author zhuzhen
     * @date 2015年11月30日 下午1:35:21
     */
    @RequestMapping("/edit")
    @ResponseBody
    public Json updateTransportDiverDto(HttpServletRequest req, HttpServletResponse resp,
        TransportDriverDto transportDriverDto)
    {
       Json response = new Json();
       
       if(StringUtils.isEmpty(transportDriverDto.getId()))
       {
           response.setSuccess(false);
           response.setMsg("未选中配送人");
           return response;
       }
       
       if(StringUtils.isEmpty(transportDriverDto.getAdminUserId()))
       {
           response.setSuccess(false);
           response.setMsg("供应商不能为空");
           return response;
       }
       
       
       if(StringUtils.isEmpty(transportDriverDto.getName()))
       {
           response.setSuccess(false);
           response.setMsg("姓名不能为空");
           return response;
       }
       
       
       if(StringUtils.isEmpty(transportDriverDto.getPhone()))
       {
           response.setSuccess(false);
           response.setMsg("手机号码不能为空");
           return response;
       }
       
       if(transportDriverDto.getState() == null)
       {
           response.setSuccess(false);
           response.setMsg("状态不能为空");
           return response;
       }
       
       TransportDriverDto result = transportDriverService.findTransportDiverById(transportDriverDto.getId());
       if(result == null)
       {
           response.setSuccess(false);
           response.setMsg("配送人不存在");
           return response;
       }
       
       TransportDriverDto newRecord = new TransportDriverDto();
       newRecord.setId(transportDriverDto.getId());//id
       newRecord.setAdminUserId(transportDriverDto.getAdminUserId());//供应商
       newRecord.setName(transportDriverDto.getName());//姓名
       newRecord.setNickName(transportDriverDto.getNickName());//昵称
       newRecord.setPhone(transportDriverDto.getPhone());//手机号码
       newRecord.setState(transportDriverDto.getState());//状态
       
       int count = transportDriverService.updateTransportDiver(newRecord);
       if(count <= 0)
       {
           response.setSuccess(false);
           response.setMsg("添加失败");
           return response;
       }
       
       response.setSuccess(true);
       response.setMsg("添加成功");
       return response;
    }
    
    
    /**
     * 
     * showTransportDiverDtoEditPasswordPage：一句话描述方法功能
     * @param req
     * @param resp
     * @param transportDriverDto
     * @param pageHelperDto
     * @return
     * @exception	
     * @author zhuzhen
     * @date 2015年11月30日 下午4:18:23
     */
    @RequestMapping("/editPasswordPage")
    public String showTransportDiverDtoEditPasswordPage(HttpServletRequest req, HttpServletResponse resp,
        TransportDriverDto transportDriverDto)
    {
        if(StringUtils.isEmpty(transportDriverDto.getId()))
        {
            req.setAttribute("errorMsg", "没有选中配送人");
            return "error/errorMsg";//跳转到错误页面
        }
        
        
        List<TransportDriverDto> drivers = transportDriverService.findTransportDriverDtosBy(transportDriverDto, null);
        if(!CollectionUtils.isEmpty(drivers))
        {
            List<ProjectDto> projects = userService.getProjectBySession(req.getSession());
            if(!CollectionUtils.isEmpty(projects))
            {
                req.setAttribute("projects", projects);
                req.setAttribute("driver", drivers.get(0));
                
                return "carte/lbs/driver/transportDriverEditPassword";
            }
            
            req.setAttribute("errorMsg", "该用户没有所属项目");
            return "error/errorMsg";
        }
        
        req.setAttribute("errorMsg", "该配送人不存在");
        return "error/errorMsg";
        
    }
    
    /**
     * 
     * updateDrviePassword：一句话描述方法功能
     * @param req
     * @param resp
     * @param transportDriverDto
     * @param pageHelperDto
     * @return
     * @exception	
     * @author zhuzhen
     * @date 2015年11月30日 下午2:03:07
     */
    @RequestMapping("/editPassword")
    @ResponseBody
    public Json updateDrviePassword(HttpServletRequest req, HttpServletResponse resp,
        TransportDriverDto transportDriverDto, String password, String newPassword)
    {
       Json response = new Json();
       
       if(StringUtils.isEmpty(transportDriverDto.getId()))
       {
           response.setSuccess(false);
           response.setMsg("未选中配送人");
           return response;
       }
       
       if(StringUtils.isEmpty(password))
       {
           response.setSuccess(false);
           response.setMsg("原密码不能为空");
           return response;
       }
       
       if(StringUtils.isEmpty(newPassword))
       {
           response.setSuccess(false);
           response.setMsg("新密码不能为空");
           return response;
       }
       
       
       TransportDriverDto driver = transportDriverService.findTransportDiverById(transportDriverDto.getId());
       if(driver == null)
       {
           response.setSuccess(false);
           response.setMsg("配送人不存在");
           return response;
       }
       
       //密码是否正确
       if(!MD5Util.md5(password).equals(driver.getPassword()))
       {
           response.setSuccess(false);
           response.setMsg("原密码不正确");
           return response;
       }
       
       //新密码不能与原密码相同
       if(MD5Util.md5(newPassword).equals(driver.getPassword()))
       {
           response.setSuccess(false);
           response.setMsg("新密码不能与原密码相同");
           return response;
       }
       
       TransportDriverDto newRecord = new TransportDriverDto();
       newRecord.setId(transportDriverDto.getId());
       newRecord.setPassword(MD5Util.md5(newPassword));
       
       int count = transportDriverService.updateTransportDiver(newRecord);
       if(count <= 0)
       {
           response.setSuccess(false);
           response.setMsg("修改失败");
           return response;
       }
       
       response.setSuccess(true);
       response.setMsg("修改成功");
       return response;
    }
    
    /**
     * 
     * deleteTransportDiverDto：一句话描述方法功能
     * @param req
     * @param resp
     * @param transportDriverDto
     * @param pageHelperDto
     * @return
     * @exception	
     * @author zhuzhen
     * @date 2015年11月30日 下午2:02:13
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Json deleteTransportDiverDto(HttpServletRequest req, HttpServletResponse resp,
        TransportDriverDto transportDriverDto)
    {
       Json response = new Json();
       
       if(StringUtils.isEmpty(transportDriverDto.getId()))
       {
           response.setSuccess(false);
           response.setMsg("未选中配送人");
           return response;
       }
       
       TransportDriverDto driver = transportDriverService.findTransportDiverById(transportDriverDto.getId());
       if(driver == null)
       {
           response.setSuccess(false);
           response.setMsg("配送人不存在");
           return response;
       }
       
       TransportDriverDto newRecord = new TransportDriverDto();
       newRecord.setId(transportDriverDto.getId());
       newRecord.setStat(0);//设置为无效
       
       int count = transportDriverService.updateTransportDiver(newRecord);
       if(count <= 0)
       {
           response.setSuccess(false);
           response.setMsg("删除失败");
           return response;
       }
       
       response.setSuccess(true);
       response.setMsg("删除成功");
       return response;
    }
    
    /**
     * 
     * findTransportDriverBy：一句话描述方法功能
     * @param req
     * @param resp
     * @param transportDriverDto
     * @param pageHelperDto
     * @return
     * @exception	
     * @author zhuzhen
     * @date 2015年11月27日 下午1:07:26
     */
    @ResponseBody
    @RequestMapping("/findTransportDriverBySupplyId")
    public List<TransportDriverDto> findTransportDriverBySupplyId(HttpServletRequest req, HttpServletResponse resp,
        String adminUserId)
    {
       if(StringUtils.isEmpty(adminUserId))
       {
           return null;
       }
       
       TransportDriverDto param = new TransportDriverDto();
       param.setAdminUserId(adminUserId);
       
       List<TransportDriverDto> data = transportDriverService.findTransportDriverDtosBy(param, null);
       if(!CollectionUtils.isEmpty(data))
       {
           return data;
       }
       
       return null;
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

