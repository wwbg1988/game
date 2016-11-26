/**
 * 
 */
package com.ssic.shop.manage.service;

import java.util.List;

import com.ssic.shop.manage.dto.LuckyDrawDto;

/**		
 * <p>Title: LuckDrawService </p>
 * <p>Description: 转盘抽奖Service层</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 刘博	
 * @date 2015年9月21日 下午2:45:16	
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年9月21日 下午2:45:16</p>
 * <p>修改备注：</p>
 */
public interface LuckDrawService
{

    public LuckyDrawDto findLuckDrawById(String id);

    /**     
     * updateLuckDraw：更新抽奖兑换表
     * @param luckDrawDto
     * @exception	
     * @author 刘博
     * @date 2015年10月13日 下午12:28:16	 
     */
    public void updateLuckDraw(LuckyDrawDto luckDrawDto);

    /**     
     * findByCafetoriumId：一句话描述方法功能
     * @param cafetoriumId
     * @return
     * @exception	
     * @author 刘博
     * @date 2015年10月23日 上午10:31:48	 
     */
    public List<LuckyDrawDto> findByCafetoriumId(String cafetoriumId);

}
