package com.ssic.shop.manage.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssic.shop.manage.dao.OrderDetailDao;
import com.ssic.shop.manage.dto.OrderDetailDto;
import com.ssic.shop.manage.pojo.OrderDetail;
import com.ssic.shop.manage.service.IOrderDetailService;
import com.ssic.util.BeanUtils;

@Service
public class OrderDetailServiceImpl implements IOrderDetailService{

	@Autowired
	private OrderDetailDao orderDetailDao;
	
	@Override
	public List<OrderDetailDto> findBy(OrderDetailDto orderDetailDto) {
		// TODO Auto-generated method stub
        OrderDetail orderDetail = new OrderDetail();
        BeanUtils.copyProperties(orderDetailDto, orderDetail);
        List<OrderDetail> list=  orderDetailDao.findBy(orderDetail);		
		List<OrderDetailDto> listDto = BeanUtils.createBeanListByTarget(list, OrderDetailDto.class);
		return listDto;
	}

	@Override
	public OrderDetailDto findById(String id) {
		// TODO Auto-generated method stub
		OrderDetail  orderDetail =  orderDetailDao.findById(id);
		OrderDetailDto orderDetailDto = new OrderDetailDto();
		BeanUtils.copyProperties(orderDetail, orderDetailDto);
		return orderDetailDto;
	}

	@Override
	public void insertOrderDetail(OrderDetailDto orderDetailDto) {
		// TODO Auto-generated method stub
		OrderDetail orderDetail = new OrderDetail();
		BeanUtils.copyProperties(orderDetailDto, orderDetail);
        orderDetailDao.insertOrderDetail(orderDetail);		
	}

	@Override
	public void updateOrderDetail(OrderDetailDto orderDetailDto) {
		// TODO Auto-generated method stub
		OrderDetail orderDetail = new OrderDetail();
		BeanUtils.copyProperties(orderDetailDto, orderDetail);
		orderDetailDao.updateOrderDetail(orderDetail);
	}

}
