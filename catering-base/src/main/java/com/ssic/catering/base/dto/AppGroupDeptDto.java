package com.ssic.catering.base.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class AppGroupDeptDto implements Serializable{

	@Getter
	@Setter
	private String deptId;
	@Getter
	@Setter
	private String deptName;
	@Getter
	@Setter
	private String projId;
	@Getter
	@Setter
	private List<AppGroupDeptUserDto> deptuserlist;
	
}
