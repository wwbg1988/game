/**
 * 
 */
package com.ssic.shop.manage.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssic.shop.manage.dao.GoodsTypeDao;
import com.ssic.shop.manage.dto.GoodsTypeDto;
import com.ssic.shop.manage.dto.PageHelperDto;
import com.ssic.shop.manage.pojo.GoodsType;
import com.ssic.shop.manage.service.IGoodsTypeService;
import com.ssic.util.BeanUtils;
import com.ssic.util.constants.DataStatus;

/**		
 * <p>Title: GoodsTypeServiceImpl </p>
 * <p>Description: 商品类型Service实现层</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 刘博	
 * @date 2015年8月27日 上午10:15:42	
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年8月27日 上午10:15:42</p>
 * <p>修改备注：</p>
 */
@Service
public class GoodsTypeServiceImpl implements IGoodsTypeService
{
    @Autowired
    private GoodsTypeDao goodsTypeDao;

    /** 
    * (non-Javadoc)   
    * @see com.ssic.shop.manage.service.IGoodsTypeService#findAllBy(com.ssic.shop.manage.dto.GoodsTypeDto, com.ssic.shop.manage.dto.PageHelperDto)   
    */
    @Override
    public List<GoodsTypeDto> findAllBy(GoodsTypeDto goodsTypeDto, PageHelperDto ph)
    {
        List<GoodsTypeDto> result = new ArrayList<GoodsTypeDto>();
        GoodsType param = new GoodsType();
        BeanUtils.copyProperties(goodsTypeDto, param);
        List<GoodsType> list = goodsTypeDao.findAllBy(param, ph);
        if (!CollectionUtils.isEmpty(list))
        {
            result = BeanUtils.createBeanListByTarget(list, GoodsTypeDto.class);
        }
        return result;
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.shop.manage.service.IGoodsTypeService#findCount(com.ssic.shop.manage.dto.GoodsTypeDto)   
    */
    @Override
    public int findCount(GoodsTypeDto goodsTypeDto)
    {
        GoodsType param = new GoodsType();
        BeanUtils.copyProperties(goodsTypeDto, param);
        int counts = goodsTypeDao.findCountBy(param);
        return counts;
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.shop.manage.service.IGoodsTypeService#finByTypeName(java.lang.String)   
    */
    @Override
    public GoodsTypeDto finByTypeName(String typeName, String id)
    {
        GoodsType goodsType = goodsTypeDao.findByTypeName(typeName, id);
        if (goodsType != null)
        {
            GoodsTypeDto goodsTypeDto = new GoodsTypeDto();
            BeanUtils.copyProperties(goodsType, goodsTypeDto);
            return goodsTypeDto;
        }
        return null;
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.shop.manage.service.IGoodsTypeService#add(com.ssic.shop.manage.dto.GoodsTypeDto)   
    */
    @Override
    public void add(GoodsTypeDto goodsTypeDto)
    {
        GoodsType goodsType = new GoodsType();
        BeanUtils.copyProperties(goodsTypeDto, goodsType);
        goodsType.setStat(DataStatus.ENABLED);
        goodsType.setCreateTime(new Date());
        goodsTypeDao.insert(goodsType);
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.shop.manage.service.IGoodsTypeService#findGoodsTypeById(java.lang.String)   
    */
    @Override
    public GoodsTypeDto findGoodsTypeById(String id)
    {
        GoodsTypeDto r = new GoodsTypeDto();
        GoodsType type = goodsTypeDao.selectByPrimaryKey(id);
        if (type != null)
        {
            BeanUtils.copyProperties(type, r);
            return r;
        }
        return null;
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.shop.manage.service.IGoodsTypeService#delete(com.ssic.shop.manage.dto.GoodsTypeDto)   
    */
    @Override
    public void delete(GoodsTypeDto r)
    {
        GoodsType goodsType = new GoodsType();
        BeanUtils.copyProperties(r, goodsType);
        goodsType.setStat(DataStatus.DISABLED);
        goodsTypeDao.updateByPrimaryKeySelective(goodsType);

    }

}
