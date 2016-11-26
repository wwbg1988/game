/**
 * 
 */
package com.ssic.game.manage.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ssic.game.admin.pageModel.DataGrid;
import com.ssic.game.admin.pageModel.PageHelper;
import com.ssic.game.admin.service.ProjectService;
import com.ssic.game.common.dao.QueryConditionDao;
import com.ssic.game.common.dto.FieldsDto;
import com.ssic.game.common.dto.PageHelperDto;
import com.ssic.game.common.dto.ProjectDto;
import com.ssic.game.common.dto.query.QueryConditionDto;
import com.ssic.game.common.dto.query.QueryDto;
import com.ssic.game.common.pojo.ImsQueryCondition;
import com.ssic.game.common.util.DeptComparator;
import com.ssic.game.manage.service.IFieldsService;
import com.ssic.game.manage.service.IQueryConditionService;
import com.ssic.game.manage.service.IQueryService;
import com.ssic.util.BeanUtils;
import com.ssic.util.constants.DataStatus;

/**		
 * <p>Title: QueryConditionServiceImpl </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年7月29日 下午3:18:09	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年7月29日 下午3:18:09</p>
 * <p>修改备注：</p>
 */
@Service
public class QueryConditionServiceImpl implements IQueryConditionService
{
    @Autowired
    private QueryConditionDao conditiondao;
    @Autowired
    private IQueryService queryService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private IFieldsService fieldsService;

    /** 
    * (non-Javadoc)   
    * @see com.ssic.game.manage.service.IQueryConditionService#dataGrid(com.ssic.game.common.dto.query.QueryConditionDto, com.ssic.game.admin.pageModel.PageHelper)   
    */
    @Override
    public DataGrid dataGrid(QueryConditionDto conditionDto, PageHelper ph)
    {
        int beginRow = (ph.getPage() - 1) * ph.getRows();
        ph.setBeginRow(beginRow);
        PageHelperDto phDto = new PageHelperDto();
        BeanUtils.copyProperties(ph, phDto);
        ImsQueryCondition con = new ImsQueryCondition();
        BeanUtils.copyProperties(conditionDto, con);
        DataGrid dg = new DataGrid();
        List<QueryConditionDto> listDto = new ArrayList<QueryConditionDto>();
        List<ImsQueryCondition> list = conditiondao.findAll(con, phDto);

        for (ImsQueryCondition condition : list)
        {
            QueryConditionDto dto = new QueryConditionDto();
            BeanUtils.copyProperties(condition, dto);
            if (!StringUtils.isEmpty(condition.getQueryId()))
            {

                QueryDto queryDto = queryService.findById(condition.getQueryId());
                dto.setQueryName(queryDto.getName());
            }
            else
            {
                dto.setQueryName("");
            }
            if (!StringUtils.isEmpty(condition.getProjectId()))
            {

                ProjectDto projectDto = projectService.findById(condition.getProjectId());
                dto.setProjName(projectDto.getProjName());
            }
            else
            {
                dto.setProjName("");
            }
            if (!StringUtils.isEmpty(condition.getFieldsId()))
            {

                FieldsDto fieldDto = fieldsService.findById(condition.getFieldsId());
                dto.setFieldParamName(fieldDto.getParamName());
                dto.setFieldParamDesc(fieldDto.getParamDesc());
            }
            else
            {
                dto.setFieldParamName("");
            }
            listDto.add(dto);
        }
        int counts = conditiondao.findCountBy(con);
        dg.setTotal(Long.valueOf(counts));
        Collections.sort(listDto, new DeptComparator());
        dg.setRows(listDto);
        return dg;
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.game.manage.service.IQueryConditionService#vaildField(com.ssic.game.common.dto.query.QueryConditionDto)   
    */
    @Override
    public int vaildField(QueryConditionDto tempConditionDto)
    {
        ImsQueryCondition condition = new ImsQueryCondition();
        BeanUtils.copyProperties(tempConditionDto, condition);
        int count = conditiondao.findOptCount(condition);
        if (count > 0)
        {
            return count;
        }
        return -1;
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.game.manage.service.IQueryConditionService#add(com.ssic.game.common.dto.query.QueryConditionDto)   
    */
    @Override
    public void add(QueryConditionDto conditionDto)
    {
        ImsQueryCondition condition = new ImsQueryCondition();
        BeanUtils.copyProperties(conditionDto, condition);
        conditiondao.addQueryCondition(condition);

    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.game.manage.service.IQueryConditionService#findById(java.lang.String)   
    */
    @Override
    public QueryConditionDto findById(String id)
    {
        ImsQueryCondition con = conditiondao.selectByPrimaryKey(id);
        QueryConditionDto dto = new QueryConditionDto();
        BeanUtils.copyProperties(con, dto);
        return dto;
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.game.manage.service.IQueryConditionService#vaildOrder(com.ssic.game.common.dto.query.QueryConditionDto)   
    */
    @Override
    public int vaildOrder(QueryConditionDto tempConditionDto)
    {
        ImsQueryCondition condition = new ImsQueryCondition();
        BeanUtils.copyProperties(tempConditionDto, condition);
        //查正序
        int i = conditiondao.findOrderAsc(condition);
        //查倒序
        int j = conditiondao.findOrderDesc(condition);
        return i + j;
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.game.manage.service.IQueryConditionService#validConditionName(java.lang.String)   
    */
    @Override
    public int validConditionName(String name)
    {

        ImsQueryCondition condition = new ImsQueryCondition();
        condition.setName(name);
        //查正序
        int count = conditiondao.findConditionName(condition);
        return count;
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.game.manage.service.IQueryConditionService#update(com.ssic.game.common.dto.query.QueryConditionDto)   
    */
    @Override
    public void update(QueryConditionDto conditionDto)
    {
        ImsQueryCondition condition = new ImsQueryCondition();
        BeanUtils.copyProperties(conditionDto, condition);
        conditiondao.updateQueryCondition(condition);

    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.game.manage.service.IQueryConditionService#validEditConditionName(java.lang.String, java.lang.String)   
    */
    @Override
    public int validEditConditionName(String name, String id)
    {
        ImsQueryCondition condition = new ImsQueryCondition();
        condition.setName(name);
        //查正序
        int count = conditiondao.findEditConditionName(condition, id);
        return count;
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.game.manage.service.IQueryConditionService#delete(java.lang.String)   
    */
    @Override
    public void delete(String queryConditionId)
    {

        ImsQueryCondition condition = conditiondao.selectByPrimaryKey(queryConditionId);
        condition.setStat(DataStatus.DISABLED);
        conditiondao.updateQueryCondition(condition);
    }
}
