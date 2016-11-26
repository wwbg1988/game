/**
 * 
 */
package com.ssic.game.common.service.impl;

import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssic.game.common.dao.ImsRolesDao;
import com.ssic.game.common.pojo.ImsRole;
import com.ssic.game.common.service.IRolesService;

/**		
 * <p>Title: RoleServiceImpl </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年7月2日 上午9:46:51	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年7月2日 上午9:46:51</p>
 * <p>修改备注：</p>
 */
@Service
public class RolesServiceImpl implements IRolesService
{
    @Autowired
    @Getter
    private ImsRolesDao imsRolesDao;

    
     /** 
     * (non-Javadoc)   
     * @see com.ssic.game.common.service.IRolesService#findRole(com.ssic.game.common.pojo.ImsRole)   
     */
    @Override
    public List<ImsRole> findRole(ImsRole imsRole)
    {
        
        return imsRolesDao.findAllBy(imsRole);
    }


    
     /** 
     * (non-Javadoc)   
     * @see com.ssic.game.common.service.IRolesService#findById(java.lang.String)   
     */
    @Override
    public ImsRole findById(String id)
    {
        // TODO 添加方法注释
        return imsRolesDao.selectByPrimaryKey(id);
    }

}

