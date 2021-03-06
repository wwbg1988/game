package com.ssic.catering.base.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ssic.catering.base.dto.NextWeekCarteDtos;

public interface NextWeekCarteExMapper
{
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_next_week_carte
     *
     * @mbggenerated Thu Aug 06 14:31:54 CST 2015
     */
    int count();

    /**     
     * findNextWeekCarteList：重复菜单的数量
     * @param nextWeekCarteDto
     * @return
     * @exception	
     * @author yuanbin
     * @date 2015年8月6日 下午5:57:41	 
     */
    List<NextWeekCarteDtos> findNextWeekCarteList();

    /**     
     * findDistinctCarteWeekByCafeId：根据食堂id查询下周菜单对象集合
     * @param cafetoriumId
     * @return
     * @exception	
     * @author 刘博
     * @date 2015年8月10日 下午3:04:13	 
     */
    List<NextWeekCarteDtos> findDistinctCarteWeekByCafeId(@Param("cafetoriumId") String cafetoriumId);

    String findDishPercent(@Param("nextWeekCarteDto") NextWeekCarteDtos nextWeekCarteDto);

    List<NextWeekCarteDtos> findAllDistinctDish(@Param("nextWeekCarteDto") NextWeekCarteDtos nextWeekCarteDto);
}
