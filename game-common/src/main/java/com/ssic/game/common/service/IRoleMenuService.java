package com.ssic.game.common.service;

import java.util.List;

import com.ssic.game.common.dto.RoleMenuDto;
import com.ssic.game.common.pageModel.Tree;
import com.ssic.game.common.pojo.RoleMenu;

/**     
 * findByProjectId：一句话描述方法功能
 * @param roleId
 * @return
 * @exception	
 * @author yuanbin
 * @date 2015年8月18日 上午9:25:06	 
 */

public interface IRoleMenuService {

	List<RoleMenuDto> findBy(RoleMenuDto roleMenuDto);

	RoleMenuDto findById(String id);
	
	void insertRoleMenu(RoleMenuDto roleMenuDto);
	
	void updateRoleMenu(RoleMenuDto roleMenuDto);
	
	void deleteRoleMenu(RoleMenuDto roleMenuDto);

	
	/**     
	 * menuTree：一句话描述方法功能
	 * @param searchName
	 * @return
	 * @exception	
	 * @author yuanbin
	 * @date 2015年8月20日 上午10:34:11	 
	 */
	List<RoleMenuDto> menuTree(RoleMenuDto roleMenuDto);

	
	/**     
	 * tree：一句话描述方法功能
	 * @param userId
	 * @return
	 * @exception	
	 * @author yuanbin
	 * @date 2015年8月20日 上午11:50:50	 
	 */
	List<RoleMenuDto> tree(String roleId);

	
	/**     
	 * grantMenu：一句话描述方法功能
	 * @param string
	 * @param resourceIds
	 * @exception	
	 * @author yuanbin
	 * @date 2015年8月21日 上午9:49:47	 
	 */
	void grantMenu(String roleId, String menuIds);
}
