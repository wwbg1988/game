/**
 * 
 */
package com.ssic.catering.manage.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssic.catering.admin.dto.TImsUsersDto;
import com.ssic.catering.admin.pageModel.DataGrid;
import com.ssic.catering.admin.pageModel.Json;
import com.ssic.catering.admin.pageModel.PageHelper;
import com.ssic.catering.admin.service.ProjectService;
import com.ssic.catering.admin.service.UserServiceI;
import com.ssic.catering.base.dto.AddressDto;
import com.ssic.catering.base.dto.AddressUserDto;
import com.ssic.catering.base.dto.CafetoriumDto;
import com.ssic.catering.base.dto.CarteTypeDto;
import com.ssic.catering.base.dto.CompanyDto;
import com.ssic.catering.base.dto.ConfigScoreValveConfDto;
import com.ssic.catering.base.dto.PageHelperDto;
import com.ssic.catering.base.pojo.Address;
import com.ssic.catering.base.pojo.Carte;
import com.ssic.catering.base.service.IAddressService;
import com.ssic.catering.base.service.IAddressUserService;
import com.ssic.catering.base.service.ICafetoriumService;
import com.ssic.catering.base.service.ICarteService;
import com.ssic.catering.base.service.ICarteTypeService;
import com.ssic.catering.base.service.ICompanyService;
import com.ssic.catering.base.service.IConfigScoreValveConfService;
import com.ssic.game.common.dto.DataGridDto;
import com.ssic.game.common.dto.ImsUsersDto;
import com.ssic.game.common.dto.ProjectDto;
import com.ssic.game.common.service.IImsUserService;
import com.ssic.util.BeanUtils;
import com.ssic.util.constants.DataStatus;

/**		
 * <p>Title: CafrtoriumController </p>
 * <p>Description: 食堂</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 刘博	
 * @date 2015年8月6日 下午3:44:36	
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年8月6日 下午3:44:36</p>
 * <p>修改备注：</p>
 */
@Controller
@RequestMapping("/cafrtoriumController")
public class CafrtoriumController
{

    @Autowired
    private ICafetoriumService cafetoriumService;
    @Autowired
    private ICarteTypeService carteTypeService;
    @Autowired
    private ICarteService carteService;
    @Autowired
    private ICompanyService companyService;
    @Autowired
    private IAddressService addressService;
    @Autowired
    private IAddressUserService addressUserService;
    @Autowired
    private IImsUserService iImsUserService;
    @Autowired
    private ImsUserController imsUserController;
    @Autowired
    private IConfigScoreValveConfService confService;
    @Autowired
    private UserServiceI userServiceI;
    @Autowired
    private ProjectService projectService;

    protected static final Log logger = LogFactory.getLog(CafrtoriumController.class);

    /**
     * 跳转到食堂管理页面
     * 
     * @return
     */
    @RequestMapping("/manager")
    public String manager(HttpServletRequest request)
    {
        CompanyDto companyDto = new CompanyDto();
        List<CompanyDto> companyList = companyService.findAll(companyDto);
        request.setAttribute("companyList", companyList);
        List<AddressDto> addressList = addressService.findAll();
        request.setAttribute("addressList", addressList);
        return "carte/address/cafetorium";
    }

    /**
     * 获取食堂grid表单
     * 
     * @param fieldsDto
     * @return
     */
    @RequestMapping("/dataGrid")
    @ResponseBody
    public DataGrid dataGrid(CafetoriumDto cafetoriumDto, PageHelper ph, HttpSession session)
    {
        DataGrid dataGrid = new DataGrid();
        PageHelperDto phDto = new PageHelperDto();
        Integer beginRow = (ph.getPage() - 1) * ph.getRows();
        BeanUtils.copyProperties(ph, phDto);
        phDto.setBeginRow(beginRow);
        //返回对象;
        List<CafetoriumDto> listResult = new ArrayList<CafetoriumDto>();
        //返回数量;
        int countResult = 0;
        //获取用户拥有的项目权限
        List<ProjectDto> listProject = userServiceI.getProjectBySession(session);
        if (!CollectionUtils.isEmpty(listProject))
        { //循环遍历每一个项目，放入查找dto中对象
            for (ProjectDto projectDto : listProject)
            {
                cafetoriumDto.setProjId(projectDto.getId());
                List<CafetoriumDto> list = cafetoriumService.findALL(cafetoriumDto, phDto);
                int count = cafetoriumService.findCount(cafetoriumDto);
                listResult.addAll(list);
                //获取总的数量
                countResult += count;
            }
        }

        dataGrid.setRows(listResult);
        dataGrid.setTotal(Long.valueOf(countResult));
        return dataGrid;
    }

    /**
     * 添加食堂页面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/addPage")
    public String addPage(HttpServletRequest request, String addressId, HttpSession session)
    {
        CafetoriumDto cafetoriumDto = new CafetoriumDto();
        cafetoriumDto.setId(UUID.randomUUID().toString());
        AddressDto address = addressService.findById(addressId);
        addressService.findAddressById(cafetoriumDto.getAddressId());
        cafetoriumDto.setAddressName(address.getAddressName());
        cafetoriumDto.setAddressId(addressId);
        request.setAttribute("cafetoriumDto", cafetoriumDto);
        List<ProjectDto> listproject = projectService.findAll();
        request.setAttribute("listproject", listproject);
        //获取用户拥有的项目权限
        List<ProjectDto> listProject = userServiceI.getProjectBySession(session);
        if (!CollectionUtils.isEmpty(listProject))
        {
            if (listProject.size() > 1)
            {
                request.setAttribute("superadmin", 1);
            }
            else
            {
                request.setAttribute("projId", listProject.get(0).getId());
                request.setAttribute("superadmin", 0);
            }
        }

        return "carte/address/cafrtoriumAdd";
    }

    /**
     * 添加食堂
     * 
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public Json add(CafetoriumDto cafetoriumDto, HttpSession session)
    {
        Json j = new Json();
        TImsUsersDto user = (TImsUsersDto) session.getAttribute("user");
        try
        {

            if (!StringUtils.isEmpty(cafetoriumDto.getProjId()))
            {
                CafetoriumDto dto =
                    cafetoriumService.findByName(cafetoriumDto.getCafeName(), cafetoriumDto.getProjId());
                if (dto != null)
                {
                    j.setSuccess(false);
                    j.setMsg("该食堂名称已经存在，请勿重复添加！");
                    return j;
                }
                AddressUserDto addressUsers = new AddressUserDto();
                addressUsers.setProjId(cafetoriumDto.getProjId());
                List<AddressUserDto> addreUserLists = addressUserService.findAll(addressUsers);
                if (!CollectionUtils.isEmpty(addreUserLists))
                {
                    for (AddressUserDto adto : addreUserLists)
                    { //如果当前食堂的负责人已经是其他食堂负责人或市负责人，则提示错误消息
                        if (!StringUtils.isEmpty(cafetoriumDto.getUserId())
                            && cafetoriumDto.getUserId().equals(adto.getUserId()))
                        {
                            j.setSuccess(false);
                            j.setMsg("该食堂负责人已经是其他食堂负责人或市负责人,请勿重复添加负责人！");
                            return j;
                        }
                    }
                }

                String cafeCode = cafetoriumService.getNewCafeCode(cafetoriumDto.getProjId());
                cafetoriumDto.setCafeCode(cafeCode);
                cafetoriumDto.setCreateUserId(user.getId());
                //食堂新增
                cafetoriumService.add(cafetoriumDto);
                //组装食堂管理员对象数据
                AddressUserDto addressUserDto = new AddressUserDto();
                addressUserDto.setAddressId(cafetoriumDto.getAddressId());
                addressUserDto.setUserId(cafetoriumDto.getUserId());
                addressUserDto.setAddressType(4);
                addressUserDto.setCafeCode(cafeCode);
                addressUserDto.setProjId(cafetoriumDto.getProjId());
                //食堂管理员新增
                addressUserService.insert(addressUserDto);
                j.setSuccess(true);
                j.setMsg("添加食堂成功！");
                j.setObj(cafetoriumDto);
            }
            else
            {
                j.setSuccess(false);
                j.setMsg("添加食堂失败，项目id不存在或登录用户为超管！");
                j.setObj(cafetoriumDto);
            }
        }
        catch (Exception e)
        {
            // e.printStackTrace();
            j.setMsg(e.getMessage());
        }
        return j;
    }

    /**
     * 删除食堂
     * 
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Json delete(String id)
    {
        Json j = new Json();
        CafetoriumDto cafetoriumDto = cafetoriumService.findById(id);
        List<Carte> carteList = carteService.findAllByCafetorId(id);
        List<CarteTypeDto> typeList = carteTypeService.finByCafetoriumId(id);
        if (!CollectionUtils.isEmpty(typeList))
        {
            j.setMsg("该食堂下还有菜品类型,不能删除！");
            j.setSuccess(false);
            return j;
        }
        if (!CollectionUtils.isEmpty(carteList))
        {
            j.setMsg("该食堂下还有菜品,不能删除！");
            j.setSuccess(false);
            return j;
        }
        cafetoriumService.delete(cafetoriumDto);
        //删除食堂区域关系记录
        addressUserService.deleteByAddressIdAndCafeCode(cafetoriumDto.getAddressId(),
            cafetoriumDto.getCafeCode());
        j.setMsg("删除食堂成功！");
        j.setSuccess(true);
        return j;
    }

    /**
     * 食堂编辑页面
     * 
     * @return
     */
    @RequestMapping("/editPage")
    public String editPage(HttpServletRequest request, String id, HttpSession session)
    {

        CafetoriumDto r = cafetoriumService.findById(id);
        CompanyDto companyDto = new CompanyDto(); //获取用户拥有的项目权限
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
        List<CompanyDto> companyList = companyService.findAll(companyDto);
        AddressUserDto addressUser = new AddressUserDto();
        addressUser.setCafeCode(r.getCafeCode());
        addressUser.setAddressType(4);
        addressUser.setAddressId(r.getAddressId());
        List<AddressUserDto> addreUserList = addressUserService.findAll(addressUser);
        if (!CollectionUtils.isEmpty(addreUserList))
        {
            r.setUserId(addreUserList.get(0).getUserId());
        }
        Address address = addressService.findAddressById(r.getAddressId());
        //addressName
        r.setAddressId(address.getId());
        r.setAddressName(address.getAddressName());
        request.setAttribute("cafetoriumDto", r);
        request.setAttribute("companyList", companyList);

        List<ImsUsersDto> userList = imsUserController.findAll(session);
        request.setAttribute("userList", userList);
        return "carte/address/cafetoriumEdit";
    }

    /**
     * 编辑食堂
     * 
     * @param resource
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    public Json edit(CafetoriumDto cafetoriumDto, HttpSession session)
    {
        TImsUsersDto user = (TImsUsersDto) session.getAttribute("user");
        Json j = new Json();
        //540dfbc4-ddf1-4c89-b620-3c59bb7e3f1f
        CafetoriumDto cafeDto = cafetoriumService.findById(cafetoriumDto.getId());
        CafetoriumDto dtos =
            cafetoriumService.findByName(cafetoriumDto.getCafeName(), cafetoriumDto.getProjId());
        if (dtos != null && !dtos.getId().equals(cafetoriumDto.getId()))
        {
            j.setSuccess(false);
            j.setMsg("该食堂名称已经存在，请勿编辑重复食堂名称！");
            return j;
        }
        AddressUserDto addressUsers = new AddressUserDto();
        addressUsers.setProjId(cafetoriumDto.getProjId());
        List<AddressUserDto> addreUserLists = addressUserService.findAll(addressUsers);
        if (!CollectionUtils.isEmpty(addreUserLists))
        {
            for (AddressUserDto dto : addreUserLists)
            { //如果当前食堂的负责人已经是其他食堂负责人或市负责人，则提示错误消息
                if (!StringUtils.isEmpty(cafetoriumDto.getUserId())
                    && cafetoriumDto.getUserId().equals(dto.getUserId()))
                {
                    if (cafeDto != null && !cafeDto.getCafeCode().equals(dto.getCafeCode()))
                    {
                        j.setSuccess(false);
                        j.setMsg("该食堂负责人已经是其他食堂负责人或市负责人,请勿重复编辑负责人！");
                        return j;
                    }
                }
            }
        }
        CafetoriumDto r = cafetoriumService.findById(cafetoriumDto.getId());
        cafetoriumDto.setCafeCode(r.getCafeCode());
        cafetoriumDto.setCreateTime(r.getCreateTime());
        cafetoriumDto.setCreateUserId(user.getId());
        //更新食堂
        cafetoriumService.update(cafetoriumDto);
        //查询食堂负责人
        AddressUserDto addressUser = new AddressUserDto();
        addressUser.setCafeCode(r.getCafeCode());
        addressUser.setAddressType(4);
        addressUser.setAddressId(r.getAddressId());
        List<AddressUserDto> addreUserList = addressUserService.findAll(addressUser);
        //如果食堂负责人存在，则修改，负责新增
        if (!CollectionUtils.isEmpty(addreUserList))
        {
            AddressUserDto dto = addreUserList.get(0);
            //如果当前编辑页面的食堂负责人发生改变,则更新;
            if (!dto.getUserId().equals(cafetoriumDto.getUserId()))
            {
                dto.setUserId(cafetoriumDto.getUserId());
                addressUserService.update(dto);
            }
        }
        else
        {
            //组装食堂管理员对象数据
            AddressUserDto addressUserDto = new AddressUserDto();
            addressUserDto.setAddressId(r.getAddressId());
            addressUserDto.setProjId(cafetoriumDto.getProjId());
            addressUserDto.setUserId(cafetoriumDto.getUserId());
            addressUserDto.setAddressType(4);
            addressUserDto.setCafeCode(r.getCafeCode());
            //食堂管理员新增
            addressUserService.insert(addressUserDto);
        }
        j.setSuccess(true);
        j.setMsg("编辑成功！");
        return j;
    }

    /**
     * 跳转到资源管理页面
     * 
     * @return
     */
    @RequestMapping("/managers")
    public String managers(HttpServletRequest request, String id)
    {
        request.setAttribute("id", id);
        return "carte/address/cateAddress";
    }

    @RequestMapping("/cafeAddress")
    @ResponseBody
    public DataGridDto dataGrid(String addressId, PageHelper ph, HttpSession session)
    {
        DataGridDto dataGrid = new DataGridDto();
        //获取用户拥有的项目权限
        List<ProjectDto> listProject = userServiceI.getProjectBySession(session);
        Integer beginRow = (ph.getPage() - 1) * ph.getRows();
        PageHelperDto phDto = new PageHelperDto();
        phDto.setBeginRow(beginRow);
        BeanUtils.copyProperties(ph, phDto);
        phDto.setRows(30);

        if (!CollectionUtils.isEmpty(listProject))
        {
            if (listProject.size() > 1)
            {//超管
                dataGrid = addressUserService.findAllDataGrid(addressId, phDto, null);
            }
            else
            {
                dataGrid = addressUserService.findAllDataGrid(addressId, phDto, listProject.get(0).getId());
            }
        }
        return dataGrid;
    }

    /**
     * 
     * CarteConScoreValve：添加餐厅评分项预警阀值
     * @param request
     * @param caftrotiumId
     * @param caftrotiumName
     * @return
     * @exception   
     * @author pengcheng.yang
     * @date 2015年8月20日 上午9:44:35
     */
    @RequestMapping("/addCarteConScoreValve")
    public String CarteConScoreValve(HttpServletRequest request, String cafetoriumId, String caftrotiumName)
    {
        try
        {
            request.setCharacterEncoding("utf-8");
            request.setAttribute("cafetoriumId", cafetoriumId);
            request.setAttribute("caftrotiumName", caftrotiumName);
        }
        catch (UnsupportedEncodingException e)
        {
            //  对异常进行简要描述

        }

        return "carte/address/carteConScoreValveAdd";
    }

    /**
     * 
     * SaveCarteConScoreValve：保存添加的餐厅评分项阀值
     * @param dto
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年8月20日 上午10:05:44
     */
    @RequestMapping("/SaveCarteConScoreValve")
    @ResponseBody
    public Json SaveCarteConScoreValve(ConfigScoreValveConfDto dto)
    {
        Json j = new Json();
        try
        {
            if (!StringUtils.isEmpty(dto))
            {
                List<ConfigScoreValveConfDto> list = confService.queryConfigId(dto.getCafetoriumId());
                if (list.size() > 0)
                {
                    j.setSuccess(false);
                    j.setMsg("该餐厅评分项阀值已经添加！");
                    return j;
                }
                else
                {
                    dto.setId(UUID.randomUUID().toString());
                    dto.setCreateTime(new Date());
                    dto.setStat(DataStatus.ENABLED);
                    int flag = confService.insertConfScoreValue(dto);
                    if (flag == 1)
                    {
                        j.setSuccess(true);
                        j.setMsg("添加餐厅评分项阀值成功！");
                    }
                    else
                    {
                        j.setSuccess(false);
                        j.setMsg("添加餐厅评分项阀值失败！");
                        return j;
                    }
                }
            }
            else
            {
                j.setSuccess(false);
                j.setMsg("请输入正确的餐厅评分项阀值！");
                return j;
            }
        }
        catch (Exception e)
        {
            j.setMsg(e.getMessage());
        }

        return j;
    }

    @RequestMapping("/findAll")
    @ResponseBody
    public List<CafetoriumDto> findAll(HttpSession session)
    {
        if (session == null)
        {
            logger.error("session不能为空");

        }

        List<ProjectDto> pjds = userServiceI.getProjectBySession(session);
        if (CollectionUtils.isEmpty(pjds))
        {
            logger.error("该用户没有对应的projectId");

        }
        List<CafetoriumDto> cafeList = new ArrayList<CafetoriumDto>();
        if (pjds.size() > 1)
        {
            cafeList = cafetoriumService.findCafetoriumAll();
        }
        else if (pjds.size() == 1)
        {
            CafetoriumDto cafetoriumDto = new CafetoriumDto();
            cafetoriumDto.setProjId(pjds.get(0).getId());
            cafeList = cafetoriumService.findALL(cafetoriumDto, null);
        }
        return cafeList;
    }
    
    /**
     * 
     * findCafetoriumsBy：根据条件查询餐厅列表
     * @param req
     * @param resp
     * @param projectId
     * @return
     * @exception	
     * @author zhuzhen
     * @date 2015年11月24日 上午9:21:08
     */
    @RequestMapping("/findCafetoriumsBy")
    @ResponseBody
    public List<CafetoriumDto> findCafetoriumsBy(HttpServletRequest req, HttpServletResponse resp, CafetoriumDto cafetoriumDto)
    {
       return cafetoriumService.findCafetoriumsBy(cafetoriumDto);
    }

}
