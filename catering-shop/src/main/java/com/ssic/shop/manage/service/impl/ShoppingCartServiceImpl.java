package com.ssic.shop.manage.service.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ssic.shop.manage.dao.ShoppingCartDao;
import com.ssic.shop.manage.dto.GoodsDto;
import com.ssic.shop.manage.dto.ShoppingCartDto;
import com.ssic.shop.manage.pojo.ShoppingCart;
import com.ssic.shop.manage.service.IGoodsService;
import com.ssic.shop.manage.service.IShoppingCartService;
import com.ssic.util.BeanUtils;
import com.ssic.util.UUIDGenerator;

@Service
public class ShoppingCartServiceImpl implements IShoppingCartService{

	@Autowired
	private ShoppingCartDao shoppingCartDao;
	
	@Autowired
    private IGoodsService goodsService;
	
	/**
	 * (non-Javadoc)   
	 * @see com.ssic.shop.manage.service.IShoppingCartService#findBy(com.ssic.shop.manage.dto.ShoppingCartDto)
	 * @author pengcheng.yang
	 * @desc 根据用户Id和购物车Id查询用户在购物车的信息
	 */
	@Override
	public List<ShoppingCartDto> findBy(ShoppingCartDto shoppingCartDto) {
		ShoppingCart shoppingCart = new ShoppingCart();
		BeanUtils.copyProperties(shoppingCartDto, shoppingCart);
		List<ShoppingCart> list= shoppingCartDao.findBy(shoppingCart);
		List<ShoppingCartDto> listDto  = null;
		if(list.size()>0){
		    listDto= BeanUtils.createBeanListByTarget(list, ShoppingCartDto.class);
		}
		return listDto;
	}

	/**
	 * 
	 * (non-Javadoc)   
	 * @see com.ssic.shop.manage.service.IShoppingCartService#findById(java.lang.String)
	 * @desc 根据购物车商品信息Id删除购物车对应商品
	 * @author pengcheng.yang
	 */
	@Override
	public ShoppingCartDto findById(String id) {
		// TODO Auto-generated method stub
		ShoppingCart shoppingCart= shoppingCartDao.findById(id);
		ShoppingCartDto shoppingCartDto = new ShoppingCartDto();
		BeanUtils.copyProperties(shoppingCart, shoppingCartDto);
		return shoppingCartDto;
	}

	/**
	 * 
	 * (non-Javadoc)   
	 * @see com.ssic.shop.manage.service.IShoppingCartService#insertShoppingCart(com.ssic.shop.manage.dto.ShoppingCartDto)
	 * @author pengcheng.yang
	 * @desc 购物车添加商品
	 */
	@Override
	public int insertShoppingCart(ShoppingCartDto shoppingCartDto) {
		// TODO Auto-generated method stub
		ShoppingCart shoppingCart = new ShoppingCart();
		BeanUtils.copyProperties(shoppingCartDto, shoppingCart);
		shoppingCart.setId(UUIDGenerator.getUUID());
		shoppingCart.setStat(1);
		shoppingCart.setCreateTime(new Date());
		int flag = shoppingCartDao.insertShoppingCart(shoppingCart);
		if(flag>0){
		    return flag;
		}
		return 0;
	}

	@Override
	public void updateShoppingCart(ShoppingCartDto shoppingCartDto) {
		// TODO Auto-generated method stub
		ShoppingCart shoppingCart = new ShoppingCart();
		BeanUtils.copyProperties(shoppingCartDto, shoppingCart);
		shoppingCartDao.updateShoppingCart(shoppingCart);
	}

	
	 /** 
	 * (non-Javadoc)   
	 * @see com.ssic.shop.manage.service.IShoppingCartService#findByCarterUserId(com.ssic.shop.manage.dto.ShoppingCartDto) 
	 * 
	 * @author yuanbin
	 */
	@Override
	public List<ShoppingCartDto> findByCarterUserId(ShoppingCartDto shoppingCartDto) {
		ShoppingCart shoppingCart = new ShoppingCart();
		BeanUtils.copyProperties(shoppingCartDto, shoppingCart);
		List<ShoppingCart> list= shoppingCartDao.findByCarterUserId(shoppingCart);
		List<ShoppingCartDto> listDto= BeanUtils.createBeanListByTarget(list, ShoppingCartDto.class);
		return listDto;
	}

	
	 /** 
	 * (non-Javadoc)   
	 * @see com.ssic.shop.manage.service.IShoppingCartService#delect(com.ssic.shop.manage.dto.ShoppingCartDto)  
	 * 
	 *  @author yuanbin
	 */
	@Override
	public void delect(String carteUserId) {
		shoppingCartDao.delect(carteUserId);
		
	}

	/**
	 * 
	 * (non-Javadoc)   
	 * @see com.ssic.shop.manage.service.IShoppingCartService#deleteShoppingCartInfo(com.ssic.shop.manage.dto.ShoppingCartDto)
	 * @desc 根据用户Id和购物车Id删除购物车对应的商品
	 * @author pengcheng.yang
	 */
    @Override
    public int deleteShoppingCartInfo(String userId ,String shopCatId)
    {
        if((!StringUtils.isEmpty(userId))&&(!StringUtils.isEmpty(shopCatId))){
            int flag = shoppingCartDao.deleteShoppingCartInfo(userId, shopCatId);
            if(flag>0){
                return flag;
            }
        }
        return 0;
    }
    
    /**
     * 
     * insertShoppingCartRequest：购物车添加商品
     * @param shoppingCartDto
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年9月16日 下午5:43:42
     */
    @Override
    public int insertShoppingCartRequest(HttpServletRequest request) {
        String json = request.getParameter("data");
        JSONObject fromObject = JSONObject.fromObject(json);
        
        //购物车商品数量
        int count = 0;
        
        //加入购物车的商品ID
        String shopId = fromObject.getString("shopId");
        
        //表示用户唯一ID
        String userId = fromObject.getString("userId");
        
        //添加商品的数量
        int shopNum = Integer.parseInt(fromObject.getString("shopNum"));
        
        /*//该商品当前购物车中商品数量
        int stockNum = countGoodsCount(shopId, userId);*/
        
        //查询限时抢购的商品的限购数量
        GoodsDto goodsDto = goodsService.findGoodsById(shopId);
        
        //如果是限购商品这检查购物车中该商品数量超过限购数量
        if("1".equals(goodsDto.getGoodsTypeId())){
            //商品限购件数
            int purchase = Integer.parseInt(goodsDto.getPurchase());
            //查询限时抢购商品在数据库中存在的数量 (包括已经提交订单的数量)
            int countNum = countGoodsType(shopId,userId);
            //限购商品
            if(countNum+shopNum <= purchase){
                //添加购物车
                int countAdd = countIsNotAdd(shopId,userId);
                if(countAdd > 0){
                    //更改购物车商品的数量
                    count = countIsNotAddShopId(shopId,userId,shopNum);
                }else{
                    int flag = findGoods(shopId , userId ,shopNum);
                    if(flag > 0){
                        count = shoppingCartDao.countShoppingCart(userId);
                    }
                }
            }
            return count;
        }else{
            //查询要加入购物车的商品是否已经在购物车存在则更改商品数量，不存在则新增一条数据
            int countAdd = countIsNotAdd(shopId,userId);
            if(countAdd > 0){
                //更改购物车商品的数量
                return countIsNotAddShopId(shopId,userId,shopNum);
            }else{
                //添加购物车
                int flag = findGoods(shopId , userId ,shopNum);
                if(flag > 0){
                    count = shoppingCartDao.countShoppingCart(userId);
                }
            }
            return count;
        }
        
        
    }
    
    
    /**
     * 
     * countIsNotAddShop：更改购物车已存在商品数量
     * @param shopId
     * @param userId
     * @param shopNum
     * @return
     * @exception   
     * @author pengcheng.yang
     * @date 2015年9月22日 上午10:16:17
     */
    public int countIsNotAddShopId(String shopId , String userId, int shopNum){
        //更改购物车商品的数量
        int num = this.updataShopIdCount(shopId, userId, shopNum);
        if(num > 0){
           return  shoppingCartDao.countShoppingCart(userId);
        }
           return -1;
    }
    
    /**
     * 
     * countIsNotAddShop：更改购物车已存在商品数量
     * @param shopId
     * @param userId
     * @param shopNum
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年9月22日 上午10:16:17
     */
    public int countIsNotAddShop(String shopId , String userId, int shopNum){
        //更改购物车商品的数量
        int num = this.updataShopCount(shopId, userId, shopNum);
        if(num > 0){
           return  shoppingCartDao.countShoppingCart(userId);
        }
           return 0;
    }
    
    
    /**
     * 
     * findGoods： 添加购物车
     * @param shopId
     * @param userId
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年9月21日 下午5:54:45
     */
    public int findGoods(String shopId , String userId,int shopNum){
        int count = 0;
        GoodsDto goodsDto = goodsService.findGoodsById(shopId);
        //添加购物车
        ShoppingCartDto dto = new ShoppingCartDto();
        if(goodsDto != null){
            dto.setGoodsId(goodsDto.getId());
            dto.setGoodsName(goodsDto.getGoodsName());
            dto.setCount(shopNum);
            dto.setPrice(goodsDto.getSalesPrice());
            dto.setIcon(goodsDto.getIcon());
            dto.setGoodsTypeId(goodsDto.getGoodsTypeId());
            dto.setOpenId(userId);
        }
        int flag = insertShoppingCart(dto);
        if(flag > 0){
            count = shoppingCartDao.countShoppingCart(userId);
        }
        return count;
    }
    
    

    /**
     * (non-Javadoc)   
     * @see com.ssic.shop.manage.service.IShoppingCartService#countShoppingCart(java.lang.String)
     * @author pengcheng.yang
     * @desc 查询对应用户购物车商品数量
     */
    @Override
    public int countShoppingCart(String userId)
    {
        int count = shoppingCartDao.countShoppingCart(userId);
        if(count > 0){
            return count;
        }
        return 0;
    }

    /**
     * 
     * (non-Javadoc)   
     * @see com.ssic.shop.manage.service.IShoppingCartService#countIsNotAdd(java.lang.String, java.lang.String)
     * @author pengcheng.yang
     * @desc 统计要添加的商品在数据库是否存在
     * @date 2015-09-21
     */
    @Override
    public int countIsNotAdd(String shopId, String userId)
    {
        if(!StringUtils.isEmpty(shopId) && !StringUtils.isEmpty(userId)){
            return shoppingCartDao.countIsNotAdd(shopId, userId);
        }
        return -1;
    }

    /**
     * 
     * (non-Javadoc)   
     * @see com.ssic.shop.manage.service.IShoppingCartService#updataShopCount(java.lang.String, java.lang.String, int)
     * @author pengcheng.yang
     * @desc 如果要添加购物车的商品已经存在更改商品数量，不再产生新的数据
     * @date 2015-09-21
     */
    @Override
    public int updataShopCount(String shopId, String userId, int shopNum)
    {
        if(!StringUtils.isEmpty(shopId) && !StringUtils.isEmpty(userId) && shopNum != 0){
            return shoppingCartDao.updataShopCount(shopId, userId, shopNum);
        }
        return 0;
    }
    
    /**
     * 
     * (non-Javadoc)   
     * @see com.ssic.shop.manage.service.IShoppingCartService#updataShopCount(java.lang.String, java.lang.String, int)
     * @author pengcheng.yang
     * @desc 更改购物车商品数量
     * @date 2015-09-22
     */
    @Override
    public int updataShopIdCount(String shopId, String userId, int shopNum)
    {
        if(!StringUtils.isEmpty(shopId) && !StringUtils.isEmpty(userId) && shopNum != 0){
            return shoppingCartDao.updataShopIdCount(shopId, userId, shopNum);
        }
        return 0;
    }
    
    
    /**
     * (non-Javadoc)   
     * @see com.ssic.shop.manage.service.IShoppingCartService#countGoodsType(java.lang.String, java.lang.String)
     * @author pengcheng.yang
     * @desc 统计限时抢购的商品在购物车表中的数量
     * @date 2015-09-21
     */
    @Override
    public int countGoodsType(String shopId, String userId)
    {
        if(!StringUtils.isEmpty(shopId) && !StringUtils.isEmpty(userId)){
            return shoppingCartDao.countGoodsType(shopId, userId);
        }
        return 0;
    }

    /**
     * 
     * (non-Javadoc)   
     * @see com.ssic.shop.manage.service.IShoppingCartService#updateShopIdNum(java.lang.String, java.lang.String, java.lang.String)
     * @author pengcheng.yang
     * @desc 更改购物车商品数量
     * @date 2015-09-22
     */
    @Override
    public int updateShopIdNum(String shopId, String userId ,String text, String shopNum)
    {
        int count = 0;
        int shopNumber = Integer.parseInt(shopNum);
        int stockNum = countGoodsCount(shopId, userId);
        if(shopNumber == stockNum){
            shopNumber = stockNum;
        }else{
            return -1;
        }
        //查询限时抢购的商品的限购数量
        GoodsDto goodsDto = goodsService.findGoodsById(shopId);
        //如果是限购商品这检查购物车中该商品数量超过限购数量
        if("1".equals(goodsDto.getGoodsTypeId())){
            if("0".equals(text)){
                //判断购物车数量是否剩1件
                if(shopNumber == 1){
                    count = 1;
                }else{
                    //更改购物车商品的数量
                    int flag = countIsNotAddShop(shopId,userId,shopNumber-1);
                    if(flag > 0){
                        return flag;
                    }else{
                        return -1;
                    }
                }
            }
            if("1".equals(text)){
                //商品限购件数
                int purchase = Integer.parseInt(goodsDto.getPurchase());
                //查询限时抢购商品在购物车中存在的数量
                int countNum = countGoodsType(shopId,userId);
                //限购商品
                if(countNum < purchase){
                    //更改购物车商品的数量
                    int flag = countIsNotAddShop(shopId,userId,shopNumber+1);
                    if(flag > 0){
                        return flag;
                    }else{
                        return -1;
                    }
                }
            }
            return count;
        }else{
            if("1".equals(text)){
                    //更改购物车商品的数量 
                    int flag = countIsNotAddShop(shopId,userId,shopNumber+1);
                    if(flag > 0){
                        return flag;
                    }else{
                        return -1;
                    }
            }
            if("0".equals(text)){
                    //更改购物车商品的数量 
                if(shopNumber == 1){
                    count = 1;
                }else{
                    int flag = countIsNotAddShop(shopId,userId,shopNumber-1);
                    if(flag > 0){
                        return flag;
                    }else{
                        return -1;
                    }
                }
            }
            return count;
        }
    }

    /**
     * 
     * (non-Javadoc)   
     * @see com.ssic.shop.manage.service.IShoppingCartService#countGoodsCount(java.lang.String, java.lang.String)
     * @author pengcheng.yang
     * @desc 查询对应商品在购物车中的数量
     * @date 2015-09-22
     */
    @Override
    public int countGoodsCount(String shopId, String userId)
    {
        return shoppingCartDao.countGoodsCount(shopId, userId);
    }
    
    

}
