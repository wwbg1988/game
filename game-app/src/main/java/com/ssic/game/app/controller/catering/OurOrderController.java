/**
 * 
 */
package com.ssic.game.app.controller.catering;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssic.catering.common.util.AppResponse;
import com.ssic.game.common.util.HttpDataResponse;
import com.ssic.game.common.util.SerialNumberUtils;
import com.ssic.shop.manage.dto.GoodsDto;
import com.ssic.shop.manage.dto.OrderDetGoods;
import com.ssic.shop.manage.dto.OrderDetailDto;
import com.ssic.shop.manage.dto.OrderDetailsDto;
import com.ssic.shop.manage.dto.OrderDto;
import com.ssic.shop.manage.dto.OrderListDto;
import com.ssic.shop.manage.service.IGoodsService;
import com.ssic.shop.manage.service.IOrderDetailService;
import com.ssic.shop.manage.service.IOrderService;
import com.ssic.util.BeanUtils;
import com.ssic.util.StringUtils;
import com.ssic.util.constants.DataStatus;
import com.ssic.util.model.Response;

/**		
 * <p>Title: OurOrderController </p>
 * <p>Description: 我的订单历史页面</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author yuanbin	
 * @date 2015年8月27日 下午4:05:39	
 * @version 1.0
 * <p>修改人：yuanbin</p>
 * <p>修改时间：2015年8月27日 下午4:05:39</p>
 * <p>修改备注：</p>
 */
@Controller
@RequestMapping("/ourOrder")
public class OurOrderController
{

    @Autowired
    private IOrderService iOrderService;

    @Autowired
    private IOrderDetailService iOrderDetailService;

    @Autowired
    private IGoodsService iGoodsService;

    /**         
     * ourOrder：我的订单历史
     * @param query
     * @return
     * @exception	
     * @author yuanbin
     * @date 2015年8月27日 下午1:52:34	  
     */
    @RequestMapping(value = "/ourOrder.do", method = {RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public Response<List<OrderListDto>> ourOrder(String openId, String cafetoriumId)
    {
        Response<List<OrderListDto>> result = new Response<>();
        
        if(StringUtils.isEmpty(openId))
        {
            result.setStatus(HttpDataResponse.HTTP_FAILE);
            result.setMessage("微信id不能为空");
            return result;
        }
        
        if(StringUtils.isEmpty(cafetoriumId))
        {
            result.setStatus(HttpDataResponse.HTTP_FAILE);
            result.setMessage("食堂id不能为空");
            return result;
        }
        
        List<OrderListDto> orderListDto = new ArrayList<OrderListDto>();
        

        //查询订单List
        OrderDto orderDto = new OrderDto();
        orderDto.setStat(DataStatus.ENABLED);
        orderDto.setOpenId(openId);
        orderDto.setCafetoriumId(cafetoriumId);
        List<OrderDto> orderList = iOrderService.findBy(orderDto);
        if (orderList != null & orderList.size() > 0)
        {
            for (OrderDto order : orderList)
            {
                OrderListDto orderDtos = new OrderListDto();//单个订单
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date crTime = order.getCreateTime();
                String create_time = format.format(crTime);
                orderDtos.setCreateTime(create_time); //订单创建时间
                orderDtos.setOrderStatus(order.getOrderStatus());//订单状态:1未支付，2：已支付；3：已完成(由食堂确认)
                orderDtos.setGoodsAmount(order.getGoodsAmount());//订单总金额
                orderDtos.setAccountGoodsType(accountGoodsType(order.getId()));//包裹数量
                orderDtos.setAccountGoods(accountGoods(order.getId()));//订单中商品总数量
                orderDtos.setPayWay(order.getPayWay());//支付方式
                String orderId = order.getId();
                String orderNo = order.getOrderNo();
                if (orderId != null)
                {
                    orderDtos.setOrderId(orderId);
                    orderDtos.setOrderNo(orderNo);
                    orderDtos.setOrderDetGoods(getOrderDetailList(orderId));
                }
                orderListDto.add(orderDtos);
            }
        }
        else
        {
            result.setStatus(AppResponse.RETURN_SUCCESS);
            result.setMessage("订单为空");
            return result;
        }
        result.setStatus(AppResponse.RETURN_SUCCESS);
        result.setMessage("返回成功!");
        result.setData(orderListDto);
        return result;

    }

    //订单中商品总数量
    private int accountGoods(String orderId)
    {
        OrderDetailDto orderDetailDto = new OrderDetailDto();
        orderDetailDto.setStat(DataStatus.ENABLED);
        orderDetailDto.setOrderId(orderId);
        int account = 0;
        List<OrderDetailDto> orderDetailDtoList = iOrderDetailService.findBy(orderDetailDto);
        for (OrderDetailDto goods : orderDetailDtoList)
        {
            account = goods.getGoodsCount().intValue() + account;
        }
        return account;
    }

    //包裹数量
    private int accountGoodsType(String orderId)
    {
        OrderDetailDto orderDetailDto = new OrderDetailDto();
        orderDetailDto.setStat(DataStatus.ENABLED);
        orderDetailDto.setOrderId(orderId);
        int account = 0;
        int account1 = 0;
        int account2 = 0;
        List<OrderDetailDto> orderDetailDtoList = iOrderDetailService.findBy(orderDetailDto);
        for (OrderDetailDto goods : orderDetailDtoList)
        {
            String goodId = goods.getGoodsTypeId();
            if (goodId.equals("1"))
            {
                account1 = 1;
            }
            if (goodId.equals("2"))
            {
                account2 = 2;
            }
        }
        if ((String.valueOf(account1 + account2)).equals("3"))
        {
            account = 2;
        }
        else
        {
            account = 1;
        }
        return account;
    }

    /**     
     * getOrderDetailList：得到订单中商品详情列表
     * @param string 
     * @return
     * @exception	
     * @author yuanbin
     * @date 2015年9月15日 下午5:57:53	 
     */
    @RequestMapping(value = "/ourOrderDetail.do", method = {RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public OrderDetGoods getOrderDetailList(String orderId)
    {
        OrderDetGoods result = new OrderDetGoods();

        OrderDetailDto orderDetailDto = new OrderDetailDto();
        orderDetailDto.setStat(DataStatus.ENABLED);
        orderDetailDto.setOrderId(orderId);

        List<OrderDetailsDto> orderDetailsDtoList = new ArrayList<OrderDetailsDto>();
        List<OrderDetailDto> orderDetailDtoList = iOrderDetailService.findBy(orderDetailDto);
        for (OrderDetailDto goods : orderDetailDtoList)
        {
            OrderDetailsDto orderDetailsDto = new OrderDetailsDto();//订单中商品对象
            BeanUtils.copyProperties(goods, orderDetailsDto);
            if (goods.getGoodsId() != null)
            {
                GoodsDto goodDto = iGoodsService.findGoodsById(goods.getGoodsId());
                if (goodDto != null)
                {
                    orderDetailsDto.setIcon(goodDto.getIcon());//获取图片路径
                }
            }
            orderDetailsDtoList.add(orderDetailsDto);
        }

        OrderDto order = iOrderService.findById(orderId);

        result.setGoodsAmount(order.getGoodsAmount());//订单总金额
        result.setOrderNo(order.getOrderNo());//订单编号
        result.setShippingStatus(order.getShippingStatus());//包裹2的配送状态
        result.setAccountGoodsType(accountGoodsType(orderId));//包裹总数量(所属商品类型)
        result.setShippingStatus(order.getShippingStatus());//包裹2商品配送状态:0:未发货;1:已发货;2:已收货;3:备货中;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date crTime = order.getCreateTime();
        String create_time = format.format(crTime);
        result.setCreateTime(create_time);//订单时间
        result.setPayWay(order.getPayWay());//支付方式

        Double goodsIdAmountOne = 0.0;//包裹1中商品总价
        Double goodsIdAmountTwo = 0.0;//包裹2中商品总价
        int accountGoodsOne = 0;//包裹1中商品总数量
        int accountGoodsTwo = 0;//包裹2中商品总数量
        List<OrderDetailsDto> orderDetailOneList = new ArrayList<OrderDetailsDto>();
        List<OrderDetailsDto> orderDetailTwoList = new ArrayList<OrderDetailsDto>();
        for (OrderDetailsDto orderDetailsByGoodId : orderDetailsDtoList)
        {//遍历订单详情对象
            String goodId = orderDetailsByGoodId.getGoodsTypeId();
            if (goodId.equals("1"))
            {
                accountGoodsOne += orderDetailsByGoodId.getGoodsCount();
                goodsIdAmountOne =
                    (orderDetailsByGoodId.getGoodsPrice() * accountGoodsOne) + goodsIdAmountOne;
                OrderDetailsDto orderDetailOne = new OrderDetailsDto();
                BeanUtils.copyProperties(orderDetailsByGoodId, orderDetailOne);
                orderDetailOneList.add(orderDetailOne);
            }
            if (goodId.equals("2"))
            {
                accountGoodsTwo += orderDetailsByGoodId.getGoodsCount();
                goodsIdAmountTwo =
                    (orderDetailsByGoodId.getGoodsPrice() * accountGoodsTwo) + goodsIdAmountTwo;
                OrderDetailsDto orderDetailTwo = new OrderDetailsDto();
                BeanUtils.copyProperties(orderDetailsByGoodId, orderDetailTwo);
                orderDetailTwoList.add(orderDetailTwo);
            }
        }
        result.setGoodsIdAmountOne(goodsIdAmountOne);
        result.setGoodsIdAmountTwo(goodsIdAmountTwo);
        result.setAccountGoodsOne(accountGoodsOne);
        result.setAccountGoodsTwo(accountGoodsTwo);

        result.setOrderDetailOneList(orderDetailOneList);
        result.setOrderDetailTwoList(orderDetailTwoList);

        return result;
    }

    /**     
    * cancelPay：取消支付
    * @param order 订单对象
    * @return
    * @exception   
    * @author 刘博
    * @date 2015年6月25日 下午3:47:41    
    */
    @RequestMapping("/cancelPay.do")
    @ResponseBody
    public Response<OrderDto> cancelPay(String openId, String orderId, String isCancelPay)
    {
        Response<OrderDto> result = new Response<OrderDto>();

        if (StringUtils.isEmpty(openId) || StringUtils.isEmpty(orderId))
        {
            result.setStatus(HttpDataResponse.HTTP_FAILE);
            result.setMessage("微信标识或订单为空");
            return result;
        }
        if (StringUtils.isEmpty(isCancelPay))
        {
            result.setStatus(HttpDataResponse.HTTP_FAILE);
            result.setMessage("取消支付操作异常");
            return result;
        }

        //根据订单id查找订单对象
        OrderDto dest = iOrderService.findById(orderId);
        if (dest != null)
        {

            if (openId.equals(dest.getOpenId()))
            {
                //取消付款
                if (isCancelPay.equals("1"))
                { //生成新的订单编号
                    String order_no = SerialNumberUtils.getOrderNo();
                    //放入新的订单编号,更新到数据库
                    dest.setOrderNo(order_no);
                    //更新订单状态为未支付
                    dest.setOrderStatus(1);
                    dest.setLastUpdateTime(new Date());
                    iOrderService.upateOrder(dest);
                    //放入订单对象
                    result.setData(dest);
                    result.setStatus(HttpDataResponse.HTTP_SUCCESS);
                    return result;
                }

            }
            result.setStatus(HttpDataResponse.HTTP_FAILE);
            result.setMessage("订单所属人不匹配");
            return result;
        }

        result.setStatus(HttpDataResponse.HTTP_FAILE);
        result.setMessage("未找到该订单");
        return result;
    }

    @RequestMapping("/payDelivery.do")
    @ResponseBody
    public Response<String> payDelivery(OrderDto order)
    {
        Response<String> result = new Response<>();

        if (StringUtils.isEmpty(order.getOpenId()) || StringUtils.isEmpty(order.getOrderNo()))
        {
            result.setStatus(HttpDataResponse.HTTP_FAILE);
            result.setMessage("微信标识或订单编号为空");
            return result;
        }

        OrderDto dest = iOrderService.findByOrderNo(order.getOrderNo());
        if (dest != null)
        {

            if (order.getOpenId().equals(dest.getOpenId()))
            {
                if (dest.getPayWay() == null)
                {
                    //新纪录
                    OrderDto newOne = new OrderDto();

                    newOne.setId(dest.getId());
                    newOne.setPayWay(order.getPayWay());
                    newOne.setLastUpdateTime(new Date());
                    //                  newOne.setOrderStatus(2);
                    iOrderService.upateOrder(newOne);

                    result.setStatus(HttpDataResponse.HTTP_SUCCESS);
                    return result;
                }

                result.setStatus(HttpDataResponse.HTTP_FAILE);
                result.setMessage("订单已处理");
                return result;
            }

            result.setStatus(HttpDataResponse.HTTP_FAILE);
            result.setMessage("订单所属人不匹配");
            return result;
        }

        result.setStatus(HttpDataResponse.HTTP_FAILE);
        result.setMessage("未找到该订单");
        return result;
    }

}
