package com.ssic.game.common.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class ProcessUsersDto implements Serializable{

	  @Getter
	  @Setter
	  private String id;
	  @Getter
	  @Setter
	  private String procId;
	  @Getter
	  @Setter
	  private String userId;
	  @Getter
	  @Setter
	  private Integer stat;
	  @Getter
	  @Setter
	  private Date lastUpdateTime;
	  @Getter
	  @Setter
	  private Date createTime;
	  @Getter
	  @Setter
	  private String userName;
	
}
