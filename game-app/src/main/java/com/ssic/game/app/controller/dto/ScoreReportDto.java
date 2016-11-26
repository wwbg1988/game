package com.ssic.game.app.controller.dto;

import com.alibaba.fastjson.JSON;
import com.ssic.catering.base.dto.AddressUserDto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class ScoreReportDto {

	@Getter
	@Setter
	private JSON datas;
	
	@Getter
    @Setter
    private JSON data;
	
	@Getter
	@Setter
	private AddressUserDto addressUser;
}

