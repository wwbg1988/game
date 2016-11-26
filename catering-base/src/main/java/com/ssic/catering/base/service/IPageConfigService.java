package com.ssic.catering.base.service;

import java.util.List;

import com.ssic.catering.base.dto.PageConfigDto;
import com.ssic.catering.base.dto.PageHelperDto;

/**
 * 		
 * <p>Title: PageService </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author zhuzhen	
 * @date 2015年11月23日 下午4:51:07	
 * @version 1.0
 * <p>修改人：zhuzhen</p>
 * <p>修改时间：2015年11月23日 下午4:51:07</p>
 * <p>修改备注：</p>
 */
public interface IPageConfigService
{
    /**
     * 
     * getPageConfigDtoBy：分页查询
     * @param param 可以为空
     * @param ph 
     * @return
     * @exception	
     * @author zhuzhen
     * @date 2015年11月23日 下午4:52:41
     */
    List<PageConfigDto> getPageConfigDtoBy(PageConfigDto param, PageHelperDto ph);
    
    /**
     * 
     * getPageConfigDtoCountBy：获取总条数
     * @param param
     * @return
     * @exception	
     * @author zhuzhen
     * @date 2015年11月23日 下午5:06:15
     */
    long getPageConfigDtoCountBy(PageConfigDto param);
    
    /**
     * 
     * addPageConfigDto：添加数据
     * @param pageConfigDto
     * @return
     * @exception	
     * @author zhuzhen
     * @date 2015年11月23日 下午5:49:38
     */
    int addPageConfig(PageConfigDto pageConfigDto);
    
    
   /**
    * 
    * updatePageConfig：更新数据
    * @param pageConfigDto
    * @return
    * @exception	
    * @author zhuzhen
    * @date 2015年11月23日 下午6:00:14
    */
    int updatePageConfig(PageConfigDto pageConfigDto);
    
   /**
    * 
    * findPageConfigById：一句话描述方法功能
    * @param id
    * @return
    * @exception	
    * @author zhuzhen
    * @date 2015年11月30日 下午6:13:43
    */
    PageConfigDto findPageConfigDtoById(String id);
}

