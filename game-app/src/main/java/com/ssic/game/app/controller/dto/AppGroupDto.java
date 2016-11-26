package com.ssic.game.app.controller.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class AppGroupDto implements Serializable{
	  @Getter
      @Setter
	  private String groupId;
      @Getter
      @Setter
	  private String groupName;
      @Getter
      @Setter
	  private String groupInfo;
      @Getter
      @Setter
	  private String groupHead;
      @Getter
      @Setter
      private String parentId;
      @Getter
      @Setter
      private List<AppGroupDto> list;
      
}
