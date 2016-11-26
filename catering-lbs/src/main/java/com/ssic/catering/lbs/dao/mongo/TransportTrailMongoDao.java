/**
 * 
 */
package com.ssic.catering.lbs.dao.mongo;

import static org.springframework.data.mongodb.core.query.Criteria.where;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.ssic.catering.lbs.documents.TransportTrail;
import com.ssic.catering.lbs.repositories.TransportTrailRepository;
import com.ssic.game.common.dto.PageHelperDto;
import com.ssic.util.ArrayUtils;
import com.ssic.util.constants.DataStatus;

/**		
 * <p>Title: TransportTrailDao </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author rkzhang	
 * @date 2015年11月25日 下午1:37:37	
 * @version 1.0
 * <p>修改人：rkzhang</p>
 * <p>修改时间：2015年11月25日 下午1:37:37</p>
 * <p>修改备注：</p>
 */
@Repository
public class TransportTrailMongoDao {
    
    protected static final Log logger = LogFactory.getLog(TransportTrailMongoDao.class);

    @Autowired
    private MongoOperations template;
    
    @Autowired
    private TransportTrailRepository repository;
    
    /**
     * 
     * insertTranSportTrailInfo：保存配送车辆的行走轨迹
     * @param tranil
     * @exception	
     * @author pengcheng.yang
     * @date 2015年11月25日 下午4:00:59
     */
    public void insertTranSportTrailInfo(TransportTrail tranil) {
    	repository.save(tranil);
    }
    /**
     * 
     * findBydriverIdAndTaskId：查询正在配送中的车辆运行轨迹
     * @param tranil
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年11月25日 下午4:54:42
     */
    public TransportTrail findBydriverIdAndTaskId(String projectId,String taskId){
    	Query query = new Query();
    	query.addCriteria(where("stat").is(DataStatus.ENABLED));
    	if(!StringUtils.isEmpty(projectId) && !StringUtils.isEmpty(taskId)){
    		query.addCriteria(where("projectId").is(projectId));
    		query.addCriteria(where("transportTaskId").is(taskId));
    		List<Order> orderList = new ArrayList<Sort.Order>();
    		orderList.add(new Order(Sort.Direction.DESC, "createTime"));
    		Sort sort = new Sort(orderList);
    		query.with(sort);
    		TransportTrail list = template.findOne(query, TransportTrail.class);
    		if(list != null){
    			return list;
    		}
    	}
    	return null;
    }
    
    /**
     * 
     * findBydriverIdAndTaskIdAsc：查询任务的第一个坐标点
     * @param projectId
     * @param taskId
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年12月3日 下午4:21:20
     */
    public TransportTrail findBydriverIdAndTaskIdAsc(String projectId,String taskId){
        Query query = new Query();
        query.addCriteria(where("stat").is(DataStatus.ENABLED));
        if(!StringUtils.isEmpty(projectId) && !StringUtils.isEmpty(taskId)){
            query.addCriteria(where("projectId").is(projectId));
            query.addCriteria(where("transportTaskId").is(taskId));
            List<Order> orderList = new ArrayList<Sort.Order>();
            orderList.add(new Order(Sort.Direction.ASC, "createTime"));
            Sort sort = new Sort(orderList);
            query.with(sort);
            TransportTrail list = template.findOne(query, TransportTrail.class);
            if(list != null){
                return list;
            }
        }
        return null;
    }
    
    
    /**
     * 
     * findBydriverIdAndTaskIding：查询正在配送中和配送完成的车辆轨迹
     * @param projectId
     * @param taskId
     * @return
     * @exception	
     * @author apple
     * @date 2015年11月26日 下午7:22:06
     */
    public List<TransportTrail> findBydriverIdAndTaskIding(String projectId,String taskId){
    	Query query = new Query();
    	query.addCriteria(where("stat").is(DataStatus.ENABLED));
    	if(!StringUtils.isEmpty(projectId) && !StringUtils.isEmpty(taskId)){
    		query.addCriteria(where("projectId").is(projectId));
    		query.addCriteria(where("transportTaskId").is(taskId));
    		List<Order> orderList = new ArrayList<Sort.Order>();
    		orderList.add(new Order(Sort.Direction.ASC, "createTime"));
    		Sort sort = new Sort(orderList);
    		query.with(sort);
    		List<TransportTrail> list = template.find(query, TransportTrail.class);
    		if(list != null){
    			return list;
    		}
    	}
    	return new ArrayList<TransportTrail>();
    }
    
    /**
     * 
     * findBy：查询
     * @param transportTrail
     * @return
     * @exception	
     * @author zhuzhen
     * @date 2015年11月27日 下午3:20:59
     */
    public List<TransportTrail> findBy(TransportTrail param ,PageHelperDto ph)
    {
        Query query = new Query();
        
        if(param != null)
        {
            //id
            if(!StringUtils.isEmpty(param.getId()))
            {
              query.addCriteria(where("id").is(param.getId()));
            }
            //projectId
            if(!StringUtils.isEmpty(param.getProjectId()))
            {
                if(param.getProjectId().contains(","))
                {
                    String[] projectIds =param.getProjectId().split(",");
                    if(!ArrayUtils.isEmpty(projectIds))
                    {
                        query.addCriteria(where("projectId").in(Arrays.asList(projectIds)));
                    }
                }
                else
                {
                    query.addCriteria(where("projectId").is(param.getProjectId()));
                }
            }
            //driverId
            if(!StringUtils.isEmpty(param.getDriverId()))
            {
              query.addCriteria(where("driverId").is(param.getDriverId()));
            }
            //transportTaskId
            if(!StringUtils.isEmpty(param.getTransportTaskId()))
            {
              query.addCriteria(where("transportTaskId").is(param.getTransportTaskId()));
            }
            //typeId
            if(!StringUtils.isEmpty(param.getTypeId()))
            {
              query.addCriteria(where("typeId").is(param.getTypeId()));
            }
            //coordinateAddress
            if(!StringUtils.isEmpty(param.getCoordinateAddress()))
            {
              query.addCriteria(where("coordinateAddress").is(param.getCoordinateAddress()));
            }
            //coordinateType
            if(null != param.getCoordinateType())
            {
              query.addCriteria(where("coordinateType").is(param.getCoordinateType()));
            }
            //createTime
            //lastUpdateTime
            //stat
            if(null == param.getStat())
            {
              query.addCriteria(where("stat").is(DataStatus.ENABLED));
            }
            //imei
            if(!StringUtils.isEmpty(param.getImei()))
            {
              query.addCriteria(where("imei").is(param.getImei()));
            }
            //longitude
            if(!StringUtils.isEmpty(param.getLongitude()))
            {
              query.addCriteria(where("longitude").is(param.getLongitude()));
            }
            //latitude
            if(!StringUtils.isEmpty(param.getLatitude()))
            {
              query.addCriteria(where("latitude").is(param.getLatitude()));
            }
        }
        
       
        
        
        if(ph != null)
        {
            //开始下标
            if(ph.getBeginRow()==null || ph.getBeginRow() < 0)
            {
                ph.setBeginRow(0);
            }
            
            //当前页
            if(ph.getPage() <= 0)
            {
                ph.setPage(1);
            }
            
            //页面大小
            if(ph.getRows() <= 0)
            {
                ph.setRows(1);
            }
            
            query.skip((ph.getPage()-1)*ph.getRows());
            query.limit(ph.getRows());
            
            List<Order> orderList = new ArrayList<Sort.Order>();

            //排序字段 
            if(!StringUtils.isEmpty(ph.getSort()) && !StringUtils.isEmpty(ph.getOrder()))
            {
                orderList.add(new Order(Sort.Direction.fromString(ph.getOrder()), ph.getSort()));
            }
            else
            {
                
                orderList.add(new Order(Sort.Direction.DESC, "createTime"));
               
            }
            
            Sort sort = new Sort(orderList);
            query.with(sort);
        }
        
        List<TransportTrail> list = template.find(query, TransportTrail.class);
        if(list != null){
            return list;
        }
        
        return null;
    }
    
    /**
     * 
     * findCountBy：一句话描述方法功能
     * @param param
     * @return
     * @exception	
     * @author zhuzhen
     * @date 2015年11月27日 下午3:55:08
     */
    public long findCountBy(TransportTrail param)
    {
        Query query = new Query();
        
        if(param != null)
        {
            //id
            if(!StringUtils.isEmpty(param.getId()))
            {
              query.addCriteria(where("id").is(param.getId()));
            }
            //projectId
            if(!StringUtils.isEmpty(param.getProjectId()))
            {
              if(param.getProjectId().contains(","))
              {
                  String[] projectIds =param.getProjectId().split(",");
                  if(!ArrayUtils.isEmpty(projectIds))
                  {
                      query.addCriteria(where("projectId").in(Arrays.asList(projectIds)));
                  }
              }
              else
              {
                  query.addCriteria(where("projectId").is(param.getProjectId()));
              }
            }
            //driverId
            if(!StringUtils.isEmpty(param.getDriverId()))
            {
              query.addCriteria(where("driverId").is(param.getDriverId()));
            }
            //transportTaskId
            if(!StringUtils.isEmpty(param.getTransportTaskId()))
            {
              query.addCriteria(where("transportTaskId").is(param.getTransportTaskId()));
            }
            //typeId
            if(!StringUtils.isEmpty(param.getTypeId()))
            {
              query.addCriteria(where("typeId").is(param.getTypeId()));
            }
            //coordinateAddress
            if(!StringUtils.isEmpty(param.getCoordinateAddress()))
            {
              query.addCriteria(where("coordinateAddress").is(param.getCoordinateAddress()));
            }
            //coordinateType
            if(null != param.getCoordinateType())
            {
              query.addCriteria(where("coordinateType").is(param.getCoordinateType()));
            }
            //createTime
            //lastUpdateTime
            //stat
            if(null == param.getStat())
            {
              query.addCriteria(where("stat").is(DataStatus.ENABLED));
            }
            //imei
            if(!StringUtils.isEmpty(param.getImei()))
            {
              query.addCriteria(where("imei").is(param.getImei()));
            }
            //longitude
            if(!StringUtils.isEmpty(param.getLongitude()))
            {
              query.addCriteria(where("longitude").is(param.getLongitude()));
            }
            //latitude
            if(!StringUtils.isEmpty(param.getLatitude()))
            {
              query.addCriteria(where("latitude").is(param.getLatitude()));
            }
        }
        
        return template.count(query, TransportTrail.class);
    }
    
}

