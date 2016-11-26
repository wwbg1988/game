package com.ssic.game.app.controller.lbs;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssic.catering.lbs.dao.mongo.TransportTrailMongoDao;
import com.ssic.catering.lbs.documents.TransportTrail;
import com.ssic.catering.lbs.documents.TransportTrailDto;
import com.ssic.catering.lbs.documents.TransportTrailDtoList;
import com.ssic.catering.lbs.documents.TransportTrailList;
import com.ssic.catering.lbs.dto.TransportTaskDto;
import com.ssic.catering.lbs.service.TransportDriverService;
import com.ssic.catering.lbs.service.TransportTaskService;
import com.ssic.game.app.listener.InitApplicationContext;
import com.ssic.game.common.util.HttpDataResponse;
import com.ssic.util.UUIDGenerator;
import com.ssic.util.constants.DataStatus;
import com.ssic.util.model.Response;

/**
 *      
 * <p>Title: AppLbsController </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author pengcheng.yang   
 * @date 2015年11月25日 下午1:26:46  
 * @version 1.0
 * <p>修改人：apple</p>
 * <p>修改时间：2015年11月25日 下午1:26:46</p>
 * <p>修改备注：</p>
 */
@Controller
@RequestMapping("/http/api/lbs")
public class AppLbsController {
    
    private static final Log logger = LogFactory.getLog(InitApplicationContext.class);
    
    @Autowired
    private TransportTaskService transportTaskService;
    
    @Autowired
    private TransportTrailMongoDao transportTrailDao;
    
    @Autowired
    private TransportDriverService transportDriverService;
    
    /**
     * 
     * transportDriverInfo：查询驾驶员正在运行任务结果集
     * @return
     * @exception   
     * @author pengcheng.yang
     * @date 2015年11月25日 下午1:48:28
     */
    @ResponseBody
    @RequestMapping(value = "/findTaskInfo.do",method = {RequestMethod.POST,RequestMethod.GET})
    public Response<List<TransportTaskDto>> transportTaskInfo(String driverId){
        logger.info("transportTaskInfo 参数 driverId = "+driverId);
        return transportTaskService.findtaskInfoByDriverId(driverId);
    }
    
    /**
     * 
     * insertTranSportTrailInfo：保存配送车辆行走轨迹信息
     * @param driverId
     * @param taskId
     * @param longitude
     * @param dimensions
     * @return
     * @exception   
     * @author pengcheng.yang
     * @date 2015年11月25日 下午3:12:20
     */
    @ResponseBody
    @RequestMapping(value = "/insertTranSportInfo.do",method = {RequestMethod.POST,RequestMethod.GET})
    public Response<String> insertTranSportTrailInfo(String suppliersId,String driverId,String taskId,String longitude,String latitude,String addrStr,String imei,String time,String distance){
        Response<String> result = new Response<String>();
        try {
            
            //查询供应商所属项目Id
            TransportTaskDto dto = transportTaskService.findByTaskAdminUserId(taskId);
            if(dto != null){
                TransportTrail tranil = new TransportTrail();
                tranil.setProjectId(dto.getProjectId());
                tranil.setDriverId(driverId);
                tranil.setTransportTaskId(taskId);
                tranil.setLongitude(longitude);
                tranil.setLatitude(latitude);
                tranil.setCoordinateAddress(addrStr);
                tranil.setId(UUIDGenerator.getUUID());
                tranil.setStat(DataStatus.ENABLED);
                tranil.setCreateTime(new Date());
                tranil.setCoordinateType(1);
                tranil.setImei(imei);
                tranil.setTime(time);
                tranil.setDistance(distance);
                //保存行车轨迹
                transportTrailDao.insertTranSportTrailInfo(tranil);
                result.setMessage("行车轨迹保存成功!");
                result.setStatus(HttpDataResponse.HTTP_SUCCESS);
                return result;
            }else{
                result.setStatus(HttpDataResponse.HTTP_FAILE);
                result.setMessage("行车轨迹保存失败!");
                return result;
            }
        } catch (Exception e) {
            result.setStatus(HttpDataResponse.HTTP_FAILE);
            result.setMessage("行车轨迹保存失败!");
            return result;
        }
    }
    
    /**
     * 
     * updateTaskAndDriverState：更改任务状态和驾驶员是否在任务中的状态
     * @param account
     * @param taskId
     * @param typeId
     * @return
     * @exception   
     * @author pengcheng.yang
     * @date 2015年11月26日 下午6:57:29
     */
    @ResponseBody
    @RequestMapping(value = "/updateTaskAndDriverState.do",method = {RequestMethod.POST,RequestMethod.GET})
    public Response<String> updateTaskAndDriverState(String driverId,String taskId,String typeId){ 
        	Response<String> result = new Response<String>();
        	if(!StringUtils.isEmpty(typeId) && !StringUtils.isEmpty(driverId) && !StringUtils.isEmpty(taskId)){
        		//如果是开始任务保存轨迹成功后更改任务状态为 2 （typeId 0:开始 1:结束）
        		if("0".equals(typeId)){
        			//检查当前驾驶员是否有任务正在配送中
        			int state = transportTaskService.findDriverTaskState(driverId);
        			if(state == 0){
        			    //如果是开始任务去驾驶员表中标识该驾驶员有任务
        			    int flag1 = transportTaskService.updateTaskState(taskId, 2);
        			    int flag2 = transportDriverService.updateDriverState(driverId,1);
        				if(flag1 == 1 && flag2 == 1){
        					result.setStatus(HttpDataResponse.HTTP_SUCCESS);
        					result.setMessage("任务开始成功!");
        					return result;
        				}else{
        					result.setStatus(HttpDataResponse.HTTP_FAILE);
        					result.setMessage("任务开始失败!");
        					return result;
        				}
        			}else{
        				result.setStatus(HttpDataResponse.HTTP_FAILE);
        				result.setMessage("当前正在有任务配送中!");
        				return result;
        			}
        		}
        		//如果是开始任务保存轨迹成功后更改任务状态为 3 （typeId 0:开始 1:结束）
        		if("1".equals(typeId)){
        			int bool1 = transportTaskService.updateTaskState(taskId, 3);
        			//如果是开始任务去驾驶员表中标识该驾驶员有任务
        			int bool2 = transportDriverService.updateDriverState(driverId,0);
        			if(bool1 == 1 && bool2 == 1){
        				result.setStatus(HttpDataResponse.HTTP_SUCCESS);
        				result.setMessage("任务结束成功!");
        				return result;
        			}else{
        				result.setStatus(HttpDataResponse.HTTP_FAILE);
        				result.setMessage("任务结束失败!");
        				return result;
        			}
        		}
        	}
        		result.setStatus(HttpDataResponse.HTTP_FAILE);
    			result.setMessage("参数异常!");
    			return result;
    }
    
    
    /**
     * 
     * findBydriverIdAndTaskId：供应商查询正在配送中的车辆形式轨迹
     * @param tranil
     * @return
     * @exception   
     * @author pengcheng.yang
     * @date 2015年11月25日 下午6:07:18
     */
    @ResponseBody
    @RequestMapping(value = "/findTranSport.do",method = {RequestMethod.POST,RequestMethod.GET})
    public Response<List<TransportTrailDtoList>> findBydriverIdAndTaskId(String projectId){
        
        Response<List<TransportTrailDtoList>> result = new Response<List<TransportTrailDtoList>>();
        //查询当前项目下所有的正在运行的所有任务
        List<TransportTrailDto> taskList = transportTaskService.findByTaskId(projectId);
        
        List<TransportTrailDtoList> dtoList = new ArrayList<TransportTrailDtoList>();
        if(taskList != null && taskList.size() > 0){
            for (int i = 0; i < taskList.size(); i++) {
                TransportTrailDtoList dlist = new TransportTrailDtoList();
                dlist.setEndLatitude(taskList.get(i).getEndLatitude());
                dlist.setEndLongitude(taskList.get(i).getEndLongitude());
                dlist.setStartLatitude(taskList.get(i).getStartLatitude());
                dlist.setStartLongitude(taskList.get(i).getStartLongitude());
                dlist.setTaskName(taskList.get(i).getTaskName());
                dlist.setMerchandise(taskList.get(i).getMerchandise());
                dlist.setTelephone(taskList.get(i).getTelephone());
                dlist.setSupplierName(taskList.get(i).getSupplierName());
                dlist.setDeparture(taskList.get(i).getDeparture());
                dlist.setDestination(taskList.get(i).getDestination());
                dlist.setDriverName(taskList.get(i).getDriverName());
                dlist.setDriverId(taskList.get(i).getDriverId());
                dlist.setProjectId(taskList.get(i).getProjectId());
                dlist.setTransportTaskId(taskList.get(i).getTransportTaskId());
                dlist.setId(taskList.get(i).getId());
                //查询项目下每一条任务的运行轨迹
                TransportTrail trailDto = transportTrailDao.findBydriverIdAndTaskId(projectId,taskList.get(i).getId());
                dlist.setResultDto(trailDto);
                dtoList.add(dlist);
            }
            result.setStatus(HttpDataResponse.HTTP_SUCCESS);
            result.setMessage("查询项目下所有正在运行的任务成功!");
            result.setData(dtoList);
            return result;
            
        }else{
            result.setStatus(HttpDataResponse.HTTP_FAILE);
            result.setMessage("当前项目下没有任务!");
            return result;
        }
        //查询当前所属项目下面所有正在运行的任务Id
//      List<String> listTaskId = transportTaskService.findTaskIdState(projectId);
        
    }
    
    
    /**
     * 
     * findBydriverIdAndTaskIding：供应商查询正在配送中和配送完成的车辆形式轨迹
     * @param projectId
     * @return
     * @exception   
     * @author pengcheng.yang
     * @date 2015年11月26日 下午7:48:04
     */
    @ResponseBody
    @RequestMapping(value = "/findTaskingAndTask.do",method = {RequestMethod.POST,RequestMethod.GET})
    public Response<List<TransportTrailDtoList>> findBydriverIdAndTaskIding(String projectId){
        
        Response<List<TransportTrailDtoList>> result = new Response<List<TransportTrailDtoList>>();
        //查询当前项目下所有的正在运行的所有任务
        List<TransportTrailDto> taskList = transportTaskService.findByTaskIdAndComplete(projectId);
        
        List<TransportTrailDtoList> dtoList = new ArrayList<TransportTrailDtoList>();
        if(taskList != null && taskList.size() > 0){
            for (int i = 0; i < taskList.size(); i++) {
                TransportTrailDtoList dlist = new TransportTrailDtoList();
                dlist.setEndLatitude(taskList.get(i).getEndLatitude());
                dlist.setEndLongitude(taskList.get(i).getEndLongitude());
                dlist.setStartLatitude(taskList.get(i).getStartLatitude());
                dlist.setStartLongitude(taskList.get(i).getStartLongitude());
                dlist.setTaskName(taskList.get(i).getTaskName());
                dlist.setMerchandise(taskList.get(i).getMerchandise());
                dlist.setTelephone(taskList.get(i).getTelephone());
                dlist.setSupplierName(taskList.get(i).getSupplierName());
                dlist.setDeparture(taskList.get(i).getDeparture());
                dlist.setDestination(taskList.get(i).getDestination());
                dlist.setDriverName(taskList.get(i).getDriverName());
                dlist.setId(taskList.get(i).getId());
                dlist.setDriverId(taskList.get(i).getDriverId());
                dlist.setProjectId(taskList.get(i).getProjectId());
                dlist.setTransportTaskId(taskList.get(i).getTransportTaskId());
                dlist.setState(taskList.get(i).getState());
                if(taskList.get(i).getEndTime() != null){
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    String endTime = format.format(taskList.get(i).getEndTime());
                    dlist.setEndTime(endTime);
                }else{
                    dlist.setEndTime("");
                }
                //查询项目下每一条任务的运行轨迹
                List<TransportTrailList> trail = new ArrayList<TransportTrailList>();
                List<TransportTrail> list = transportTrailDao.findBydriverIdAndTaskIding(projectId,taskList.get(i).getId());
                if (list.size() > 0)
                {
                    for (int j = 0; j < list.size(); j++)
                    {
                        TransportTrailList tlist = new TransportTrailList();
                        tlist.setId(list.get(j).getId());
                        tlist.setLatitude(list.get(j).getLatitude());
                        tlist.setLongitude(list.get(j).getLongitude());
                        tlist.setTime(list.get(j).getTime());
                        tlist.setDistance(list.get(j).getDistance());
                        
                        trail.add(tlist);
                        dlist.setResultList(trail);
                    }
                }
                dtoList.add(dlist);
            }
            result.setStatus(HttpDataResponse.HTTP_SUCCESS);
            result.setMessage("查询项目下所有正在运行的任务成功!");
            result.setData(dtoList);
            return result;
            
        }else{
            result.setStatus(HttpDataResponse.HTTP_FAILE);
            result.setMessage("当前项目下没有任务!");
            return result;
        }
    }

}

