package com.ssic.shop.manage.dao;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssic.shop.manage.mapper.GoodsImageMapper;
import com.ssic.shop.manage.pojo.GoodsImage;
import com.ssic.util.base.MyBatisBaseDao;

/**
 * 		
 * <p>Title: GoodsImageDao1 </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 朱振	
 * @date 2015年11月14日 下午2:59:25	
 * @version 1.0
 * <p>修改人：朱振</p>
 * <p>修改时间：2015年11月14日 下午2:59:25</p>
 * <p>修改备注：</p>
 */
@Repository
public class GoodsImageExDao extends MyBatisBaseDao<GoodsImage>
{
    @Autowired
    @Getter
    private GoodsImageMapper mapper;
}

