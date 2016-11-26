/**
 * 
 */
package com.ssic.catering.base.service;

import java.util.List;

import com.ssic.catering.base.dto.AddressUserDto;
import com.ssic.catering.base.dto.PageHelperDto;
import com.ssic.game.common.dto.DataGridDto;

/**		
 * <p>Title: IAddressUserService </p>
 * <p>Description:  区域用户service接口层</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年8月8日 上午11:12:17	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年8月8日 上午11:12:17</p>
 * <p>修改备注：</p>
 */
public interface IAddressUserService {

	/**
	 * 
	 * getAddressUserByUserId：通过用户ID获取此用户负责的区域
	 * @param userId 用户Id
	 * @return 用户负责 
	 * @exception	
	 * @author 张亚伟
	 * @date 2015年8月8日 下午2:21:33
	 */
	AddressUserDto getAddressUserByUserId(String userId);

	/**     
	 * finAddressListByUserId：一句话描述方法功能
	 * @param userId
	 * @return
	 * @exception	
	 * @author Administrator
	 * @date 2015年8月8日 上午11:18:50	 
	 */
	List<AddressUserDto> finAddressListByUserId(String userId);

	/**
	 * 
	 * getAddressUserByID：通过主键ID获取区域负责人信息
	 * @param id
	 * @return
	 * @exception	
	 * @author 张亚伟
	 * @date 2015年8月10日 下午4:52:55
	 */
	public AddressUserDto findAddressUserByID(String id);

	/**     
	  * finAddressListByAddressId：通过区域ID获取区域负责人集合
	  * @param id
	  * @return
	  * @exception	
	  * @author 刘博
	  * @date 2015年8月11日 下午2:47:38	 
	  */
	List<AddressUserDto> finAddressListByAddressId(String id);

	/**
	 * finAddressUserByAddressId：通过区域ID获取区域负责人信息
	 * @param addressId 区域Id
	 * @return
	 * @exception	
	 * @author 张亚伟
	 * @date 2015年8月12日 上午10:10:50
	 */
	AddressUserDto finAddressUserByAddressId(String addressId);
	
	AddressUserDto finAddressUserByAddressId(String addressId,String projId,String addresstype);


	/**
	 * 
	 * finAddressUserByAddressIdAndCafeCode：通过区域ID和食堂编码获取食堂负责人信息
	 * @param addressId 区域ID
	 * @param cafeCode 食堂编码
	 * @return
	 * @exception	
	 * @author 张亚伟
	 * @date 2015年8月18日 下午2:58:19
	 */
	AddressUserDto finAddressUserByAddressIdAndCafeCode(String addressId,
			String cafeCode);


    
    /**     
     * insert：一句话描述方法功能
     * @param addressUserDto
     * @return
     * @exception	
     * @author 刘博
     * @date 2015年8月17日 下午1:24:14	 
     */
    void insert(AddressUserDto addressUserDto);

    
    /**     
     * findAll：一句话描述方法功能
     * @param addressUser
     * @return
     * @exception	
     * @author 刘博
     * @date 2015年8月17日 下午4:20:38	 
     */
    List<AddressUserDto> findAll(AddressUserDto addressUser);

    
    /**     
     * update：一句话描述方法功能
     * @param addressUserDto
     * @exception	
     * @author 刘博
     * @date 2015年8月17日 下午4:31:54	 
     */
    void update(AddressUserDto addressUserDto);
    
    DataGridDto findAllDataGrid(String addressId, PageHelperDto phDto,String projId);

    
    /**     
     * deleteByAddressIdAndCafeCode：通过区域id和食堂编码删除区域用户关系
     * @param addressId
     * @param cafeCode
     * @exception	
     * @author 刘博
     * @date 2015年8月26日 下午12:34:23	 
     */
    void deleteByAddressIdAndCafeCode(String addressId, String cafeCode);

	
	/**     
	 * finAddressUserByCafeCode：一句话描述方法功能
	 * @param cafeCode
	 * @return
	 * @exception	
	 * @author yuanbin
	 * @date 2015年8月26日 下午4:32:21	 
	 */
	AddressUserDto finAddressUserByCafeCode(String cafeCode);

    
    /**     
     * delete：删除
     * @param dto
     * @exception	
     * @author 刘博
     * @date 2015年11月11日 下午2:56:39	 
     */
    void delete(AddressUserDto dto);


}
