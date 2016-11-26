package com.ssic.shop.manage.mapper;

import org.apache.ibatis.annotations.Param;

import com.ssic.shop.manage.dto.ShoppingCartDto;

/**
 * 		
 * <p>Title: ShoppingCartExMapper </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author pengcheng.yang	
 * @date 2015年9月16日 下午4:42:44	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年9月16日 下午4:42:44</p>
 * <p>修改备注：</p>
 */

public interface ShoppingCartExMapper {
    
    /**
     * 
     * deleteShoppingCartInfo：删除购物车里面某件商品
     * @param userId
     * @param shopCatId
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年9月18日 上午11:54:54
     */
    int deleteShoppingCartInfo(@Param("userId")String userId ,@Param("shopCatId")String shopCatId);

     
    /**
     * @author yuanbin
     * 
     * <p>Description: 清空购物车</p>
     */
	void deleteByExample(String carteUserId);
	
	/**
	 * 
	 * countShoppingCart：查询用户今天购物车添加的商品
	 * @param userId
	 * @return
	 * @exception	
	 * @author pengcheng.yang
	 * @date 2015年9月18日 上午11:57:11
	 */
	int countShoppingCart(@Param("userId")String userId);
	
	/**
	 * 
	 * countIsNotAdd：统计要添加的商品在数据库是否存在
	 * @param shopId
	 * @param userId
	 * @return
	 * @exception	
	 * @author pengcheng.yang
	 * @date 2015年9月21日 下午4:18:48
	 */
	int countIsNotAdd(@Param("shopId")String shopId , @Param("userId")String userId);
	
	/**
	 * 
	 * updataShopCount：如果要添加购物车的商品已经存在更改商品数量，不再产生新的数据
	 * @param shopId
	 * @param userId
	 * @return
	 * @exception	
	 * @author pengcheng.yang
	 * @date 2015年9月21日 下午4:21:31
	 */
	int updataShopCount(@Param("shopId")String shopId , @Param("userId")String userId ,@Param("shopNum")int shopNum);
	
	/**
	 * 
	 * countGoodsType：统计限时抢购的商品在购物车表中的数量
	 * @param shopId
	 * @param userId
	 * @return
	 * @exception	
	 * @author pengcheng.yang
	 * @date 2015年9月21日 下午5:30:14
	 */
	int countGoodsType(@Param("shopId")String shopId , @Param("userId")String userId);
	
	/**
	 * 
	 * countGoodsCount：查询对应商品在购物车中的数量
	 * @param shopId
	 * @param userId
	 * @return
	 * @exception	
	 * @author pengcheng.yang
	 * @date 2015年9月22日 下午4:08:11
	 */
	int countGoodsCount(@Param("shopId")String shopId , @Param("userId")String userId);
	
	/**
	 * 
	 * updataShopIdCount：更改购物车种商品的数量
	 * @param shopId
	 * @param userId
	 * @param shopNum
	 * @return
	 * @exception	
	 * @author pengcheng.yang
	 * @date 2015年9月22日 下午5:53:14
	 */
	int updataShopIdCount(@Param("shopId")String shopId, @Param("userId")String userId, @Param("shopNum")int shopNum);
}