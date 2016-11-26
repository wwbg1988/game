package com.ssic.catering.base.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 		
 * <p>Title: PushAddressUserDto </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author pengcheng.yang	
 * @date 2015年8月11日 上午8:55:05	
 * @version 1.0
 * <p>修改人：pengcheng.yang</p>
 * <p>修改时间：2015年8月11日 上午8:55:05</p>
 * <p>修改备注：</p>
 */
public class PushAddressUserDto implements Serializable
{
    /**
     * 区域父id
     */
    @Getter
    @Setter
    private String parentId;
    
    /**
     * 区域负责人id
     */
    @Getter
    @Setter
    private String userId;
    
    /**
     * qjy_account帐号
     */
    @Getter
    @Setter
    private String qjyAccount;
    
    
    /**
     * 餐厅所属城市
     */
    @Getter
    @Setter
    private String addressName;
    
    @Getter
    @Setter
    private String addressId;
    
    @Getter
    @Setter
    private Integer addressType;
}

