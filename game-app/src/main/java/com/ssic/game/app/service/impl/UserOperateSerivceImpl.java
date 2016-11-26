/**
 * 
 */
package com.ssic.game.app.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Objects;
import com.ssic.game.app.controller.dto.UserInfoDto;
import com.ssic.game.app.dao.UserOperateDao;
import com.ssic.game.app.service.IUserOperateSerivce;
import com.ssic.game.app.controller.dto.ProcessDto;
import com.ssic.game.app.controller.dto.ProjectDto;

/**		
 * <p>Title: UserOperateSerivceImpl </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Vincet	
 * @date 2015年6月24日 下午8:27:14	
 * @version 1.0
 */
@Service
public class UserOperateSerivceImpl implements IUserOperateSerivce
{

    @Autowired
    private UserOperateDao userOperateDao;

    /**    
    * (non-Javadoc)   
    * @see com.ssic.game.app.service.IUserOperateSerivce#login(java.lang.String, java.lang.String)   
    */
    @Override
    public UserInfoDto login(String name, String pwd)
    {
        //查询用户个人信息
        return userOperateDao.findUserInfo(name, pwd);
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.game.app.service.IUserOperateSerivce#modifyPwd(java.lang.String, java.lang.String)   
    */
    @Override
    public boolean modifyPwd(String userId, String pwd)
    {
        int count = userOperateDao.updateUserInfo(userId, pwd);
        System.err.println("return modifyPwd count:" + count);
        if (count > 0)
        {
            return true;
        }
        else
        {
            return false;
        }

    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.game.app.service.IUserOperateSerivce#queryProjectInfo(java.lang.String)   
    */
    @Override
    public List<ProjectDto> queryProjectInfo(String userId)
    {
        List<ProjectDto> listProj = userOperateDao.findProjectInfo(userId);

        if (null != listProj && listProj.size() > 0)
        {
            for (int i = 0; i < listProj.size(); i++)
            {

                ProjectDto projDto = userOperateDao.findDeptInfo(userId, listProj.get(i).getId());
                if (null != projDto)
                {   
                    listProj.get(i).setDeptId(projDto.getDeptId());
                    listProj.get(i).setDeptName(projDto.getDeptName());
                }

            }
        }

        return listProj;
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.game.app.service.IUserOperateSerivce#queryProcessInfo(java.lang.String, java.lang.String)   
    */
    @Override
    public List<ProcessDto> queryProcessInfo(String userId, String projId)
    {
        return userOperateDao.findProcessInfo(userId, projId);
    }

}
