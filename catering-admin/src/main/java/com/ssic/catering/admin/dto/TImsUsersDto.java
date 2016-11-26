package com.ssic.catering.admin.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class TImsUsersDto implements Serializable
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
    private Integer age;
    @Getter
    @Setter
    private Date createdatetime;
    @Getter
    @Setter
    private String deptId;
    @Getter
    @Setter
    private Integer gender;
    @Getter
    @Setter
    private Integer isAdmin;
    @Getter
    @Setter
    private Integer isDelete;
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
    private Date createdatetimeStart;

    @Getter
    @Setter
    private Date createdatetimeEnd;

    @Getter
    @Setter
    private Date modifydatetimeStart;

    @Getter
    @Setter
    private Date modifydatetimeEnd;

    @Getter
    @Setter
    private String searchName;

    @Getter
    @Setter
    private String roleIds;

    @Getter
    @Setter
    private String roleNames;

    @Getter
    @Setter
    private String email;

    @Getter
    @Setter
    private String deptName;
    @Getter
    @Setter
    private String userTable;

    @Getter
    @Setter
    private String projectIds;

    @Getter
    @Setter
    private String deptIds;

}
