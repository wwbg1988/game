package com.ssic.catering.base.service;

import java.util.List;

import com.ssic.catering.base.dto.CommentDto;
import com.ssic.catering.base.dto.FindDataResults;
import com.ssic.util.model.Response;

/**
 *      
 * <p>Title: ConfigSearchService </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author pengcheng.yang   
 * @date 2015年8月3日 下午8:41:35    
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年8月3日 下午8:41:35</p>
 * <p>修改备注：</p>
 */
public interface IConfigSearchService 
{
    Response<FindDataResults> findBy(String cafetoriumId,String userId);

    
    
    /**
     * findConfigScoreByAddressCode：通过区域辅助码获取这个区域的平均评分
     * @param addressCode
     * @return
     * @exception	
     * @author 张亚伟
     * @date 2015年8月10日 上午9:55:41
     */
    public String findConfigScoreByAddressCode(String addressCode);
    
    public String findConfigHistoryScoreByAddressCode(String addressCode);

    List<CommentDto> findCountComments();

}

