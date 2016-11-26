package com.ssic.catering.lbs.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ssic.catering.lbs.dto.TransportDriverDto;
import com.ssic.catering.lbs.pojo.TransportDriverExample;

/**
 * 		
 * <p>Title: TransportDriverExMapper </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author pengcheng.yang	
 * @date 2015年11月25日 下午1:32:48	
 * @version 1.0
 * <p>修改人：apple</p>
 * <p>修改时间：2015年11月25日 下午1:32:48</p>
 * <p>修改备注：</p>
 */
public interface TransportDriverExMapper {
	TransportDriverDto findUserInfo(@Param("username")String username,@Param("pwd")String pwd);
	
	/**
	 * 
	 * updateDriverState：表示用户是否有任务
	 * @param username
	 * @return
	 * @exception	
	 * @author pengcheng.yang
	 * @date 2015年11月26日 上午11:29:13
	 */
	int updateDriverState(@Param("driverId")String driverId,@Param("state")int state);
	
	
	/**
     * 
     * selectTransportDriverDtosBy：一句话描述方法功能
     * @param example
     * @return
     * @exception   
     * @author zhuzhen
     * @date 2015年11月26日 下午7:41:00
     */
    List<TransportDriverDto> selectTransportDriverDtosBy(@Param("example") TransportDriverExample example);
    
    /**
     * 
     * countTransportDriverDtoBy：一句话描述方法功能
     * @param example
     * @return
     * @exception   
     * @author zhuzhen
     * @date 2015年11月26日 下午8:12:09
     */
    long countTransportDriverDtoBy(@Param("example") TransportDriverExample example);
}