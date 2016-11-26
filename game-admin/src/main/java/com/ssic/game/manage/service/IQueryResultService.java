package com.ssic.game.manage.service;

import java.util.List;

import com.ssic.game.admin.pageModel.PageHelper;
import com.ssic.game.common.dto.query.QueryResultDto;

public interface IQueryResultService {
	 List<QueryResultDto> findBy(QueryResultDto queryResultDto);
	
	 void insertResult(QueryResultDto queryResultDto);
	 
	 List<QueryResultDto> findAllByPH(QueryResultDto queryResultDto,PageHelper ph);
	 
	 int findAllCount(QueryResultDto queryResultDto);
	 
	 void updateResult(QueryResultDto queryResultDto);
	 
	 void deleteResult(QueryResultDto queryResultDto);
}
