package com.ssic.catering.base.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * 		
 * <p>Title: CommentDto </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author pengcheng.yang	
 * @date 2015年8月3日 下午8:14:01	
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年8月3日 下午8:14:01</p>
 * <p>修改备注：</p>
 */
public class CommentDto implements Serializable
{
    
     /**   
     * serialVersionUID: （一句话描述这个变量表示什么）      
     */   
    
    private static final long serialVersionUID = -4538104242489692028L;

    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private String configListId;

    @Getter
    @Setter
    private String comment;

    @Getter
    @Setter
    private Integer stat;

    @Getter
    @Setter
    private Date lastUpdateTime;

    @Getter
    @Setter
    private Date createTime;

    @Getter
    @Setter
    private String cafetoriumId;

    @Getter
    @Setter
    private String userUniquenessId;
    
    @Getter
    @Setter
    private Integer countComments;
    
    /**
     * 食堂名称
     */
    @Getter
    @Setter
    private String cafeName;
    
    /**
     * 敏感词名称
     */
    @Getter
    @Setter
    private String sensitiveName;
    /**
     * 分组时间
     */
    @Getter
    @Setter
    private String groupTime;
}

