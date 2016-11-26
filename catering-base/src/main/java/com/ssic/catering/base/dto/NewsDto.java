package com.ssic.catering.base.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class NewsDto implements Serializable{

    
     /**   
     * serialVersionUID: （一句话描述这个变量表示什么）      
     */   
    
    private static final long serialVersionUID = 9042544579539427510L;
    @Getter
    @Setter
    private String id;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private String listTitle;
    @Getter
    @Setter
    private String listText;
    @Getter
    @Setter
    private String listImageUrlId;
    @Getter
    @Setter
    private String title;
    @Getter
    @Setter
    private String text;
    @Getter
    @Setter
    private String textNewsImageId;
    @Getter
    @Setter
    private String textHtml;
    @Getter
    @Setter
    private Date newsTime;
    @Getter
    @Setter
    private String SNewsTime;
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
    private Integer state;
    @Getter
    @Setter
    private String projId;
    
    /**
     * 项目名称
     */
    @Getter
    @Setter
    private String projectName;


}
