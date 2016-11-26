package com.ssic.game.app.controller.shop;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssic.catering.common.util.AppResponse;
import com.ssic.shop.manage.dto.ShoppingCartDto;
import com.ssic.shop.manage.service.IShoppingCartService;
import com.ssic.util.model.Response;

/**
 *      
 * <p>Title: ShoppingCartController </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author pengcheng.yang   
 * @date 2015年9月16日 下午5:20:50   
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年9月16日 下午5:20:50</p>
 * <p>修改备注：</p>
 */
@Controller
@RequestMapping(value = "/http/api/shop")
public class ShoppingCartController
{
    @Autowired
    private IShoppingCartService shoppingCartService;
    
    /**
     * 
     * findShoppingCartInfoBy：根据用户Id查询该用户购物车限时抢购商品信息
     * @param userId
     * @return
     * @exception   
     * @author pengcheng.yang
     * @date 2015年9月16日 下午5:39:00
     */
    @ResponseBody
    @RequestMapping(value = "/searchShoppingCartLimitInfo.do",method = {RequestMethod.GET, RequestMethod.POST})
    public Response<List<ShoppingCartDto>> findShoppingCartLimitInfoBy(String userId){
        Response<List<ShoppingCartDto>> result = new Response<List<ShoppingCartDto>>();
        ShoppingCartDto dto = new ShoppingCartDto();
        dto.setOpenId(userId);
        dto.setGoodsTypeId("1");
        List<ShoppingCartDto> list = shoppingCartService.findBy(dto);
        if(list != null){
            result.setMessage("查询购物车信息成功!");
            result.setData(list);
            result.setStatus(AppResponse.RETURN_SUCCESS);
            return result;
        }else{
            result.setMessage("查询购物车信息失败!");
            result.setStatus(AppResponse.RETURN_FAILE);
            return result;
        }
    }
    
    
    /**
     * 
     * findShoppingCartInfoBy：根据用户Id查询该用户购物车限时抢购商品信息
     * @param userId
     * @return
     * @exception   
     * @author pengcheng.yang
     * @date 2015年9月16日 下午5:39:00
     */
    @ResponseBody
    @RequestMapping(value = "/searchShoppingCartFineInfo.do",method = {RequestMethod.GET, RequestMethod.POST})
    public Response<List<ShoppingCartDto>> findShoppingCartFineInfoBy(String userId){
        Response<List<ShoppingCartDto>> result = new Response<List<ShoppingCartDto>>();
        ShoppingCartDto dto = new ShoppingCartDto();
        dto.setOpenId(userId);
        dto.setGoodsTypeId("2");
        List<ShoppingCartDto> list = shoppingCartService.findBy(dto);
        if(list != null){
            result.setMessage("查询购物车信息成功!");
            result.setData(list);
            result.setStatus(AppResponse.RETURN_SUCCESS);
            return result;
        }else{
            result.setMessage("查询购物车信息失败!");
            result.setStatus(AppResponse.RETURN_FAILE);
            return result;
        }
   }
    
    /**
     * 
     * insertShoppingCart：添加购物车
     * @param request
     * @return
     * @exception   
     * @author pengcheng.yang
     * @date 2015年9月16日 下午5:45:53
     */
    @ResponseBody
    @RequestMapping(value = "/searchInsertShoppingCart.do",method = {RequestMethod.GET, RequestMethod.POST})
    public int insertShoppingCart(HttpServletRequest request){
        return shoppingCartService.insertShoppingCartRequest(request);
    }
    
    /**
     * 
     * deleteShoppingCartInfo：根据用户Id和购物车Id删除购物车商品信息
     * @param userId
     * @param shopCatId
     * @return
     * @exception   
     * @author pengcheng.yang
     * @date 2015年9月16日 下午5:47:54
     */
    @ResponseBody
    @RequestMapping(value = "/searchDeleteShoppingCart.do",method = {RequestMethod.GET, RequestMethod.POST})
    public int deleteShoppingCartInfo(String userId ,String shopId){
        return shoppingCartService.deleteShoppingCartInfo(userId, shopId);
    }
    
    /**
     * 
     * countShoppingCart：查询单个用户购物车商品数量
     * @param userId
     * @return
     * @exception   
     * @author pengcheng.yang
     * @date 2015年9月22日 上午10:52:43
     */
    @ResponseBody
    @RequestMapping(value = "/searchCountShoppingCart.do",method = {RequestMethod.GET, RequestMethod.POST})
    public int countShoppingCart(String userId){
        return shoppingCartService.countShoppingCart(userId);
    }
    
    /**
     * 
     * updateShopIdNum：更改购物车商品的数量
     * @param shopId
     * @param userId
     * @param shopNum
     * @return
     * @exception   
     * @author pengcheng.yang
     * @date 2015年9月22日 下午3:10:44
     */
    @ResponseBody
    @RequestMapping(value = "/searchupdateShopIdNum.do",method = {RequestMethod.GET, RequestMethod.POST})
    public int updateShopIdNum(String shopId,String userId,String marktext,String shopNum){
        return shoppingCartService.updateShopIdNum(shopId, userId ,marktext, shopNum);
    }
    
    
}

