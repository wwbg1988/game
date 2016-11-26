/**
 * 
 */
package com.ssic.game.common.dao;


import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.ssic.game.common.dto.PageHelperDto;
import com.ssic.game.common.mapper.ImsQueryActionMapper;
import com.ssic.game.common.pojo.ImsQueryAction;
import com.ssic.game.common.pojo.ImsQueryActionExample;
import com.ssic.game.common.pojo.ImsQueryActionExample.Criteria;
import com.ssic.util.base.MyBatisBaseDao;
import com.ssic.util.constants.DataStatus;

/**		
 * <p>Title: QueryActionDao </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年7月28日 下午4:51:59	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年7月28日 下午4:51:59</p>
 * <p>修改备注：</p>
 */
@Repository
public class QueryActionDao extends MyBatisBaseDao<ImsQueryAction>
{
    @Autowired
    @Getter
    private ImsQueryActionMapper mapper;
    
    public List<ImsQueryAction> findBy(ImsQueryAction param){
        ImsQueryActionExample example = new ImsQueryActionExample();
        Criteria criteria = example.createCriteria();
        if(!StringUtils.isEmpty(param.getId())){
            criteria.andIdEqualTo(param.getId());
        }
        if(!StringUtils.isEmpty(param.getProjectId())){
           criteria.andProjectIdEqualTo(param.getProjectId()); 
        }
        if(!StringUtils.isEmpty(param.getQueryId())){
            criteria.andQueryIdEqualTo(param.getQueryId());
        }
        criteria.andStatEqualTo(DataStatus.ENABLED);
      
        return   mapper.selectByExample(example)!=null?  mapper.selectByExample(example):null;
        
    }
    
    public List<ImsQueryAction> findAllByPH(ImsQueryAction param,PageHelperDto phdto){
        ImsQueryActionExample example = new ImsQueryActionExample();
        Criteria criteria = example.createCriteria();
        if(!StringUtils.isEmpty(param.getId())){
            criteria.andIdEqualTo(param.getId());
        }
        if(!StringUtils.isEmpty(param.getProjectId())){
           criteria.andProjectIdEqualTo(param.getProjectId()); 
        }
        if(!StringUtils.isEmpty(param.getQueryId())){
            criteria.andQueryIdEqualTo(param.getQueryId());
        }
        criteria.andStatEqualTo(DataStatus.ENABLED);
        example.setOrderByClause("  create_time desc  limit "+ phdto.getBeginRow()+ ","+phdto.getRows());
        return   mapper.selectByExample(example)!=null?  mapper.selectByExample(example):null;
    	
    }
    
    public int findAllCount(ImsQueryAction param){
    	   ImsQueryActionExample example = new ImsQueryActionExample();
           Criteria criteria = example.createCriteria();
           if(!StringUtils.isEmpty(param.getId())){
               criteria.andIdEqualTo(param.getId());
           }
           if(!StringUtils.isEmpty(param.getProjectId())){
              criteria.andProjectIdEqualTo(param.getProjectId()); 
           }
           if(!StringUtils.isEmpty(param.getQueryId())){
               criteria.andQueryIdEqualTo(param.getQueryId());
           }
           criteria.andStatEqualTo(DataStatus.ENABLED);
           return   mapper.countByExample(example);
    }
    
    public void insertAction(ImsQueryAction param){
    	mapper.insert(param);
    }
    
    public void updateAction(ImsQueryAction param){
    	mapper.updateByPrimaryKeySelective(param);
    }
    
    public void deleteAction(String id){
    	mapper.deleteByPrimaryKey(id);
    }
    
    public ImsQueryAction   findById(String id){
    	return mapper.selectByPrimaryKey(id);
    }
    
}

