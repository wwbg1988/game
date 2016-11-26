package com.ssic.catering.lbs.documents;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@ToString
@Document(collection = "transport_trail")
public class TransportTrail implements Serializable{
    
    private static final long serialVersionUID = -8519483834400798157L;

    @Getter
    @Setter
    private String id;
    
    @Getter
    @Setter
    private String projectId;

    @Getter
    @Setter
    private String driverId;

    /**
     * 任务
     */
    @Getter
    @Setter
    private String transportTaskId;

    /**
     * 轨迹的类型
     * 轨迹的类型，1开始，2运送途中，3结束
     */
    @Getter
    @Setter
    private String typeId;

    /**
     * 坐标具体名称
     */
    @Getter
    @Setter
    private String coordinateAddress;

    /**
     * 坐标类型，如火星坐标
     */
    @Getter
    @Setter
    private Integer coordinateType;

    /**
     * 创建时间
     */
    @Getter
    @Setter
    private Date createTime;

    /**
     * 更新时间
     */
    @Getter
    @Setter
    private Date lastUpdateTime;

    /**
     * 是否有效
     */
    @Getter
    @Setter
    private Integer stat;
    
    /**
	 * 设备编号
	 */
	@Setter
	@Getter
	private String imei;
	
	/**
	 * 经度
	 */
	@Getter
	@Setter
	private String longitude;
	
	/**
	 * 纬度
	 */
	@Getter
	@Setter
	private String latitude;
	
	/**
	 * 剩余时间
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

}