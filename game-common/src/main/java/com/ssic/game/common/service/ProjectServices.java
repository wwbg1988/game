/**
 * 
 */
package com.ssic.game.common.service;

import com.ssic.game.common.dto.ProjectDto;
import com.ssic.game.common.dto.ProjectUsersDto;

/**		
 * <p>Title: ProjectServices </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年7月11日 下午4:56:50	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年7月11日 下午4:56:50</p>
 * <p>修改备注：</p>
 */
public interface ProjectServices 
{
    ProjectDto findById(String id);
    
    ProjectUsersDto  findByUserID(String userId);
}

