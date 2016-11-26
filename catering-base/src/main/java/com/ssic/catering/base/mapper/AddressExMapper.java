/**
 * 
 */
package com.ssic.catering.base.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ssic.catering.base.dto.AddressDto;

/**		
 * <p>Title: AddressExMapper </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 刘博	
 * @date 2015年8月12日 下午1:22:13	
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年8月12日 下午1:22:13</p>
 * <p>修改备注：</p>
 */
public interface AddressExMapper
{

    
    /**
     * 
     * validAddressRootExists： 验证区域根节点是否存在
     * @param addressId
     * @return
     * @exception   
     * @author 刘博
     * @date 2015年8月11日 上午9:15:20
     */
    AddressDto validAddressRootExists(@Param("addressId")String addressId , @Param("projId")String projId);
    
    /**
     * 
     * validAddressRootExists： 验证区域根节点是否存在
     * @param addressId
     * @return
     * @exception   
     * @author 刘博
     * @date 2015年8月11日 上午9:15:20
     */
    List<AddressDto> findCodeByLastCreateTime();
    
    /**
     * 
     * queryAddressId：根据用户ID查询addressId
     * @param userId
     * @return
     * @exception   
     * @author pengcheng.yang
     * @date 2015年8月13日 下午12:20:48
     */
    AddressDto queryAddressId(@Param("userId")String userId);
    /**
     * 
     * queryAddressIdAndParentIds：更具addressId查询下面的子Id
     * @param parentId
     * @return
     * @exception   
     * @author Administrator
     * @date 2015年8月13日 下午12:23:24
     */
    List<AddressDto> queryAddressIdAndParentIds(@Param("parentId")String parentId);
    
    /**
     * 
     * queryCityId：查询城市Id
     * @param parentId
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年8月13日 下午5:38:52
     */
    List<AddressDto> queryCityId(@Param("cityId")String cityId);
    
    

    
}

