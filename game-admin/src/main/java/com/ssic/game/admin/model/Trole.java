package com.ssic.game.admin.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;




public class Trole implements java.io.Serializable {

	private String id;
	private Integer no;
	private String pjNo;
	private String post_no;
	private Trole trole;
	private String name;
	private Date createTime;
	private String remark;
	private Integer seq;
	private Set<Trole> troles = new HashSet<Trole>(0);
	private Set<Tresource> tresources = new HashSet<Tresource>(0);
	private Set<Tuser> tusers = new HashSet<Tuser>(0);

	public Trole() {
	}

	public Trole(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public Trole(String id, Trole trole, String name, String remark, Integer seq, Set<Trole> troles, Set<Tresource> tresources, Set<Tuser> tusers) {
		this.id = id;
		this.trole = trole;
		this.name = name;
		this.remark = remark;
		this.seq = seq;
		this.troles = troles;
		this.tresources = tresources;
		this.tusers = tusers;
	}


	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public Trole getTrole() {
		return this.trole;
	}

	public void setTrole(Trole trole) {
		this.trole = trole;
	}


    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getRemark() {
		return this.remark;
	}

	


	public void setRemark(String remark) {
		this.remark = remark;
	}


	public Integer getSeq() {
		return this.seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
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
	


	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	

	public String getPost_no() {
		return post_no;
	}

	public void setPost_no(String post_no) {
		this.post_no = post_no;
	}


	public Set<Trole> getTroles() {
		return this.troles;
	}

	public void setTroles(Set<Trole> troles) {
		this.troles = troles;
	}

    public Set<Tresource> getTresources() {
		return this.tresources;
	}

	public void setTresources(Set<Tresource> tresources) {
		this.tresources = tresources;
	}

    public Set<Tuser> getTusers() {
		return this.tusers;
	}

	public void setTusers(Set<Tuser> tusers) {
		this.tusers = tusers;
	}

}
