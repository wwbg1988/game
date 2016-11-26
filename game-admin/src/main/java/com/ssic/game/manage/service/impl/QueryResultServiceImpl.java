package com.ssic.game.manage.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssic.game.admin.pageModel.PageHelper;
import com.ssic.game.admin.service.ProjectService;
import com.ssic.game.common.dao.QueryResultDao;
import com.ssic.game.common.dto.FieldsDto;
import com.ssic.game.common.dto.PageHelperDto;
import com.ssic.game.common.dto.ProjectDto;
import com.ssic.game.common.dto.query.QueryDto;
import com.ssic.game.common.dto.query.QueryResultDto;
import com.ssic.game.common.pojo.ImsQueryResult;
import com.ssic.game.manage.service.IFieldsService;
import com.ssic.game.manage.service.IQueryResultService;
import com.ssic.game.manage.service.IQueryService;
import com.ssic.util.BeanUtils;

@Service
public class QueryResultServiceImpl implements IQueryResultService{

	@Autowired
	private QueryResultDao queryResultDao;
	@Autowired
	private ProjectService projectService;
	@Autowired
	private IQueryService  queryService;
	@Autowired
	private IFieldsService fieldsService;
	
	
	public List<QueryResultDto> findBy(QueryResultDto queryResultDto){
		List<QueryResultDto> listresultdto = new ArrayList<QueryResultDto>();
	   ImsQueryResult imsQueryResult = new ImsQueryResult();
	   BeanUtils.copyProperties(queryResultDto, imsQueryResult);
	   List<ImsQueryResult> listresult=	queryResultDao.findBy(imsQueryResult);
	  if(listresult!=null && listresult.size()>0){
		  for(ImsQueryResult queryResult:listresult){
			  QueryResultDto queryResultDto2 = new QueryResultDto();
			  BeanUtils.copyProperties(queryResult, queryResultDto2);
			  listresultdto.add(queryResultDto2);
		  }
	  }
	  return listresultdto;
	}


	@Override
	public void insertResult(QueryResultDto queryResultDto) {
		// TODO Auto-generated method stub
		ImsQueryResult imsQueryResult = new ImsQueryResult();
		BeanUtils.copyProperties(queryResultDto, imsQueryResult);
		queryResultDao.insertResult(imsQueryResult);
	}


	@Override
	public List<QueryResultDto> findAllByPH(QueryResultDto queryResultDto,
			PageHelper ph) {
		// TODO Auto-generated method stub
		 PageHelperDto phdto = new PageHelperDto();
		 phdto.setOrder(ph.getOrder());
		 phdto.setPage(ph.getPage());
		 phdto.setRows(ph.getRows());
		 phdto.setSort(ph.getSort());
		 phdto.setBeginRow((ph.getPage()-1)*ph.getRows());
		 
		 ImsQueryResult imsQueryResult = new ImsQueryResult();
		 BeanUtils.copyProperties(queryResultDto, imsQueryResult);
		 List<ImsQueryResult> listresult = queryResultDao.findAllByPH(imsQueryResult,phdto);
		 List<QueryResultDto> result = BeanUtils.createBeanListByTarget(listresult,QueryResultDto.class);
		 
		 //查询项目名称，查询名称，字段名称
		  if(result!=null && result.size()>0){
			  for(QueryResultDto QueryResult1:result){
				  String projid = QueryResult1.getProjectId();
				  String queryid = QueryResult1.getQueryId();
				  String fields = QueryResult1.getFieldsId();
				  ProjectDto  projectDto = projectService.findById(projid);
				  QueryResult1.setProjectName(projectDto.getProjName());
				  QueryDto queryDto = queryService.findById(queryid);
				  QueryResult1.setQueryName(queryDto.getName());
				  FieldsDto fieldsDto = fieldsService.findById(fields);
				  QueryResult1.setFieldsName(fieldsDto.getParamDesc());
			  }
			  
		  }
		 
		 
		return result;
	}


	@Override
	public int findAllCount(QueryResultDto queryResultDto) {
		// TODO Auto-generated method stub
		ImsQueryResult imsQueryResult = new ImsQueryResult();
		BeanUtils.copyProperties(queryResultDto, imsQueryResult);
		return queryResultDao.findAllCount(imsQueryResult);
	}


	@Override
	public void updateResult(QueryResultDto queryResultDto) {
		// TODO Auto-generated method stub
		ImsQueryResult imsQueryResult = new ImsQueryResult();
		BeanUtils.copyProperties(queryResultDto, imsQueryResult);
		queryResultDao.updateResult(imsQueryResult);
	}


	@Override
	public void deleteResult(QueryResultDto queryResultDto) {
		// TODO Auto-generated method stub
		String id = queryResultDto.getId();
		queryResultDao.deleteResult(id);
	}
	
}
