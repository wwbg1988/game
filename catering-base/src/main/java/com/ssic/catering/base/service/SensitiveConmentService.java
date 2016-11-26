/**
 * 
 */
package com.ssic.catering.base.service;

import java.util.List;

import com.ssic.catering.base.dto.CommentSensitiveDto;
import com.ssic.catering.base.dto.SensitiveDto;
import com.ssic.catering.base.dto.SensitiveWarningReportDto;
import com.ssic.catering.base.pojo.SensitiveWarningReport;

/**		
 * <p>Title: SensitiveService </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author yuanbin	
 * @date 2015年8月13日 上午11:24:22	
 * @version 1.0
 * <p>修改人：yuanbin</p>
 * <p>修改时间：2015年8月13日 上午11:24:22</p>
 * <p>修改备注：</p>
 */
public interface SensitiveConmentService {
	
	SensitiveWarningReport selectByPrimaryKey(String id);

    
	
    /**     
     * findByCommentId：通过评论id查询对应的敏感词集合
     * @param id
     * @return
     * @exception	
     * @author 刘博
     * @date 2015年8月14日 上午9:09:50	 
     */
    List<CommentSensitiveDto> findByCommentId(String id);



	
	/**     
	 * warnaReportList：一句话描述方法功能
	 * @param reportId
	 * @return
	 * @exception	
	 * @author yuanbin
	 * @date 2015年8月25日 下午1:25:15	 
	 */
	List<SensitiveWarningReportDto> warnaReportList(String reportId);
    
    
	
}

