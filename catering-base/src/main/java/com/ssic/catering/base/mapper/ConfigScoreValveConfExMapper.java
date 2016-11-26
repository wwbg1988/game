package com.ssic.catering.base.mapper;

import org.apache.ibatis.annotations.Param;

import com.ssic.catering.base.dto.ConfigScoreValveConfDto;

/**
 * 		
 * <p>Title: ConfigScoreValveConfExMapper </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author pengcheng.yang	
 * @date 2015年8月12日 下午3:43:20	
 * @version 1.0
 * <p>修改人：pengcheng.yang</p>
 * <p>修改时间：2015年8月12日 下午3:43:20</p>
 * <p>修改备注：</p>
 */
public interface ConfigScoreValveConfExMapper {
    int insertConfScoreValue(@Param("configScore")ConfigScoreValveConfDto Dto);
    int updateConfScoreValue(@Param("configScore")ConfigScoreValveConfDto Dto);
    int deleteConfScoreValue(@Param("configScore")ConfigScoreValveConfDto Dto);
}