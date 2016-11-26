package com.ssic.catering.lbs.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * 		
 * <p>Title: TransportTaskDto </p>
 * <p>Description: 仅供后台使用</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author zhuzhen	
 * @date 2015年12月3日 下午6:44:20	
 * @version 1.0
 * <p>修改人：zhuzhen</p>
 * <p>修改时间：2015年12月3日 下午6:44:20</p>
 * <p>修改备注：</p>
 */
public class TransportTaskAdminDto implements Serializable
{

    
     /**   
     * serialVersionUID: （一句话描述这个变量表示什么）      
     */   
    
    private static final long serialVersionUID = 7923302209921946676L;
    
    /**
     * 主键
     */
    @Setter
    @Getter
    private String id;

    /**
     * 项目id
     */
    @Setter
    @Getter
    private String projectId;
    
    /**
     * 任务名称
     */
    @Setter
    @Getter
    private String taskName;

    /**
     * 任务发布人（团餐公司id）
     */
    @Setter
    @Getter
    private String adminUserId;

    /**
     * 供应商id
     */
    @Setter
    @Getter
    private String adminSupplierUserId;

    /**
     * 运送目的地id
     */
    @Setter
    @Getter
    private String transportDestId;

    /**
     * 驾驶员id
     */
    @Setter
    @Getter
    private String driverId;

    /**
     * 出发地
     */
    @Setter
    @Getter
    private String departPlace;
    
    /**
     * 货物名称
     */
    @Setter
    @Getter
    private String merchandise;

    /**
     * 任务状态
     * 1未开始,2进行中，3已完成
     */
    @Setter
    @Getter
    private Integer state;

    /**
     * 创建时间
     */
    @Setter
    @Getter
    private Date createTime;

    /**
     * 更新时间
     */
    @Setter
    @Getter
    private Date lastUpdateTime;

    /**
     * 是否有效
     */
    @Setter
    @Getter
    private Integer stat;
    
    /**
     * 项目名称
     */
    @Setter
    @Getter
    private String projectName;
    
    /**
     * 任务发布人名称
     */
    @Setter
    @Getter
    private String adminUserName;
    
    /**
     * 供应商名字
     */
    @Setter
    @Getter
    private String adminSupplierUserName;   
    
    /**
     * 目的地
     */
    @Setter
    @Getter
    private String destPlace;
    
    /**
     * 运送人名字
     */
    @Setter
    @Getter
    private String transportDriverName;
    
    /**
     * 运送目的地id
     */
    @Setter
    @Getter
    private String transportAddress;
    
    /**
     * 供应商经度
     */
    @Getter
    @Setter
    private String longitude;
    
    /**
     * 供应商纬度
     */
    @Getter
    @Setter
    private String latitude;
    
    
    /**
     * 供应商名字
     */
    @Getter
    @Setter
    private String supplierName;
    
    
    /**
     * 供应商地址
     */
    @Setter
    @Getter
    private String supplierAddress;
}

