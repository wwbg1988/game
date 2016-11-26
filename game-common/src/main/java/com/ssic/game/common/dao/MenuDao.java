package com.ssic.game.common.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import com.ssic.game.common.dto.MenuDto;
import com.ssic.game.common.mapper.MenuMapper;
import com.ssic.game.common.mapper.RoleMenuExMapper;
import com.ssic.game.common.pojo.Menu;
import com.ssic.game.common.pojo.MenuExample;
import com.ssic.game.common.pojo.MenuExample.Criteria;
import com.ssic.util.BeanUtils;
import com.ssic.util.constants.DataStatus;

@Repository
public class MenuDao
{

    @Autowired
    public MenuMapper mapper;
    @Autowired
    public RoleMenuExMapper eXmapper;

    /**
     * 
     * findBy：一句话描述方法功能
     * @param param
     * @return
     * @exception	
     * 修改人 朱振
     * @date 2015年8月21日 下午2:28:24
     */
    public List<Menu> findBy(Menu param)
    {
        MenuExample example = new MenuExample();
        Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty(param.getProjId()))
        {
            criteria.andProjIdEqualTo(param.getProjId());
        }
        if (!StringUtils.isEmpty(param.getId()))
        {
            criteria.andIdEqualTo(param.getId());
        }
        if (!StringUtils.isEmpty(param.getProcId()))
        {
            criteria.andProcIdEqualTo(param.getProcId());
        }
        if (!StringUtils.isEmpty(param.getPid()))
        {
            criteria.andPidEqualTo(param.getPid());
        }
        criteria.andStatEqualTo(DataStatus.ENABLED);
        return mapper.selectByExample(example);
    }

    public Menu findById(String id)
    {
        return mapper.selectByPrimaryKey(id);
    }

    public void insertMenu(Menu param)
    {
        mapper.insert(param);
    }

    public void updateMenu(Menu param)
    {
        mapper.updateByPrimaryKeySelective(param);
    }

    public List<MenuDto> findByroleId(String roleId)
    {
        return eXmapper.findByroleId(roleId);
    }

    /**     
     * findByProjectId：一句话描述方法功能
     * @param projectId
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年8月14日 上午9:48:05	 
     */
    public List<MenuDto> findByProjectId(String projectId)
    {
        // TODO 添加方法注释
        return eXmapper.findByProjectId(projectId);
    }

    /**
     *
     * findAll：查询所有的菜单
     * @return
     * @exception	
     * @author 朱振
     * @date 2015年8月20日 下午3:17:39
     */
    public List<MenuDto> findAll()
    {
        List<Menu> menuList = mapper.selectByExample(null);

        if (menuList != null && menuList.size() > 0)
        {
            return BeanUtils.createBeanListByTarget(menuList, MenuDto.class);
        }

        return null;
    }

    /**     
     * findChildListByParentId：一句话描述方法功能
     * @param parentId
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年12月2日 下午3:45:56	 
     */
    public List<Menu> findChildListByParentId(String parentId)
    {
        MenuExample example = new MenuExample();
        Criteria criteria = example.createCriteria();
        example.setDistinct(true);
        criteria.andStatEqualTo(DataStatus.ENABLED);
        if (!StringUtils.isEmpty(parentId))
        {
            criteria.andPidEqualTo(parentId);
        }
        return mapper.selectByExample(example);
    }

}
