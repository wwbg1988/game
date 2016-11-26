/**
 * 
 */
package com.ssic.catering.base.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssic.catering.base.dao.RoleProjectDao;
import com.ssic.catering.base.dto.RoleProjectDto;
import com.ssic.catering.base.pojo.RoleProject;
import com.ssic.catering.base.service.IRoleProjectService;
import com.ssic.util.BeanUtils;
import com.ssic.util.constants.DataStatus;

/**		
 * <p>Title: RoleProjectServiceImpl </p>
 * <p>Description: 角色项目关系Serice层实现类</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 刘博	
 * @date 2015年10月26日 下午1:55:39	
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年10月26日 下午1:55:39</p>
 * <p>修改备注：</p>
 */
@Service
public class RoleProjectServiceImpl implements IRoleProjectService
{

    @Autowired
    private RoleProjectDao dao;

    /** 
    * (non-Javadoc)   
    * @see com.ssic.catering.base.service.IRoleProjectService#grantProject(com.ssic.catering.base.dto.RoleProjectDto)   
    */
    @Override
    public void grantProject(RoleProjectDto roleProjectDto)
    {
        RoleProject roleProject = new RoleProject();
        BeanUtils.copyProperties(roleProjectDto, roleProject);
        roleProject.setCreateTime(new Date());
        dao.insert(roleProject);

    }

    public List<RoleProjectDto> findAllBy(RoleProjectDto roleProjectDto)
    {
        List<RoleProjectDto> result = new ArrayList<RoleProjectDto>();
        RoleProject roleProject = new RoleProject();

        BeanUtils.copyProperties(roleProjectDto, roleProject);
        List<RoleProject> list = dao.findAllBy(roleProject, null);

        if (list != null && list.size() > 0)
        {
            result = BeanUtils.createBeanListByTarget(list, RoleProjectDto.class);
        }
        return result;
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.catering.base.service.IRoleProjectService#delete(com.ssic.catering.base.dto.RoleProjectDto)   
    */
    @Override
    public void delete(RoleProjectDto delete_dto)
    {
        RoleProject roleProject = new RoleProject();
        BeanUtils.copyProperties(delete_dto, roleProject);
        roleProject.setStat(DataStatus.DISABLED);
        dao.updateByPrimaryKeySelective(roleProject);
    }
}
