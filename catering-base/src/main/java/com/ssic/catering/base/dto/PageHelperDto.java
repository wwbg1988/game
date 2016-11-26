package com.ssic.catering.base.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

public class PageHelperDto implements Serializable{

	
	@Getter
	@Setter
	private int page;// 当前页
	@Getter
	@Setter
	private int rows;// 每页显示记录数
	@Getter
	@Setter
	private String sort;// 排序字段
	@Getter
	@Setter
	private String order;// asc/desc
	@Getter
	@Setter
	private Integer beginRow;
}
