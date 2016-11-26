package com.ssic.catering.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ssic.catering.admin.dto.TImsMenuTypeDto;
import com.ssic.catering.admin.pojo.MenuType;

public interface TImsMenuTypeExMapper {

	MenuType findById(@Param("menuTypeId") String menuTypeId);

	List<TImsMenuTypeDto> findAll();

}
