package com.ssic.game.manage.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssic.game.admin.pageModel.DataGrid;
import com.ssic.game.admin.pageModel.Json;
import com.ssic.game.admin.pageModel.PageHelper;
import com.ssic.game.admin.pageModel.Role;
import com.ssic.game.admin.pageModel.SessionInfo;
import com.ssic.game.admin.pageModel.Tree;
import com.ssic.game.admin.util.ConfigUtil;
import com.ssic.game.common.dto.RoleUsersDto;
import com.ssic.game.common.dto.TmsRoleDto;
import com.ssic.game.common.service.ITmsRoleservice;
import com.ssic.util.UUIDGenerator;


@Controller
@RequestMapping("/imsRoleController")
public class ImsRoleController {

	@Autowired
	private ITmsRoleservice iTmsRoleservice;
	
	@RequestMapping("/findBy")
	@ResponseBody
	public List<TmsRoleDto> findBy(TmsRoleDto tmsRoleDto){
		return iTmsRoleservice.findBy(tmsRoleDto);
	}
	
	@RequestMapping("/insertRole")
	@ResponseBody
	public Json insertRole(TmsRoleDto tmsRoleDto){
		Json j = new Json();
		
		if(tmsRoleDto.getName()==null || tmsRoleDto.getName().equals("")){
			j.setMsg("角色名称不能为空");
			j.setSuccess(false);
			return j;
		}
		if(tmsRoleDto.getName().length()>30){
			j.setMsg("角色名称长度不能大于30");
			j.setSuccess(false);
			return j;
		}
		
		if(tmsRoleDto.getDescribes()!=null){
			if(tmsRoleDto.getDescribes().length()>100){
				j.setMsg("描述长度不能大于100");
				j.setSuccess(false);
				return j;
			}
			
		}
		
		if(tmsRoleDto.getProjId()==null || tmsRoleDto.getProjId().equals("")){
			  j.setMsg("项目不能为空");
			  j.setSuccess(false);
			  return j;
		}
		
		
		tmsRoleDto.setId(UUIDGenerator.getUUID());
		tmsRoleDto.setStat(1);
		iTmsRoleservice.insertRole(tmsRoleDto);
		
		j.setMsg("创建角色成功");
		j.setSuccess(true);
		
		return j;
	}
	
	@RequestMapping("/updateRole")
	@ResponseBody
	public Json updateRole(TmsRoleDto tmsRoleDto){
		Json j = new Json();
		
		if(tmsRoleDto.getName()==null || tmsRoleDto.getName().equals("")){
			j.setMsg("角色名称不能为空");
			j.setSuccess(false);
			return j;
		}
		if(tmsRoleDto.getName().length()>30){
			j.setMsg("角色名称长度不能大于30");
			j.setSuccess(false);
			return j;
		}
		
		if(tmsRoleDto.getDescribes()!=null){
			if(tmsRoleDto.getDescribes().length()>100){
				j.setMsg("描述长度不能大于100");
				j.setSuccess(false);
				return j;
			}
			
		}
		

		iTmsRoleservice.updateRole(tmsRoleDto);
		
		j.setMsg("角色更新成功");
		j.setSuccess(true);
		return j;
	}
	
	@RequestMapping("/deleteRole")
	@ResponseBody
	public Json deleteRole(TmsRoleDto tmsRoleDto){
		Json j = new Json();
		if(tmsRoleDto.getId()==null || tmsRoleDto.getId().equals("")){
			j.setMsg("删除对象不能为空");
			j.setSuccess(false);
			return j;
		}
		iTmsRoleservice.deleteRole(tmsRoleDto);
		j.setMsg("删除角色成功");
		j.setSuccess(true);
		return j;
	}
	
	@ResponseBody
	@RequestMapping("/dataGrid")
    public DataGrid dataGrid(TmsRoleDto tmsRoleDto, com.ssic.game.common.pageModel.PageHelper ph){
		DataGrid dataGrid = new DataGrid();
		List<TmsRoleDto> list = iTmsRoleservice.findByAll(tmsRoleDto,ph);
		int count = iTmsRoleservice.findCount(tmsRoleDto);
		
		if(list!=null && list.size()>0){
			for(TmsRoleDto roleDtp :list){
				String roleId = roleDtp.getId();
				RoleUsersDto ruDto = new RoleUsersDto();
				ruDto.setRoleId(roleId);
				List<RoleUsersDto> listru= iTmsRoleservice.findUserNames(ruDto);
				String user_ids="";
				if(listru!=null && listru.size()>0){
					for(RoleUsersDto roleUsersDto:listru){
						user_ids = user_ids + roleUsersDto.getUserNames()+",";
					}
					user_ids = user_ids.substring(0, user_ids.length()-1);
				}
				roleDtp.setUserNames(user_ids);
			}
		}
		dataGrid.setRows(list);
		dataGrid.setTotal(Long.valueOf(count));
    	return dataGrid;
    }
	
	@RequestMapping("/manager")
	public String manager(){
		return "ims/tImsRole";
	}
	
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request,String projid) {
		request.setAttribute("projid", projid);
		return "ims/tImsRoleAdd";
	}
	
	@RequestMapping("/editRole")
	public String editProcess(HttpServletRequest request, String id){
	    TmsRoleDto  tmsRoleDto = new TmsRoleDto();
	    TmsRoleDto rDto = new TmsRoleDto();
	    tmsRoleDto.setId(id);
	    List<TmsRoleDto> listrole = iTmsRoleservice.findBy(tmsRoleDto);
	    if(listrole!=null && listrole.size()>0){
	    	rDto = listrole.get(0);
	    }
		request.setAttribute("rDto", rDto);
	
		return "ims/tImsRoleEdit";
	}
	
	 @RequestMapping("/grantUserPage")
	 public String grantUserPage(String ids, HttpServletRequest request,HttpSession session)
	    {
	        request.setAttribute("ids", ids);
	        session.setAttribute("grantUserPerss", ids);
	        Role u = new Role();
	        if (ids != null && !ids.equalsIgnoreCase(""))
	        {
	        String result =	iTmsRoleservice.findUserPers(ids);
	            u.setResourceIds(result);
	            request.setAttribute("role", u);
	        }
	        return "ims/tImsRoleGrant";
	    }
	 
	    /**
	     * 角色树(只能看到自己拥有的角色)
	     * 
	     * @return
	     */
	    @RequestMapping("/userTree")
	    @ResponseBody
	    public List<com.ssic.game.common.pageModel.Tree> userTree(String searchName)
	    {
	        return iTmsRoleservice.userTree(searchName);
	    }
	    
	    /**
	     * 用户授权
	     * 
	     * @param ids
	     * @return
	     */
	    @RequestMapping("/grantUsers")
	    @ResponseBody
	    public Json grantUsers(String resourceIds,HttpSession session) {
	        Json j = new Json();

	        if(session.getAttribute("grantUserPerss")!=null){
	        	
	        	iTmsRoleservice.grantUser(session.getAttribute("grantUserPerss").toString(), resourceIds);
	        
	            j.setSuccess(true);
	            j.setMsg("授权成功！");
	            return j;
	        }else{
	            j.setSuccess(false);
	            j.setMsg("寻找不到动作id，请重新赋用户权限!");
	            return j;
	        }
	    }
	    
	    /**
		 * 角色树(只能看到自己拥有的角色)
		 * 
		 * @return
		 */
		@RequestMapping("/tree")
		@ResponseBody
		public List<com.ssic.game.common.pageModel.Tree> tree(HttpSession session,String userId) {
			SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.SESSIONINFONAME);
			return iTmsRoleservice.tree(userId);
			//return roleService.tree(sessionInfo);
		}
	
}
