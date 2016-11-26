package com.ssic.game.admin.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.ssic.game.admin.dto.TImsMenuDto;
import com.ssic.game.admin.mapper.TImsMenuExMapper;
import com.ssic.game.admin.mapper.TImsMenuTypeExMapper;
import com.ssic.game.admin.mapper.TImsRoleMenuExMapper;
import com.ssic.game.admin.mapper.TImsUserRoleExMapper;
import com.ssic.game.admin.mapper.TImsUsersExMapper;
import com.ssic.game.admin.pageModel.SessionInfo;
import com.ssic.game.admin.pojo.Menu;
import com.ssic.game.admin.pojo.MenuAndRoles;
import com.ssic.game.admin.pojo.MenuType;
import com.ssic.game.admin.util.MyComparator;
import com.ssic.util.BeanUtils;
import com.ssic.util.UUIDGenerator;

@Repository
public class TImsMenuDao
{

    @Autowired
    private TImsMenuExMapper tImsMenuExMapper;
    @Autowired
    private TImsMenuTypeExMapper tImsMenuTypeExMapper;

    @Autowired
    private TImsUserRoleExMapper tImsUserRoleExMapper;

    @Autowired
    private TImsRoleMenuExMapper tImsRoleMenuExMapper;

    @Autowired
    private TImsUsersExMapper tImsUserExMapper;

    public List<TImsMenuDto> treeGrid(SessionInfo sessionInfo)
    {
        List<TImsMenuDto> menuDtoList = new ArrayList<TImsMenuDto>();
        List<Menu> menuList = new ArrayList<Menu>();
   
        String menuId = "";
        if (sessionInfo != null)
        {

        
            menuList = tImsMenuExMapper.findByUserId(sessionInfo.getId());
        }

  
        
        if (!CollectionUtils.isEmpty(menuList))
        {
            for (Menu m : menuList)
            {

            
                if (m != null)
                {
                    TImsMenuDto r = new TImsMenuDto();
                    BeanUtils.copyProperties(m, r);
                    if (!StringUtils.isEmpty(m.getPid()))
                    {
                        r.setPid(m.getPid());
                        Menu parentMenu = tImsMenuExMapper.findById(m.getPid());
                        r.setPname(parentMenu.getName());
                    }
                    r.setMenuTypeId(m.getMenuTypeId());
                    MenuType menuType = tImsMenuTypeExMapper.findById(m.getMenuTypeId());
                    r.setMenuTypeName(menuType.getName());
                    if (!StringUtils.isEmpty(m.getIcon()))
                    {
                        r.setIconCls(m.getIcon());
                    }
                    menuDtoList.add(r);
                }
            }
        }

        Collections.sort(menuDtoList, new MyComparator());

        return menuDtoList;
    }

    public List<TImsMenuDto> getTree(String id)
    {
        List<TImsMenuDto> menuDtoList = new ArrayList<TImsMenuDto>();
        List<Menu> menuList = new ArrayList<Menu>();

        menuList = tImsMenuExMapper.findByUserId(id);
       
        if (!CollectionUtils.isEmpty(menuList))
        {
            for (Menu m : menuList)
            {

            
                if (m != null)
                {
                    TImsMenuDto r = new TImsMenuDto();
                    BeanUtils.copyProperties(m, r);
                    if (!StringUtils.isEmpty(m.getPid()))
                    {
                        r.setPid(m.getPid());
                        Menu parentMenu = tImsMenuExMapper.findById(m.getPid());
                        r.setPname(parentMenu.getName());
                    }
                    r.setMenuTypeId(m.getMenuTypeId());
                    MenuType menuType = tImsMenuTypeExMapper.findById(m.getMenuTypeId());
                    r.setMenuTypeName(menuType.getName());
                    if (!StringUtils.isEmpty(m.getIcon()))
                    {
                        r.setIconCls(m.getIcon());
                    }
                    menuDtoList.add(r);
                }
            }
        }

        return menuDtoList;
    }

    public void deleteById(String id)
    {
        List<Menu> menus = tImsMenuExMapper.findChildMenuById(id);
        if (menus.size() > 0)
        {
            // 如果该菜单有子对象，则把所有子对象的stat设置为0
            for (Menu menu : menus)
            {
                tImsMenuExMapper.updateDeleteStat(menu.getId());
                tImsRoleMenuExMapper.updateRoleMenu(menu.getId());
            }
        }
        // 删除该菜单对象(stat更新为0);
        tImsMenuExMapper.updateDeleteStat(id);
        //删除角色菜单对象(stat更新0)
        tImsRoleMenuExMapper.updateRoleMenu(id);
    }

    public void insertBy(TImsMenuDto menuDto, SessionInfo sessionInfo)
    {

        if (menuDto != null)
        {
            Menu menu = new Menu();
            BeanUtils.copyProperties(menuDto, menu);
            menu.setCreateTime(new Date());
            menu.setStat(1);
            tImsMenuExMapper.insertBy(menu);
            // 由于当前用户所属的角色，没有访问新添加的资源权限，所以在新添加资源的时候，将当前资源授权给当前用户的所有角色，以便添加资源后在资源列表中能够找到
            if (sessionInfo.getId() != null)
            {
                List<String> roles = tImsUserRoleExMapper.findRoleBy(sessionInfo.getId());

                for (String roleId : roles)
                {
                    String pkId = UUIDGenerator.getUUID();
                    tImsRoleMenuExMapper.insertBy(pkId, roleId, menu.getId());
                }
            }
        }
    }

    public TImsMenuDto findById(String id)
    {
        if (id != null)
        {
            TImsMenuDto r = new TImsMenuDto();
            Menu menu = tImsMenuExMapper.findById(id);
            BeanUtils.copyProperties(menu, r);
            return r;
        }
        return null;
    }

    public void editMenu(TImsMenuDto menuDto)
    {
        Menu menu = tImsMenuExMapper.findById(menuDto.getId());
        if (menu != null)
        {
            BeanUtils.copyProperties(menuDto, menu);
            tImsMenuExMapper.updateMenu(menu);
        }

    }

}
