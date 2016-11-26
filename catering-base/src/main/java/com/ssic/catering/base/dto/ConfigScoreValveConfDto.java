package com.ssic.catering.base.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * 		
 * <p>Title: ConfigScoreValveConfDto </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年8月10日 上午10:15:35	
 * @version 1.0
 * <p>修改人：pengcheng.yang</p>
 * <p>修改时间：2015年8月10日 上午10:15:35</p>
 * <p>修改备注：</p>
 */
public class ConfigScoreValveConfDto implements Serializable
{
    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private String configId;

    @Getter
    @Setter
    private String cafetoriumId;
    
    @Getter
    @Setter
    private String cafetoriumName;

    @Getter
    @Setter
    private Integer level;

    @Getter
    @Setter
    private Integer valveCount;

    @Getter
    @Setter
    private Float valvePercent;

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

