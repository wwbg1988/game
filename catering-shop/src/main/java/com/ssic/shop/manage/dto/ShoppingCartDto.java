package com.ssic.shop.manage.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class ShoppingCartDto implements Serializable{

	    @Getter
	    @Setter
	    private String id;

	    @Getter
	    @Setter
	    private String goodsName;

	    @Getter
	    @Setter
	    private String goodsId;

	    @Getter
	    @Setter
	    private Integer count;

	    @Getter
	    @Setter
	    private String icon;

	    @Getter
	    @Setter
	    private String goodsTypeId;   // 1 包裹1  食堂提货     2  包裹2  送货上门

	    @Getter
	    @Setter
	    private String openId;

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
	    private Double price;

	
}
