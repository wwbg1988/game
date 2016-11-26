package com.ssic.game.admin.service;

import java.util.List;

import com.ssic.game.admin.pageModel.PageHelper;
import com.ssic.game.admin.pageModel.Role;
import com.ssic.game.admin.pageModel.Tree;
import com.ssic.game.common.dto.ProjectDto;


public interface ProjectService {
	public List<ProjectDto> findAll();

	public ProjectDto findById(String id);
	
	public List<ProjectDto> findByIdName(ProjectDto projectDto);
	
	public List<ProjectDto> findByIdNameAll(ProjectDto projectDto,PageHelper ph);
	
	public void insert(ProjectDto projectDto);
	
	public void update(ProjectDto projectDto);
	
	public void deleteById(ProjectDto projectDto);
	
	public String findUserPers(String id);
	
	public List<Tree> userTree(String searchName,String projId);
	
	public void grantUser(String projid,String resourceIds);
	
	public List<Role>  treeGrid(ProjectDto projectDto);
	
	public int findCount(ProjectDto projectDto);

    
    /**     
     * allTree：一句话描述方法功能
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年7月9日 下午9:08:33	 
     */
    public List<Tree> allTree();
	
}
