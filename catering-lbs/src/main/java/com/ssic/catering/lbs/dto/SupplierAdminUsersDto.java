package com.ssic.catering.lbs.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * 		
 * <p>Title: SupplierAdminUsersDto </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author zhuzhen	
 * @date 2015年12月3日 下午4:08:03	
 * @version 1.0
 * <p>修改人：zhuzhen</p>
 * <p>修改时间：2015年12月3日 下午4:08:03</p>
 * <p>修改备注：</p>
 */
public class SupplierAdminUsersDto implements Serializable
{

    
     /**   
     * serialVersionUID: （一句话描述这个变量表示什么）      
     */   
    
    private static final long serialVersionUID = -658152965635078386L;
    
    @Getter
    @Setter
    private String id;

    /**
     * 后台账号
     */
    @Getter
    @Setter
    private String adminUsersId;

    /**
     * 供应商
     */
    @Getter
    @Setter
    private String supplierId;

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

