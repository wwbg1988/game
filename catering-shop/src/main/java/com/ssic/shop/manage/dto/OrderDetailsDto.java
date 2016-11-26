package com.ssic.shop.manage.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
/**		
 * <p>Title: OrderListDto </p>
 * <p>Description: 订单商品列表</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author yuanbin	
 * @date 2015年9月16日 下午2:09:35	
 * @version 1.0
 * <p>修改人：yuanbin</p>
 * <p>修改时间：2015年9月16日 下午2:09:35	</p>
 * <p>修改备注：</p>
 */
public class OrderDetailsDto implements Serializable{

	
	@Getter
	@Setter
    private String icon;//商品图片路径
	
	@Getter
	@Setter
	private String id;

	@Getter
	@Setter
	private String goodsId;
	
    @Getter
    @Setter
    private Double goodsPrice;//商品价格

	@Getter
	@Setter
	private String goodsName;

	@Getter
	@Setter
	private Integer goodsCount;//单个商品的数量

	@Getter
	@Setter
	private String goodsTypeId;//所属商品类型 :1(限时特购):食堂提货;2(精品推荐):送货上门

	@Getter
	@Setter
	private String orderId;

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
