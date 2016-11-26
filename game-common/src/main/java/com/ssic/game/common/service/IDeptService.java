/**
 * 
 */
package com.ssic.game.common.service;

import java.util.List;

import com.ssic.game.common.dto.DeptDto;
import com.ssic.game.common.dto.MeetingPerDto;

/**		
 * <p>Title: IDeptService </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年7月11日 上午10:10:13	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年7月11日 上午10:10:13</p>
 * <p>修改备注：</p>
 */
public interface IDeptService
{

    
    /**     
     * findById：一句话描述方法功能
     * @param id
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年7月11日 上午10:12:33	 
     */
    DeptDto findById(String id);  
    
    MeetingPerDto checkMeeting(String userId,String projId,String mdeptCode);
    List<DeptDto> findBy(DeptDto param);

}

