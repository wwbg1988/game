/**
 * 
 */
package com.ssic.game.common.dao;

import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.ssic.game.common.mapper.RoleUsersMapper;
import com.ssic.game.common.pojo.RoleUsers;
import com.ssic.game.common.pojo.RoleUsersExample;
import com.ssic.game.common.pojo.RoleUsersExample.Criteria;

/**		
 * <p>Title: ImsRoleUserDao </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年7月2日 上午9:56:57	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年7月2日 上午9:56:57</p>
 * <p>修改备注：</p>
 */
@Repository
public class ImsRoleUserDao
{
    @Autowired
    @Getter
    private RoleUsersMapper mapper; 
   
    public List<RoleUsers> findAllBy(RoleUsers param){
        
        if(!StringUtils.isEmpty(param.getUserId())){
            RoleUsersExample example = new RoleUsersExample();
            Criteria criteria = example.createCriteria();
            if(!StringUtils.isEmpty(param.getUserId())){
                
                criteria.andUserIdEqualTo(param.getUserId());
            }
            if(!StringUtils.isEmpty(param.getId())){
                criteria.andIdEqualTo(param.getId());
            }
            criteria.andStatEqualTo(1);
            return mapper.selectByExample(example);
        }
        return null;
        
    }

}

