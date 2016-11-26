package com.ssic.catering.base.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ssic.catering.base.dto.SensitiveDto;

/**
 * 		
 * <p>Title: ICommentScoreService </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author pengcheng.yang	
 * @date 2015年8月5日 下午5:00:22	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年8月5日 下午5:00:22</p>
 * <p>修改备注：</p>
 */
public interface ICommentScoreService
{
    String insertsCommentScore(HttpServletRequest request);

	
	/**     
	 * commentCount：一句话描述方法功能
	 * @param addressId
	 * @return
	 * @exception	
	 * @author yuanbin
	 * @date 2015年8月31日 上午10:31:27	 
	 */
	String commentCount(String addressId);
    
    
}

