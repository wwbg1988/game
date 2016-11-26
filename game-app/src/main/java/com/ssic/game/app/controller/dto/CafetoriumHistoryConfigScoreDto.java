package com.ssic.game.app.controller.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

import com.ssic.catering.base.dto.CafetoriumDto;
import com.ssic.catering.base.pojo.Config;

public class CafetoriumHistoryConfigScoreDto {

	@Getter
	@Setter
	private CafetoriumDto cafetoriumDto;

	@Getter
	@Setter
	private List<Config> configList;

	@Getter
	@Setter
	private List<HistoryConfigScoreDto> historyConfigScoreDtoList;

}
