/**
 * 
 */
package com.ssic.game.ims.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ssic.game.common.dao.FieldsDao;
import com.ssic.game.common.dao.ProjectDao;
import com.ssic.game.common.dao.QueryActionDao;
import com.ssic.game.common.dao.QueryConditionDao;
import com.ssic.game.common.dao.QueryDao;
import com.ssic.game.common.dao.QueryResultDao;
import com.ssic.game.common.dto.FieldsDto;
import com.ssic.game.common.dto.ProjectDto;
import com.ssic.game.common.dto.query.QueryActionDto;
import com.ssic.game.common.dto.query.QueryConditionDto;
import com.ssic.game.common.dto.query.QueryPage;
import com.ssic.game.common.dto.query.QueryPageList;
import com.ssic.game.common.dto.query.QueryResultDto;
import com.ssic.game.common.pojo.ImsQuery;
import com.ssic.game.common.pojo.ImsQueryAction;
import com.ssic.game.common.pojo.ImsQueryCondition;
import com.ssic.game.common.pojo.ImsQueryResult;
import com.ssic.game.ims.service.IQueryAchieveService;
import com.ssic.util.BeanUtils;
import com.ssic.util.constants.DataStatus;
import com.ssic.util.model.Response;

/**		
 * <p>Title: QuerySearchServiceImpl </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年7月28日 下午4:37:26	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年7月28日 下午4:37:26</p>
 * <p>修改备注：</p>
 */
@Service
public class QueryAchieveServiceImpl implements IQueryAchieveService
{
    @Autowired
    private QueryResultDao queryResultDao;
    
    @Autowired
    private QueryActionDao queryActionDao;  
    
    @Autowired
    private QueryConditionDao queryConditionDao;
    
    @Autowired
    private QueryDao queryDao;
    
    @Autowired
    private ProjectDao projectDao;
    
    @Autowired
    private FieldsDao fieldsDao;

    
     /** 
     * (non-Javadoc)   
     * @see com.ssic.game.ims.service.IQueryAchieveService#findQuery(java.lang.String)   
     */
    @Override
    public Response<QueryPage> findQuery(String queryId)
    {
        QueryPage queryPage = new QueryPage();
        ImsQuery imsQuery = queryDao.findById(queryId);
        if(imsQuery!=null){
            
            queryPage.setResults(findResultList(imsQuery.getProjectId(), queryId));
            
            queryPage.setActions(findActionList(imsQuery.getProjectId(), queryId));
            
            queryPage.setConditions(findCondition(imsQuery.getProjectId(), queryId));
            
            queryPage.setQueryId(queryId);
            
            return new Response<QueryPage>(DataStatus.HTTP_SUCCESS,"",queryPage);
            
            
            
        }else{
            return new Response<QueryPage>(DataStatus.HTTP_FAILE,"无法查询到query信息",null);
        }
        
    }
    
    //获取QueryResult列表
    private List<QueryResultDto> findResultList(String projId,String queryId){
        List<QueryResultDto> resultList = new ArrayList<QueryResultDto>();
        ImsQueryResult param = new ImsQueryResult();
        param.setProjectId(projId);
        param.setQueryId(queryId);
     
        List<ImsQueryResult> list =  queryResultDao.findBy(param);
        if(list!=null&&list.size()>0){
           List<QueryResultDto> tempList = BeanUtils.createBeanListByTarget(list, QueryResultDto.class);
           for(QueryResultDto result : tempList){
               if(!StringUtils.isEmpty(result.getProjectId())){
                   result.setProjDto(findProById(result.getProjectId()));
               }
               if(!StringUtils.isEmpty(result.getFieldsId())){
                   result.setFieldsDto(findFieldById(result.getFieldsId()));
               }
               resultList.add(result);
           }
           return resultList;
        }
        return null;
    }
    
    //获取QueryActionDto列表
    private List<QueryActionDto> findActionList(String projId,String queryId){
        List<QueryActionDto> resultList = new ArrayList<QueryActionDto>();
        ImsQueryAction param =new ImsQueryAction();
        param.setProjectId(projId);
        param.setQueryId(queryId);
        List<ImsQueryAction> list = queryActionDao.findBy(param);
        if(list!=null&&list.size()>0){
            List<QueryActionDto> tempList  =  BeanUtils.createBeanListByTarget(list, QueryActionDto.class);
            
            for(QueryActionDto result : tempList){
                if(!StringUtils.isEmpty(result.getProjectId())){
                    result.setProjDto(findProById(result.getProjectId()));
                }
                resultList.add(result);
            }
            
           
        }
        QueryActionDto imsQueryActions = new QueryActionDto();
        imsQueryActions.setUrl("/http/api/ims/search.do");
        imsQueryActions.setName("查询");
        imsQueryActions.setQueryId(queryId);
        resultList.add(imsQueryActions);
        
        QueryActionDto cleanQuery = new QueryActionDto();
        cleanQuery.setName("清空");
        cleanQuery.setQueryId(queryId);
        cleanQuery.setId("clean");
        resultList.add(cleanQuery);
        return resultList;
    }
    
    //获取QueryConditionDto列表
    private List<QueryConditionDto> findCondition(String projId,String queryId){
        List<QueryConditionDto> resultList= new ArrayList<QueryConditionDto>();
        ImsQueryCondition param = new ImsQueryCondition();
        param.setProjectId(projId);
        param.setQueryId(queryId);
        List<ImsQueryCondition> list = queryConditionDao.findBy(param);
        if(list!=null&&list.size()>0){
            List<QueryConditionDto> tempList=BeanUtils.createBeanListByTarget(list, QueryConditionDto.class);
            for(QueryConditionDto result: tempList){
                if(!StringUtils.isEmpty(result.getProjectId())){
                    result.setProjDto(findProById(result.getProjectId()));
                }
                if(!StringUtils.isEmpty(result.getFieldsId())){
                    result.setFieldsDto(findFieldById(result.getFieldsId()));
                }
                resultList.add(result);
            }
            return resultList;
        }
        return null;
    }
    
    private ProjectDto findProById(String projId){
        return projectDao.findById(projId);
    }
    private FieldsDto findFieldById(String fieldId){
        return fieldsDao.findById(fieldId);
    }

    
     /** 
     * (non-Javadoc)   
     * @see com.ssic.game.ims.service.IQueryAchieveService#findList(java.lang.String)   
     */
    @Override
    public Response<List<QueryPageList>> findList(String project)
    {
        List<QueryPageList> result = new ArrayList<QueryPageList>();
        ImsQuery imsQuery = new ImsQuery();
        imsQuery.setProjectId(project);
        List<ImsQuery> tempList = queryDao.findBy(imsQuery);
        if(tempList!=null&&tempList.size()>0){
            for(ImsQuery imsQuerys : tempList){
                QueryPageList temps = new QueryPageList();
                temps.setQueryId(imsQuerys.getId());
                temps.setQueryName(imsQuerys.getName());
                String url ="/http/api/ims/achieveQueryPage.do?queryId="+imsQuerys.getId();
                temps.setUrl(url);
                result.add(temps);
            }
            return new Response<List<QueryPageList>>(DataStatus.HTTP_SUCCESS,"查询成功",result);
        }
        return  new Response<List<QueryPageList>>(DataStatus.HTTP_FAILE,"查询失败",null);
    }

    

}

