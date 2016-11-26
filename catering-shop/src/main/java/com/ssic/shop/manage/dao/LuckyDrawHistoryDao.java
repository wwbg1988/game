/**
 * 
 */
package com.ssic.shop.manage.dao;

import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.ssic.shop.manage.dto.LuckyDrawHistoryDto;
import com.ssic.shop.manage.mapper.LuckyDrawHistoryExMapper;
import com.ssic.shop.manage.mapper.LuckyDrawHistoryMapper;
import com.ssic.shop.manage.pojo.LuckyDrawHistory;
import com.ssic.shop.manage.pojo.LuckyDrawHistoryExample;
import com.ssic.shop.manage.pojo.LuckyDrawHistoryExample.Criteria;
import com.ssic.util.base.MyBatisBaseDao;
import com.ssic.util.constants.DataStatus;

/**		
 * <p>Title: LuckyDrawHistoryDao </p>
 * <p>Description: 微信用户历史抽奖记录dao层</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 刘博
 * @date 2015年9月22日 下午4:26:17	
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年9月22日 下午4:26:17</p>
 * <p>修改备注：</p>
 */
@Repository
public class LuckyDrawHistoryDao extends MyBatisBaseDao<LuckyDrawHistory>
{

    @Autowired
    @Getter
    private LuckyDrawHistoryMapper mapper;

    @Autowired
    private LuckyDrawHistoryExMapper exMapper;

    /**
     * 
     * findLuckDrawById：根据抽奖Id查询对应抽奖的的详细信息
     * @param id
     * @return
     * @exception   
     * @author 刘博
     * @date 2015年9月18日 上午11:42:29
     */
    public List<LuckyDrawHistory> findLuckDrawHistoryByOpenIdAndCafeId(String openId, String cafetoriumId)
    {
        LuckyDrawHistoryExample example = new LuckyDrawHistoryExample();
        Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty(openId))
        {
            criteria.andOpenIdEqualTo(openId);
        }
        if (!StringUtils.isEmpty(cafetoriumId))
        {
            criteria.andCafetoriumIdEqualTo(cafetoriumId);
        }
        criteria.andStatEqualTo(DataStatus.ENABLED);
        example.setOrderByClause("create_time desc");
        return mapper.selectByExample(example);
    }

    public void insertLuckyDrawHistory(LuckyDrawHistory param)
    {
        mapper.insertSelective(param);
    }

    public void upateLuckyDrawHistory(LuckyDrawHistory param)
    {
        mapper.updateByPrimaryKeySelective(param);
    }

    /**
     * 
     * queryLuckyDrawHoistoryInfo：根据openId查询用户当天的抽奖次数以及中奖次数
     * @param openId
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年10月13日 下午2:40:04
     */
    public LuckyDrawHistoryDto queryLuckyDrawHoistoryInfo(String openId)
    {
        return exMapper.queryLuckyDrawHoistoryInfo(openId);
    }

}
