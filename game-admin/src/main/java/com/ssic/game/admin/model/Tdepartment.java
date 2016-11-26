package com.ssic.game.admin.model;

import java.util.Date;


public class Tdepartment implements java.io.Serializable {
	
	private String id;
	//自增部门编号
	private Integer no;
	//所属赛事项目 多个|隔开
	private String pjNo;
	//部门名称
	private String deptName;
	//部门级别
	private Integer deptLevel;
	//部门管理员 默认为0
	private Integer deptAdmin;
    //创建时间
	private Date createTime;
	


	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public Integer getNo() {
		return no;
	}
	public void setNo(Integer no) {
		this.no = no;
	}
	
	public String getPjNo() {
		return pjNo;
	}
	public void setPjNo(String pjNo) {
		this.pjNo = pjNo;
	}
	
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	
	public Integer getDeptLevel() {
		return deptLevel;
	}
	public void setDeptLevel(Integer deptLevel) {
		this.deptLevel = deptLevel;
	}
	
	public Integer getDeptAdmin() {
		return deptAdmin;
	}
	public void setDeptAdmin(Integer deptAdmin) {
		this.deptAdmin = deptAdmin;
	}
	

	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
	
	
}
