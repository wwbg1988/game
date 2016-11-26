package com.ssic.game.app.controller.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

import com.ssic.catering.base.dto.CafetoriumDto;
import com.ssic.catering.base.pojo.Config;

public class CafetoriumConfigScoreReportDto {

	/**
	 * 食堂信息
	 */
	@Getter
	@Setter
	private CafetoriumDto cafetoriumDto;

	/**
	 * 评分服务项
	 */
	@Getter
	@Setter
	private List<Config> configList;

	/**
	 * 评分信息
	 */
	@Getter
	@Setter
	private HistoryConfigScoreDto historyConfigScoreDto;
}
