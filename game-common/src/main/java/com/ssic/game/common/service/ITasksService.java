/**
 * 
 */
package com.ssic.game.common.service;

import com.ssic.game.common.dto.TasksDto;

/**		
 * <p>Title: ITasksService </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年7月2日 下午3:05:12	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年7月2日 下午3:05:12</p>
 * <p>修改备注：</p>
 */
public interface ITasksService
{

    
    /**     
     * findByLastCreateTime：一句话描述方法功能
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年7月2日 下午3:14:35	 
     */
    TasksDto findByLastCreateTime(String procId);
    
    /**     
     * findByTaskId：一句话描述方法功能
     * @return
     * @exception   
     * @author Administrator
     * @date 2015年7月3日 下午4:14:35     
     */
   TasksDto findByTaskId(String id);

}

