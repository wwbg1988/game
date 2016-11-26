package com.ssic.shop.manage.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class OrderConsigneeDto implements Serializable{

	    @Getter
	    @Setter
	    private String id;

	    @Getter
	    @Setter
	    private String orderId;

	    @Getter
	    @Setter
	    private String consigneeId;

	    @Getter
	    @Setter
	    private String goodsTypeId;

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
