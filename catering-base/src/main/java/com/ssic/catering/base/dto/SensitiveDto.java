package com.ssic.catering.base.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * 		
 * <p>Title: CtrSensitiveDto </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author pengcheng.yang
 * @date 2015年8月3日 下午7:35:31	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年8月3日 下午7:35:31</p>
 * <p>修改备注：</p>
 */
public class SensitiveDto implements Serializable
{
    @Getter
    @Setter
    private String id;
    
    @Getter
    @Setter
    private String sensitiveName;
    
    @Getter
    @Setter
    private Integer stat;
    
    @Getter
    @Setter
    private Date createTime;
    
    @Getter
    @Setter
    private Integer warning;
    
    @Getter
    @Setter
    private String valveId;
    
    @Getter
    @Setter
    private String cafetoriumId;
    
    @Getter
    @Setter
    private String cafetoriumName;
    
    @Getter
    @Setter
    private Date lastUpdateTime;
    
    @Getter
    @Setter
    private Integer valveCount;
    
    @Getter
    @Setter
    private Float valvePercent;
    
}

