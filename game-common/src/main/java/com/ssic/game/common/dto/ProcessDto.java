package com.ssic.game.common.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class ProcessDto implements Serializable{

    private static final long serialVersionUID = 1600455749556671863L;
    
	@Getter
	@Setter
	private String id;
	@Getter
	@Setter
	private String projId;
	@Getter
	@Setter
	private String procName;
	@Getter
	@Setter
	private String describes;
	@Getter
	@Setter
	private Integer isdefine;
	@Getter
	@Setter
	private String startTask;
	@Getter
	@Setter
	private Integer stat;
	@Getter
	@Setter
	private Date startDate;
	@Getter
	@Setter
	private Date endDate;
	@Getter
	@Setter
	private Integer state;
	@Getter
	@Setter
	private Date lastUpdateTime;
	@Getter
	@Setter
	private Date createTime;
	
	@Getter
	@Setter
	private String imageUrl;
	
	@Getter
	@Setter
	private String projName;

	@Getter
	@Setter
	private String procUsers;
	
	@Getter
	@Setter
	private String userName;

	
}
