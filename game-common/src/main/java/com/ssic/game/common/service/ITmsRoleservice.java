package com.ssic.game.common.service;

import java.util.List;





import com.ssic.game.common.dto.ProjectDto;
import com.ssic.game.common.dto.RoleUsersDto;
import com.ssic.game.common.dto.TmsRoleDto;
import com.ssic.game.common.pageModel.PageHelper;
import com.ssic.game.common.pageModel.Tree;

public interface ITmsRoleservice {

	public List<TmsRoleDto> findBy(TmsRoleDto tmsRoleDto);
	
	public void insertRole(TmsRoleDto tmsRoleDto);
	
	public void updateRole(TmsRoleDto tmsRoleDto);
	
	public void deleteRole(TmsRoleDto tmsRoleDto);
	
	public String findUserPers(String ids);
	
	public List<Tree> userTree(String searchName);
	public List<Tree> cateringUserTree(String searchName, List<ProjectDto> pjds);
	public void grantUser(String userpress,String resourceIds);
	
	public List<TmsRoleDto> findByAll(TmsRoleDto tmsRoleDto,PageHelper ph);
	
	public int findCount(TmsRoleDto tmsRoleDto);
	
	public List<Tree> tree(String userId);
	
	public List<RoleUsersDto> findRoleUser(RoleUsersDto roleUsersDto);
	
	public List<RoleUsersDto> findUserNames(RoleUsersDto roleUsersDto);
	
	public TmsRoleDto findById(String id);
	
	public void insertRoleUser(RoleUsersDto roleUsersDto);
}
