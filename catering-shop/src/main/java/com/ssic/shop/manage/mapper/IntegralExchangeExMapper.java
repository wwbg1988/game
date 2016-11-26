package com.ssic.shop.manage.mapper;

import java.util.List;

import com.ssic.shop.manage.pojo.IntegralExchange;

public interface IntegralExchangeExMapper {
    /**
     * 通过积分兑换类别查询积分物品 
     * @author 朱振	
     * @date 2015年10月20日 下午4:24:24	
     * @version 1.0
     * @param cafetoriumId
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年10月20日 下午4:24:24</p>
     * <p>修改备注：</p>
     */
    List<IntegralExchange> findExchangesByCafetoriumId(String cafetoriumId);
}