package com.ssic.game.admin.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssic.game.admin.dao.ImsRoleDao;
import com.ssic.game.admin.dao.ResourceDao;
import com.ssic.game.admin.dao.RoleDao;
import com.ssic.game.admin.dao.UserDao;
import com.ssic.game.admin.dto.MenuAndRoleDto;
import com.ssic.game.admin.dto.RoleMenuDto;
import com.ssic.game.admin.dto.TImsMenuDto;
import com.ssic.game.admin.dto.TImsRoleDto;
import com.ssic.game.admin.dto.TImsRoleMenuDto;
import com.ssic.game.admin.dto.TImsUsersRoleDto;
import com.ssic.game.admin.mapper.TImsUserRoleExMapper;
import com.ssic.game.admin.model.Tresource;
import com.ssic.game.admin.model.Trole;
import com.ssic.game.admin.pageModel.Role;
import com.ssic.game.admin.pageModel.SessionInfo;
import com.ssic.game.admin.pageModel.Tree;
import com.ssic.game.admin.service.RoleServiceI;
import com.ssic.util.UUIDGenerator;

@Service
public class RoleServiceImpl implements RoleServiceI {

	@Autowired
	private RoleDao roleDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private ResourceDao resourceDao;
	
	@Autowired
	private ImsRoleDao imsRoleDao;
	
	@Autowired
	private TImsUserRoleExMapper userRoleMapper;
	

	
	public void add(Role role, SessionInfo sessionInfo) {
	//	Trole t = new Trole();
	//	BeanUtils.copyProperties(role, t);
	//	if (role.getPid() != null && !role.getPid().equalsIgnoreCase("")) {
	//		t.setTrole(roleDao.get(Trole.class, role.getPid()));
	//	}
		//roleDao.save(t);

		//插入role
		TImsRoleDto roleDto = new TImsRoleDto();
		roleDto.setId(role.getId());
		roleDto.setRole_name(role.getName());
		roleDto.setRemark(role.getRemark());
		roleDto.setSeq(role.getSeq());
		roleDto.setPid(role.getPid());
		imsRoleDao.insertRole(roleDto);
		

		//String pkId=UUIDGenerator.getUUID();
		
		int counts = userRoleMapper.findCountRoleBy(sessionInfo.getId(), role.getId());
		if(counts>0){
			userRoleMapper.updateBy(sessionInfo.getId(), role.getId());
		}else{
			TImsUsersRoleDto uRoleDto = new TImsUsersRoleDto();
			uRoleDto.setId(role.getId());
			uRoleDto.setRole_id(role.getId());
			uRoleDto.setUser_id(sessionInfo.getId());
			imsRoleDao.inUserRole(uRoleDto);
		}
		
		//插入UserRole
		
		// 刚刚添加的角色，赋予给当前的用户
		//Tuser user = userDao.get(Tuser.class, sessionInfo.getId());
		//user.getTroles().add(t);
	}

	
	public Role get(String id) {
		Role r = new Role();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		   TImsRoleDto  troleDto = new TImsRoleDto();
		   troleDto.setId(id);
		   
		List<RoleMenuDto> rMenuDtoList = imsRoleDao.findRoleMenu(troleDto);
		TImsRoleDto roleDto = imsRoleDao.findById(id);
		//------------------------------
	//	Trole t = roleDao.get("select distinct t from Trole t left join fetch t.tresources resource where t.id = :id", params);
		//r.setPid(ram.getPid());
		//r.setPname(ram.getRole_name());
		if (roleDto != null) {
			BeanUtils.copyProperties(roleDto, r);
			r.setName(roleDto.getRole_name());
			if (roleDto.getPid()!= null) {
				r.setPid(roleDto.getPid());
			//	TImsRoleDto roleDto2 = new TImsRoleDto();
				TImsRoleDto roleDto2 = imsRoleDao.findById(roleDto.getPid());
				if(roleDto2==null){
					 roleDto2 = imsRoleDao.findByPid(roleDto.getPid());
				}
				
				r.setPname(roleDto2.getRole_name());
			}
			//Set<Tresource> s = t.getTresources();
			if (rMenuDtoList != null && !rMenuDtoList.isEmpty()) {
				boolean b = false;
				String ids = "";
				String names = "";
				for (RoleMenuDto tr : rMenuDtoList) {
					if (b) {
						ids += ",";
						names += ",";
					} else {
						b = true;
					}
					ids += tr.getMenu_id();
					names += tr.getMenu_name();
				}
				r.setResourceIds(ids);
				r.setResourceNames(names);
			}
		}
		return r;
	}

	
	public void edit(Role role) {
	//	Trole t = roleDao.get(Trole.class, role.getId());
	//	if (t != null) {
	//		BeanUtils.copyProperties(role, t);
			
			//开始修改角色表
			TImsRoleDto roDto = new TImsRoleDto();
			roDto.setRole_name(role.getName());
			roDto.setSeq(role.getSeq());
			roDto.setRemark(role.getRemark());
			roDto.setPid(role.getPid());
			roDto.setId(role.getId());
			imsRoleDao.updateRole(roDto);
			
			
//			if (role.getPid() != null && !role.getPid().equalsIgnoreCase("")) {
//				t.setTrole(roleDao.get(Trole.class, role.getPid()));
//			}
//			if (role.getPid() != null && !role.getPid().equalsIgnoreCase("")) {// 说明前台选中了上级资源
//				Trole pt = roleDao.get(Trole.class, role.getPid());
//				isChildren(t, pt);// 说明要将当前资源修改到当前资源的子/孙子资源下
//				t.setTrole(pt);
//			} else {
//				t.setTrole(null);// 前台没有选中上级资源，所以就置空
//			}
		//}
	}

	/**
	 * 判断是否是将当前节点修改到当前节点的子节点
	 * 
	 * @param t
	 *            当前节点
	 * @param pt
	 *            要修改到的节点
	 * @return
	 */
	private boolean isChildren(Trole t, Trole pt) {
		if (pt != null && pt.getTrole() != null) {
			if (pt.getTrole().getId().equalsIgnoreCase(t.getId())) {
				pt.setTrole(null);
				return true;
			} else {
				return isChildren(t, pt.getTrole());
			}
		}
		return false;
	}

	
	public List<Role> treeGrid(SessionInfo sessionInfo) {
		List<Role> rl = new ArrayList<Role>();
		List<Trole> tl = new ArrayList<Trole>();
		List<MenuAndRoleDto>  list_map_role = new ArrayList<MenuAndRoleDto>();
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> menu_role_map = new HashMap<String, Object>(); 
		List<TImsRoleDto> roles = new ArrayList<TImsRoleDto>();
		if (sessionInfo != null) {
			params.put("userId", sessionInfo.getId());// 查自己有权限的角色
			
			TImsRoleDto roDto = new TImsRoleDto();
			roDto.setPid(sessionInfo.getId());
		   //  roles =imsRoleDao.findBy(roDto);
		    // list_map_role =   imsRoleDao.findGreedTree(roDto);
			// 根据用户id查对应的角色
		     String user_id =sessionInfo.getId();
		     roles = imsRoleDao.findByUserId(user_id);
		     
		     
		     //获取所有的角色
		     List<String> list_role = new ArrayList<String>();
		     if(roles!=null&&roles.size()>0){
		    	 for(int i=0;i<roles.size();i++){
		    		 String role_id = roles.get(i).getId();
		    		 list_role.add(role_id);
		    	 }
		     }
		     
		     //获取角色对应的权限
		    
		     
		     for(int j=0;j<list_role.size();j++){
		    	 String roleids = list_role.get(j);
		    	 roDto.setId(roleids);
		    	 List<MenuAndRoleDto> roleiddto	=   imsRoleDao.findGreedTree(roDto);
		    	 menu_role_map.put(roleids, roleiddto);
		     }
		     
		     
		//	tl = roleDao.find("select distinct t from Trole t left join fetch t.tresources resource join fetch t.tusers user where user.id = :userId order by t.seq", params);
		}
		//else {
		//	tl = roleDao.find("select distinct t from Trole t left join fetch t.tresources resource order by t.seq");
		//}
		
		 if(roles!=null && roles.size()>0){
			 for(TImsRoleDto mdot : roles ){
				 Trole tr_dto = new Trole();
				 tr_dto.setId(mdot.getId());
				 tr_dto.setName(mdot.getRole_name());
				 tr_dto.setRemark(mdot.getRemark());
				 tr_dto.setSeq(mdot.getSeq());
				 tr_dto.setPjNo(mdot.getPid());
				 tl.add(tr_dto);
			 }
			 
		 }
		
		
		if (tl != null && tl.size() > 0) {
			for (Trole t : tl) {
				Role r = new Role();
				BeanUtils.copyProperties(t, r);
				r.setIconCls("status_online");
				//开始增加上级id,上级名称
					r.setPid(t.getPjNo());
					r.setPname(t.getName());
				
				Set<Tresource> s = t.getTresources();
				List<MenuAndRoleDto> s_role =  (List<MenuAndRoleDto>) menu_role_map.get(t.getId());
				if (s_role != null && s_role.size()>0) {
					boolean b = false;
					String ids = "";
					String names = "";
					for (MenuAndRoleDto tr : s_role) {
						if (b) {
							ids += ",";
							names += ",";
						} else {
							b = true;
						}
						ids += tr.getName();
						if(tr.getMenu_name()==null){
							names +="";
						}else{
							names += tr.getMenu_name();
						}
						
					}
				
					r.setResourceIds(ids);
					r.setResourceNames(names);
				}
				rl.add(r);
			}
		}
		return rl;
	}

	
	public void delete(String id) {
		//Trole t = roleDao.get(Trole.class, id);
		//更新t_ims_role 和t_ims_users_role
		TImsRoleDto tImsRoleDto = new TImsRoleDto();
		tImsRoleDto.setId(id);
		tImsRoleDto.setStat(0);
		imsRoleDao.updateRoleStat(tImsRoleDto);
		
		TImsUsersRoleDto tImsUsersRoleDto = new TImsUsersRoleDto();
		tImsUsersRoleDto.setRole_id(id);
		tImsUsersRoleDto.setStat(0);
		imsRoleDao.updateUserRole(tImsUsersRoleDto);
		
	//	del(t);
	}

	
	public List<Tree> tree(SessionInfo sessionInfo) {
		List<Trole> l = new ArrayList<Trole>();
		List<Tree> lt = new ArrayList<Tree>();
		
		List<TImsRoleDto> list_roledto = new ArrayList<TImsRoleDto>();

		Map<String, Object> params = new HashMap<String, Object>();
	
			// 根据用户id查对应的角色
		     String user_id =sessionInfo.getId();
		     list_roledto = imsRoleDao.findByUserId(user_id);
		//	params.put("userId", sessionInfo.getId());// 查自己有权限的角色
		//	l = roleDao.find("select distinct t from Trole t join fetch t.tusers user where user.id = :userId order by t.seq", params);
		

	/*	 if(list_roledto!=null && list_roledto.size()>0){
			 for(TImsRoleDto tdot : list_roledto ){
				 System.out.println(tdot);
				 Trole tr_dto = new Trole();
				 tr_dto.setId(tdot.getId());
				 tr_dto.setName(tdot.getRole_name());
				 tr_dto.setRemark(tdot.getRemark());
				 tr_dto.setSeq(tdot.getSeq());
				 l.add(tr_dto);
			 }
			 
		 }
		
		 
		if (l != null && l.size() > 0) {
			for (Trole t : l) {
				Tree tree = new Tree();
				BeanUtils.copyProperties(t, tree);
				tree.setText(t.getName());
				tree.setIconCls("status_online");
				if (t.getTrole() != null) {
					tree.setPid(t.getTrole().getId());
				}
				lt.add(tree);
			}
		}*/
		
		if (list_roledto != null && list_roledto.size() > 0) {
			for (TImsRoleDto roleDto : list_roledto) {
			        Tree tree = new Tree();
					BeanUtils.copyProperties(roleDto, tree);
					if (!StringUtils.isEmpty(roleDto.getPid())) {
						tree.setPid(roleDto.getPid());
					}
					tree.setText(roleDto.getRole_name());
					// 设置借点url属性
					Map<String, Object> attr = new HashMap<String, Object>();
					tree.setIconCls("status_online");
					lt.add(tree);
			}
		}
		
		return lt;
	}

	
	public List<Tree> allTree(SessionInfo sessionInfo) {
		return this.tree(sessionInfo);
	}

	
	public void grant(Role role) {
		
		// 先  role_menu stat全部更新为0 ，再 插入role_menu 表

	   TImsRoleMenuDto tImsRoleMenuDto = new TImsRoleMenuDto();
	   tImsRoleMenuDto.setRole_id(role.getId());
	   tImsRoleMenuDto.setStat(0);

		imsRoleDao.updateRoleMenu(tImsRoleMenuDto);
		
		
		
		//Trole t = roleDao.get(Trole.class, role.getId());
		if (role.getResourceIds() != null && !role.getResourceIds().equalsIgnoreCase("")) {
			String ids = "";
			boolean b = false;
			for (String id : role.getResourceIds().split(",")) {
				TImsRoleMenuDto tImsRoleMenuDto_2 = new TImsRoleMenuDto();
				
				//查询出该菜单下所有的子菜单并且插入role_menu
				List<TImsMenuDto> listsubmenu =    imsRoleDao.finSubMenus(id);
				
				if(listsubmenu!=null&&listsubmenu.size()>0){
					for(int j=0;j<listsubmenu.size();j++){
						String uui_2=	 UUIDGenerator.getUUID();
						TImsMenuDto msMenuDto = listsubmenu.get(j);
					//	if(role.getResourceIds().contains(msMenuDto.getId())){
							TImsRoleMenuDto tImsRoleMenuDto_3 = new TImsRoleMenuDto();
							tImsRoleMenuDto_3.setId(uui_2);
							tImsRoleMenuDto_3.setRole_id(role.getId());
							tImsRoleMenuDto_3.setMenu_id(msMenuDto.getId());
							tImsRoleMenuDto_3.setStat(1);
							imsRoleDao.insertRoleMenu(tImsRoleMenuDto_3);
					//	}
						
					}
				}
				
				
				String uui=	 UUIDGenerator.getUUID();
				
				tImsRoleMenuDto_2.setId(uui);
				tImsRoleMenuDto_2.setRole_id(role.getId());
				tImsRoleMenuDto_2.setMenu_id(id);
				tImsRoleMenuDto_2.setStat(1);
				imsRoleDao.insertRoleMenu(tImsRoleMenuDto_2);
				
			}
			
			
			
		//	t.setTresources(new HashSet<Tresource>(resourceDao.find("select distinct t from Tresource t where t.id in (" + ids + ")")));
		}
		//else {
		//	t.setTresources(null);
		//}
	}

}
