/**
 * 
 */
package com.ssic.catering.base.dao;

import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssic.catering.base.dto.PageHelperDto;
import com.ssic.catering.base.mapper.RoleProjectMapper;
import com.ssic.catering.base.pojo.RoleProjectExample.Criteria;
import com.ssic.catering.base.pojo.RoleProject;
import com.ssic.catering.base.pojo.RoleProjectExample;
import com.ssic.util.StringUtils;
import com.ssic.util.base.MyBatisBaseDao;
import com.ssic.util.constants.DataStatus;

/**		
 * <p>Title: RoleProjectDao </p>
 * <p>Description: 项目角色关系dao</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 刘博	
 * @date 2015年10月26日 下午1:50:28	
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年10月26日 下午1:50:28</p>
 * <p>修改备注：</p>
 */
@Repository
public class RoleProjectDao extends MyBatisBaseDao<RoleProject>
{

    @Autowired
    @Getter
    private RoleProjectMapper mapper;

    /**     
     * findAllBy：一句话描述方法功能
     * @param cartType
     * @return
     * @exception   
     * @author Administrator
     * @date 2015年8月4日 上午11:33:53    
     */
    public List<RoleProject> findAllBy(RoleProject param, PageHelperDto ph)
    {
        RoleProjectExample example = new RoleProjectExample();
        Criteria criteria = example.createCriteria();
        if (ph != null && !StringUtils.isEmpty(String.valueOf(ph.getBeginRow()))
            && !StringUtils.isEmpty(String.valueOf(ph.getRows())))
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
        if (!StringUtils.isEmpty(param.getRoleId()))
        {
            criteria.andRoleIdEqualTo(param.getRoleId());
        }
        if (!StringUtils.isEmpty(param.getProjId()))
        {
            criteria.andProjIdEqualTo(param.getProjId());
        }
        criteria.andStatEqualTo(DataStatus.ENABLED);
        return mapper.selectByExample(example);
    }
}
