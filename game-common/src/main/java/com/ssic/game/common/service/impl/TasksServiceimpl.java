/**
 * 
 */
package com.ssic.game.common.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.ssic.game.common.dao.TasksDao;
import com.ssic.game.common.dto.TasksDto;
import com.ssic.game.common.pojo.Tasks;
import com.ssic.game.common.service.ITasksService;
import com.ssic.game.common.util.DataStatusUtils;
import com.ssic.util.BeanUtils;

/**		
 * <p>Title: TasksServiceimpl </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年7月2日 下午3:05:25	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年7月2日 下午3:05:25</p>
 * <p>修改备注：</p>
 */
@Service
public class TasksServiceimpl implements ITasksService
{

    @Autowired
    private TasksDao taskDao;

    /** 
    * (non-Javadoc)   
    * @see com.ssic.game.common.service.ITasksService#findByLastCreateTime()   
    */
    @Override
    public TasksDto findByLastCreateTime(String procId)
    {
        List<TasksDto> taskList = taskDao.findByLastCreateTime(procId);
        TasksDto taskDto = CollectionUtils.isEmpty(taskList) ? null : taskList.get(0);

        if (isNotExist(taskDto))
        {
            return null;
        }
        return taskDto;
    }

    private boolean isNotExist(TasksDto taskDto)
    {
        return taskDto == null || DataStatusUtils.isNotEnabled(taskDto.getStat());
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.game.common.service.ITasksService#findByTaskId(java.lang.String)   
    */
    @Override
    @Cacheable(value = "default", key = "'game.common.dto.TasksDto:' + #taskId")
    public TasksDto findByTaskId(String taskId) 
    {
        TasksDto taskDto = taskDao.findById(taskId);
        if (isNotExist(taskDto))
        {
            return null;
        }
        return taskDto;
    }
}
