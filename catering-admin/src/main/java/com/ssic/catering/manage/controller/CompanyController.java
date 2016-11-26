/**
 * 
 */
package com.ssic.catering.manage.controller;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssic.catering.admin.pageModel.DataGrid;
import com.ssic.catering.admin.pageModel.Json;
import com.ssic.catering.admin.pageModel.PageHelper;
import com.ssic.catering.admin.service.ProjectService;
import com.ssic.catering.admin.service.UserServiceI;
import com.ssic.catering.base.dto.CompanyDto;
import com.ssic.catering.base.dto.PageHelperDto;
import com.ssic.catering.base.dto.SensitiveDto;
import com.ssic.catering.base.pojo.Company;
import com.ssic.catering.base.pojo.Sensitive;
import com.ssic.catering.base.service.ICafetoriumService;
import com.ssic.catering.base.service.ICompanyService;
import com.ssic.game.common.dao.ProjectDao;
import com.ssic.game.common.dto.ProjectDto;
import com.ssic.util.BeanUtils;

/**		
 * <p>Title: CompanyController </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年8月6日 下午5:15:32	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年8月6日 下午5:15:32</p>
 * <p>修改备注：</p>
 */
@Controller
@RequestMapping("/companyController")
public class CompanyController
{
    @Autowired
    private ICompanyService companyService;

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private ICafetoriumService cafetoriumService;

    @Autowired
    private ProjectService projectService;
    @Autowired
    private UserServiceI userServiceI;

    @RequestMapping("/findAll")
    @ResponseBody
    public List<CompanyDto> findAll(CompanyDto companyDto, HttpSession session)
    {
        //获取用户拥有的项目权限
        List<ProjectDto> listProject = userServiceI.getProjectBySession(session);
        if (!CollectionUtils.isEmpty(listProject))
        {
            if (listProject.size() > 1)
            {//超管
                companyDto.setProjId(null);
            }
            else
            {
                companyDto.setProjId(listProject.get(0).getId());
            }
        }
        return companyService.findAll(companyDto);
    }

    @RequestMapping("/manager")
    public String manager(HttpServletRequest request)
    {

        List<ProjectDto> listproject = projectService.findAll();
        request.setAttribute("listproject", listproject);
        return "carte/company/company";
    }

    /**
     * 
     * dataGrid：公司信息管理
     * @param companyDto
     * @param ph
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年10月21日 上午11:55:22
     */
    @RequestMapping("/dataGrid")
    @ResponseBody
    public DataGrid dataGrid(CompanyDto companyDto, PageHelper ph, HttpSession session)
    {
        //获取用户拥有的项目权限
        List<ProjectDto> listProject = userServiceI.getProjectBySession(session);
        if (!CollectionUtils.isEmpty(listProject))
        {
            if (listProject.size() > 1)
            {//超管
                companyDto.setProjId(null);
            }
            else
            {
                companyDto.setProjId(listProject.get(0).getId());
            }
        }
        DataGrid dataGrid = new DataGrid();
        PageHelperDto phDto = new PageHelperDto();
        Integer beginRow = (ph.getPage() - 1) * ph.getRows();

        BeanUtils.copyProperties(ph, phDto);
        phDto.setBeginRow(beginRow);
        List<CompanyDto> list = companyService.findCompanyAllBy(companyDto, phDto);
        for (int i = 0; i < list.size(); i++)
        {
            //根据projectId查询出projectName
            ProjectDto dto = projectDao.findById(list.get(i).getProjId());
            if (dto != null)
            {
                list.get(i).setProjName(dto.getProjName());
            }
            else
            {
                list.get(i).setProjName("");
            }
            //查询公司下面是否有食堂 flag 1:不允许删除公司 0:允许删除公司
            int flag = cafetoriumService.countCafetoriumByCompanyId(list.get(i).getId());
            if (flag > 0)
            {
                list.get(i).setFlag(1);
            }
            else
            {
                list.get(i).setFlag(0);
            }
        }
        int count = companyService.countCompanyBy();
        dataGrid.setRows(list);
        dataGrid.setTotal(Long.valueOf(count));
        return dataGrid;
    }

    /**
     * 
     * addPage：添加公司
     * @param request
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年10月22日 上午9:03:55
     */
    @RequestMapping("/addCompanyInfo")
    public String addCompany(HttpServletRequest request)
    {
        CompanyDto com = new CompanyDto();
        com.setId(UUID.randomUUID().toString());
        request.setAttribute("comuuid", com);
        List<ProjectDto> listproject = projectService.findAll();
        request.setAttribute("listproject", listproject);
        return "carte/company/companyAdd";
    }

    /**
     * 
     * addCompany：添加公司名称
     * @param companyDto
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年10月22日 上午9:36:10
     */
    @RequestMapping("/add")
    @ResponseBody
    public Json addCompany(CompanyDto companyDto, HttpSession session)
    {
        //获取用户拥有的项目权限
        List<ProjectDto> listProject = userServiceI.getProjectBySession(session);
        if (!CollectionUtils.isEmpty(listProject))
        {
            if (listProject.size() > 1)
            {//超管
                companyDto.setProjId(null);
            }
            else
            {
                companyDto.setProjId(listProject.get(0).getId());
            }
        }
        Json j = new Json();
        try
        {
            CompanyDto company = companyService.findByName(companyDto.getCompanyName());
            if (company != null)
            {
                j.setSuccess(false);
                j.setMsg("该公司名称已经存在，请勿重复添加！");
                return j;
            }
            companyService.insertCompanyInfo(companyDto);
            j.setSuccess(true);
            j.setMsg("添加公司成功！");
            j.setObj(companyDto);
        }
        catch (Exception e)
        {
            j.setMsg(e.getMessage());
        }
        return j;
    }

    /**
     * 
     * delete：删除公司
     * @param id
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年10月22日 上午9:55:02
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Json delete(String id)
    {
        Json j = new Json();
        if (!StringUtils.isEmpty(id))
        {
            Company company = companyService.getCompanyInfoById(id);
            CompanyDto dto = new CompanyDto();
            BeanUtils.copyProperties(company, dto);
            int flag = companyService.deleteCompanyInfo(dto);
            if (flag > 0)
            {
                j.setMsg("删除公司成功！");
                j.setSuccess(true);
            }
            else
            {
                j.setMsg("删除公司失败,请稍候再试!");
                j.setSuccess(false);
            }
        }
        else
        {
            j.setMsg("删除公司失败,请稍候再试!");
            j.setSuccess(false);
        }
        return j;
    }

    /**
     * 
     * editPage：跳转公司编辑页面
     * @param request
     * @param id
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年10月22日 上午10:21:46
     */
    @RequestMapping("/editPage")
    public String editPage(HttpServletRequest request, String id)
    {
        CompanyDto companyDto = new CompanyDto();
        Company company = companyService.getCompanyInfoById(id);
        BeanUtils.copyProperties(company, companyDto);
        //查询公司下面是否有食堂 flag 1:不允更改公司项目 0:允许更改公司项目
        int flag = cafetoriumService.countCafetoriumByCompanyId(id);
        if (flag > 0)
        {
            companyDto.setFlag(1);
        }
        else
        {
            companyDto.setFlag(0);
            List<ProjectDto> listproject = projectService.findAll();
            request.setAttribute("listproject", listproject);
        }
        request.setAttribute("companyDto", companyDto);
        return "carte/company/companyEdit";
    }

    /**
     * 
     * edit：修改公司信息
     * @param companyDto
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年10月22日 上午10:05:05
     */
    @RequestMapping("/edit")
    @ResponseBody
    public Json edit(CompanyDto companyDto)
    {
        Json j = new Json();
        Company company = companyService.getCompanyInfoById(companyDto.getId());
        company.setLastUpdateTime(new Date());
        company.setEmail(companyDto.getEmail());
        company.setMobileNo(companyDto.getMobileNo());
        company.setProjId(companyDto.getProjId());
        company.setCompanyName(companyDto.getCompanyName());

        int flag = companyService.updateCompanyInfo(company);
        if (flag > 0)
        {
            j.setMsg("编辑成功！");
            j.setSuccess(true);
        }
        else
        {
            j.setMsg("编辑失败！");
            j.setSuccess(false);
        }
        return j;
    }

}
