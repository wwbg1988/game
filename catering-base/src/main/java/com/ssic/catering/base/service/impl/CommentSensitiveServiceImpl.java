package com.ssic.catering.base.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssic.catering.base.dao.CommentSensitiveDao;
import com.ssic.catering.base.pojo.Comment;
import com.ssic.catering.base.service.ICommentScoreService;
import com.ssic.catering.base.service.ICommentSensitiveService;
/**
 * 		
 * <p>Title: CommentSensitiveServiceImpl </p>
 * <p>Description: 敏感词记录评论逻辑实现</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 张亚伟	
 * @date 2015年8月11日 下午10:06:51	
 * @version 1.0
 * <p>修改人：张亚伟</p>
 * <p>修改时间：2015年8月11日 下午10:06:51</p>
 * <p>修改备注：</p>
 */
@Service
public class CommentSensitiveServiceImpl implements ICommentSensitiveService {

	@Autowired
	private CommentSensitiveDao commentSensitiveDao;
	
	
	/**
	 * findCommentSensitiveListByCommentList：通过评论集合获取敏感词Id
	 * @param commentList 评论集合
	 * @return
	 * @exception	
	 * @author 张亚伟
	 * @date 2015年8月11日 下午9:35:18
	 */
	@Override
	public List<String> findCommentSensitiveListByCommentList(
			List<Comment> commentList) {
		return commentSensitiveDao.findCommentSensitiveListByCommentList(commentList);
	}


}

