package com.ssic.catering.admin.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

public class TImsUsersRoleDto implements Serializable {
    
     /**   
     * serialVersionUID: （一句话描述这个变量表示什么）      
     */   
    
    private static final long serialVersionUID = 1L;
    @Getter
    @Setter
	private String user_id;
    @Getter
    @Setter
    private String role_id;
    @Getter
    @Setter
    private Integer stat;
    @Getter
    @Setter
    private String id;
      
}
