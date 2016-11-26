package com.ssic.shop.manage.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

public class SubmitDto implements Serializable{
	@Getter
	@Setter
	private String openId;
	
	@Getter
	@Setter
	private String name;
	
	@Getter
	@Setter
	private String phone;
	
	@Getter
	@Setter
	private String names;
	
	@Getter
	@Setter
	private String phones;
	
	@Getter
	@Setter
	private String floor;
	
	@Getter
	@Setter
	private String department;
	
	@Getter
	@Setter
	private String cafetoriumId;
}
