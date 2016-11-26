package com.ssic.shop.manage.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssic.shop.manage.dao.ConsigneeDao;
import com.ssic.shop.manage.dto.ConsigneeDto;
import com.ssic.shop.manage.pojo.Consignee;
import com.ssic.shop.manage.service.IConsigneeService;
import com.ssic.util.BeanUtils;

@Service
public class ConsigneeServiceImpl implements IConsigneeService{

	@Autowired
	private ConsigneeDao consigneeDao;
	
	@Override
	public List<ConsigneeDto> findBy(ConsigneeDto consigneeDto) {
		// TODO Auto-generated method stub
		Consignee consignee = new Consignee();
		List<Consignee> list= consigneeDao.findBy(consignee);
		List<ConsigneeDto> listDto = BeanUtils.createBeanListByTarget(list, ConsigneeDto.class);
		return listDto;
	}

	@Override
	public ConsigneeDto findById(String id) {
		// TODO Auto-generated method stub
		Consignee consignee = consigneeDao.findById(id);
		ConsigneeDto consigneeDto = new ConsigneeDto();
		BeanUtils.copyProperties(consignee, consigneeDto);
		return consigneeDto;
	}

	@Override
	public void insertConsignee(ConsigneeDto consigneeDto) {
		// TODO Auto-generated method stub
		Consignee consignee = new Consignee();
		BeanUtils.copyProperties(consigneeDto, consignee);
		consigneeDao.insertConsignee(consignee);
	}

	@Override
	public void updateConsignee(ConsigneeDto consigneeDto) {
		// TODO Auto-generated method stub
		Consignee consignee = new Consignee();
		BeanUtils.copyProperties(consigneeDto, consignee);
		consigneeDao.updateConsignee(consignee);
	}

}
