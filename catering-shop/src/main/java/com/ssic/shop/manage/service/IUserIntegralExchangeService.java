package com.ssic.shop.manage.service;

import java.util.List;

import com.ssic.shop.manage.dto.PageHelperDto;
import com.ssic.shop.manage.dto.UserIntegralExchangeDto;

/**		
 * <p>Title: IUserIntegralExchangeService </p>
 * <p>Description: 商城用户兑换记录</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 朱振	
 * @date 2015年9月18日 下午3:30:24	
 * @version 1.0
 * <p>修改人：朱振</p>
 * <p>修改时间：2015年9月18日 下午3:30:24</p>
 * <p>修改备注：</p>
 */
public interface IUserIntegralExchangeService
{
    /**
     * 分页查询	 
     * @author 朱振	
     * @date 2015年9月18日 下午3:34:52	
     * @version 1.0
     * @param userIntegralExchange
     * @param ph
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年9月18日 下午3:34:52</p>
     * <p>修改备注：</p>
     */
    public List<UserIntegralExchangeDto> getListBy(UserIntegralExchangeDto userIntegralExchangeDto, PageHelperDto ph);
    
    /**
     * 同时更新个人积分和用户积分兑换表	 
     * @author 朱振	
     * @date 2015年9月18日 下午3:35:22	
     * @version 1.0
     * @param userIntegralExchangeDto
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年9月18日 下午3:35:22</p>
     * <p>修改备注：</p>
     */
    public int addUserIntegralExchange(UserIntegralExchangeDto userIntegralExchangeDto);
}

