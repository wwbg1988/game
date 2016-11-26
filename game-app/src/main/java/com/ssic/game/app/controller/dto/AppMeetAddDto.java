package com.ssic.game.app.controller.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class AppMeetAddDto implements Serializable{
    @Getter
    @Setter
	private String addressID;
	@Getter
	@Setter
	private String addressName;
	@Getter
	@Setter
	private List<AppMeetAddDto> list;
	
	
}
