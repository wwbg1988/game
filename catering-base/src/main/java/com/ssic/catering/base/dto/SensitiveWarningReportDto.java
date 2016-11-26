/**
 * 
 */
package com.ssic.catering.base.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**		
 * <p>Title: SensitiveWarningReportDto </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author pengcheng.yang	
 * @date 2015年8月11日 下午4:08:18	
 * @version 1.0
 * <p>修改人：pengcheng.yang</p>
 * <p>修改时间：2015年8月11日 下午4:08:18</p>
 * <p>修改备注：</p>
 */
public class SensitiveWarningReportDto implements Serializable
{
    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private String cafetoriumId;

    @Getter
    @Setter
    private String sensitiveId;

    @Getter
    @Setter
    private String sensitiveName;

    @Getter
    @Setter
    private Integer count;

    @Getter
    @Setter
    private Date createTime;
    
    @Getter
    @Setter
    private String dateString;

    @Getter
    @Setter
    private Integer warning;

    @Getter
    @Setter
    private String message;

    @Getter
    @Setter
    private String addressName;

    @Getter
    @Setter
    private Float warningproportion;

    @Getter
    @Setter
    private String addressId;

    @Getter
    @Setter
    private Integer stat;
    
    @Getter
    @Setter
    private String url;

}

