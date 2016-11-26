/**
 * 
 */
package com.ssic.catering.base.service;

import java.util.List;

import com.ssic.catering.base.dto.CarteTypeDto;
import com.ssic.catering.base.dto.PageHelperDto;
import com.ssic.catering.base.pojo.CarteType;

/**     
 * <p>Title: ICarteTypeService </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator    
 * @date 2015年8月4日 上午9:18:04    
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年8月4日 上午9:18:04</p>
 * <p>修改备注：</p>
 */
public interface ICarteTypeService
{

    


    
    /**     
     * add：一句话描述方法功能
     * @param carteTypeDto
     * @exception   
     * @author Administrator
     * @date 2015年8月4日 上午11:09:46    
     */
    void add(CarteTypeDto carteTypeDto);

    
    /**     
     * findALL：一句话描述方法功能
     * @param carteTypeDto
     * @param phDto
     * @return
     * @exception   
     * @author Administrator
     * @date 2015年8月4日 上午11:19:41    
     */
    List<CarteTypeDto> findALL(CarteTypeDto carteTypeDto, PageHelperDto phDto);


    
    /**     
     * findCount：一句话描述方法功能
     * @param carteTypeDto
     * @return
     * @exception   
     * @author Administrator
     * @date 2015年8月4日 上午11:19:53    
     */
    int findCount(CarteTypeDto carteTypeDto);


    
    /**     
     * delete：一句话描述方法功能
     * @param tempCarteType
     * @exception   
     * @author Administrator
     * @date 2015年8月4日 上午11:23:16    
     */
    void delete(CarteTypeDto tempCarteType);


    
    /**     
     * findById：一句话描述方法功能
     * @param id
     * @return
     * @exception   
     * @author Administrator
     * @date 2015年8月4日 上午11:23:33    
     */
    CarteTypeDto findById(String id);


    
    /**     
     * update：一句话描述方法功能
     * @param carteTypeDto
     * @exception   
     * @author Administrator
     * @date 2015年8月4日 上午11:23:39    
     */
    void update(CarteTypeDto carteTypeDto);


    
    /**     
     * finByName：一句话描述方法功能
     * @param carteTypeName
     * @param cafetoriumId 
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年8月6日 下午1:03:07	 
     */
    CarteType finByName(String carteTypeName, String cafetoriumId);


    
    /**     
     * finByCafetorium：一句话描述方法功能
     * @param cafetoriumId
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年8月7日 上午10:09:49	 
     */
    List<CarteTypeDto> finByCafetoriumId(String cafetoriumId);
    
    /**
     * 	 
     * @author 朱振	
     * @date 2015年11月4日 上午10:50:15	
     * @version 1.0
     * @param carteTypeDto
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年11月4日 上午10:50:15</p>
     * <p>修改备注：</p>
     */
    int updateBy(CarteTypeDto carteTypeDto);

}

