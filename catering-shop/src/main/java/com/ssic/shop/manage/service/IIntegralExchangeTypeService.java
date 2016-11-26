package com.ssic.shop.manage.service;

import java.util.List;

import com.ssic.shop.manage.dto.IntegralExchangeTypeDto;

/**		
 * <p>Title: IIntegralExchangeTypeService </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 朱振	
 * @date 2015年9月22日 上午9:08:36	
 * @version 1.0
 * <p>修改人：朱振</p>
 * <p>修改时间：2015年9月22日 上午9:08:36</p>
 * <p>修改备注：</p>
 */
public interface IIntegralExchangeTypeService
{
    /**
     * 查询
     * @author 朱振	
     * @date 2015年9月22日 上午9:09:30	
     * @version 1.0
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年9月22日 上午9:09:30</p>
     * <p>修改备注：</p>
     */
    public List<IntegralExchangeTypeDto> getList();
    
    /**
     * 查询byId	 
     * @author 朱振	
     * @date 2015年9月22日 上午11:27:08	
     * @version 1.0
     * @param id
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年9月22日 上午11:27:08</p>
     * <p>修改备注：</p>
     */
    public IntegralExchangeTypeDto findIntegralExchangeById(String id);
    
    
    /**
     * 保存	 
     * @author 朱振	
     * @date 2015年9月22日 上午9:15:28	
     * @version 1.0
     * @param integralExchangeTypeDto
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年9月22日 上午9:15:28</p>
     * <p>修改备注：</p>
     */
    public int addIntegralExchangeType(IntegralExchangeTypeDto integralExchangeTypeDto);
}

