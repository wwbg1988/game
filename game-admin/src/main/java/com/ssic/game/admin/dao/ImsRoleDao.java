package com.ssic.game.admin.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssic.game.admin.dto.MenuAndRoleDto;
import com.ssic.game.admin.dto.RoleMenuDto;
import com.ssic.game.admin.dto.TImsMenuDto;
import com.ssic.game.admin.dto.TImsRoleDto;
import com.ssic.game.admin.dto.TImsRoleMenuDto;
import com.ssic.game.admin.dto.TImsUsersRoleDto;
import com.ssic.game.admin.mapper.TImsRoleExMapper;
import com.ssic.game.admin.pojo.MenuAndRoles;
import com.ssic.game.admin.pojo.Roles;
import com.ssic.game.admin.pojo.UsersRole;

@Repository
public class ImsRoleDao {
	@Autowired
	private TImsRoleExMapper tImsRoleExMapper;

	public List<TImsRoleDto> findBy(TImsRoleDto tImsRoleDto) {

		List<Roles> roles = tImsRoleExMapper.findBy(tImsRoleDto);

		List<TImsRoleDto> list_role = new ArrayList<TImsRoleDto>();

		if (roles != null && roles.size() > 0) {
			for (int i = 0; i < roles.size(); i++) {
				Roles r1 = roles.get(i);
				TImsRoleDto rodto = new TImsRoleDto();
				BeanUtils.copyProperties(r1, rodto);
				list_role.add(rodto);
			}
			return list_role;
		}

		return null;
	}
	
	public List<RoleMenuDto> findRoleMenu(TImsRoleDto tImsRoleDto){
		List<RoleMenuDto> roles = tImsRoleExMapper.findRoleMenu(tImsRoleDto);
			return roles;
	}

	public List<TImsRoleDto> findByUserId(String user_id){
		
		List<Roles> roles = tImsRoleExMapper.findByUserId(user_id);
		
		List<TImsRoleDto> list_role = new ArrayList<TImsRoleDto>();

		if (roles != null && roles.size() > 0) {
			for (int i = 0; i < roles.size(); i++) {
				Roles r1 = roles.get(i);
				TImsRoleDto rodto = new TImsRoleDto();
				BeanUtils.copyProperties(r1, rodto);
				list_role.add(rodto);
			}
			return list_role;
		}

		return null;
		
	}
	
	
	public List<MenuAndRoleDto> findGreedTree(TImsRoleDto tImsRoleDto){
		
	//	List<Roles> roles = tImsRoleExMapper.findGreedTree(tImsRoleDto);

		List<MenuAndRoles> menuroles =	tImsRoleExMapper.findGreedTree(tImsRoleDto);
		
		List<MenuAndRoleDto> menurodto = new ArrayList<MenuAndRoleDto>();
		
		if(menuroles!=null&&menuroles.size()>0){
			for(int i=0;i<menuroles.size();i++){
				MenuAndRoles mrol = menuroles.get(i);
				MenuAndRoleDto mroldto = new MenuAndRoleDto();
				BeanUtils.copyProperties(mrol, mroldto);
				menurodto.add(mroldto);
			}
			return menurodto;
		}
		return null;
		
	}
	
	public List<TImsMenuDto> finSubMenus(String pid){
		List<TImsMenuDto> subMenus = tImsRoleExMapper.finSubMenus(pid);
		
		return subMenus;
	}
	
	public TImsRoleDto findById(String id){
		Roles roles =  tImsRoleExMapper.findById(id);
		if(roles!=null){
			TImsRoleDto result =new TImsRoleDto();
			BeanUtils.copyProperties(roles, result);
			return result;
		}
		return null;
	}
	
	public TImsRoleDto findByPid(String pid){
		Roles roles =  tImsRoleExMapper.findByPid(pid);
		if(roles!=null){
			TImsRoleDto result =new TImsRoleDto();
			BeanUtils.copyProperties(roles, result);
			return result;
		}
		return null;
		
	}
	
	
	public void inUserRole(TImsUsersRoleDto tImsUsersRoleDto){
		 tImsRoleExMapper.inUserRole(tImsUsersRoleDto);
	}
	
	public void insertRole(TImsRoleDto tImsRoleDto){
		tImsRoleExMapper.insertRole(tImsRoleDto);
	}
	
	public void updateRole(TImsRoleDto tImsRoleDto){
		tImsRoleExMapper.updateRole(tImsRoleDto);
	}
	public void updateUserRole(TImsUsersRoleDto tImsUsersRoleDto){
		tImsRoleExMapper.updateUserRole(tImsUsersRoleDto);
	}
	
	public void updateRoleStat(TImsRoleDto tImsRoleDto){
		tImsRoleExMapper.updateRoleStat(tImsRoleDto);
	}
	
	public void updateRoleMenu(TImsRoleMenuDto tImsRoleMenuDto){
		tImsRoleExMapper.updateRoleMenu(tImsRoleMenuDto);
	}
	
	public void insertRoleMenu(TImsRoleMenuDto tImsRoleMenuDto){
		tImsRoleExMapper.insertRoleMenu(tImsRoleMenuDto);
	}
	
	
}
