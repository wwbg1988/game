/**
 * 
 */
package com.ssic.catering.manage.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ssic.catering.admin.pageModel.DataGrid;
import com.ssic.catering.admin.pageModel.Json;
import com.ssic.catering.admin.pageModel.PageHelper;
import com.ssic.catering.admin.service.ProjectService;
import com.ssic.catering.admin.service.UserServiceI;
import com.ssic.catering.base.dto.AddressUserDto;
import com.ssic.catering.base.service.IAddressUserService;
import com.ssic.catering.base.service.ICaterProjectUsersService;
import com.ssic.catering.base.service.ICreateImageService;
import com.ssic.catering.base.service.ICreatePhdtoService;
import com.ssic.catering.base.service.IimsUserService;
import com.ssic.catering.common.image.ImageInfoDto;
import com.ssic.catering.common.image.PropertiesUtils;
import com.ssic.catering.common.util.MD5Util;
import com.ssic.game.common.dto.DeptUsersDto;
import com.ssic.game.common.dto.ImsUsersDto;
import com.ssic.game.common.dto.LTUserDto;
import com.ssic.game.common.dto.PageHelperDto;
import com.ssic.game.common.dto.ProjectDto;
import com.ssic.game.common.dto.ProjectUsersDto;
import com.ssic.game.common.dto.RoleUsersDto;
import com.ssic.game.common.pojo.ImsUsers;
import com.ssic.game.common.service.DeptUserService;
import com.ssic.game.common.service.IImsUserService;
import com.ssic.game.common.service.ILiaoTianService;
import com.ssic.game.common.service.ITmsRoleservice;
import com.ssic.game.common.util.CateringProjectG;
import com.ssic.game.im.dto.QjyImUserDto;
import com.ssic.game.im.service.IQjyImService;
import com.ssic.util.UUIDGenerator;

/**		
 * <p>Title: ImsUserController </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 刘博	
 * @date 2015年8月17日 上午9:27:49	
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年8月17日 上午9:27:49</p>
 * <p>修改备注：</p>
 */

@Controller
@RequestMapping("imsUserController")
public class ImsUserController
{

    @Autowired
    private IImsUserService iImsUserService;
    @Autowired
    private DeptUserService deptUserService;
    @Autowired
    private ICreateImageService createImageService;
    @Autowired
    private IAddressUserService addressUserService;
    @Autowired
    private IQjyImService qjyImService;
    @Autowired
    private ICreatePhdtoService createPhdtoService;
    @Autowired
    private IimsUserService catimsUserService;
    @Autowired
    private ICaterProjectUsersService caterProjectUsersService;
    @Autowired
    private ITmsRoleservice iTmsRoleservice;
    @Autowired
    private UserServiceI userServiceI;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private ILiaoTianService liaoTianService;

    protected static final Log logger = LogFactory.getLog(ImsUserController.class);

    private static String role_qj = CateringProjectG.role_qj; //填写请假单名单

    private static String role_cc = CateringProjectG.role_cc; //填写出差单名单

    private static String role_bx = CateringProjectG.role_bx; //填写报销单名单

    private static String role_qj_pt = CateringProjectG.role_qj_pt; //填写请假单名单  趴体外烩

    private static String role_cc_pt = CateringProjectG.role_cc_pt; //填写出差单名单  趴体外烩

    private static String role_bx_pt = CateringProjectG.role_bx_pt; //填写报销单名单  趴体外烩

    private static String cater_project = CateringProjectG.cater_project; //团餐项目

    private static String patiwaihui_project = CateringProjectG.patiwaihui_project; // 趴体外会项目
    
    private static String kps_project = CateringProjectG.kps_project;  //康帕斯项目
    
    private static String role_bx_kps = CateringProjectG.role_bx_kps;  //
    
    private static String  role_qj_juju  =  CateringProjectG.role_qj_juju;   //填写请假单 聚运动
    
    private static String role_cc_juju = CateringProjectG.role_cc_juju;  //填写出差单 聚运动
    
    private static String role_bx_juju = CateringProjectG.role_bx_juju;  //填写报销单 聚运动
    
    private static String juju_project = CateringProjectG.juju_project ;  //聚运动项目
    
    
    
    

    @RequestMapping("/manager")
    public String manager(HttpServletRequest request)
    {
        String nginxPath = PropertiesUtils.getProperty("nginx.url");
        request.setAttribute("nginxPath", nginxPath);
        return "carte/imsUser/imsUser";
    }

    @RequestMapping("/dataGrid")
    @ResponseBody
    public DataGrid dataGrid(ImsUsersDto imsUsersDto, PageHelper ph, HttpSession session)
    {

        //获取用户拥有的项目权限
        List<ProjectDto> listProject = userServiceI.getProjectBySession(session);

        List<ImsUsersDto> resultList = new ArrayList<ImsUsersDto>();
        DataGrid dataGrid = new DataGrid();
        PageHelperDto phdto =
            createPhdtoService.getNewPhDto(ph.getOrder(), ph.getPage(), ph.getRows(), ph.getSort());
        List<ImsUsersDto> list = iImsUserService.findUsersAll(imsUsersDto, phdto);
        for (int i = 0; i < list.size(); i++)
        {
            ImsUsersDto iuDto = list.get(i);
            String userId = iuDto.getId();
            DeptUsersDto deptUsersDto = new DeptUsersDto();
            deptUsersDto.setUserId(userId);
            List<DeptUsersDto> deptUsers = deptUserService.findDeptUser(deptUsersDto);

            String projectNames = "";
            String deptIdNames = "";
            if (deptUsers != null && deptUsers.size() > 0)
            {
                for (int j = 0; j < deptUsers.size(); j++)
                {
                    DeptUsersDto ddto = deptUsers.get(j);
                    if (!CollectionUtils.isEmpty(listProject))
                    {
                        if (listProject.size() > 1)
                        {//超管
                            ProjectDto pro = projectService.findById(ddto.getProjId());
                            iuDto.setProjectNames(pro.getProjName());
                            iuDto.setDeptNames(ddto.getDeptName());
                            if (!StringUtils.isEmpty(ddto.getIsAdmin()))
                            {
                                iuDto.setIsAdmin(Integer.parseInt(ddto.getIsAdmin()));
                            }
                            else
                            {
                                iuDto.setIsAdmin(0);
                            }
                            resultList.add(iuDto);
                        }
                        else
                        {
                            if (ddto.getProjId().equals(listProject.get(0).getId()))
                            {
                                projectNames = projectNames + ddto.getProjName() + ",";
                                deptIdNames = deptIdNames + ddto.getDeptName() + ",";
                                projectNames = projectNames.substring(0, projectNames.length() - 1);
                                deptIdNames = deptIdNames.substring(0, deptIdNames.length() - 1);
                                iuDto.setProjectNames(projectNames);
                                iuDto.setDeptNames(deptIdNames);
                                if (!StringUtils.isEmpty(ddto.getIsAdmin()))
                                {
                                    iuDto.setIsAdmin(Integer.parseInt(ddto.getIsAdmin()));
                                }
                                else
                                {
                                    iuDto.setIsAdmin(0);
                                }
                                resultList.add(iuDto);
                            }
                        }
                    }
                }
            }
        }
        int count = iImsUserService.findCount(imsUsersDto);
        dataGrid.setRows(resultList);
        dataGrid.setTotal(Long.valueOf(resultList.size()));
        return dataGrid;
    }

    @RequestMapping("/add")
    public String add(HttpServletRequest request)
    {
        request.setAttribute("userId", UUIDGenerator.getUUID());
        return "carte/imsUser/imsUserAdd";
    }

    @RequestMapping("/insertUserAndADept")
    @ResponseBody
    public Json insertUserAndADept(ImsUsersDto user, HttpSession session, String flag, String userId,
        String isExistManager)
    {
        Json j = new Json();

        if (flag.equals("user"))
        {
            user.setId(userId);
            Json j_user = insertUser(user, session);
            String msgUser = j_user.getMsg();
            if (msgUser.equals("用户插入成功"))
            {
                j.setMsg("用户插入成功");
                j.setSuccess(true);
            }
            else
            {
                j.setMsg(j_user.getMsg());
                j.setSuccess(false);
            }
        }
        else
        {
            ImsUsersDto temps = new ImsUsersDto();
            temps.setId(userId);
            if (userId != null && !userId.equals(""))
            {
                user.setId(userId);
                Json j_dept = insertDept(user, session, isExistManager);
                String msgDept = j_dept.getMsg();
                if (msgDept.equals("新增部门成功"))
                {
                    j.setMsg("新增部门成功");
                    j.setSuccess(true);
                }
                else
                {
                    j.setMsg(j_dept.getMsg());
                    j.setSuccess(false);
                }
            }
            else
            {
                j.setMsg("用户账号获取失败");
                j.setSuccess(false);
            }
        }
        return j;
    }

    @RequestMapping("/insertDept")
    @ResponseBody
    public Json insertDept(ImsUsersDto user, HttpSession session, String isExistManager)
    {
        Json j = new Json();
        if (user.getProjectIds() == null || user.getProjectIds().equals(""))
        {
            j.setMsg("项目ID不能为空");
            j.setSuccess(false);
            return j;
        }
        if (user.getDeptIds() == null || user.getDeptIds().equals(""))
        {
            j.setMsg("部门ID不能为空");
            j.setSuccess(false);
            return j;
        }
        //插入t_ims_dept_users
        DeptUsersDto deptUsersDto = new DeptUsersDto();
        deptUsersDto.setId(UUIDGenerator.getUUID());
        deptUsersDto.setUserId(user.getId());
        deptUsersDto.setProjId(user.getProjectIds());
        deptUsersDto.setDeptId(user.getDeptIds());
        deptUsersDto.setCreateTime(new Date());
        deptUsersDto.setStat(1);
        deptUsersDto.setIsAdmin(isExistManager);
        deptUserService.insertDeptUser(deptUsersDto);

        //插入project_users
        ProjectUsersDto projectUsersDto = new ProjectUsersDto();
        projectUsersDto.setProjId(user.getProjectIds());
        projectUsersDto.setUserId(user.getId());
        projectUsersDto.setStat(1);
        projectUsersDto.setCreateTime(new Date());
        projectUsersDto.setId(UUIDGenerator.getUUID());
        caterProjectUsersService.insertPUser(projectUsersDto);

        //插入t_ims_role_users
        addThreeRoles(user);

        j.setMsg("新增部门成功");
        j.setSuccess(false);
        return j;
    }

    public void addThreeRoles(ImsUsersDto user)
    {

        String projId = user.getProjectIds();
        RoleUsersDto roleUsersDto_qj = new RoleUsersDto();
        roleUsersDto_qj.setId(UUIDGenerator.getUUID());
        if (cater_project.equals(projId))
        {
            roleUsersDto_qj.setRoleId(role_qj);
        }
        else if (patiwaihui_project.equals(projId))
        {
            roleUsersDto_qj.setRoleId(role_qj_pt);
        }else if(juju_project.equals(projId)){
        	roleUsersDto_qj.setRoleId(role_qj_juju);
        }
        roleUsersDto_qj.setUserId(user.getId());
        roleUsersDto_qj.setStat(1);
        roleUsersDto_qj.setCreateTime(new Date());
        iTmsRoleservice.insertRoleUser(roleUsersDto_qj);

        RoleUsersDto roleUsersDto_cc = new RoleUsersDto();
        roleUsersDto_cc.setId(UUIDGenerator.getUUID());
        if (cater_project.equals(projId))
        {
            roleUsersDto_cc.setRoleId(role_cc);
        }
        else if (patiwaihui_project.equals(projId))
        {
            roleUsersDto_cc.setRoleId(role_cc_pt);
        }else if(juju_project.equals(projId)){
        	roleUsersDto_cc.setRoleId(role_cc_juju);
        }
        roleUsersDto_cc.setUserId(user.getId());
        roleUsersDto_cc.setStat(1);
        roleUsersDto_cc.setCreateTime(new Date());
        iTmsRoleservice.insertRoleUser(roleUsersDto_cc);

        RoleUsersDto roleUsersDto_bx = new RoleUsersDto();
        roleUsersDto_bx.setId(UUIDGenerator.getUUID());
        if (cater_project.equals(projId))
        {
            roleUsersDto_bx.setRoleId(role_bx);
        }
        else if (patiwaihui_project.equals(projId))
        {
            roleUsersDto_bx.setRoleId(role_bx_pt);
        }else if(juju_project.equals(projId)){
        	roleUsersDto_bx.setRoleId(role_bx_juju);
        }
        roleUsersDto_bx.setUserId(user.getId());
        roleUsersDto_bx.setStat(1);
        roleUsersDto_bx.setCreateTime(new Date());
        iTmsRoleservice.insertRoleUser(roleUsersDto_bx);

    }

    @RequestMapping("/insertUser")
    @ResponseBody
    public Json insertUser(ImsUsersDto user, HttpSession session)
    {
        Json j = new Json();
        if (user.getPassword() == null || user.getPassword().equals(""))
        {
            j.setMsg("密码不能为空");
            j.setSuccess(false);
            return j;
        }
        if (user.getPassword().length() < 6)
        {
            j.setSuccess(false);
            j.setMsg("密码长度不能小于六位");
            return j;
        }

        if (user.getName().length() > 10)
        {
            j.setSuccess(false);
            j.setMsg("用户名称不能大于10位");
            return j;
        }
        if (user.getUserNo().length() > 10)
        {
            j.setSuccess(false);
            j.setMsg("用户工号不能大于10位");
            return j;
        }

        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(user.getAge() + "");
        if (!isNum.matches())
        {
            j.setSuccess(false);
            j.setMsg("年龄不是数字");
            return j;
        }

        if (user.getPhone() == null || "".equals(user.getPhone()))
        {
            j.setSuccess(false);
            j.setMsg("手机号不能为空");
            return j;
        }
        Pattern ptel = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher mtel = ptel.matcher(user.getPhone());
        if (!mtel.matches())
        {
            j.setSuccess(false);
            j.setMsg("手机号不合法");
            return j;
        }

        ImsUsersDto temps = new ImsUsersDto();
        temps.setUserAccount(user.getUserAccount());
        int tempCounts = iImsUserService.vailUserAccount(temps);
        if (tempCounts > 0)
        {
            j.setSuccess(false);
            j.setMsg("登陆账号已存在");
            return j;
        }
        Pattern p = Pattern.compile("^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\\.([a-zA-Z0-9_-])+)+$");
        Matcher isMail = p.matcher(user.getEmail());
        if (!StringUtils.isEmpty(user.getEmail()) && !isMail.matches())
        {
            j.setSuccess(false);
            j.setMsg("邮箱不合法");
            return j;
        }
//        user.setCreatedatetime(new Date());
//        user.setStat(1);
//        user.setPassword(MD5Util.md5(user.getPassword()));
//        user.setQjyAccount("qjy_" + user.getUserAccount());
//        user.setMobilePhone(user.getPhone());
//        //插入t_ims_users
//        iImsUserService.insertUser(user);
        //添加IM聊天
        LTUserDto lTUserDto = new LTUserDto();
        lTUserDto.setUserAccount(user.getUserAccount());
        lTUserDto.setPassword(MD5Util.md5(user.getPassword()));
        liaoTianService.addLTUser(lTUserDto);
        
        //查询该账号是否注册成功，如果不存在，则返回失败
        String states = liaoTianService.findIsExistByAccount(lTUserDto);
        if(!"200".equals(states)){
        	j.setMsg("用户添加失败，请重新添加");
        	j.setSuccess(false);
        	return j;
        }else{
        	  user.setPassword(MD5Util.md5(user.getPassword()));
              iImsUserService.createUser(user);
        }
        //添加亲加云账号
        //addQjUser(user);

        j.setMsg("用户插入成功");
        j.setSuccess(true);
        return j;
    }

    public void addQjUser(ImsUsersDto user)
    {
        QjyImUserDto qjyImUserDto = new QjyImUserDto();
        qjyImUserDto.setUserAccount("qjy_" + user.getUserAccount());
        qjyImUserDto.setNickname(user.getName());
        qjyImUserDto.setPwd("123456");
        qjyImService.importUsers(qjyImUserDto);
    }

    @RequestMapping("/vailUserAccount")
    @ResponseBody
    public int vailUserAccount(String userAccount)
    {
        ImsUsersDto temps = new ImsUsersDto();
        temps.setUserAccount(userAccount);
        int tempCounts = iImsUserService.vailUserAccount(temps);
        return tempCounts;
    }

    @RequestMapping("deleteUser")
    @ResponseBody
    public Json deleteUser(ImsUsersDto imsUsersDto)
    {
        Json j = new Json();
        catimsUserService.delAllUser(imsUsersDto);
        //       iImsUserService.deleteUser(imsUsersDto);
        //        DeptUsersDto deptUsersDto = new DeptUsersDto();
        //        deptUsersDto.setUserId(imsUsersDto.getId());
        //        deptUserService.deleteDeptUser(deptUsersDto);
        j.setMsg("删除用户成功");
        j.setSuccess(true);
        return j;
    }

    @RequestMapping("/finduserByproj")
    @ResponseBody
    public List<DeptUsersDto> finduserByproj(HttpSession session)
    {
        List<DeptUsersDto> deptUsers = new ArrayList<DeptUsersDto>();
        //获取用户拥有的项目权限
        List<ProjectDto> listProject = userServiceI.getProjectBySession(session);
        DeptUsersDto deptUsersDto = new DeptUsersDto();
        if (!CollectionUtils.isEmpty(listProject))
        {
            if (listProject.size() > 1)
            {//超管
             // deptUsersDto.setProjId(cateringProjId);
                deptUsersDto.setStat(1);
                deptUsers = deptUserService.findDeptUser(deptUsersDto);
            }
            else
            {
                deptUsersDto.setProjId(listProject.get(0).getId());
                deptUsersDto.setStat(1);
                deptUsers = deptUserService.findDeptUser(deptUsersDto);
            }
        }

        return deptUsers;
    }

    @ResponseBody
    @RequestMapping("/findAll")
    public List<ImsUsersDto> findAll(HttpSession session)
    {
        //获取用户拥有的项目权限
        List<ProjectDto> listProject = userServiceI.getProjectBySession(session);
        List<ImsUsersDto> resultList = new ArrayList<ImsUsersDto>();
        List<ImsUsersDto> userList = new ArrayList<ImsUsersDto>();
        ImsUsersDto imsUsersDto = new ImsUsersDto();
        List<ImsUsersDto> list = iImsUserService.findUsers(imsUsersDto);
        if (!CollectionUtils.isEmpty(listProject))
        {
            if (listProject.size() > 1)
            {//超管
                resultList.addAll(list);
            }
            else
            {
                for (int i = 0; i < list.size(); i++)
                {
                    ImsUsersDto iuDto = list.get(i);
                    String userId = iuDto.getId();
                    DeptUsersDto deptUsersDto = new DeptUsersDto();
                    deptUsersDto.setUserId(userId);
                    List<DeptUsersDto> deptUsers = deptUserService.findDeptUser(deptUsersDto);
                    if (deptUsers != null && deptUsers.size() > 0)
                    {
                        for (int j = 0; j < deptUsers.size(); j++)
                        {
                            DeptUsersDto ddto = deptUsers.get(j);
                            AddressUserDto addressUser = new AddressUserDto();
                            addressUserService.findAll(addressUser);
                            if (ddto.getProjId().equals(listProject.get(0).getId()))
                            {
                                resultList.add(iuDto);
                            }
                        }
                    }
                }
            }
        }

        /* //查找所有的系统用户，如果addressUserList已经出现该用户，则返回的结果集中去掉该用户
         AddressUserDto addressUser = new AddressUserDto();
         List<AddressUserDto> addressUserList = addressUserService.findAll(addressUser);

         for (AddressUserDto addressUserDto : addressUserList)
         {
             for (ImsUsersDto usrDto : resultList)
             {
                 if (usrDto.getId().equals(addressUserDto.getUserId()))
                 {
                     userList.add(usrDto);
                 }
             }
         }
         resultList.removeAll(userList);*/
        return resultList;
    }

    //上传图片 展示图片
    @RequestMapping("/showPicture")
    @ResponseBody
    public void showPicture(HttpServletResponse response, String userImage) throws Exception
    {
        createImageService.showPicture(response, userImage);
    }

    @RequestMapping("/upLoadImage")
    public String upLoadImage(HttpServletRequest request, String id)
    {
        request.setAttribute("userid", id);
        return "carte/imsUser/userUpLoad";
    }

    //上传图片

    @RequestMapping("/create")
    @ResponseBody
    public Json createImage(ImageInfoDto image, @RequestParam("image") MultipartFile myfile,
        HttpServletRequest request, HttpServletResponse response, String userid) throws IOException
    {
        logger.info("upload pic : " + image);
        Json j = new Json();
        Map<String, Object> map = createImageService.createImage(image, myfile, request, response);
        //如果已经有图片则更新image_url		
        ImsUsers imsUsers = new ImsUsers();
        imsUsers.setId(userid);
        String imageurl = (String) map.get("image_url");
        imsUsers.setUserImage("/upload/images/" + imageurl);
        imsUsers.setModifydatetime(new Date());
        iImsUserService.updateImsUsers(imsUsers);
        j.setMsg((String) map.get("message"));
        j.setSuccess((boolean) map.get("success"));
        return j;
    }
}
