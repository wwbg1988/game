package com.ssic.catering.manage.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssic.catering.admin.pageModel.DataGrid;
import com.ssic.catering.admin.pageModel.Json;
import com.ssic.catering.admin.pageModel.Role;
import com.ssic.catering.base.service.ICQjyFriendService;
import com.ssic.game.common.dto.ImsUsersDto;
import com.ssic.game.common.dto.QjyFriendDto;
import com.ssic.game.common.pageModel.PageHelper;
import com.ssic.game.common.pageModel.Tree;

@Controller
@RequestMapping("/cQjyFriendController")
public class CQjyFriendController {

	@Autowired
	private ICQjyFriendService cQjyFriendService;
	
	
	@RequestMapping("/manager")
	public String manager(QjyFriendDto qjyFriendDto,HttpServletRequest request){
		return "carte/cQjyFriend/cQjyFriend";
	}
	
	@RequestMapping("/qjyFriendAdd")
	public String qjyFriendAdd(){
		return "carte/cQjyFriend/cQjyFriendAdd";
	}
	@RequestMapping("/qjyFriendEdit")
	public String qjyFriendEdit(){
		return "carte/cQjyFriend/cQjyFriendEdit";
	}
	
	@RequestMapping("/findQJYF")
	@ResponseBody
	public List<QjyFriendDto>  findQJYF(QjyFriendDto qjyFriendDto){
		return cQjyFriendService.findQJYF(qjyFriendDto);
	}
	
	@RequestMapping("/insertQJYF")
	@ResponseBody
	public Json insertQJYF(QjyFriendDto qjyFriendDto){
		Json j=new Json();
		cQjyFriendService.insertQJYF(qjyFriendDto);
		j.setMsg("添加好友成功");
		j.setSuccess(true);
		return j;
	}
	
	@RequestMapping("updateQJY")
	@ResponseBody
	public Json updateQJY(QjyFriendDto qjyFriendDto){
		Json j = new Json();
		cQjyFriendService.updateQJY(qjyFriendDto);
		j.setMsg("更新好友成功");
		j.setSuccess(true);
		return j;
	}
	
	@RequestMapping("deleteQJYF")
	@ResponseBody
	public Json deleteQJYF(QjyFriendDto qjyFriendDto){
		Json j = new Json();
		cQjyFriendService.deleteQJYF(qjyFriendDto);
		j.setMsg("删除好友成功");
		j.setSuccess(true);
		return j;
	}
	
	@ResponseBody
	@RequestMapping("/dataGrid")
    public DataGrid dataGrid(ImsUsersDto imsUsersDto, PageHelper ph){
		imsUsersDto.setProjId("");
    	List<ImsUsersDto> list = cQjyFriendService.findCaterUser(imsUsersDto,ph);
        int count = cQjyFriendService.findCaterUserCount(imsUsersDto);
    	if(list!=null && list.size()>0 ){
			 for(int i=0;i<list.size();i++){
				 ImsUsersDto uDto = list.get(i);
				 QjyFriendDto qjyFriendDto = new QjyFriendDto();
				 qjyFriendDto.setUserId(uDto.getId());
				 List<QjyFriendDto> qjylist =  cQjyFriendService.findQJYF(qjyFriendDto);
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
	          
	            String result = cQjyFriendService.findUserPers(ids);
	            u.setResourceIds(result);
	            request.setAttribute("role", u);
	        }
	        return "carte/cQjyFriend/cQjyFriendGrant";
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
	        return cQjyFriendService.userTree(searchName,userPerss);
	    }
	    
	    @RequestMapping("/grantUsers")
	    @ResponseBody
	    public Json grantUsers(String resourceIds,HttpSession session) {
	        Json j = new Json();

	        if(session.getAttribute("grantUserPerss")!=null){
	        	cQjyFriendService.grantUser(session.getAttribute("grantUserPerss").toString(), resourceIds);
	            j.setSuccess(true);
	            j.setMsg("添加好友成功！");
	            return j;
	        }else{
	            j.setSuccess(false);
	            j.setMsg("寻找不到用户id，请重新添加好友!");
	            return j;
	        }


	    }
	
	
	
}
