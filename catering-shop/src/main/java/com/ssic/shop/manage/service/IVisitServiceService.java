package com.ssic.shop.manage.service;

import com.ssic.shop.manage.dto.VisitServiceDto;

/**		
 * <p>Title: IVisitServiceService </p>
 * <p>Description: 大厨上门烧菜</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 朱振	
 * @date 2015年9月15日 下午5:12:20	
 * @version 1.0
 * <p>修改人：朱振</p>
 * <p>修改时间：2015年9月15日 下午5:12:20</p>
 * <p>修改备注：</p>
 */
public interface IVisitServiceService
{
    /**
     * 保存	 
     * @author 朱振	
     * @date 2015年9月16日 下午2:20:01	
     * @version 1.0
     * @param visitServiceDto
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年9月16日 下午2:20:01</p>
     * <p>修改备注：</p>
     */
    public int save(VisitServiceDto visitServiceDto);
}

