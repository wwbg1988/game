package com.ssic.catering.base.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * 		
 * <p>Title: ConfigDto </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author pengcheng.yang	
 * @date 2015年8月3日 下午8:09:50	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年8月3日 下午8:09:50</p>
 * <p>修改备注：</p>
 */
public class ConfigDto implements Serializable
{

    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private String configName;
    
    /**
     * 本项平均分
     */
    @Getter
    @Setter
    private String score;

    @Getter
    @Setter
    private Date createTime;

    @Getter
    @Setter
    private Date lastUpdateTime;
    
    @Getter
    @Setter
    private String projId;
    
    @Getter
    @Setter
    private String proName;
    
    @Getter
    @Setter
    private String imageUrl;

    @Getter
    @Setter
    private Integer stat;
    
}

