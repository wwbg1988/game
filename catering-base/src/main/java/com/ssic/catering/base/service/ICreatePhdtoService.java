package com.ssic.catering.base.service;

import com.ssic.game.common.dto.PageHelperDto;


public interface ICreatePhdtoService {

	public PageHelperDto getNewPhDto (String order,int page,int rows,String sort);
	
}
