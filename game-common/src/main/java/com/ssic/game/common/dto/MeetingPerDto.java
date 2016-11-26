package com.ssic.game.common.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

public class MeetingPerDto implements Serializable{

	@Getter
	@Setter
	private Integer is_dept;
	@Getter
	@Setter
	private Integer is_all;
	@Getter
	@Setter
	private String message;
	@Getter
	@Setter
	private String errorCode;
	
}
