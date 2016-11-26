package com.ssic.game.app.controller.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

public class QJYSendFriDto implements Serializable{

	@Getter
	@Setter
	private String userId;
	@Getter
	@Setter
	private String qjyAccount;
	@Getter
	@Setter
	private String friendUserId;
	@Getter
	@Setter
	private String userName;
	@Getter
	@Setter
	private String friendUserName;
	
	
}
