/**
 * 
 */
package com.ssic.catering.base.service;

import java.util.List;

import com.ssic.game.common.dto.PageHelperDto;
import com.ssic.catering.lbs.dto.TransportDestDto;

/**		
 * <p>Title: ITransportDestService </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 刘博	
 * @date 2015年11月25日 下午1:13:49	
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年11月25日 下午1:13:49</p>
 * <p>修改备注：</p>
 */
public interface ITransportDestService
{

    /**     
     * findALL：一句话描述方法功能
     * @param transportDestDto
     * @param phDto
     * @return
     * @exception   
     * @author 刘博
     * @date 2015年11月25日 下午1:26:51     
     */
    List<TransportDestDto> findALL(TransportDestDto transportDestDto, PageHelperDto phDto);

    
    /**     
     * findCount：一句话描述方法功能
     * @param transportDestDto 
     * @return
     * @exception	
     * @author 刘博
     * @date 2015年11月25日 下午3:10:17	 
     */
    int findCount(TransportDestDto transportDestDto);


    
    /**     
     * add：一句话描述方法功能
     * @param transportDestDto
     * @exception	
     * @author 刘博
     * @date 2015年11月25日 下午3:40:39	 
     */
    void add(TransportDestDto transportDestDto);


    
    /**     
     * findById：一句话描述方法功能
     * @param id
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年11月25日 下午3:49:25	 
     */
    TransportDestDto findById(String id);


    
    /**     
     * update：一句话描述方法功能
     * @param r
     * @exception	
     * @author Administrator
     * @date 2015年11月25日 下午3:49:29	 
     */
    void update(TransportDestDto r);


    
    /**     
     * delete：一句话描述方法功能
     * @param r
     * @exception	
     * @author 刘博 
     * @date 2015年11月25日 下午3:57:57	 
     */
    void delete(TransportDestDto r);


    
    /**     
     * findByLongitudeAndLatitude：一句话描述方法功能
     * @param longitude 精度
     * @param latitude  维度
     * @return
     * @exception	
     * @author 刘博
     * @date 2015年11月26日 下午12:01:12	 
     */
    List<TransportDestDto> findByLongitudeAndLatitude(String longitude, String latitude);

    
    /**
     * 
     * findBy：查询方法
     * @param transportDestDto
     * @return
     * @exception   
     * @author zhuzhen
     * @date 2015年11月26日 下午2:16:41
     */
    List<TransportDestDto> findBy(TransportDestDto transportDestDto);
}
