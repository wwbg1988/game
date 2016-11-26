/**
 * 
 */
package com.ssic.game.app.service;

import java.util.List;

import com.ssic.game.app.controller.dto.UserInfoDto;
import com.ssic.game.app.controller.dto.ProcessDto;
import com.ssic.game.app.controller.dto.ProjectDto;

/**		
 * <p>Title: UserOperateSerivce </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Vincent	
 * @date 2015年6月24日 下午8:26:26	
 * @version 1.0
 */

public interface IUserOperateSerivce
{

    /**     
     * login：根据用户登录帐号和密码查询用户是否存在
     * @param name
     * @param pwd
     * @return
     * @exception	
     * @author Alex
     * @date 2015年6月24日 下午9:57:45	 
     */
    UserInfoDto login(String name, String pwd);

    /**     
     * modifyPwd：修改用户密码
     * @param name
     * @param pwd
     * @return
     * @exception	
     * @author Vincent
     * @date 2015年6月30日 下午7:57:35	 
     */
    boolean modifyPwd(String userId, String pwd);

    /**     
     * queryProjectInfo：查询用户项目信息
     * @param userId
     * @return  
     * @exception	
     * @author Vincent
     * @date 2015年7月2日 下午8:32:58	 
     */
    List<ProjectDto> queryProjectInfo(String userId);
    
    
    /**     
     * queryProcessInfo：查询用户项目流程信息
     * @param projId
     * @param userId
     * @return
     * @exception	
     * @author Alex
     * @date 2015年7月9日 上午10:20:44	 
     */
    List<ProcessDto> queryProcessInfo(String userId,String projId);
}
