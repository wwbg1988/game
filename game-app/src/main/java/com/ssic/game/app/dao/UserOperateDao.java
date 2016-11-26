/**
 * 
 */
package com.ssic.game.app.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssic.game.app.controller.dto.UserInfoDto;
import com.ssic.game.app.mapper.UserOperateMapper;
import com.ssic.game.app.controller.dto.ProcessDto;
import com.ssic.game.app.controller.dto.ProjectDto;

/**		
 * <p>Title: UserOperateDao </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Vincet	
 * @date 2015年6月24日 下午8:28:57	
 * @version 1.0
 * <p>修改人：Vincet</p>
 * <p>修改时间：2015年6月24日 下午8:28:57</p>
 * <p>修改备注：</p>
 */
@Repository
public class UserOperateDao
{
    @Autowired
    private UserOperateMapper userOperateMapper;

    /**             
     * login：根据用户登录帐号和密码查询用户信息
     * @param name
     * @param pwd
     * @return  
     * @exception	
     * @author Vincent
     * @date 2015年6月24日 下午9:32:15	 
     */
    public UserInfoDto findUserInfo(String name, String pwd)
    {
        return userOperateMapper.findUserInfo(name, pwd);

    }

    /**     
     * updateUserInfo：根据用户登录帐号更改密码
     * @param name
     * @param pwd
     * @return
     * @exception	
     * @author Vincent
     * @date 2015年6月24日 下午8:00:50	 
     */
    public int updateUserInfo(String userId, String pwd)
    {
        return userOperateMapper.updateUserInfo(userId, pwd);

    }

    public List<ProjectDto> findProjectInfo(String userId)
    {
        return userOperateMapper.findUserProj(userId);

    }

    public ProjectDto findDeptInfo(String userId, String projId)
    {
        return userOperateMapper.findUserDept(userId, projId);

    }   

    public List<ProcessDto> findProcessInfo(String userId, String projId)
    {

        return userOperateMapper.findUserProc(userId, projId);

    }           

}
