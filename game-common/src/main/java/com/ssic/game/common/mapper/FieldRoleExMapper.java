package com.ssic.game.common.mapper;

import org.apache.ibatis.annotations.Param;

import com.ssic.game.common.pojo.FieldRole;

public interface FieldRoleExMapper {
    
    void updateDel(@Param("fi")FieldRole fieldRole);
}