package com.ssic.game.im.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class GetGroupsRespDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2124406769806444588L;
	@Getter
	@Setter
	 private List<RespSimpleGroupDto> list;
	  @Getter
	  @Setter
	  private String status;
	
	
}
