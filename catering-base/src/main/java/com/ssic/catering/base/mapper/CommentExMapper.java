package com.ssic.catering.base.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ssic.catering.base.dto.CommentDto;
import com.ssic.catering.base.pojo.Comment;
/**
 * 		
 * <p>Title: CommentExMapper </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年8月6日 下午8:38:48	
 * @version 1.0
 * <p>修改人：pengcheng.yang</p>
 * <p>修改时间：2015年8月6日 下午8:38:48</p>
 * <p>修改备注：</p>
 */
public interface CommentExMapper {
    int queryIfComments(@Param("cafetoriumId")String cafetoriumId,@Param("userId")String userId);
    List<CommentDto> findCountComments();
    
    /**
     * findCommentListByCafetoriumIdAndCreateTime：通过食堂Id和创建时间获取评论集合
     * @param cafetoriumId 食堂ID
     * @param createTime 时间
     * @return
     * @exception	
     * @author 张亚伟
     * @date 2015年8月11日 下午8:23:40
     */
    List<Comment> findCommentListByCafetoriumIdAndCreateTime(@Param("cafetoriumId")String cafetoriumId,@Param("createTime")String createTime);
	
	/**     
	 * commentCount：一句话描述方法功能
	 * @param addressId
	 * @return
	 * @exception	
	 * @author yuanbin
	 * @date 2015年8月31日 上午10:33:50	 
	 */
	String commentCount(String addressId);
}