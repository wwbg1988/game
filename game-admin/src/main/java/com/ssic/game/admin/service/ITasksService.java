/**   
 * bare_field_name   
 * com.ssic.game.admin.service	
 * @return  the bare_field_name 
 */

package com.ssic.game.admin.service;

import java.util.List;

import com.ssic.game.admin.pageModel.Json;
import com.ssic.game.admin.pageModel.PageHelper;
import com.ssic.game.common.dto.ActionsDto;
import com.ssic.game.common.dto.TasksDto;

/**		
 * <p>Title: TaskService </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年6月24日 上午8:57:17	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年6月24日 上午8:57:17</p>
 * <p>修改备注：</p>
 */
public interface ITasksService
{
    public List<TasksDto> findAllBy(TasksDto tasksDto);

    public void insert(TasksDto tasksDto);

    public void update(TasksDto tasksDto);

    public void delete(String id);

    public List<TasksDto> findTasks(TasksDto tasksDto);

    public List<ActionsDto> findActions();

    public void updateForm(TasksDto tasksDto);

    public List<TasksDto> findByLastCreateTime(String procId);

    public TasksDto findById(String id);

    public List<TasksDto> findTasksALL(TasksDto tasksDto, PageHelper ph);

    public int findCount(TasksDto tasksDto);

    /**     
     * findTaskRole：查找当前任务节点对应的角色
     * @param ids
     * @param projId
     * @return
     * @exception	
     * @author 刘博
     * @date 2015年11月17日 下午1:55:46	 
     */
    public String findTaskRole(String ids, String projId);

    
    /**     
     * grant：一句话描述方法功能
     * @param ids
     * @param roleIds
     * @return
     * @exception	
     * @author 刘博
     * @date 2015年11月17日 下午2:35:39	 
     */
    public String grant(String ids, String roleIds);
}
