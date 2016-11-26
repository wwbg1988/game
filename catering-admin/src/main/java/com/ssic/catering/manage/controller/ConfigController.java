/**
 * 
 */
package com.ssic.catering.manage.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ssic.catering.admin.dto.TImsUsersDto;
import com.ssic.catering.admin.pageModel.DataGrid;
import com.ssic.catering.admin.pageModel.Json;
import com.ssic.catering.admin.pageModel.PageHelper;
import com.ssic.catering.admin.service.ProjectService;
import com.ssic.catering.admin.service.UserServiceI;
import com.ssic.catering.base.dto.CafetoriumDto;
import com.ssic.catering.base.dto.ConfigDto;
import com.ssic.catering.base.dto.ConfigScoreValveConfDto;
import com.ssic.catering.base.dto.PageHelperDto;
import com.ssic.catering.base.dto.Tree;
import com.ssic.catering.base.pojo.ConfigScoreValveConf;
import com.ssic.catering.base.service.ICafetoriumService;
import com.ssic.catering.base.service.IConfigScoreValveConfService;
import com.ssic.catering.base.service.IConfigService;
import com.ssic.catering.base.service.ICreateImageService;
import com.ssic.catering.common.image.ImageInfoDto;
import com.ssic.catering.common.image.PropertiesUtils;
import com.ssic.game.common.dto.ProjectDto;
import com.ssic.util.BeanUtils;
import com.ssic.util.constants.DataStatus;

/**		
 * <p>Title: ConfigController </p>
 * <p>Description:  食堂评论的星级配置项管理grid</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 刘博	
 * @date 2015年8月14日 上午11:16:50	
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年8月14日 上午11:16:50</p>
 * <p>修改备注：</p>
 */
@Controller
@RequestMapping("/configController")
public class ConfigController
{
    @Autowired
    private IConfigService configService;
    
    @Autowired
    private IConfigScoreValveConfService conf;
    
    @Autowired
    private ICafetoriumService cafeService;
    
    @Autowired
    private UserServiceI userServiceI;
    
    @Autowired
    private ProjectService projectService;
    
    @Autowired
    private ICreateImageService createImageService;
    

    /**
     *  星级配置项管理grid管理页面
     * 
     * @return
     */
    @RequestMapping("/manager")
    public String manager(HttpServletRequest request,HttpSession session)
    {
        String userId = getSessionUserId(session);
        if(!"".equals(userId)){
            String nginxPath = PropertiesUtils.getProperty("nginx.url");
            request.setAttribute("nginxPath", nginxPath);
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
        return "carte/config/config";
    }
    
    /**
     * 
     * 星级配置项管理grid
     * @param fieldsDto
     * @return
     */
    @RequestMapping("/dataGrid")
    @ResponseBody
    public DataGrid dataGrid(ConfigDto configDto, PageHelper ph,HttpSession session)
    {
        DataGrid dataGrid = new DataGrid();
        PageHelperDto phDto = new PageHelperDto();
        Integer beginRow = (ph.getPage() - 1) * ph.getRows();
        BeanUtils.copyProperties(ph, phDto);
        phDto.setBeginRow(beginRow);

        String userId = getSessionUserId(session);
        if(!"".equals(userId)){
            List<String> listStr = cafeService.CafetoriumByProjId(userId);
            if(listStr.size() > 0 && listStr != null){
                //分页查询服务项
                List<ConfigDto> list = configService.findALL(configDto,listStr,phDto);
                for (int i = 0; i < list.size(); i++){
                    //查询当前服务项所属项目名称
                    ProjectDto dto =  projectService.findById(list.get(i).getProjId());
                    list.get(i).setProName(dto.getProjName());
                }
                int count = configService.findCount(configDto,listStr);
                dataGrid.setRows(list);
                dataGrid.setTotal(Long.valueOf(count));
            }
        }
        return dataGrid;
    }

    /**
     * 跳转到添加配置项页面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/addPage")
    public String addPage(HttpServletRequest request,HttpSession session)
    {
        ConfigDto u = new ConfigDto();
        u.setId(UUID.randomUUID().toString());
        request.setAttribute("configDto", u);
        
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
        return "carte/config/configAdd";
    }

    /**
     * 添加配置项
     * 
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public Json add(ConfigDto configDto,ImageInfoDto image,HttpSession session,@RequestParam("image") MultipartFile myfile,
        HttpServletRequest request, HttpServletResponse response)
    {
        Json j = new Json();
        try
        {
            //验证projId和配置项名称是否为空
            if("".equals(configDto.getProjId()) || configDto.getProjId() == null || "".equals(configDto.getConfigName()) || configDto.getConfigName() ==null){
                j.setSuccess(false);
                j.setMsg("数据丢失,添加失败!");
                return j;
            }
          //查询添加的配置项是否已经存在
            List<ConfigDto> dto = configService.finByName(configDto.getConfigName(),configDto.getProjId());
            if (dto.size()>0)
            {
                j.setSuccess(false);
                j.setMsg("该配置项名称已经存在，请勿重复添加！");
                return j;
            }else{
                //检查图片上传是否为空
                Map<String, Object> map = createImageService.createImage(image, myfile, request, response);
                if((boolean)map.get("success") == false){
                    j.setMsg((String) map.get("message"));
                    j.setSuccess((boolean) map.get("success"));
                    return j;
                }else{
                    String imageurl = (String) map.get("image_url");
                    configDto.setImageUrl("/upload/images/"+imageurl);
                    //保存配置项信息
                    configService.add(configDto);
                    j.setSuccess(true);
                    j.setMsg("添加配置项成功！");
                    j.setObj(configDto);
                }
            }
        }catch (Exception e){
            j.setMsg(e.getMessage());
        }
        return j;
    }

    /**
     * 配置项编辑页面
     * 
     * @return
     */
    @RequestMapping("/editPage")
    public String editPage(HttpServletRequest request,HttpSession session,String id)
    {
        ConfigDto r = configService.findConfigToId(id);
        request.setAttribute("configDto", r);
        
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
        return "carte/config/configEdit";
    }

    /**
     * 编辑配置项
     * 
     * @param resource
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    public Json edit(ConfigDto configDto,ImageInfoDto image,HttpSession session,@RequestParam("image") MultipartFile myfile,
        HttpServletRequest request, HttpServletResponse response){
        Json j = new Json();
        try{
            
            boolean flag = false;
            
            List<String> listStr = new ArrayList<String>();
            listStr.add(configDto.getId());
            //判断当前修改的名字是否已经存在
            List<ConfigDto> dto = configService.findByConfigName(configDto,listStr);
            for (int i = 0; i < dto.size(); i++){
                if(configDto.getConfigName().equals(dto.get(i).getConfigName())){
                   flag = true;
                   break;
                }
            }
            if(flag){
                j.setSuccess(false);
                j.setMsg("该配置项名称已经存在，请勿重复添加！");
                return j;
            }else{
                //上传评论项图标 并验证上传的图片是否为空
                Map<String, Object> map = createImageService.createImage(image, myfile, request, response);
                if((boolean)map.get("success") == false){
                    j.setMsg((String) map.get("message"));
                    j.setSuccess((boolean) map.get("success"));
                    return j;
                }else{
                    String imageurl = (String) map.get("image_url");
                    configDto.setImageUrl("/upload/images/"+imageurl);
                    ConfigDto r = configService.findConfigToId(configDto.getId());
                    configDto.setCreateTime(r.getCreateTime());
                    configDto.setLastUpdateTime(new Date());
                    configService.update(configDto);
                    j.setSuccess(true);
                    j.setMsg("编辑成功！");
                }
            }
        }catch (Exception e){
            j.setMsg(e.getMessage());
        }
        
        return j;
    }

    /**
     * 删除配置项
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
            ConfigDto r = configService.findConfigToId(id);
            configService.delete(r);
            j.setMsg("删除配置项成功！");
            j.setSuccess(true);
        }
        else
        {
            j.setMsg("删除配置项失败，id不存在！");
            j.setSuccess(false);
        }
        return j;
    }

    /**
     * Config 查找TREE
     */
    @RequestMapping("/findTree")
    @ResponseBody
    public List<Tree> findTree(String searchName,HttpSession session)
    {
        String userId = getSessionUserId(session);
        if(!"".equals(userId)){
            //查询当前登录用户拥有的项目
            List<String> listStr = cafeService.CafetoriumByProjId(userId);
            List<Tree> listTree = configService.findTree(searchName,listStr);
            if(listTree != null){
                return listTree;
            }else{
                return new ArrayList<Tree>();
            }
        }
        return new ArrayList<Tree>();
    }

    @RequestMapping("/grantPage")
    public String grantPage(String id, HttpServletRequest request, HttpSession session)
    {
        session.setAttribute("cafeId", id);
        String result = configService.chooseConfig(id);
        request.setAttribute("configIds",result);
        return "carte/config/configGrant";
    }

    @RequestMapping("/grantCafe")
    @ResponseBody
    public Json grantCafe(String userIdss,HttpSession session){
        String id ="";
        Json j = new Json();
        if(StringUtils.isEmpty(session.getAttribute("cafeId"))){
            j.setSuccess(false);
            j.setMsg("查询餐厅编号失败");
            return j;
        }
        id =session.getAttribute("cafeId").toString();
        session.removeAttribute("cafeId");
        int status = configService.grant(id, userIdss);
        if(status == DataStatus.HTTP_SUCCESS){
            j.setSuccess(true);
            j.setMsg("勾选成功");
            return j;
        }else{
            j.setSuccess(false);
            j.setMsg("勾选失败");
            return j;
        }
            
    }
    
    /**
     * 
     * configScoreValveConf：跳转食堂评分预警阀值查询页
     * @param request
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年8月20日 下午1:36:44
     */
    @RequestMapping("/configScoreValveConf")
    public String configScoreValveConf(HttpServletRequest request){
        return "carte/config/configScoreValveConfPage";
    }
    
    @RequestMapping("/QueryConfigScoreValveConf")
    @ResponseBody
    public DataGrid configScoreValveConfDataGrid(ConfigScoreValveConfDto dto,PageHelper ph,HttpSession session){
        DataGrid dataGrid = new DataGrid();
        PageHelperDto phDto = new PageHelperDto();
        Integer beginRow = (ph.getPage() - 1) * ph.getRows();
        
        BeanUtils.copyProperties(ph, phDto);
        phDto.setBeginRow(beginRow);
        
        String userId = getSessionUserId(session); 
        if(!"".equals(userId)){
            List<String> listStr = cafeService.CafetoriumByProJectId(userId);
            if(listStr.size() > 0 && listStr != null){
                List<ConfigScoreValveConfDto> list = conf.findBy(dto,listStr,phDto);
                for (int i = 0; i < list.size(); i++)
                {
                    CafetoriumDto cafe = this.cafeService.findById(list.get(i).getCafetoriumId());
                    list.get(i).setCafetoriumName(cafe.getCafeName());
                }
                int count = conf.findCountBy(listStr);
                dataGrid.setRows(list);
                dataGrid.setTotal(Long.valueOf(count));
            }
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
     * 
     * deleteConfigScoreValveConf：删除评分项预警阀值
     * @param id
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年8月20日 下午3:11:21
     */
    @RequestMapping("/configScoreValveConfDelete")
    @ResponseBody
    public Json deleteConfigScoreValveConf(String id){
        Json j = new Json();
        if(!StringUtils.isEmpty(id)){
            ConfigScoreValveConf config = conf.getConfigConfById(id);
            ConfigScoreValveConfDto dto = new ConfigScoreValveConfDto(); 
            BeanUtils.copyProperties(config, dto);
            int flag = conf.deleteConfigConfById(dto);
            if(flag>0){
                j.setMsg("删除评分项预警阀值成功！");
                j.setSuccess(true);
            }else{
                j.setMsg("删除评分项预警阀值失败！");
                j.setSuccess(false);
            }
        }else{
            j.setMsg("评分项预警阀值Id不存在！");
            j.setSuccess(true);
        }
        return j;
    }
    
    /**
     * 
     * configScoreValveConfUpdateEdit：跳转到评分项预警阀值页面
     * @param request
     * @param id
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年8月20日 下午4:04:50
     */
    @RequestMapping("configScoreValveConfUpdatePage")
    public String configScoreValveConfUpdateEdit(HttpServletRequest request , String id){
        ConfigScoreValveConfDto dto = new ConfigScoreValveConfDto();
        ConfigScoreValveConf config = conf.getConfigConfById(id);
        CafetoriumDto cafe = this.cafeService.findById(config.getCafetoriumId());
        dto.setCafetoriumName(cafe.getCafeName());
        BeanUtils.copyProperties(config, dto);
        request.setAttribute("ConfDto", dto);
        return "carte/config/configScoreValveConfPageEdit";
    }
    
    /**
     * 
     * UpdateConfigScoreValveConf：保存修改评分项预警阀值
     * @param dto
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年8月20日 下午4:05:29
     */
    @RequestMapping("/configScoreValveConfUpdate")
    @ResponseBody
    public Json UpdateConfigScoreValveConf(ConfigScoreValveConfDto dto){
        Json j = new Json();
        if(!StringUtils.isEmpty(dto)){
            ConfigScoreValveConf config = conf.getConfigConfById(dto.getId());
            if(!StringUtils.isEmpty(config)){
                dto.setCafetoriumId(config.getCafetoriumId());
                dto.setCreateTime(config.getCreateTime());
                dto.setLastUpdateTime(new Date());
                dto.setStat(DataStatus.ENABLED);
                int flag = conf.updateConfigConfById(dto);
                if(flag>0){
                    j.setMsg("修改评分项预警阀值成功！");
                    j.setSuccess(true);
                }else{
                    j.setMsg("修改评分项预警阀值失败！");
                    j.setSuccess(false);
                }
            }
        }else{
            j.setMsg("评分项预警阀值Id不存在！");
            j.setSuccess(true);
        }
        return j;
    }
    
}
