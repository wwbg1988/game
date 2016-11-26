/**
 * 
 */
package com.ssic.shop.manage.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**		
 * <p>Title: OrderListDto </p>
 * <p>Description: 我的订单列表</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author yuanbin	
 * @date 2015年8月27日 下午4:09:35	
 * @version 1.0
 * <p>修改人：yuanbin</p>
 * <p>修改时间：2015年8月27日 下午4:09:35</p>
 * <p>修改备注：</p>
 */
public class OrderListDto implements Serializable
{

    @Getter
    @Setter
    private String orderId;//订单编号

    @Getter
    @Setter
    private Integer orderStatus;//订单状态:1未支付，2：已支付；3：已完成(由食堂确认)

    @Getter
    @Setter
    private Double goodsAmount;//订单商品总价

    @Getter
    @Setter
    private int accountGoodsType;//包裹数量(所属商品类型)

    @Getter
    @Setter
    private int accountGoods;//订单中商品总数量

    @Getter
    @Setter
    private String orderNo;//订单编号

    @Getter
    @Setter
    private Integer payWay;

    @Getter
    @Setter
    private String createTime;//订单时间

    @Getter
    @Setter
    private OrderDetGoods orderDetGoods;//商品订单列表List

}
