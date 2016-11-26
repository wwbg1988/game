package com.ssic.catering.manage.controller;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssic.catering.admin.pageModel.Json;
import com.ssic.catering.base.dto.QjyCateringUserDto;
import com.ssic.catering.base.eventBus.EventRegisterC;
import com.ssic.catering.base.service.IQjyCateringService;

@Controller
@RequestMapping("/qjyCateringController")
public class QjyCateringController {

	@Autowired
	private IQjyCateringService qjyCateringService;
	@Autowired
	private EventRegisterC eventRegister;
	
	@RequestMapping("/sendMessage")
	@ResponseBody
	public Json sendMessage(QjyCateringUserDto qjyCateringUserDto) throws UnsupportedEncodingException{
		Json j = new Json();
		
		 List<String> toIdList = new ArrayList<String>();
		 toIdList.add("qjy_wuw");
		 qjyCateringUserDto.setToIdList(toIdList);
		 String text= new String("您在0724-3的赛事执行方还有未办理的事项，请尽快办理".getBytes(),"UTF-8");
		 qjyCateringUserDto.setText(text);
		 
		 
		if(qjyCateringUserDto==null){
			j.setMsg("消息信息不能为空");
			j.setSuccess(false);
			return j;
		}
		
		if(qjyCateringUserDto.getToIdList()==null || qjyCateringUserDto.getToIdList().size()==0){
			if(qjyCateringUserDto.getToIds() == null || qjyCateringUserDto.getToIds().equals("")){
				j.setMsg("给发送的id不能空");
				j.setSuccess(false);
				return j;
			}
		}
		
	
		if(qjyCateringUserDto.getText() == null || qjyCateringUserDto.getText().equals("")){
			j.setMsg("信息内容不能为空");
			j.setSuccess(false);
			return j;
		}
		
		if(qjyCateringUserDto.getText().length()>150){
			j.setMsg("信息内容长度不能大于150");
			j.setSuccess(false);
			return j;
		}
		
		   eventRegister.getQjyEvent().post(qjyCateringUserDto);
		
//		Map< String, String > map =  qjyCateringService.sendMessage(qjyCateringUserDto);
//		
//		 String message = map.get("Msg");
//		 String error_code = map.get("error_code");
//		 j.setMsg(message);
//		 j.setObj(error_code);
		return j;
	}
	
	

    @RequestMapping("/addFriend")
    @ResponseBody
   public Json addFriend(QjyCateringUserDto qjyCateringUserDto){
	   Json j = new Json();

	   if(qjyCateringUserDto==null){
		   j.setMsg("好友信息不能为空");
		   j.setSuccess(false);
		   return j;
	   }
	   if(qjyCateringUserDto.getUserAccount()==null || qjyCateringUserDto.getUserAccount().equals("")){
		   j.setMsg("被操作的账号不能为空");
		   j.setSuccess(false);
		   return j;
	   }
	   if(qjyCateringUserDto.getFriendAccount()==null || qjyCateringUserDto.getFriendAccount().equals("")){
		   j.setMsg("好友账号不能为空");
		   j.setSuccess(false);
		   return j;
	   }
	   if(qjyCateringUserDto.getUserAccount().length()>30){
		   j.setMsg("被操作的账号长度不能大于30");
		   j.setSuccess(false);
		   return j;
	   }
	   if(qjyCateringUserDto.getFriendAccount().length()>30){
		   j.setMsg("好友账号的长度不能大于30");
		   j.setSuccess(false);
		   return j;
	   }
	   JSONObject result=  qjyCateringService.addFriend(qjyCateringUserDto);
	   String error_code = result.getString("errcode");
	        if ("200".equals(error_code))
	        {
	            j.setMsg("亲加云连接成功");
	            j.setSuccess(true);
	            return j;
	        }
	        else
	        {
	        	j.setMsg("errcode=" + error_code);
	        	j.setSuccess(false);
	        	return j;
	        }
   }
    
    @RequestMapping("/deletFriend")
    @ResponseBody
    public Json deletFriend(QjyCateringUserDto qjyCateringUserDto){
    	Json j = new Json();
    	if(qjyCateringUserDto==null){
    		j.setMsg("删除好友的对象不能为空");
    		j.setSuccess(false);
    		return j;
    	}
    	if(qjyCateringUserDto.getUserAccount()==null || qjyCateringUserDto.getUserAccount().equals("")){
    		j.setMsg("被操作的账号不能为空");
    		j.setSuccess(false);
    		return j;
    	}
    	if(qjyCateringUserDto.getFriendAccount()==null || qjyCateringUserDto.getFriendAccount().equals("")){
    		j.setMsg("删除的好友不能为空");
    		j.setSuccess(false);
    		return j;
    	}
    	
    	  JSONObject result=  qjyCateringService.deletFriend(qjyCateringUserDto);
   	   String error_code = result.getString("errcode");
 	        if ("200".equals(error_code))
 	        {
 	            j.setMsg("亲加云连接成功");
 	            j.setSuccess(true);
 	            return j;
 	        }
 	        else
 	        {
 	        	j.setMsg("errcode=" + error_code);
 	        	j.setSuccess(false);
 	        	return j;
 	        }
 
    }
    
    //创建群组
    @RequestMapping("/createGroup")
    @ResponseBody
    public Json createGroup(QjyCateringUserDto qjyCateringUserDto){
    	  Map<String, Object> result=  qjyCateringService.createGroup(qjyCateringUserDto);
      	  Json j = new Json();
    	  String error_code = (String) result.get("error_code");
    	  if("200".equals(error_code)){
    		  j.setMsg("亲加云连接成功");
        	  j.setSuccess(true);
        	  j.setObj(result.get("data"));
        	  return j;
    	  }else{
    		  j.setMsg("亲加云连接失败");
        	  j.setSuccess(false);
        	  j.setObj(result.get("data"));
        	  return j; 
    	  }
    }
    
    //修改群信息
    @RequestMapping("/modifyGroup")
    @ResponseBody
    public Json modifyGroup(QjyCateringUserDto qjyCateringUserDto){
    	  Map<String, Object> result=  qjyCateringService.modifyGroup(qjyCateringUserDto);
      	  Json j = new Json();
    	  String error_code = (String) result.get("error_code");
    	  if("200".equals(error_code)){
    		  j.setMsg("亲加云连接成功");
        	  j.setSuccess(true);
        	  j.setObj(result.get("data"));
        	  return j;
    	  }else{
    		  j.setMsg("亲加云连接失败");
        	  j.setSuccess(false);
        	  j.setObj(result.get("data"));
        	  return j; 
    	  }
     }
    
    //获取群成员列表
    @RequestMapping("/getGroupUserList")
    @ResponseBody
    public Json getGroupUserList(QjyCateringUserDto qjyCateringUserDto){
    	  Map<String, Object> result=  qjyCateringService.getGroupUserList(qjyCateringUserDto);
      	  Json j = new Json();
    	  String error_code = (String) result.get("error_code");
    	  if("200".equals(error_code)){
    		  j.setMsg("亲加云连接成功");
        	  j.setSuccess(true);
        	  j.setObj(result.get("data"));
        	  return j;
    	  }else{
    		  j.setMsg("亲加云连接失败");
        	  j.setSuccess(false);
        	  j.setObj(result.get("data"));
        	  return j; 
    	  }
    }
    
    //添加群成员
    @RequestMapping("/addGroupMember")
    @ResponseBody
    public Json addGroupMember(QjyCateringUserDto qjyCateringUserDto){
    	  Map<String, Object> result=  qjyCateringService.addGroupMember(qjyCateringUserDto);
      	  Json j = new Json();
    	  String error_code = (String) result.get("error_code");
    	  if("200".equals(error_code)){
    		  j.setMsg("亲加云连接成功");
        	  j.setSuccess(true);
        	  j.setObj(result.get("data"));
        	  return j;
    	  }else{
    		  j.setMsg("亲加云连接失败");
        	  j.setSuccess(false);
        	  j.setObj(result.get("data"));
        	  return j; 
    	  }
    }
    
    //删除群成员
    @RequestMapping("/delGroupMember")
    @ResponseBody
    public Json delGroupMember(QjyCateringUserDto qjyCateringUserDto){
    	 Map<String, Object> result=  qjyCateringService.delGroupMember(qjyCateringUserDto);
     	  Json j = new Json();
   	  String error_code = (String) result.get("error_code");
   	  if("200".equals(error_code)){
   		  j.setMsg("亲加云连接成功");
       	  j.setSuccess(true);
       	  j.setObj(result.get("data"));
       	  return j;
   	  }else{
   		  j.setMsg("亲加云连接失败");
       	  j.setSuccess(false);
       	  j.setObj(result.get("data"));
       	  return j; 
   	  }
    }
    //获取app下所有的群
    @RequestMapping("/getGroups")
    @ResponseBody
    public Json getGroups(QjyCateringUserDto qjyCateringUserDto){
    	Json j = new Json();
    	Map<String, Object> result=  qjyCateringService.getGroups(qjyCateringUserDto);
    	String error_code = (String) result.get("error_code");
    	  if("200".equals(error_code)){
       		  j.setMsg("亲加云连接成功");
           	  j.setSuccess(true);
           	  j.setObj(result.get("data"));
           	  return j;
       	  }else{
       		  j.setMsg("亲加云连接失败");
           	  j.setSuccess(false);
           	  j.setObj(result.get("data"));
           	  return j; 
       	  }
    }
    
    
   //亲加云新文档测试，    可以根据用户ID获取新的群列表
    @RequestMapping("/findNewGroups")
    @ResponseBody
    public Json findNewGroups(QjyCateringUserDto qjyCateringUserDto){
    	Json j = new Json();
    	//----
    	Map<String, Object> result=  qjyCateringService.findNewGroups(qjyCateringUserDto);
    	String error_code = (String) result.get("error_code");
  	  if("200".equals(error_code)){
     		  j.setMsg("亲加云连接成功");
         	  j.setSuccess(true);
         	  j.setObj(result.get("data"));
         	  return j;
     	  }else{
     		  j.setMsg("亲加云连接失败");
         	  j.setSuccess(false);
         	  j.setObj(result.get("data"));
         	  return j; 
     	  }
    	
    	
    }
}
