package com.ssic.catering.admin.pojo;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class Users implements Serializable{
	
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
	private Integer stat;
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
	private String email;

}
