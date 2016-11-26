/**
 * 
 */
package com.ssic.game.common.service;

import java.util.List;

import com.ssic.game.common.pojo.ImsRole;

/**		
 * <p>Title: IRoleService </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年7月2日 上午9:46:41	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年7月2日 上午9:46:41</p>
 * <p>修改备注：</p>
 */
public interface IRolesService
{
    public List<ImsRole> findRole(ImsRole imsRole);
    public ImsRole findById(String id);
    
}

