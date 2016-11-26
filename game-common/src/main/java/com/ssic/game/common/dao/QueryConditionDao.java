/**
 * 
 */
package com.ssic.game.common.dao;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.ssic.game.common.constant.QueryConditionType;
import com.ssic.game.common.dto.FieldsDto;
import com.ssic.game.common.dto.PageHelperDto;
import com.ssic.game.common.dto.query.QueryConditionDto;
import com.ssic.game.common.mapper.ImsQueryConditionMapper;
import com.ssic.game.common.pojo.Fields;
import com.ssic.game.common.pojo.FieldsExample;
import com.ssic.game.common.pojo.FiledsClone;
import com.ssic.game.common.pojo.Forms;
import com.ssic.game.common.pojo.ImsQueryCondition;
import com.ssic.game.common.pojo.ImsQueryConditionExample;
import com.ssic.game.common.pojo.ImsQueryConditionExample.Criteria;
import com.ssic.util.base.MyBatisBaseDao;
import com.ssic.util.constants.DataStatus;

/**		
 * <p>Title: QueryConditionDao </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年7月28日 下午4:51:59	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年7月28日 下午4:51:59</p>
 * <p>修改备注：</p>
 */
@Repository
public class QueryConditionDao extends MyBatisBaseDao<ImsQueryCondition>
{
    @Autowired
    @Getter
    private ImsQueryConditionMapper mapper;

    public List<ImsQueryCondition> findBy(ImsQueryCondition param)
    {
        ImsQueryConditionExample example = new ImsQueryConditionExample();
        Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty(param.getId()))
        {
            criteria.andIdEqualTo(param.getId());
        }
        if (!StringUtils.isEmpty(param.getProjectId()))
        {
            criteria.andProjectIdEqualTo(param.getProjectId());
        }
        if (!StringUtils.isEmpty(param.getQueryId()))
        {
            criteria.andQueryIdEqualTo(param.getQueryId());
        }

        criteria.andStatEqualTo(DataStatus.ENABLED);
        example.setOrderByClause(" serial_num asc");

        return mapper.selectByExample(example) != null ? mapper.selectByExample(example) : null;

    }

    /**     
     * findAll：一句话描述方法功能
     * @param con
     * @param phDto
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年7月29日 下午3:28:03	 
     */
    public List<ImsQueryCondition> findAll(ImsQueryCondition param, PageHelperDto ph)
    {
        ImsQueryConditionExample example = new ImsQueryConditionExample();
        Criteria criteria = example.createCriteria();
        criteria.andStatEqualTo(DataStatus.ENABLED);
        if (ph != null && !StringUtils.isEmpty(ph.getBeginRow()) && !StringUtils.isEmpty(ph.getRows()))
        {
            int beginRow = ph.getBeginRow();
            int rows = ph.getRows();
            example.setOrderByClause("create_time desc limit " + beginRow + "," + rows);
        }
        else
        {
            example.setOrderByClause("create_time desc");
        }

        if (!StringUtils.isEmpty(param.getId()))
        {
            criteria.andIdEqualTo(param.getId());
        }
        if (!StringUtils.isEmpty(param.getProjectId()))
        {
            criteria.andProjectIdEqualTo(param.getProjectId());
        }
        if (!StringUtils.isEmpty(param.getQueryId()))
        {
            criteria.andQueryIdEqualTo(param.getQueryId());
        }

        return mapper.selectByExample(example) != null ? mapper.selectByExample(example) : null;
    }

    /**     
     * findCountBy：一句话描述方法功能
     * @param conditionDto
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年7月29日 下午3:31:41	 
     */
    public int findCountBy(ImsQueryCondition param)
    {
        ImsQueryConditionExample example = new ImsQueryConditionExample();
        Criteria criteria = example.createCriteria();
        criteria.andStatEqualTo(DataStatus.ENABLED);

        if (!StringUtils.isEmpty(param.getId()))
        {
            criteria.andIdEqualTo(param.getId());
        }
        if (!StringUtils.isEmpty(param.getProjectId()))
        {
            criteria.andProjectIdEqualTo(param.getProjectId());
        }
        if (!StringUtils.isEmpty(param.getQueryId()))
        {
            criteria.andQueryIdEqualTo(param.getQueryId());
        }
        if (!StringUtils.isEmpty(param.getFieldsId()))
        {
            criteria.andFieldsIdEqualTo(param.getFieldsId());
        }

        int count = mapper.countByExample(example);
        return count;
    }

    /**     
     * addQueryCondition：一句话描述方法功能
     * @param condition
     * @exception	
     * @author Administrator
     * @date 2015年7月30日 下午1:47:03	 
     */
    public void addQueryCondition(ImsQueryCondition condition)
    {
        mapper.insert(condition);

    }

    /**     
     * findOrder：一句话描述方法功能
     * @param condition
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年7月30日 下午3:27:00	 
     */
    public int findOrderAsc(ImsQueryCondition condition)
    {
        ImsQueryConditionExample example = new ImsQueryConditionExample();
        Criteria criteria = example.createCriteria();
        criteria.andStatEqualTo(DataStatus.ENABLED);
        if (!StringUtils.isEmpty(condition.getQueryId()))
        {
            criteria.andQueryIdEqualTo(condition.getQueryId());
        }
        if (!StringUtils.isEmpty(condition.getFieldsId()))
        {
            criteria.andFieldsIdEqualTo(condition.getFieldsId());
        }

        criteria.andOptEqualTo(QueryConditionType.ORDERBY_ASC);

        int count = mapper.countByExample(example);
        return count;
    }

    /**     
     * findOrderDesc：一句话描述方法功能
     * @param condition
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年7月30日 下午3:31:09	 
     */
    public int findOrderDesc(ImsQueryCondition condition)
    {
        ImsQueryConditionExample example = new ImsQueryConditionExample();
        Criteria criteria = example.createCriteria();
        criteria.andStatEqualTo(DataStatus.ENABLED);
        if (!StringUtils.isEmpty(condition.getQueryId()))
        {
            criteria.andQueryIdEqualTo(condition.getQueryId());
        }
        if (!StringUtils.isEmpty(condition.getFieldsId()))
        {
            criteria.andFieldsIdEqualTo(condition.getFieldsId());
        }

        criteria.andOptEqualTo(QueryConditionType.ORDERBY_DESC);

        int count = mapper.countByExample(example);
        return count;
    }

    /**     
     * findConditionName：一句话描述方法功能
     * @param condition
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年7月30日 下午4:03:36	 
     */
    public int findConditionName(ImsQueryCondition condition)
    {
        ImsQueryConditionExample example = new ImsQueryConditionExample();
        Criteria criteria = example.createCriteria();
        criteria.andStatEqualTo(DataStatus.ENABLED);
        if (!StringUtils.isEmpty(condition.getName()))
        {
            criteria.andNameEqualTo(condition.getName());
        }
        int count = mapper.countByExample(example);
        return count;
    }

    /**     
     * findOptCount：一句话描述方法功能
     * @param condition
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年7月30日 下午4:27:20	 
     */
    public int findOptCount(ImsQueryCondition condition)
    {
        ImsQueryConditionExample example = new ImsQueryConditionExample();
        Criteria criteria = example.createCriteria();
        criteria.andStatEqualTo(DataStatus.ENABLED);
        List<Integer> values = new ArrayList<Integer>();
        values.add(QueryConditionType.EQ);
        values.add(QueryConditionType.GT);
        values.add(QueryConditionType.GTE);
        values.add(QueryConditionType.LIKE);
        values.add(QueryConditionType.LT);
        values.add(QueryConditionType.LTE);

        criteria.andOptIn(values);

        if (!StringUtils.isEmpty(condition.getQueryId()))
        {
            criteria.andQueryIdEqualTo(condition.getQueryId());
        }
        if (!StringUtils.isEmpty(condition.getFieldsId()))
        {
            criteria.andFieldsIdEqualTo(condition.getFieldsId());
        }
        int count = mapper.countByExample(example);
        return count;
    }

    /**     
     * updateQueryCondition：一句话描述方法功能
     * @param condition
     * @exception	
     * @author Administrator
     * @date 2015年7月30日 下午5:31:40	 
     */
    public void updateQueryCondition(ImsQueryCondition condition)
    {
        mapper.updateByPrimaryKey(condition);
    }

    /**     
     * findConditionName：一句话描述方法功能
     * @param condition
     * @param id
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年7月30日 下午5:51:18	 
     */
    public int findEditConditionName(ImsQueryCondition condition, String id)
    {
        ImsQueryConditionExample example = new ImsQueryConditionExample();
        Criteria criteria = example.createCriteria();
        criteria.andStatEqualTo(DataStatus.ENABLED);
        if (!StringUtils.isEmpty(condition.getName()))
        {
            criteria.andNameEqualTo(condition.getName());
        }
        if (!StringUtils.isEmpty(id))
        {
            criteria.andIdNotEqualTo(id);
        }
        int count = mapper.countByExample(example);
        return count;
    }

}
