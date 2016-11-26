/**
 * 
 */
package com.ssic.game.common.service;

import java.util.List;

import com.ssic.game.common.dto.ActionsDto;
import com.ssic.game.common.pojo.ActionRole;
import com.ssic.game.common.pojo.Actions;

/**		
 * <p>Title: IActionService </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年7月2日 上午9:40:56	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年7月2日 上午9:40:56</p>
 * <p>修改备注：</p>
 */
public interface IActionService
{
    //角色actionVail
    public int vailActionsForRole(ActionRole actionRole);
    
    //用户actionVail
    public int vailActionsForUser(ActionRole actionRole);

    
    /**     
     * findById：一句话描述方法功能
     * @param actionId
     * @return
     * @exception	
     * @author 刘博
     * @date 2015年7月2日 上午11:08:00	 
     */
    public ActionsDto findById(String actionId);

    
    /**     
     * findAction：一句话描述方法功能
     * @param projId
     * @param procId
     * @param nowTaskId
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年7月23日 下午4:07:52	 
     */
    public ActionsDto findAction(String projId, String procId, String nowTaskId);
    
    public  List<Actions> findBy(ActionsDto actionsDto);
}

