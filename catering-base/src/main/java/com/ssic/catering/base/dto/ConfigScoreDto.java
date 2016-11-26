package com.ssic.catering.base.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;


/**
 * 		
 * <p>Title: ConfigScore </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author pengcheng.yang
 * @date 2015年8月3日 下午7:53:57	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年8月3日 下午7:53:57</p>
 * <p>修改备注：</p>
 */
public class ConfigScoreDto implements Serializable
{

    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private String configId;
    @Getter
    @Setter
    private String configName;
    

    @Getter
    @Setter
    private String score;

    @Getter
    @Setter
    private String cafetoriumId;
    /**
     * 食堂名称
     */
    @Getter
    @Setter
    private String cafeName;

    @Getter
    @Setter
    private Date createTime;

    @Getter
    @Setter
    private Integer stat;
    
    @Getter
    @Setter
    private Integer countScore;
    /**
     * 分组时间
     */
    @Getter
    @Setter
    private String groupTime;
    
}

