package com.ssic.game.common.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class QjyFriendDto implements  Serializable{

	@Getter
	@Setter
	private String id;
	@Getter
	@Setter
	private String userId;
	@Getter
	@Setter
	private String qjyAccount;
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
	private String friendUserId;
	
}
