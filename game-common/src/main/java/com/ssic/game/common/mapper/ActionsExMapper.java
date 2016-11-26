package com.ssic.game.common.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ssic.game.common.pojo.Actions;

public interface ActionsExMapper {
    List<Map<Object,Object>> getAll(@Param("actions")Actions actions,@Param("beginRows")int beginRows,@Param("rows") int rows);
    List<Map<Object,Object>> getFinsh(@Param("actions")Actions actions,@Param("beginRows")int beginRows,@Param("rows") int rows);
    int getCount(@Param("actions")Actions actions);
    int getCountFinish(@Param("actions")Actions actions);
}