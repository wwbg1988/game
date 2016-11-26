package com.ssic.catering.lbs.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ssic.catering.lbs.dao.TransportDriverDao;
import com.ssic.catering.lbs.dao.TransportTaskDao;
import com.ssic.catering.lbs.documents.TransportTrailDto;
import com.ssic.catering.lbs.dto.TransportTaskDto;
import com.ssic.catering.lbs.pojo.TransportTask;
import com.ssic.catering.lbs.service.TransportTaskService;
import com.ssic.util.BeanUtils;
import com.ssic.util.constants.DataStatus;
import com.ssic.util.model.Response;

/**
 * 		
 * <p>Title: TransportTaskServiceImpl </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author pengcheng.yang	
 * @date 2015年11月25日 下午1:15:53	
 * @version 1.0
 * <p>修改人：apple</p>
 * <p>修改时间：2015年11月25日 下午1:15:53</p>
 * <p>修改备注：</p>
 */
@Service
public class TransportTaskServiceImpl implements TransportTaskService {
    
	 @Autowired
	 private TransportTaskDao transportTaskDao;
	 
	 @Autowired
     private TransportDriverDao transportDriverDao;
	 
	 /**
	  * 
	  * findtaskInfoByDriverId:查询当前登录任务人拥有任务
	  * @param driverId
	  * @return
	  * @exception	
	  * @author pengcheng.yang
	  * @date 2015年11月25日 下午1:16:50
	  */
	@Override
	public Response<List<TransportTaskDto>> findtaskInfoByDriverId(String driverId) {
		
		List<TransportTaskDto> listDto = new ArrayList<TransportTaskDto>();
		//查询任务结果集
		List<TransportTask> list = transportTaskDao.findtaskInfoByDriverId(driverId);
		
		if(!StringUtils.isEmpty(list)){
			//把pojo转化成dto
			listDto = BeanUtils.createBeanListByTarget(list,TransportTaskDto.class);
			//根据目的地的Id查询目的地的地址
			for (int i = 0; i < listDto.size(); i++) {
				TransportTaskDto dto = transportTaskDao.findAddressByDestId(listDto.get(i).getTransportDestId());
				listDto.get(i).setTransportAddress(dto.getTransportAddress());
				listDto.get(i).setLatitude(dto.getLatitude());
				listDto.get(i).setLongitude(dto.getLongitude());
			}
		}else{
			return new Response<List<TransportTaskDto>>(DataStatus.HTTP_FAILE,"无法查询到任务信息",new ArrayList<TransportTaskDto>());
		}
		return new Response<List<TransportTaskDto>>(DataStatus.HTTP_SUCCESS, "",listDto);
	}
	

	/**
	 * 
	 * findByTaskAdminUserId：根据供应商Id查询任务所属项目Id
	 * @param adminUserId
	 * @return
	 * @exception	
	 * @author pengcheng.yang
	 * @date 2015年11月25日 下午5:37:23
	 */
	@Override
	public TransportTaskDto findByTaskAdminUserId(String taskId) {
		TransportTaskDto dto = transportTaskDao.findByTaskAdminUserId(taskId);
		if(dto != null){
			return dto;
		}
		return null;
	}

	/**
	 * 
	 * updateTaskState：更改任务的状态
	 * @param taskId
	 * @param state
	 * @return
	 * @exception	
	 * @author pengcheng.yang
	 * @date 2015年11月26日 上午9:13:25
	 */
	@Override
	public int updateTaskState(String taskId, int state) {
		int flag = transportTaskDao.updateTaskState(taskId, state);
		if(flag > 0){
			return flag;
		}
		return 0;
	}
	
	/**
	 * 
	 * findTaskIdState：查询当前项目下面所有正在运行的任务轨迹
	 * @param projectId
	 * @return
	 * @exception	
	 * @author pengcheng.yang
	 * @date 2015年11月26日 上午9:26:06
	 */
	@Override
	public List<String> findTaskIdState(String projectId) {
		return transportTaskDao.findTaskIdState(projectId);
	}

	/**
	 * 
	 * findByTaskId：根据任务Id查询任务的出发地和目的地和经纬度
	 * @param taskId
	 * @return
	 * @exception	
	 * @author pengcheng.yang
	 * @date 2015年11月26日 下午1:53:38
	 */
	@Override
	public List<TransportTrailDto> findByTaskId(String projectId) {
		if(!StringUtils.isEmpty(projectId)){
			return transportTaskDao.findByTaskId(projectId);
		}
		return null;
	}

	/**
	 * 
	 * findByTaskIdAndComplete：根据任务Id查询正在配送中和配送完成的任务的出发地和目的地和经纬度
	 * @param projectId
	 * @return
	 * @exception	
	 * @author pengcheng.yang
	 * @date 2015年11月26日 下午7:20:09
	 */
	@Override
	public List<TransportTrailDto> findByTaskIdAndComplete(String projectId) {
		if(!StringUtils.isEmpty(projectId)){
			return transportTaskDao.findByTaskIdAndComplete(projectId);
		}
		return null;
	}
	
	/**
     * 
     * findDriverTaskState：查询当前驾驶员是否有任务在配送中
     * @param driverId
     * @return
     * @exception   
     * @author pengcheng.yang
     * @date 2015年12月1日 上午9:07:24
     */
    @Override
    public int findDriverTaskState(String driverId)
    {
        return transportTaskDao.findDriverTaskState(driverId);
    }

}

