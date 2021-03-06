package com.ssic.catering.admin.pojo;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class MenuAndRoles implements Serializable{
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
	private Integer age;
	@Getter
	@Setter
	private Date createdatetime;
	@Getter
	@Setter
	private String  gender;
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
	private String password;
	@Getter
	@Setter
	private String qjy_account;
	@Getter
	@Setter
	private String user_account;
	@Getter
	@Setter
	private String user_image;
	@Getter
	@Setter
	private String user_no;
	@Getter
	@Setter
	private String role_id;
	@Getter
	@Setter
	private String user_id;
	@Getter
	@Setter
	private String r_role_id;
	@Getter
	@Setter
	private String r_menu_id;
	@Getter
	@Setter
	private String menu_id;
	@Getter
	@Setter
	private Date menu_create_time;
	@Getter
	@Setter
	private String menu_icon;
	@Getter
	@Setter
	private Integer menu_isright;
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
}
