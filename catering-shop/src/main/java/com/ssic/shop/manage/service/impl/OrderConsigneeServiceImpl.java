package com.ssic.shop.manage.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssic.shop.manage.dao.OrderConsigneeDao;
import com.ssic.shop.manage.dto.OrderConsigneeDto;
import com.ssic.shop.manage.pojo.OrderConsignee;
import com.ssic.shop.manage.service.IOrderConsigneeService;
import com.ssic.util.BeanUtils;

@Service
public class OrderConsigneeServiceImpl implements IOrderConsigneeService{

	@Autowired
	private OrderConsigneeDao orderConsigneeDao;
	
	@Override
	public List<OrderConsigneeDto> findBy(OrderConsigneeDto orderConsigneeDto) {
		// TODO Auto-generated method stub
		OrderConsignee orderConsignee = new OrderConsignee();
		BeanUtils.copyProperties(orderConsigneeDto, orderConsignee);
		List<OrderConsignee> list=  orderConsigneeDao.findBy(orderConsignee);
		List<OrderConsigneeDto> listDto = BeanUtils.createBeanListByTarget(list, OrderConsigneeDto.class);
		return listDto;
	}

	@Override
	public OrderConsigneeDto findById(String id) {
		// TODO Auto-generated method stub
		OrderConsignee orderConsignee = orderConsigneeDao.findById(id);
		OrderConsigneeDto orderConsigneeDto = new OrderConsigneeDto();
		BeanUtils.copyProperties(orderConsignee, orderConsigneeDto);
		return orderConsigneeDto;
	}

	@Override
	public void inserOrderConsignee(OrderConsigneeDto orderConsigneeDto) {
		// TODO Auto-generated method stub
		OrderConsignee orderConsignee = new OrderConsignee();
		BeanUtils.copyProperties(orderConsigneeDto, orderConsignee);
		orderConsigneeDao.inserOrderConsignee(orderConsignee);
	}

	@Override
	public void updateOrderConsignee(OrderConsigneeDto orderConsigneeDto) {
		// TODO Auto-generated method stub
		OrderConsignee orderConsignee = new OrderConsignee();
		BeanUtils.copyProperties(orderConsigneeDto, orderConsignee);
		orderConsigneeDao.updateOrderConsignee(orderConsignee);
	}

	
	
	
	
}
