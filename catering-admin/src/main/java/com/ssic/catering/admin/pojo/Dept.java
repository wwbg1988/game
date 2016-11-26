package com.ssic.catering.admin.pojo;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class Dept implements Serializable
{
    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private Date createTime;

    @Getter
    @Setter
    private int deptAdmin;

    @Getter
    @Setter
    private String deptLevel;

    @Getter
    @Setter
    private String deptName;

    @Getter
    @Setter
    private String projId;

    @Getter
    @Setter
    private int stat;
    
    @Getter
    @Setter
    private String deptCode;
    
    @Getter
    @Setter
    private String pid;
    
    @Getter
    @Setter
    private Date lastUpdateTime;

}
