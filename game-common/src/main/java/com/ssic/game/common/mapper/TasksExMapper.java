package com.ssic.game.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ssic.game.common.dto.ActionsDto;
import com.ssic.game.common.dto.PageHelperDto;
import com.ssic.game.common.dto.TasksDto;
import com.ssic.game.common.pojo.Tasks;


public interface TasksExMapper {
    List<Tasks> findAll(@Param("task")Tasks tasks); 
    
    List<TasksDto> findTasks(@Param("task")TasksDto tasksdto);

    void insert(@Param("task")Tasks task);
    
    void update(@Param("task")Tasks task);
    
    void delete(@Param("id") String id);
    
    List<ActionsDto> findActions();
    
    void updateForm(@Param("task")TasksDto tasksdto);
    
    List<TasksDto> findByLastCreateTime(@Param("procId") String procId);
    
    TasksDto findById(@Param("id")String id);
    
    List<TasksDto> findTasksALL(@Param("task")TasksDto tasksdto,@Param("page")PageHelperDto ph);
    
    int findCount(@Param("task")TasksDto tasksdto);
}
