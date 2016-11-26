package com.ssic.shop.manage.service;

import java.util.List;

import com.ssic.shop.manage.dto.IntegralExchangeDto;
import com.ssic.shop.manage.dto.PageHelperDto;

/**		
 * <p>Title: IIntegralExchangeService </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 朱振	
 * @date 2015年9月18日 上午11:59:46	
 * @version 1.0
 * <p>修改人：朱振</p>
 * <p>修改时间：2015年9月18日 上午11:59:46</p>
 * <p>修改备注：</p>
 */
public interface IIntegralExchangeService
{
    /**
     * 分页查询	 
     * @author 朱振	
     * @date 2015年9月18日 下午1:26:53	
     * @version 1.0
     * @param integralExchangeDto
     * @param ph
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年9月18日 下午1:26:53</p>
     * <p>修改备注：</p>
     */
    public List<IntegralExchangeDto> getListBy(IntegralExchangeDto integralExchangeDto, PageHelperDto ph);
    
    /**
     * 	 
     * @author 朱振	
     * @date 2015年9月21日 上午11:42:48	
     * @version 1.0
     * @param ph
     * beginRow 开始  >=0
     * rows 页面大小 >0
     * sort 排序字段
     * order 升序（asc）/降序(desc)
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年9月21日 上午11:42:48</p>
     * <p>修改备注：</p>
     */
    public List<IntegralExchangeDto> getListBy(PageHelperDto ph);
    
    /**
     * 保存
     * @author 朱振	
     * @date 2015年9月18日 下午1:26:57	
     * @version 1.0
     * @param integralExchangeDto
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年9月18日 下午1:26:57</p>
     * <p>修改备注：</p>
     */
    public int addIntegralExchange(IntegralExchangeDto integralExchangeDto);
    
    
    /**
     * 查询所有的	 
     * @author 朱振	
     * @date 2015年10月9日 上午9:40:27	
     * @version 1.0
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年10月9日 上午9:40:27</p>
     * <p>修改备注：</p>
     */
    public List<IntegralExchangeDto> getList();
    
    /**
     * 通过食堂id查询积分兑换类型，再通过积分兑换类型查询积分物品	 
     * @author 朱振	
     * @date 2015年10月20日 下午4:18:37	
     * @version 1.0
     * @param cafetoriumId
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年10月20日 下午4:18:37</p>
     * <p>修改备注：</p>
     */
    public List<IntegralExchangeDto> getIntegralExchangeByCafetoriumId(String cafetoriumId);
}

