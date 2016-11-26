/**
 * 
 */
package com.ssic.game.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ssic.game.app.controller.dto.UserInfoDto;
import com.ssic.game.app.controller.dto.ProcessDto;
import com.ssic.game.app.controller.dto.ProjectDto;

/**		
 * <p>Title: UserOperateMapper </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Vincent	
 * @date 2015年6月24日 下午9:14:25	
 * @version 1.0
 * <p>修改人：Vincent</p>
 * <p>修改时间：2015年6月24日 下午9:14:25</p>
 * <p>修改备注：</p>
 */
public interface UserOperateMapper
{
    
    /**     
     * findUserInfo：根据用户帐号密码查询用户信息
     * @param userAccount
     * @param password
     * @return
     * @exception	
     * @author Vincent
     * @date 2015年6月30日 下午8:52:34	 
     */
    UserInfoDto findUserInfo(@Param("userAccount") String userAccount, @Param("password") String password);

    
    /**     
     * updateUserInfo：更改密码
     * @param userAccount
     * @param password
     * @return
     * @exception	
     * @author Vincent
     * @date 2015年6月30日 下午8:52:41	 
     */
    int updateUserInfo(@Param("userId") String userId, @Param("password") String password);
    
    
    /**     
     * findUserProj：查询用户项目信息
     * @param userAccount
     * @param password
     * @return
     * @exception	
     * @author Vincent
     * @date 2015年7月3日 上午9:55:55	 
     */
    List<ProjectDto> findUserProj(@Param("userId") String userId);
    
    
    /**     
     * findUserDept：查询用户部门信息
     * @param userId
     * @return
     * @exception	
     * @author Vincent
     * @date 2015年7月9日 下午8:00:10	 
     */     
    ProjectDto findUserDept(@Param("userId") String userId,@Param("projId") String projId);
    
    
    /**     
     * findUserProc：查询用户流程信息
     * @param userAccount
     * @param password
     * @return
     * @exception	
     * @author Vincent
     * @date 2015年7月3日 上午9:55:58	 
     */
    List<ProcessDto> findUserProc(@Param("userId") String userId, @Param("projId") String projId);
    
            
}
