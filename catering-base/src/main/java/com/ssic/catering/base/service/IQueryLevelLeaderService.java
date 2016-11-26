/**
 * 
 */
package com.ssic.catering.base.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ssic.catering.base.dto.PushAddressUserDto;

/**		
 * <p>Title: QueryLevelLeaderService </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author pengcheng.yang	
 * @date 2015年8月11日 上午10:02:08	
 * @version 1.0
 * <p>修改人：pengcheng.yang</p>
 * <p>修改时间：2015年8月11日 上午10:02:08</p>
 * <p>修改备注：</p>
 */
public interface IQueryLevelLeaderService
{
    /**
     * 
     * queryCityManager：查询城市经理
     * @param cafetoriumId
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年8月11日 上午10:03:32
     */
    List<PushAddressUserDto> queryCityManager(String cafetoriumId);
    /**
     * 
     * queryProvincialManager：查询省市经理
     * @param cafetoriumId
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年8月11日 上午10:03:53
     */
    List<PushAddressUserDto> queryProvincialManager(String cafetoriumId);
    /**
     * 
     * queryRegionalManager：查询大区负责人
     * @param cafetoriumId
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年8月11日 上午10:04:12
     */
    List<PushAddressUserDto> queryRegionalManager(String cafetoriumId);
    /**
     * 
     * queryCompanyLeader：查询总公司负责人
     * @param cafetoriumId
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年8月11日 上午10:04:36
     */
    List<PushAddressUserDto> queryCompanyLeader(String cafetoriumId);
}

