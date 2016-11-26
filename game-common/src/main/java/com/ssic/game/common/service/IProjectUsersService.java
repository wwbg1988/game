package com.ssic.game.common.service;

import com.ssic.game.common.dto.ProjectDto;

/**		
 * <p>Title: IProjectUsersService </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 朱振	
 * @date 2015年10月29日 上午11:31:26	
 * @version 1.0
 * <p>修改人：朱振</p>
 * <p>修改时间：2015年10月29日 上午11:31:26</p>
 * <p>修改备注：</p>
 */
public interface IProjectUsersService
{
    /**
     * 通过ims_user的userId查找到所有的project<BR>	 
     * @author 朱振	
     * @date 2015年10月29日 上午11:32:50	
     * @version 1.0
     * @param userId
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年10月29日 上午11:32:50</p>
     * <p>修改备注：</p>
     */
    ProjectDto getProjectByIMSUserId(String userId);
}

