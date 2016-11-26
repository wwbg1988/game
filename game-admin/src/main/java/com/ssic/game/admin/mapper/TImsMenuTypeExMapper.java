package com.ssic.game.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ssic.game.admin.dto.TImsMenuTypeDto;
import com.ssic.game.admin.pojo.MenuType;

public interface TImsMenuTypeExMapper {

	MenuType findById(@Param("menuTypeId") String menuTypeId);

	List<TImsMenuTypeDto> findAll();

}
