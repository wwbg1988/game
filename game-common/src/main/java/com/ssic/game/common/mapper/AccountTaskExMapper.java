package com.ssic.game.common.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface AccountTaskExMapper {
    List<Map<Object,Object>> getAll();
    List<Map<Object,Object>>getAngncy(@Param("userId")String userId,@Param("projId")String projId,@Param("procId")String procId,@Param("procInstId")String procInstId);
    List<Map<Object,Object>>getAngncyNotProcess(@Param("userId")String userId,@Param("projId")String projId,@Param("beginRows")int beginRows,@Param("endRows")int endRows,@Param("searchDate")String searchDate);
    
}