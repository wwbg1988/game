package com.ssic.catering.lbs.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * 		
 * <p>Title: SupplierDto </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author zhuzhen	
 * @date 2015年12月3日 下午4:08:10	
 * @version 1.0
 * <p>修改人：zhuzhen</p>
 * <p>修改时间：2015年12月3日 下午4:08:10</p>
 * <p>修改备注：</p>
 */
public class SupplierDto implements Serializable
{
    
    
     /**   
     * serialVersionUID: （一句话描述这个变量表示什么）      
     */   
    
    private static final long serialVersionUID = 3539476493118046457L;

    @Getter
    @Setter
    private String id;
    
    /**
     * 项目id
     * 
     */
    @Getter
    @Setter
    private String projectId;
    
    /**
     * 项目名称
     */
    @Setter
    @Getter
    private String projectName;

    /**
     * 供应商名称
     */
    @Getter
    @Setter
    private String supplierName;

    /**
     * 供应商地址
     */
    @Getter
    @Setter
    private String address;

    /**
     * 供应商地址经度
     */
    @Getter
    @Setter
    private String longitude;

    /**
     * 供应商地址纬度
     */
    @Getter
    @Setter
    private String latitude;

    @Getter
    @Setter
    private Date createTime;

    @Getter
    @Setter
    private Date lastUpdateTime;

    @Getter
    @Setter
    private Integer stat;

}

