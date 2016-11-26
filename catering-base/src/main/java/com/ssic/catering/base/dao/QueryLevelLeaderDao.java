package com.ssic.catering.base.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssic.catering.base.dto.PushAddressUserDto;
import com.ssic.catering.base.mapper.AddressUserExMapper;

/**
 * 		
 * <p>Title: QueryLevelLeaderDao </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author pengcheng.yang	
 * @date 2015年8月11日 上午9:44:19	
 * @version 1.0
 * <p>修改人：pengcheng.yang</p>
 * <p>修改时间：2015年8月11日 上午9:44:19</p>
 * <p>修改备注：</p>
 */
@Repository
public class QueryLevelLeaderDao
{
    @Autowired
    private AddressUserExMapper mapper;
    
    /**
     * 
     * queryCityManager：查询城市经理
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年8月11日 上午9:56:30
     */
    public List<PushAddressUserDto> queryCityManager(String cafetoriumId){
        List<PushAddressUserDto> list =  mapper.queryCityManager(cafetoriumId);
        if(list != null){
            return list;
        }
        return null;
    }
    
    /**
     * 
     * queryProvincialManager：查询省市经理
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年8月11日 上午9:57:22
     */
    public List<PushAddressUserDto> queryProvincialManager(String cafetoriumId){
        List<PushAddressUserDto> list =  mapper.queryProvincialManager(cafetoriumId);
        if(list != null){
            return list;
        }
        return null;
    }
    
    /**
     * 
     * queryRegionalManager：查询大区负责人
     * @return
     * @exception	
     * @author pengcheng.yang 
     * @date 2015年8月11日 上午9:57:42
     */
    public List<PushAddressUserDto> queryRegionalManager(String cafetoriumId){
        List<PushAddressUserDto> list =  mapper.queryRegionalManager(cafetoriumId);
        if(list != null){
            return list;
        }
        return null;
    }
    
    /**
     * 
     * queryRegionalManager：总公司负责人
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年8月11日 上午9:58:19
     */
    public List<PushAddressUserDto> queryCompanyLeader(String cafetoriumId){
        List<PushAddressUserDto> list =  mapper.queryCompanyLeader(cafetoriumId);
        if(list != null){
            return list;
        }
        return null;
    }
    
}

