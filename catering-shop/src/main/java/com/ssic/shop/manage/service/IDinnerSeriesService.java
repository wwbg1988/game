package com.ssic.shop.manage.service;

import java.util.List;

import com.ssic.shop.manage.dto.DinnerSeriesDto;
import com.ssic.shop.manage.dto.PageHelperDto;

/**		
 * <p>Title: IDinnerSeriesService </p>
 * <p>Description: 套餐</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 朱振	
 * @date 2015年9月16日 上午10:08:42	
 * @version 1.0
 * <p>修改人：朱振</p>
 * <p>修改时间：2015年9月16日 上午10:08:42</p>
 * <p>修改备注：</p>
 */
public interface IDinnerSeriesService
{
    /**
     * 分页查询	 
     * @author 朱振	
     * @date 2015年9月18日 下午3:49:27	
     * @version 1.0
     * @param dinnerSeriesDto
     * @param pageHelperDto
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年9月18日 下午3:49:27</p>
     * <p>修改备注：</p>
     */
    public List<DinnerSeriesDto> getListBy(DinnerSeriesDto dinnerSeriesDto, PageHelperDto pageHelperDto);
    
    /**
     * 保存	 
     * @author 朱振	
     * @date 2015年9月18日 下午3:49:44	
     * @version 1.0
     * @param dinnerSeriesDto
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年9月18日 下午3:49:44</p>
     * <p>修改备注：</p>
     */
    public int insert(DinnerSeriesDto dinnerSeriesDto);
    
    /**
     * 	 
     * @author 朱振	
     * @date 2015年10月9日 上午10:23:12	
     * @version 1.0
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年10月9日 上午10:23:12</p>
     * <p>修改备注：</p>
     */
    public List<DinnerSeriesDto> getList();
}

