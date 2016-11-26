package com.ssic.catering.lbs.dao;

import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.ssic.catering.lbs.documents.TransportTrailDto;
import com.ssic.catering.lbs.dto.TransportTaskDto;
import com.ssic.catering.lbs.mapper.TransportTaskExMapper;
import com.ssic.catering.lbs.mapper.TransportTaskMapper;
import com.ssic.catering.lbs.pojo.TransportTask;
import com.ssic.catering.lbs.pojo.TransportTaskExample;
import com.ssic.catering.lbs.pojo.TransportTaskExample.Criteria;
import com.ssic.util.base.MyBatisBaseDao;

/**
 * 		
 * <p>Title: TransportTaskDao </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author pengcheng.yang	
 * @date 2015年11月25日 下午12:31:43	
 * @version 1.0
 * <p>修改人：apple</p>
 * <p>修改时间：2015年11月25日 下午12:31:43</p>
 * <p>修改备注：</p>
 */
@Repository
public class TransportTaskDao extends MyBatisBaseDao<TransportTask>{

	@Autowired
	@Getter
	private TransportTaskMapper mapper;
	
	@Autowired
	private TransportTaskExMapper exMapper;
	
	/**
	 * 
	 * findtaskInfoByDriverId：查询当前登录任务人拥有任务
	 * @param driverId
	 * @return
	 * @exception	
	 * @author pengcheng.yang
	 * @date 2015年11月25日 下午12:41:03
	 */
	public List<TransportTask> findtaskInfoByDriverId(String driverId){
		TransportTaskExample example = new TransportTaskExample();
		Criteria criteria = example.createCriteria();
		if(!StringUtils.isEmpty(driverId)){
			criteria.andDriverIdEqualTo(driverId);
		}
		criteria.andStatEqualTo(1);//任务有效 1
		criteria.andStateNotEqualTo(3);//除去已完成的任务
		example.setOrderByClause("state desc");
		List<TransportTask> list = mapper.selectByExample(example);
		if(!StringUtils.isEmpty(list)){
			return list;
		}
		return null;
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
	public TransportTaskDto findByTaskAdminUserId(String taskId){
		if(!StringUtils.isEmpty(taskId)){
			return exMapper.findByTaskAdminUserId(taskId);
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
	 * @date 2015年11月26日 上午9:11:49
	 */
	public int updateTaskState(String taskId,int state){
		return exMapper.updateTaskState(taskId, state);
	}
	
	/**
	 * 
	 * findTaskIdState：查询当前项目下面所有正在运行的任务轨迹
	 * @param projectId
	 * @return
	 * @exception	
	 * @author pengcheng.yang
	 * @date 2015年11月26日 上午9:25:06
	 */
	public List<String> findTaskIdState(String projectId){
		return exMapper.findTaskIdState(projectId);
	}
	
	/**
	 * 
	 * findByTaskId：根据任务Id正在配送查询的任务出发地和目的地和经纬度
	 * @param taskId
	 * @return
	 * @exception	
	 * @author apple
	 * @date 2015年11月26日 下午1:26:14
	 */
	public List<TransportTrailDto> findByTaskId(String projectId){
		return exMapper.findByTaskId(projectId);
	}
	
	/**
	 * 
	 * findByTaskIdAndComplete：根据任务Id正在配送查询和配送完成的任务出发地和目的地和经纬度
	 * @param projectId
	 * @return
	 * @exception	
	 * @author apple
	 * @date 2015年11月26日 下午7:19:01
	 */
	public List<TransportTrailDto> findByTaskIdAndComplete(String projectId){
		return exMapper.findByTaskIdAndComplete(projectId);
	}
	
   
   /**
    * 
    * findAddressByDestId：根据目的地Id查询目的地地址
    * @param destId
    * @return
    * @exception	
    * @author pengcheng.yang
    * @date 2015年11月30日 下午3:07:03
    */
   public TransportTaskDto findAddressByDestId(String destId){
	   return exMapper.findAddressByDestId(destId);
   }
   
   /**
    * 
    * findDriverTaskState：查询当前驾驶员是否有任务在配送中
    * @param driverId
    * @return
    * @exception	
    * @author pengcheng.yang
    * @date 2015年12月1日 上午9:06:31
    */
   public int findDriverTaskState(String driverId){
       return exMapper.findDriverTaskState(driverId);
   }
}

