/**
 * 
 */
package com.ssic.shop.manage.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.ssic.shop.manage.dao.WeixnUserCafetoriumDao;
import com.ssic.shop.manage.dto.WeixnUserCafetoriumDto;
import com.ssic.shop.manage.pojo.WeixnUserCafetorium;
import com.ssic.shop.manage.service.IWeixnUserCafetoriumService;
import com.ssic.util.BeanUtils;

/**		
 * <p>Title: WeixnUserCafetoriumServiceImpl </p>
 * <p>Description: 微信用户关注的食堂Serivice层实现类</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 刘博	
 * @date 2015年10月27日 下午4:11:30	
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年10月27日 下午4:11:30</p>
 * <p>修改备注：</p>
 */
@Service
public class WeixnUserCafetoriumServiceImpl implements IWeixnUserCafetoriumService
{
    private static final Logger log = Logger.getLogger(WeixnUserCafetoriumServiceImpl.class);
    
    @Autowired
    private WeixnUserCafetoriumDao weixnUserCafetoriumDao;

    /** 
    * (non-Javadoc)   
    * @see com.ssic.shop.manage.service.IWeixnUserCafetoriumService#findAllBy(com.ssic.shop.manage.pojo.WeixnUserCafetorium)   
    */
    @Override
    public List<WeixnUserCafetoriumDto> findAllBy(WeixnUserCafetoriumDto param)
    {
        List<WeixnUserCafetoriumDto> result = new ArrayList<WeixnUserCafetoriumDto>();
        WeixnUserCafetorium weixnUserCafetorium = new WeixnUserCafetorium();
        BeanUtils.copyProperties(param, weixnUserCafetorium);
        List<WeixnUserCafetorium> list = weixnUserCafetoriumDao.findAll(weixnUserCafetorium);
        if (!CollectionUtils.isEmpty(list))
        {
            result = BeanUtils.createBeanListByTarget(list, WeixnUserCafetoriumDto.class);
        }
        return result;
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.shop.manage.service.IWeixnUserCafetoriumService#insert(com.ssic.shop.manage.dto.WeixnUserCafetoriumDto)   
    */
    @Override
    public void insert(WeixnUserCafetoriumDto dto)
    {
        WeixnUserCafetorium obj = new WeixnUserCafetorium();
        BeanUtils.copyProperties(dto, obj);
        obj.setIsDefault(dto.getIsDefault());
        weixnUserCafetoriumDao.insertSelective(obj);

    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.shop.manage.service.IWeixnUserCafetoriumService#update(com.ssic.shop.manage.dto.WeixnUserCafetoriumDto)   
    */
    @Override
    public void update(WeixnUserCafetoriumDto cafe)
    {
        WeixnUserCafetorium obj = new WeixnUserCafetorium();
        BeanUtils.copyProperties(cafe, obj);
        obj.setIsDefault(cafe.getIsDefault());
        weixnUserCafetoriumDao.updateByPrimaryKeySelective(obj);

    }
    
    /**
     * (non-Javadoc)   
     * @see com.ssic.shop.manage.service.IWeixnUserCafetoriumService#setDefaultCafetorium(java.lang.String, java.lang.String)
     */
    @Override
    public Integer setDefaultCafetorium(String openId, String cafetoriumId)
    {
        if(StringUtils.isEmpty(openId))
        {
            log.error("微信id不能为空");
            return null;
        }
        
        if(StringUtils.isEmpty(cafetoriumId))
        {
            log.error("cafetoriumId不能为空");
            return null;
        }
        
        return weixnUserCafetoriumDao.updateDefaultCafetorium(openId, cafetoriumId);
    }
}
