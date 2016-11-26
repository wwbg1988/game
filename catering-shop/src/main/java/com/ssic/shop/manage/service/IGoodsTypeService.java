/**
 * 
 */
package com.ssic.shop.manage.service;

import java.util.List;

import com.ssic.shop.manage.dto.GoodsTypeDto;
import com.ssic.shop.manage.dto.PageHelperDto;

/**		
 * <p>Title: IGoodsTypeService </p>
 * <p>Description: 商品类型Service层</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 刘博	
 * @date 2015年8月27日 上午10:12:41	
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年8月27日 上午10:12:41</p>
 * <p>修改备注：</p>
 */
public interface IGoodsTypeService
{

public List<GoodsTypeDto> findAllBy(GoodsTypeDto param, PageHelperDto ph);

    
    /**     
     * findCount：一句话描述方法功能
     * @param carteUserDto
     * @return
     * @exception   
     * @author 刘博
     * @date 2015年8月27日 上午10:38:51   
     */
    public int findCount(GoodsTypeDto goodsDto);


    
    /**     
     * finByTypeName：一句话描述方法功能
     * @param typeName
     * @return
     * @exception	
     * @author 刘博
     * @date 2015年8月27日 下午5:58:14	 
     */
    public GoodsTypeDto finByTypeName(String typeName,String id);


    
    /**     
     * add：一句话描述方法功能
     * @param goodsTypeDto
     * @exception	
     * @author 刘博
     * @date 2015年8月27日 下午5:58:23	 
     */
    public void add(GoodsTypeDto goodsTypeDto);


    
    /**     
     * findGoodsTypeById：一句话描述方法功能
     * @param id
     * @return
     * @exception	
     * @author 刘博
     * @date 2015年8月28日 上午9:20:22	 
     */
    public GoodsTypeDto findGoodsTypeById(String id);


    
    /**     
     * delete：一句话描述方法功能
     * @param r
     * @exception	
     * @author 刘博
     * @date 2015年8月28日 上午9:20:35	 
     */
    public void delete(GoodsTypeDto r);
    
}

