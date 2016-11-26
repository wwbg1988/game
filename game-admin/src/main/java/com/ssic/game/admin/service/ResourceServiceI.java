package com.ssic.game.admin.service;

import java.util.List;

import com.ssic.game.admin.dto.TImsMenuDto;
import com.ssic.game.admin.pageModel.Resource;
import com.ssic.game.admin.pageModel.SessionInfo;
import com.ssic.game.admin.pageModel.Tree;


/**
 * 资源Service
 * 
 * @author 刘博
 * 
 */
public interface ResourceServiceI {

	/**
	 * 获得资源树(资源类型为菜单类型)
	 * 
	 * 通过用户ID判断，他能看到的资源
	 * 
	 * @param sessionInfo
	 * @param tabType 
	 * @return
	 */
	public List<Tree> tree(SessionInfo sessionInfo);

	/**
	 * 获得资源树(包括所有资源类型)
	 * 
	 * 通过用户ID判断，他能看到的资源
	 * 
	 * @param sessionInfo
	 * @return
	 */
	public List<Tree> allTree(SessionInfo sessionInfo);

	/**
	 * 获得资源列表
	 * 
	 * @param sessionInfo
	 * 
	 * @return
	 */
	public List<TImsMenuDto> treeGrid(SessionInfo sessionInfo);

	/**
	 * 添加资源
	 * 
	 * @param resource
	 * @param sessionInfo
	 */
	public void add(Resource resource, SessionInfo sessionInfo);

	/**
	 * 删除资源
	 * 
	 * @param id
	 */
	public void delete(String id);

	/**
	 * 修改资源
	 * 
	 * @param menuDto
	 */
	public void edit(TImsMenuDto menuDto);

	/**
	 * 获得一个资源
	 * 
	 * @param id
	 * @return
	 */
	public Resource get(String id);

	public void add(TImsMenuDto menuDto, SessionInfo sessionInfo);

    
    /**     
     * tabTree：一句话描述方法功能
     * @param sessionInfo
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年7月29日 上午11:57:02	 
     */
    public List<Tree> tabTree(SessionInfo sessionInfo);

    
    /**     
     * allsTree：一句话描述方法功能
     * @param sessionInfo
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年7月29日 下午12:19:09	 
     */
    public List<Tree> allsTree(SessionInfo sessionInfo);

    
   

}
