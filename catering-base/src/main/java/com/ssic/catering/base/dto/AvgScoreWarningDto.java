package com.ssic.catering.base.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
/**
 * 		
 * <p>Title: AvgScoreWarningDto </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年8月10日 下午3:25:40	
 * @version 1.0
 * <p>修改人：pengcheng.yang</p>
 * <p>修改时间：2015年8月10日 下午3:25:40</p>
 * <p>修改备注：</p>
 */
public class AvgScoreWarningDto implements Serializable
{
    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private Float avgScore;

    @Getter
    @Setter
    private String cafetoriumId;

    @Getter
    @Setter
    private Date createTime;

    @Getter
    @Setter
    private Integer stat;

    @Getter
    @Setter
    private String userId;

    @Getter
    @Setter
    private String message;
    
    @Getter
    @Setter
    private String parentId;

    @Getter
    @Setter
    private String addressName;

    @Getter
    @Setter
    private String qjyAccount;
}

