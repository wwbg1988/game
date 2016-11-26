package com.ssic.shop.manage.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.ssic.shop.manage.mapper.OrderExMapper;
import com.ssic.shop.manage.mapper.OrderMapper;
import com.ssic.shop.manage.pojo.Order;
import com.ssic.shop.manage.pojo.OrderExample;
import com.ssic.shop.manage.pojo.OrderExample.Criteria;
import com.ssic.util.constants.DataStatus;

@Repository
public class OrderDao {
   
	@Autowired
	private OrderMapper mapper;
	
	@Autowired
	private OrderExMapper exMapper;
	
	public List<Order> findBy(Order param){
		OrderExample example = new OrderExample();
		Criteria criteria = example.createCriteria();
		//id
		if(!StringUtils.isEmpty(param.getId()))
		{
		  criteria.andIdEqualTo(param.getId());
		}
		//orderNo
		if(!StringUtils.isEmpty(param.getOrderNo()))
		{
		  criteria.andOrderNoEqualTo(param.getOrderNo());
		}
		//orderStatus
		if(null != param.getOrderStatus())
		{
		  criteria.andOrderStatusEqualTo(param.getOrderStatus());
		}
		//shippingStatus
		if(null != param.getShippingStatus())
		{
		  criteria.andShippingStatusEqualTo(param.getShippingStatus());
		}
		//payWay
		if(null != param.getPayWay())
		{
		  criteria.andPayWayEqualTo(param.getPayWay());
		}
		//payStatus
		if(null != param.getPayStatus())
		{
		  criteria.andPayStatusEqualTo(param.getPayStatus());
		}
		//message
		if(!StringUtils.isEmpty(param.getMessage()))
		{
		  criteria.andMessageEqualTo(param.getMessage());
		}
		//goodsAmount
		if(!StringUtils.isEmpty(param.getGoodsAmount()))
		{
		  criteria.andGoodsAmountEqualTo(param.getGoodsAmount());
		}
		//openId
		if(!StringUtils.isEmpty(param.getOpenId()))
		{
		  criteria.andOpenIdEqualTo(param.getOpenId());
		}
		//cafetoriumId
		if(!StringUtils.isEmpty(param.getCafetoriumId()))
		{
		  criteria.andCafetoriumIdEqualTo(param.getCafetoriumId());
		}
		//createTime
		//payTime
		//lastUpdateTime
		//stat
		if(null != param.getStat())
		{
		  criteria.andStatEqualTo(DataStatus.ENABLED);
		}

	    example.setOrderByClause("create_time desc");
		return mapper.selectByExample(example);	
	}
	
	public Order findById(String id){
		return mapper.selectByPrimaryKey(id);
	}
	
	public void insertOrder(Order param){
		mapper.insertSelective(param);
	}
	
	public void upateOrder(Order param){
		mapper.updateByPrimaryKeySelective(param);
	}
	
	public Order findByOrderNo(String orderNo)
	{
	   return exMapper.selectByOrderNo(orderNo);
	}
	
	/**
	 * 
	 * countMonthlysales：统计商品的月销量
	 * @param shopId
	 * @param cafetoriumId
	 * @return
	 * @exception	
	 * @author pengcheng.yang
	 * @date 2015年10月8日 上午11:17:01
	 */
	public int countMonthlysales(String shopId,String cafetoriumId){
	    return exMapper.countMonthlysales(shopId, cafetoriumId);
	}
	
	
}
