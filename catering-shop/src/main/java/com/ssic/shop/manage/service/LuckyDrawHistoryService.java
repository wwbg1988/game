/**
 * 
 */
package com.ssic.shop.manage.service;

import java.util.List;

import com.ssic.shop.manage.dto.LuckyDrawHistoryDto;

/**		
 * <p>Title: LuckyDrawHistoryService </p>
 * <p>Description: 微信用户历史抽奖记录Service层</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 刘博	
 * @date 2015年9月22日 下午4:27:09	
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年9月22日 下午4:27:09</p>
 * <p>修改备注：</p>
 */
public interface LuckyDrawHistoryService
{
    /**
     * 
     * findLuckDrawHistoryByOpenId：根据微信用户唯一标识id和食堂id查询抽奖记录
     * @param cafetoriumId 
     * @param id
     * @return
     * @exception   
     * @author 刘博
     * @date 2015年9月18日 上午11:42:29
     */
    public List<LuckyDrawHistoryDto> findLuckDrawHistoryByOpenIdAndCafeId(String openId, String cafetoriumId);

    public void insertLuckyDrawHistory(LuckyDrawHistoryDto param);

    public void upateLuckyDrawHistory(LuckyDrawHistoryDto param);

    /**
     * 
     * queryLuckyDrawHoistoryInfo：根据openId查询用户当天的抽奖次数以及中奖次数
     * @param openId
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年10月13日 下午2:43:08
     */
    public LuckyDrawHistoryDto queryLuckyDrawHoistoryInfo(String openId);
}
