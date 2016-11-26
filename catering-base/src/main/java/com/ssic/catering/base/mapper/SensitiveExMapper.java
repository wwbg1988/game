package com.ssic.catering.base.mapper;

import com.ssic.catering.base.pojo.Sensitive;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface SensitiveExMapper {
    List<Sensitive> querySersitive(@Param("cafetoriumId")String cafetoriumId);
}