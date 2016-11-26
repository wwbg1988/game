package com.ssic.catering.admin.pojo;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;


public class Menu implements Serializable {

	@Getter
    @Setter
	private String id;
	@Getter
    @Setter
	private Date createTime;
	@Getter
    @Setter
	private String icon;
	@Getter
	@Setter
	private Integer isRight;
	@Getter
    @Setter
	private String name;
	@Getter
    @Setter
	private Integer no;
	@Getter
	@Setter
	private String pjNo;
	@Getter
    @Setter
	private String remark;
	@Getter
    @Setter
	private Integer seq;
	@Getter
    @Setter
	private String url;
	@Getter
	@Setter
	private String pid;
	@Getter
	@Setter
	private String menuTypeId;
	@Getter
	@Setter
	private Integer stat;
	@Getter
    @Setter
    private Integer tabType;
 
   
}
