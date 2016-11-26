package com.ssic.shop.manage.service;

import java.util.List;

import com.ssic.shop.manage.dto.OrderConsigneeDto;

public interface IOrderConsigneeService {

	List<OrderConsigneeDto> findBy(OrderConsigneeDto orderConsigneeDto);
	
	OrderConsigneeDto findById(String id);
	
	void inserOrderConsignee(OrderConsigneeDto orderConsigneeDto);
	
	void updateOrderConsignee (OrderConsigneeDto orderConsigneeDto);
	
}
