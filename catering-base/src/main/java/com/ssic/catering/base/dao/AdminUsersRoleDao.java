package com.ssic.catering.base.dao;

import java.util.Arrays;
import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssic.catering.base.dto.AdminUsersDto;
import com.ssic.catering.base.mapper.AdminUsersRoleExMapper;
import com.ssic.catering.base.mapper.AdminUsersRoleMapper;
import com.ssic.catering.base.pojo.AdminUsersRole;
import com.ssic.catering.base.pojo.AdminUsersRoleExample;
import com.ssic.game.common.dto.PageHelperDto;
import com.ssic.util.ArrayUtils;
import com.ssic.util.StringUtils;
import com.ssic.util.base.MyBatisBaseDao;
import com.ssic.util.constants.DataStatus;

@Repository
public class AdminUsersRoleDao extends MyBatisBaseDao<AdminUsersRole>
{
    @Autowired
    @Getter
    private AdminUsersRoleMapper mapper;

    @Autowired
    private AdminUsersRoleExMapper exMapper;

    /**
     * 
     * findAdminUsersDtosBy：一句话描述方法功能
     * 
     * @param param
     * @param pageHelperDto
     * @return
     * @exception
     * @author zhuzhen
     * @date 2015年12月2日 下午4:02:34
     */
    public List<AdminUsersDto> findAdminUsersDtosBy(AdminUsersRole param, PageHelperDto ph)
    {
        AdminUsersRoleExample example = new AdminUsersRoleExample();
        AdminUsersRoleExample.Criteria criteria = example.createCriteria();

        if (param != null)
        {
            // id
            if (!StringUtils.isEmpty(param.getId()))
            {
                criteria.andIdEqualTo(param.getId());
            }
            // userId
            if (!StringUtils.isEmpty(param.getUserId()))
            {
                criteria.andUserIdEqualTo(param.getUserId());
            }
            // roleId
            if (!StringUtils.isEmpty(param.getRoleId()))
            {
                if (param.getRoleId().contains(","))
                {
                    String[] roleIds = param.getRoleId().split(",");
                    if (!ArrayUtils.isEmpty(roleIds))
                    {
                        criteria.andRoleIdIn(Arrays.asList(roleIds));
                    }
                }
                else
                {
                    criteria.andRoleIdEqualTo(param.getRoleId());
                }
            }
            // stat
            if (null == param.getStat())
            {
                criteria.andStatEqualTo(DataStatus.ENABLED);
            }
        }
        else
        {
            criteria.andStatEqualTo(DataStatus.ENABLED);
        }
        

        if (ph != null)
        {
            // 开始下标
            if (ph.getBeginRow() == null || ph.getBeginRow() < 0)
            {
                ph.setBeginRow(0);
            }

            // 当前页
            if (ph.getPage() <= 0)
            {
                ph.setPage(1);
            }

            // 页面大小
            if (ph.getRows() <= 0)
            {
                ph.setRows(1);
            }

            // 排序字段
            if (!StringUtils.isEmpty(ph.getSort()) && !StringUtils.isEmpty(ph.getOrder()))
            {
                example.setOrderByClause(ph.getSort() + " " + ph.getOrder() + " limit " + ph.getBeginRow()
                    + "," + ph.getPage() * ph.getRows());
            }
            else
            {
                example.setOrderByClause("createdatetime desc limit " + ph.getBeginRow() + "," + ph.getPage()
                    * ph.getRows());
            }
        }

        return exMapper.selectAdminUsersDtosBy(example);

    }

    /**
     * 
     * findAdminUsersDtoCountBy：一句话描述方法功能
     * 
     * @param param
     * @return
     * @exception
     * @author zhuzhen
     * @date 2015年12月2日 下午4:31:43
     */
    public long findAdminUsersDtoCountBy(AdminUsersRole param)
    {
        AdminUsersRoleExample example = new AdminUsersRoleExample();
        AdminUsersRoleExample.Criteria criteria = example.createCriteria();

        if (param != null)
        {
            // id
            if (!StringUtils.isEmpty(param.getId()))
            {
                criteria.andIdEqualTo(param.getId());
            }
            // userId
            if (!StringUtils.isEmpty(param.getUserId()))
            {
                criteria.andUserIdEqualTo(param.getUserId());
            }
            // roleId
            if (!StringUtils.isEmpty(param.getRoleId()))
            {
                if (param.getRoleId().contains(","))
                {
                    String[] roleIds = param.getRoleId().split(",");
                    if (!ArrayUtils.isEmpty(roleIds))
                    {
                        criteria.andRoleIdIn(Arrays.asList(roleIds));
                    }
                }
                else
                {
                    criteria.andRoleIdEqualTo(param.getRoleId());
                }
            }
            // stat
            if (null == param.getStat())
            {
                criteria.andStatEqualTo(DataStatus.ENABLED);
            }
        }
        else
        {
            criteria.andStatEqualTo(DataStatus.ENABLED);
        }

        return exMapper.selectAdminUsersDtoCountBy(example);
    }
}
