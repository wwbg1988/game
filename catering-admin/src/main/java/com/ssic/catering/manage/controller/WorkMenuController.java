package com.ssic.catering.manage.controller;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssic.catering.admin.pageModel.Json;
import com.ssic.catering.admin.service.UserServiceI;
import com.ssic.game.common.dto.MenuDto;
import com.ssic.game.common.dto.ProjectDto;
import com.ssic.game.common.pageModel.Tree;
import com.ssic.game.common.service.IMenuService;
import com.ssic.game.common.service.IRoleMenuService;

/**
 * 		
 * <p>Title: WorkMenuController </p>
 * <p>Description: 类描述<br>工作平台菜单</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 朱振	
 * @date 2015年8月18日 上午11:34:31	
 * @version 1.0
 * <p>修改人：朱振</p>
 * <p>修改时间：2015年8月18日 上午11:34:31</p>
 * <p>修改备注：</p>
 */
@Controller
@RequestMapping("/workMenuController")
public class WorkMenuController
{

    @Autowired
    private IMenuService menuService;

    @Autowired
    private IRoleMenuService iRoleMenuService;
    @Autowired
    private UserServiceI userServiceI;

    /**
     * 
     * workMenu：跳转到工作平台菜单管理页面
     * @return
     * @exception	
     * @author 朱振
     * @date 2015年8月18日 上午11:35:42
     */
    @RequestMapping(value = "/manager", method = {RequestMethod.GET, RequestMethod.POST })
    public String workMenu()
    {
        return "carte/workPlat/menu";
    }

    /**
     * 
     * treeGrid：返回工作平台菜单中的所有数据
     * @return
     * @exception	
     * @author 朱振
     * @date 2015年8月18日 上午11:36:36
     */
    @ResponseBody
    @RequestMapping(value = "/treeGrid", method = {RequestMethod.GET, RequestMethod.POST })
    public List<MenuDto> treeGrid(HttpSession session)
    {

        //获取用户拥有的项目权限
        List<ProjectDto> listProject = userServiceI.getProjectBySession(session);
        String projId = "";
        if (!CollectionUtils.isEmpty(listProject))
        {
            if (listProject.size() > 1)
            {//超管 
                projId = null;
            }
            else
            {
                projId = listProject.get(0).getId();
            }
        }
        MenuDto menu = new MenuDto();
        menu.setProjId(projId);
        //当findBy的参数对象中的所有属性值为空时可以查询所有的数据
        return menuService.findBy(menu);
    }

    /**
     * updateMenu：添加菜单页面
     * @return
     * @exception	
     * @author yuanbin
     * @date 2015年8月18日 下午13:36:36
     */
    @RequestMapping("/addPage")
    public String addPage(HttpServletRequest request)
    {
        return "carte/workPlat/cateringMenuAdd";
    }

    /**
     * updateMenu：添加菜单
     * @return
     * @exception	
     * @author yuanbin
     * @date 2015年8月18日 下午13:36:36
     */
    @RequestMapping("/insertMenu")
    @ResponseBody
    public Json insertMenu(MenuDto menuDto, HttpSession session)
    {
        Json j = new Json();
        //获取用户拥有的项目权限
        List<ProjectDto> listProject = userServiceI.getProjectBySession(session);
        String projId = "";
        if (!CollectionUtils.isEmpty(listProject))
        {
            if (listProject.size() > 1)
            {//超管 
                projId = null;
            }
            else
            {
                projId = listProject.get(0).getId();
            }
            menuDto.setProjId(projId);
        }
        if (menuDto.getName() == null || menuDto.getName().equals(""))
        {
            j.setMsg("菜单名称不能为空");
            j.setSuccess(false);
            return j;
        }
        if (menuDto.getName().length() > 30)
        {
            j.setMsg("菜单名称长度不能大于30");
            j.setSuccess(false);
            return j;
        }
        menuDto.setId(UUID.randomUUID().toString());
        menuService.insertMenu(menuDto);
        j.setMsg("菜单添加成功");
        j.setSuccess(true);
        j.setObj(menuDto);
        return j;

    }

    /**
     * updateMenu：修改菜单
     * @return
     * @exception	
     * @author yuanbin
     * @date 2015年8月18日 下午13:36:36
     */
    @RequestMapping("/updateMenu")
    @ResponseBody
    public Json updateMenu(MenuDto menuDto, HttpSession session)
    {
        Json j = new Json();
        //获取用户拥有的项目权限
        List<ProjectDto> listProject = userServiceI.getProjectBySession(session);
        if (!CollectionUtils.isEmpty(listProject))
        {
            if (listProject.size() > 1)
            {//超管 
                menuDto.setProjId(null);
            }
            else
            {
                menuDto.setProjId(listProject.get(0).getId());
            }
        }
        if (menuDto.getName() == null || menuDto.getName().equals(""))
        {
            j.setMsg("菜单名称不能为空");
            j.setSuccess(false);
            return j;
        }
        if (menuDto.getName().length() > 30)
        {
            j.setMsg("菜单名称长度不能大于30");
            j.setSuccess(false);
            return j;
        }

        menuService.updateMenu(menuDto);

        j.setMsg("菜单更新成功");
        j.setSuccess(true);
        return j;
    }

    //修改跳转页面
    @RequestMapping("/editPage")
    public String editProcess(HttpServletRequest request, String id)
    {
        MenuDto menuDto = new MenuDto();
        MenuDto rDto = new MenuDto();
        menuDto.setId(id);
        List<MenuDto> listMenu = menuService.findBy(menuDto);
        if (listMenu != null && listMenu.size() > 0)
        {
            rDto = listMenu.get(0);
        }
        request.setAttribute("rDto", rDto);

        return "carte/workPlat/cateringMenuEdit";
    }

    /**
     * updateMenu：删除菜单
     * @return
     * @exception	
     * @author yuanbin
     * @date 2015年8月18日 下午13:36:36
     */
    @RequestMapping("/deleteMenu")
    @ResponseBody
    public Json deleteMenu(MenuDto menuDto)
    {
        Json j = new Json();
        if (menuDto.getId() == null || menuDto.getId().equals(""))
        {
            j.setMsg("删除对象不能为空");
            j.setSuccess(false);
            return j;
        }
        MenuDto menuDt = menuService.findById(menuDto.getId());
        menuService.deleteMenu(menuDt);
        j.setMsg("删除菜单成功");
        j.setSuccess(true);
        return j;
    }

    @RequestMapping("/tree")
    @ResponseBody
    public List<Tree> tree(String menuId, HttpSession session)
    {
        //获取用户拥有的项目权限
        List<ProjectDto> listProject = userServiceI.getProjectBySession(session);
        String projId = "";
        if (!CollectionUtils.isEmpty(listProject))
        {
            if (listProject.size() > 1)
            {//超管 
                projId = null;
            }
            else
            {
                projId = listProject.get(0).getId();
            }
        }
        List<Tree> treeList = menuService.AllTree(menuId, projId);
        return treeList;

    }

}
