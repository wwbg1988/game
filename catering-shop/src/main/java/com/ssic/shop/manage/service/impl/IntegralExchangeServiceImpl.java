package com.ssic.shop.manage.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssic.shop.manage.dao.IntegralExchangeDao;
import com.ssic.shop.manage.dto.IntegralExchangeDto;
import com.ssic.shop.manage.dto.PageHelperDto;
import com.ssic.shop.manage.pojo.IntegralExchange;
import com.ssic.shop.manage.service.IIntegralExchangeService;
import com.ssic.util.BeanUtils;
import com.ssic.util.StringUtils;

/**		
 * <p>Title: IntegralExchangeServiceImpl </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 朱振	
 * @date 2015年9月18日 下午1:11:19	
 * @version 1.0
 * <p>修改人：朱振</p>
 * <p>修改时间：2015年9月18日 下午1:11:19</p>
 * <p>修改备注：</p>
 */
@Service
public class IntegralExchangeServiceImpl implements IIntegralExchangeService
{
    private static Logger log = Logger.getLogger(IntegralExchangeServiceImpl.class);

    @Autowired
    private IntegralExchangeDao integralExchangeDao;
    
    
   /**
    * 
    * (non-Javadoc)   
    * @see com.ssic.shop.manage.service.IIntegralExchangeService#getListBy(com.ssic.shop.manage.dto.IntegralExchangeDto, com.ssic.shop.manage.dto.PageHelperDto)
    */
    @Override
    public List<IntegralExchangeDto> getListBy(IntegralExchangeDto integralExchangeDto, PageHelperDto ph)
    {
        IntegralExchange integralExchange = new IntegralExchange();
        
        if(integralExchangeDto != null)
        {
            BeanUtils.copyProperties(integralExchangeDto, integralExchange);
        }
        
        List<IntegralExchange> list = integralExchangeDao.findAll(integralExchange, ph);
        if(!CollectionUtils.isEmpty(list))
        {
            return BeanUtils.createBeanListByTarget(list, IntegralExchangeDto.class);
        }
        
        return null;
    }
    
    /**
     * 
     * (non-Javadoc)   
     * @see com.ssic.shop.manage.service.IntegralExchangeService#addIntegralExchange(com.ssic.shop.manage.dto.IntegralExchangeDto)
     */
    @Override
    public int addIntegralExchange(IntegralExchangeDto integralExchangeDto)
    {
        if(integralExchangeDto != null)
        {
            IntegralExchange integralExchange = new IntegralExchange();
            BeanUtils.copyProperties(integralExchangeDto, integralExchange);
            
            return integralExchangeDao.insert(integralExchange);
        }
        
        
        return 0;        
    }
    
    
    /**
     * 
     * (non-Javadoc)   
     * @see com.ssic.shop.manage.service.IIntegralExchangeService#getListBy(com.ssic.shop.manage.dto.PageHelperDto)
     */
    @Override
    public List<IntegralExchangeDto> getListBy(PageHelperDto ph)
    {
       //return BeanUtils.createBeanListByTarget(integralExchangeDao.findAll(ph), IntegralExchangeDto.class);
       
       List<IntegralExchange> list = integralExchangeDao.findAll(ph);
       
       if(!CollectionUtils.isEmpty(list))
       {
           return BeanUtils.createBeanListByTarget(list, IntegralExchangeDto.class);
       }
       
       return null;
    }
    
    @Override
    public List<IntegralExchangeDto> getList()
    {
        List<IntegralExchange> list = integralExchangeDao.findAll();
        
        if(!CollectionUtils.isEmpty(list))
        {
            return BeanUtils.createBeanListByTarget(list, IntegralExchangeDto.class);
        }
        
        return null;
    }
    
    /**
     * 
     * (non-Javadoc)   
     * @see com.ssic.shop.manage.service.IIntegralExchangeService#getIntegralExchangeByCafetoriumId(java.lang.String)
     */
    @Override
    public List<IntegralExchangeDto> getIntegralExchangeByCafetoriumId(String cafetoriumId)
    {
        
        if(StringUtils.isEmpty(cafetoriumId))
        {
            log.warn("parameter:cafetoriumId="+cafetoriumId);
            return null;
        }
        
        List<IntegralExchange> integralExchanges = integralExchangeDao.findAllByCafetoriumId(cafetoriumId);
        if(CollectionUtils.isEmpty(integralExchanges))
        {
            log.warn("积分兑换表为空");
            return null;
        }
        
       return BeanUtils.createBeanListByTarget(integralExchanges, IntegralExchangeDto.class);
    }

}

