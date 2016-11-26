package com.ssic.catering.lbs.service;

import java.util.List;

import com.ssic.catering.lbs.documents.TransportTrailDto;
import com.ssic.catering.lbs.dto.TransportTaskDto;
import com.ssic.game.common.dto.PageHelperDto;
import com.ssic.util.model.Response;

/**
 * 		
 * <p>Title: TransportTaskService </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author pengcheng.yang	
 * @date 2015年11月25日 下午1:14:22	
 * @version 1.0
 * <p>修改人：</p>
 * <p>修改时间：2015年11月25日 下午1:14:22</p>
 * <p>修改备注：</p>
 */
public interface TransportTaskService {
	 /**
	  * 
	  * findtaskInfoByDriverId:查询当前登录任务人拥有任务
	  * @param driverId
	  * @return
	  * @exception	
	  * @author pengcheng.yang
	  * @date 2015年11月25日 下午1:16:50
	  */
	Response<List<TransportTaskDto>> findtaskInfoByDriverId(String driverId);
	
	/**
	 * 
	 * findByTaskAdminUserId：根据供应商Id查询任务所属项目Id
	 * @param adminUserId
	 * @return
	 * @exception	
	 * @author pengcheng.yang
	 * @date 2015年11月25日 下午5:39:52
	 */
	TransportTaskDto findByTaskAdminUserId(String taskId);
	
	/**
	 * 
	 * updateTaskState：更改任务的状态
	 * @param taskId
	 * @param state
	 * @return
	 * @exception	
	 * @author pengcheng.yang
	 * @date 2015年11月26日 上午9:13:21
	 */
	int updateTaskState(String taskId,int state);
	
	/**
	 * 
	 * findTaskIdState：查询当前项目下面所有正在运行的任务轨迹
	 * @param projectId
	 * @return
	 * @exception	
	 * @author pengcheng.yang
	 * @date 2015年11月26日 上午9:25:38
	 */
	List<String> findTaskIdState(String projectId);
	
	/**
	 * 
	 * findByTaskId：根据任务Id查询正在配送中的任务的出发地和目的地和经纬度
	 * @param taskId
	 * @return
	 * @exception	
	 * @author pengcheng.yang
	 * @date 2015年11月26日 下午1:53:38
	 */
	List<TransportTrailDto> findByTaskId(String projectId);
	
	/**
	 * 
	 * findByTaskIdAndComplete：根据任务Id查询正在配送中和配送完成的任务的出发地和目的地和经纬度
	 * @param projectId
	 * @return
	 * @exception	
	 * @author pengcheng.yang
	 * @date 2015年11月26日 下午7:20:09
	 */
	List<TransportTrailDto> findByTaskIdAndComplete(String projectId);
	
    
    /**
     * 
     * findDriverTaskState：查询当前驾驶员是否有任务在配送中
     * @param driverId
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年12月1日 上午9:07:24
     */
    int findDriverTaskState(String driverId);
}

