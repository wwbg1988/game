package com.ssic.game.common.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * 		
 * <p>Title: MenuDto </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author milkteaye	
 * @date 2015年8月12日 下午1:40:18	
 * @version 1.0
 * <p>修改人：milkteaye</p>
 * <p>修改时间：2015年8月12日 下午1:40:18</p>
 * <p>修改备注：</p>
 */
public class MenuDto implements Serializable
{
    @Getter
    @Setter
    private String id;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private String remark;
    @Getter
    @Setter
    private String url;
    @Getter
    @Setter
    private String pid;
    @Getter
    @Setter
    private Integer stat;
    @Getter
    @Setter
    private Date createTime;
    @Getter
    @Setter
    private Date lastUpdateTime;
    @Getter
    @Setter
    private String menuType;
    @Getter
    @Setter
    private String projId;
    @Getter
    @Setter
    private String procId;
    @Getter
    @Setter
    private String procType;
    @Getter
    @Setter
    private String projName;
    @Getter
    @Setter
    private String iconCls;
    
}
