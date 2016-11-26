package com.ssic.catering.base.service;

import java.util.Date;
import java.util.List;

import com.ssic.catering.base.dao.ConfigScoreDao;
import com.ssic.catering.base.dto.ConfigScoreDto;
import com.ssic.catering.base.dto.PageHelperDto;

/**
 * 		
 * <p>Title: IConfigScoreService </p>
 * <p>Description: 食堂评分操作</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 张亚伟	
 * @date 2015年8月11日 下午1:33:38	
 * @version 1.0
 * <p>修改人：张亚伟</p>
 * <p>修改时间：2015年8月11日 下午1:33:38</p>
 * <p>修改备注：</p>
 */
public interface IConfigScoreService {

	/**
	 * findCreateTimeDistinct：通过食堂ID获取评分日期(去重)
	 * @param cafetoriumId 食堂ID
	 * @param index 第几页
	 * @param size 每页多少条信息
	 * @return
	 * @exception	
	 * @author 张亚伟
	 * @date 2015年8月11日 下午1:08:19
	 */
	public List<String> findCreateTimeDistinct(String cafetoriumId, Integer index, Integer size);

	/**
	 * findConfigScoreToCreateTime：通过时间获取食堂评分信息
	 * @param cafetoriumId 食堂ID
	 * @param time 时间
	 * @return
	 * @exception	
	 * @author 张亚伟
	 * @date 2015年8月11日 下午3:12:19
	 */
	public List<ConfigScoreDto> findConfigScoreListToCafetoriumIdByCreateTime(String cafetoriumId,String time);

    
    /**     
     * findALL：一句话描述方法功能
     * @param caftrotiumId
     * @param phDto
     * @return
     * @exception	
     * @author 刘博
     * @date 2015年8月14日 下午1:47:42	 
     */
    public List<ConfigScoreDto> findALL(String caftrotiumId, PageHelperDto phDto);

    
    /**     
     * findCount：一句话描述方法功能
     * @param caftrotiumId
     * @return
     * @exception	
     * @author 刘博
     * @date 2015年8月14日 下午1:48:02	 
     */
    public int findCount(String caftrotiumId);
	
}

