package com.ssic.game.app.controller.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

public class AppGroupUserDto implements Serializable{

	@Getter
	@Setter 
	private String groupId;


	@Getter
	@Setter
	private String groupName;
}
