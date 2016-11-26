package com.ssic.catering.admin.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.ssic.catering.admin.dto.TImsMenuTypeDto;
import com.ssic.catering.admin.mapper.TImsMenuTypeExMapper;
import com.ssic.catering.admin.pojo.MenuType;

@Repository
public class TImsMenuTypeDao {

	@Autowired
	private TImsMenuTypeExMapper tImsMenuTypeExMapper;

	public List<TImsMenuTypeDto> finAll() {
		List<TImsMenuTypeDto> dtoList = tImsMenuTypeExMapper.findAll();
		return dtoList;
	}
	
	@Cacheable(value = "default", key = "'game.admin.pojo.MenuType:'+#menuTypeId")
	public MenuType getMenuTyoeById(String id) {
		return tImsMenuTypeExMapper.findById(id);
	}
}
