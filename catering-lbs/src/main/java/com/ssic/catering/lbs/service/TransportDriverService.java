package com.ssic.catering.lbs.service;

import java.util.List;

import com.ssic.catering.lbs.dto.TransportDriverDto;
import com.ssic.game.common.dto.PageHelperDto;
/**
 * 		
 * <p>Title: TransportDriverService </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author apple	
 * @date 2015年11月25日 上午11:36:50	
 * @version 1.0
 * <p>修改人：apple</p>
 * <p>修改时间：2015年11月25日 上午11:36:50</p>
 * <p>修改备注：</p>
 */
public interface TransportDriverService {
	/**
	 * 
	 * findUserNameAndPwd：验证用户登录信息是否正确
	 * @param username
	 * @param pwd
	 * @return
	 * @exception	
	 * @author pengcheng.yang
	 * @date 2015年11月25日 上午11:37:01
	 */
	public TransportDriverDto findUserNameAndPwd(String username,String pwd);
	
	/**
	 * 
	 * updateDriverState：标识用户是否有任务
	 * @param username
	 * @return
	 * @exception	
	 * @author pengcheng.yang
	 * @date 2015年11月26日 上午11:32:02
	 */
	public int updateDriverState(String username,int state);
	
	/**
     * 
     * findTransportDiverDtosBy：根据条件查询
     * @param transportDriverDto
     * @param ph
     * @return
     * @exception   
     * @author zhuzhen
     * @date 2015年11月26日 下午7:15:42
     */
    public List<TransportDriverDto> findTransportDriverDtosBy(TransportDriverDto transportDriverDto, PageHelperDto ph);
    
    /**
     * 
     * findTransportDiverDtoCountBy：一句话描述方法功能
     * @param transportDriverDto
     * @return
     * @exception   
     * @author zhuzhen
     * @date 2015年11月26日 下午8:10:27
     */
    public long findTransportDriverDtoCountBy(TransportDriverDto transportDriverDto);

    /**
     * 
     * addTransportDiver：一句话描述方法功能
     * @param transportDiverDto
     * @return
     * @exception   
     * @author zhuzhen
     * @date 2015年11月26日 下午8:22:53
     */
    int addTransportDiver(TransportDriverDto transportDriverDto);
    
   /**
    * 
    * updateTransportDiver：一句话描述方法功能
    * @param transportDriverDto
    * @return
    * @exception	
    * @author zhuzhen
    * @date 2015年11月30日 下午1:49:32
    */
    int updateTransportDiver(TransportDriverDto transportDriverDto);
    
   /**
    * 
    * findTransportDiverById：一句话描述方法功能
    * @param id
    * @return
    * @exception	
    * @author zhuzhen
    * @date 2015年11月30日 下午1:55:50
    */
    TransportDriverDto findTransportDiverById(String id);
    
    /**
     * 
     * findTransportDiverByAccount：一句话描述方法功能
     * @param account
     * @return
     * @exception	
     * @author zhuzhen
     * @date 2015年11月30日 下午2:19:49
     */
    TransportDriverDto findTransportDiverByAccount(String account);
}

