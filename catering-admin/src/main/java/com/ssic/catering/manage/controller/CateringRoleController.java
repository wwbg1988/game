package com.ssic.catering.manage.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssic.catering.admin.pageModel.DataGrid;
import com.ssic.catering.admin.pageModel.Json;
import com.ssic.catering.admin.pageModel.Role;
import com.ssic.catering.admin.pageModel.SessionInfo;
import com.ssic.catering.admin.service.UserServiceI;
import com.ssic.catering.admin.util.ConfigUtil;
import com.ssic.catering.common.util.CateringProject;
import com.ssic.game.common.dto.MenuDto;
import com.ssic.game.common.dto.ProjectDto;
import com.ssic.game.common.dto.RoleMenuDto;
import com.ssic.game.common.dto.RoleUsersDto;
import com.ssic.game.common.dto.TmsRoleDto;
import com.ssic.game.common.pageModel.Tree;
import com.ssic.game.common.service.IMenuService;
import com.ssic.game.common.service.IRoleMenuService;
import com.ssic.game.common.service.ITmsRoleservice;
import com.ssic.util.StringUtils;
import com.ssic.util.UUIDGenerator;

@Controller
@RequestMapping("/cateringRoleController")
public class CateringRoleController
{
    @Autowired
    private ITmsRoleservice iTmsRoleservice;

    @Autowired
    private IRoleMenuService iRoleMenuService;

    @Autowired
    private IMenuService iMenuService;
    @Autowired
    private UserServiceI userService;
    protected static final Log logger = LogFactory.getLog(CateringRoleController.class);

    @RequestMapping("/findBy")
    @ResponseBody
    public List<TmsRoleDto> findBy(TmsRoleDto tmsRoleDto)
    {
        return iTmsRoleservice.findBy(tmsRoleDto);
    }

    @RequestMapping("/insertRole")
    @ResponseBody
    public Json insertRole(TmsRoleDto tmsRoleDto)
    {
        Json j = new Json();
        if (tmsRoleDto.getName() == null || tmsRoleDto.getName().equals(""))
        {
            j.setMsg("角色名称不能为空");
            j.setSuccess(false);
            return j;
        }
        if (tmsRoleDto.getName().length() > 30)
        {
            j.setMsg("角色名称长度不能大于30");
            j.setSuccess(false);
            return j;
        }

        if (tmsRoleDto.getDescribes() != null)
        {
            if (tmsRoleDto.getDescribes().length() > 100)
            {
                j.setMsg("描述长度不能大于100");
                j.setSuccess(false);
                return j;
            }

        }
        if (tmsRoleDto.getProjId() == null || tmsRoleDto.getProjId().equals(""))
        {
            j.setMsg("项目不能为空");
            j.setSuccess(false);
            return j;
        }

        tmsRoleDto.setId(UUIDGenerator.getUUID());
        tmsRoleDto.setStat(1);
        // tmsRoleDto.setProjId(cateringProjId);
        iTmsRoleservice.insertRole(tmsRoleDto);

        j.setMsg("创建角色成功");
        j.setSuccess(true);

        return j;
    }

    @RequestMapping("/updateRole")
    @ResponseBody
    public Json updateRole(TmsRoleDto tmsRoleDto)
    {
        Json j = new Json();

        if (tmsRoleDto.getName() == null || tmsRoleDto.getName().equals(""))
        {
            j.setMsg("角色名称不能为空");
            j.setSuccess(false);
            return j;
        }
        if (tmsRoleDto.getName().length() > 30)
        {
            j.setMsg("角色名称长度不能大于30");
            j.setSuccess(false);
            return j;
        }

        if (tmsRoleDto.getDescribes() != null)
        {
            if (tmsRoleDto.getDescribes().length() > 100)
            {
                j.setMsg("描述长度不能大于100");
                j.setSuccess(false);
                return j;
            }

        }

        iTmsRoleservice.updateRole(tmsRoleDto);

        j.setMsg("角色更新成功");
        j.setSuccess(true);
        return j;
    }

    @RequestMapping("/deleteRole")
    @ResponseBody
    public Json deleteRole(TmsRoleDto tmsRoleDto)
    {
        Json j = new Json();
        if (tmsRoleDto.getId() == null || tmsRoleDto.getId().equals(""))
        {
            j.setMsg("删除对象不能为空");
            j.setSuccess(false);
            return j;
        }
        iTmsRoleservice.deleteRole(tmsRoleDto);
        j.setMsg("删除角色成功");
        j.setSuccess(true);
        return j;
    }

    @ResponseBody
    @RequestMapping("/dataGrid")
    public DataGrid dataGrid(TmsRoleDto tmsRoleDto, com.ssic.game.common.pageModel.PageHelper ph,
        HttpSession session)
    {
        List<ProjectDto> pjds = userService.getProjectBySession(session);
        if (CollectionUtils.isEmpty(pjds))
        {
            logger.error("该用户没有对应的projectId");

        }
        if (!CollectionUtils.isEmpty(pjds))
        {
            if (pjds.size() > 1)
            {//超管
                tmsRoleDto.setProjId(null);
            }
            else
            {
                tmsRoleDto.setProjId(pjds.get(0).getId());

            }
        }
        DataGrid dataGrid = new DataGrid();
        List<TmsRoleDto> list = iTmsRoleservice.findByAll(tmsRoleDto, ph);
        int count = iTmsRoleservice.findCount(tmsRoleDto);
        if (list != null && list.size() > 0)
        {
            for (TmsRoleDto roleDtp : list)
            {
                String roleId = roleDtp.getId();
                RoleUsersDto ruDto = new RoleUsersDto();
                ruDto.setRoleId(roleId);
                List<RoleUsersDto> listru = iTmsRoleservice.findUserNames(ruDto);
                String user_ids = "";
                if (listru != null && listru.size() > 0)
                {
                    for (RoleUsersDto roleUsersDto : listru)
                    {
                        user_ids = user_ids + roleUsersDto.getUserNames() + ",";
                    }
                    user_ids = user_ids.substring(0, user_ids.length() - 1);
                }
                roleDtp.setUserNames(user_ids);
            }
        }
        dataGrid.setRows(list);
        dataGrid.setTotal(Long.valueOf(count));
        return dataGrid;
    }

    @RequestMapping("/manager")
    public String manager()
    {
        return "carte/role/cateringRole";
    }

    @RequestMapping("/addPage")
    public String addPage(HttpServletRequest request, String projid)
    {
        request.setAttribute("projid", projid);
        return "carte/role/cateringRoleAdd";
    }

    @RequestMapping("/editRole")
    public String editProcess(HttpServletRequest request, String id)
    {
        TmsRoleDto tmsRoleDto = new TmsRoleDto();
        TmsRoleDto rDto = new TmsRoleDto();
        tmsRoleDto.setId(id);
        List<TmsRoleDto> listrole = iTmsRoleservice.findBy(tmsRoleDto);
        if (listrole != null && listrole.size() > 0)
        {
            rDto = listrole.get(0);
        }
        request.setAttribute("rDto", rDto);

        return "carte/role/cateringRoleEdit";
    }

    @RequestMapping("/grantUserPage")
    public String grantUserPage(String ids, HttpServletRequest request, HttpSession session)
    {
        request.setAttribute("ids", ids);
        session.setAttribute("grantUserPerss", ids);
        Role u = new Role();
        if (ids != null && !ids.equalsIgnoreCase(""))
        {
            String result = iTmsRoleservice.findUserPers(ids);
            u.setResourceIds(result);
            request.setAttribute("role", u);
        }
        return "carte/role/cateringRoleGrant";
    }

    /**
     * 角色树(只能看到自己拥有的角色)
     * 
     * @return
     */
    @RequestMapping("/userTree")
    @ResponseBody
    public List<com.ssic.game.common.pageModel.Tree> menusTree(String searchName, HttpSession session)
    {
        List<ProjectDto> pjds = userService.getProjectBySession(session);
        if (CollectionUtils.isEmpty(pjds))
        {
            logger.error("该用户没有对应的projectId");

        }

        return iTmsRoleservice.cateringUserTree(searchName, pjds);
    }

    /**
     * 用户授权
     * 
     * @param ids
     * @return
     */
    @RequestMapping("/grantUsers")
    @ResponseBody
    public Json grantUsers(String resourceIds, HttpSession session)
    {
        Json j = new Json();

        if (session.getAttribute("grantUserPerss") != null)
        {

            iTmsRoleservice.grantUser(session.getAttribute("grantUserPerss").toString(), resourceIds);

            j.setSuccess(true);
            j.setMsg("授权成功！");
            return j;
        }
        else
        {
            j.setSuccess(false);
            j.setMsg("寻找不到动作id，请重新赋用户权限!");
            return j;
        }
    }

    /**
     * 角色树(只能看到自己拥有的角色)
     * 
     * @return
     */
    @RequestMapping("/tree")
    @ResponseBody
    public List<com.ssic.game.common.pageModel.Tree> tree(HttpSession session, String userId)
    {
        SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.SESSIONINFONAME);
        return iTmsRoleservice.tree(userId);
        //return roleService.tree(sessionInfo);
    }

    /**
     * 角色绑定菜单页面
     * 
     * @return
     * 
     * @author yuanbin
     */
    @RequestMapping("/grantMenuPage")
    public String grantMenuPage(String id, HttpServletRequest request, HttpSession session)
    {

        if (!StringUtils.isEmpty(id))
        {
            Role r = new Role();

            RoleMenuDto roleMenuDto = new RoleMenuDto();
            roleMenuDto.setRoleId(id);
            roleMenuDto.setStat(1);

            List<RoleMenuDto> rMenuDtoList = iRoleMenuService.findBy(roleMenuDto);
            TmsRoleDto roleDto = iTmsRoleservice.findById(id);

            if (roleMenuDto != null)
            {
                r.setId(id);
                r.setName(roleDto.getName());

                if (rMenuDtoList != null && !rMenuDtoList.isEmpty())
                {
                    boolean b = false;
                    String ids = "";
                    for (RoleMenuDto rd : rMenuDtoList)
                    {
                        if (b)
                        {
                            ids += ",";
                        }
                        else
                        {
                            b = true;
                        }
                        ids += rd.getMenuId();
                        //names += "新闻";
                    }
                    r.setResourceIds(ids);
                    //r.setResourceNames(names);
                }
            }

            request.setAttribute("role", r);
        }
        return "carte/role/cateringMenuGrant";
    }

    /**
     * 角色树(只能看到自己拥有的角色)
     * 
     * @return
     * 
     * @author yuanbin
     */

    @RequestMapping("/allTree")
    @ResponseBody
    public List<com.ssic.game.common.pageModel.Tree> menuTree(String searchName, HttpSession session)
    {
        List<ProjectDto> listProject = userService.getProjectBySession(session);
        if (!CollectionUtils.isEmpty(listProject))
        {
            if (listProject.size() > 1)
            {//超管 
                return iMenuService.AllTree();
            }
            else
            {
                return getByListMenu(listProject.get(0).getId());
            }
        }
        return iMenuService.AllTree();

    }

    /**
     * grantMenus：绑定菜单
     * 
     * @param role
     * @return
     * 
     * @author yuanbin
     * 
     * <p>
     * 修改人朱振
     * <p>     * 
     * @date 2015年8月21日 上午10:15:18
     */
    @RequestMapping("/grantMenus")
    @ResponseBody
    public Json grantMenus(Role role)
    {
        Json j = new Json();

        iRoleMenuService.grantMenu(role.getId(), role.getResourceIds());
        j.setMsg("授权成功！");
        j.setSuccess(true);
        return j;
    }

    private List<Tree> getByListMenu(String projId)
    {
        List<Tree> lt = new ArrayList<Tree>();
        List<MenuDto> allTrees = iMenuService.findByProjectId(projId);
        if (allTrees != null && allTrees.size() > 0)
        {
            for (MenuDto menuDto : allTrees)
            {
                Tree tree = new Tree();

                if (!StringUtils.isEmpty(menuDto.getPid()))
                {
                    tree.setPid(menuDto.getPid());
                }

                tree.setId(menuDto.getId());
                tree.setText(menuDto.getRemark());

                tree.setIconCls("status_online");

                lt.add(tree);
            }
        }
        return lt;
    }
}
