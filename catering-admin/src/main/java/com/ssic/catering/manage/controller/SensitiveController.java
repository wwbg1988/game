/**
 * 
 */
package com.ssic.catering.manage.controller;

import java.util.ArrayList;
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

import com.ssic.catering.admin.dto.TImsUsersDto;
import com.ssic.catering.admin.pageModel.DataGrid;
import com.ssic.catering.admin.pageModel.Json;
import com.ssic.catering.admin.pageModel.PageHelper;
import com.ssic.catering.admin.service.ProjectService;
import com.ssic.catering.admin.service.UserServiceI;
import com.ssic.catering.base.dto.CafetoriumDto;
import com.ssic.catering.base.dto.PageHelperDto;
import com.ssic.catering.base.dto.SensitiveDto;
import com.ssic.catering.base.dto.SensitiveValveConfDto;
import com.ssic.catering.base.pojo.Sensitive;
import com.ssic.catering.base.pojo.SensitiveValveConf;
import com.ssic.catering.base.service.ICafetoriumService;
import com.ssic.catering.base.service.ISensitiveService;
import com.ssic.catering.base.service.ISensitiveValveConfService;
import com.ssic.game.common.dto.ProjectDto;
import com.ssic.util.BeanUtils;

/**		
 * <p>Title: SensitiveController </p>
 * <p>Description: 敏感词controller</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 刘博	
 * @date 2015年8月14日 上午10:08:19	
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年8月14日 上午10:08:19</p>
 * <p>修改备注：</p>
 */
@Controller
@RequestMapping("/sensitiveController")
public class SensitiveController
{
    @Autowired
    private ISensitiveService sensitiveService;
    
    @Autowired
    private ISensitiveValveConfService confService;
    
    @Autowired
    private ICafetoriumService cafeService;
    
    @Autowired
    private ProjectService projectService;
    
    @Autowired
    private UserServiceI userServiceI;
    
    @Autowired
    private ICafetoriumService cafetoriumService;

    /**
     * 敏感词管理页面
     * 
     * @return
     */
    @RequestMapping("/manager")
    public String manager(HttpServletRequest request,HttpSession session)
    {
        List<CafetoriumDto> cafeList = getCafetoriumList(session);
        request.setAttribute("cafeList", cafeList);
        return "carte/sensitive/sensitive";
    }
    
    /**
     * 
     * getCafetoriumList：查询当前登录用户下拥有食堂
     * @param session
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年10月30日 下午2:23:00
     */
    public  List<CafetoriumDto> getCafetoriumList(HttpSession session){
        List<CafetoriumDto> cafeList = new ArrayList<CafetoriumDto>();
        //获取用户拥有的项目权限
        List<ProjectDto> listProject = userServiceI.getProjectBySession(session);
        if (!CollectionUtils.isEmpty(listProject))
        { //循环遍历每一个项目，放入查找dto中对象
            if (listProject.size() > 1)
            {//超管
                CafetoriumDto cafetoriumDto = new CafetoriumDto();
                cafeList = cafetoriumService.findALL(cafetoriumDto, null);
            }
            else
            {//获取项目id
                String projId = listProject.get(0).getId();
                CafetoriumDto cafetoriumDto = new CafetoriumDto();
                cafetoriumDto.setProjId(projId);
                cafeList = cafetoriumService.findALL(cafetoriumDto, null);
            }
        }
        return cafeList;
    }

    /**
     * 
     * 敏感词管理grid
     * @param fieldsDto
     * @return
     */
    @RequestMapping("/dataGrid")
    @ResponseBody
    public DataGrid dataGrid(SensitiveDto sensitiveDto, PageHelper ph,HttpSession session)
    {
        DataGrid dataGrid = new DataGrid();
        PageHelperDto phDto = new PageHelperDto();
        Integer beginRow = (ph.getPage() - 1) * ph.getRows();

        BeanUtils.copyProperties(ph, phDto);
        phDto.setBeginRow(beginRow);
        
        String userId = getSessionUserId(session); 
        if(!"".equals(userId)){
            List<String> listStr = cafeService.CafetoriumByProJectId(userId);
            if(listStr.size() > 0 && listStr != null){
                List<SensitiveDto> list = sensitiveService.findALL(sensitiveDto, phDto,listStr);
                for (int j = 0; j < list.size(); j++)
                {
                    CafetoriumDto cafe = this.cafeService.findById(list.get(j).getCafetoriumId());
                    if(cafe != null){
                        list.get(j).setCafetoriumName(cafe.getCafeName());
                    }
                }
                int count = sensitiveService.findCount(sensitiveDto,listStr);
                dataGrid.setRows(list);
                dataGrid.setTotal(Long.valueOf(count));
            }
        }
        
        return dataGrid;
    }

    /**
     * 跳转到添加敏感词页面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/addPage")
    public String addPage(HttpServletRequest request,HttpSession session)
    {
        SensitiveDto u = new SensitiveDto();
        u.setId(UUID.randomUUID().toString());
        request.setAttribute("sensitiveDto", u);
        
        List<CafetoriumDto> cafeList = getCafetoriumList(session);
        request.setAttribute("cafeList", cafeList);
        
        return "carte/sensitive/sensitiveAdd";
    }

    /**
     * 添加敏感词
     * 
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public Json add(SensitiveDto sensitiveDto)
    {
        Json j = new Json();
        try
        {
            SensitiveDto sensitive = sensitiveService.finByName(sensitiveDto.getSensitiveName(),sensitiveDto.getCafetoriumId());
            if (sensitive != null)
            {
                j.setSuccess(false);
                j.setMsg("该敏感词名称在该食堂已经存在，请勿重复添加！");
                return j;
            }
            sensitiveService.add(sensitiveDto);
            j.setSuccess(true);
            j.setMsg("添加敏感词成功！");
            j.setObj(sensitiveDto);
        }
        catch (Exception e)
        {
            j.setMsg(e.getMessage());
        }
        return j;
    }

    /**
     * 敏感词编辑页面
     * 
     * @return
     */
    @RequestMapping("/editPage")
    public String editPage(HttpServletRequest request, String id,HttpSession session)
    {
        SensitiveDto sensitiveDto = new SensitiveDto();
        Sensitive r = sensitiveService.getSensitiveById(id);
        BeanUtils.copyProperties(r, sensitiveDto);
        //查询食堂名称
        CafetoriumDto cafe = this.cafeService.findById(r.getCafetoriumId());
        sensitiveDto.setCafetoriumName(cafe.getCafeName());
        request.setAttribute("sensitiveDto", sensitiveDto);
        
        
        List<CafetoriumDto> cafeList = getCafetoriumList(session);
        request.setAttribute("cafeList", cafeList);
        
        return "carte/sensitive/sensitiveEdit";
    }

    /**
     * 编辑敏感词
     * @param resource
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    public Json edit(SensitiveDto dto)
    {
        Json j = new Json();
        try
        {
            Sensitive r = sensitiveService.getSensitiveById(dto.getId());
            if("0".equals(dto.getWarning())){
                dto.setValveId("");
            }
            if("1".equals(dto.getWarning())){
                dto.setValveId(r.getValveId());
            }
            dto.setCreateTime(r.getCreateTime());
            dto.setLastUpdateTime(new Date());
            sensitiveService.update(dto);
            j.setSuccess(true);
            j.setMsg("编辑成功！");
        }
        catch (Exception e)
        {
            j.setMsg(e.getMessage());
        }
        return j;
    }

    /**
     * 删除敏感词
     * 
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Json delete(String id)
    {
        Json j = new Json();
        if (!StringUtils.isEmpty(id))
        {
            Sensitive tempSensitive = sensitiveService.getSensitiveById(id);
            sensitiveService.delete(tempSensitive);
            j.setMsg("删除敏感词成功！");
            j.setSuccess(true);
        }
        else
        {
            j.setMsg("删除敏感词失败，id不存在！");
            j.setSuccess(false);
        }
        return j;
    }
    
    /**
     * 
     * sensitiveValveConf：敏感词预警阀值列表页
     * @param request
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年8月18日 上午9:33:23
     */
    @RequestMapping("/sensitiveValveConfAdd")
    public String sensitiveValveConf(HttpServletRequest request,HttpSession session){
        
        String userId = getSessionUserId(session);
        if(!"".equals(userId)){
            List<String> listStr = cafeService.CafetoriumByProjId(userId);
            //查询拥有项目数量
            if(listStr.size() > 1){//超级管理员用户
                List<ProjectDto> listproject =  projectService.findAll();
                request.setAttribute("listproject", listproject);
                request.setAttribute("flag", "0");
            }else if(listStr.size() == 1){//普通用户
                ProjectDto proDto =  projectService.findById(listStr.get(0));
                request.setAttribute("proName", proDto.getProjName());
                request.setAttribute("proId", proDto.getId());
                request.setAttribute("flag", "1");
            }
        }
        
        return "carte/sensitive/sensitiveValveConf";
    }
    
    
    /**
     * 
     * 敏感词管理grid
     * @author pengcheng.yang
     * @param fieldsDto
     * @return
     */
    @RequestMapping("/sensitivedataGrid")
    @ResponseBody
    public DataGrid sensitivedataGrid(SensitiveValveConfDto sensitiveValveConfDto, PageHelper ph,HttpSession session)
    {
        DataGrid dataGrid = new DataGrid();
        PageHelperDto phDto = new PageHelperDto();
        Integer beginRow = (ph.getPage() - 1) * ph.getRows();

        BeanUtils.copyProperties(ph, phDto);
        phDto.setBeginRow(beginRow);
        
        String userId = getSessionUserId(session);
        if(!"".equals(userId)){
            //查询当前登录用户拥有的项目
            List<String> listStr = cafeService.CafetoriumByProjId(userId);
            //根据当前用户拥有的项目查询出所属项目下面的敏感词预警阀值
            List<SensitiveValveConfDto> list = confService.findBy(sensitiveValveConfDto, phDto,listStr);
            for (int i = 0; i < list.size(); i++){
                //查询当前服务项所属项目名称
                ProjectDto dto =  projectService.findById(list.get(i).getProjId());
                list.get(i).setProName(dto.getProjName());
            }
            int count = confService.findCountBy(listStr);
            dataGrid.setRows(list);
            dataGrid.setTotal(Long.valueOf(count));
        }
        
        return dataGrid;
    }
        
    /**
     * 
     * getSessionUserId：获取session中的用户信息
     * @param session
     * @return
     * @exception   
     * @author pengcheng.yang
     * @date 2015年10月27日 上午11:25:56
     */
    public String getSessionUserId(HttpSession session)
    {
       if(session == null)
       {
           return "";
       }
       TImsUsersDto user = (TImsUsersDto)session.getAttribute("user");
       if(user == null)
       {
           return "";
       }
       
       if(StringUtils.isEmpty(user.getId()))
       {
           return "";
       }
       return user.getId();
    }
    
    
    /**
     * 跳转到添加敏感词页面
     * @author pengcheng.yang
     * @param request
     * @return
     */
    @RequestMapping("/addSensitiveConf")
    public String addSensitiveConf(HttpServletRequest request,HttpSession session)
    {
        SensitiveValveConfDto u = new SensitiveValveConfDto();
        u.setId(UUID.randomUUID().toString());
        request.setAttribute("SensitiveValveConfDto", u);
        
        String userId = getSessionUserId(session);
        if(!"".equals(userId)){
            List<String> listStr = cafeService.CafetoriumByProjId(userId);
            //查询拥有项目数量
            if(listStr.size() > 1){//超级管理员用户
                List<ProjectDto> listproject =  projectService.findAll();
                request.setAttribute("listproject", listproject);
                request.setAttribute("flag", "0");
            }else if(listStr.size() == 1){//普通用户
                ProjectDto proDto =  projectService.findById(listStr.get(0));
                request.setAttribute("proName", proDto.getProjName());
                request.setAttribute("proId", proDto.getId());
                request.setAttribute("flag", "1");
            }
        }
        
        return "carte/sensitive/sensitiveValveConfAdd";
    }
    
    /**
     * 添加敏感词预警阀值
     * @author pengcheng.yang
     * @return
     */
    @RequestMapping("/sensitiveVconfAdd")
    @ResponseBody
    public Json sensitiveVconfAdd(SensitiveValveConfDto dto)
    {
        Json j = new Json();
        try
        {
          //验证projId是否为空
            if("".equals(dto.getProjId()) || dto.getProjId() == null){
                j.setSuccess(false);
                j.setMsg("数据丢失,添加失败!");
                return j;
            }
            int flag = confService.sensitiveVconfAdd(dto);
            if(flag==1){
                j.setSuccess(true);
                j.setMsg("添加敏感词预警阀值成功！");
                j.setObj(dto);
            }else {
                j.setSuccess(false);
                j.setMsg("该敏感词敏感词预警阀值失败，请稍候添加！");
                return j;
            }
        }
        catch (Exception e)
        {
            j.setMsg(e.getMessage());
        }
        return j;
    }
    
    
    /**
     * 删除敏感词预警阀值
     * @param id
     * @return
     * @author pengcheng.yang
     */
    @RequestMapping("/sensitiveVconfDelete")
    @ResponseBody
    public Json sensitiveVconfDelete(String id)
    {
        Json j = new Json();
        if (!StringUtils.isEmpty(id))
        {
            SensitiveValveConf conf = confService.getSensitiveConfById(id);
            conf.setId(id);
            int flag = confService.sensitiveVconfDelete(conf);
            if(flag==1){
                j.setMsg("删除敏感词预警阀值成功！");
                j.setSuccess(true);
            }else{
                j.setMsg("删除敏感词预警阀值失败,稍后再试！");
                j.setSuccess(false);
                return j;
            }
        }
        else
        {
            j.setMsg("删除敏感词预警阀值失败，id不存在！");
            j.setSuccess(false);
        }
        return j;
    }
    
    
    /**
     * 跳转敏感词预警阀值
     * @author pengcheng.yang
     * @return
     */
    @RequestMapping("/sensitiveConfEdit")
    public String sensitiveConfEdit(HttpServletRequest request, String id,HttpSession session)
    {
        SensitiveValveConfDto dto = new SensitiveValveConfDto();
        SensitiveValveConf conf = confService.getSensitiveConfById(id);
        BeanUtils.copyProperties(conf, dto);
        request.setAttribute("SensitiveValveConfDto", dto);
        
        String userId = getSessionUserId(session);
        if(!"".equals(userId)){
            List<String> listStr = cafeService.CafetoriumByProjId(userId);
            //查询拥有项目数量
            if(listStr.size() > 1){//超级管理员用户
                List<ProjectDto> listproject =  projectService.findAll();
                request.setAttribute("listproject", listproject);
                request.setAttribute("flag", "0");
            }else if(listStr.size() == 1){//普通用户
                ProjectDto proDto =  projectService.findById(listStr.get(0));
                request.setAttribute("proName", proDto.getProjName());
                request.setAttribute("proId", proDto.getId());
                request.setAttribute("flag", "1");
            }
        }
        return "carte/sensitive/sensitiveVconfEdit";
    }
    
    /**
     * 编辑敏感词预警阀值
     * @author pengcheng.yang
     * @param resource
     * @return
     */
    @RequestMapping("/sensitiveVEdit")
    @ResponseBody
    public Json sensitiveVfEdit(SensitiveValveConfDto dto)
    {
        Json j = new Json();
        SensitiveValveConf conf = confService.getSensitiveConfById(dto.getId());
        if(!StringUtils.isEmpty(conf)){
            dto.setCreateTime(conf.getCreateTime());
            dto.setLastUpdateTime(new Date());
            dto.setProjId(conf.getProjId());
            int flag = confService.sensitiveVconfUpdate(dto);
            if(flag==1){
                j.setMsg("修改敏感词预警阀值成功！");
                j.setSuccess(true);
            }else{
                j.setMsg("修改敏感词预警阀值失败,请稍候再试！");
                j.setSuccess(false);
            }
        }
        return j;
    }
    
    /**
     * 
     * SensitiveVcEdit：跳转到预警阀值选择页
     * @param id
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年8月19日 上午10:45:57
     */
    @RequestMapping("/SensitiveVcEdit")
    public String SensitiveVcEdit(HttpServletRequest request,String id){
        request.setAttribute("sensitiveId",id);
        return "carte/sensitive/editSensitive";
        
    }
    
    
    @RequestMapping("/SaveSensitive")
    @ResponseBody
    public Json SaveSensitive(String id,String sensitiveId)
    {
        Json j = new Json();
        SensitiveDto dto = new SensitiveDto();
        Sensitive r = sensitiveService.getSensitiveById(sensitiveId);
        if(r != null){
            dto.setCreateTime(r.getCreateTime());
            dto.setLastUpdateTime(new Date());
            dto.setId(r.getId());
            dto.setStat(r.getStat());
            dto.setSensitiveName(r.getSensitiveName());
            dto.setValveId(id);
            dto.setWarning(r.getWarning());
            dto.setCafetoriumId(r.getCafetoriumId());
            sensitiveService.update(dto);
            j.setSuccess(true);
            j.setMsg("预警阀值设置成功！");
        }else{
            j.setSuccess(false);
            j.setMsg("预警阀值设置失败,请稍候再试！");
        }
        return j;
    }
    
}
