package com.ssic.catering.base.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.ssic.catering.base.dao.CommentDao;
import com.ssic.catering.base.dto.CafetoriumDto;
import com.ssic.catering.base.dto.CommentDto;
import com.ssic.catering.base.dto.CommentSensitiveDto;
import com.ssic.catering.base.dto.PageHelperDto;
import com.ssic.catering.base.pojo.Comment;
import com.ssic.catering.base.pojo.Sensitive;
import com.ssic.catering.base.service.ICafetoriumService;
import com.ssic.catering.base.service.ICommentService;
import com.ssic.catering.base.service.ISensitiveService;
import com.ssic.catering.base.service.SensitiveConmentService;
import com.ssic.util.BeanUtils;
import com.ssic.util.constants.DataStatus;

@Service
public class CommentServiceImpl implements ICommentService
{

    @Autowired
    private CommentDao commentDao;
    @Autowired
    private ICafetoriumService cafetoriumService;
    @Autowired
    private SensitiveConmentService sensitiveConmentService;
    @Autowired
    private ISensitiveService sensitiveService;

    /**
     * findCommentListByCafetoriumIdAndCreateTime：通过食堂Id和创建时间获取评论集合
     * @param cafetoriumId 食堂ID
     * @param createTime 时间
     * @return
     * @exception	
     * @author 张亚伟
     * @date 2015年8月11日 下午8:23:40
     */
    @Override
    public List<Comment> findCommentListByCafetoriumIdAndCreateTime(String cafetoriumId, String createTime)
    {
    	//如果时间为空,获取所有评论信息
    	if (createTime==null) {
    		return commentDao.findCommentListByCafetoriumId(cafetoriumId);
		}else
		{
			return commentDao.findCommentListByCafetoriumIdAndCreateTime(cafetoriumId, createTime);
		}
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.catering.base.service.ICommentService#findALL(java.lang.String, com.ssic.catering.base.dto.PageHelperDto)   
    */
    @Override
    public List<CommentDto> findALL(String caftrotiumId, PageHelperDto phDto)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<CommentDto> result = new ArrayList<CommentDto>();
        Comment comment = new Comment();
        comment.setCafetoriumId(caftrotiumId);
        List<Comment> list = commentDao.findAll(comment, phDto);
        if (!CollectionUtils.isEmpty(list))
        {
            result = BeanUtils.createBeanListByTarget(list, CommentDto.class);
            for (CommentDto dto : result)
            {
                if (!StringUtils.isEmpty(dto.getCafetoriumId()))
                {
                    CafetoriumDto cafe = cafetoriumService.findById(dto.getCafetoriumId());
                    dto.setCafeName(cafe.getCafeName());
                    dto.setSensitiveName(findSensitiveNameByCommentId(dto.getId()));

                }
                String date = sdf.format(dto.getCreateTime());
                dto.setGroupTime(date);
            }
            return result;
        }
        return result;
    }

    public String findSensitiveNameByCommentId(String commentId)
    {
        String sensitiveName = "";
        List<CommentSensitiveDto> sensitiveList = sensitiveConmentService.findByCommentId(commentId);
        if (!CollectionUtils.isEmpty(sensitiveList))
        {
            for (int i = 0; i < sensitiveList.size(); i++)
            {
                if (i > 0)
                {
                    sensitiveName += ",";
                }
                Sensitive sen = sensitiveService.getSensitiveById(sensitiveList.get(i).getSensitiveId());
                sensitiveName += sen.getSensitiveName();
            }
        }
        return sensitiveName;
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.catering.base.service.ICommentService#findCount(java.lang.String)   
    */
    @Override
    public int findCount(String caftrotiumId)
    {
        Comment comment = new Comment();
        comment.setCafetoriumId(caftrotiumId);
        int counts = commentDao.findCount(comment);
        return counts;
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.catering.base.service.ICommentService#findById(java.lang.String)   
    */
    @Override
    public CommentDto findById(String id)
    {
        CommentDto dto = new CommentDto();
        Comment comment = commentDao.selectByPrimaryKey(id);
        BeanUtils.copyProperties(comment, dto);
        if (dto != null)
        {
            return dto;
        }
        return null;
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.catering.base.service.ICommentService#delete(com.ssic.catering.base.dto.CommentDto)   
    */
    @Override
    public void delete(CommentDto tempCarte)
    {
        Comment comment = commentDao.selectByPrimaryKey(tempCarte.getId());
        comment.setStat(DataStatus.DISABLED);
        comment.setLastUpdateTime(new Date());
        commentDao.updateByPrimaryKey(comment);

    }

}
