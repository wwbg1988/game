package com.ssic.catering.base.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ssic.catering.base.dto.ConfigScoreDto;
import com.ssic.catering.base.pojo.Cafetorium;
import com.ssic.catering.base.pojo.ConfigScore;

/**
 * 		
 * <p>Title: ConfigScoreExMapper </p>
 * <p>Description: 计算评论平均分接口</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年8月8日 上午9:56:17	
 * @version 1.0
 * <p>修改人：pengcheng.yang</p>
 * <p>修改时间：2015年8月8日 上午9:56:17</p>
 * <p>修改备注：</p>
 */
public interface ConfigScoreExMapper {
	List<ConfigScoreDto> CountScore(@Param("cafetoriumId") String cafetoriumId);

	public Integer findScoreByCafetoriumAndCreateTime(
			@Param("cafetoriumList") List<Cafetorium> cafetoriumList,
			@Param("createTime") Date createTime);

	public Integer findCountByCafetoriumAndCreateTime(
			@Param("cafetoriumList") List<Cafetorium> cafetoriumList,
			@Param("createTime") Date createTime);

	//获取食堂历史评分
	
	public Integer findHistoryCountByCafetorium(@Param("cafetoriumList") List<Cafetorium> cafetoriumList);
	
	public Integer findHistoryScoreByCafetorium(@Param("cafetoriumList") List<Cafetorium> cafetoriumList);
	
	
	/**
	 * findCafetoriumScoreByCafetoriumId：通过食堂Id获取食堂总分
	 * @param cafetoriumId 食堂Id
	 * @param createTime 创建时间
	 * @return
	 * @exception	
	 * @author 张亚伟
	 * @date 2015年8月11日 上午9:41:35
	 */
	public Integer findCafetoriumScoreByCafetoriumId(
			@Param("cafetoriumId") String cafetoriumId,
			@Param("createTime") Date createTime);

	/**
	 * findCafetoriumCountByCafetoriumId：通过食堂Id获取食堂评分条数
	 * @param cafetoriumId 食堂Id
	 * @param createTime 创建时间
	 * @return
	 * @exception	
	 * @author 张亚伟
	 * @date 2015年8月11日 上午9:41:35
	 */
	public Integer findCafetoriumCountByCafetoriumId(
			@Param("cafetoriumId") String cafetoriumId,
			@Param("createTime") Date createTime);

	
	/**
	 * findCreateTimeDistinct：通过食堂ID获取评分日期(去重)
	 * @param cafetoriumId 食堂ID
	 * @param index 第几页
	 * @param size 每页多少条信息
	 * @return
	 * @exception	
	 * @author 张亚伟
	 * @date 2015年8月11日 下午1:08:19
	 */
	public List<String> findCreateTimeDistinct(@Param("cafetoriumId")String cafetoriumId,
			@Param("index")Integer index, @Param("size")Integer size);

	
	/**
	 * findConfigScoreToCreateTime：通过时间获取食堂评分信息
	 * @param cafetoriumId 食堂ID
	 * @param time 时间
	 * @return
	 * @exception	
	 * @author 张亚伟
	 * @date 2015年8月11日 下午3:12:19
	 */
	List<ConfigScore> findConfigScoreListToCafetoriumIdByCreateTime(
			@Param("cafetoriumId")String cafetoriumId, @Param("time")String time);	
}