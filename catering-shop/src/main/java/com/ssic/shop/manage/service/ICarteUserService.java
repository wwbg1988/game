/**
 * 
 */
package com.ssic.shop.manage.service;

import java.util.List;

import com.ssic.shop.manage.dto.PageHelperDto;
import com.ssic.shop.manage.dto.CarteUserDto;

/**		
 * <p>Title: ICarteUserService </p>
 * <p>Description: 商城用户Service层</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 刘博	
 * @date 2015年8月27日 上午10:13:17	
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年8月27日 上午10:13:17</p>
 * <p>修改备注：</p>
 */
public interface ICarteUserService
{
    public List<CarteUserDto> findAllBy(CarteUserDto param, PageHelperDto ph);

    
    /**     
     * findCount：一句话描述方法功能
     * @param carteUserDto
     * @return
     * @exception	
     * @author 刘博
     * @date 2015年8月27日 上午10:38:51	 
     */
    public int findCount(CarteUserDto carteUserDto);
    
    /**
     * 
     * findByWxUniqueIdAndCafetoriumId：有且只有一个
     * @param wxUniqueId
     * @param cafetoriumId
     * @return
     * @exception   
     * @author 朱振
     * @date 2015年8月27日 上午10:37:32
     */
    public CarteUserDto findByWxUniqueIdAndCafetoriumId(String wxUniqueId, String cafetoriumId);

    /** 
     * insertCarteUser：记录个人的生日信息
     * @param carteUserDto
     * @return
     * @exception   
     * @author 朱振
     * @date 2015年8月27日 上午10:29:37
     */
    public int insertCarteUser(CarteUserDto carteUserDto);
    
    
    /**
     * 	 
     * @author 朱振	
     * @date 2015年9月21日 上午10:15:13	
     * @version 1.0
     * @param carteUserDto
     * @param param
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年9月21日 上午10:15:13</p>
     * <p>修改备注：</p>
     */
    public int updateCarteUserBy(CarteUserDto carteUserDto, CarteUserDto param);
    
    
    /**
     * 	 
     * @author 朱振	
     * @date 2015年9月21日 上午10:16:17	
     * @version 1.0
     * @param carteUserDto
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年9月21日 上午10:16:17</p>
     * <p>修改备注：</p>
     */
    public int updateCarteUser(CarteUserDto carteUserDto);
}

