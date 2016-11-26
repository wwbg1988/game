package com.ssic.catering.base.service;

import java.util.List;

import com.ssic.catering.base.pojo.Comment;

/**
 * 		
 * <p>Title: ICommentSensitiveService </p>
 * <p>Description: 敏感词逻辑层接口</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 张亚伟	
 * @date 2015年8月11日 下午9:31:31	
 * @version 1.0
 * <p>修改人：张亚伟</p>
 * <p>修改时间：2015年8月11日 下午9:31:31</p>
 * <p>修改备注：</p>
 */
public interface ICommentSensitiveService {

	/**
	 * findCommentSensitiveListByCommentList：通过评论集合获取敏感词Id
	 * @param commentList 评论集合
	 * @return
	 * @exception	
	 * @author 张亚伟
	 * @date 2015年8月11日 下午9:35:18
	 */
	public List<String> findCommentSensitiveListByCommentList(List<Comment> commentList);
	
}

