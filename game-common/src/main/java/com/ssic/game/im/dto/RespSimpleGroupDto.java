package com.ssic.game.im.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

public class RespSimpleGroupDto implements Serializable{

	  /**
	 * 
	 */
	private static final long serialVersionUID = -4177278545088076039L;
	@Getter
	  @Setter
	  private Long groupId;
	  @Getter
	  @Setter
	  private String groupName;
	  @Getter
	  @Setter
	  private String groupHead;
	  @Getter
	  @Setter
	  private Byte needVerify;
	  @Getter
	  @Setter
	  private Byte isPrivate;

	
	
}
