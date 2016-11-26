/**
 * 
 */
package com.ssic.game.ims.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.mysql.fabric.xmlrpc.base.Array;
import com.ssic.game.common.constant.QueryConditionType;
import com.ssic.game.common.dao.FieldsDao;
import com.ssic.game.common.dao.FormsDao;
import com.ssic.game.common.dao.QueryConditionDao;
import com.ssic.game.common.dao.QueryDao;
import com.ssic.game.common.dao.TasksDao;
import com.ssic.game.common.dto.FieldsDto;
import com.ssic.game.common.dto.PageHelperDto;
import com.ssic.game.common.dto.TasksDto;
import com.ssic.game.common.pojo.ImsQuery;
import com.ssic.game.common.pojo.ImsQueryCondition;
import com.ssic.game.ims.model.FormDataQuerys;
import com.ssic.game.ims.model.FormDataResults;
import com.ssic.game.ims.service.IQuerySearchService;
import com.ssic.ims.documents.FormData;
import com.ssic.ims.dto.Condition;
import com.ssic.ims.dto.FormDataQuery;
import com.ssic.ims.dto.Opt;
//import com.ssic.ims.dto.FormDataQuery;
import com.ssic.ims.service.IActionRecordService;
import com.ssic.ims.service.IFormDataQueryService;
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
public class QuerySearchServiceImpl implements IQuerySearchService
{

    @Autowired
    private QueryConditionDao queryConditionDao;

    @Autowired
    private TasksDao tasksDao;

    @Autowired
    private QueryDao queryDao;

    @Autowired
    private FormsDao formsDao;
    
    @Autowired
    private IActionRecordService recordService;
    
    @Autowired
    private FieldsDao fieldsDao;

    @Autowired
    private IFormDataQueryService queryService;
    

    /** 
    * (non-Javadoc)   
    * @see com.ssic.game.ims.service.IQuerySearchService#findBy(com.ssic.game.ims.model.FormDataQuery, com.ssic.game.common.dto.PageHelperDto)   
    * Author:milkteaye
    */
    @Override
    public Response<FormDataResults> findBy(FormDataQuerys query, PageHelperDto ph)
    {
        FormDataResults results = new FormDataResults();
        if (valiEmpty(query, ph) == false)
        {
            return new Response<FormDataResults>(DataStatus.HTTP_FAILE, "传输参数不能为空", null);
        }
        ImsQuery imsQuery = queryDao.findById(query.getQueryId());
        if (imsQuery != null)
        {
            //查询TASKID
            if (!StringUtils.isEmpty(imsQuery.getTaskId()))
            {
                TasksDto tasksDto = tasksDao.findById(imsQuery.getTaskId());
                if (tasksDto != null)
                {
                    //查询FORM表单ID
                    String formId = tasksDto.getFormId();
                    if (!StringUtils.isEmpty(formId))
                    {
                        //查询
                        Page<FormData> tempPage =  findFormData(tasksDto.getProjId(), formId, ph, query);
                        if(tempPage!=null&&tempPage.getContent()!=null&&tempPage.getContent().size()>0){
                            List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
                            for(FormData formData :tempPage.getContent()){
                                resultList.add(formData.getValues());
                            }
                            results.setResults(resultList);
                            return new Response<FormDataResults>(DataStatus.HTTP_SUCCESS, "查询成功!", results);
                        }
                    }
                }
            }
        }
        else
        {
            return new Response<FormDataResults>(DataStatus.HTTP_FAILE, "查询失败!", null);
        }

        return null;
    }
    
    /**
     * 
     * 查询findFormData数据
     * @param userId
     * @param procInstanceId
     * @param formId
     * @return
     * @exception   
     * @author milkteaye
     * @date 2015年7月22日 上午10:50:18
     */
    private Page<FormData> findFormData (String projId,String formId,PageHelperDto ph,FormDataQuerys formQuery)
    {
        FormDataQuery querys = new FormDataQuery();
        Map<String,Object> map = formQuery.getFields();
        ImsQueryCondition params = new ImsQueryCondition();
        params.setProjectId(projId);
        params.setQueryId(formQuery.getQueryId());
        List<ImsQueryCondition> lists= queryConditionDao.findBy(params);
        
        if(map!=null&&lists!=null&&lists.size()>0){
            for(String key  :map.keySet()){
                
                    for(ImsQueryCondition ims : lists){
                        FieldsDto fieldsDto = fieldsDao.findById(ims.getFieldsId());
                        //匹配成功并且opt状态不为order by ,like的情况
                        if(fieldsDto.getParamName().equals(key)&&!"".equals(map.get(key))&&map.get(key)!=null&&(ims.getOpt()!=QueryConditionType.LIKE||ims.getOpt()!=QueryConditionType.ORDERBY_ASC||ims.getOpt()!=QueryConditionType.ORDERBY_DESC)){
                          
                            if(fieldsDto!=null){
                                int opts = ims.getOpt();
                                querys.addFieldsCondition(new Condition(key, getOpt(opts),map.get(key)));
                            }
                         
                            break;
                        }else if(fieldsDto.getParamName().equals(key)&&!"".equals(map.get(key))&&map.get(key)!=null&&ims.getOpt()==QueryConditionType.LIKE){  //匹配成功且opt状态为like
//                          querys.addFieldsCondition(new Condition());
                            break;
                            
                      }else if(fieldsDto.getParamName().equals(key)&&(ims.getOpt()==QueryConditionType.ORDERBY_ASC||ims.getOpt()==QueryConditionType.ORDERBY_DESC)){//陪陪成功且OPT状态为order by的情况
                          if(ims.getOpt()==QueryConditionType.ORDERBY_ASC){
                              querys.addOrder(key, Direction.ASC);
                          }else{
                              querys.addOrder(key, Direction.DESC);
                          }
                          break;
                      }
                }

            }
            int currRows = ph.getPage()*ph.getRows();
            int nextRows = (ph.getPage()+1)*ph.getRows();
            
           return queryService.findByPage(querys, new PageRequest(currRows,  nextRows));
        }
       
      return null; 
    }
    /** 
    * (non-Javadoc)   
    * @see com.ssic.game.ims.service.IQuerySearchService#findBy(com.ssic.game.ims.model.FormDataQuery, com.ssic.game.common.dto.PageHelperDto)   
    * Author:milkteaye
    */
    private Opt getOpt(int opts){
        if(opts==1){
            return Opt.eq;
        }
        if(opts==2){
            return Opt.lt;
        }
        if(opts==3){
            return Opt.lte;
        }
        if(opts==4){
            return Opt.gt;
        }
        if(opts==5){
            return Opt.gte;
        }
        return null;
    }
    
    
    /** 
    * (non-Javadoc)   
    * @see com.ssic.game.ims.service.IQuerySearchService#findBy(com.ssic.game.ims.model.FormDataQuery, com.ssic.game.common.dto.PageHelperDto)   
    * Author:milkteaye
    *     //非空判断
    */

    private boolean valiEmpty(FormDataQuerys query, PageHelperDto ph)
    {
        if (StringUtils.isEmpty(query.getQueryId()))
        {
            return false;
        }
//        if (StringUtils.isEmpty(query.getFields()))
//        {
//            return false;
//        }
        if (ph.getRows() == 0)
        {
            return false;
        }
        return true;
    }

}
