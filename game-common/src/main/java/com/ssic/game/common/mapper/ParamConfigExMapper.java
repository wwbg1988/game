package com.ssic.game.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ssic.game.common.dto.KeyValueDto;
import com.ssic.game.common.dto.ParamConfigDto;
import com.ssic.game.common.pojo.ParamConfigExample;


public interface ParamConfigExMapper 
{
    /**
     * 将查询出来的结果按名值对的方式显示<BR>	 
     * @author 朱振	
     * @date 2015年10月28日 下午3:12:32	
     * @version 1.0
     * @param example
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年10月28日 下午3:12:32</p>
     * <p>修改备注：</p>
     */
    List<KeyValueDto<String>> findParametersBy(@Param("example")ParamConfigExample example);
    
    /**
     * 	 
     * @author 朱振	
     * @date 2015年11月16日 下午3:33:10	
     * @version 1.0
     * @param example
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年11月16日 下午3:33:10</p>
     * <p>修改备注：</p>
     */
    long findCountBy(@Param("example")ParamConfigExample example);
    
    
    /**
     * 多表查询	 
     * @author 朱振	
     * @date 2015年11月16日 下午4:25:53	
     * @version 1.0
     * @param example
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年11月16日 下午4:25:53</p>
     * <p>修改备注：</p>
     */
    List<ParamConfigDto> findParamConfigDtoBy(@Param("example")ParamConfigExample example);
}