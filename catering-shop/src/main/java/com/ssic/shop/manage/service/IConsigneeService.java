package com.ssic.shop.manage.service;

import java.util.List;

import com.ssic.shop.manage.dto.ConsigneeDto;

public interface IConsigneeService {

	  /*
	   * 物流信息处理类
	   * 
	   */
	  List<ConsigneeDto> findBy(ConsigneeDto consigneeDto);
	  
	  ConsigneeDto findById(String id);
	  
	  void insertConsignee(ConsigneeDto consigneeDto);
	  
	  void updateConsignee(ConsigneeDto consigneeDto);
}
