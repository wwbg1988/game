package com.ssic.catering.base.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ssic.catering.base.dto.CarteDto;

public interface CarteExMapper {
    
    /**
     * 	 
     * @author 朱振	
     * @date 2015年10月10日 上午11:19:32	
     * @version 1.0
     * @param carte
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年10月10日 上午11:19:32</p>
     * <p>修改备注：</p>
     */
    List<CarteDto> selectMenuByCafetoriumId(@Param("cafetoriumId")String cafetoriumId);
}