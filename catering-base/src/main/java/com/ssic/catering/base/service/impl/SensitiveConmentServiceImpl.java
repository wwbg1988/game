/**
 * 
 */
package com.ssic.catering.base.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssic.catering.base.dao.CommentSensitiveDao;
import com.ssic.catering.base.dao.SensitiveWarningReportDao;
import com.ssic.catering.base.dto.CommentSensitiveDto;
import com.ssic.catering.base.dto.SensitiveWarningReportDto;
import com.ssic.catering.base.pojo.CommentSensitive;
import com.ssic.catering.base.pojo.SensitiveWarningReport;
import com.ssic.catering.base.service.SensitiveConmentService;
import com.ssic.util.BeanUtils;

/**		
 * <p>Title: SensitiveConmentServiceImpl </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author yuanbin	
 * @date 2015年8月13日 上午11:27:09	
 * @version 1.0
 * <p>修改人：yuanbin</p>
 * <p>修改时间：2015年8月13日 上午11:27:09</p>
 * <p>修改备注：</p>
 */
@Service
public class SensitiveConmentServiceImpl implements SensitiveConmentService
{

    @Autowired
    SensitiveWarningReportDao sensitiveWarningReportDao;
    @Autowired
    CommentSensitiveDao commentSensitiveDao;

    public SensitiveWarningReport selectByPrimaryKey(String id)
    {

        return sensitiveWarningReportDao.selectByPrimaryKey(id);
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.catering.base.service.SensitiveConmentService#findByCommentId(java.lang.String)   
    */
    @Override
    public List<CommentSensitiveDto> findByCommentId(String commentId)
    {
        CommentSensitive param = new CommentSensitive();
        param.setCommentId(commentId);
        List<CommentSensitive> list = commentSensitiveDao.findBy(param);
        if (!CollectionUtils.isEmpty(list))
        {
            return BeanUtils.createBeanListByTarget(list, CommentSensitiveDto.class);
        }
        return null;
    }

	
	 /** 
	 * (non-Javadoc)   
	 * @see com.ssic.catering.base.service.SensitiveConmentService#warnaReportList(java.lang.String) 
	 * @author yuanbin  
	 */
	@Override
	public List<SensitiveWarningReportDto> warnaReportList(String reportId) {
		// TODO 添加方法注释
		return commentSensitiveDao.warnaReportList(reportId);
	}
}
