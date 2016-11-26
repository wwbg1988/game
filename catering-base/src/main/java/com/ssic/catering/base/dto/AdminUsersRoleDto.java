package com.ssic.catering.base.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 		
 * <p>Title: AdminUsersRoleDto </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author zhuzhen	
 * @date 2015年12月2日 下午4:44:49	
 * @version 1.0
 * <p>修改人：zhuzhen</p>
 * <p>修改时间：2015年12月2日 下午4:44:49</p>
 * <p>修改备注：</p>
 */
public class AdminUsersRoleDto implements Serializable
{

    
     /**   
     * serialVersionUID: （一句话描述这个变量表示什么）      
     */   
    
    private static final long serialVersionUID = -7572583716421298550L;
    
    /**
     * 主键
     */
    @Getter
    @Setter
    private String id;

    /**
     * t_ctr_admin_users
     */
    @Getter
    @Setter
    private String userId;

    /**
     * t_ctr_admin_role
     */
    @Getter
    @Setter
    private String roleId;

    
    @Getter
    @Setter
    private Integer stat;
}

