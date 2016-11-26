/**
 * 
 */
package com.ssic.game.app.controller.catering;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssic.catering.common.util.AppResponse;
import com.ssic.game.common.util.SerialNumberUtils;
import com.ssic.shop.manage.dto.ConsigneeDto;
import com.ssic.shop.manage.dto.GoodsDto;
import com.ssic.shop.manage.dto.OrderConsigneeDto;
import com.ssic.shop.manage.dto.OrderDetailDto;
import com.ssic.shop.manage.dto.OrderDto;
import com.ssic.shop.manage.dto.OrderListDto;
import com.ssic.shop.manage.dto.ShoppingCartDto;
import com.ssic.shop.manage.dto.SubmitDto;
import com.ssic.shop.manage.service.IConsigneeService;
import com.ssic.shop.manage.service.IGoodsService;
import com.ssic.shop.manage.service.IOrderConsigneeService;
import com.ssic.shop.manage.service.IOrderDetailService;
import com.ssic.shop.manage.service.IOrderService;
import com.ssic.shop.manage.service.IShoppingCartService;
import com.ssic.util.UUIDGenerator;
import com.ssic.util.constants.DataStatus;
import com.ssic.util.constants.HttpDataResponse;
import com.ssic.util.model.Response;

/**		
 * <p>Title: SubmitOrderController </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author yuanbin	
 * @date 2015年8月28日 上午9:05:58	
 * @version 1.0
 * <p>修改人：yuanbin</p>
 * <p>修改时间：2015年8月28日 上午9:05:58</p>
 * <p>修改备注：</p>
 */
@Controller
@RequestMapping("/submitOrder")
public class SubmitOrderController {
	
	@Autowired
	private IShoppingCartService iShoppingCartService;
	
	@Autowired
	private IGoodsService iGoodsService;
	
	@Autowired
	private IOrderService iOrderService;
	
	@Autowired
	private IOrderDetailService iOrderDetailService;
	
	@Autowired
	private IConsigneeService iConsigneeService;
	
	@Autowired
	private IOrderConsigneeService iOrderConsigneeService;
	
	/**         
	 * submitOrder：提交订单
	 * @param carterUserId 用户Id
	 * @param shoppingId   购物车ID
	 * @return
	 * @exception	
	 * @author yuanbin
	 * @date 2015年8月28日 下午1:52:34	  
	 */	
	@RequestMapping(value = "/submitOrder.do", method = {RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Response<OrderDto> submitOrder(SubmitDto submitDto){
		Response<OrderDto> result = new Response<>();
        String openId=submitDto.getOpenId();
		//从购物车中获取产品信息
		ShoppingCartDto shoppingCartDto=new ShoppingCartDto();
		shoppingCartDto.setOpenId(openId);
		shoppingCartDto.setStat(DataStatus.ENABLED);
        /**
		int ad=0;  int ab=0;
		//得到该用户购物车的商品列表
		List<ShoppingCartDto> ShoppingCartList=iShoppingCartService.findByCarterUserId(shoppingCartDto);
		for(ShoppingCartDto shoppingCart:ShoppingCartList){
             
			//判断商品库存
			GoodsDto goodsDto=iGoodsService.findGoodsById(shoppingCart.getGoodsId());
			Integer goodLimit=goodsDto.getCountLimit();//商品最大购买数量
			Integer goodsStocks=goodsDto.getGoodsStocks();//商品库存量
			Integer goodsCount=shoppingCart.getCount();//购物车中该产品数量
			if(goodsCount<goodsStocks & goodsCount<goodLimit){
				ad=0;
			}else{
				ab=1;
			}
		}
		if(0==ad+ab){
		**/
			//生成订单号，创建新的订单内容和订单详情
			OrderDto orderDto=new OrderDto();
			orderDto.setId(UUIDGenerator.getUUID());
			orderDto.setStat(DataStatus.ENABLED);
			orderDto.setOrderNo(SerialNumberUtils.getOrderNo());//自动生成订单号
			orderDto.setPayStatus(0);  
			orderDto.setOrderStatus(1);
			orderDto.setOpenId(openId);
			orderDto.setCreateTime(new Date());
			orderDto.setGoodsAmount(totalAmount(openId));//得到商品总的金额
			orderDto.setCafetoriumId(submitDto.getCafetoriumId());
			iOrderService.insertOrder(orderDto);

			//得到该用户购物车的商品列表
			List<ShoppingCartDto> ShoppingCartsList=iShoppingCartService.findByCarterUserId(shoppingCartDto);
			for(ShoppingCartDto shoppingCart:ShoppingCartsList){
                /**
				//更新商品库存
				GoodsDto goodsDto=iGoodsService.findGoodsById(shoppingCart.getGoodsId());
				Integer goodsStocks=goodsDto.getGoodsStocks();//商品库存量
				Integer goodsCount=shoppingCart.getCount();//购物车中该产品数量
				goodsDto.setGoodsStocks(goodsStocks-goodsCount);
				iGoodsService.update(goodsDto);
                **/
                
				//添加订单详情    
				OrderDetailDto orderDetailDto=new OrderDetailDto();
				orderDetailDto.setGoodsId(shoppingCart.getGoodsId());
				orderDetailDto.setGoodsName(shoppingCart.getGoodsName());
				orderDetailDto.setGoodsCount(shoppingCart.getCount());
				orderDetailDto.setGoodsTypeId(shoppingCart.getGoodsTypeId());
				GoodsDto goodsDto=iGoodsService.findGoodsById(shoppingCart.getGoodsId());
				orderDetailDto.setGoodsPrice(goodsDto.getPrice());//设置商品价格
				orderDetailDto.setStat(DataStatus.ENABLED);
				orderDetailDto.setCreateTime(new Date());//new Date()为获取当前系统时间
				orderDetailDto.setId(UUIDGenerator.getUUID());
				orderDetailDto.setOrderId(orderDto.getId());//订单号
				iOrderDetailService.insertOrderDetail(orderDetailDto);
			}
			//清空购物车
			iShoppingCartService.delect(shoppingCartDto.getOpenId());
            
			String name=submitDto.getName(); 
			String phone=submitDto.getPhone(); 
			if(!StringUtils.isEmpty(name) && !StringUtils.isEmpty(phone)){
				//根据包裹添加收货人信息和收货人信息订单关系表----包裹一：自取(手机号和姓名)
				ConsigneeDto consigneeDtos=new ConsigneeDto();
				consigneeDtos.setConsigneeName(submitDto.getName());
				consigneeDtos.setStat(DataStatus.ENABLED);
				consigneeDtos.setLastUpdateTime(new Date());
				consigneeDtos.setId(UUIDGenerator.getUUID());
				consigneeDtos.setCreateTime(new Date());
				consigneeDtos.setConsigneePhone(submitDto.getPhone());
				iConsigneeService.insertConsignee(consigneeDtos);
				//收货订单关系表
				OrderConsigneeDto orderConsigneeDto=new OrderConsigneeDto();
				orderConsigneeDto.setId(UUIDGenerator.getUUID());
				orderConsigneeDto.setCreateTime(new Date());
				orderConsigneeDto.setLastUpdateTime(new Date());
				orderConsigneeDto.setGoodsTypeId("1");
				orderConsigneeDto.setOrderId(orderDto.getId());
				orderConsigneeDto.setConsigneeId(consigneeDtos.getId());
				orderConsigneeDto.setStat(DataStatus.ENABLED);	
				iOrderConsigneeService.inserOrderConsignee(orderConsigneeDto);//收货订单关系表
			}
		        String names=submitDto.getNames();   
		        String phones=submitDto.getPhones();  
			if(!StringUtils.isEmpty(names) && !StringUtils.isEmpty(phones)){
				//根据包裹添加收货人信息和收货人信息订单关系表----包裹二：发货(具体地址)		
				ConsigneeDto consigneeDtoTwos=new ConsigneeDto();
				consigneeDtoTwos.setConsigneeDept(submitDto.getDepartment());
				consigneeDtoTwos.setConsigneeFloor(submitDto.getFloor());
				consigneeDtoTwos.setConsigneeName(submitDto.getNames());
				consigneeDtoTwos.setStat(DataStatus.ENABLED);
				consigneeDtoTwos.setLastUpdateTime(new Date());
				consigneeDtoTwos.setId(UUIDGenerator.getUUID());
				consigneeDtoTwos.setCreateTime(new Date());
				consigneeDtoTwos.setConsigneePhone(submitDto.getPhones());
				iConsigneeService.insertConsignee(consigneeDtoTwos);		 
				//收货订单关系表
				OrderConsigneeDto orderConsigneesDto=new OrderConsigneeDto();
				orderConsigneesDto.setId(UUIDGenerator.getUUID());
				orderConsigneesDto.setCreateTime(new Date());
				orderConsigneesDto.setLastUpdateTime(new Date());
				orderConsigneesDto.setGoodsTypeId("2");
				orderConsigneesDto.setOrderId(orderDto.getId());
				orderConsigneesDto.setConsigneeId(consigneeDtoTwos.getId());
				orderConsigneesDto.setStat(DataStatus.ENABLED);	
				iOrderConsigneeService.inserOrderConsignee(orderConsigneesDto);//收货订单关系表
			}

			//保存成功
			result.setData(orderDto);
			result.setStatus(HttpDataResponse.HTTP_SUCCESS);
			result.setMessage("提交成功");
			return result;

	}

	
	/**     
	 * totalAmount：计算订单商品总金额
	 * @return
	 * @exception	
	 * @author yuanbin
	 * @date 2015年8月28日 下午5:05:49	 
	 */
	private Double totalAmount(String openId) {

		Double totalAmount=(double) 0;//订单商品总金额
		ShoppingCartDto shoppingCartDto=new ShoppingCartDto();
		shoppingCartDto.setOpenId(openId);
		shoppingCartDto.setStat(DataStatus.ENABLED);
		List<ShoppingCartDto> ShoppingCartList=iShoppingCartService.findByCarterUserId(shoppingCartDto);//得到该用户购物车的商品列表
		for(ShoppingCartDto shoppingCart:ShoppingCartList){
			GoodsDto goodsDto=iGoodsService.findGoodsById(shoppingCart.getGoodsId());
			Double price=goodsDto.getPrice();//得到商品价格
			Double goodsCount=Double.parseDouble(String.valueOf(shoppingCart.getCount()));//得到此商品订单数量
			Double amount=(price*goodsCount);
			totalAmount=amount+totalAmount;
		}	
		return totalAmount;
	}
	

}

