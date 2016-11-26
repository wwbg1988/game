package com.ssic.catering.admin.service;

import java.util.List;

import com.ssic.catering.admin.pageModel.Tree;
import com.ssic.game.common.dto.ProjectDto;


public interface ProjectService {

    public List<Tree> allTree();
	
	public List<ProjectDto> findAll();
	
	/**
	 * 
	 * findById：根据projId查询对应的项目信息
	 * @param id
	 * @return
	 * @exception	
	 * @author pengcheng.yang
	 * @date 2015年10月28日 上午10:41:55
	 */
	public ProjectDto findById(String id);

    
    /**     
     * allTreeByProjId：通过项目id查找项目树
     * @param projId
     * @return
     * @exception	
     * @author 刘博
     * @date 2015年10月30日 下午4:32:45	 
     */
    public List<Tree> allTreeByProjId(String projId);
}
