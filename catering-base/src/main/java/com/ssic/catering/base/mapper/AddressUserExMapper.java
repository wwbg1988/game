package com.ssic.catering.base.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ssic.catering.base.dto.AddressUserDto;
import com.ssic.catering.base.dto.PushAddressUserDto;
/**
 * 		
 * <p>Title: AddressUserExMapper </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author pengcheng.yang	
 * @date 2015年8月11日 上午9:16:18	
 * @version 1.0
 * <p>修改人：pengcheng.yang</p>
 * <p>修改时间：2015年8月11日 上午9:16:18</p>
 * <p>修改备注：</p>
 */
public interface AddressUserExMapper {
    
    /**
     * 
     * queryCityManager：查询城市经理
     * @param cafetoriumId
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年8月11日 上午9:15:20
     */
    List<PushAddressUserDto> queryCityManager(@Param("cafetoriumId")String cafetoriumId);
    /**
     * 
     * queryCityManager：查询省市经理
     * @param cafetoriumId
     * @return
     * @exception   
     * @author pengcheng.yang
     * @date 2015年8月11日 上午9:15:20
     */
    List<PushAddressUserDto> queryProvincialManager(@Param("cafetoriumId")String cafetoriumId);
    /**
     * 
     * queryCityManager：查询大区经理
     * @param cafetoriumId
     * @return
     * @exception   
     * @author pengcheng.yang
     * @date 2015年8月11日 上午9:15:20
     */
    List<PushAddressUserDto> queryRegionalManager(@Param("cafetoriumId")String cafetoriumId);
    /**
     * 
     * queryCityManager：总公司负责人
     * @param cafetoriumId
     * @return
     * @exception   
     * @author pengcheng.yang
     * @date 2015年8月11日 上午9:15:20
     */
    List<PushAddressUserDto> queryCompanyLeader(@Param("cafetoriumId")String cafetoriumId);
	
	/**     
	 * finAddressUserByCafeCode：一句话描述方法功能
	 * @param cafeCode
	 * @return
	 * @exception	
	 * @author yuanbin
	 * @date 2015年8月26日 下午4:37:08	 
	 */
	AddressUserDto finAddressUserByCafeCode(@Param("cafeCode")String cafeCode);

}