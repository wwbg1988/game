package com.ssic.shop.manage.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssic.shop.manage.dao.OrderDao;
import com.ssic.shop.manage.dto.OrderDto;
import com.ssic.shop.manage.pojo.Order;
import com.ssic.shop.manage.service.IOrderService;
import com.ssic.util.BeanUtils;

@Service
public class OrderServiceImpl implements IOrderService{

	@Autowired
	private OrderDao orderDao;
	
	@Override
	public List<OrderDto> findBy(OrderDto orderDto) {
		// TODO Auto-generated method stub
		Order order = new Order();
		BeanUtils.copyProperties(orderDto, order);
		List<Order> list=  orderDao.findBy(order);
        List<OrderDto> listDto = BeanUtils.createBeanListByTarget(list, OrderDto.class);
		return listDto;
	}

	@Override
	public OrderDto findById(String id) {
		// TODO Auto-generated method stub
		Order order= orderDao.findById(id);
		OrderDto orderDto = new OrderDto();
		BeanUtils.copyProperties(order, orderDto);
		return orderDto;
	}

	@Override
	public void insertOrder(OrderDto orderDto) {
		// TODO Auto-generated method stub
		Order order= new Order();
		BeanUtils.copyProperties(orderDto, order);
		orderDao.insertOrder(order);
	}

	@Override
	public void upateOrder(OrderDto orderDto) {
		// TODO Auto-generated method stub
		Order order= new Order();
		BeanUtils.copyProperties(orderDto, order);
		order.setOrderStatus(orderDto.getOrderStatus());
		orderDao.upateOrder(order);
	}
	
	
	@Override
	public OrderDto findByOrderNo(String orderNo)
	{
	    OrderDto result = null;
	    
	    Order order = orderDao.findByOrderNo(orderNo);
	    if(order != null)
	    {
	        result = new OrderDto();
	        BeanUtils.copyProperties(order, result);
	    }
	    
	    return result;

	}

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
    @Override
    public int countMonthlysales(String shopId, String cafetoriumId)
    {
        return  orderDao.countMonthlysales(shopId, cafetoriumId);
    }

}
