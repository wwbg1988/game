package com.ssic.game.app.controller.catering;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;



























import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.internal.LinkedTreeMap;
import com.gotye.entity.RespSimpleGroup;
import com.gotye.entity.RespUser;
import com.gotye.entity.resp.group.GetGroupMembersResp;
import com.gotye.entity.resp.group.GetGroupsResp;
import com.gotye.entity.resp.user.GetFriendsResp;
import com.ssic.catering.base.dto.AppGroupDeptDto;
import com.ssic.catering.base.dto.AppGroupDeptUserDto;
import com.ssic.catering.base.dto.GroupInfoDto;
import com.ssic.catering.base.dto.GroupUserDto;
import com.ssic.catering.base.service.IGroupInfoService;
import com.ssic.catering.base.service.IGroupUserService;
import com.ssic.catering.base.service.IMeetingService;
import com.ssic.catering.common.util.AppResponse;
import com.ssic.game.app.controller.dto.AppGroupUserDto;
import com.ssic.game.app.handler.MultipleTree;
import com.ssic.game.app.handler.Node;
import com.ssic.game.common.dao.DeptUserDao;
import com.ssic.game.common.dao.ImsUserDao;
import com.ssic.game.common.dto.DeptDto;
import com.ssic.game.common.dto.DeptUsersDto;
import com.ssic.game.common.dto.ImUserDto;
import com.ssic.game.common.dto.ImsUsersDto;
import com.ssic.game.common.dto.ProjectUsersDto;
import com.ssic.game.common.service.DeptUserService;
import com.ssic.game.common.service.IDeptService;
import com.ssic.game.common.service.IImsUserService;
import com.ssic.game.common.service.ProjectServices;
import com.ssic.game.im.dto.GetGroupsRespDto;
import com.ssic.game.im.dto.RespSimpleGroupDto;
import com.ssic.game.im.service.IQjyImService;
import com.ssic.util.BeanUtils;
import com.ssic.util.UUIDGenerator;
import com.ssic.util.model.Response;


@Controller
@RequestMapping("/appGroupController")
public class AppGroupController {

	@Autowired
	private IGroupInfoService groupInfoService;
	@Autowired
	private IGroupUserService groupUserService;
	@Autowired
	private  IQjyImService iQjyImService;
	@Autowired
	private IImsUserService iImsUserService;
	@Autowired
	private IMeetingService meetingService;
	@Autowired
    private ProjectServices projectServices;
	@Autowired
	private IDeptService deptService;
	@Autowired
	private DeptUserService deptUserService;
	

	
	@RequestMapping("/findGroup.do")
	@ResponseBody
	public Response<Node> findGroup(String userId){
		long startTime=System.currentTimeMillis();   //获取开始时间
		
		Response<Node> result = new Response<Node>();
		GroupInfoDto groupInfoDto = new GroupInfoDto();
		//获取全部的群列表，t_ctr_group_info中的群是固定的组织结构，剩下的是全部的临时群按list返回
		List<AppGroupUserDto> listAllGroup = new ArrayList<AppGroupUserDto>();   //  全部的群列表
	
		if(userId==null || "".equals(userId)){
			result.setMessage("用户编号不能为空");
			result.setStatus(AppResponse.RETURN_FAILE);
			return result;
		}
		
	    ImsUsersDto imsUsersDto	= iImsUserService.findByUserId(userId);
	    if(imsUsersDto==null){
	    	result.setMessage("该用户不存在");
	    	result.setStatus(AppResponse.RETURN_FAILE);
	    	return result;
	    }
	    
		String projId="";
		Map<String, Object> mapproj = findProjByUserId(userId);
		Boolean projState = (Boolean) mapproj.get("SUCCESS");
		if(projState){
			ProjectUsersDto projectUsersDto = (ProjectUsersDto) mapproj.get("projectUsersDto");
			projId =projectUsersDto.getProjId();
		}else{
			 result.setMessage("项目ID不存在！");
			 result.setStatus(AppResponse.RETURN_FAILE);
			 return result;
		}
		groupInfoDto.setProjId(projId);
	    
	    long endTime2=System.currentTimeMillis(); //获取结束时间
		System.out.println("开始连接亲加云运行时间： "+(endTime2-startTime)+"ms");
		//查询缓存
		GetGroupsRespDto getGroupsRespDto =	iQjyImService.findRadisGroup(imsUsersDto.getQjyAccount());
		List<RespSimpleGroupDto> listresp = getGroupsRespDto.getList();
		List<RespSimpleGroup> listen = BeanUtils.createBeanListByTarget(listresp, RespSimpleGroup.class);
		String  error_code = getGroupsRespDto.getStatus();
		Map<String,Object> result11 = new HashMap<String, Object>();
		result11.put("error_code", error_code);
		result11.put("data", listen);
		//Map<String, Object> result11=   iQjyImService.findNewGroups(imsUsersDto.getQjyAccount());
		long endTime3=System.currentTimeMillis(); //获取结束时间
		System.out.println("连接结束亲加云运行时间： "+(endTime3-startTime)+"ms");  
		
		List oo = (List) result11.get("data");
		if(oo!=null && oo.size()>0){
			for(int j=0;j<oo.size();j++){
				RespSimpleGroup rsgoo = (RespSimpleGroup) oo.get(j);
				String groupidoo=  rsgoo.getGroupId().toString();
				String groupNameoo = rsgoo.getGroupName();
				//查询这个groupid是否存在
				GroupInfoDto gidtokk = new GroupInfoDto();
				gidtokk.setGroupId(groupidoo);
			    int countkk = groupInfoService.findCount(gidtokk);
				if(countkk==0){
					AppGroupUserDto kkagu = new AppGroupUserDto();
					kkagu.setGroupId(groupidoo);
					kkagu.setGroupName(groupNameoo);
					listAllGroup.add(kkagu);	
				}
			}
		}
		
		//查询所有群的层级关系，parentid 为空时是最上层
	    List<GroupInfoDto> list = groupInfoService.findBy(groupInfoDto);
		//找出  listAllGroup 中 不存在 list的群
		List<Node> nodes = new ArrayList<Node>();
		List dataList = new ArrayList();
		if(list!=null && list.size()>0){
			for(GroupInfoDto grinfo2 :  list){
				HashMap dataRecord1 = new HashMap();  
				  dataRecord1.put("id", grinfo2.getId());  
				  dataRecord1.put("text", grinfo2.getGroupName());  
				  dataRecord1.put("parentId", grinfo2.getParentId());
				  dataRecord1.put("groupId", grinfo2.getGroupId());  
				  dataList.add(dataRecord1);
			}
			
			long endTime4=System.currentTimeMillis(); //获取结束时间
			System.out.println("开始分组树亲加云运行时间： "+(endTime4-startTime)+"ms");  
			MultipleTree multipleTree = new MultipleTree();
			//Node node =   multipleTree.multipleTree(dataList);
			long endTime5=System.currentTimeMillis(); //获取结束时间
			System.out.println("结束分组树亲加云运行时间： "+(endTime5-startTime)+"ms");  
			Node node =sortlist(dataList);
			//加入剩余的群信息
			node.setListnode(listAllGroup);
			result.setStatus(AppResponse.RETURN_SUCCESS);
			result.setData(node);
			result.setMessage("群信息返回成功!");
			
			long endTime=System.currentTimeMillis(); //获取结束时间
			System.out.println("获取群信息程序运行时间： "+(endTime-startTime)+"ms");
			return result;
 		}else{
			result.setMessage("群为空");
			result.setStatus(AppResponse.RETURN_FAILE);
			long endTime=System.currentTimeMillis(); //获取结束时间
			System.out.println("获取群信息程序运行时间： "+(endTime-startTime)+"ms");
			return result;
		}
	}
	
	//查询所有的部门
	@RequestMapping("/findAllDept.do")
	@ResponseBody
	public Response<Node> findAllDept(String userId){
		long startTime=System.currentTimeMillis();   //获取开始时间
		
		Response<Node> result = new Response<Node>();
		GroupInfoDto groupInfoDto = new GroupInfoDto();
		//获取全部的群列表，t_ctr_group_info中的群是固定的组织结构，剩下的是全部的临时群按list返回
		List<AppGroupUserDto> listAllGroup = new ArrayList<AppGroupUserDto>();   //  全部的群列表
	
		if(userId==null || "".equals(userId)){
			result.setMessage("用户编号不能为空");
			result.setStatus(AppResponse.RETURN_FAILE);
			return result;
		}
		
	    ImsUsersDto imsUsersDto	= iImsUserService.findByUserId(userId);
	    if(imsUsersDto==null){
	    	result.setMessage("该用户不存在");
	    	result.setStatus(AppResponse.RETURN_FAILE);
	    	return result;
	    }
	    
		String projId="";
		Map<String, Object> mapproj = findProjByUserId(userId);
		Boolean projState = (Boolean) mapproj.get("SUCCESS");
		if(projState){
			ProjectUsersDto projectUsersDto = (ProjectUsersDto) mapproj.get("projectUsersDto");
			projId =projectUsersDto.getProjId();
		}else{
			 result.setMessage("项目ID不存在！");
			 result.setStatus(AppResponse.RETURN_FAILE);
			 return result;
		}
		groupInfoDto.setProjId(projId);
	    
	    long endTime2=System.currentTimeMillis(); //获取结束时间
		System.out.println("开始连接亲加云运行时间： "+(endTime2-startTime)+"ms");

		//查询所有群的层级关系，parentid 为空时是最上层
	 //   List<GroupInfoDto> list = groupInfoService.findBy(groupInfoDto);
	    
	    DeptDto deptDto2 = new DeptDto();
	    deptDto2.setStat(1);
	    deptDto2.setProjId(projId);
	    List<DeptDto> listdept= deptService.findBy(deptDto2);
		//找出  listAllGroup 中 不存在 list的群
		List<Node> nodes = new ArrayList<Node>();
		List dataList = new ArrayList();
		
		if(listdept!=null && listdept.size()>0){
			for(DeptDto deptDto3 : listdept){
				HashMap dataRecord1 = new HashMap();  
				  dataRecord1.put("id", deptDto3.getId());  
				  dataRecord1.put("text", deptDto3.getDeptName());  
				  dataRecord1.put("parentId", deptDto3.getPid());
				  dataRecord1.put("createtime", deptDto3.getCreateTime());
				//  dataRecord1.put("groupId", grinfo2.getGroupId());  
				  dataList.add(dataRecord1);
			}
			MultipleTree multipleTree = new MultipleTree();
			//Node node =   multipleTree.multipleTree(dataList);
			long endTime5=System.currentTimeMillis(); //获取结束时间
			System.out.println("结束分组树亲加云运行时间： "+(endTime5-startTime)+"ms");  
			Node node =sortlist(dataList);
			//加入剩余的群信息
			node.setListnode(listAllGroup);
			result.setStatus(AppResponse.RETURN_SUCCESS);
			result.setData(node);
			result.setMessage("群信息返回成功!");
			
			long endTime=System.currentTimeMillis(); //获取结束时间
			System.out.println("获取群信息程序运行时间： "+(endTime-startTime)+"ms");
			return result;
		}else{
			result.setMessage("群为空");
			result.setStatus(AppResponse.RETURN_FAILE);
			long endTime=System.currentTimeMillis(); //获取结束时间
			System.out.println("获取群信息程序运行时间： "+(endTime-startTime)+"ms");
			return result;
		}
	}
	
	//查询部门下的用户
	@RequestMapping("/findUsersByDept.do")
	@ResponseBody
	public Response<List<ImsUsersDto>> findUsersByDept(String deptId ,String userId){
		Response<List<ImsUsersDto>> result = new Response<List<ImsUsersDto>>();
		List<ImsUsersDto> listUser = new ArrayList<ImsUsersDto>();
		if(deptId==null || "".equals(deptId)){
			result.setMessage("部门编号不能为空");
			result.setStatus(AppResponse.RETURN_FAILE);
			return result;
		}
		if(userId==null || "".equals(userId)){
			result.setMessage("用户编号不能为空");
			result.setStatus(AppResponse.RETURN_FAILE);
			return result;
		}
		
		String projId="";
		Map<String, Object> mapproj = findProjByUserId(userId);
		Boolean projState = (Boolean) mapproj.get("SUCCESS");
		if(projState){
			ProjectUsersDto projectUsersDto = (ProjectUsersDto) mapproj.get("projectUsersDto");
			projId =projectUsersDto.getProjId();
		}else{
			 result.setMessage("项目ID不存在！");
			 result.setStatus(AppResponse.RETURN_FAILE);
			 return result;
		}
		
		DeptUsersDto deptUserDto = new DeptUsersDto();
		deptUserDto.setDeptId(deptId);
		deptUserDto.setStat(1);
		deptUserDto.setProjId(projId);
		List<DeptUsersDto> listdeu = deptUserService.findAllBy(deptUserDto);
		if(listdeu!=null && listdeu.size()>0){
			for(DeptUsersDto deptUsersDto2 : listdeu){
				String userId2 = deptUsersDto2.getUserId();
				if(!userId.equals(userId2)){
					ImsUsersDto imsUsersDto2 =  iImsUserService.findByUserId(userId2);
					listUser.add(imsUsersDto2);
				}
			}
			result.setMessage("用户返回成功");
			result.setData(listUser);
			result.setStatus(AppResponse.RETURN_SUCCESS);
			return result;
		}else{
			result.setMessage("该部门下用户为空");
			result.setStatus(AppResponse.RETURN_FAILE);
			return result;
		}
	}
	
	@RequestMapping("/findBySearchName")
	@ResponseBody
	public Response<List<ImsUsersDto>> findBySearchName (String searchName,String userId){
		Response<List<ImsUsersDto>> result = new Response<List<ImsUsersDto>>();
		if(StringUtils.isEmpty(userId)){
			result.setMessage("用户编号不能为空");
			result.setStatus(AppResponse.RETURN_FAILE);
			return result;
		}
		String projId="";
		Map<String, Object> mapproj = findProjByUserId(userId);
		Boolean projState = (Boolean) mapproj.get("SUCCESS");
		if(projState){
			ProjectUsersDto projectUsersDto = (ProjectUsersDto) mapproj.get("projectUsersDto");
			projId =projectUsersDto.getProjId();
		}else{
			 result.setMessage("项目ID不存在！");
			 result.setStatus(AppResponse.RETURN_FAILE);
			 return result;
		}
		ImsUsersDto imsUsersDto = new ImsUsersDto();
		imsUsersDto.setProjId(projId);
		imsUsersDto.setSearchName(searchName);
		List<ImsUsersDto> list = iImsUserService.findBySearchName(imsUsersDto);
		result.setData(list);
		result.setMessage("返回成功");
        result.setStatus(AppResponse.RETURN_SUCCESS);
        return result;
	}
	
	
	
	//查询当前部门的人员和子部门的信息
	@RequestMapping("/findSubDeptAndUser")
	@ResponseBody
	public  Response<Map<String, Object>>  findSubDeptAndUser(String deptId,String userId){
		Response<Map<String, Object>> result = new Response<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		List<ImsUsersDto> listUser = new ArrayList<ImsUsersDto>();

		if(userId==null || "".equals(userId)){
			result.setMessage("用户编号不能为空");
			result.setStatus(AppResponse.RETURN_FAILE);
			return result;
		}
		
		String projId="";
		Map<String, Object> mapproj = findProjByUserId(userId);
		Boolean projState = (Boolean) mapproj.get("SUCCESS");
		if(projState){
			ProjectUsersDto projectUsersDto = (ProjectUsersDto) mapproj.get("projectUsersDto");
			projId =projectUsersDto.getProjId();
		}else{
			 result.setMessage("项目ID不存在！");
			 result.setStatus(AppResponse.RETURN_FAILE);
			 return result;
		}
		
		if(!StringUtils.isEmpty(deptId)){   //部门id不为空
			DeptUsersDto deptUserDto = new DeptUsersDto();
			deptUserDto.setDeptId(deptId);
			deptUserDto.setStat(1);
			deptUserDto.setProjId(projId);
			List<DeptUsersDto> listdeu = deptUserService.findAllBy(deptUserDto);
			if(listdeu!=null && listdeu.size()>0){
				for(DeptUsersDto deptUsersDto2 : listdeu){
					String userId2 = deptUsersDto2.getUserId();
					if(!userId.equals(userId2)){
						ImsUsersDto imsUsersDto2 =  iImsUserService.findByUserId(userId2);
						listUser.add(imsUsersDto2);
					}
				}
			}
			map.put("listUser", listUser);
			DeptDto param = new DeptDto();
			param.setPid(deptId);
			param.setProjId(projId);
			List<DeptDto> listDept =  deptService.findBy(param);
			map.put("listDept", listDept);
		}else{  //部门id为空
			DeptDto param = new DeptDto();
			param.setPid("");
			param.setProjId(projId);
			List<DeptDto> listDept =  deptService.findBy(param);
			map.put("listDept", listDept);
			map.put("listUser", listUser);
		}
		result.setData(map);
		result.setMessage("返回子部门和用户信息成功");
		result.setStatus(AppResponse.RETURN_SUCCESS);
		return result;
		
	    
	}
	
	
	
	
	//搜索部门下的用户
	@RequestMapping("/searchUser.do")
    @ResponseBody
	public Response<List<ImsUsersDto> > searchUser(String deptId,String searchName,String projectId){
		Response<List<ImsUsersDto>> result = new Response<List<ImsUsersDto>>();		
		List<ImsUsersDto> listu2=  deptUserService.searchUser(deptId, searchName);
		if(listu2!=null && listu2.size()>0){
			result.setMessage("返回用户成功");
			result.setData(listu2);
			result.setStatus(AppResponse.RETURN_SUCCESS);
			return result;
		}else{
			result.setMessage("用户不存在");
			result.setStatus(AppResponse.RETURN_FAILE);
			return result;
		}		
	}
	
	//查看我的
	@RequestMapping("/findMyInfo.do")
	@ResponseBody
	public Response<ImsUsersDto> findMyInfo(String userId){
		Response<ImsUsersDto> result = new Response<ImsUsersDto>();
		if(userId==null || "".equals(userId)){
			result.setMessage("用户id不能为空");
			result.setStatus(AppResponse.RETURN_FAILE);
			return result;
		}
		ImsUsersDto imsUsersDto = iImsUserService.findByUserId(userId);
		//查询我的部门
		DeptUsersDto  deptUsersDto = new DeptUsersDto();
		deptUsersDto.setUserId(userId);
		deptUsersDto.setStat(1);
		List<DeptUsersDto> listdu1=  deptUserService.findAllBy(deptUsersDto);
		List<String> listdname = new ArrayList<String>();
		if(listdu1!=null && listdu1.size()>0){
			DeptDto deptdto2 = deptService.findById(listdu1.get(0).getDeptId());
			String deptName2 = deptdto2.getDeptName();
			listdname.add(deptName2);
			String pid2 = deptdto2.getPid();
			for(int i=0 ;i<20 ;i++){
				if(!"".equals(pid2)){
					DeptDto deptdto3 = deptService.findById(pid2);
					String deptName3 = deptdto3.getDeptName();
					listdname.add(deptName3);
					pid2 = deptdto3.getPid();
				}else{
					imsUsersDto.setDeptName(deptName2);
					break;
				}
			}
			
			String dName = "";
			if(listdname.size()>0){
				for(int j=listdname.size()-1;j>=0;j--){
				//	System.out.println("kaishi    "+j);
				//	System.out.println("value     "+listdname.get(j));
					if(j!=0){
						dName = dName +listdname.get(j) +"--";
					}else{
						dName = dName +listdname.get(j) ;
					}
				}
				imsUsersDto.setDeptName(dName);
			}
			
		}else{
			result.setMessage("该用户的部门为空");
			result.setStatus(AppResponse.RETURN_FAILE);
			return result;
		}
		
		
		result.setMessage("返回用户成功");
		result.setData(imsUsersDto);
		result.setStatus(AppResponse.RETURN_SUCCESS);
		return result;
	}
	
	
	//删除查询中的缓存
	@RequestMapping("/deleteCarch.do")
	@ResponseBody
	public Response<String> deleteCarch(String userId){
		Response<String> result = new Response<String>();
		if(userId==null || "".equals(userId)){
			result.setMessage("用户编号不能为空");
			result.setStatus(AppResponse.RETURN_FAILE);
			return result;
		}
	    ImsUsersDto imsUsersDto	= iImsUserService.findByUserId(userId);
	    iQjyImService.deleteRadisGroup(imsUsersDto.getQjyAccount());
	    result.setMessage("删除缓存成功");
	    result.setStatus(AppResponse.RETURN_SUCCESS);
	    return result;
	}
	
	
	@RequestMapping("/getGroupUserList.do")
	@ResponseBody
	public Response<List<ImsUsersDto>> getGroupUserList(String groupId){
		Response<List<ImsUsersDto>> result = new Response<List<ImsUsersDto>>();
		List<ImsUsersDto> listu = new ArrayList<ImsUsersDto>();
		if(groupId==null || "".equals(groupId)){
			result.setMessage("群ID不能为空");
			result.setStatus(AppResponse.RETURN_FAILE);
			return result;
		}
		Map<String, Object> map = iQjyImService.getGroupUserList(groupId);
		List groupuser = (List) map.get("data");
		if(groupuser!=null && groupuser.size()>0){
			for(int j=0;j<groupuser.size();j++){
				RespUser respUser = (RespUser) groupuser.get(j);
				String account = respUser.getAccount();
				String nickname = respUser.getNickname();
                 ImsUsersDto imsUsersDto = new ImsUsersDto();
                 imsUsersDto.setQjyAccount(account);
                 imsUsersDto.setName(nickname);
                 listu.add(imsUsersDto);
			}
		}
		result.setData(listu);
		result.setMessage("返回群成员成功");
		result.setStatus(AppResponse.RETURN_SUCCESS);
		return result;
		
		
	}
	
	//获取层级关系的好友
	@RequestMapping("/getGroupDeptUser.do")
    @ResponseBody
	public Response<List<AppGroupDeptDto>> getGroupDeptUser(String userId){
		Response<List<AppGroupDeptDto>> result = new Response<List<AppGroupDeptDto>>();
		//获取该用户的好友列表
//		  ImsUsersDto imsUsersDto	= iImsUserService.findByUserId(userId);
//		    if(imsUsersDto==null){
//		    	result.setMessage("该用户不存在");
//		    	result.setStatus(AppResponse.RETURN_FAILE);
//		    	return result;
//		    }
//		Map<String, Object> result11=   iQjyImService.getFriends(imsUsersDto.getQjyAccount());
//		System.out.println(imsUsersDto);
//		
//		List oo = (List) result11.get("data");
//		if(oo!=null && oo.size()>0){
//			for(int j=0;j<oo.size();j++){
//				GetFriendsResp getFriendsResp = (GetFriendsResp) oo.get(j);
//				String account = getFriendsResp.getEntities().get(j).getAccount();
//				String nickname = getFriendsResp.getEntities().get(j).getNickname();
//				
//			}
//		}
	    ImsUsersDto imsUsersDto	= iImsUserService.findByUserId(userId);
	    if(imsUsersDto==null){
	    	result.setMessage("该用户不存在");
	    	result.setStatus(AppResponse.RETURN_FAILE);
	    	return result;
	    }
	    
		String projId="";
		Map<String, Object> mapproj = findProjByUserId(userId);
		Boolean projState = (Boolean) mapproj.get("SUCCESS");
		if(projState){
			ProjectUsersDto projectUsersDto = (ProjectUsersDto) mapproj.get("projectUsersDto");
			projId =projectUsersDto.getProjId();
		}else{
			 result.setMessage("项目ID不存在！");
			 result.setStatus(AppResponse.RETURN_FAILE);
			 return result;
		}
		
		//获取团餐下的所有部门
		AppGroupDeptDto appGroupDeptDto1 = new AppGroupDeptDto();
		appGroupDeptDto1.setProjId(projId);   
		List<AppGroupDeptDto> listdept = meetingService.getdept(appGroupDeptDto1);
		
		if(listdept==null){
			result.setMessage("部门为空");
			result.setStatus(AppResponse.RETURN_FAILE);
			return result;
		}

		if(listdept!=null && listdept.size()>0){
			for(AppGroupDeptDto appGroupDeptDto2 : listdept){
				List<AppGroupDeptUserDto> list=  meetingService.getGroupDeptUser(appGroupDeptDto2.getDeptId(),projId);
				List<AppGroupDeptUserDto> list2 = new ArrayList<AppGroupDeptUserDto>();
				if(list!=null && list.size()>0){
					for(AppGroupDeptUserDto agdto : list){
						String userid4= agdto.getUserId();
						if(!userid4.equals(imsUsersDto.getQjyAccount())){
							list2.add(agdto);
						}
					}
				}
				appGroupDeptDto2.setDeptuserlist(list2);
			}
		}
			result.setMessage("返回好友列表成功");
		    result.setData(listdept);
		    result.setStatus(AppResponse.RETURN_SUCCESS);
		    return result;
	}
	
	@RequestMapping("/findGroupUsers.do")
	@ResponseBody
	public Response<List<ImUserDto>> findGroupUsers(String groupId){
		Response<List<ImUserDto>> result = new Response<List<ImUserDto>>();
		if(groupId==null || "".equals(groupId)){
			result.setMessage("groupID为空");
            result.setStatus(AppResponse.RETURN_FAILE);
            return result;
		}
		List<ImUserDto> listimu = new ArrayList<ImUserDto>();
		GroupInfoDto groupInfoDto = new GroupInfoDto();
		groupInfoDto.setGroupId(groupId);
		List<GroupInfoDto> list =    groupInfoService.findBy(groupInfoDto);
		if(list!=null){
			String infoId = list.get(0).getId();
			if(infoId!=null){
				GroupUserDto groupUserDto = new GroupUserDto();
				groupUserDto.setInfoId(infoId);
				List<GroupUserDto> listgu =  groupUserService.findBy(groupUserDto);
				if(listgu!=null && listgu.size()>0){
					for(GroupUserDto  groupUserDto2 : listgu){
						ImUserDto imUserDto2 = new ImUserDto();
						imUserDto2.setImCount(groupUserDto2.getUserAccount());
						imUserDto2.setUserId(groupUserDto2.getUserId());
						ImsUsersDto  imsUsersDto2 = iImsUserService.findByUserId(groupUserDto2.getUserId());
						imUserDto2.setUserName(imsUsersDto2.getName());
						listimu.add(imUserDto2);
					}
				}else{
					result.setMessage("群组人员为空");
					result.setStatus(AppResponse.RETURN_FAILE);
					return result;
				}
			}
		}else{
			result.setMessage("群组为空");
			result.setStatus(AppResponse.RETURN_FAILE);
			return result;
		}
		result.setData(listimu);
		result.setMessage("返回群组成员成功");
		result.setStatus(AppResponse.RETURN_SUCCESS);
		return result;
	}
	
	
	
	public  Node sortlist(List datat){
		 // 读取层次数据结果集列表   
		  List dataList = datat;    
		    
		  // 节点列表（散列表，用于临时存储节点对象）  
		  LinkedTreeMap nodeList = new LinkedTreeMap();  
		  // 根节点  
		  Node root = null;  
		  // 根据结果集构造节点列表（存入散列表）  
		  for (Iterator it = dataList.iterator(); it.hasNext();) {  
		   Map dataRecord = (Map) it.next();  
		   Node node = new Node();  
		   node.id = (String) dataRecord.get("id");  
		   node.text = (String) dataRecord.get("text");  
		   node.parentId = (String) dataRecord.get("parentId");  
		   node.createtime = (Date) dataRecord.get("createtime");
		   nodeList.put(node.id, node);  
		  }  
		  // 构造无序的多叉树  
		  Set entrySet = nodeList.entrySet();  
		  for (Iterator it = entrySet.iterator(); it.hasNext();) {  
		   Node node = (Node) ((Map.Entry) it.next()).getValue();  
		   if (node.parentId == null || node.parentId.equals("")) {  
		    root = node;  
		   } else {  
		    ((Node) nodeList.get(node.parentId)).addChild(node);  
		   }  
		  }  
		  // 输出无序的树形菜单的JSON字符串  
		  System.out.println(root.toString());     
		  // 对多叉树进行横向排序  
		//  root.sortChildren();  
		  // 输出有序的树形菜单的JSON字符串  
		  System.out.println(root.toString());
		return root;
	}
	
	 //根据userid查询项目id
	 public Map<String, Object> findProjByUserId(String userid){
		ProjectUsersDto projectUsersDto = projectServices.findByUserID(userid);
		 Map<String, Object> map = new HashMap<String, Object>();
		 if(projectUsersDto!=null && projectUsersDto.getProjId()!=null){
			 map.put("SUCCESS", true);
			 map.put("projectUsersDto", projectUsersDto);
		 }else{
			 map.put("SUCCESS", false);
			 map.put("projectUsersDto", projectUsersDto);
		 }
		 return  map;
	 }
	 
	 @RequestMapping("/addGroupUsers.do")
	 @ResponseBody
	 public void addGroupUsers(){
		 GroupInfoDto groupInfoDto = new GroupInfoDto();
		 String groupId = UUIDGenerator.getUUID();
		 groupInfoDto.setId(groupId);
		 groupInfoDto.setGroupId(groupId);
		 groupInfoDto.setStat(1);
		 groupInfoDto.setGroupName("测试大群4");
		 groupInfoDto.setGroupInfo("测试大群4");
		 groupInfoDto.setCreateTime(new Date());
		 groupInfoDto.setProjId("9c5d7f80-ebe0-4951-a4a5-05d8e1603175");
		 groupInfoDto.setCreateuserid("998ef8e4-a3f6-4815-b7f6-2d365cdf42c8");
		 groupInfoService.insertGroupInfo(groupInfoDto);
		 List<ImsUsersDto> list =  iImsUserService.findByProjId(null);
		 if(list!=null && list.size()>0){
			 for(int i=0;i<list.size();i++){
				 ImsUsersDto imsUsersDto  = list.get(i);
				 GroupUserDto groupUserDto = new GroupUserDto();
				 groupUserDto.setId(UUIDGenerator.getUUID());
				 groupUserDto.setInfoId(groupId);
				 groupUserDto.setCreateTime(new Date());
				 groupUserDto.setUserAccount(imsUsersDto.getUserAccount());
				 groupUserDto.setUserId(imsUsersDto.getId());
				 groupUserDto.setStat(1);
				 groupUserService.insertGroupUser(groupUserDto);
			 }
		 }

	 }
	 
	 @RequestMapping("/findUserByGroupId.do")
	 @ResponseBody
	 public List<String> findUserByGroupId (String groupId){
		  GroupUserDto groupUserDto = new GroupUserDto();
		  groupUserDto.setInfoId(groupId);
		  groupUserDto.setStat(1);
		  List<GroupUserDto> list= groupUserService.findBy(groupUserDto);
//		  List<ImUserDto> listu = new ArrayList<ImUserDto>();
//		  if(list!=null && list.size()>0){
//			  for(GroupUserDto groupUserDto2 : list){
//				  String guseraccount = groupUserDto2.getUserAccount();
//				  String guserid = groupUserDto2.getUserId();
//				  ImsUsersDto imsUsersDto = iImsUserService.findByUserId(guserid);
//				  ImUserDto imUserDto2 = new ImUserDto();
//				  imUserDto2.setImCount(guseraccount);
//				  imUserDto2.setUserId(guserid);
//				  imUserDto2.setUserName(imsUsersDto.getName());
//				  listu.add(imUserDto2);
//			  }
//		  }
		  
		  
		  List<String> listuu = new ArrayList<String>();
		  if(list!=null && list.size()>0){
			  for(GroupUserDto groupUserDto2 : list){
				  String guseraccount = groupUserDto2.getUserAccount();
				  listuu.add(guseraccount);
			  }
		  }
		  
		  return listuu;
	 }
	 
	 @RequestMapping("/findGroupDetalByUser.do")
	 @ResponseBody
	 private Response<List<GroupInfoDto>>  findGroupDetalByUser(String userAccount){
		 long startime = System.currentTimeMillis();
		 Response<List<GroupInfoDto>> result = new Response<List<GroupInfoDto>>();
		 List<GroupInfoDto> listgroup = new ArrayList<GroupInfoDto>();
		 GroupUserDto groupUserDto1 = new GroupUserDto();
		 groupUserDto1.setUserAccount(userAccount);
		 //查询出所有的用户信息
		 ImsUsersDto imsuk = new ImsUsersDto();
		 List<ImsUsersDto> listk= iImsUserService.findBy(imsuk);
		 //查询用户登陆账号所在的群
		 List<GroupUserDto> listgu1= groupUserService.findBy(groupUserDto1);
		 if(listgu1!=null && listgu1.size()>0){
			 for(GroupUserDto groupUserDto3 : listgu1){
				 String infoId = groupUserDto3.getInfoId();
				 //查询该群所有的用户
						  GroupUserDto groupUserDto = new GroupUserDto();
						  groupUserDto.setStat(1);
						  groupUserDto.setInfoId(infoId);
						  List<GroupUserDto> list= groupUserService.findBy(groupUserDto);
						  List<ImUserDto> listu = new ArrayList<ImUserDto>();
						  GroupInfoDto groupInfoDto = groupInfoService.findById(infoId);
						  //在java中循环提高速度
						  for(ImsUsersDto imsUsersDtok:listk){
							  if(groupInfoDto.getCreateuserid().equals(imsUsersDtok.getId())){
								  groupInfoDto.setCreateusername(imsUsersDtok.getName());
								  groupInfoDto.setCreateuserImage(imsUsersDtok.getUserImage());
							  }
						  }
//						  ImsUsersDto imsUsersDto1 = iImsUserService.findByUserId(groupInfoDto.getCreateuserid());
//						  if(imsUsersDto1!=null){
//							  groupInfoDto.setCreateusername(imsUsersDto1.getName());
//							  groupInfoDto.setCreateuserImage(imsUsersDto1.getUserImage());
//						  }
						  if(list!=null && list.size()>0){
							  for(GroupUserDto groupUserDto2 : list){
								  String guseraccount = groupUserDto2.getUserAccount();
								  String guserid = groupUserDto2.getUserId();
								  //在java中循环提高速度
								  for(ImsUsersDto imsUsersDtok:listk){
									  if(guserid.equals(imsUsersDtok.getId())){
										  ImUserDto imUserDto2 = new ImUserDto();
										  imUserDto2.setImCount(guseraccount);
										  imUserDto2.setUserId(guserid);
										  imUserDto2.setUserName(imsUsersDtok.getName());
										  imUserDto2.setUserImage(imsUsersDtok.getUserImage());
										  listu.add(imUserDto2);
									  }
								  }
//								  ImsUsersDto imsUsersDto = iImsUserService.findByUserId(guserid);
//								  ImUserDto imUserDto2 = new ImUserDto();
//								  imUserDto2.setImCount(guseraccount);
//								  imUserDto2.setUserId(guserid);
//								  imUserDto2.setUserName(imsUsersDto.getName());
//								  imUserDto2.setUserImage(imsUsersDto.getUserImage());
//								  listu.add(imUserDto2);
							  }
							 // groupUserDto3.setList(listu);
							  groupInfoDto.setList(listu);
				 }
						  
				 listgroup.add(groupInfoDto);
			 }
			 long endtime = System.currentTimeMillis();
			 System.out.println("运行时间-------："+(endtime-startime));
			 result.setMessage("返回群列表成功");
			 result.setStatus(AppResponse.RETURN_SUCCESS);
			 result.setData(listgroup);
			 return result;
		 }else{
			 result.setMessage("群列表为空");
			 result.setStatus(AppResponse.RETURN_SUCCESS);
			 result.setData(listgroup);
			 return result;
		 }
	
	 }
	 
	 @RequestMapping("/addLTGroup.do")
	 @ResponseBody
	 public Response<GroupInfoDto> addLTGroup(GroupInfoDto groupInfoDto){    //创建群组
		 Response<GroupInfoDto> result = new Response<GroupInfoDto>();
		// 44902ce2-48da-4110-bf82-2fc3816f9d7d  majichao  马骥超
		 //699d338a-46d8-40fb-b1da-06d222bb39b8  zhangdengfen   张登峰
		 //606acafe-c306-4307-a57a-be19e53750dd   chengl   程龙
		
//		 groupInfoDto.setGroupName("三年二班");
//		 groupInfoDto.setCreateuserid("44902ce2-48da-4110-bf82-2fc3816f9d7d");
//		 groupInfoDto.setUsers("44902ce2-48da-4110-bf82-2fc3816f9d7d,699d338a-46d8-40fb-b1da-06d222bb39b8,606acafe-c306-4307-a57a-be19e53750dd");		 
		 
		 if(StringUtils.isEmpty(groupInfoDto.getGroupName())){
			 result.setMessage("群名称不能为空");
			 result.setStatus(AppResponse.RETURN_FAILE);
			 return result;
		 }
		 if(StringUtils.isEmpty(groupInfoDto.getCreateuserid())){
			 result.setMessage("创建者ID不能为空");
			 result.setStatus(AppResponse.RETURN_FAILE);
			 return result;
		 }
		 if(StringUtils.isEmpty(groupInfoDto.getUsers())){
			 result.setMessage("群成员不能为空");
			 result.setStatus(AppResponse.RETURN_FAILE);
			 return result;
		 }
		 
		 String[] users = groupInfoDto.getUsers().split(",");
		 if(users==null || users.length==0){
			 result.setMessage("群成员不能为空");
			 result.setStatus(AppResponse.RETURN_FAILE);
			 return result;
		 }
		 
		 String groupId = UUIDGenerator.getUUID();
		 groupInfoDto.setId(groupId);
		 groupInfoDto.setGroupId(groupId);
		 groupInfoDto.setStat(1);
		 groupInfoDto.setGroupName(groupInfoDto.getGroupName());
		 groupInfoDto.setGroupInfo(groupInfoDto.getGroupInfo());
		 groupInfoDto.setCreateTime(new Date());
		 groupInfoDto.setProjId(groupInfoDto.getProjId());
		 groupInfoDto.setCreateuserid(groupInfoDto.getCreateuserid());
		 groupInfoService.insertGroupInfo(groupInfoDto);
		 if(users!=null && users.length>0){
			 for(int i=0;i<users.length;i++){
				 String usersid = users[i];
				 ImsUsersDto imsUsersDto2 = iImsUserService.findByUserId(usersid);
				 GroupUserDto groupUserDto = new GroupUserDto();
				 groupUserDto.setId(UUIDGenerator.getUUID());
				 groupUserDto.setInfoId(groupId);
				 groupUserDto.setCreateTime(new Date());
				 if(imsUsersDto2!=null){
					 groupUserDto.setUserAccount(imsUsersDto2.getUserAccount()); 
				 }
				 groupUserDto.setUserId(usersid);
				 groupUserDto.setStat(1);
				 groupUserService.insertGroupUser(groupUserDto);
			 }
		 }
		 
		 String usersid = groupInfoDto.getCreateuserid();
		 ImsUsersDto imsUsersDto3 = iImsUserService.findByUserId(usersid);
		 GroupUserDto groupUserDto3 = new GroupUserDto();
		 groupUserDto3.setId(UUIDGenerator.getUUID());
		 groupUserDto3.setInfoId(groupId);
		 groupUserDto3.setCreateTime(new Date());
		 if(imsUsersDto3!=null){
			 groupUserDto3.setUserAccount(imsUsersDto3.getUserAccount()); 
		 }
		 groupUserDto3.setUserId(usersid);
		 groupUserDto3.setStat(1);
		 groupUserService.insertGroupUser(groupUserDto3);
		 
		 
		 result.setData(groupInfoDto);
		 result.setMessage("创建成功");
		 result.setStatus(AppResponse.RETURN_SUCCESS);
		 return result;
	 }
	 
	 @RequestMapping("/findLTGroupInfo.do")
	 @ResponseBody
	 public Response<GroupInfoDto> findLTGroupInfo(GroupInfoDto groupInfoDto){  //查询群详情
		 Response<GroupInfoDto> result = new Response<GroupInfoDto>();
		 long star = System.currentTimeMillis();
		 if(StringUtils.isEmpty(groupInfoDto.getGroupId())){
			 result.setMessage("群ID不能为空");
			 result.setStatus(AppResponse.RETURN_FAILE);
			 return result;
		 }
		 GroupInfoDto groupInfoDto2 = groupInfoService.findLTGroupInfo(groupInfoDto);
		 result.setMessage("返回群成员成功");
		 result.setStatus(AppResponse.RETURN_SUCCESS);
		 result.setData(groupInfoDto2);
		 long end = System.currentTimeMillis();
		 System.out.println("查询群详情运行时间："+(end-star));
		 return result;
	 }
	 
	 @RequestMapping("/editLTGroupName.do")
     @ResponseBody
	 public Response<String> editLTGroupName(GroupInfoDto groupInfoDto){   //修改群名称
		 Response<String> result = new Response<String>();
		 if(StringUtils.isEmpty(groupInfoDto.getGroupId())){
			 result.setMessage("群ID不能为空");
			 result.setStatus(AppResponse.RETURN_FAILE);
			 return result;
		 }
		 if(StringUtils.isEmpty(groupInfoDto.getGroupName())){
			 result.setMessage("群名称不能为空");
			 result.setStatus(AppResponse.RETURN_FAILE);
			 return result;
		 }
		 if(groupInfoDto.getGroupName().length()>20){
			 result.setMessage("群名称的长度不能大于20");
			 result.setStatus(AppResponse.RETURN_FAILE);
			 return result;
		 }
		 groupInfoService.editLTGroupName(groupInfoDto);
	     result.setMessage("修改群名称成功");
		 result.setStatus(AppResponse.RETURN_SUCCESS); 
		 return result; 
	 }
	 
	 @RequestMapping("/addLTGroupUser.do")
	 @ResponseBody
	 public Response<String> addLTGroupUser(GroupInfoDto groupInfoDto){   //添加群成员
		 Response<String> result = new Response<String>();
		 if(StringUtils.isEmpty(groupInfoDto.getGroupId())){
			 result.setMessage("群ID不能为空");
			 result.setStatus(AppResponse.RETURN_FAILE);
			 return result;
		 }
		 if(StringUtils.isEmpty(groupInfoDto.getUsers())){
			 result.setMessage("群成员不能为空");
			 result.setStatus(AppResponse.RETURN_FAILE);
			 return result;
		 }
		 groupInfoService.addLTGroupUser(groupInfoDto);
		 result.setMessage("添加群成员成功");
		 result.setStatus(AppResponse.RETURN_SUCCESS);
		 return result;
	 }
	 
	 @RequestMapping("/leaveLTGroup.do")
	 @ResponseBody
	 public Response<String> leaveLTGroup(GroupInfoDto groupInfoDto){   //离开群组
		 Response<String> result = new Response<String>();
		 if(StringUtils.isEmpty(groupInfoDto.getGroupId())){
			 result.setMessage("群ID不能为空");
			 result.setStatus(AppResponse.RETURN_FAILE);
			 return result;
		 }
		 if(StringUtils.isEmpty(groupInfoDto.getUsers())){
			 result.setMessage("群成员不能为空");
			 result.setStatus(AppResponse.RETURN_FAILE);
			 return result;
		 }
		 groupInfoService.leaveLTGroup(groupInfoDto);
		 result.setMessage("离开群组成功");
		 result.setStatus(AppResponse.RETURN_SUCCESS);
		 return result;
	 }
	 
	 
	 @RequestMapping("/delLTGroup.do")
	 @ResponseBody
	 public Response<String> delLTGroup(GroupInfoDto groupInfoDto){    //解散群组
		 Response<String> result = new Response<String>();
		 if(StringUtils.isEmpty(groupInfoDto.getGroupId())){
			 result.setMessage("群ID不能为空");
			 result.setStatus(AppResponse.RETURN_FAILE);
			 return result;
		 }
		 if(StringUtils.isEmpty(groupInfoDto.getUsers())){
			 result.setMessage("群成员不能为空");
			 result.setStatus(AppResponse.RETURN_FAILE);
			 return result;
		 }
		 String delinfo=  groupInfoService.delLTGroup(groupInfoDto);
		 if("200".equals(delinfo)){
			 result.setMessage("解散群组成功");
			 result.setStatus(AppResponse.RETURN_SUCCESS);
			 return result;
		 }else{
			 result.setMessage("只有管理员才能解散群组");
			 result.setStatus(AppResponse.RETURN_FAILE);
			 return result;
		 }
	
	 }
	 
	 
}
