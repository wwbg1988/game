package com.ssic.catering.admin.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class DeptLevelDto implements Serializable{
	
	
     /**   
     * serialVersionUID: （一句话描述这个变量表示什么）      
     */   
    
    private static final long serialVersionUID = 1L;

    @Getter
	@Setter
	private String id;
	
	@Getter
	@Setter
	private Integer level;
	
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
	private Integer  stat;
	
}
