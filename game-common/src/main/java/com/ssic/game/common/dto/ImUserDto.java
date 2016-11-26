package com.ssic.game.common.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

public class ImUserDto implements Serializable{

	
	@Getter
	@Setter
	private String userId;
	@Getter
	@Setter
	private String imCount;
	@Getter
	@Setter
	private String userName;
	@Getter
	@Setter
	private String userImage;
}
