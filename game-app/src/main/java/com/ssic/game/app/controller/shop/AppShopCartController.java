package com.ssic.game.app.controller.shop;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssic.catering.common.util.AppResponse;
import com.ssic.shop.manage.dto.ShoppingCartDto;
import com.ssic.shop.manage.service.IShoppingCartService;
import com.ssic.util.model.Response;

@Controller
@RequestMapping("/appShopCartController")
public class AppShopCartController {

	
	@Autowired
	private IShoppingCartService shoppingCartService;
 	
	/**
	 * 展示用户购物车的商品
	 * */
	
	
	@RequestMapping("/findCart.do")
	@ResponseBody
	public Response<List<ShoppingCartDto>> findCart(String openId){
		Response<List<ShoppingCartDto>> result = new Response<List<ShoppingCartDto>>();
	    if(openId==null || "".equals(openId)){
	    	result.setMessage("用户ID不能为空!");
	    	result.setStatus(AppResponse.RETURN_FAILE);
	    	return result;
	    }
		ShoppingCartDto shoppingCartDto = new ShoppingCartDto();
		shoppingCartDto.setOpenId(openId);
		List<ShoppingCartDto> list=   shoppingCartService.findBy(shoppingCartDto);
		if(list==null || list.size()==0){
			result.setMessage("该用户购物车商品为空!");
			result.setStatus(AppResponse.RETURN_SUCCESS);
			return result;
		}else{
			result.setMessage("购物车商品返回成功!");
			result.setData(list);
			result.setStatus(AppResponse.RETURN_SUCCESS);
			return result;
		}

	}
	
	
	/**
	 * 用户点击删除购物车商品
	 * **/
	
	@RequestMapping("/delCarte")
	@ResponseBody
	public Response<String> delCarte(String openId,String goodsId){
		Response<String> result = new Response<String>();
		ShoppingCartDto shoppingCartDto = new ShoppingCartDto();
		shoppingCartDto.setOpenId(openId);
		shoppingCartDto.setGoodsId(goodsId);
		List<ShoppingCartDto> list =  shoppingCartService.findBy(shoppingCartDto);
		if(list!=null && list.size()>0){
			for(ShoppingCartDto scDto:list){
				
			}
		}
		
		
		return result;
		
	}
	
	
	
}
