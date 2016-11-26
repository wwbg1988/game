/**
 * 
 */
package com.ssic.game.common.dao;

import java.util.Date;
import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.ssic.game.common.mapper.TaskRoleMapper;
import com.ssic.game.common.pojo.ActionRole;
import com.ssic.game.common.pojo.ActionRoleExample;
import com.ssic.game.common.pojo.TaskRole;
import com.ssic.game.common.pojo.TaskRoleExample;
import com.ssic.game.common.pojo.TaskRoleExample.Criteria;
import com.ssic.util.UUIDGenerator;
import com.ssic.util.base.MyBatisBaseDao;
import com.ssic.util.constants.DataStatus;

/**		
 * <p>Title: TaskRoleDao </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 刘博	
 * @date 2015年11月17日 下午2:21:01	
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年11月17日 下午2:21:01</p>
 * <p>修改备注：</p>
 */
@Repository
public class TaskRoleDao extends MyBatisBaseDao<TaskRole>
{
    @Autowired
    @Getter
    private TaskRoleMapper mapper;

    public List<TaskRole> findRoleAll(TaskRole param)
    {
        TaskRoleExample example = new TaskRoleExample();
        Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty(param.getProjId()))
        {
            criteria.andProjIdEqualTo(param.getProjId());
        }
        if (!StringUtils.isEmpty(param.getProcId()))
        {
            criteria.andProcIdEqualTo(param.getProcId());
        }
        if (!StringUtils.isEmpty(param.getRoleId()))
        {
            criteria.andRoleIdEqualTo(param.getRoleId());
        }
        if (!StringUtils.isEmpty(param.getTaskId()))
        {
            criteria.andTaskIdEqualTo(param.getTaskId());
        }
        if (!StringUtils.isEmpty(param.getFormId()))
        {
            criteria.andFormIdEqualTo(param.getFormId());
        }
        criteria.andStatEqualTo(DataStatus.ENABLED);

        return mapper.selectByExample(example);
    }

    public void insertRoles(TaskRole param)
    {
        if (!StringUtils.isEmpty(param.getProjId()) && !StringUtils.isEmpty(param.getTaskId()))
        {
            param.setId(UUIDGenerator.getUUID());
            param.setCreateTime(new Date());
            param.setStat(DataStatus.ENABLED);
            mapper.insert(param);
        }
    }

    public void delRoles(TaskRole param)
    {
        if (!StringUtils.isEmpty(param.getProjId()) && !StringUtils.isEmpty(param.getTaskId()))
        {
            TaskRoleExample example = new TaskRoleExample();
            Criteria criteria = example.createCriteria();
            criteria.andProjIdEqualTo(param.getProjId());
            criteria.andTaskIdEqualTo(param.getTaskId());
            criteria.andStatEqualTo(1);
            List<TaskRole> list = mapper.selectByExample(example);
            if (list != null && list.size() > 0)
            {
                for (TaskRole taskRole : list)
                {
                    taskRole.setStat(DataStatus.DISABLED);
                    mapper.updateByPrimaryKey(taskRole);
                }
            }
        }
    }

}
