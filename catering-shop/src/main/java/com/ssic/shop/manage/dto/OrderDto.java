package com.ssic.shop.manage.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class OrderDto implements Serializable{
	@Getter
	@Setter
	private String id;
	@Getter
	@Setter
    private String orderNo;
	@Getter
	@Setter
    private Integer orderStatus;
	@Getter
	@Setter
    private Integer shippingStatus;
	@Getter
	@Setter
    private Integer payWay;
	@Getter
	@Setter
    private Integer payStatus;
	@Getter
	@Setter
    private String message;
	@Getter
	@Setter
    private Double goodsAmount;
	@Getter
	@Setter
    private String openId;
	@Getter
	@Setter
    private Date createTime;
	@Getter
	@Setter
    private String cafetoriumId;
	@Getter
	@Setter
    private Date payTime;
	@Getter
	@Setter
    private Date lastUpdateTime;
	@Getter
	@Setter
    private Integer stat;
	//如果用户点取消支付，需要重新生成订单编号;
	@Getter
    @Setter
    private Integer isCancelPay;
}
