/**
 * 
 */
package com.ssic.game.common.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ssic.game.common.dao.ActionsDao;
import com.ssic.game.common.dto.ActionsDto;
import com.ssic.game.common.pojo.ActionRole;
import com.ssic.game.common.pojo.Actions;
import com.ssic.game.common.service.IActionService;
import com.ssic.game.common.util.DataStatusUtils;
import com.ssic.util.BeanUtils;

/**		
 * <p>Title: ActionServiceImpl </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年7月2日 上午9:41:26	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年7月2日 上午9:41:26</p>
 * <p>修改备注：</p>
 */
@Service
public class ActionServiceImpl implements IActionService
{

    @Autowired
    private ActionsDao actionsDao;

    /** 
    * (non-Javadoc)   
    * @see com.ssic.game.common.service.IActionService#vailActionsForRole(com.ssic.game.common.pojo.ActionRole)   
    */
    @Override
    public int vailActionsForRole(ActionRole actionRole)
    {

        return 0;
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.game.common.service.IActionService#vailActionsForUser(com.ssic.game.common.pojo.ActionRole)   
    */
    @Override
    public int vailActionsForUser(ActionRole actionRole)
    {
        return 0;
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.game.common.service.IActionService#findById(java.lang.String)   
    */
    @Override
  /*  @Cacheable(value = "default", key = "'game.common.dto.ActionsDto:' + #actionId")*/
    public ActionsDto findById(String actionId)
    {
        Actions actions = actionsDao.selectByPrimaryKey(actionId);
        if (isNotExist(actions))
        {
            return null;
        }
        return BeanUtils.createBeanByTarget(actions, ActionsDto.class);
    }

    private boolean isNotExist(Actions actions)
    {
        return actions == null || DataStatusUtils.isNotEnabled(actions.getStat());
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.game.common.service.IActionService#findAction(java.lang.String, java.lang.String, java.lang.String)   
    */
    @Override
    public ActionsDto findAction(String projId, String procId, String nowTaskId)
    {
        Actions actions = new Actions();
        actions.setProjId(projId);
        actions.setProcId(procId);
        actions.setTaskId(nowTaskId);
        List<Actions> list = actionsDao.findAllBy(actions);
        if (StringUtils.isEmpty(list))
        {
            return null;
        }
        return BeanUtils.createBeanByTarget(list.get(0), ActionsDto.class);
    }

    
     /** 
     * (non-Javadoc)   
     * @see com.ssic.game.common.service.IActionService#findBy(com.ssic.game.common.dto.ActionsDto)   
     */
    @Override
    public List<Actions> findBy(ActionsDto actionsDto)
    {
        Actions actions = new Actions();
        BeanUtils.copyProperties(actionsDto, actions);
        return actionsDao.findAllBy(actions);
        
    }

}
