package com.ssic.game.manage.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssic.game.admin.pageModel.PageHelper;
import com.ssic.game.common.dao.ActionsDao;
import com.ssic.game.common.dao.ProcessDao;
import com.ssic.game.common.dao.ProjectDao;
import com.ssic.game.common.dao.QueryDao;
import com.ssic.game.common.dao.TasksDao;
import com.ssic.game.common.dto.ActionsDto;
import com.ssic.game.common.dto.PageHelperDto;
import com.ssic.game.common.dto.ProcessDto;
import com.ssic.game.common.dto.ProjectDto;
import com.ssic.game.common.dto.TasksDto;
import com.ssic.game.common.dto.query.QueryDto;
import com.ssic.game.common.pojo.Actions;
import com.ssic.game.common.pojo.ImsQuery;
import com.ssic.game.common.pojo.Process;
import com.ssic.game.manage.service.IQueryService;
import com.ssic.util.BeanUtils;

@Service
public class QueryServiceImpl implements IQueryService{
    @Autowired
	private QueryDao queryDao;
    @Autowired
    private ProjectDao projectDao;
    @Autowired
    private ProcessDao processDao;
    @Autowired
    private TasksDao tasksDao;
    @Autowired
    private ActionsDao actionsDao;

	@Override
	public List<QueryDto> findAll(QueryDto queryDto) {
		ImsQuery imsQuery = new ImsQuery();
		BeanUtils.copyProperties(queryDto, imsQuery);
		List<ImsQuery> listIms = queryDao.findBy(imsQuery);
		List<QueryDto> listQuery = new ArrayList<QueryDto>();
		if(listIms!=null && listIms.size()>0){
			for(ImsQuery imsQuery2:listIms){
				QueryDto queryDto2 = new QueryDto();
			    BeanUtils.copyProperties(imsQuery2, queryDto2);
				listQuery.add(queryDto2);
			}
		}
		return listQuery;
	}

	@Override
	public int findAllCount(QueryDto queryDto) {
		ImsQuery imsQuery = new ImsQuery();
		BeanUtils.copyProperties(queryDto, imsQuery);
		return queryDao.findAllCount(imsQuery);
	}

	@Override
	public List<QueryDto> findAllByPH(QueryDto queryDto,PageHelper ph) {
		PageHelperDto phdto = new PageHelperDto();
		 phdto.setOrder(ph.getOrder());
		 phdto.setPage(ph.getPage());
		 phdto.setRows(ph.getRows());
		 phdto.setSort(ph.getSort());
		 phdto.setBeginRow((ph.getPage()-1)*ph.getRows());
		 ImsQuery imsQuery = new ImsQuery();
		 BeanUtils.copyProperties(queryDto, imsQuery);
		 List<ImsQuery> listims = queryDao.findAllByPH(imsQuery,phdto);
		 List<QueryDto> result = BeanUtils.createBeanListByTarget(listims, QueryDto.class);	
		 
		 //查询项目名称，流程名称，节点名称，动作名称
		 if(result!=null && result.size()>0){
			 for(QueryDto query_1 : result){
				 String projid = query_1.getProjectId();
				 String procId = query_1.getProcessId();
				 String taskId = query_1.getTaskId();
				 String actionId = query_1.getActionId();
				 ProjectDto  projectDto = projectDao.findById(projid);
				 if(projectDto!=null){
					 query_1.setProjectName(projectDto.getProjName());
				 }
				 Process process =processDao.findById(procId);
				 if(process!=null){
					 query_1.setProcessName(process.getProcName());
				 }
				 TasksDto  tasksDto =tasksDao.findById(taskId);
				 if(tasksDto!=null){
					 query_1.setTaskName(tasksDto.getName());
				 }
				 Actions actions = new Actions();
				 actions.setId(actionId);
				 List<Actions> listactions=   actionsDao.findAllBy(actions);
				 if(listactions!=null && listactions.size()>0){
					 query_1.setActionName(listactions.get(0).getName());
				 }
			 }
		 }
		 
		 
         return result;
	}

	@Override
	public void insertQuery(QueryDto queryDto) {
		ImsQuery imsQuery = new ImsQuery();
		BeanUtils.copyProperties(queryDto, imsQuery);
		queryDao.insertQuery(imsQuery);
	}

	@Override
	public QueryDto findById(String id) {
	  ImsQuery imsQuery=	queryDao.findById(id);
	  QueryDto queryDto = new QueryDto();
	  BeanUtils.copyProperties(imsQuery, queryDto);
		return queryDto;
	}

	@Override
	public void updateQuery(QueryDto queryDto) {
		ImsQuery imsQuery = new ImsQuery();
		BeanUtils.copyProperties(queryDto, imsQuery);
//		queryDao.updateByPrimaryKey(imsQuery);
		queryDao.updateByPrimaryKeySelective(imsQuery);
	}

	@Override
	public void deleteQuery(QueryDto queryDto) {
		// TODO Auto-generated method stub
		String id = queryDto.getId();
		queryDao.deleteByPrimaryKey(id);
	}
	
	
	

}
