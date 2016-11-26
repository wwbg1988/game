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
import com.ssic.game.common.mapper.ImsQueryResultMapper;
import com.ssic.game.common.pojo.ImsQueryExample;
import com.ssic.game.common.pojo.ImsQueryResult;
import com.ssic.game.common.pojo.ImsQueryResultExample;
import com.ssic.game.common.pojo.ImsQueryResultExample.Criteria;
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
public class QueryResultDao extends MyBatisBaseDao<ImsQueryResult>
{
    @Autowired
    @Getter
    private ImsQueryResultMapper mapper;
    
    public List<ImsQueryResult> findBy(ImsQueryResult param){
        ImsQueryResultExample example = new ImsQueryResultExample();
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
        example.setOrderByClause(" serial_num asc");
        
      
        return   mapper.selectByExample(example)!=null?  mapper.selectByExample(example):null;
        
    }
    
    public void insertResult(ImsQueryResult param){
    	mapper.insert(param);
    }

    public List<ImsQueryResult> findAllByPH(ImsQueryResult param,PageHelperDto phdto){
       ImsQueryResultExample example = new ImsQueryResultExample();
        Criteria criteria = example.createCriteria();
        if(!StringUtils.isEmpty(param.getId())){
            criteria.andIdEqualTo(param.getId());
        }
        if(!StringUtils.isEmpty(param.getQueryId())){
        	criteria.andQueryIdEqualTo(param.getQueryId());
        }
        criteria.andStatEqualTo(DataStatus.ENABLED);
        example.setOrderByClause("  create_time desc  limit "+ phdto.getBeginRow()+ ","+phdto.getRows());
    	return  mapper.selectByExample(example)!=null?  mapper.selectByExample(example):null;
    }
    
    public int findAllCount(ImsQueryResult param){
    	ImsQueryResultExample example = new ImsQueryResultExample();
  	    Criteria criteria = example.createCriteria();
        if(!StringUtils.isEmpty(param.getId())){
            criteria.andIdEqualTo(param.getId());
        }
        criteria.andStatEqualTo(DataStatus.ENABLED);
  	    return mapper.countByExample(example);
    }
    
    public void updateResult(ImsQueryResult param){
    	mapper.updateByPrimaryKeySelective(param);
    }
    
    public void deleteResult(String id){
    	mapper.deleteByPrimaryKey(id);
    }
    
}

