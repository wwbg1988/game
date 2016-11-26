package com.ssic.shop.manage.dao;

import java.util.List;


import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import com.ssic.shop.manage.mapper.ShoppingCartExMapper;
import com.ssic.shop.manage.mapper.ShoppingCartMapper;
import com.ssic.shop.manage.pojo.ShoppingCart;
import com.ssic.shop.manage.pojo.ShoppingCartExample;
import com.ssic.shop.manage.pojo.ShoppingCartExample.Criteria;
import com.ssic.util.constants.DataStatus;

@Repository
public class ShoppingCartDao{

	@Autowired
    private ShoppingCartMapper mapper;
	
	@Autowired
	private ShoppingCartExMapper exMapper;
	
	
	/**
	 * 
	 * findBy：根据用户Id和购物车Id查询用户在购物车的信息
	 * @param param
	 * @return
	 * @exception	
	 * @author pengcheng.yang
	 * @date 2015年9月16日 下午4:57:54
	 */
	public List<ShoppingCart>  findBy(ShoppingCart param){
		ShoppingCartExample example = new ShoppingCartExample();
		Criteria criteria = example.createCriteria();
		if(!StringUtils.isEmpty(param.getId())){
			criteria.andIdEqualTo(param.getId());
		}
		if(!StringUtils.isEmpty(param.getOpenId())){
		    criteria.andOpenIdEqualTo(param.getOpenId());
		}
		if(!StringUtils.isEmpty(param.getGoodsTypeId())){
            criteria.andGoodsTypeIdEqualTo(param.getGoodsTypeId());
        }
		criteria.andStatEqualTo(DataStatus.ENABLED);
		return mapper.selectByExample(example);
	}
	
	/**
	 * 
	 * findById：一句话描述方法功能
	 * @param id
	 * @return
	 * @exception	
	 * @author pengcheng.yang
	 * @date 2015年9月16日 下午4:58:13
	 */
	public ShoppingCart findById(String id){
		return mapper.selectByPrimaryKey(id);
	}
	
	/**
	 * 
	 * insertShoppingCart：一句话描述方法功能
	 * @param param
	 * @return
	 * @exception	
	 * @author pengcheng.yang
	 * @date 2015年9月16日 下午4:58:20
	 */
	public int insertShoppingCart(ShoppingCart param){
		return mapper.insertSelective(param);
	}
	
	/**
	 * 
	 * updateShoppingCart：一句话描述方法功能
	 * @param param
	 * @exception	
	 * @author pengcheng.yang
	 * @date 2015年9月16日 下午4:58:27
	 */
	public void updateShoppingCart(ShoppingCart param){
		mapper.updateByPrimaryKeySelective(param);
	}

	
	/**     
	 * findByCarterUserId：一句话描述方法功能
	 * @param shoppingCart
	 * @return
	 * @exception	
	 * @author yuanbin
	 * @date 2015年8月28日 上午10:54:39	 
	 */
	public List<ShoppingCart> findByCarterUserId(ShoppingCart param) {
		ShoppingCartExample example = new ShoppingCartExample();
		 Criteria criteria = example.createCriteria();
		if(!StringUtils.isEmpty(param.getOpenId())){
			criteria.andOpenIdEqualTo(param.getOpenId());
		}
		criteria.andStatEqualTo(param.getStat());
		return mapper.selectByExample(example);
	}

	
	/**     
	 * delect：一句话描述方法功能
	 * @param shoppingCart
	 * @exception	
	 * @author yuanbin
	 * @date 2015年8月28日 下午6:12:06	 
	 */
	public void delect(String carteUserId) {
	    exMapper.deleteByExample(carteUserId);
		
	}
	
	/**
	 * 
	 * deleteShoppingCartInfo：根据用户Id和购物车Id删除用户购物车里面对应的商品
	 * @param userId
	 * @param shopCatId
	 * @return
	 * @exception	
	 * @author pengcheng.yang
	 * @date 2015年9月16日 下午5:13:26
	 */
	public int deleteShoppingCartInfo(String userId ,String shopCatId){
	       return exMapper.deleteShoppingCartInfo(userId,shopCatId);
	}
	
	/**
	 * 
	 * countShoppingCart：统计用户今天购物车中添加的商品信息
	 * @param userId
	 * @return
	 * @exception	
	 * @author pengcheng.yang
	 * @date 2015年9月18日 上午11:59:02
	 */
	public int countShoppingCart(String userId){
	    return exMapper.countShoppingCart(userId);
	}
	
	/**
	 * 
	 * countIsNotAdd：统计要添加的商品在数据库是否存在
	 * @param shopId
	 * @param userId
	 * @return
	 * @exception	
	 * @author pengcheng.yang
	 * @date 2015年9月21日 下午4:17:23
	 */
	public int countIsNotAdd(String shopId , String userId){
	    return exMapper.countIsNotAdd(shopId, userId);
	}
	
	/**
	 * 
	 * updataShopCount：如果要添加购物车的商品已经存在更改商品数量，不再产生新的数据
	 * @return
	 * @exception	
	 * @author pengcheng.yang
	 * @date 2015年9月21日 下午4:20:28
	 */
	public int updataShopCount(String shopId , String userId ,int shopNum){
	    return exMapper.updataShopCount(shopId, userId, shopNum);
	}
	
	/**
	 * 
	 * countGoodsType：统计限时抢购的商品在购物车表中的数量
	 * @param shopId
	 * @param userId
	 * @return
	 * @exception	
	 * @author pengcheng.yang
	 * @date 2015年9月21日 下午5:33:09
	 */
	public int countGoodsType(String shopId , String userId){
	    return  exMapper.countGoodsType(shopId, userId);
	}
	
	/**
	 * 
	 * countGoodsCount：查询对应商品在购物车中的数量
	 * @param shopId
	 * @param userId
	 * @return
	 * @exception	
	 * @author pengcheng.yang
	 * @date 2015年9月22日 下午4:11:41
	 */
	public int countGoodsCount(String shopId , String userId){
	    return exMapper.countGoodsCount(shopId, userId);
	}
	
	/**
	 * 
	 * updataShopIdCount：更改购物车商品数量
	 * @param shopId
	 * @param userId
	 * @param shopNum
	 * @return
	 * @exception	
	 * @author pengcheng.yang
	 * @date 2015年9月22日 下午5:52:08
	 */
	public int updataShopIdCount(String shopId, String userId, int shopNum){
	    return exMapper.updataShopIdCount(shopId, userId, shopNum);
	}
}
