package com.ssic.game.common.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ssic.game.common.dao.MenuDao;
import com.ssic.game.common.dao.ProjectDao;
import com.ssic.game.common.dto.MenuDto;
import com.ssic.game.common.dto.ProjectDto;
import com.ssic.game.common.pageModel.Tree;
import com.ssic.game.common.pojo.Menu;
import com.ssic.game.common.service.IMenuService;
import com.ssic.game.common.service.IParamConfigService;
import com.ssic.util.BeanUtils;
import com.ssic.util.constants.DataStatus;

@Service
public class MenuServiceImpl implements IMenuService
{

    @Autowired
    private MenuDao menuDao;
    @Autowired
    private IParamConfigService paramConfigService;
    @Autowired
    private ProjectDao projectDao;

    @Override
    public List<MenuDto> findBy(MenuDto menuDto)
    {

        //属性stat默认情况下为1	  
        if (menuDto != null && menuDto.getStat() == null)
        {
            menuDto.setStat(1);
        }

        // TODO Auto-generated method stub
        Menu menu = new Menu();
        BeanUtils.copyProperties(menuDto, menu);
        List<Menu> list = menuDao.findBy(menu);
        List<MenuDto> listdto = BeanUtils.createBeanListByTarget(list, MenuDto.class);
        for (MenuDto dto : listdto)
        {
            if (!StringUtils.isEmpty(dto.getProjId()))
            {
                ProjectDto project = projectDao.findById(dto.getProjId());
                dto.setProjName(project.getProjName());
            }
            dto.setIconCls("rainbow");
        }
        return listdto;
    }

    @Override
    public MenuDto findById(String id)
    {
        Menu menu = menuDao.findById(id);
        MenuDto menuDto = new MenuDto();
        BeanUtils.copyProperties(menu, menuDto);
        return menuDto;
    }

    @Override
    public void insertMenu(MenuDto menuDto)
    {
        // TODO Auto-generated method stub
        Menu menu = new Menu();
        BeanUtils.copyProperties(menuDto, menu);
        menu.setCreateTime(new Date());
        menu.setStat(DataStatus.ENABLED);
        menuDao.insertMenu(menu);
    }

    @Override
    public void updateMenu(MenuDto menuDto)
    {
        // TODO Auto-generated method stub
        Menu menu = new Menu();
        BeanUtils.copyProperties(menuDto, menu);
        menuDao.updateMenu(menu);
    }

    @Override
    public void deleteMenu(MenuDto menuDto)
    {
        // TODO Auto-generated method stub
        Menu menu = new Menu();
        BeanUtils.copyProperties(menuDto, menu);
        menu.setStat(DataStatus.DISABLED);
        menuDao.updateMenu(menu);
    }

    @Override
    public List<MenuDto> findByroleId(String roleId)
    {
        return menuDao.findByroleId(roleId);
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.game.common.service.IMenuService#findByProjectId()   
    */
    @Override
    public List<MenuDto> findByProjectId(String projectId)
    {
        // TODO 添加方法注释
        return menuDao.findByProjectId(projectId);
    }

    /**
     * 
     * (non-Javadoc)   
     * @see com.ssic.game.common.service.IMenuService#tree()
     */
    @Override
    public List<Tree> AllTree()
    {
        List<Tree> lt = new ArrayList<Tree>();
        List<MenuDto> allTrees = menuDao.findAll();
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

    /** 
    * (non-Javadoc)   
    * @see com.ssic.game.common.service.IMenuService#AllTree(java.lang.String)   
    */
    @Override
    public List<Tree> AllTree(String menuId, String projId)
    {
        List<Tree> lt = new ArrayList<Tree>();
        List<MenuDto> allTrees = menuDao.findAll();
        List<Menu> filterDtoList = new ArrayList<Menu>();
        Map<String, MenuDto> maps = new HashMap<String, MenuDto>();
        if (allTrees != null && allTrees.size() > 0)
        {

            for (MenuDto menuDto : allTrees)
            {
                if (!StringUtils.isEmpty(projId) && menuDto.getProjId().equals(projId)
                    && menuDto.getStat() == 1)
                {
                    maps.put(menuDto.getId(), menuDto);
                }
            }
            if (!StringUtils.isEmpty(menuId))
            {
                Menu localMenu = menuDao.findById(menuId);
                //查找过滤不包括当前deptId的集合;
                List<Menu> needFilterList = findChild(menuId, filterDtoList);
                needFilterList.add(localMenu);
                for (Menu dto : needFilterList)
                {
                    if (maps.containsKey(dto.getId()))
                    {
                        maps.remove(dto.getId());
                    }
                }

            }
            if (!maps.isEmpty())
            {
                for (String id : maps.keySet())
                {
                    Menu menu = menuDao.findById(id);
                    Tree tree = new Tree();
                    BeanUtils.copyProperties(menu, tree);
                    if (!StringUtils.isEmpty(menu.getPid()))
                    {
                        tree.setPid(menu.getPid());
                    }
                    tree.setText(menu.getName());
                    tree.setIconCls("rainbow");
                    lt.add(tree);
                }
            }
        }
        return lt;
    }

    public List<Menu> findChild(String parentId, List<Menu> resultList)
    {
        List<Menu> childList = menuDao.findChildListByParentId(parentId);
        if (!CollectionUtils.isEmpty(childList))
        {
            for (Menu dto : childList)
            {
                resultList.add(dto);
                findChild(dto.getId(), resultList);
            }
        }
        return resultList;
    }

}
