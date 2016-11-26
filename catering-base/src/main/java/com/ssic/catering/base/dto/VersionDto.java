package com.ssic.catering.base.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class VersionDto implements Serializable{

	    @Getter
	    @Setter
	    private String id;
	    @Getter
	    @Setter
	    private String versionname;
	    @Getter
	    @Setter
	    private Integer versionnum;
	    @Getter
	    @Setter
	    private Integer versiontype;
	    @Getter
	    @Setter
	    private String versionurl;
	    @Getter
	    @Setter
	    private Date createTime;
	    @Getter
	    @Setter
	    private Date lastUpdateTime;
	    @Getter
	    @Setter
	    private Integer stat;
	    @Getter
	    @Setter
	    private String remark;
	
	
}
