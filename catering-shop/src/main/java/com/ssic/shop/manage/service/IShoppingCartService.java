package com.ssic.shop.manage.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Param;

import com.ssic.shop.manage.dto.ShoppingCartDto;

public interface IShoppingCartService {

	List<ShoppingCartDto> findBy (ShoppingCartDto shoppingCartDto);
	
	ShoppingCartDto findById(String id);
	
	
	/**
     * 
     * insertShoppingCartInfo：添加购物车
     * @param dto
     * @return
     * @exception   
     * @author pengcheng.yang
     * @date 2015年9月16日 下午3:56:27
     */
	int insertShoppingCart(ShoppingCartDto shoppingCartDto);
	
	void updateShoppingCart(ShoppingCartDto shoppingCartDto);

	
	/**     
	 * findByCarterUserId：一句话描述方法功能
	 * @param shoppingCartDto
	 * @exception	
	 * @author yuanbin
	 * @date 2015年8月28日 上午10:36:03	 
	 */
	List<ShoppingCartDto> findByCarterUserId(ShoppingCartDto shoppingCartDto);

	
	/**     
	 * delect：一句话描述方法功能
	 * @param shoppingCart
	 * @exception	
	 * @author yuanbin
	 * @date 2015年8月28日 下午6:09:31	 
	 */
	void delect(String carteUserId);
	
	
	
	/**
	 * 
	 * deleteShoppingCartInfo：删除购物车中商品
	 * @param dto
	 * @return
	 * @exception	
	 * @author pengcheng.yang
	 * @date 2015年9月16日 下午3:57:57
	 */
	int deleteShoppingCartInfo(String userId ,String shopCatId);
	
	/**
	 * 
	 * insertShoppingCartRequest：添加购物车
	 * @param request
	 * @return
	 * @exception	
	 * @author Administrator
	 * @date 2015年9月16日 下午5:44:57
	 */
	int insertShoppingCartRequest(HttpServletRequest request);
	
	/**
	 * 
	 * countShoppingCart：查询对应用户购物车商品数量
	 * @param userId
	 * @return
	 * @exception	
	 * @author pengcheng.yang
	 * @date 2015年9月21日 下午1:51:26
	 */
	int countShoppingCart(String userId);
	
	/**
	 * 
	 * countIsNotAdd：统计要添加的商品在数据库是否存在
	 * @param shopId
	 * @param userId
	 * @return
	 * @exception	
	 * @author pengcheng.yang
	 * @date 2015年9月21日 下午4:35:29
	 */
	int countIsNotAdd(String shopId , String userId);
	
	/**
	 * 
	 * updataShopCount：如果要添加购物车的商品已经存在更改商品数量，不再产生新的数据
	 * @param shopId
	 * @param userId
	 * @param shopNum
	 * @return
	 * @exception	
	 * @author pengcheng.yang
	 * @date 2015年9月21日 下午4:36:09
	 */
	int updataShopCount(String shopId , String userId , int shopNum);
	
	/**
	 * 
	 * countGoodsType：统计限时抢购的商品在购物车表中的数量
	 * @param shopId
	 * @param userId
	 * @return
	 * @exception	
	 * @author pengcheng.yang
	 * @date 2015年9月21日 下午5:33:51
	 */
	int countGoodsType(String shopId , String userId);
	
	/**
	 * 
	 * updateShopIdNum：更改购物车商品数量
	 * @param shopId
	 * @param userId
	 * @param shopNum
	 * @return
	 * @exception	
	 * @author pengchenglyang
	 * @date 2015年9月22日 下午3:11:55
	 */
	int updateShopIdNum(String shopId,String userId,String text,String shopNum);
	
	/**
	 * 
	 * countGoodsCount：查询对应商品在购物车中的数量
	 * @param shopId
	 * @param userId
	 * @return
	 * @exception	
	 * @author pengcheng.yang
	 * @date 2015年9月22日 下午4:09:23
	 */
	int countGoodsCount(String shopId , String userId);
	
	/**
	 * 
	 * updataShopIdCount：更改购物车商品数量
	 * @param shopId
	 * @param userId
	 * @param shopNum
	 * @return
	 * @exception	
	 * @author pengcheng.yang
	 * @date 2015年9月22日 下午5:49:19
	 */
	int updataShopIdCount(String shopId, String userId, int shopNum);
	
}
