package com.ssic.catering.manage.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
import com.ssic.catering.admin.pageModel.PageHelper;
import com.ssic.catering.admin.pageModel.SessionInfo;
import com.ssic.catering.admin.service.ISupplierAdminUsersAdminService;
import com.ssic.catering.admin.service.UserServiceI;
import com.ssic.catering.admin.util.ConfigUtil;
import com.ssic.catering.base.service.ISupplierAdminUsersService;
import com.ssic.catering.base.service.ISupplierService;
import com.ssic.catering.lbs.dto.SupplierAdminUsersDto;
import com.ssic.catering.lbs.dto.SupplierDto;
import com.ssic.game.common.dao.ProjectDao;
import com.ssic.game.common.dto.PageHelperDto;
import com.ssic.game.common.dto.ProjectDto;
import com.ssic.game.common.pageModel.Tree;
import com.ssic.util.BeanUtils;

/**
 * 		
 * <p>Title: SupplierController </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author zhuzhen	
 * @date 2015年12月1日 下午1:08:40	
 * @version 1.0
 * <p>修改人：zhuzhen</p>
 * <p>修改时间：2015年12月1日 下午1:08:40</p>
 * <p>修改备注：</p>
 */
@Controller
@RequestMapping("/supplierController")
public class SupplierController
{
    @Autowired
    private ISupplierService supplierService;

    @Autowired
    private UserServiceI userServiceI;

    @Autowired
    private ProjectDao projectDao;
    
    @Autowired
    private ISupplierAdminUsersService supplierAdminUsersService;
    
    @Autowired
    private ISupplierAdminUsersAdminService supplierAdminUsersAdminService;

    /**
     * 供应商管理页面
     * 
     * @return
     */
    @RequestMapping("/manager")
    public String manager(HttpServletRequest request)
    {

        return "carte/lbs/supplier/supplier";
    }

    /**
     * 
     * dataGrid：供应商信息管理
     * @param transportDestDto
     * @param ph
     * @return
     * @exception   
     * @author 刘博
     * @date 2015年10月21日 上午11:55:22
     */
    @RequestMapping("/dataGrid")
    @ResponseBody
    public DataGrid dataGrid(SupplierDto supplierDto, PageHelper ph, HttpSession session)
    {
        //获取用户拥有的项目权限
        List<ProjectDto> listProject = userServiceI.getProjectBySession(session);
        if (!CollectionUtils.isEmpty(listProject))
        {
            if (listProject.size() > 1)
            {//超管
                supplierDto.setProjectId(null);
            }
            else
            {
                supplierDto.setProjectId(listProject.get(0).getId());
            }
        }
        DataGrid dataGrid = new DataGrid();
        PageHelperDto phDto = new PageHelperDto();
        Integer beginRow = (ph.getPage() - 1) * ph.getRows();

        BeanUtils.copyProperties(ph, phDto);
        phDto.setBeginRow(beginRow);
        List<SupplierDto> list = supplierService.findALL(supplierDto, phDto);
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
        int count = supplierService.findCount(supplierDto);
        dataGrid.setRows(list);
        dataGrid.setTotal(Long.valueOf(count));
        return dataGrid;
    }

    /**
     * 跳转到添加供应商页面
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
            SupplierDto supplierDto = new SupplierDto();
            //获取用户拥有的项目权限
            List<ProjectDto> listProject = userServiceI.getProjectBySession(session);
            if (!CollectionUtils.isEmpty(listProject))
            {
                if (listProject.size() > 1)
                {//超管
                    supplierDto.setProjectId(null);
                }
                else
                {
                    supplierDto.setProjectId(listProject.get(0).getId());
                }
            }
            supplierDto.setId(UUID.randomUUID().toString());
            request.setCharacterEncoding("utf-8");
            request.setAttribute("supplierDto", supplierDto);
        }
        catch (UnsupportedEncodingException e)
        {
            //  对异常进行简要描述
        }
        return "carte/lbs/supplier/supplierAdd";
    }

    /**
     * 添加供应商
     * 
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public Json add(SupplierDto supplierDto, HttpSession session)
    {
        Json j = new Json();

        try
        {
            if (supplierDto.getLongitude() == null || supplierDto.getLongitude() == "")
            {
                j.setMsg("经度不能为空!");
                j.setSuccess(false);
                return j;
            }
            if (supplierDto.getLatitude() == null || supplierDto.getLatitude() == "")
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
                    supplierDto.setProjectId(null);
                }
                else
                {
                    supplierDto.setProjectId(listProject.get(0).getId());
                }
            }

            SupplierDto dto = supplierService.findByName(supplierDto.getSupplierName());

            if (dto != null && !StringUtils.isEmpty(dto.getSupplierName()))
            {
                j.setSuccess(false);
                j.setMsg("该供应商名称已经存在,请勿重复添加！");
                return j;
            }
            List<SupplierDto> list =
                supplierService.findByLongitudeAndLatitude(supplierDto.getLongitude(),
                    supplierDto.getLatitude());

            if (!CollectionUtils.isEmpty(list))
            {
                j.setSuccess(false);
                j.setMsg("该供应商地址已经存在,请勿重复添加！");
                return j;
            }
            supplierService.add(supplierDto);
            j.setSuccess(true);
            j.setMsg("添加供应商成功！");
            j.setObj(supplierDto);
        }
        catch (Exception e)
        {
            // e.printStackTrace();
            j.setMsg(e.getMessage());
        }
        return j;
    }

    /**
     * 编辑供应商页面
     * 
     * @return
     */
    @RequestMapping("/editPage")
    public String editPage(HttpServletRequest request, String id)
    {
        SupplierDto r = supplierService.findById(id);
        request.setAttribute("supplierDto", r);
        return "carte/lbs/supplier/supplierEdit";
    }

    /**
     * 编辑供应商
     * 
     * @param resource
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    public Json edit(SupplierDto supplierDto)
    {
        Json j = new Json();
        SupplierDto r = supplierService.findById(supplierDto.getId());
        r.setAddress(supplierDto.getAddress());
        r.setLatitude(supplierDto.getLatitude());
        r.setLongitude(supplierDto.getLongitude());
        r.setSupplierName(supplierDto.getSupplierName());
        r.setLastUpdateTime(new Date());
        supplierService.update(r);
        j.setSuccess(true);
        j.setMsg("编辑成功！");
        return j;
    }

    /**
     * 删除供应商
     * 
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Json delete(String id)
    {
        Json j = new Json();
        SupplierDto r = supplierService.findById(id);
        supplierService.delete(r);
        j.setMsg("删除供应商成功！");
        j.setSuccess(true);
        return j;
    }
    
    
    /**
     * 
     * findSupplierBy：一句话描述方法功能
     * @param req
     * @param resp
     * @param supplierDto
     * @return
     * @exception	
     * @author zhuzhen
     * @date 2015年12月3日 下午5:18:56
     */
    @RequestMapping("/findSuppliersBy")
    @ResponseBody
    public List<SupplierDto> findSupplierBy(HttpServletRequest req, HttpServletResponse resp,
        SupplierDto supplierDto)
    {
        String supplierIds = this.findSuppliersBySession(req.getSession());
        
        if(StringUtils.isEmpty(supplierIds))
        {
            return null;
        }
        
        if(StringUtils.isEmpty(supplierDto.getId()))
        {
            supplierDto.setId(supplierIds);
        }
        else if(!supplierIds.contains(supplierDto.getId()))//参数中id不包含在该用户的权限中
        {
            return null;
        }
        return supplierService.findBy(supplierDto);
    }
    
    /**
     * 
     * bindingSupplierPage：一句话描述方法功能
     * @param req
     * @param resp
     * @param supplierAdminUsersDto
     * @return
     * @exception	
     * @author zhuzhen
     * @date 2015年12月4日 上午11:43:27
     */
    @RequestMapping("/bindingSupplierPage")
    public String bindingSupplierPage(HttpServletRequest req, HttpServletResponse resp,
        SupplierAdminUsersDto supplierAdminUsersDto)
    {
        if(StringUtils.isEmpty(supplierAdminUsersDto.getAdminUsersId()))
        {
            req.setAttribute("errorMsg", "后台账号不能为空");
            return "error/errorMsg";//跳转到错误页面
        }
        
        SupplierAdminUsersDto param = new SupplierAdminUsersDto();
        param.setAdminUsersId(supplierAdminUsersDto.getAdminUsersId());
        List<SupplierAdminUsersDto> checkedList = supplierAdminUsersService.findSupplierAdminUsersBy(param);
        
        if(!CollectionUtils.isEmpty(checkedList))
        {
            StringBuilder checkedSupplierIds = new StringBuilder();
            for(int i=0; i<checkedList.size();i++)
            {
                checkedSupplierIds.append(checkedList.get(i).getSupplierId());

                if(i != checkedList.size()-1)
                {
                    checkedSupplierIds.append(",");
                }
            }
            req.setAttribute("checkedSupplierIds", checkedSupplierIds);
        }
        
        req.setAttribute("adminUsersId", supplierAdminUsersDto.getAdminUsersId());
        return "carte/lbs/supplier/bindingSupplier";
    }
    
    /**
     * 
     * bindingSupplier：一句话描述方法功能
     * @param req
     * @param resp
     * @param supplierAdminUsersDto
     * @return
     * @exception	
     * @author zhuzhen
     * @date 2015年12月4日 下午1:41:03
     */
    @RequestMapping("/bindingSupplier")
    @ResponseBody
    public Json bindingSupplier(HttpServletRequest req, HttpServletResponse resp,
        SupplierAdminUsersDto supplierAdminUsersDto)
    {
        Json response = new Json();
        //后台账号
        if(StringUtils.isEmpty(supplierAdminUsersDto.getAdminUsersId()))
        {
            response.setSuccess(false);
            response.setMsg("后台账号不能为空");
            
            return response;
        }
        
        //供应商
        if(StringUtils.isEmpty(supplierAdminUsersDto.getSupplierId()))
        {
            response.setSuccess(false);
            response.setMsg("供应商不能为空");
            
            return response;
        }
        
        if(supplierAdminUsersService.bindingSuppliers(supplierAdminUsersDto) <0)
        {
            response.setSuccess(false);
            response.setMsg("绑定失败");
            return response;
        }
        else
        {
            response.setSuccess(true);
            response.setMsg("绑定成功");
            return response;
        }
        
    }
    
   /**
    * 
    * findSupplierBySession：一句话描述方法功能
    * @param req
    * @param resp
    * @return
    * @exception	
    * @author zhuzhen
    * @date 2015年12月4日 下午1:17:24
    */
    @RequestMapping("/findSupplierTree")
    @ResponseBody
    public List<Tree> findSupplierBySession(HttpServletRequest req, HttpServletResponse resp)
    {
        List<ProjectDto> projects = userServiceI.getProjectBySession(req.getSession());
        if(CollectionUtils.isEmpty(projects))
        {
            return null;
        }
        
        List<SupplierDto> datas = supplierService.findBy(null);
        
        if(CollectionUtils.isEmpty(datas))
        {
            return null;
        }
        
        List<Tree> tree = new ArrayList<>();
        for(ProjectDto project:projects)
        {
            Tree first = new Tree();
            first.setId(project.getId());
            first.setText(project.getProjName());
            first.setChildren(new ArrayList<Tree>());
            for(SupplierDto supplier:datas)
            {
                if(!StringUtils.isEmpty(project.getId()) && project.getId().equals(supplier.getProjectId()))
                {
                    Tree second = new Tree();
                    second.setId(supplier.getId());
                    second.setText(supplier.getSupplierName());
                    first.getChildren().add(second);
                }
            }
            
            tree.add(first);
        }
        
        return tree; 
    }
    
    @RequestMapping("/bindingSupplier/adminUsers")
    public String adminUsers(HttpServletRequest req, HttpServletResponse resp,
        SupplierAdminUsersDto supplierAdminUsersDto)
    {
        List<ProjectDto> projects = userServiceI.getProjectBySession(req.getSession());
        if(!CollectionUtils.isEmpty(projects))
        {
            req.setAttribute("projects", projects);
        }
        return "carte/lbs/supplier/adminUsers";
    }
    
    
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
