package com.ssic.shop.manage.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.ssic.shop.manage.mapper.OrderConsigneeMapper;
import com.ssic.shop.manage.pojo.OrderConsignee;
import com.ssic.shop.manage.pojo.OrderConsigneeExample;
import com.ssic.shop.manage.pojo.OrderConsigneeExample.Criteria;
import com.ssic.util.constants.DataStatus;


@Repository
public class OrderConsigneeDao {

	@Autowired
	private OrderConsigneeMapper mapper;
	
	
	public List<OrderConsignee> findBy(OrderConsignee param){
		OrderConsigneeExample example = new OrderConsigneeExample();
		Criteria criteria = example.createCriteria();
		if(!StringUtils.isEmpty(param.getId())){
			criteria.andIdEqualTo(param.getId());
		}
		criteria.andStatEqualTo(DataStatus.ENABLED);
		return mapper.selectByExample(example);	
	}
	
	public OrderConsignee findById(String id){
		return mapper.selectByPrimaryKey(id);
	}
	
	public void inserOrderConsignee(OrderConsignee param){
	    mapper.insertSelective(param);
	}
	
	public void updateOrderConsignee(OrderConsignee param){
		mapper.updateByPrimaryKeySelective(param);
	}
	
	
}
