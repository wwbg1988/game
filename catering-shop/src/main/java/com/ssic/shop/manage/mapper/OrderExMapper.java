package com.ssic.shop.manage.mapper;

import org.apache.ibatis.annotations.Param;

import com.ssic.shop.manage.pojo.Order;

public interface OrderExMapper {
 
    /**
     * 通过订单编号查询记录	 
     * @author 朱振	
     * @date 2015年10月6日 下午12:18:06	
     * @version 1.0
     * @param orderNo
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年10月6日 下午12:18:06</p>
     * <p>修改备注：</p>
     */
    Order selectByOrderNo(String orderNo);
    
    /**
     * 
     * countMonthlysales：统计商品的月销量
     * @param shopId
     * @param cafetoriumId
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年10月8日 上午11:14:25
     */
    int countMonthlysales(@Param("shopId")String shopId,@Param("cafetoriumId")String cafetoriumId);
}