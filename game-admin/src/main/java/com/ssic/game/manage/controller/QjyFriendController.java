package com.ssic.game.manage.controller;

import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssic.game.admin.dto.TImsUsersDto;
import com.ssic.game.admin.pageModel.DataGrid;
import com.ssic.game.admin.pageModel.Json;
import com.ssic.game.admin.pageModel.PageHelper;
import com.ssic.game.admin.pageModel.Role;
import com.ssic.game.admin.pageModel.Tree;
import com.ssic.game.common.dto.ImsUsersDto;
import com.ssic.game.common.dto.ProcessDto;
import com.ssic.game.common.dto.QjyFriendDto;
import com.ssic.game.manage.service.IQjyFriendService;
import com.ssic.util.UUIDGenerator;



@Controller
@RequestMapping("/qjyFriendController")
public class QjyFriendController {
	
	@Autowired
	private IQjyFriendService  qjyFriendService;

	@RequestMapping("/manager")
	public String manager(){
		return "ims/qjyFriend";
	}
	@RequestMapping("/qjyFriendAdd")
	public String qjyFriendAdd(){
		return "ims/qjyFriendAdd";
	}
	@RequestMapping("/qjyFriendEdit")
	public String qjyFriendEdit(){
		return "ims/qjyFriendEdit";
	}
	
	@RequestMapping("/findQJYF")
	@ResponseBody
	public List<QjyFriendDto>  findQJYF(QjyFriendDto qjyFriendDto){
		return qjyFriendService.findQJYF(qjyFriendDto);
	}
	
	@RequestMapping("/insertQJYF")
	@ResponseBody
	public Json insertQJYF(QjyFriendDto qjyFriendDto){
		Json j=new Json();
		qjyFriendService.insertQJYF(qjyFriendDto);
		j.setMsg("添加好友成功");
		j.setSuccess(true);
		return j;
	}
	
	@RequestMapping("updateQJY")
	@ResponseBody
	public Json updateQJY(QjyFriendDto qjyFriendDto){
		Json j = new Json();
		qjyFriendService.updateQJY(qjyFriendDto);
		j.setMsg("更新好友成功");
		j.setSuccess(true);
		return j;
	}
	
	@RequestMapping("deleteQJYF")
	@ResponseBody
	public Json deleteQJYF(QjyFriendDto qjyFriendDto){
		Json j = new Json();
		qjyFriendService.deleteQJYF(qjyFriendDto);
		j.setMsg("删除好友成功");
		j.setSuccess(true);
		return j;
	}
	
	@ResponseBody
	@RequestMapping("/dataGrid")
    public DataGrid dataGrid(ImsUsersDto imsUsersDto, PageHelper ph){
		imsUsersDto.setProjId("1959a627-1a89-4497-b1e4-144d019d6687");
    	List<ImsUsersDto> list = qjyFriendService.findUser(imsUsersDto,ph);
        int count = qjyFriendService.findUserCount(imsUsersDto);
    	if(list!=null && list.size()>0 ){
			 for(int i=0;i<list.size();i++){
				 ImsUsersDto uDto = list.get(i);
				 QjyFriendDto qjyFriendDto = new QjyFriendDto();
				 qjyFriendDto.setUserId(uDto.getId());
				 List<QjyFriendDto> qjylist =  qjyFriendService.findQJYF(qjyFriendDto);
				 String userFriends = "";
				 if(qjylist!=null && qjylist.size()>0){
					 for(int j=0;j<qjylist.size();j++){
						 userFriends = userFriends + qjylist.get(j).getQjyAccount()+",";
					 }
					 userFriends = userFriends.substring(0, userFriends.length()-1);
					 uDto.setUserFriends(userFriends);
				 }
				
			 }
		}
		DataGrid dataGrid = new DataGrid();
		dataGrid.setRows(list);
		dataGrid.setTotal(Long.valueOf(count));
		return dataGrid;
    }
	
	 @RequestMapping("/grantUserPage")
	 public String grantUserPage(String ids, HttpServletRequest request,HttpSession session)
	    {
	        request.setAttribute("ids", ids);
	        session.setAttribute("grantUserPerss", ids);
	        Role u = new Role();
	        if (ids != null && !ids.equalsIgnoreCase(""))
	        {
	          
	            String result = qjyFriendService.findUserPers(ids);
	            u.setResourceIds(result);
	            request.setAttribute("role", u);
	        }
	        return "ims/qjyFriendGrant";
	    }
	  /**
	     * 角色树(只能看到自己拥有的角色)
	     * 
	     * @return
	     */
	    @RequestMapping("/userTree")
	    @ResponseBody
	    public List<Tree> userTree(String searchName,HttpSession session)
	    {
	    	 String userPerss = session.getAttribute("grantUserPerss").toString();
	    //	 System.out.println("userPerss="+userPerss);
	        return qjyFriendService.userTree(searchName,userPerss);
	    }
	    
	    @RequestMapping("/grantUsers")
	    @ResponseBody
	    public Json grantUsers(String resourceIds,HttpSession session) {
	        Json j = new Json();

	        if(session.getAttribute("grantUserPerss")!=null){
	        	qjyFriendService.grantUser(session.getAttribute("grantUserPerss").toString(), resourceIds);
	            j.setSuccess(true);
	            j.setMsg("添加好友成功！");
	            return j;
	        }else{
	            j.setSuccess(false);
	            j.setMsg("寻找不到用户id，请重新添加好友!");
	            return j;
	        }


	    }
	    
	    //每个用户都添加该用户为好友
	    @RequestMapping("/addUserForEveryone")
	    @ResponseBody
	    public Json addUserForEveryone(){
	    	Json j= new Json();
	  //  	yingzong    尹总    30a3c09d-53a5-4684-ada0-db91a183b15b
	    	String friendId = "30a3c09d-53a5-4684-ada0-db91a183b15b";
	    	String friendAccount = "yingzong";
	    	String projId = "cd312abd-dc85-4f8d-a8d4-669a85ca524e";   //康帕斯
	    	
	    	//查询该部门下除过尹总的其他用户
	    	ImsUsersDto imsUsersDto = new ImsUsersDto();
	    	imsUsersDto.setId(friendId);
	    	imsUsersDto.setProjId(projId);
	    	List<ImsUsersDto> listu = qjyFriendService.findOtherOne(imsUsersDto);
	    	
	    	if(listu!=null && listu.size()>0){
	    		for(ImsUsersDto imsUsersDto1 : listu){
	    			QjyFriendDto qjyFriendDto3 = new QjyFriendDto();
					qjyFriendDto3.setId(UUIDGenerator.getUUID());
					qjyFriendDto3.setStat(1);
					qjyFriendDto3.setCreateTime(new Date());
					qjyFriendDto3.setFriendUserId(friendId);
					qjyFriendDto3.setQjyAccount("qjy_"+friendAccount);
					qjyFriendDto3.setUserId(imsUsersDto1.getId());
					qjyFriendService.insertQJYF(qjyFriendDto3);
	    			
	    			QjyFriendDto qjyFriendDto2 = new QjyFriendDto();
	    			//qjyFriendDto2.setQjyAccount("qjy_"+imsUsersDto1.getUserAccount());
	    			qjyFriendDto2.setUserId("qjy_"+imsUsersDto1.getUserAccount());
	    			qjyFriendDto2.setFriendUserId("qjy_"+friendAccount);
	    			qjyFriendService.addOtherFriend(qjyFriendDto2);
	    		}
	    	}
	    	j.setMsg("为每个人添加好友："+friendAccount);
	    	j.setSuccess(true);
	    	return j;
	    	
	    }
	    
	    //为用户添加剩余的好友
	    @RequestMapping("/addOtherFriend")
	    @ResponseBody
	    public Json addOtherFriend(){
	    	Json j = new Json();
	    	//查询好友表里的所有用户
	        List<QjyFriendDto> list1 =	qjyFriendService.findUsers();
	    	if(list1!=null && list1.size()>0){
	    		for(QjyFriendDto qjyFriendDto1 : list1){
	    			String userId1 = qjyFriendDto1.getUserId();  //用户账号
	    			String qjuserId1 = qjyFriendDto1.getQjyAccount();
	    			userId1="c7ce96d2-31b5-4862-a1fa-c0ec9778cd60";
	    			qjuserId1="hudefu";
	    			//查询不是好友的用户
	    			List<ImsUsersDto> list2 =  qjyFriendService.findNotFriend(userId1);
	    			if(list2!=null && list2.size()>0){
	    				for(ImsUsersDto  imsUsersDto2 : list2){
	    					  Timer   timer   =   new   Timer(); 
	    					String qjfriend2 = imsUsersDto2.getQjyAccount();
	    					 QjyFriendDto qjyFriendDto2 = new QjyFriendDto();
	    					qjyFriendDto2.setUserId(qjuserId1);
	    					qjyFriendDto2.setFriendUserId(qjfriend2);
	    					qjyFriendService.addOtherFriend(qjyFriendDto2);
	    					QjyFriendDto qjyFriendDto3 = new QjyFriendDto();
	    					qjyFriendDto3.setId(UUIDGenerator.getUUID());
	    					qjyFriendDto3.setStat(1);
	    					qjyFriendDto3.setCreateTime(new Date());
	    					qjyFriendDto3.setFriendUserId(imsUsersDto2.getId());
	    					qjyFriendDto3.setQjyAccount(qjfriend2);
	    					qjyFriendDto3.setUserId(userId1);
	    					qjyFriendService.insertQJYF(qjyFriendDto3);
	    				}
	    			
	    			}
	    		}
	    	}
	    	
	    	j.setMsg("添加剩余好友成功");
	    	j.setSuccess(true);
	    	return j;
	    }
	    
}
