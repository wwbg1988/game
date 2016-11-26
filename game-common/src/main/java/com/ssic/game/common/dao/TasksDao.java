/**
 * 
 */
package com.ssic.game.common.dao;

import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.ssic.game.common.dto.ActionsDto;
import com.ssic.game.common.dto.PageHelperDto;
import com.ssic.game.common.dto.TaskRoleDto;
import com.ssic.game.common.dto.TaskUserDto;
import com.ssic.game.common.dto.TasksDto;
import com.ssic.game.common.mapper.TaskRoleMapper;
import com.ssic.game.common.mapper.TaskUserMapper;
import com.ssic.game.common.mapper.TasksExMapper;
import com.ssic.game.common.mapper.TasksMapper;
import com.ssic.game.common.pojo.TaskRole;
import com.ssic.game.common.pojo.TaskRoleExample;
import com.ssic.game.common.pojo.TaskRoleExample.Criteria;
import com.ssic.game.common.pojo.TaskUser;
import com.ssic.game.common.pojo.TaskUserExample;
import com.ssic.game.common.pojo.Tasks;
import com.ssic.util.base.MyBatisBaseDao;
import com.ssic.util.constants.DataStatus;

/**		
 * <p>Title: TasksDao </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author rkzhang	
 * @date 2015年6月24日 上午9:01:19	
 * @version 1.0
 * <p>修改人：rkzhang</p>
 * <p>修改时间：2015年6月24日 上午9:01:19</p>
 * <p>修改备注：</p>
 */
@Repository
public class TasksDao extends MyBatisBaseDao<Tasks> {

//    @Getter
    @Autowired
    private TasksExMapper exMapper;
    
    @Getter
    @Autowired
    private TasksMapper mapper;
    
    @Autowired
    private TaskRoleMapper roleMapper;
    
    @Autowired
    private TaskUserMapper userMapper;
    
    public TaskRole findRoleById(String id){
        return roleMapper.selectByPrimaryKey(id);
        
    }
    
    public List<TaskRole> findRoleBy(TaskRoleDto taskRoleDto){
        TaskRoleExample example = new TaskRoleExample();
        Criteria criteria = example.createCriteria();
        if(!StringUtils.isEmpty(taskRoleDto.getProjId())){
            criteria.andProjIdEqualTo(taskRoleDto.getProjId());
        }
        if(!StringUtils.isEmpty(taskRoleDto.getProcId())){
            criteria.andProcIdEqualTo(taskRoleDto.getProcId());
        }
        if(!StringUtils.isEmpty(taskRoleDto.getFormId())){
            criteria.andFormIdEqualTo(taskRoleDto.getFormId());
        }
        if(!StringUtils.isEmpty(taskRoleDto.getRoleId())){
            criteria.andRoleIdEqualTo(taskRoleDto.getRoleId());
        }
        if(!StringUtils.isEmpty(taskRoleDto.getTaskId())){
            criteria.andTaskIdEqualTo(taskRoleDto.getTaskId());
        }
        criteria.andStatEqualTo(DataStatus.ENABLED);
        return roleMapper.selectByExample(example);
        
    }
    
    public TaskUser findUserById(String id){
        return userMapper.selectByPrimaryKey(id);
    }
    
    public List<TaskUser> findUserBy(TaskUserDto taskUserDto){
        TaskUserExample example = new TaskUserExample();
        com.ssic.game.common.pojo.TaskUserExample.Criteria criteria = example.createCriteria();
        if(!StringUtils.isEmpty(taskUserDto.getProjId())){
            criteria.andProjIdEqualTo(taskUserDto.getProjId());
        }
        if(!StringUtils.isEmpty(taskUserDto.getProcId())){
            criteria.andProcIdEqualTo(taskUserDto.getProcId());
        }
        if(!StringUtils.isEmpty(taskUserDto.getFormId())){
            criteria.andFormIdEqualTo(taskUserDto.getFormId());
        }
        criteria.andStatEqualTo(DataStatus.ENABLED);
        return userMapper.selectByExample(example);
    }
    
    public List<Tasks>findAllBy(Tasks tasks){
        return exMapper.findAll(tasks);
    }

    public List<TasksDto> findTasks(TasksDto taskdto){
    	return exMapper.findTasks(taskdto);
    }
    
    public int insert(Tasks tasks){
    	
    	exMapper.insert(tasks);
    	return 1;
    }
    
    public void update(Tasks tasks){
    	exMapper.update(tasks);
    }
    
    public void delete(String id){
    	exMapper.delete(id);
    }
    
    public List<ActionsDto>  findActions(){
    	 return exMapper.findActions();
    }
    
    public void updateForm(TasksDto tasksDto){
    	exMapper.updateForm(tasksDto);
    }

    
    /**     
     * findByLastCreateTime：一句话描述方法功能
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年7月2日 下午3:22:56	 
     */
    public List<TasksDto> findByLastCreateTime(String procId)
    {
        // TODO 添加方法注释
        return exMapper.findByLastCreateTime(procId);
    }
    
    public TasksDto findById(String id){
    	return exMapper.findById(id);
    }
    
    public List<TasksDto> findTasksALL(TasksDto taskdto,PageHelperDto ph){
    	return exMapper.findTasksALL(taskdto,ph);
    }
    
    public int findCount(TasksDto taskdto){
    	return exMapper.findCount(taskdto);
    }
}

