package com.ssic.catering.base.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * 		
 * <p>Title: CommentSensitiveDto </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年8月6日 下午1:07:59	
 * @version 1.0
 * <p>修改人：pengcheng.yang</p>
 * <p>修改时间：2015年8月6日 下午1:07:59</p>
 * <p>修改备注：</p>
 */
public class CommentSensitiveDto implements Serializable
{
    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private String commentId;

    @Getter
    @Setter
    private String sensitiveId;

    @Getter
    @Setter
    private Date createTime;

    @Getter
    @Setter
    private Integer stat;
}

