package com.ssic.shop.manage.dto;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**		
 * <p>Title: OrderListDto </p>
 * <p>Description: 订单商品详情，分包裹返回参数</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author yuanbin	
 * @date 2015年9月16日 下午2:09:35	
 * @version 1.0
 * <p>修改人：yuanbin</p>
 * <p>修改时间：2015年9月16日 下午2:09:35	</p>
 * <p>修改备注：</p>
 */
public class OrderDetGoods implements Serializable{
	
	@Getter
	@Setter
    private List<OrderDetailsDto> orderDetailOneList;//包裹1
	
	@Getter
	@Setter
    private List<OrderDetailsDto> orderDetailTwoList;//包裹2
	
	@Getter
	@Setter
    private Integer payWay;//支付方式
	
	@Getter
	@Setter
    private String orderNo;//订单编号
	
	@Getter
	@Setter
    private String createTime;//订单创建时间
	
	@Getter
	@Setter
    private Double goodsAmount;//订单商品总价
	
	@Getter
	@Setter
    private int accountGoodsType;//包裹总数量(所属商品类型)
	
	@Getter
	@Setter
    private Double goodsIdAmountOne;//包裹1中商品总价
	
	@Getter
	@Setter
    private Double goodsIdAmountTwo;//包裹2中商品总价

	@Getter
	@Setter
    private int accountGoodsOne;//包裹1中商品总数量
	
	@Getter
	@Setter
    private int accountGoodsTwo;//包裹2中商品总数量
	
	@Getter
	@Setter
    private Integer shippingStatus;//包裹2商品配送状态: 0:未发货;1:已发货;2:已收货;3:备货中;
	
}
