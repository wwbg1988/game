package com.ssic.game.common.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssic.game.common.dao.MenuDao;
import com.ssic.game.common.dao.RoleMenuDao;
import com.ssic.game.common.dto.RoleMenuDto;
import com.ssic.game.common.pojo.Menu;
import com.ssic.game.common.pojo.RoleMenu;
import com.ssic.game.common.service.IRoleMenuService;
import com.ssic.util.BeanUtils;
import com.ssic.util.StringUtils;
import com.ssic.util.UUIDGenerator;

@Service
public class RoleMenuServiceImpl implements IRoleMenuService{

	@Autowired
	private RoleMenuDao roleMenuDao;
	
	@Autowired
	private MenuDao menuDao;
	
	@Override  
	public List<RoleMenuDto> findBy(RoleMenuDto roleMenuDto) {
		// TODO Auto-generated method stub
		RoleMenu roleMenu = new RoleMenu();
		BeanUtils.copyProperties(roleMenuDto, roleMenu);;
		List<RoleMenu> list= roleMenuDao.findBy(roleMenu);
		List<RoleMenuDto> listdto = BeanUtils.createBeanListByTarget(list, RoleMenuDto.class);
		return listdto;
	}

	public RoleMenuDto findById(String id) {
		// TODO Auto-generated method stub
		RoleMenu roleMenu = roleMenuDao.findById(id);
		RoleMenuDto roleMenuDto = new RoleMenuDto();
		BeanUtils.copyProperties(roleMenu, roleMenuDto);
		return roleMenuDto;
	}

	@Override
	public void insertRoleMenu(RoleMenuDto roleMenuDto) {
		// TODO Auto-generated method stub
		RoleMenu roleMenu = new RoleMenu();
		BeanUtils.copyProperties(roleMenuDto, roleMenu);
		roleMenuDao.insertRoleMenu(roleMenu);
	}

	@Override
	public void updateRoleMenu(RoleMenuDto roleMenuDto) {
		// TODO Auto-generated method stub
		RoleMenu roleMenu = new RoleMenu();
		BeanUtils.copyProperties(roleMenuDto, roleMenu);
		roleMenuDao.updateRoleMenu(roleMenu);
	}

	@Override
	public void deleteRoleMenu(RoleMenuDto roleMenuDto) {
		// TODO Auto-generated method stub
		RoleMenu roleMenu = new RoleMenu();
		BeanUtils.copyProperties(roleMenuDto, roleMenu);
		roleMenuDao.updateRoleMenu(roleMenu);
	}

	
	 /** 
	 * (non-Javadoc)   
	 * @see com.ssic.game.common.service.IRoleMenuService#menuTree(java.lang.String)   
	 * 
	 * @author yuanbin
	 */
	//查询该角色下绑定的菜单
	@Override
	public List<RoleMenuDto> menuTree(RoleMenuDto roleMenuDto) {
		
		RoleMenu roleMenu = new RoleMenu();
		
		List<RoleMenuDto> result = new ArrayList<RoleMenuDto>();
		BeanUtils.copyProperties(roleMenuDto, roleMenu);
		List<RoleMenu> menulist= roleMenuDao.findBy(roleMenu);
		 if(menulist!=null && menulist.size()>0){
	        	for(RoleMenu roleMenus:menulist){
	        		RoleMenuDto tree = new RoleMenuDto();
	        			tree.setId(roleMenus.getId());
	        			tree.setRoleId(roleMenus.getRoleId());
	        			tree.setMenuId(roleMenus.getMenuId());
	        			tree.setStat(1);
	        			result.add(tree);
	        		}
	        	}
		return result;
	}

	
	 /** 
	 * (non-Javadoc)   
	 * @see com.ssic.game.common.service.IRoleMenuService#tree(java.lang.String)
	 * 
	 *    @author yuanbin
	 */
	@Override
	public List<RoleMenuDto> tree(String roleId) {
	
	    //查询角色綁定的菜單
		List<RoleMenuDto> result =new ArrayList<RoleMenuDto>();
		RoleMenuDto roleMenuDto = new RoleMenuDto();
		roleMenuDto.setStat(1);
		RoleMenu roleMenu=new RoleMenu();
		BeanUtils.copyProperties(roleMenuDto, roleMenu);
		 List<RoleMenu> listr1= roleMenuDao.findBy(roleMenu);
		 BeanUtils.copyProperties(listr1, roleMenuDto);
		 result.add(roleMenuDto);
		return result;
		
		
	}

	
	 /** 
	 * (non-Javadoc)   
	 * @see com.ssic.game.common.service.IRoleMenuService#grantMenu(java.lang.String, java.lang.String)   
	 */
	@Override
	public void grantMenu(String roleId, String menuIds) {		
		
		// 先  role_menu stat全部更新为0 ，再 插入role_menu 表

//	       RoleMenuDto roleMenuDto = new RoleMenuDto();
//	       roleMenuDto.setId(roleId);
//	       roleMenuDto.setStat(0);

	       roleMenuDao.unGrantByRoleId(roleId);    
	        
	        
	        if (!StringUtils.isEmpty(menuIds)) {
	            for (String id : menuIds.split(",")) {
	                Menu param = new Menu();
	                param.setPid(id);
	                param.setStat(1);
	                
	                //查询出该菜单下所有的子菜单并且插入role_menu
	                List<Menu> listsubmenu = menuDao.findBy(param);	                
	              
	                if(listsubmenu!=null&&listsubmenu.size()>0){
	                    for(int j=0;j<listsubmenu.size();j++){                    

	                        Menu m = listsubmenu.get(j);
	                        
                            RoleMenu roleMenu = new RoleMenu();
                            roleMenu.setId(UUIDGenerator.getUUID());
                            roleMenu.setRoleId(roleId);
                            roleMenu.setMenuId(m.getId());
                            roleMenu.setCreateTime(new Date());
                            roleMenu.setStat(1);
                            
                            roleMenuDao.insertRoleMenu(roleMenu);
	                    }
	                }            
	                
	                //更新	                
	                RoleMenu rm = new RoleMenu();
	                rm.setId(UUIDGenerator.getUUID());
	                rm.setRoleId(roleId);
	                rm.setMenuId(id);
	                rm.setCreateTime(new Date());
	                rm.setStat(1);
                    roleMenuDao.insertRoleMenu(rm);	                
	            }
	            
	        }
	}

}
