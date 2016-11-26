package com.ssic.game.manage.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssic.game.admin.dto.TImsUsersDto;
import com.ssic.game.admin.pageModel.DataGrid;
import com.ssic.game.admin.pageModel.Json;
import com.ssic.game.admin.pageModel.PageHelper;
import com.ssic.game.admin.util.MD5Util;
import com.ssic.game.common.dto.DeptDto;
import com.ssic.game.common.dto.DeptUsersDto;
import com.ssic.game.common.dto.ImsUsersDto;
import com.ssic.game.common.dto.LTUserDto;
import com.ssic.game.common.dto.PageHelperDto;
import com.ssic.game.common.dto.ProjectUsersDto;
import com.ssic.game.common.dto.RoleUsersDto;
import com.ssic.game.common.service.DeptUserService;
import com.ssic.game.common.service.IImsUserService;
import com.ssic.game.common.service.ILiaoTianService;
import com.ssic.game.common.service.ITmsRoleservice;
import com.ssic.game.common.util.CateringProjectG;
import com.ssic.game.im.dto.QjyImUserDto;
import com.ssic.game.im.service.IQjyImService;
import com.ssic.game.manage.qinjia.QJConnectDto;
import com.ssic.game.manage.service.DeptService;
import com.ssic.game.manage.service.IOperateUserService;
import com.ssic.game.manage.service.IProjectUsersService;
import com.ssic.util.UUIDGenerator;

@Controller
@RequestMapping("imsUserController")
public class ImsUserController
{

    @Autowired
    private IImsUserService iImsUserService;
    @Autowired
    private IProjectUsersService iprojectUsersService;
    @Autowired
    private DeptUserService deptUserService;
    @Autowired
    private QinJiaController qinJiaController;
    @Autowired
    private DeptService deptService;
    @Autowired
    private IQjyImService qjyImService;
    @Autowired
    private IProjectUsersService caterProjectUsersService;
    @Autowired
    private ITmsRoleservice iTmsRoleservice;
    @Autowired
    private ILiaoTianService liaoTianService;
    @Autowired
    private IOperateUserService iOperateUserService;

    

    @RequestMapping("/manager")
    public String manager()
    {
        return "ims/imsUser";
    }

    @RequestMapping("/add")
    public String add(HttpServletRequest request)
    {
        request.setAttribute("userId", UUIDGenerator.getUUID());
        return "ims/imsUserAdd";
    }
    
    @RequestMapping("/addTwo")
    public String addTwo(HttpServletRequest request){
    	request.setAttribute("userId", UUIDGenerator.getUUID());
    	return "ims/imsUserAddTwo";
    }
    

    @RequestMapping("/edit")
    public String edit(HttpServletRequest request, String id)
    {
        ImsUsersDto imsUsersDto = new ImsUsersDto();
        ImsUsersDto udto = new ImsUsersDto();
        imsUsersDto.setId(id);
        List<ImsUsersDto> list = iImsUserService.findUsers(imsUsersDto);
        if (list != null && list.size() > 0)
        {
            udto = list.get(0);
        }
        DeptUsersDto deptUsersDto = new DeptUsersDto();
        deptUsersDto.setUserId(id);
        List<DeptUsersDto> deptUsers = deptUserService.findDeptUser(deptUsersDto);
        DeptUsersDto duDto = new DeptUsersDto();
        String projids = "";
        String deptids = "";
        if (deptUsers != null && deptUsers.size() > 0)
        {
            duDto = deptUsers.get(0);
            projids = duDto.getProjId();
            deptids = duDto.getDeptId();

            udto.setProjectIds(projids);
            udto.setDeptIds(deptids);
            // udto.setIsAdmin(Integer.parseInt(duDto.getIsAdmin()) );
        }
        udto.setProjectIds(projids);
        udto.setDeptIds(deptids);
        //如果没有部门关系则返回的管理员 默认为0
        // udto.setIsAdmin(0);
        // }
        request.setAttribute("user", udto);
        return "ims/imsUserEdit";
    }

    @RequestMapping("/dataGrid")
    @ResponseBody
    public DataGrid dataGrid(ImsUsersDto imsUsersDto, PageHelper ph)
    {
        DataGrid dataGrid = new DataGrid();
        PageHelperDto phdto = new PageHelperDto();
        phdto.setOrder(ph.getOrder());
        phdto.setPage(ph.getPage());
        phdto.setRows(ph.getRows());
        phdto.setSort(ph.getSort());
        phdto.setBeginRow((ph.getPage() - 1) * ph.getRows());
        //查询所有的人员信息
        List<ImsUsersDto> listOperUser = iOperateUserService.dataGrid(imsUsersDto, phdto);
        //查询所有的人员数量
        int count = iImsUserService.findCount(imsUsersDto);
        dataGrid.setRows(listOperUser);
        dataGrid.setTotal(Long.valueOf(count));
        return dataGrid;

    }

    @RequestMapping("/insertUserDept2")
    @ResponseBody
    public Json insertUserDept2(ImsUsersDto user, String userId, String isExistManager){  //单独只有一个项目一个部门
    	 Json j = new Json();
    	 if (user.getProjId() == null || user.getProjId().equals(""))
         {
             j.setMsg("项目ID不能为空");
             j.setSuccess(false);
             return j;
         }
         if (user.getDeptId() == null || user.getDeptId().equals(""))
         {
             j.setMsg("部门ID不能为空");
             j.setSuccess(false);
             return j;
         }
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
         user.setId(userId);
         iOperateUserService.insertUserDept2(user,isExistManager);
    	 j.setMsg("新增部门用户成功");
    	 j.setSuccess(true);
    	 return j;
    	
    }
    
    
    
    @RequestMapping("/insertUserAndADept")
    @ResponseBody
    public Json insertUserAndADept(ImsUsersDto user, String flag, String userId,
        String deptId, String isExistManager)
    {
        Json j = new Json();

        if (flag.equals("user"))
        {
            user.setId(userId);
            Json j_user = insertUser(user);
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
            //  int tempCounts =  iImsUserService.vailUserAccount(temps);
            if (!StringUtils.isEmpty(deptId))
            {
                user.setDeptIds(deptId);
            }

            if (userId != null && !userId.equals(""))
            {
                // List<ImsUsersDto>  tempCounts=   iImsUserService.findUsers(temps);

                //	ImsUsersDto userDto = tempCounts.get(0);
                user.setId(userId);
                //user.setIsAdmin(Integer.parseInt(isAdminDept));
                Json j_dept = insertDept(user, isExistManager);
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
    public Json insertDept(ImsUsersDto user, String isExistManager)
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
        //插入部门
        iOperateUserService.insertDept(user,isExistManager);
        //插入t_ims_role_users
      //  addThreeRoles(user);
        j.setMsg("新增部门成功");
        j.setSuccess(false);
        return j;
    }

    @RequestMapping("/insertUser")
    @ResponseBody
    public Json insertUser(ImsUsersDto user)
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
        //添加用户
        String states = iOperateUserService.insertUser(user);
        if(!"200".equals(states)){
        	j.setMsg("用户添加失败，请重新添加");
        	j.setSuccess(false);
        	return j;
        }
        //添加亲加云账号
        j.setMsg("用户插入成功");
        j.setSuccess(true);
        return j;
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

    @RequestMapping("/updateUser")
    @ResponseBody
    public Json updateUser(ImsUsersDto user)
    {
        Json j = new Json();
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(user.getAge() + "");
        if (!isNum.matches())
        {
            j.setSuccess(false);
            j.setMsg("年龄不是数字");
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

        ImsUsersDto temps = new ImsUsersDto();
        temps.setUserAccount(user.getUserAccount());
        int tempCounts = iImsUserService.vailUserAccount(temps);
        ImsUsersDto imsUsers = new ImsUsersDto();
        imsUsers.setId(user.getId());
        List<ImsUsersDto> listU = iImsUserService.findUsers(imsUsers);

        if (tempCounts > 0 && !listU.get(0).getUserAccount().equals(user.getUserAccount()))
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
        user.setModifydatetime(new Date());
        iImsUserService.updateUser(user);
        j.setMsg("更新用户成功");
        j.setSuccess(true);
        return j;
    }

    @RequestMapping("/updateDept")
    @ResponseBody
    public Json updateDept(ImsUsersDto user)
    {
        Json j = new Json();

        if (user.getDeptIds() == null || user.getDeptIds().equals(""))
        {
            j.setMsg("部门不能为空");
            j.setSuccess(false);
            return j;
        }

        if (user.getProjectIds() == null || user.getProjectIds().equals(""))
        {
            j.setMsg("项目不能为空");
            j.setSuccess(false);
            return j;
        }

        String[] proj_ids = user.getProjectIds().split(",");
        String[] dept_ids = user.getDeptIds().split(",");

        if (proj_ids.length != dept_ids.length)
        {
            j.setMsg("项目和部门必须一一对应 ");
            j.setSuccess(false);
            return j;
        }
        //更新部门
        iOperateUserService.updateDept(user);
        j.setMsg("更新部门成功");
        j.setSuccess(true);
        return j;
    }

    @RequestMapping("deleteUser")
    @ResponseBody
    public Json deleteUser(ImsUsersDto imsUsersDto)
    {
        Json j = new Json();
        iImsUserService.delAllUser(imsUsersDto);;
        j.setMsg("删除用户成功");
        j.setSuccess(true);
        return j;
    }

    /**
     * 跳转到用户授权页面
     * 
     * @return
     */
    @RequestMapping("/grantPage")
    public String grantPage(String ids, HttpServletRequest request)
    {
        request.setAttribute("ids", ids);
        if (ids != null && !ids.equalsIgnoreCase("") && ids.indexOf(",") == -1)
        {
            //查询这个用户
            ImsUsersDto imsUsersDto = iImsUserService.findByUserId(ids);
            //查询这个用户的角色 
            String result = iImsUserService.findUserRole(ids);

            imsUsersDto.setRoleIds(result);
            request.setAttribute("user", imsUsersDto);
        }
        return "ims/imsUserGrant";
    }

    /**
     * 用户授权
     * 
     * @param ids
     * @return
     */
    @RequestMapping("/grant")
    @ResponseBody
    public Json grant(String ids, ImsUsersDto user)
    {
        Json j = new Json();
        iImsUserService.grant(ids, user);
        j.setSuccess(true);
        j.setMsg("授权成功！");
        return j;
    }

    /**
     * 跳转到编辑用户密码页面
     * 
     * @param id
     * @param request
     * @return
     */
    @RequestMapping("/editPwdPage")
    public String editPwdPage(String id, HttpServletRequest request)
    {

        ImsUsersDto u = iImsUserService.findByUserId(id);
        request.setAttribute("user", u);
        return "ims/imsUserEditPwd";
    }

    //更新密码
    @RequestMapping("/upPasswod")
    @ResponseBody
    public Json upPasswod(ImsUsersDto user)
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
        if (user.getId() == null || user.getId().equals(""))
        {
            j.setMsg("用户ID不能为空");
            j.setSuccess(false);
            return j;
        }
        user.setModifydatetime(new Date());
        user.setPassword(MD5Util.md5(user.getPassword()));
        iImsUserService.upPasswod(user);
        j.setMsg("更新成功");
        j.setSuccess(true);
        return j;
    }

    @RequestMapping("/addLTUser")
    @ResponseBody
    public Json addLTUser()
    {
    	System.out.println("-----开始addLTUser----");
        Json j = new Json();
        //添加全部用户
        List<ImsUsersDto> list = iImsUserService.findByProjId(null);
        System.out.println("--size--"+list.size());
        for (ImsUsersDto imsUsersDto2 : list)
        {
            LTUserDto lTUserDto = new LTUserDto();
            lTUserDto.setUserAccount(imsUsersDto2.getUserAccount());
            lTUserDto.setPassword(imsUsersDto2.getPassword());
            System.out.println(imsUsersDto2.getUserAccount()+"-----"+imsUsersDto2.getPassword());
            String result = liaoTianService.addLTUser(lTUserDto);
        }
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
}
