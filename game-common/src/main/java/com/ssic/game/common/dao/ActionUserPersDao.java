/**   
 * bare_field_name   
 * com.ssic.game.common.dao	
 * @return  the bare_field_name 
 */

package com.ssic.game.common.dao;

import java.util.Date;
import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.ssic.game.common.mapper.ActionUserMapper;
import com.ssic.game.common.pojo.ActionUser;
import com.ssic.game.common.pojo.ActionUserExample;
import com.ssic.game.common.pojo.ActionUserExample.Criteria;
import com.ssic.util.UUIDGenerator;
import com.ssic.util.base.MyBatisBaseDao;


/**		
 * <p>Title: ActionsDao </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年6月24日 上午9:03:59	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年6月24日 上午9:03:59</p>
 * <p>修改备注：</p>
 */
@Repository
public class ActionUserPersDao extends MyBatisBaseDao<ActionUser>
{
//对应表t_ims_action_role

    @Autowired
    @Getter
    private ActionUserMapper mapper;

  
    public List<ActionUser> findUserAll(ActionUser param){
        ActionUserExample example = new ActionUserExample();
        Criteria criteria = example.createCriteria();
        if(!StringUtils.isEmpty(param.getProjId())){
            criteria.andProjIdEqualTo(param.getProjId());
        }
        if(!StringUtils.isEmpty(param.getProcId())){
            criteria.andProcIdEqualTo(param.getProcId());
        }
        if(!StringUtils.isEmpty(param.getUserId())){
            criteria.andUserIdEqualTo(param.getUserId());
        }
        if(!StringUtils.isEmpty(param.getActionId())){
            criteria.andActionIdEqualTo(param.getActionId());
        }
        
        criteria.andStatEqualTo(1);
        
        return mapper.selectByExample(example);
    }
    
    public void insertRoles(ActionUser param){
        if(!StringUtils.isEmpty(param.getProjId())&&!StringUtils.isEmpty(param.getActionId())){
            param.setId(UUIDGenerator.getUUID());
            param.setCreateTime(new Date());
            param.setStat(1);
            mapper.insert(param);
        }
    }
    public void delRoles(ActionUser param){
        if(!StringUtils.isEmpty(param.getProjId())&&!StringUtils.isEmpty(param.getActionId())){
            ActionUserExample example = new ActionUserExample();
            Criteria criteria = example.createCriteria();
            criteria.andProjIdEqualTo(param.getProjId());
            criteria.andActionIdEqualTo(param.getActionId());
            criteria.andStatEqualTo(1);
            List<ActionUser> list = mapper.selectByExample(example);
            if(list!=null && list.size()>0){
//                ActionUsersExample example2 = new ActionUsersExample();
//                Criteria criteria2 = example2.createCriteria();
//                criteria2.andProjIdEqualTo(param.getProjId());
//                criteria2.andActionIdEqualTo(param.getActionId());
//                criteria2.andStatEqualTo(false);
//                criteria.andLastTimeEqualTo(new Date());
//                mapper.
                for(ActionUser actionUsers : list){
                    actionUsers.setStat(0);
                    mapper.updateByPrimaryKey(actionUsers);
                }
            }
        }
    }

}
