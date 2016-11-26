package com.ssic.game.common.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class ImsUsersDto implements Serializable
{

    private static final long serialVersionUID = 1L;

    @Getter
    @Setter
    private String id;
    @Getter
    @Setter
    private Integer age;  //年龄
    @Getter
    @Setter
    private Date createdatetime;  //创建时间
    @Getter
    @Setter
    private String deptId; //部门id
    @Getter
    @Setter
    private Integer gender; //男1   女2
    @Getter
    @Setter
    private Integer isAdmin;  //是否是部门管理员 1是：0否;
    @Getter
    @Setter
    private Integer isDelete;  //不使用的
    @Getter
    @Setter
    private Date modifydatetime; //修改时间
    @Getter
    @Setter
    private String name;  //姓名
    @Getter
    @Setter
    private String password; //密码
    @Getter
    @Setter
    private String qjyAccount; //亲家云账号
    @Getter
    @Setter
    private String userAccount; //登录账号
    @Getter
    @Setter
    private String userImage; //上传图片
    @Getter
    @Setter
    private String userNo;  //用户编号,当员工编号适用;

    @Getter
    @Setter
    private Date createdatetimeStart; //  查询条件参数：创建日期 开始时间

    @Getter
    @Setter
    private Date createdatetimeEnd; //  查询条件参数：创建日期  结束时间

    @Getter
    @Setter
    private Date modifydatetimeStart; //  查询条件参数：修改日期 开始时间

    @Getter
    @Setter
    private Date modifydatetimeEnd; //  查询条件参数：修改日期 结束时间

    @Getter
    @Setter
    private String searchName; // 姓名的查询条件参数

    @Getter
    @Setter
    private String roleIds; // 用户的角色id集合,有多个，","分割

    @Getter
    @Setter
    private String roleNames;  // 用户的角色名称集合,有多个，","分割

    @Getter
    @Setter
    private String email; //邮箱

    @Getter
    @Setter
    private String deptName;//部门名称

    @Getter
    @Setter
    private String userFriends;  // 用户的好友id集合,有多个，","分割
    @Getter
    @Setter
    private Integer stat;  //是否有效  1：是 0:否;
    @Getter
    @Setter
    private String projId; //用户所拥有的项目的id
    @Getter
    @Setter
    private String projName;  //用户所拥有的项目的名称

    @Getter
    @Setter
    private String projectIds; //用户所拥有的项目的id集合

    @Getter
    @Setter
    private String deptIds;  //用户所有的部门id集合 
    @Getter
    @Setter
    private String projectNames; //用户所拥有的项目的名称集合
    @Getter
    @Setter
    private String deptNames;  //用户所有的部门集合 
    
    @Getter
    @Setter
    private String weixin; //用户微信号
    
    @Getter
    @Setter
    private String weixinNickName;//微信昵称
    
    @Getter
    @Setter
    private String phone;//用户手机号
    
    @Getter
    @Setter
    private String mobilePhone;//用户手机号
    
    
    @Getter
    @Setter
    private String identifyingCode;//手机验证码
    
    @Getter
    @Setter
    private Integer mobileState;//手机绑定的状态
    
    @Getter
    @Setter
    private String namePY;  //姓名拼音

}
