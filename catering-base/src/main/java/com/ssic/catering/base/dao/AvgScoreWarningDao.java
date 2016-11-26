package com.ssic.catering.base.dao;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssic.catering.base.dto.AvgScoreWarningDto;
import com.ssic.catering.base.mapper.AvgScoreWarningMapper;
import com.ssic.catering.base.pojo.AvgScoreWarning;
import com.ssic.util.BeanUtils;
import com.ssic.util.UUIDGenerator;

/**
 * 		
 * <p>Title: AvgScoreWarningDao </p>
 * <p>Description: 查询每个餐厅平均分预警消息</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年8月10日 下午3:24:21	
 * @version 1.0
 * <p>修改人：pengcheng.yang</p>
 * <p>修改时间：2015年8月10日 下午3:24:21</p>
 * <p>修改备注：</p>
 */
@Repository
public class AvgScoreWarningDao
{
    @Autowired
    private AvgScoreWarningMapper mapper;
    
    public void inserAvgScoreWarnInfo(AvgScoreWarningDto avgs){
        avgs.setCreateTime(new Date());
        avgs.setStat(1);
        avgs.setId(UUIDGenerator.getUUID());
        AvgScoreWarning result = new AvgScoreWarning();
        BeanUtils.copyProperties(avgs, result);
        mapper.insertSelective(result);
    }
    
}

