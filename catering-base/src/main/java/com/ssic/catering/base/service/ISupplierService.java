/**
 * 
 */
package com.ssic.catering.base.service;

import java.util.List;

import com.ssic.catering.lbs.dto.SupplierDto;
import com.ssic.game.common.dto.PageHelperDto;

/**		
 * <p>Title: ISupplierService </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 刘博	
 * @date 2015年12月3日 下午4:18:23	
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年12月3日 下午4:18:23</p>
 * <p>修改备注：</p>
 */
public interface ISupplierService
{

    
    /**     
     * findALL：一句话描述方法功能
     * @param supplierDto
     * @param phDto
     * @return
     * @exception   
     * @author 刘博
     * @date 2015年11月25日 下午1:26:51     
     */
    List<SupplierDto> findALL(SupplierDto supplierDto, PageHelperDto phDto);

    
    /**     
     * findCount：一句话描述方法功能
     * @param supplierDto 
     * @return
     * @exception   
     * @author 刘博
     * @date 2015年11月25日 下午3:10:17   
     */
    int findCount(SupplierDto supplierDto);


    
    /**     
     * add：一句话描述方法功能
     * @param supplierDto
     * @exception   
     * @author 刘博
     * @date 2015年11月25日 下午3:40:39   
     */
    void add(SupplierDto supplierDto);


    
    /**     
     * findById：一句话描述方法功能
     * @param id
     * @return
     * @exception   
     * @author 刘博
     * @date 2015年11月25日 下午3:49:25   
     */
    SupplierDto findById(String id);


    
    /**     
     * update：一句话描述方法功能
     * @param r
     * @exception   
     * @author 刘博
     * @date 2015年11月25日 下午3:49:29   
     */
    void update(SupplierDto r);


    
    /**     
     * delete：一句话描述方法功能
     * @param r
     * @exception   
     * @author 刘博 
     * @date 2015年11月25日 下午3:57:57   
     */
    void delete(SupplierDto r);


    
    /**     
     * findByLongitudeAndLatitude：一句话描述方法功能
     * @param longitude 精度
     * @param latitude  维度
     * @return
     * @exception   
     * @author 刘博
     * @date 2015年11月26日 下午12:01:12  
     */
    List<SupplierDto> findByLongitudeAndLatitude(String longitude, String latitude);

    
    /**
     * 
     * findBy：查询方法
     * @param supplierDto
     * @return
     * @exception   
     * @author zhuzhen
     * @date 2015年11月26日 下午2:16:41
     */
    List<SupplierDto> findBy(SupplierDto supplierDto);


    
    /**     
     * findByName：一句话描述方法功能
     * @param supplierName
     * @return
     * @exception	
     * @author 刘博
     * @date 2015年12月3日 下午5:36:57	 
     */
    SupplierDto findByName(String supplierName);
}

