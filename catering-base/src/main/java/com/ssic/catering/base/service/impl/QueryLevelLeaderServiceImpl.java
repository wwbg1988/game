/**
 * 
 */
package com.ssic.catering.base.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ssic.catering.base.dao.QueryLevelLeaderDao;
import com.ssic.catering.base.dto.PushAddressUserDto;
import com.ssic.catering.base.service.IQueryLevelLeaderService;

/**		
 * <p>Title: QueryLevelLeaderServiceImpl </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author pengcheng.yang	
 * @date 2015年8月11日 上午10:05:27	
 * @version 1.0
 * <p>修改人：pengcheng.yang</p>
 * <p>修改时间：2015年8月11日 上午10:05:27</p>
 * <p>修改备注：</p>
 */
@Service
public class QueryLevelLeaderServiceImpl implements IQueryLevelLeaderService
{

    @Autowired
    private QueryLevelLeaderDao queryLevelLeaderDao;
    
     /** 
     * (non-Javadoc)   
     * @see com.ssic.catering.base.service.IQueryLevelLeaderService#queryCityManager(java.lang.String)   
     * 查询城市经理
     */
    @Override
    public List<PushAddressUserDto> queryCityManager(String cafetoriumId)
    {
        if(!StringUtils.isEmpty(cafetoriumId)){
           return this.queryLevelLeaderDao.queryCityManager(cafetoriumId);
        }
        return null;
    }

    
     /** 
     * (non-Javadoc)   
     * @see com.ssic.catering.base.service.IQueryLevelLeaderService#queryProvincialManager(java.lang.String)   
     * 查询省市经理
     */
    @Override
    public List<PushAddressUserDto> queryProvincialManager(String cafetoriumId)
    {
        if(!StringUtils.isEmpty(cafetoriumId)){
            return this.queryLevelLeaderDao.queryProvincialManager(cafetoriumId);
         }
         return null;
    }

    
     /** 
     * (non-Javadoc)   
     * @see com.ssic.catering.base.service.IQueryLevelLeaderService#queryRegionalManager(java.lang.String)   
     * 查询大区负责人
     */
    @Override
    public List<PushAddressUserDto> queryRegionalManager(String cafetoriumId)
    {
        if(!StringUtils.isEmpty(cafetoriumId)){
            return this.queryLevelLeaderDao.queryRegionalManager(cafetoriumId);
         }
         return null;
    }

    
     /** 
     * (non-Javadoc)   
     * @see com.ssic.catering.base.service.IQueryLevelLeaderService#queryCompanyLeader(java.lang.String)   
     * 查询总公司负责人
     */
    @Override
    public List<PushAddressUserDto> queryCompanyLeader(String cafetoriumId)
    {
        if(!StringUtils.isEmpty(cafetoriumId)){
            return this.queryLevelLeaderDao.queryCompanyLeader(cafetoriumId);
         }
         return null;
    }

}

