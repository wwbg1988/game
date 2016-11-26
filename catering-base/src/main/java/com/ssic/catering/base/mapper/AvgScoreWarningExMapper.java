package com.ssic.catering.base.mapper;

import org.apache.ibatis.annotations.Param;

/**
 * 		
 * <p>Title: AvgScoreWarningExMapper </p>
 * <p>Description: 统计每个餐厅当天有没有预警消息</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年8月10日 下午2:45:31	
 * @version 1.0
 * <p>修改人：pengcheng.yang</p>
 * <p>修改时间：2015年8月10日 下午2:45:31</p>
 * <p>修改备注：</p>
 */
public interface AvgScoreWarningExMapper {
    int totalWarningMessages(@Param("cafetoriumId")String cafetoriumId);
}