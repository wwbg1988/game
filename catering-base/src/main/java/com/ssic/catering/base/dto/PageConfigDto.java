package com.ssic.catering.base.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 		
 * <p>Title: PageConfigDto </p>
 * <p>Description: 微信商城 页面配置项dto</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author zhuzhen	
 * @date 2015年11月23日 下午2:56:55	
 * @version 1.0
 * <p>修改人：zhuzhen</p>
 * <p>修改时间：2015年11月23日 下午2:56:55</p>
 * <p>修改备注：</p>
 */
@ToString
public class PageConfigDto implements Serializable
{
    
     /**   
     * serialVersionUID: （一句话描述这个变量表示什么）      
     */   
    
    private static final long serialVersionUID = 7162972338288544583L;

    /**
     * 主键id
     */
    @Getter
    @Setter
    private String id;

    /**
     * 食堂id
     */
    @Getter
    @Setter
    private String cafetoriumId;

    /**
     * 项目id
     */
    @Getter
    @Setter
    private String projectId;

    /**
     * 配置项描述
     */
    @Getter
    @Setter
    private String name;

    /**
     * 是否启用enabledUrl
     */
    @Getter
    @Setter
    private Integer isEnabled;

    /**
     * 启用时的URL
     */
    @Getter
    @Setter
    private String enabledUrl;

    /**
     * 不启用时的URL
     */
    @Getter
    @Setter
    private String notEnabledUrl;

    /**
     * 创建时间
     */
    @Getter
    @Setter
    private Date createTime;

    /**
     * 最后更新时间
     */
    @Getter
    @Setter
    private Date lastUpdateTime;

    /**
     * 是否有效
     */
    @Getter
    @Setter
    private Integer stat;
    
    /**
     * 项目名称
     */
    @Getter
    @Setter
    private String projectName;
    
    /**
     * 餐厅名字
     */
    @Getter
    @Setter
    private String cafetoriumName;

    

}

