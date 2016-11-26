package com.ssic.catering.admin.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class RoleMenuDto implements  Serializable{
	
	
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
	private Date menu_create_time;
	@Getter
	@Setter
	private String menu_icon;
	@Getter
	@Setter
	private Integer menu_isRight;
	@Getter
	@Setter
	private String menu_name;
	@Getter
	@Setter
	private Integer menu_no;
	@Getter
	@Setter
	private String menu_pj_no;
	@Getter
	@Setter
	private String menu_remark;
	@Getter
	@Setter
	private Integer menu_seq;
	@Getter
	@Setter
	private String menu_pid;
	@Getter
	@Setter
	private String menu_menu_type_id;
	@Getter
	@Setter
	private String menu_url;
	@Getter
	@Setter
	private String menu_id;
	@Getter
	@Setter
	private String r_m_role_id;
	@Getter
	@Setter
	private String r_m_menu_id;
	
	

}
