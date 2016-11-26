package com.ssic.catering.admin.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class TImsRoleDto implements Serializable{
   
     
     /**   
     * serialVersionUID: （一句话描述这个变量表示什么）      
     */   
    
    private static final long serialVersionUID = 1L;
    @Getter
     @Setter
	 private String id;
     @Getter
     @Setter
     private Date create_time;
     @Getter
     @Setter
     private String role_name;
     @Getter
     @Setter
     private Integer no;
     @Getter
     @Setter
     private String pj_no;
     @Getter
     @Setter
     private String post_no;
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
     
}
