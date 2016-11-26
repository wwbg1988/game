package com.ssic.catering.lbs.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ssic.catering.lbs.documents.TransportTrailDto;
import com.ssic.catering.lbs.dto.TransportTaskAdminDto;
import com.ssic.catering.lbs.dto.TransportTaskDto;
import com.ssic.catering.lbs.pojo.TransportTaskExample;

/**
 * 		
 * <p>Title: TransportTaskExMapper </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author pengcheng.yang	
 * @date 2015年11月25日 下午5:30:53	
 * @version 1.0
 * <p>修改人：apple</p>
 * <p>修改时间：2015年11月25日 下午5:30:53</p>
 * <p>修改备注：</p>
 */
public interface TransportTaskExMapper {
	/**
	 * 
	 * findByTaskAdminUserId：根据供应商Id查询任务所属项目Id
	 * @param adminUserId
	 * @return
	 * @exception	
	 * @author pengcheng.yang
	 * @date 2015年11月25日 下午5:32:26
	 */
	TransportTaskDto findByTaskAdminUserId(@Param("taskId")String taskId); 
	/**
	 * 
	 * updateTaskState：更改任务的状态
	 * @param taskId
	 * @return
	 * @exception	
	 * @author pengcheng.yang
	 * @date 2015年11月26日 上午9:05:39
	 */
	int updateTaskState(@Param("taskId")String taskId,@Param("state")int state);
	
	/**
	 * 
	 * findTaskIdState：查询当前项目下面所有正在运行的任务轨迹
	 * @param projectId
	 * @return
	 * @exception	
	 * @author pengcheng.yang
	 * @date 2015年11月26日 上午9:23:58
	 */
	List<String> findTaskIdState(@Param("projectId")String projectId);
	
	/**
	 * 
	 * findByTaskId：根据任务Id查询正在配送中的任务出发地和目的地和目的地的经纬度
	 * @param taskId
	 * @return
	 * @exception	
	 * @author pengcheng.yang
	 * @date 2015年11月26日 下午1:28:46
	 */
	List<TransportTrailDto> findByTaskId(@Param("projectId")String projectId);
	
	/**
	 * 
	 * findByTaskIdAndComplete：根据任务Id查询正在配送中和配送完成的任务出发地和目的地和目的地的经纬度
	 * @param projectId
	 * @return
	 * @exception	
	 * @author apple
	 * @date 2015年11月26日 下午7:17:15
	 */
	List<TransportTrailDto> findByTaskIdAndComplete(@Param("projectId")String projectId);
	
	
	 /**
     * 
     * selectTransportTaskAdminDtosByExample：多表查询
     * @param example
     * @return
     * @exception   
     * @author zhuzhen
     * @date 2015年11月26日 上午9:10:43
     */
    List<TransportTaskAdminDto> selectTransportTaskAdminDtosByExample(@Param("example")TransportTaskExample example);
    
    /**
     * 
     * countTransportTaskAdminDtosByExample：总条数
     * @param example
     * @return
     * @exception   
     * @author zhuzhen
     * @date 2015年11月26日 上午10:04:09
     */
    long countTransportTaskAdminDtosByExample(@Param("example")TransportTaskExample example);
    
    /**
     * 
     * findAddressByDestId：根据目的地Id查询目的地地址
     * @param destId
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年11月30日 下午3:05:09
     */
    TransportTaskDto findAddressByDestId(@Param("destId")String destId);
    
    /**
     * 
     * findDriverTaskState：查询当前驾驶员是否有任务在配送中
     * @param driverId
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年12月1日 上午9:05:14
     */
    int findDriverTaskState(@Param("driverId")String driverId); 
}