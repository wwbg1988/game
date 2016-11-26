package com.ssic.catering.lbs.documents;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class TransportTrailDtoList implements Serializable{
    
    private static final long serialVersionUID = -8519483834400798157L;
    
    /**
	 * 任务Id
	 */
	@Setter
	@Getter
    private String id;
	
	/**
	 * 出发点
	 */
	@Setter
	@Getter
	private String departure;
	
	/**
	 * 目的地
	 */
	@Setter
	@Getter
	private String destination;
	
	/**
	 * 驾驶员名字
	 */
	@Getter
	@Setter
	private String driverName;
	
	/**
	 * 目的地经度
	 */
	@Getter
	@Setter
	private String endLongitude;
	
	/**
	 * 目的地纬度
	 */
	@Getter
	@Setter
	private String endLatitude;
	
	/**
     * 任务运行的第一个点经度
     */
    @Getter
    @Setter
    private String startLongitude;
    
    /**
     *  任务运行的第一个点纬度
     */
    @Getter
    @Setter
    private String startLatitude;
	/**
	 * 货物名称
	 */
	@Setter
	@Getter
	private String merchandise;
	
	/**
	 * 任务名称
	 */
	@Getter
	@Setter
	private String taskName;
	
	/**
     * 电话
     */
    @Getter
    @Setter
    private String telephone;
    
    /**
     * 任务结束时间
     */
    @Getter
    @Setter
    private String endTime;
    
    /**
     * 供应商名字
     */
    @Getter
    @Setter
    private String supplierName;
    
    
    @Getter
    @Setter
    private String projectId;

    @Getter
    @Setter
    private String driverId;
    
    /**
     * 驾驶员是否在任务中
     */
    @Getter
    @Setter
    private int state;

    /**
     * 任务
     */
    @Getter
    @Setter
    private String transportTaskId;

	/**
	 * 组装手机端数据
	 */
	@Setter
	@Getter
	private List<TransportTrailList> resultList;
	
	/**
	 * 组装手机端数据
	 */
	@Setter
	@Getter
	private TransportTrail resultDto;
	
}