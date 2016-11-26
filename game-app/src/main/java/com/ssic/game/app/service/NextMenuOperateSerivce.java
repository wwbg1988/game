package com.ssic.game.app.service;

import java.util.List;

import com.ssic.catering.base.dto.NextWeekCarteDto;
import com.ssic.catering.base.dto.NextWeekCarteDtos;
import com.ssic.catering.base.dto.PageHelperDto;
import com.ssic.catering.base.pojo.NextWeekCarte;

/**		
 * <p>Title: NextMenuOperateSerivce </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author yuanbin
 * @date 2015年8月4日 下午13:37:00	
 * @version 1.0
 */

public interface NextMenuOperateSerivce
{

    /**     
     * queryProjectInfo：查询项目信息
     * @param userId
     * @return  
     * @exception	
     * @author yuanbin
     * @date 2015年8月5日 下午1:32:58	 
     */
    List<NextWeekCarteDto> findAll(NextWeekCarteDto nextWeekCarteDto, PageHelperDto phDto);

    /**         
     * findNextMenu：查找重复菜单的数量
     * @param query
     * @return
     * @exception	
     * @author yuanbin
     * @date 2015年8月6日 下午3:58:34	  
     */

    List<NextWeekCarteDtos> findNextWeekCarteList();

    /**         
     * findNextMenu：查找菜单总的数量
     * @param query
     * @return
     * @exception	
     * @author yuanbin
     * @date 2015年8月6日 下午3:58:34	  
     */

    int count();

    int insertNextWeekCarte(NextWeekCarte nextWeekCarte);

    /**     
     * findDistinctCarteWeekByCafeId：一句话描述方法功能
     * @param id
     * @exception	
     * @author Administrator
     * @date 2015年8月10日 下午1:29:15	 
     */
    List<NextWeekCarteDtos> findDistinctCarteWeekByCafeId(String id);

    String findDishPercent(NextWeekCarteDtos dtos);

    List<NextWeekCarteDtos> findAllDistinctDish(NextWeekCarteDtos nextWeekCarteDtos);

    /**
     * 
     * findNextWeekCarteByCarteWeek：按照周数获取本周的评价
     * @param localWeek 周数
     * @return
     * @exception	
     * @author 张亚伟
     * @date 2015年8月17日 上午11:23:56
     */
	List<NextWeekCarte> findNextWeekCarteByCarteWeek(int localWeek);

	
	/**
     * findNextWeekCarteByCarteWeek：按照周数和用户的唯一标识获取本周的评价
     * @param localWeek 周数
     * @param userId 微信用户的OpenId
     * @return
     * @exception	
     * @author 张亚伟
     * @date 2015年8月17日 上午11:23:56
     */
	List<NextWeekCarte> findNextWeekCarteByCarteWeekAndOpenId(int localWeek,
			String userId);

	/**
     * findNextWeekCarteByCarteWeekAndOpenIdAndCafetoriumId：按照周数和用户的唯一标识获取食堂的本周的评价
     * @param localWeek 周数
     * @param userId 微信用户的OpenId
     * @param cafetoriumId 食堂Id
     * @return
     * @exception	
     * @author 张亚伟
     * @date 2015年8月17日 上午11:23:56
     */
	List<NextWeekCarte> findNextWeekCarteByCarteWeekAndOpenIdAndCafetoriumId(
			int localWeek, String userId, String cafetoriumId);

	/**
	 * findNextWeekVoteByLocalWeekAndCarteNameAndCafetoriumId：获取菜单投票数
	 * @param localWeek 周数
	 * @param carteName 菜品名称
	 * @param cafetoriumId 食堂ID
	 * @return
	 * @exception	
	 * @author 张亚伟
	 * @date 2015年9月16日 上午10:14:36
	 */
	Integer findNextWeekVoteByLocalWeekAndCarteNameAndCafetoriumId(
			int localWeek, String carteName, String cafetoriumId);

	



}
