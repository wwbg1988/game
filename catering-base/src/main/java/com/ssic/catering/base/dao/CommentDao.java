package com.ssic.catering.base.dao;

import java.util.Date;
import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.ssic.catering.base.dto.CommentDto;
import com.ssic.catering.base.dto.PageHelperDto;
import com.ssic.catering.base.mapper.CommentExMapper;
import com.ssic.catering.base.mapper.CommentMapper;
import com.ssic.catering.base.pojo.Carte;
import com.ssic.catering.base.pojo.CarteExample;
import com.ssic.catering.base.pojo.Comment;
import com.ssic.catering.base.pojo.CommentExample;
import com.ssic.catering.base.pojo.ConfigScoreExample;
import com.ssic.catering.base.pojo.CommentExample.Criteria;
import com.ssic.util.base.MyBatisBaseDao;
import com.ssic.util.constants.DataStatus;

/**
 * 		
 * <p>Title: CommentDao </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author pengcheng.yang	
 * @date 2015年8月4日 上午9:44:33	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年8月4日 上午9:44:33</p>
 * <p>修改备注：</p>
 */
@Repository
public class CommentDao extends MyBatisBaseDao<Comment>
{
    @Autowired
    @Getter
    private CommentMapper mapper;

    @Autowired
    private CommentExMapper commentExMapper;

    /**
     * 
     * findBy：一句话描述方法功能
     * @param param
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年8月4日 上午9:59:08
     */
    public List<Comment> findBy(Comment param)
    {
        CommentExample example = new CommentExample();
        Criteria critera = example.createCriteria();
        if (!StringUtils.isEmpty(param.getCafetoriumId()))
        {
            critera.andCafetoriumIdEqualTo(param.getCafetoriumId());
        }
        if (!StringUtils.isEmpty(param.getConfigListId()))
        {
            critera.andConfigListIdEqualTo(param.getConfigListId());
        }
        if (!StringUtils.isEmpty(param.getUserUniquenessId()))
        {
            critera.andUserUniquenessIdEqualTo(param.getUserUniquenessId());
        }
        example.setOrderByClause("create_time desc");
        critera.andStatEqualTo(DataStatus.ENABLED);

        return mapper.selectByExample(example);
    }

    /**
     * 
     * insertComments：保存用户提交的评论
     * @param comments
     * @exception	
     * @author pengcheng.yang
     * @date 2015年8月4日 上午10:13:47
     */
    public void insertComments(Comment comments)
    {
        comments.setStat(1);
        comments.setCreateTime(new Date());
        mapper.insertSelective(comments);
    }

    /**
     * 
     * queryIfComments：检查一个用户一天对一个餐厅只能评论一次
     * @param cafetoriumId
     * @param userId
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年8月7日 上午9:35:10
     */
    public int queryIfComments(String cafetoriumId, String userId)
    {
        if (!StringUtils.isEmpty(cafetoriumId) && !StringUtils.isEmpty(userId))
        {
            return this.commentExMapper.queryIfComments(cafetoriumId, userId);
        }
        return 0;
    }

    /**
     * 
     * findCountComments：一句话描述方法功能
     * @param cafetoriumId
    * @return 
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年8月8日 上午11:31:39
     */
    public List<CommentDto> findCountComments()
    {
        return this.commentExMapper.findCountComments();
    }

    /**
     * findCommentListByCafetoriumIdAndCreateTime：通过食堂Id和创建时间获取评论集合
     * @param cafetoriumId 食堂ID
     * @param createTime 时间
     * @return
     * @exception	
     * @author 张亚伟
     * @date 2015年8月11日 下午8:23:40
     */
    public List<Comment> findCommentListByCafetoriumIdAndCreateTime(String cafetoriumId, String createTime)
    {
        return commentExMapper.findCommentListByCafetoriumIdAndCreateTime(cafetoriumId, createTime);
    }

    /**
     * findCommentListByCafetoriumId：通过食堂Id获取评论集合
     * @param cafetoriumId 食堂ID
     * @return
     * @exception	
     * @author 张亚伟
     * @date 2015年8月11日 下午8:23:40
     */
    public List<Comment> findCommentListByCafetoriumId(String cafetoriumId)
    {
        CommentExample example = new CommentExample();
        Criteria critera = example.createCriteria();
        critera.andCafetoriumIdEqualTo(cafetoriumId);
        return mapper.selectByExample(example);
    }

    /**     
     * findAll：一句话描述方法功能
     * @param comment
     * @param phDto
     * @return
     * @exception	
     * @author 刘博
     * @date 2015年8月13日 下午2:27:42	 
     */
    public List<Comment> findAll(Comment param, PageHelperDto ph)
    {
        CommentExample example = new CommentExample();
        Criteria criteria = example.createCriteria();
        if (ph != null && !StringUtils.isEmpty(String.valueOf(ph.getBeginRow()))
            && !StringUtils.isEmpty(String.valueOf(ph.getRows())))
        {
            int beginRow = ph.getBeginRow();
            int rows = ph.getRows();
            example.setOrderByClause("create_time desc limit " + beginRow + "," + rows);
        }
        else
        {
            example.setOrderByClause("create_time desc");
        }

        if (!StringUtils.isEmpty(param.getId()))
        {
            criteria.andIdEqualTo(param.getId());
        }
        if (!StringUtils.isEmpty(param.getCafetoriumId()))
        {
            criteria.andCafetoriumIdEqualTo(param.getCafetoriumId());

        }
        criteria.andStatEqualTo(DataStatus.ENABLED);
        return mapper.selectByExample(example);
    }

    /**     
     * findCountBy：一句话描述方法功能
     * @param Comment
     * @return
     * @exception   
     * @author 刘博
     * @date 2015年8月4日 上午11:41:13    
     */
    public int findCount(Comment param)
    {
        CommentExample example = new CommentExample();
        Criteria criteria = example.createCriteria();

        criteria.andStatEqualTo(DataStatus.ENABLED);
        if (!StringUtils.isEmpty(param.getId()))
        {
            criteria.andIdEqualTo(param.getId());
        }
        if (!StringUtils.isEmpty(param.getCafetoriumId()))
        {
            criteria.andCafetoriumIdEqualTo(param.getCafetoriumId());

        }
        int count = mapper.countByExample(example);
        return count;
    }

    /**     
     * commentCount：一句话描述方法功能
     * @param addressId
     * @return
     * @exception	
     * @author yuanbin
     * @date 2015年8月31日 上午10:33:11	 
     */
    public String commentCount(String addressId)
    {
        // TODO 添加方法注释
        return commentExMapper.commentCount(addressId);
    }
}
