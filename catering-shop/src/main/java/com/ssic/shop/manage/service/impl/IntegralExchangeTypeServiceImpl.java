package com.ssic.shop.manage.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.ssic.shop.manage.dao.IntegralExchangeTypeDao;
import com.ssic.shop.manage.dto.IntegralExchangeTypeDto;
import com.ssic.shop.manage.pojo.IntegralExchangeType;
import com.ssic.shop.manage.service.IIntegralExchangeTypeService;
import com.ssic.util.BeanUtils;

/**		
 * <p>Title: IntegralExchangeTypeServiceImpl </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 朱振	
 * @date 2015年9月22日 上午9:10:54	
 * @version 1.0
 * <p>修改人：朱振</p>
 * <p>修改时间：2015年9月22日 上午9:10:54</p>
 * <p>修改备注：</p>
 */
@Service
public class IntegralExchangeTypeServiceImpl implements IIntegralExchangeTypeService
{
    
    @Autowired
    private IntegralExchangeTypeDao integralExchangeTypeDao;

    /**
     * 
     * (non-Javadoc)   
     * @see com.ssic.shop.manage.service.IIntegralExchangeTypeService#getList()
     */
    @Override
    public List<IntegralExchangeTypeDto> getList()
    {
        List<IntegralExchangeType> list = integralExchangeTypeDao.findAll();
        if(!CollectionUtils.isEmpty(list))
        {
            return BeanUtils.createBeanListByTarget(list, IntegralExchangeTypeDto.class);
        }
        
        return null;
    }
    
    
    /**
     * 
     * (non-Javadoc)   
     * @see com.ssic.shop.manage.service.IIntegralExchangeTypeService#addIntegralExchangeType(com.ssic.shop.manage.dto.IntegralExchangeTypeDto)
     */
    @Override
    public int addIntegralExchangeType(IntegralExchangeTypeDto integralExchangeTypeDto)
    {
        IntegralExchangeType type = new IntegralExchangeType();
        BeanUtils.copyProperties(integralExchangeTypeDto, type);
        
        return integralExchangeTypeDao.insert(type);
    }
    
    /**
     * 
     * (non-Javadoc)   
     * @see com.ssic.shop.manage.service.IIntegralExchangeTypeService#findIntegralExchangeById(java.lang.String)
     */
    @Override
    public IntegralExchangeTypeDto findIntegralExchangeById(String id)
    {
        IntegralExchangeTypeDto result = new IntegralExchangeTypeDto();

        IntegralExchangeType  integralExchangeType = integralExchangeTypeDao.findById(id);
        if(integralExchangeType != null)
        {
            BeanUtils.copyProperties(integralExchangeType, result);
        }
        
        return result;
    }
}

