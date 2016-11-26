package com.ssic.catering.lbs.documents;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class TransportTrailDto implements Serializable{
    
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
	 * 出发地纬度
	 */
	@Getter
	@Setter
	private String startLatitude;
	
	/**
     * 出发地经度
     */
    @Getter
    @Setter
	private String startLongitude;
	
	/**
	 * 电话
	 */
	@Getter
	@Setter
	private String telephone;
	
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
     * 任务结束时间
     */
    @Getter
    @Setter
    private Date endTime;
    
    /**
     * 任务
     */
    @Getter
    @Setter
    private String transportTaskId;
	
	/**
     * 移动速度
     */
    @Getter
    @Setter
    private String time;
    
    /**
     * 距离
     */
    @Getter
    @Setter
    private String distance;
    
    /**
     * 任务名称
     */
    @Getter
    @Setter
    private String taskName;
    
    /**
     * 货物名称
     */
    @Setter
    @Getter
    private String merchandise;
	
}