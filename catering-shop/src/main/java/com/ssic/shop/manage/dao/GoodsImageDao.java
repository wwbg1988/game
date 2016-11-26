package com.ssic.shop.manage.dao;

import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssic.shop.manage.dto.GoodsImageDto;
import com.ssic.shop.manage.mapper.GoodsImageMapper;
import com.ssic.shop.manage.pojo.GoodsImage;
import com.ssic.shop.manage.pojo.GoodsImageExample;
import com.ssic.shop.manage.pojo.GoodsImageExample.Criteria;
import com.ssic.util.StringUtils;
import com.ssic.util.base.MyBatisBaseDao;
import com.ssic.util.constants.DataStatus;

/**
 * 		
 * <p>Title: GoodsImageDao </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author pengcheng.yang	
 * @date 2015年9月21日 上午11:49:06	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年9月21日 上午11:49:06</p>
 * <p>修改备注：</p>
 */
@Repository
public class GoodsImageDao extends MyBatisBaseDao<GoodsImageDto>
{
    @Autowired
    @Getter
    private GoodsImageMapper mapper;
    
    /**
     * 
     * findGoodsImageById：根据商品Id查询跟商品详情的轮播图片
     * @param shopId
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年9月21日 上午11:54:01
     */
    public List<GoodsImage> findGoodsImageById(String shopId){
        GoodsImageExample example = new GoodsImageExample();
        Criteria criteria = example.createCriteria();
        criteria.andStatEqualTo(DataStatus.ENABLED);
        if (!StringUtils.isEmpty(shopId))
        {
            criteria.andGoodsIdEqualTo(shopId);
        }
        List<GoodsImage> list = mapper.selectByExample(example);
        if(list != null){
            return list;
        }
        return null;
    }

}

