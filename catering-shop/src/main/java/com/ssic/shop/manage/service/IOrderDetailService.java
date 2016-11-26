package com.ssic.shop.manage.service;

import java.util.List;

import com.ssic.shop.manage.dto.OrderDetailDto;

public interface IOrderDetailService {

	List<OrderDetailDto> findBy(OrderDetailDto orderDetailDto);
	
	OrderDetailDto findById(String id);
	 
	void insertOrderDetail(OrderDetailDto orderDetailDto);
	
	void updateOrderDetail(OrderDetailDto orderDetailDto);
	
	
	
} 
