package com.ssic.catering.base.service;

import java.util.List;

import com.ssic.game.common.dto.PageHelperDto;
import com.ssic.catering.lbs.dto.TransportTaskAdminDto;

/**
 * 		
 * <p>Title: ITransportTaskService </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author zhuzhen	
 * @date 2015年12月4日 上午8:48:12	
 * @version 1.0
 * <p>修改人：zhuzhen</p>
 * <p>修改时间：2015年12月4日 上午8:48:12</p>
 * <p>修改备注：</p>
 */
public interface ITransportTaskAdminService
{
    /**
     * 
     * getTransportTaskDtoBy：分页查询
     * @param transportTaskDto
     * @param pageHelperDto
     * @return
     * @exception   
     * @author zhuzhen
     * @date 2015年11月26日 上午9:41:54
     */
    List<TransportTaskAdminDto> getTransportTaskAdminDtoBy(TransportTaskAdminDto transportTaskAdminDto, PageHelperDto pageHelperDto);
    
    /**
     * 
     * getTransportTaskDtoCountBy：获取总条数
     * @param transportTaskDto
     * @return
     * @exception   
     * @author zhuzhen
     * @date 2015年11月26日 上午9:49:15
     */
    long getTransportTaskAdminDtoCountBy(TransportTaskAdminDto transportTaskAdminDto);
    
    /**
     * 
     * addTransportTask：发布任务
     * @param transportTaskDto
     * @return
     * @exception   
     * @author zhuzhen
     * @date 2015年11月26日 下午2:54:54
     */
    int addTransportTask(TransportTaskAdminDto transportTaskAdminDto);
    
    /**
     * 
     * updateTransportTask：分配
     * @param transportTaskDto
     * @return
     * @exception   
     * @author zhuzhen
     * @date 2015年11月26日 下午3:14:29
     */
    int updateTransportTask(TransportTaskAdminDto transportTaskDto);
    
    /**
     * 
     * findById：一句话描述方法功能
     * @param id
     * @return
     * @exception   
     * @author zhuzhen
     * @date 2015年11月26日 下午3:21:24
     */
    TransportTaskAdminDto findById(String id);
    
    /**
     * 
     * dispatchingTask：配送任务
     * @param transportTaskDto
     * @return
     * @exception   
     * @author zhuzhen
     * @date 2015年11月30日 下午2:31:42
     */
    int dispatchingTask(TransportTaskAdminDto transportTaskDto);
}

