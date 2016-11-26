package com.ssic.catering.base.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

public class AppGroupDeptUserDto implements Serializable{

	@Getter
	@Setter
	private String userId;
	@Getter
	@Setter
	private String userName;
	@Getter
	@Setter
	private String deptId;
	@Getter
	@Setter
	private String deptName;
	
}
