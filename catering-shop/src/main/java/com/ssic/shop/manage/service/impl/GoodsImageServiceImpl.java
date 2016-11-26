package com.ssic.shop.manage.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssic.shop.manage.dao.GoodsImageDao;
import com.ssic.shop.manage.dto.GoodsImageDto;
import com.ssic.shop.manage.dto.ShoppingCartDto;
import com.ssic.shop.manage.pojo.GoodsImage;
import com.ssic.shop.manage.service.IGoodsImageService;
import com.ssic.util.BeanUtils;

/**
 * 		
 * <p>Title: GoodsImageServiceImpl </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author pengcheng.yang	
 * @date 2015年9月21日 下午12:04:35	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年9月21日 下午12:04:35</p>
 * <p>修改备注：</p>
 */
@Service
public class GoodsImageServiceImpl implements IGoodsImageService
{
    
    @Autowired
    private GoodsImageDao goodsImageDao;
    
    /**
     * 
     * (non-Javadoc)   
     * @see com.ssic.shop.manage.service.IGoodsImageService#findGoodsImageById(java.lang.String)
     * @author pengcheng.yang
     * @desc 根据商品Id查询跟商品详情的轮播图片
     */
    @Override
    public List<GoodsImageDto> findGoodsImageById(String shopId)
    {
        List<GoodsImage> list = goodsImageDao.findGoodsImageById(shopId);
        List<GoodsImageDto> listDto = null;
        if(list != null){
            listDto = BeanUtils.createBeanListByTarget(list, GoodsImageDto.class);
        }
        return listDto;
    }

}

