package com.ssic.catering.admin.dao;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssic.catering.admin.mapper.SupplierAdminUsersExMapper;
import com.ssic.catering.lbs.dto.SupplierDto;
import com.ssic.catering.lbs.pojo.SupplierAdminUsers;
import com.ssic.catering.lbs.pojo.SupplierAdminUsersExample;
import com.ssic.util.ArrayUtils;
import com.ssic.util.StringUtils;
import com.ssic.util.constants.DataStatus;

@Repository
public class SupplierAdminUsersAdminDao
{
    @Autowired
    private SupplierAdminUsersExMapper exMapper;
    
   /**
    * 
    * findSupplierDtosBy：一句话描述方法功能
    * @param param
    * @return
    * @exception	
    * @author zhuzhen
    * @date 2015年12月8日 下午12:39:21
    */
    public List<SupplierDto> findSupplierDtosBy(SupplierAdminUsers param)
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
                if(param.getAdminUsersId().contains(","))
                {
                    String[] suppliers = param.getAdminUsersId().split(",");
                    if(!ArrayUtils.isEmpty(suppliers))
                    {
                        criteria.andAdminUsersIdIn(Arrays.asList(suppliers));
                    }
                }
                else
                {
                    criteria.andAdminUsersIdEqualTo(param.getAdminUsersId());
                }
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
        
        return exMapper.selectSupplierDtoBy(example);
    }
}

