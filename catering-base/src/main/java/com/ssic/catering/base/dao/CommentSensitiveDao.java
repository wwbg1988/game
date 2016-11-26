package com.ssic.catering.base.dao;

import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.ssic.catering.base.dto.SensitiveWarningReportDto;
import com.ssic.catering.base.mapper.CommentSensitiveExMapper;
import com.ssic.catering.base.mapper.CommentSensitiveMapper;
import com.ssic.catering.base.pojo.Comment;
import com.ssic.catering.base.pojo.CommentSensitive;
import com.ssic.catering.base.pojo.CommentSensitiveExample;
import com.ssic.catering.base.pojo.Config;
import  com.ssic.catering.base.pojo.CommentSensitiveExample.Criteria;
import com.ssic.util.base.MyBatisBaseDao;
import com.ssic.util.constants.DataStatus;

/**
 * 		
 * <p>Title: CommentSensitiveDto </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年8月6日 下午1:09:37	
 * @version 1.0
 * <p>修改人：pengcheng.yang</p>
 * <p>修改时间：2015年8月6日 下午1:09:37</p>
 * <p>修改备注：</p>
 */
@Repository
public class CommentSensitiveDao extends MyBatisBaseDao<CommentSensitive>
{
    @Autowired
    @Getter
    private CommentSensitiveMapper mapper;
    @Autowired
    private CommentSensitiveExMapper exMapper;
    
    /**
     * 
     * findBy：查询评论和敏感词关联表
     * @param param
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年8月6日 下午1:28:41
     */
    public List<CommentSensitive> findBy(CommentSensitive param){
        CommentSensitiveExample example = new CommentSensitiveExample();
        Criteria criteara = example.createCriteria();
        criteara.andStatEqualTo(DataStatus.ENABLED);
        if(!StringUtils.isEmpty(param.getId())){
            criteara.andIdEqualTo(param.getId());
        }
        if(!StringUtils.isEmpty(param.getCommentId())){
            criteara.andCommentIdEqualTo(param.getCommentId());
        }
        if(!StringUtils.isEmpty(param.getSensitiveId())){
            criteara.andSensitiveIdEqualTo(param.getSensitiveId());
        }
        if(!StringUtils.isEmpty(param.getCreateTime())){
            criteara.andCreateTimeEqualTo(param.getCreateTime());
        }
        return mapper.selectByExample(example);
    }
    
    /**
     * 
     * findById：一句话描述方法功能
     * @param id
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年8月6日 下午1:29:32
     */
    public CommentSensitive findById(String id){
        if(!StringUtils.isEmpty(id)){
            return mapper.selectByPrimaryKey(id);
        }
        return null;
    }
    /**
     * 
     * insertCommentSensitive：一句话描述方法功能
     * @exception	
     * @author pengcheng.yang
     * @date 2015年8月6日 下午1:30:10
     */
    public void insertCommentSensitive(List<String> sensitiveList,String commentid){
        if(!StringUtils.isEmpty(sensitiveList)&&!StringUtils.isEmpty(commentid)){
            exMapper.insertConf(sensitiveList,commentid);
        }
    }

    
    /**
	 * findCommentSensitiveListByCommentList：通过评论集合获取敏感词Id
	 * @param commentList 评论集合
	 * @return
	 * @exception	
	 * @author 张亚伟
	 * @date 2015年8月11日 下午9:35:18
	 */
	public List<String> findCommentSensitiveListByCommentList(
			List<Comment> commentList) {
		
		return exMapper.findCommentSensitiveListByCommentList(commentList);
	}

	
	/**     
	 * warnaReportList：一句话描述方法功能
	 * @param reportId
	 * @return
	 * @exception	
	 * @author yuanbin
	 * @date 2015年8月25日 下午1:26:10	 
	 */
	public List<SensitiveWarningReportDto> warnaReportList(String id) {
		// TODO 添加方法注释
		return exMapper.warnaReportList(id);
	}
	
	
}

