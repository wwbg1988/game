package com.ssic.game.app.dao;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class JsonDto {
	@Getter
	@Setter
	private String name;
	
	@Getter
	@Setter
	private String value;
	
	@Getter
	@Setter
	private String hisvalue="2";
}

