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
import com.ssic.game.common.dto.query.QueryDto;
import com.ssic.game.common.mapper.ImsQueryMapper;
import com.ssic.game.common.pojo.ImsQuery;
import com.ssic.game.common.pojo.ImsQueryExample;
import com.ssic.game.common.pojo.ImsQueryExample.Criteria;
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
public class QueryDao extends MyBatisBaseDao<ImsQuery>
{
    @Autowired
    @Getter
    private ImsQueryMapper mapper;
    

    
    public List<ImsQuery> findBy(ImsQuery param){
        ImsQueryExample example = new ImsQueryExample();
        Criteria criteria = example.createCriteria();
        if(!StringUtils.isEmpty(param.getId())){
            criteria.andIdEqualTo(param.getId());
        }
        if(!StringUtils.isEmpty(param.getProjectId())){
        	criteria.andProjectIdEqualTo(param.getProjectId());
        }
        if(!StringUtils.isEmpty(param.getProcessId())){
        	criteria.andProcessIdEqualTo(param.getProcessId());
        }
        if(!StringUtils.isEmpty(param.getTaskId())){
        	criteria.andTaskIdEqualTo(param.getTaskId());
        }
        if(!StringUtils.isEmpty(param.getActionId())){
        	criteria.andActionIdEqualTo(param.getActionId());
        }
       criteria.andStatEqualTo(DataStatus.ENABLED);
      
        return   mapper.selectByExample(example)!=null?  mapper.selectByExample(example):null;
        
    }
    
    public ImsQuery findById(String id){
        if(!StringUtils.isEmpty(id)){
            return mapper.selectByPrimaryKey(id);
        }
        return null;
    }

    public int findAllCount(ImsQuery param){
    	ImsQueryExample example = new ImsQueryExample();
    	  Criteria criteria = example.createCriteria();
          if(!StringUtils.isEmpty(param.getId())){
              criteria.andIdEqualTo(param.getId());
          }
          if(!StringUtils.isEmpty(param.getProjectId())){
          	criteria.andProjectIdEqualTo(param.getProjectId());
          }
          if(!StringUtils.isEmpty(param.getProcessId())){
          	criteria.andProcessIdEqualTo(param.getProcessId());
          }
          if(!StringUtils.isEmpty(param.getTaskId())){
          	criteria.andTaskIdEqualTo(param.getTaskId());
          }
          if(!StringUtils.isEmpty(param.getActionId())){
          	criteria.andActionIdEqualTo(param.getActionId());
          }
          criteria.andStatEqualTo(DataStatus.ENABLED);
    	return mapper.countByExample(example);
    }
    
    public List<ImsQuery> findAllByPH(ImsQuery param,PageHelperDto phdto){
        ImsQueryExample example = new ImsQueryExample();
        Criteria criteria = example.createCriteria();
        if(!StringUtils.isEmpty(param.getId())){
            criteria.andIdEqualTo(param.getId());
        }
        if(!StringUtils.isEmpty(param.getProjectId())){
        	criteria.andProjectIdEqualTo(param.getProjectId());
        }
        if(!StringUtils.isEmpty(param.getProcessId())){
        	criteria.andProcessIdEqualTo(param.getProcessId());
        }
        if(!StringUtils.isEmpty(param.getTaskId())){
        	criteria.andTaskIdEqualTo(param.getTaskId());
        }
        if(!StringUtils.isEmpty(param.getActionId())){
        	criteria.andActionIdEqualTo(param.getActionId());
        }
        criteria.andStatEqualTo(DataStatus.ENABLED);
        example.setOrderByClause("  create_time desc  limit "+ phdto.getBeginRow()+ ","+phdto.getRows());
    	return  mapper.selectByExample(example)!=null?  mapper.selectByExample(example):null;
    }
    
    public void insertQuery(ImsQuery param){
    	mapper.insert(param);
    }
    
  
}

