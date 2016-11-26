package com.ssic.catering.base.service.impl;

import org.springframework.stereotype.Service;

import com.ssic.catering.base.service.ICreatePhdtoService;
import com.ssic.game.common.dto.PageHelperDto;

@Service
public class CreatePhdtoServiceImpl implements ICreatePhdtoService{

	@Override
	public PageHelperDto getNewPhDto(String order, int page, int rows,
			String sort) {
		// TODO Auto-generated method stub
		 PageHelperDto phdto = new PageHelperDto();
	        phdto.setOrder(order);
	        phdto.setPage(page);
	        phdto.setRows(rows);
	        phdto.setSort(sort);
	        phdto.setBeginRow((page - 1) * rows);
	        return phdto;
	}

}
