/**
 * 
 */
package com.ssic.shop.manage.service;

import java.util.List;

import com.ssic.game.common.pageModel.PageHelper;
import com.ssic.shop.manage.dto.FindDataResults;
import com.ssic.shop.manage.dto.GoodsDto;
import com.ssic.shop.manage.dto.PageHelperDto;
import com.ssic.shop.manage.pojo.Goods;
import com.ssic.util.model.Response;

/**		
 * <p>Title: IGoodsService </p>
 * <p>Description: 商品Service层</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 刘博
 * @date 2015年8月27日 上午10:11:57	
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年8月27日 上午10:11:57</p>
 * <p>修改备注：</p>
 */
public interface IGoodsService
{

    public List<GoodsDto> findAllBy(GoodsDto param, PageHelperDto ph);

    
    /**     
     * findCount：一句话描述方法功能
     * @param carteUserDto
     * @return
     * @exception   
     * @author 刘博
     * @date 2015年8月27日 上午10:38:51   
     */
    public int findCount(GoodsDto goodsDto);
    

    /**     
     * findByGoodsIdAndName：通过id和商品名称查找商品
     * @param goodsId
     * @param goodsName
     * @return
     * @exception   
     * @author 刘博
     * @date 2015年8月28日 下午1:59:58    
     */
    public GoodsDto findByGoodsIdAndName(String goodsId, String goodsName);

    /**     
     * findGoodsById：
     * @param id
     * @return
     * @exception   
     * @author 刘博
     * @date 2015年8月28日 下午1:59:58    
     */
    public GoodsDto findGoodsById(String id);

    /**     
     * delete：
     * @param id
     * @return
     * @exception   
     * @author 刘博
     * @date 2015年8月28日 下午1:59:58    
     */
    public void delete(GoodsDto r);

    
    /**     
     * add：一句话描述方法功能
     * @param goodsDto
     * @exception   
     * @author 刘博
     * @date 2015年8月28日 上午9:33:06    
     */
    public void add(GoodsDto goodsDto);

    
    /**
     * 
     * findIsTurnAllBy：查询对应食堂轮播的商品的内容
     * @param param
     * @return
     * @exception   
     * @author pengcheng.yang
     * @date 2015年8月28日 上午11:17:20
     */
    public List<GoodsDto> findIsTurnAllBy(GoodsDto goodsDto);
    
    /**
     * 
     * findLimitedQualityAllBy：分页查询限时特购的商品信息和精品推荐商品信息
     * @param param
     * @param ph
     * @return
     * @exception   
     * @author pengcheng.yang
     * @date 2015年8月28日 上午11:24:28
     */
    public List<GoodsDto> findLimitedQualityAllBy(GoodsDto goodsDto, PageHelper ph);
    
    /**
     * 
     * findGoodsIsTurnBy：查询轮播商品信息
     * @param cafetoriumId
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年9月16日 上午11:27:17
     */
    public Response<FindDataResults> findGoodsIsTurnBy(String cafetoriumId);

    /**
     * 
     * findGoodsLimitedBy：查询详情页面限时特购商品信息
     * @param cafetoriumId
     * @param ph
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年9月16日 下午1:44:52
     */
    public Response<FindDataResults> findGoodsLimitedBy(String cafetoriumId, PageHelper ph);


    /**
     * 
     * findGoodsFineBy：查询详情页面精品推荐商品信息
     * @param cafetoriumId
     * @param ph
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年9月16日 下午1:46:17
     */
    public Response<FindDataResults> findGoodsFineBy(String cafetoriumId, PageHelper ph);
    
    /**
     * 
     * findlimitAndFineStickAllBy：查询首页限时特购和精品推荐商品信息
     * @param param
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年9月16日 下午1:58:17
     */
    public List<GoodsDto> findlimitAndFineStickAllBy(GoodsDto param);
    
    /**
     * 
     * findlimitStickAllBy：查询首页限时特购商品信息
     * @param goodsDto
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年9月16日 下午2:08:24
     */
    public Response<FindDataResults> findlimitStickAllBy(String cafetoriumId);
    
    /**
     * 
     * findFineStickAllBy：查询首页精品推荐商品信息
     * @param goodsDto
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年9月16日 下午2:08:28
     */
    public Response<FindDataResults> findFineStickAllBy(String cafetoriumId);

    /**
     * 
     * update：更新商品库存
     * @param goodsDto
     * @return
     * @exception	
     * @author yuanbin
     * @date 2015年9月17日 下午2:08:28
     */
	public void update(GoodsDto goodsDto);
	
	/**
	 * 保存excel信息	 
	 * @author 朱振	
	 * @date 2015年11月14日 下午1:08:33	
	 * @version 1.0
	 * @param goodsList
	 * @return
	 * <p>修改人：朱振</p>
	 * <p>修改时间：2015年11月14日 下午1:08:33</p>
	 * <p>修改备注：</p>
	 */
	public int insertGoodsByExcel(List<GoodsDto> goodsList);
    
}
