package com.ssic.shop.manage.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.ssic.shop.manage.mapper.ConsigneeMapper;
import com.ssic.shop.manage.pojo.Consignee;
import com.ssic.shop.manage.pojo.ConsigneeExample;
import com.ssic.shop.manage.pojo.ConsigneeExample.Criteria;
import com.ssic.util.constants.DataStatus;


@Repository
public class ConsigneeDao {

	@Autowired
	private ConsigneeMapper mapper;
	
	public List<Consignee> findBy(Consignee param){
		ConsigneeExample example = new ConsigneeExample();
		Criteria criteria = example.createCriteria();
		if(!StringUtils.isEmpty(param.getId())){
			criteria.andIdEqualTo(param.getId());
		}
		criteria.andStatEqualTo(DataStatus.ENABLED);
		return mapper.selectByExample(example);
	}
	
	public Consignee findById(String id){
		return mapper.selectByPrimaryKey(id);
	}
	
	public void insertConsignee(Consignee param){
		mapper.insertSelective(param);
	}
	
	public void updateConsignee(Consignee param){
		mapper.updateByPrimaryKeySelective(param);
	}
	
}
