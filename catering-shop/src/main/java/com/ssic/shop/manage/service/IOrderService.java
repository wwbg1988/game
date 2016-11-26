package com.ssic.shop.manage.service;

import java.util.List;

import com.ssic.shop.manage.dto.OrderDto;

public interface IOrderService {

	List<OrderDto> findBy(OrderDto orderDto);
	
	OrderDto findById(String id);
	
	void insertOrder(OrderDto orderDto);
	
	void upateOrder(OrderDto orderDto);
	
	/**
	 * 使用订单号来查询订单数据 
	 * @author 朱振	
	 * @date 2015年10月6日 下午12:09:09	
	 * @version 1.0
	 * @param orderNo
	 * @return
	 * <p>修改人：朱振</p>
	 * <p>修改时间：2015年10月6日 下午12:09:09</p>
	 * <p>修改备注：</p>
	 */
	OrderDto findByOrderNo(String orderNo);
	
	/**
	 * 
	 * countMonthlysales：统计商品的月销量
	 * @param shopId
	 * @param cafetoriumId
	 * @return
	 * @exception	
	 * @author pengcheng.yang
	 * @date 2015年10月8日 上午11:17:54
	 */
	int countMonthlysales(String shopId,String cafetoriumId);
}
