package com.ssic.catering.lbs.dao;

import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.ssic.catering.lbs.mapper.SupplierAdminUsersMapper;
import com.ssic.catering.lbs.pojo.SupplierAdminUsers;
import com.ssic.catering.lbs.pojo.SupplierAdminUsersExample;
import com.ssic.util.base.MyBatisBaseDao;
import com.ssic.util.constants.DataStatus;

/**
 * 		
 * <p>Title: SupplierAdminUsersDao </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author zhuzhen	
 * @date 2015年12月4日 下午1:43:27	
 * @version 1.0
 * <p>修改人：zhuzhen</p>
 * <p>修改时间：2015年12月4日 下午1:43:27</p>
 * <p>修改备注：</p>
 */
@Repository
public class SupplierAdminUsersDao extends MyBatisBaseDao<SupplierAdminUsers>
{
    @Autowired
    @Getter
    private SupplierAdminUsersMapper mapper;
    
    /**
     * 
     * findBy：一句话描述方法功能
     * @param param
     * @return
     * @exception	
     * @author zhuzhen
     * @date 2015年12月4日 下午1:48:38
     */
    public List<SupplierAdminUsers> findBy(SupplierAdminUsers param)
    {
        SupplierAdminUsersExample example = new SupplierAdminUsersExample();
        SupplierAdminUsersExample.Criteria criteria = example.createCriteria();
        
        if(param != null)
        {
          //id
            if(!StringUtils.isEmpty(param.getId()))
            {
              criteria.andIdEqualTo(param.getId());
            }
            //adminUsersId
            if(!StringUtils.isEmpty(param.getAdminUsersId()))
            {
              criteria.andAdminUsersIdEqualTo(param.getAdminUsersId());
            }
            //supplierId
            if(!StringUtils.isEmpty(param.getSupplierId()))
            {
              criteria.andSupplierIdEqualTo(param.getSupplierId());
            }
            //createTime
            //lastUpdateTime
            //stat
            if(null == param.getStat())
            {
              criteria.andStatEqualTo(DataStatus.ENABLED);
            }
        }
        else
        {
            criteria.andStatEqualTo(DataStatus.ENABLED);
        }
        
        return mapper.selectByExample(example);
    }
    
    /**
     * 
     * deleteById：一句话描述方法功能
     * @param id
     * @return
     * @exception	
     * @author zhuzhen
     * @date 2015年12月4日 下午2:23:00
     */
    public int deleteById(String id)
    {
        if(StringUtils.isEmpty(id))
        {
            return 0;
        }
        
        return mapper.deleteByPrimaryKey(id);
    }
    
    /**
     * 
     * deleteBy：批量删除
     * @param param
     * @return
     * @exception	
     * @author zhuzhen
     * @date 2015年12月4日 下午2:37:56
     */
    public int deleteBy(SupplierAdminUsers param)
    {
        SupplierAdminUsersExample example = new SupplierAdminUsersExample();
        SupplierAdminUsersExample.Criteria criteria = example.createCriteria();
        
        if(param != null)
        {
          //id
            if(!StringUtils.isEmpty(param.getId()))
            {
              criteria.andIdEqualTo(param.getId());
            }
            //adminUsersId
            if(!StringUtils.isEmpty(param.getAdminUsersId()))
            {
              criteria.andAdminUsersIdEqualTo(param.getAdminUsersId());
            }
            //supplierId
            if(!StringUtils.isEmpty(param.getSupplierId()))
            {
              criteria.andSupplierIdEqualTo(param.getSupplierId());
            }
            //createTime
            //lastUpdateTime
            //stat
            if(null == param.getStat())
            {
              criteria.andStatEqualTo(DataStatus.ENABLED);
            }
        }
        else
        {
            criteria.andStatEqualTo(DataStatus.ENABLED);
        }
        
        return mapper.deleteByExample(example);
    }
}

