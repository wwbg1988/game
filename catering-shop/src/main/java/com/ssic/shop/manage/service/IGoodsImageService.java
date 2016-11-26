package com.ssic.shop.manage.service;

import java.util.List;

import com.ssic.shop.manage.dto.GoodsImageDto;

/**
 * 		
 * <p>Title: IGoodsImageService </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author pengcheng.yang	
 * @date 2015年9月21日 下午12:02:18	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年9月21日 下午12:02:18</p>
 * <p>修改备注：</p>
 */
public interface IGoodsImageService
{
    /**
     * 
     * findGoodsImageById：根据商品Id查询跟商品详情的轮播图片
     * @param shopId
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年9月21日 下午12:03:37
     */
    List<GoodsImageDto> findGoodsImageById(String shopId);
}

