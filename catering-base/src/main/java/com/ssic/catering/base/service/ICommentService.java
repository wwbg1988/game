package com.ssic.catering.base.service;

import java.util.List;

import com.ssic.catering.base.dto.CommentDto;
import com.ssic.catering.base.dto.PageHelperDto;
import com.ssic.catering.base.pojo.Comment;

public interface ICommentService {

	
	/**
     * 
     * findCommentListByCafetoriumIdAndCreateTime：通过食堂Id和创建时间获取评论集合
     * @param cafetoriumId 食堂ID
     * @param createTime 时间
     * @return
     * @exception	
     * @author 张亚伟
     * @date 2015年8月11日 下午8:23:40
     */
    List<Comment> findCommentListByCafetoriumIdAndCreateTime(String cafetoriumId,String createTime);

    
    /**     
     * findALL：一句话描述方法功能
     * @param caftrotiumId
     * @param phDto
     * @return
     * @exception	
     * @author 刘博
     * @date 2015年8月13日 下午2:15:36	 
     */
    List<CommentDto> findALL(String caftrotiumId, PageHelperDto phDto);


    
    /**     
     * findCount：一句话描述方法功能
     * @param caftrotiumId
     * @return
     * @exception	
     * @author 刘博
     * @date 2015年8月13日 下午2:15:51	 
     */
    int findCount(String caftrotiumId);


    
    /**     
     * findById：一句话描述方法功能
     * @param id
     * @return
     * @exception	
     * @author 刘博
     * @date 2015年8月13日 下午4:08:56	 
     */
    CommentDto findById(String id);


    
    /**     
     * delete：一句话描述方法功能
     * @param tempCarte
     * @exception	
     * @author 刘博
     * @date 2015年8月13日 下午4:09:25	 
     */
    void delete(CommentDto tempCarte);
}

