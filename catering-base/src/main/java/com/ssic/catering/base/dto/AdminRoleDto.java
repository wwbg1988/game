package com.ssic.catering.base.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 		
 * <p>Title: CarteAdminRoleDto </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author zhuzhen	
 * @date 2015年11月25日 下午3:20:39	
 * @version 1.0
 * <p>修改人：zhuzhen</p>
 * <p>修改时间：2015年11月25日 下午3:20:39</p>
 * <p>修改备注：</p>
 */
@ToString
public class AdminRoleDto implements Serializable
{
    
     /**   
     * serialVersionUID: （一句话描述这个变量表示什么）      
     */   
    
    private static final long serialVersionUID = -5669000983117894335L;

    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private Date createTime;

    @Getter
    @Setter
    private String roleName;

    @Getter
    @Setter
    private Integer no;

    @Getter
    @Setter
    private String pjNo;

    @Getter
    @Setter
    private String postNo;

    @Getter
    @Setter
    private String remark;

    @Getter
    @Setter
    private Integer seq;

    @Getter
    @Setter
    private String pid;

    @Getter
    @Setter
    private Integer stat;
    
    /**
     * 项目id
     */
    @Getter
    @Setter
    private String projectId;
}

