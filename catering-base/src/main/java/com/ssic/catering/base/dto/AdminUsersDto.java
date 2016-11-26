package com.ssic.catering.base.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 		
 * <p>Title: AdminUsersDto </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author zhuzhen	
 * @date 2015年11月26日 下午1:34:55	
 * @version 1.0
 * <p>修改人：zhuzhen</p>
 * <p>修改时间：2015年11月26日 下午1:34:55</p>
 * <p>修改备注：</p>
 */
@ToString
public class AdminUsersDto implements Serializable
{
     /**   
     * serialVersionUID: （一句话描述这个变量表示什么）      
     */   
    
    private static final long serialVersionUID = 6864669965334243966L;
    
    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private Integer age;

    @Getter
    @Setter
    private Date createdatetime;

    @Getter
    @Setter
    private String deptId;

    @Getter
    @Setter
    private String email;

    @Getter
    @Setter
    private Integer gender;

    @Getter
    @Setter
    private Integer isadmin;

    @Getter
    @Setter
    private Date modifydatetime;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String pjNo;

    @Getter
    @Setter
    private String postNo;

    @Getter
    @Setter
    private String password;

    @Getter
    @Setter
    private String qjyAccount;

    @Getter
    @Setter
    private String userAccount;

    @Getter
    @Setter
    private String userImage;

    @Getter
    @Setter
    private String userNo;

    @Getter
    @Setter
    private Integer stat;

    @Getter
    @Setter
    private Integer isdelete;
    
    /**
     * 项目id
     */
    @Getter
    @Setter
    private String projectId;
    
    /**
     * 项目名称
     */
    @Getter
    @Setter
    private String projectName;
}

