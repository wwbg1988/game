/**   
 * bare_field_name   
 * com.ssic.game.admin.service.impl	
 * @return  the bare_field_name 
 */

package com.ssic.game.admin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ssic.game.admin.pageModel.PageHelper;
import com.ssic.game.admin.service.ITasksService;
import com.ssic.game.common.dao.TaskRoleDao;
import com.ssic.game.common.dao.TasksDao;
import com.ssic.game.common.dto.ActionsDto;
import com.ssic.game.common.dto.PageHelperDto;
import com.ssic.game.common.dto.TaskRoleDto;
import com.ssic.game.common.dto.TasksDto;
import com.ssic.game.common.pojo.TaskRole;
import com.ssic.game.common.pojo.Tasks;
import com.ssic.util.BeanUtils;

/**		
 * <p>Title: TaskServiceImpl </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年6月24日 上午8:57:28	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年6月24日 上午8:57:28</p>
 * <p>修改备注：</p>
 */
@Service
public class TasksServiceImpl implements ITasksService
{
    @Autowired
    private TasksDao tasksDao;
    @Autowired
    private TaskRoleDao taskRoleDao;

    /** 
    * (non-Javadoc)   
    * @see com.ssic.game.admin.service.ITasksService#findByProjId(java.lang.String)   
    */
    public List<TasksDto> findAllBy(TasksDto tasksDto)
    {
        List<TasksDto> result = new ArrayList<TasksDto>();
        Tasks tasks = new Tasks();
        BeanUtils.copyProperties(tasksDto, tasks, "stat");
        List<Tasks> list = tasksDao.findAllBy(tasks);
        if (list != null && list.size() > 0)
        {
            //result = BeanUtils.createBeanListByTarget(list, TasksDto.class); 

            for (int i = 0; i < list.size(); i++)
            {
                Tasks ta = list.get(i);
                TasksDto tdto = new TasksDto();
                BeanUtils.copyProperties(ta, tdto, "stat");
                result.add(tdto);
            }

            return result;
        }

        return result;
    }

    public List<TasksDto> findTasks(TasksDto tasksDto)
    {
        // List<TasksDto> result = new ArrayList<TasksDto>();
        //   Tasks tasks =new Tasks();
        //  BeanUtils.copyProperties(tasksDto, tasks);
        //  BeanUtils.copyProperties(tasksDto, tasks,"stat");
        List<TasksDto> list = tasksDao.findTasks(tasksDto);
        //        if(list!=null&&list.size()>0){
        //        	
        //        	for(int i=0;i<list.size();i++){
        //        		Tasks ta = list.get(i);
        //        		TasksDto tdto = new TasksDto();
        //        		BeanUtils.copyProperties(ta, tdto,"stat");
        //        		result.add(tdto);
        //        	}

        //      result = BeanUtils.createBeanListByTarget(list, TasksDto.class); 

        //   return list;
        // }

        return list;
    }

    public List<TasksDto> findTasksALL(TasksDto tasksDto, PageHelper ph)
    {
        PageHelperDto phdto = new PageHelperDto();
        phdto.setOrder(ph.getOrder());
        phdto.setPage(ph.getPage());
        phdto.setRows(ph.getRows());
        phdto.setSort(ph.getSort());
        phdto.setBeginRow((ph.getPage() - 1) * ph.getRows());
        List<TasksDto> list = tasksDao.findTasksALL(tasksDto, phdto);
        return list;
    }

    @CacheEvict(value = "default", key = "'game.common.dto.TasksDto:' + #tasksDto.getId()", beforeInvocation = true)
    public void insert(TasksDto tasksDto)
    {
        Tasks tasks = new Tasks();
        BeanUtils.copyProperties(tasksDto, tasks);
        tasksDao.insert(tasks);
    }

    @CacheEvict(value = "default", key = "'game.common.dto.TasksDto:' + #tasksDto.getId()", beforeInvocation = true)
    public void update(TasksDto tasksDto)
    {
        Tasks tasks = new Tasks();
        BeanUtils.copyProperties(tasksDto, tasks);
        tasksDao.update(tasks);
    }

    @CacheEvict(value = "default", key = "'game.common.dto.TasksDto:' + #id", beforeInvocation = true)
    public void delete(String id)
    {
        tasksDao.delete(id);
    }

    public List<ActionsDto> findActions()
    {

        return tasksDao.findActions();
    }

    @Override
    public void updateForm(TasksDto tasksDto)
    {
        tasksDao.updateForm(tasksDto);

    }

    @Override
    public List<TasksDto> findByLastCreateTime(String procId)
    {

        return tasksDao.findByLastCreateTime(procId);
    }

    @Override
    public TasksDto findById(String id)
    {

        return tasksDao.findById(id);
    }

    public int findCount(TasksDto tasksDto)
    {
        return tasksDao.findCount(tasksDto);
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.game.admin.service.ITasksService#findTaskRole(java.lang.String, java.lang.String)   
    */
    @Override
    public String findTaskRole(String ids, String projId)
    {
        TaskRole taskRole = new TaskRole();
        taskRole.setTaskId(ids);
        taskRole.setProjId(projId);

        List<TaskRole> list = taskRoleDao.findRoleAll(taskRole);
        if (list != null && list.size() > 0)
        {
            String result = "";
            for (int i = 0; i < list.size(); i++)
            {
                if (i == list.size() - 1)
                {
                    result += list.get(i).getRoleId();
                }
                else
                {
                    result += list.get(i).getRoleId() + ",";
                }
            }
            return result;
        }
        return null;
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.game.admin.service.ITasksService#grant(java.lang.String, java.lang.String)   
    */
    @Override
    public String grant(String ids, String roleIds)
    {
        TasksDto taskDto = tasksDao.findById(ids);
        if (roleIds != null && roleIds.length() > 0)
        {
            List<String> roles = new ArrayList<String>();
            for (String roleId : roleIds.split(","))
            {
                roles.add(roleId);
            }
            for (String rId : roles)
            {
                TaskRole param = new TaskRole();
                if (taskDto != null)
                {
                    //项目id
                    if (!StringUtils.isEmpty(taskDto.getProjId()))
                    {
                        param.setProjId(taskDto.getProjId());
                    }
                    //流程id
                    if (!StringUtils.isEmpty(taskDto.getProcId()))
                    {
                        param.setProcId(taskDto.getProcId());
                    }
                    //表单id
                    if (!StringUtils.isEmpty(taskDto.getFormId()))
                    {
                        param.setFormId(taskDto.getFormId());
                    }
                }
                param.setTaskId(ids);
                param.setRoleId(rId);
                taskRoleDao.insertRoles(param);
            }
            return "角色赋权成功";
        }
        return null;
    }
}
