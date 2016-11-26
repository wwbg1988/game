/**
 * 
 */
package com.ssic.game.common.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.ssic.game.common.mapper.FieldUserMapper;
import com.ssic.game.common.pojo.FieldRoleExample;
import com.ssic.game.common.pojo.FieldUser;
import com.ssic.game.common.pojo.FieldUserExample;
import com.ssic.game.common.pojo.FieldUserExample.Criteria;
import com.ssic.util.base.MyBatisBaseDao;

import lombok.Getter;


/**		
 * <p>Title: FieldsDao </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 刘博	
 * @date 2015年6月24日 上午9:19:00	
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年6月24日 上午9:19:00</p>
 * <p>修改备注：</p>
 */
@Repository
public class FieldsUserDao extends MyBatisBaseDao<FieldUser>
{
    
    @Getter
    @Autowired
    private FieldUserMapper mapper;
    
   
    
    public List<FieldUser> findUserAllBy(FieldUser fieldUser){
        FieldUserExample example = new FieldUserExample();
        Criteria criteria = example.createCriteria();
        if(!StringUtils.isEmpty(fieldUser.getFieldId())){
            
            criteria.andFieldIdEqualTo(fieldUser.getFieldId());
           
        }
        if(!StringUtils.isEmpty(fieldUser.getProjId())){
            criteria.andProjIdEqualTo(fieldUser.getProjId());
        }
        if(!StringUtils.isEmpty(fieldUser.getProcId())){
            criteria.andProcIdEqualTo(fieldUser.getProcId());
        }
        if(!StringUtils.isEmpty(fieldUser.getReaderWrite())&&fieldUser.getReaderWrite()!=0){
            criteria.andReaderWriteEqualTo(fieldUser.getReaderWrite());
            
        }
        if(!StringUtils.isEmpty(fieldUser.getUserId())){
            criteria.andUserIdEqualTo(fieldUser.getUserId());
        }
        criteria.andStatEqualTo(1);
        return mapper.selectByExample(example);
    }
    
   

}
