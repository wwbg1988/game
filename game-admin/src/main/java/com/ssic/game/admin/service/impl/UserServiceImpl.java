package com.ssic.game.admin.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssic.game.admin.dao.ImsRoleDao;
import com.ssic.game.admin.dao.ResourceDao;
import com.ssic.game.admin.dao.TImsMenuDao;
import com.ssic.game.admin.dao.TImsUsersRolesDao;
import com.ssic.game.admin.dao.UserDao;
import com.ssic.game.admin.dto.TImsMenuDto;
import com.ssic.game.admin.dto.TImsUsersDto;
import com.ssic.game.admin.model.Tresource;
import com.ssic.game.admin.model.Trole;
import com.ssic.game.admin.model.Tuser;
import com.ssic.game.admin.pageModel.DataGrid;
import com.ssic.game.admin.pageModel.PageHelper;
import com.ssic.game.admin.pageModel.SessionInfo;
import com.ssic.game.admin.pageModel.Tree;
import com.ssic.game.admin.pageModel.User;
import com.ssic.game.admin.service.UserServiceI;
import com.ssic.game.admin.util.MD5Util;



@Service
public class UserServiceImpl implements UserServiceI {

	@Autowired
	private UserDao userDao;

	@Autowired
	private ImsRoleDao roleDao;
	
	@Autowired
	private ResourceDao resourceDao;
	
	@Autowired
	private TImsMenuDao tImsMenuDao;
	
	@Autowired
	private TImsUsersRolesDao userRoleDao;

	
	
	public TImsUsersDto login(TImsUsersDto user) {
		user.setPassword(MD5Util.md5(user.getPassword()));
		TImsUsersDto result = userDao.login(user);
		return result;
	}

	
	synchronized public void reg(TImsUsersDto user) throws Exception {
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("name", user.getName());
//		if (userDao.count("select count(*) from Tuser t where t.name = :name", params) > 0) {
//			throw new Exception("登录名已存在！");
//		} else {
//			Tuser u = new Tuser();
//			u.setId(UUID.randomUUID().toString());
//			u.setName(user.getName());
//			u.setPwd(MD5Util.md5(user.getPwd()));
//			u.setCreatedatetime(new Date());
//			userDao.save(u);
//		}
		
		userDao.insertBy(user);
		
	}


	public DataGrid dataGrid(TImsUsersDto user, PageHelper ph) {
//		DataGrid dg = new DataGrid();
//		List<User> ul = new ArrayList<User>();
//		Map<String, Object> params = new HashMap<String, Object>();
//		String hql = " from Tuser t ";
//		List<Tuser> l = userDao.find(hql + whereHql(user, params) + orderHql(ph), params, ph.getPage(), ph.getRows());
//		if (l != null && l.size() > 0) {
//			for (Tuser t : l) {
//				User u = new User();
//				BeanUtils.copyProperties(t, u);
//				Set<Trole> roles = t.getTroles();
//				if (roles != null && !roles.isEmpty()) {
//					String roleIds = "";
//					String roleNames = "";
//					boolean b = false;
//					for (Trole tr : roles) {
//						if (b) {
//							roleIds += ",";
//							roleNames += ",";
//						} else {
//							b = true;
//						}
//						roleIds += tr.getId();
//						roleNames += tr.getName();
//					}
//					u.setRoleIds(roleIds);
//					u.setRoleNames(roleNames);
//				}
//				ul.add(u);
//			}
//		}
//		dg.setRows(ul);
//		dg.setTotal(userDao.count("select count(*) " + hql + whereHql(user, params), params));
//		return dg;
		return userDao.findAll(user,ph);
	}

	private String whereHql(User user, Map<String, Object> params) {
		String hql = "";
		if (user != null) {
			hql += " where 1=1 ";
			if (user.getName() != null) {
				hql += " and t.name like :name";
				params.put("name", "%%" + user.getName() + "%%");
			}
			if (user.getCreatedatetimeStart() != null) {
				hql += " and t.createdatetime >= :createdatetimeStart";
				params.put("createdatetimeStart", user.getCreatedatetimeStart());
			}
			if (user.getCreatedatetimeEnd() != null) {
				hql += " and t.createdatetime <= :createdatetimeEnd";
				params.put("createdatetimeEnd", user.getCreatedatetimeEnd());
			}
			if (user.getModifydatetimeStart() != null) {
				hql += " and t.modifydatetime >= :modifydatetimeStart";
				params.put("modifydatetimeStart", user.getModifydatetimeStart());
			}
			if (user.getModifydatetimeEnd() != null) {
				hql += " and t.modifydatetime <= :modifydatetimeEnd";
				params.put("modifydatetimeEnd", user.getModifydatetimeEnd());
			}
		}
		hql += " and t.isDelete=0";
		return hql;
	}

	private String orderHql(PageHelper ph) {
		String orderString = "";
		if (ph.getSort() != null && ph.getOrder() != null) {
			orderString = " order by t." + ph.getSort() + " " + ph.getOrder();
		}
		return orderString;
	}

	
	synchronized public void add(TImsUsersDto user) throws Exception {
		userDao.insertBy(user);
	}

	
	public User get(String id) {
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("id", id);
//		Tuser t = userDao.get("select distinct t from Tuser t left join fetch t.troles role where t.id = :id", params);
//		User u = new User();
//		BeanUtils.copyProperties(t, u);
//		if (t.getTroles() != null && !t.getTroles().isEmpty()) {
//			String roleIds = "";
//			String roleNames = "";
//			boolean b = false;
//			for (Trole role : t.getTroles()) {
//				if (b) {
//					roleIds += ",";
//					roleNames += ",";
//				} else {
//					b = true;
//				}
//				roleIds += role.getId();
//				roleNames += role.getName();
//			}
//			u.setRoleIds(roleIds);
//			u.setRoleNames(roleNames);
//		}
//		return u;
		return null;
	}


	synchronized public void edit(TImsUsersDto user) throws Exception {
		userDao.updateBy(user);
	/*	Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", user.getId());
		params.put("name", user.getName());
		if (userDao.count("select count(*) from Tuser t where t.name = :name and t.id != :id", params) > 0) {
			throw new Exception("登录名已存在！");
		} else {
			Tuser u = userDao.get(Tuser.class, user.getId());
			BeanUtils.copyProperties(user, u, new String[] { "pwd", "createdatetime" });
			u.setModifydatetime(new Date());
		}*/
	}

	
	public void delete(String id) {
		if (id != null) {
			userDao.deleteBy(id);
		}
	
	}

	
	public void grant(String ids, TImsUsersDto user) {
		if (ids != null && ids.length() > 0) {
			List<String> roles = new ArrayList<String>();
			if (user.getRoleIds() != null) {
				for (String roleId : user.getRoleIds().split(",")) {
//					TImsRoleDto temp = roleDao.findById(roleId);
//					if(temp!=null){
//						roles.add(temp);
//					}
					roles.add(roleId);
				}
			}
			userDao.insertRole(roles, ids);

			
		}
	}

	
	public List<String> resourceList(String id) {
		
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("id", id);
//		Tuser t = userDao.get("from Tuser t join fetch t.troles role join fetch role.tresources resource where t.id = :id", params);
//		if (t != null) {
//			Set<Trole> roles = t.getTroles();
//			if (roles != null && !roles.isEmpty()) {
//				for (Trole role : roles) {
//					Set<Tresource> resources = role.getTresources();
//					if (resources != null && !resources.isEmpty()) {
//						for (Tresource resource : resources) {
//							if (resource != null && resource.getUrl() != null) {
//								resourceList.add(resource.getUrl());
//							}
//						}
//					}
//				}
//			}
//		}
		List<String> resourceList = new ArrayList<String>();
		List<String> tempList = userRoleDao.findBy(id);
		if(tempList!=null&&tempList.size()>0){
			List<TImsMenuDto> list = tImsMenuDao.getTree(id);
			if(list!=null&&list.size()>0){
				for(TImsMenuDto tImsMenuDto : list){
					resourceList.add(tImsMenuDto.getUrl());
				}
			}
		}
		
		return resourceList;
	}

	
	public void editPwd(TImsUsersDto user) {
		if (user != null && user.getPassword() != null && !user.getPassword().trim().equalsIgnoreCase("")) {
//			
//			Tuser u = userDao.get(Tuser.class, user.getId());
//			u.setPwd(MD5Util.md5(user.getPwd()));
//			u.setModifydatetime(new Date());
			user.setPassword(MD5Util.md5(user.getPassword()));
			user.setModifydatetime(new Date());
			userDao.updatePwd(user);
			
		}
	}

	
	public boolean editCurrentUserPwd(SessionInfo sessionInfo, String oldPwd, String pwd) {
		TImsUsersDto userDto = new TImsUsersDto();
		userDto.setId(sessionInfo.getId());
		TImsUsersDto resultUser = userDao.login(userDto);
		if(resultUser==null){
			return false;
		}
		
		if (resultUser.getPassword().equalsIgnoreCase(MD5Util.md5(oldPwd))) {// 说明原密码输入正确
			resultUser.setPassword(MD5Util.md5(pwd));
			resultUser.setModifydatetime(new Date());
			userDao.updatePwd(resultUser);
			return true;
		}
		return false;
	}

	
	public List<User> loginCombobox(String q) {
//		if (q == null) {
//			q = "";
//		}
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("name", "%%" + q.trim() + "%%");
//		List<Tuser> tl = userDao.find("from Tuser t where t.name like :name order by name", params, 1, 10);
//		List<User> ul = new ArrayList<User>();
//		if (tl != null && tl.size() > 0) {
//			for (Tuser t : tl) {
//				User u = new User();
//				u.setName(t.getName());
//				ul.add(u);
//			}
//		}
//		return ul;
		return null;
	}

	
	public DataGrid loginCombogrid(String q, PageHelper ph) {
//		if (q == null) {
//			q = "";
//		}
//		DataGrid dg = new DataGrid();
//		List<User> ul = new ArrayList<User>();
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("name", "%%" + q.trim() + "%%");
//		List<Tuser> tl = userDao.find("from Tuser t where t.name like :name order by " + ph.getSort() + " " + ph.getOrder(), params, ph.getPage(), ph.getRows());
//		if (tl != null && tl.size() > 0) {
//			for (Tuser t : tl) {
//				User u = new User();
//				u.setName(t.getName());
//				u.setCreatedatetime(t.getCreatedatetime());
//				u.setModifydatetime(t.getModifydatetime());
//				ul.add(u);
//			}
//		}
//		dg.setRows(ul);
//		dg.setTotal(userDao.count("select count(*) from Tuser t where t.name like :name", params));
//		return dg;
		return null;
	}

	
	public List<Long> userCreateDatetimeChart() {
//		List<Long> l = new ArrayList<Long>();
//		int k = 0;
//		for (int i = 0; i < 12; i++) {
//			Map<String, Object> params = new HashMap<String, Object>();
//			params.put("s", k);
//			params.put("e", k + 2);
//			k = k + 2;
//			l.add(userDao
//					.count("select count(*) from Tuser t where HOUR(t.createdatetime)>=:s and HOUR(t.createdatetime)<:e",
//							params));
//		}
//		return l;
		return null;
	}

	
	public Tuser getCurrentUser() {
	
		return null;
	}

	
	public List<Tree> findUserTree(String userId) {
//		List<Tree> lt = new ArrayList<Tree>();
//		List<Tuser> list = new ArrayList<Tuser>();
//		String hql = "";
//		if (!userId.equals("0")) {
//			hql = "from Tuser  u where  u.id='" + userId + "' and u.isDelete=0";
//		} else {
//			hql = "from Tuser  u where u.isDelete=0 ";
//		}
//		list = userDao.find(hql);
//		if (list.size() > 0) {
//			listTree(list, lt);
//		}
//
//		return lt;
//	}
//
//	public void listTree(List<Tuser> list, List<Tree> lt) {
//		System.out.println("List<Tuser> list:------------------" + list.size());
//		for (Tuser r : list) {
//			Tree tree = new Tree();
//			BeanUtils.copyProperties(r, tree);
//			tree.setText(r.getName());
//			tree.setId(r.getId());
//			tree.setIconCls("rainbow");
//			lt.add(tree);
//		}
		return null;

	}


	public int vailUserAccount(TImsUsersDto userDto) {
		return userDao.findCountByAccount(userDto);
	}


	public TImsUsersDto getUser(String id) {
		TImsUsersDto temp = new TImsUsersDto();
		temp.setId(id);
		return userDao.login(temp);
	}


	public String findUserRole(String userId) {
		List<String> list = userRoleDao.findBy(userId);
		if(list!=null&&list.size()>0){
			String result="";
			for(int i=0;i<list.size();i++){
				if(i==list.size()-1){
					result+=list.get(i);
				}else{
					result+=list.get(i)+",";
				}
			}
			return result;
		}
		return null;
	}


	@Override
	public void addImsUsers(TImsUsersDto user) {
	
		userDao.addImsUsers(user);
		
	}



}
