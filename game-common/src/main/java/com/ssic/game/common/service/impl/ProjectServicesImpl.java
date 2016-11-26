/**
 * 
 */
package com.ssic.game.common.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.ssic.game.common.dao.ProjectDao;
import com.ssic.game.common.dto.ProjectDto;
import com.ssic.game.common.dto.ProjectUsersDto;
import com.ssic.game.common.service.ProjectServices;

/**		
 * <p>Title: ProjectServicesImpl </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年7月11日 下午4:57:01	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年7月11日 下午4:57:01</p>
 * <p>修改备注：</p>
 */
@Service("ProjectServicesImpl")
public class ProjectServicesImpl implements ProjectServices
{
    @Autowired
    private ProjectDao projectDao;

    
     /** 
     * (non-Javadoc)   
     * @see com.ssic.game.common.service.ProjectServices#findById()   
     */
    @Override
    @Cacheable(value="default", key="'game.common.dto.ProjectDto:' + #projectId")
    public ProjectDto findById(String projectId)
    {
        return projectDao.findById(projectId);
    }


	@Override
	public ProjectUsersDto findByUserID(String userId) {
		// TODO Auto-generated method stub
		return projectDao.findByUserID(userId);
	}

}

