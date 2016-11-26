package com.ssic.game.app.controller.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

import com.ssic.catering.base.dto.CafetoriumDto;
import com.ssic.catering.base.dto.ConfigDto;

/**
 * 		
 * <p>Title: HistoryConfigScoreDto </p>
 * <p>Description: 用来存储食堂历史评分</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 张亚伟	
 * @date 2015年8月11日 上午11:50:15	
 * @version 1.0
 * <p>修改人：张亚伟</p>
 * <p>修改时间：2015年8月11日 上午11:50:15</p>
 * <p>修改备注：</p>
 */
public class HistoryConfigScoreDto {

	@Getter
	@Setter
	private String time;

	@Getter
	@Setter
	private List<ConfigDto> configList;

	@Getter
	@Setter
	private List<String> sensitiveList;

	/**
	 * 评论条数
	 */
	@Getter
	@Setter
	private Integer count;
	
	/**
	 * 食堂信息
	 */
	@Getter
	@Setter
	private CafetoriumDto cafetoriumDto;
	
	/**
	 * 食堂评分Json
	 */
	@Getter
	@Setter
	private ScoreReportDto scoreReportDto;
	
	//食堂当天的平均分
	@Getter
	@Setter
	private String DayScore;


}
