/**
 * 
 */
package com.ssic.game.app.service;

import java.util.List;

import com.ssic.game.common.dto.WorkSearchDto;

/**		
 * <p>Title: ISensitiveWarningOperateService </p>
 * <p>Description: 食堂敏感词预警信息</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年8月11日 下午8:26:28	
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年8月11日 下午8:26:28</p>
 * <p>修改备注：</p>
 */
public interface ISensitiveWarningOperateService
{

    /**
     * sensitiveWarningList：根据用户id查询当前用户下的食堂敏感词预警信息
     * @param addressId 区域ID
     * @return
     * @exception   
     * @author 刘博
     * @date 2015年8月11日 下午8:47:31
     */
    List<WorkSearchDto> sensitiveWarningList(String userId, String searchDate, int beginRow, int endRow);
}
