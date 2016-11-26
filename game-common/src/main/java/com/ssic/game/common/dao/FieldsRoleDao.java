/**
 * 
 */
package com.ssic.game.common.dao;

import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.ssic.game.common.mapper.FieldRoleExMapper;
import com.ssic.game.common.mapper.FieldRoleMapper;
import com.ssic.game.common.mapper.FieldUserMapper;
import com.ssic.game.common.pojo.FieldRole;
import com.ssic.game.common.pojo.FieldRoleExample;
import com.ssic.game.common.pojo.FieldRoleExample.Criteria;
import com.ssic.game.common.pojo.FieldUser;
import com.ssic.game.common.pojo.FieldUserExample;
import com.ssic.util.base.MyBatisBaseDao;

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
public class FieldsRoleDao extends MyBatisBaseDao<FieldRole>
{
    @Getter
    @Autowired
    private FieldRoleMapper mapper;
    
    @Getter
    @Autowired
    private FieldRoleExMapper exMapper;
    
    
    public List<FieldRole> findAllBy(FieldRole fieldRole){
        FieldRoleExample example = new FieldRoleExample();
        Criteria criteria = example.createCriteria();
        if(!StringUtils.isEmpty(fieldRole.getFormId())&&!StringUtils.isEmpty(fieldRole.getFieldId())){
            
            criteria.andFormIdEqualTo(fieldRole.getFormId());
            criteria.andFieldIdEqualTo(fieldRole.getFieldId());
           
        }
        if(!StringUtils.isEmpty(fieldRole.getReaderWrite())&&fieldRole.getReaderWrite()!=0){
            criteria.andReaderWriteEqualTo(fieldRole.getReaderWrite());
            
        }
        if(!StringUtils.isEmpty(fieldRole.getRoleId())){
            criteria.andRoleIdEqualTo(fieldRole.getRoleId());
        }
        criteria.andStatEqualTo(1);
        return mapper.selectByExample(example);
    }
    
    public void insertFun(FieldRole fieldRole){
        mapper.insertSelective(fieldRole);
    }
    
    public void updateFun(FieldRole fieldRole){
        exMapper.updateDel(fieldRole);
    }
    
    /**     
     * deleteByFieldId：一句话描述方法功能
     * @param id
     * @exception	
     * @author 刘博
     * @date 2015年6月27日 上午8:30:41	 
     */
    public void deleteByFieldId(String id)
    {
        FieldRoleExample example = new FieldRoleExample();
        Criteria criteria = example.createCriteria();
        if(!StringUtils.isEmpty(id)){
           criteria.andFieldIdEqualTo(id);
        }
        criteria.andStatEqualTo(1);
        List<FieldRole> fieldRoles=  mapper.selectByExample(example);
        for(FieldRole fieldRole:fieldRoles){
            fieldRole.setStat(0);
            mapper.updateByPrimaryKeySelective(fieldRole);
        }
    }
    
    /**     
     * deleteByFormId：一句话描述方法功能
     * @param formId
     * @exception	
     * @author 刘博
     * @date 2015年6月27日 下午3:26:40	 
     */
    public void deleteByFormId(String formId)
    {
        FieldRoleExample example = new FieldRoleExample();
        Criteria criteria = example.createCriteria();
        if(!StringUtils.isEmpty(formId)){
           criteria.andFormIdEqualTo(formId);
        }
        criteria.andStatEqualTo(1);
        List<FieldRole> fieldRoles=  mapper.selectByExample(example);
        for(FieldRole fieldRole:fieldRoles){
            fieldRole.setStat(0);
            mapper.updateByPrimaryKeySelective(fieldRole);
        }
        
    }

}
