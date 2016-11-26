package com.ssic.game.common.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class TmsRoleDto implements Serializable{
    
	
	  @Getter
      @Setter
	  private String id;
      @Getter
      @Setter
	  private String name;
      @Getter
      @Setter
	  private String projId;
      @Getter
      @Setter
	  private String describes;
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
      private String projName;
      @Getter
      @Setter
      private String userIds;
      @Getter
      @Setter
      private String userNames;
}
