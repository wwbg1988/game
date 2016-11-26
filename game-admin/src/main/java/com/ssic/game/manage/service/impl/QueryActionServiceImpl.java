package com.ssic.game.manage.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssic.game.admin.pageModel.PageHelper;
import com.ssic.game.admin.service.ProjectService;
import com.ssic.game.common.dao.QueryActionDao;
import com.ssic.game.common.dto.FieldsDto;
import com.ssic.game.common.dto.PageHelperDto;
import com.ssic.game.common.dto.ProjectDto;
import com.ssic.game.common.dto.query.QueryActionDto;
import com.ssic.game.common.dto.query.QueryDto;
import com.ssic.game.common.dto.query.QueryResultDto;
import com.ssic.game.common.pojo.ImsQuery;
import com.ssic.game.common.pojo.ImsQueryAction;
import com.ssic.game.manage.service.IQueryActionService;
import com.ssic.game.manage.service.IQueryService;
import com.ssic.util.BeanUtils;

@Service
public class QueryActionServiceImpl implements IQueryActionService{

	@Autowired
	private QueryActionDao queryActionDao;
	@Autowired
	private ProjectService  projectService;
	@Autowired
	private IQueryService queryService;

	@Override
	public List<QueryActionDto> findBy(QueryActionDto queryActionDto) {
		// TODO Auto-generated method stub
		List<QueryActionDto> listdto = new ArrayList<QueryActionDto>();
		ImsQueryAction imsQueryAction = new ImsQueryAction();
		BeanUtils.copyProperties(queryActionDto, imsQueryAction);
		List<ImsQueryAction> listaction= queryActionDao.findBy(imsQueryAction);
		List<QueryActionDto> listaction3=  BeanUtils.createBeanListByTarget(listaction, QueryActionDto.class);
		return listaction3;
	}

	@Override
	public List<QueryActionDto> findAllByPH(QueryActionDto queryActionDto,
			PageHelper ph) {
		 PageHelperDto phdto = new PageHelperDto();
		 phdto.setOrder(ph.getOrder());
		 phdto.setPage(ph.getPage());
		 phdto.setRows(ph.getRows());
		 phdto.setSort(ph.getSort());
		 phdto.setBeginRow((ph.getPage()-1)*ph.getRows());
		 ImsQueryAction imsQueryAction = new ImsQueryAction();
		 BeanUtils.copyProperties(queryActionDto, imsQueryAction);
		 List<ImsQueryAction> listaction=  queryActionDao.findAllByPH(imsQueryAction, phdto);
		 List<QueryActionDto> result=  BeanUtils.createBeanListByTarget(listaction, QueryActionDto.class);
		 
		 //查询项目名称，查询名称，字段名称
		  if(result!=null && result.size()>0){
			  for(QueryActionDto queryActionDto1:result){
				  String projid = queryActionDto1.getProjectId();
				  String queryid = queryActionDto1.getQueryId();
				  ProjectDto  projectDto = projectService.findById(projid);
				  queryActionDto1.setProjectName(projectDto.getProjName());
				  QueryDto queryDto = queryService.findById(queryid);
				  queryActionDto1.setQueryName(queryDto.getName());
			  }
		  }
		 
		return result;
	}

	@Override
	public int findAllCount(QueryActionDto queryActionDto) {
		// TODO Auto-generated method stub
		ImsQueryAction imsQueryAction = new ImsQueryAction();
		BeanUtils.copyProperties(queryActionDto, imsQueryAction);
		return queryActionDao.findAllCount(imsQueryAction);
	}

	@Override
	public void insertAction(QueryActionDto queryActionDto) {
		// TODO Auto-generated method stub
		ImsQueryAction imsQueryAction = new ImsQueryAction();
		BeanUtils.copyProperties(queryActionDto, imsQueryAction);
		queryActionDao.insertAction(imsQueryAction);
	}

	@Override
	public void updateAction(QueryActionDto queryActionDto) {
		// TODO Auto-generated method stub
		ImsQueryAction imsQueryAction = new ImsQueryAction();
		BeanUtils.copyProperties(queryActionDto, imsQueryAction);
		queryActionDao.updateAction(imsQueryAction);
	}

	@Override
	public void deleteAction(QueryActionDto queryActionDto) {
		// TODO Auto-generated method stub
	    String id = queryActionDto.getId();
		queryActionDao.deleteAction(id);
	}

	@Override
	public QueryActionDto findById(String id) {
		// TODO Auto-generated method stub
		ImsQueryAction imsQueryAction = queryActionDao.findById(id);
		QueryActionDto queryActionDto = new QueryActionDto();
		BeanUtils.copyProperties(imsQueryAction, queryActionDto);
		return queryActionDto;
	}
	
	
	
	
}
