package com.ssic.game.manage.service;

import java.util.List;

import com.ssic.game.admin.pageModel.PageHelper;
import com.ssic.game.common.dto.query.QueryDto;

public interface IQueryService {
	
	List<QueryDto> findAll(QueryDto queryDto);
	
	int findAllCount(QueryDto queryDto);
	
	List<QueryDto> findAllByPH(QueryDto queryDto,PageHelper ph);
	
	void insertQuery(QueryDto queryDto);
	
	QueryDto findById(String id);
	
	void updateQuery(QueryDto queryDto);
	
	void deleteQuery(QueryDto queryDto);
}
