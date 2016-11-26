package com.ssic.shop.manage.mapper;

import org.apache.ibatis.annotations.Param;

import com.ssic.shop.manage.dto.LuckyDrawHistoryDto;

public interface LuckyDrawHistoryExMapper {
    
    /**
     * 
     * queryLuckyDrawHoistoryInfo:根据openId查询用户当天的抽奖次数以及中奖次数
     * @param openId
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年10月13日 下午2:32:16
     */
    LuckyDrawHistoryDto queryLuckyDrawHoistoryInfo(@Param("openId")String openId);
}