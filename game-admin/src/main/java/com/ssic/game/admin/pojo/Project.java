package com.ssic.game.admin.pojo;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class Project implements Serializable{
	
	@Getter
	@Setter
	private String id;

	@Getter
	@Setter
	private String projName;
	
	@Getter
	@Setter
	private String describes;
	
	@Getter
	@Setter
	private int stat;
	
	@Getter
	@Setter
	private Date lastTime;
	
	@Getter
	@Setter
	private Date createTime;
}
