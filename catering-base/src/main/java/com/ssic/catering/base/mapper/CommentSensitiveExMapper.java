package com.ssic.catering.base.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ssic.catering.base.dto.SensitiveWarningReportDto;
import com.ssic.catering.base.pojo.Comment;

/**
 * 		
 * <p>Title: CommentSensitiveExMapper </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年8月6日 下午1:31:32	
 * @version 1.0
 * <p>修改人：pengcheng.yang</p>
 * <p>修改时间：2015年8月6日 下午1:31:32</p>
 * <p>修改备注：</p>
 */
public interface CommentSensitiveExMapper {
	void insertConf(@Param("sensitiveList") List<String> sensitiveId,
			@Param("commentid") String commentid);

	/**
	 * findCommentSensitiveListByCommentList：通过评论集合获取敏感词Id
	 * @param commentList 评论集合
	 * @return
	 * @exception	
	 * @author 张亚伟
	 * @date 2015年8月11日 下午9:35:18
	 */
	public List<String> findCommentSensitiveListByCommentList(
			@Param("commentList") List<Comment> commentList);
	
	/**
	 * warnaReportList
	 * @return
	 * @exception	
	 * @author yuanbin
	 * @date 2015年8月25日 上午12:00:00
	 */
	public List<SensitiveWarningReportDto> warnaReportList (@Param("id") String id);
	
}