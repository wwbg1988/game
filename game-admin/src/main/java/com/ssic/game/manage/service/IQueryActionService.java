package com.ssic.game.manage.service;

import java.util.List;

import com.ssic.game.admin.pageModel.PageHelper;
import com.ssic.game.common.dto.query.QueryActionDto;

public interface IQueryActionService {

	List<QueryActionDto> findBy(QueryActionDto queryActionDto);
	
	List<QueryActionDto> findAllByPH(QueryActionDto queryActionDto,PageHelper ph);
	
	int findAllCount(QueryActionDto queryActionDto);
	
	void insertAction(QueryActionDto queryActionDto);
	
	void updateAction(QueryActionDto queryActionDto);
	
	void deleteAction(QueryActionDto queryActionDto);
	
	QueryActionDto findById(String id);
}
