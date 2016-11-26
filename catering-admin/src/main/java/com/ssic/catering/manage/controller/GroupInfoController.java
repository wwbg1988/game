package com.ssic.catering.manage.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gotye.entity.RespGroup;
import com.gotye.entity.resp.group.DismissGroupResp;
import com.ssic.catering.admin.pageModel.DataGrid;
import com.ssic.catering.admin.pageModel.Json;
import com.ssic.catering.admin.pageModel.Role;
import com.ssic.catering.base.dto.GroupInfoDto;
import com.ssic.catering.base.dto.GroupUserDto;
import com.ssic.catering.base.dto.QjyCateringUserDto;
import com.ssic.catering.base.dto.Tree;
import com.ssic.catering.base.pojo.PageHelper;
import com.ssic.catering.base.service.IGroupInfoService;
import com.ssic.catering.base.service.IGroupUserService;
import com.ssic.catering.base.service.IQjyCateringService;
import com.ssic.game.common.dto.DeptDto;
import com.ssic.game.common.dto.ImsUsersDto;
import com.ssic.game.common.dto.ProjectDto;
import com.ssic.game.common.service.IImsUserService;
import com.ssic.util.UUIDGenerator;


@Controller
@RequestMapping("/groupInfoController")
public class GroupInfoController {

	@Autowired
	private IGroupInfoService groupInfoService;
	@Autowired
	private IGroupUserService groupUserService;
	@Autowired
	private IImsUserService imsUserService;
	@Autowired
	private IQjyCateringService qjyCateringService;
	
	
	@RequestMapping("/findBy")
	@ResponseBody
	public List<GroupInfoDto> findBy(GroupInfoDto groupInfoDto){
		List<GroupInfoDto> listinfo = groupInfoService.findBy(groupInfoDto);
		if(listinfo!=null && listinfo.size()>0){
			for(GroupInfoDto gidto : listinfo){
				//获取创建者名称
				String createUserid =gidto.getCreateuserid();
				String createUserName ="";
				if(createUserid!=null && !"".equals(createUserid)){
					ImsUsersDto imsUDto = imsUserService.findByUserId(createUserid);
					if(imsUDto!=null){
						createUserName = imsUDto.getName();
					}
				}
				gidto.setCreateusername(createUserName);
				
				String id = gidto.getId();
				GroupUserDto gudto = new GroupUserDto();
				gudto.setInfoId(id);
				List<GroupUserDto> listuser = groupUserService.findBy(gudto);
				//查询这个群组下有什么用户
				String users = "";
				if(listuser!=null && listuser.size()>0){
					for(GroupUserDto gudto2 : listuser){
						String userID = gudto2.getUserId();
						ImsUsersDto imsUDto2 =imsUserService.findByUserId(userID);
						String userName = "";
						if(imsUDto2!=null){
							userName = imsUserService.findByUserId(userID).getName();
						}
						users =  users +userName+","; 
					}
				}
				//users = users + createUserName;
				if(users!=null && !"".equals(users)){
					users = users.substring(0, users.length()-1);
				}
				
				gidto.setUsers(users);
			}
		}	
		return listinfo;
	}
	
	
	@RequestMapping("/findFirst")
	@ResponseBody
	public List<GroupInfoDto> findFirst(GroupInfoDto groupInfoDto){
		return groupInfoService.findFirst(groupInfoDto);
	}
	
	
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request){
		return "carte/group/groupInfo";
	}
	
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request){
	    return "carte/group/groupAdd";
	}
	
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request,String id){
		GroupInfoDto groupInfoDto = groupInfoService.findById(id);
		request.setAttribute("gdto", groupInfoDto);
		request.setAttribute("id", id);
		return "carte/group/groupEdit";
	}
	
	 @RequestMapping("/grantUserPage")
	 public String grantUserPage(String ids, HttpServletRequest request,HttpSession session)
	    {
	        request.setAttribute("ids", ids);
	        session.setAttribute("grantUserPerss", ids);
	        Role u = new Role();
	        if (ids != null && !ids.equalsIgnoreCase(""))
	        {
	        	String result =   groupUserService.findByGroupUser(ids);
	            u.setResourceIds(result);
	            request.setAttribute("role", u);
	        }
	        return "carte/group/groupAddUser";
	    }
	
	    @RequestMapping("/grantUsers")
	    @ResponseBody
	    public Json grantUsers(String resourceIds,HttpSession session) {
	        Json j = new Json();
	        if(session.getAttribute("grantUserPerss")!=null){
	        	//cQjyFriendService.grantUser(session.getAttribute("grantUserPerss").toString(), resourceIds);
	        	groupUserService.grantUser(session.getAttribute("grantUserPerss").toString(), resourceIds);
	            j.setSuccess(true);
	            j.setMsg("添加好友成功！");
	            return j;
	        }else{
	            j.setSuccess(false);
	            j.setMsg("寻找不到用户id，请重新添加好友!");
	            return j;
	        }


	    }
	
	 
	 
	@RequestMapping("/tree")
	@ResponseBody
	public List<Tree> tree(){
	    return groupInfoService.tree();
	}
	
	
	@RequestMapping("/insertGroupInfo")
	@ResponseBody
	public Json insertGroupInfo(GroupInfoDto groupInfoDto,HttpServletRequest request){
		Json j = new Json();
	
		if(groupInfoDto.getGroupName()==null || "".equals(groupInfoDto.getGroupName())){
			j.setMsg("群名称不能为空!");
			j.setSuccess(false);
			return j;
		}
		
		if(groupInfoDto.getCreateuserid()==null || "".equals(groupInfoDto.getCreateuserid())){
			j.setMsg("创建者ID不能为空");
			j.setSuccess(false);
			return j;
		}
		//调用亲加云建群，创建成功后，获取groupid
		QjyCateringUserDto qjyCateringUserDto = new QjyCateringUserDto();
		qjyCateringUserDto.setGroupName(groupInfoDto.getGroupName());
		ImsUsersDto imsUsersDto = imsUserService.findByUserId(groupInfoDto.getCreateuserid());
		String qjAccount = imsUsersDto.getQjyAccount();
		qjyCateringUserDto.setOwnerAccount(qjAccount);
		//  ---亲加云旧文档 创建群
	//	Map<String, Object> result=  qjyCateringService.createGroup(qjyCateringUserDto);
		//  ---亲加云新文档  创建群
		Map<String, Object> result=   qjyCateringService.createGroupNew(qjyCateringUserDto);
		String erroecode = (String) result.get("error_code");
		
		if(!"200".equals(erroecode)){
			j.setMsg("亲加云创建群组失败，错误码："+erroecode);
			j.setSuccess(false);
			return j;
		}
		
		RespGroup data = (RespGroup) result.get("data");
		String groupId = data.getGroupId().toString();
		//JSONObject aa =	(JSONObject) result.get("data");
		//String groupId = aa.getString("group_id");
	
		if(groupId==null || "".equals(groupId)){
			j.setMsg("群ID不能为空!");
			j.setSuccess(false);
			return j;
		}
		String infoId = UUIDGenerator.getUUID();
		groupInfoDto.setGroupId(groupId);
		groupInfoDto.setId(infoId);
		groupInfoDto.setCreateTime(new Date());
		groupInfoDto.setStat(1);
		groupInfoDto.setParentId(groupInfoDto.getPid());
		groupInfoService.insertGroupInfo(groupInfoDto);
		//同步的插入groupuser
		GroupUserDto groupUserDto = new GroupUserDto();
		groupUserDto.setId(UUIDGenerator.getUUID());
		groupUserDto.setInfoId(infoId);
		groupUserDto.setUserId(groupInfoDto.getCreateuserid());
		groupUserDto.setQjyAccount(imsUsersDto.getQjyAccount());
		groupUserDto.setUserAccount(imsUsersDto.getUserAccount());
		groupUserDto.setStat(1);
		groupUserDto.setCreateTime(new Date());
		groupUserService.insertGroupUser(groupUserDto);
		
		j.setMsg("插入群信息成功！");
		j.setSuccess(true);
		return j;
	}
	
	@RequestMapping("/updateGroupInfo")
	@ResponseBody
	public Json updateGroupInfo(GroupInfoDto groupInfoDto){
		Json j = new Json();
		if(groupInfoDto.getId()==null || "".equals(groupInfoDto.getId())){
			j.setMsg("群ID不能为空!");
			j.setSuccess(false);
			return j;
		}
		if(groupInfoDto.getGroupName()==null || "".equals(groupInfoDto.getGroupName())){
			j.setMsg("群名称不能为空!");
			j.setSuccess(false);
			return j;
		}
		groupInfoDto.setLastUpdateTime(new Date());
		groupInfoService.updateGroupInfo(groupInfoDto);
		j.setMsg("更新群信息成功！");
		j.setSuccess(true);
		return j;
	}
	
	@RequestMapping("/deleteGroupInfo")
	@ResponseBody
	public Json deleteGroupInfo(GroupInfoDto groupInfoDto){
		Json j = new Json();
		if(groupInfoDto.getId()==null || "".equals(groupInfoDto.getId())){
			j.setMsg("ID不能为空！");
			j.setSuccess(false);
			return j;
		}
		groupInfoDto.setLastUpdateTime(new Date());
		groupInfoDto.setStat(0);
		groupInfoService.updateGroupInfo(groupInfoDto);
		
		GroupUserDto groupUserDto2 = new GroupUserDto();
		groupUserDto2.setInfoId(groupInfoDto.getId());
		List<GroupUserDto> list = groupUserService.findBy(groupUserDto2);
		if(list!=null && list.size()>0){
			for(GroupUserDto groupUserDto3 : list){
				GroupUserDto groupUserDto = new GroupUserDto();
				groupUserDto.setStat(0);
				groupUserDto.setId(groupUserDto3.getId());
				groupUserDto.setLastUpdateTime(new Date());
				groupUserService.updateGroupUser(groupUserDto);
			}
		}
		//调用亲加云删除群
		// 亲加云新文档   删除群
		QjyCateringUserDto qjyCateringUserDto = new QjyCateringUserDto();
		qjyCateringUserDto.setGroupId(groupInfoService.findById(groupInfoDto.getId()).getGroupId());
		Map<String, Object> map =  qjyCateringService.dismissGroup(qjyCateringUserDto);
		
		String error_code = (String) map.get("error_code");
		if(!"200".equals(error_code)){
			j.setMsg("亲加云删除群组失败");
			j.setSuccess(false);
			return j;
		}
		
		j.setMsg("删除群成功!");
		j.setSuccess(true);
		return j;
	}
	
	
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(GroupInfoDto groupInfoDto ,PageHelper ph){
		DataGrid dataGrid = new DataGrid();
		List<GroupInfoDto> list = groupInfoService.findBy(groupInfoDto,ph);
		int count = groupInfoService.findCount(groupInfoDto);
		dataGrid.setRows(list);
		dataGrid.setTotal(Long.valueOf(count));
		return dataGrid;
	}
	
	
	
}
