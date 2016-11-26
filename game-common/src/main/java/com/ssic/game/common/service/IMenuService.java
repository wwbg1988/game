package com.ssic.game.common.service;

import java.util.List;

import com.ssic.game.common.dto.MenuDto;
import com.ssic.game.common.pageModel.Tree;



public interface IMenuService {

	List<MenuDto> findBy(MenuDto menuDto);
	
	MenuDto findById(String id);
	
	void insertMenu(MenuDto menuDto);
	
	void updateMenu(MenuDto menuDto);
	
	void deleteMenu(MenuDto menuDto);

	/**     
	 * findByProjectId：一句话描述方法功能
	 * @param roleId
	 * @return
	 * @exception	
	 * @author yuanbin
	 * @date 2015年8月11日 下午4:25:06	 
	 */
	List<MenuDto> findByProjectId(String projectId);
	
	/**     
	 * menuByroleId：一句话描述方法功能
	 * @param roleId
	 * @return
	 * @exception	
	 * @author yuanbin
	 * @date 2015年8月11日 下午4:25:06	 
	 */
	List<MenuDto> findByroleId(String roleId);
	
	/**
	 * 
	 * tree：树型展现所有菜单
	 * @return
	 * @exception	
	 * @author 朱振
	 * @date 2015年8月20日 下午3:05:23
	 */
	List<Tree> AllTree();

    
    /**     
     * AllTree：一句话描述方法功能
     * @param projId
     * @param projId2 
     * @return
     * @exception	
     * @author 刘博
     * @date 2015年12月2日 下午2:50:33	 
     */
    List<Tree> AllTree(String menuId, String projId);
}
