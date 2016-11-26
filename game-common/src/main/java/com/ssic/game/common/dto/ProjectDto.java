package com.ssic.game.common.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class ProjectDto implements Serializable{

	 /**   
     * serialVersionUID: （一句话描述这个变量表示什么）      
     */   
    
    private static final long serialVersionUID = 1L;

    @Getter
	@Setter
	private String id;
	
    //项目名称
	@Getter
	@Setter
	private String projName;
	
	//项目描述
	@Getter
	@Setter
	private String describes;
	
	@Getter
	@Setter
	private Boolean stat;
	
	@Getter
	@Setter
	private Date lastUpdateTime;
	
	@Getter
	@Setter
	private Date createTime;
	
	@Getter
	@Setter
	private String UserNames;
	
}
