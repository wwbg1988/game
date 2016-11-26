package com.ssic.game.common.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ssic.game.common.dao.ProjectUsersDao;
import com.ssic.game.common.dto.ProjectDto;
import com.ssic.game.common.service.IProjectUsersService;

/**		
 * <p>Title: ProjectUsersServiceImpl </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 朱振	
 * @date 2015年10月29日 上午11:48:46	
 * @version 1.0
 * <p>修改人：朱振</p>
 * <p>修改时间：2015年10月29日 上午11:48:46</p>
 * <p>修改备注：</p>
 */
@Service("ProjectUsersServiceImpl")
public class ProjectUsersServiceImpl implements IProjectUsersService
{
    private static final Logger log = Logger.getLogger(ProjectUsersServiceImpl.class);
    
    @Autowired
    private ProjectUsersDao projectUsersDao;

    /**
     * 
     * (non-Javadoc)   
     * @see com.ssic.game.common.service.IProjectUsersService#getProjectByIMSUserId(java.lang.String)
     */
    @Override
    public ProjectDto getProjectByIMSUserId(String userId)
    {
        log.info("参数userId="+userId);
        if(StringUtils.isEmpty(userId))
        {
            log.error("userId为空");
            return null;
        }
        
        return projectUsersDao.findProjectsByIMSUserId(userId);
    }

}

