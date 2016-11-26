package com.ssic.game.admin.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class TImsMenuDto implements Serializable
{

    /**   
    * serialVersionUID: （一句话描述这个变量表示什么）      
    */

    private static final long serialVersionUID = 1L;
    @Getter
    @Setter
    private String id;
    @Getter
    @Setter
    private Date createTime;
    @Getter
    @Setter
    private String icon;
    @Getter
    @Setter
    private Integer isRight;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private Integer no;
    @Getter
    @Setter
    private String pjNo;
    @Getter
    @Setter
    private String remark;
    @Getter
    @Setter
    private Integer seq;
    @Getter
    @Setter
    private String url;
    @Getter
    @Setter
    private String pid;
    @Getter
    @Setter
    private String pname;
    @Getter
    @Setter
    private String menuTypeId;
    @Getter
    @Setter
    private String menuTypeName;
    @Getter
    @Setter
    private String iconCls;
    @Getter
    @Setter
    private Integer stat;
    @Getter
    @Setter
    private Integer tabType;
}
