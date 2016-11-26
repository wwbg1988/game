package com.ssic.catering.admin.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;





public class Tuser implements java.io.Serializable {

	private String id;
	//创建时间
	private Date createdatetime;
	//修改时间
	private Date modifydatetime;
	//员工姓名
	private String name;
	//密码
	private String pwd;
	//private short isDelete = 0; // 是否删除 0 未删除 1 删除
	private Set<Trole> troles = new HashSet<Trole>(0);
	//员工关联赛事流程ID 多个赛事流程用|隔开
	private String pjNo;
	//部门编号
	private Integer deptNo;
	//所属部门
	private Tdepartment department;
	//职务编号 多职务|隔开
	private String postNo;
	//员工工号 
	private String userNo;
    //登录账号
	private String userAccount;
	//亲加云帐号 默认qjy_user_account
	private String qjyAccount;
	//年龄
	private Integer age;
	//性别
	private int gender;
	//是否为超级管理员 0普通用户,1超级管理员,2部门管理员
	private Integer isAdmin;
	//是否删除 0 有效  1 无效
	private Integer isDelete = 0;
	//员工头像
	private String userImage;
	
	
	public Tuser() {
	}

	public Tuser(String id, String name, String pwd) {
		this.id = id;
		this.name = name;
		this.pwd = pwd;
	}

	public Tuser(String id, Date createdatetime, Date modifydatetime, String name, String pwd, Set<Trole> troles) {
		this.id = id;
		this.createdatetime = createdatetime;
		this.modifydatetime = modifydatetime;
		this.name = name;
		this.pwd = pwd;
		this.troles = troles;
	}


	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public Date getCreatedatetime() {
		return this.createdatetime;
	}

	public void setCreatedatetime(Date createdatetime) {
		this.createdatetime = createdatetime;
	}


	public Date getModifydatetime() {
		return this.modifydatetime;
	}

	public void setModifydatetime(Date modifydatetime) {
		this.modifydatetime = modifydatetime;
	}


	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public Set<Trole> getTroles() {
		return this.troles;
	}

	public void setTroles(Set<Trole> troles) {
		this.troles = troles;
	}
	
	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}


    public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}


	public String getPjNo() {
		return pjNo;
	}

	public void setPjNo(String pjNo) {
		this.pjNo = pjNo;
	}
	

	public Integer getDeptNo() {
		return deptNo;
	}

	public void setDeptNo(Integer deptNo) {
		this.deptNo = deptNo;
	}
	

	public Tdepartment getDepartment() {
		return department;
	}

	public void setDepartment(Tdepartment department) {
		this.department = department;
	}
	

	public String getPostNo() {
		return postNo;
	}

    public void setPostNo(String postNo) {
		this.postNo = postNo;
	}


	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}


	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}


	public String getQjyAccount() {
		return qjyAccount;
	}

	public void setQjyAccount(String qjyAccount) {
		this.qjyAccount = qjyAccount;
	}


	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}


	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}


	public Integer getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Integer isAdmin) {
		this.isAdmin = isAdmin;
	}


	public String getUserImage() {
		return userImage;
	}

	public void setUserImage(String userImage) {
		this.userImage = userImage;
	}
	
	
	
	
	

}
