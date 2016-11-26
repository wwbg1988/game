/**
 * 
 */
package com.ssic.shop.manage.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.ssic.util.BeanUtils;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssic.shop.manage.dao.LuckyDrawHistoryDao;
import com.ssic.shop.manage.dto.GoodsDto;
import com.ssic.shop.manage.dto.LuckyDrawHistoryDto;
import com.ssic.shop.manage.pojo.LuckyDrawHistory;
import com.ssic.shop.manage.service.LuckyDrawHistoryService;

/**		
 * <p>Title: LuckyDrawHistoryServiceImpl </p>
 * <p>Description: 微信用户抽奖历史记录Service层实现类</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 刘博	
 * @date 2015年9月22日 下午4:27:26	
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年9月22日 下午4:27:26</p>
 * <p>修改备注：</p>
 */
@Service
public class LuckyDrawHistoryServiceImpl implements LuckyDrawHistoryService
{

    @Autowired
    private LuckyDrawHistoryDao luckyDrawHistoryDao;

    /** 
    * (non-Javadoc)   
    * @see com.ssic.shop.manage.service.LuckyDrawHistoryService#findLuckDrawHistoryByOpenId(java.lang.String)   
    */
    @Override
    public List<LuckyDrawHistoryDto> findLuckDrawHistoryByOpenIdAndCafeId(String openId,String cafetoriumId)
    {
        List<LuckyDrawHistoryDto> historyDtoList = new ArrayList<LuckyDrawHistoryDto>();
        List<LuckyDrawHistory> historyList = luckyDrawHistoryDao.findLuckDrawHistoryByOpenIdAndCafeId(openId,cafetoriumId);
        if (!CollectionUtils.isEmpty(historyList))
        {
            historyDtoList = BeanUtils.createBeanListByTarget(historyList, LuckyDrawHistoryDto.class);
        }
        return historyDtoList;
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.shop.manage.service.LuckyDrawHistoryService#insertLuckyDrawHistory(com.ssic.shop.manage.pojo.LuckyDrawHistory)   
    */
    @Override
    public void insertLuckyDrawHistory(LuckyDrawHistoryDto param)
    {
        LuckyDrawHistory history = new LuckyDrawHistory();
        BeanUtils.copyProperties(param, history);
        luckyDrawHistoryDao.insert(history);
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.shop.manage.service.LuckyDrawHistoryService#upateLuckyDrawHistory(com.ssic.shop.manage.pojo.LuckyDrawHistory)   
    */
    @Override
    public void upateLuckyDrawHistory(LuckyDrawHistoryDto param)
    {
        LuckyDrawHistory history = new LuckyDrawHistory();
        BeanUtils.copyProperties(param, history);
        luckyDrawHistoryDao.upateLuckyDrawHistory(history);

    }

    /**
     * 
     * queryLuckyDrawHoistoryInfo：根据openId查询用户当天的抽奖次数以及中奖次数
     * @param openId
     * @return
     * @exception   
     * @author pengcheng.yang
     * @date 2015年10月13日 下午2:43:10
     */
    @Override
    public LuckyDrawHistoryDto queryLuckyDrawHoistoryInfo(String openId)
    {
        return luckyDrawHistoryDao.queryLuckyDrawHoistoryInfo(openId);
    }

}
