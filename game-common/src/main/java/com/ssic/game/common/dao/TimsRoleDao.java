package com.ssic.game.common.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssic.game.common.dto.PageHelperDto;
import com.ssic.game.common.dto.RoleUsersDto;
import com.ssic.game.common.dto.TmsRoleDto;
import com.ssic.game.common.mapper.TimsRoleExMapper;

@Repository
public class TimsRoleDao {
    @Autowired
	private TimsRoleExMapper timsRoleExMapper;
	
	public List<TmsRoleDto> findBy(TmsRoleDto tmsRoleDto){
      	return 	timsRoleExMapper.findBy(tmsRoleDto);
	}
	
	public void insertRole(TmsRoleDto tmsRoleDto){
		timsRoleExMapper.insertRole(tmsRoleDto);
	}
	
	public void updateRole(TmsRoleDto tmsRoleDto){
		timsRoleExMapper.updateRole(tmsRoleDto);
	}
	
	public void deleteRole(TmsRoleDto tmsRoleDto){
		timsRoleExMapper.deleteRole(tmsRoleDto);
	}
	
	public List<RoleUsersDto> findRoleUser(RoleUsersDto roleUsersDto){
		
		return timsRoleExMapper.findRoleUser(roleUsersDto);
	}
	
	public void delRoleUseById(RoleUsersDto roleUsersDto){
		timsRoleExMapper.delRoleUseById(roleUsersDto);
	}
	
	public void insertRoleUser(RoleUsersDto roleUsersDto){
		timsRoleExMapper.insertRoleUser(roleUsersDto);
	}
	
	public List<TmsRoleDto> findByAll(TmsRoleDto tmsRoleDto,PageHelperDto ph){
		return timsRoleExMapper.findByAll(tmsRoleDto,ph);
	}
	
	public int findCount(TmsRoleDto tmsRoleDto){
		return timsRoleExMapper.findCount(tmsRoleDto);
	}
	
	public List<TmsRoleDto> findByUserId(String userId){
		List<TmsRoleDto> roles = timsRoleExMapper.findByUserId(userId);
		return roles;
	}
	
	public List<TmsRoleDto> findTreeRoles(String user_id){
		return timsRoleExMapper.findTreeRoles(user_id);
	}
	
	public List<RoleUsersDto> findUserNames(RoleUsersDto roleUsersDto){
		return timsRoleExMapper.findUserNames(roleUsersDto);
	}
	
}
