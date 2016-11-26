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

import com.ssic.game.common.mapper.ActionRoleMapper;
import com.ssic.game.common.mapper.ImsRoleMapper;
import com.ssic.game.common.pojo.ImsRole;
import com.ssic.game.common.pojo.ImsRoleExample;
import com.ssic.game.common.pojo.ImsRoleExample.Criteria;
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
public class ImsRolesDao extends MyBatisBaseDao<ImsRole>
{
//对应表t_ims_action_role

    @Autowired
    @Getter
    private ImsRoleMapper mapper;

  
    public List<ImsRole> findAllBy(ImsRole param){
        ImsRoleExample example = new ImsRoleExample();
        Criteria criteria = example.createCriteria();
        if(!StringUtils.isEmpty(param.getId())){
            criteria.andIdEqualTo(param.getId());
        }
        if(!StringUtils.isEmpty(param.getProjId())){
            criteria.andProjIdEqualTo(param.getProjId());
        }
        criteria.andStatEqualTo(1);
        return mapper.selectByExample(example);
        
    }
    
    public List<ImsRole> findFieldReadTree(ImsRole param,List<String> list){
        ImsRoleExample example = new ImsRoleExample();
        Criteria criteria = example.createCriteria();
        
        if(list!=null && list.size()>0){
            criteria.andIdNotIn(list);
        }
        criteria.andStatEqualTo(1);
        return mapper.selectByExample(example);
    }
    public ImsRole findById(String id){
        return mapper.selectByPrimaryKey(id);
    }
    

}
