package com.ssic.game.common.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class LTUserDto implements Serializable{

	@Getter
	@Setter
	private String userAccount;
	@Getter
	@Setter
	private String password;
	@Getter
	@Setter
	private String newPassword;
	@Getter
	@Setter
	private String from ;
	@Getter
	@Setter
	private String to;
	@Getter
	@Setter
	private String messageType;
	@Getter
	@Setter
	private String message;
	@Getter
	@Setter
	private List<String> tolist;
	
}
