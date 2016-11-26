package com.ssic.game.admin.pojo;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class DeptLevel implements Serializable{
	@Getter
	@Setter
	private String id;

	@Getter
	@Setter
	private int level;
	
	@Getter
	@Setter
	private String levelName;
	
	@Getter
	@Setter
	private Date createTime;
	
	@Getter
	@Setter
	private Date lastTime;

    @Getter
	@Setter
	private int stat;
	

}
