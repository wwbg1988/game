package com.ssic.shop.manage.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.ssic.shop.manage.mapper.OrderDetailMapper;
import com.ssic.shop.manage.pojo.OrderDetail;
import com.ssic.shop.manage.pojo.OrderDetailExample;
import com.ssic.shop.manage.pojo.OrderDetailExample.Criteria;
import com.ssic.util.constants.DataStatus;


@Repository
public class OrderDetailDao {

	@Autowired
	private OrderDetailMapper mapper;
	
	public List<OrderDetail> findBy(OrderDetail param){
		OrderDetailExample example = new OrderDetailExample();
		Criteria criteria = example.createCriteria();
		if(!StringUtils.isEmpty(param.getOrderId())){
			criteria.andOrderIdEqualTo(param.getOrderId());
		}
		criteria.andStatEqualTo(DataStatus.ENABLED);
		return mapper.selectByExample(example);
	}
	
	public OrderDetail findById(String id){ 
		return mapper.selectByPrimaryKey(id);
	}
	
	public void insertOrderDetail (OrderDetail param){
		mapper.insertSelective(param);
	}
	
	public void updateOrderDetail(OrderDetail param){
		mapper.updateByPrimaryKeySelective(param);
	}
	
	
}
