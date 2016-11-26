package com.ssic.shop.manage.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class ConsigneeDto implements Serializable{

	    @Getter
	    @Setter
	    private String id;

	    @Getter
	    @Setter
	    private String consigneeName;

	    @Getter
	    @Setter
	    private String consigneePhone;

	    @Getter
	    @Setter
	    private String consigneeFloor;

	    @Getter
	    @Setter
	    private String consigneeDept;

	    @Getter
	    @Setter
	    private Date createTime;

	    @Getter
	    @Setter
	    private Date lastUpdateTime;

	    @Getter
	    @Setter
	    private Integer stat;
	
	
}
