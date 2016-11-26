package com.ssic.game.manage.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssic.game.admin.interceptors.SecurityInterceptor;
import com.ssic.game.admin.pageModel.Json;
import com.ssic.game.common.dto.QJHistoryMDto;
import com.ssic.game.im.dto.QjyImUserDto;
import com.ssic.game.im.service.IQjyImService;
import com.ssic.game.manage.qinjia.QJConnectDto;
import com.ssic.game.manage.qinjia.QinJiaConnectInfo;
import com.ssic.game.manage.service.IQinJiaService;

import sun.misc.BASE64Decoder;  
import sun.misc.BASE64Encoder; 


@Controller
@RequestMapping("/qinJiaController")
public class QinJiaController {
	private static final Logger logger = Logger.getLogger(SecurityInterceptor.class);
	
	@Autowired
	private IQinJiaService qinJiaService;
	@Autowired
	private IQjyImService iQjyImService;
	
	@RequestMapping("/manager")
	public String manager(){
		return "ims/projectTest";
	}
	
	@RequestMapping("/qJConnect")
	public String qJConnect(){
		return "ims/qJConnect";
	}
	
	
	@RequestMapping("/sendMessage")
	@ResponseBody
	public Json sendMessage(QJConnectDto qJConnectDto){
		Json j = new Json();
		
		if(qJConnectDto==null){
			j.setMsg("消息信息不能为空");
			j.setSuccess(false);
			return j;
		}
		
		if(qJConnectDto.getToIdList()==null || qJConnectDto.getToIdList().size()==0){
			if(qJConnectDto.getToIds() == null || qJConnectDto.getToIds().equals("")){
				j.setMsg("给发送的id不能空");
				j.setSuccess(false);
				return j;
			}
		}
		
		if(qJConnectDto.getFrom()==null || qJConnectDto.getFrom().equals("")){
			j.setMsg("发送方账号不能为空");
			j.setSuccess(false);
			return j;
		}
		
		if(qJConnectDto.getText() == null || qJConnectDto.getText().equals("")){
			j.setMsg("信息内容不能为空");
			j.setSuccess(false);
			return j;
		}
		
		if(qJConnectDto.getText().length()>150){
			j.setMsg("信息内容长度不能大于150");
			j.setSuccess(false);
			return j;
		}
		
		
		JSONObject result=  qinJiaService.sendMessage(qJConnectDto);
	        String error_code = result.getString("errcode");
	        logger.info("-------------error_code=" + error_code);

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
	
	@RequestMapping("/addUser")
	@ResponseBody
	public Json addUser(QJConnectDto qJConnectDto){
		Json j = new Json();
		
		if(qJConnectDto==null){
			j.setMsg("添加用户不能为空");
			j.setSuccess(false);
			return j;
		}
		
		JSONObject result = qinJiaService.addUser(qJConnectDto);
		 
		String error_code = result.getString("errcode");
	        logger.info("-------------error_code=" + error_code);

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
	
        @RequestMapping("/addFriend")
        @ResponseBody
       public Json addFriend(QJConnectDto qJConnectDto){
    	   Json j = new Json();
  
    	   if(qJConnectDto==null){
    		   j.setMsg("好友信息不能为空");
    		   j.setSuccess(false);
    		   return j;
    	   }
    	   if(qJConnectDto.getUserAccount()==null || qJConnectDto.getUserAccount().equals("")){
    		   j.setMsg("被操作的账号不能为空");
    		   j.setSuccess(false);
    		   return j;
    	   }
    	   if(qJConnectDto.getFriendAccount()==null || qJConnectDto.getFriendAccount().equals("")){
    		   j.setMsg("好友账号不能为空");
    		   j.setSuccess(false);
    		   return j;
    	   }
    	   if(qJConnectDto.getUserAccount().length()>30){
    		   j.setMsg("被操作的账号长度不能大于30");
    		   j.setSuccess(false);
    		   return j;
    	   }
    	   if(qJConnectDto.getFriendAccount().length()>30){
    		   j.setMsg("好友账号的长度不能大于30");
    		   j.setSuccess(false);
    		   return j;
    	   }
    	   JSONObject result=  qinJiaService.addFriend(qJConnectDto);
    	   String error_code = result.getString("errcode");
  	        logger.info("-------------error_code=" + error_code);
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
        public Json deletFriend(QJConnectDto qJConnectDto){
        	Json j = new Json();
        	if(qJConnectDto==null){
        		j.setMsg("删除好友的对象不能为空");
        		j.setSuccess(false);
        		return j;
        	}
        	if(qJConnectDto.getUserAccount()==null || qJConnectDto.getUserAccount().equals("")){
        		j.setMsg("被操作的账号不能为空");
        		j.setSuccess(false);
        		return j;
        	}
        	if(qJConnectDto.getFriendAccount()==null || qJConnectDto.getFriendAccount().equals("")){
        		j.setMsg("删除的好友不能为空");
        		j.setSuccess(false);
        		return j;
        	}
        	
        	  JSONObject result=  qinJiaService.deletFriend(qJConnectDto);
       	   String error_code = result.getString("errcode");
     	        logger.info("-------------error_code=" + error_code);
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
          
        @RequestMapping("/getHistory")
        @ResponseBody
        public Json getHistory(QJConnectDto qJConnectDto){
        	Json j = new Json();
       	    JSONObject result = qinJiaService.getHistory(qJConnectDto);
	  	    String error_code = result.getString("errcode");
	        logger.info("-------------error_code=" + error_code);
	        if ("200".equals(error_code))
	        {
	            j.setMsg("亲加云连接成功");
	            j.setSuccess(true);
	            j.setObj(result);
	            return j;
	        }
	        else
	        {
	        	j.setMsg("errcode=" + error_code);
	        	j.setSuccess(false);
	        	return j;
	        }

        }
        
        //创建群
        @RequestMapping("/createGroup")
        @ResponseBody
        public Json createGroup(QJConnectDto qJConnectDto) throws UnsupportedEncodingException{
        	Json j = new Json();
       	    JSONObject result = qinJiaService.createGroup(qJConnectDto);
    	  	String error_code = result.getString("errcode");
        	  if ("200".equals(error_code))
  	        {
  	            j.setMsg("亲加云连接成功");
  	            j.setSuccess(true);
  	            j.setObj(result);
  	            return j;
  	        }
  	        else
  	        {
  	        	j.setMsg("errcode=" + error_code);
  	        	j.setSuccess(false);
  	        	return j;
  	        }
        }
        
        //添加用户
        public Json importUsers(QjyImUserDto qjyImUserDto){
        	Json j = new Json();
        	if(qjyImUserDto.getUserAccount()==null || "".equals(qjyImUserDto.getUserAccount())){
        		j.setMsg("用户账号不能为空!");
        		j.setSuccess(false);
        		return j;
        	}
        	if(qjyImUserDto.getPwd()==null || "".equals(qjyImUserDto.getPwd())){
        		j.setMsg("用户密码不能为空!");
        		j.setSuccess(false);
        		return j;
        	}
        	if(qjyImUserDto.getNickname()==null || "".equals(qjyImUserDto.getNickname())){
        		j.setMsg("用户昵称不能为空!");
        		j.setSuccess(false);
        		return j;
        	}
        	Map<String, Object> map =  iQjyImService.importUsers(qjyImUserDto);
        	String error_code = (String) map.get("error_code");
        	if("200".equals(error_code)){
        		j.setMsg("添加用户成功!");
        		j.setSuccess(true);
        		return j;
        	}else{
        		j.setMsg("添加用户失败");
        		j.setSuccess(false);
        		return j;
        	}
        }
        
        
        //上传文件
        @RequestMapping("/UploadFile")
        @ResponseBody
        public Json uploadFile(QJConnectDto qJConnectDto){
        	Json j = new Json();
        	String apk = "733a1dfc-d717-49e6-8ea9-3d700e1988b2";
        	String type = "2"; //1-语音 2-图片
        	   String strImg = GetImageStr();  
               System.out.println(strImg);
            String file = strImg;
            qJConnectDto.setAppkey(apk);;
            qJConnectDto.setFile(file);
            
            JSONObject result = qinJiaService.uploadFile(qJConnectDto);
            String error_code = result.getString("errcode");
            if ("200".equals(error_code))
	        {
	            j.setMsg("亲加云连接成功");
	            j.setSuccess(true);
	            j.setObj(result);
	            return j;
	        }
	        else
	        {
	        	j.setMsg("errcode=" + error_code);
	        	j.setSuccess(false);
	        	return j;
	        }
        }
        
        //图片转化成base64字符串  
        public static String GetImageStr()  
        {//将图片文件转化为字节数组字符串，并对其进行Base64编码处理  
            String imgFile = "E://upload/upload/20150722/4e2b803226074622b46edd1af222f544.png";//待处理的图片  
            InputStream in = null;  
            byte[] data = null;  
            //读取图片字节数组  
            try   
            {  
                in = new FileInputStream(imgFile);          
                data = new byte[in.available()];  
                in.read(data);  
                in.close();  
            }   
            catch (IOException e)   
            {  
                e.printStackTrace();  
            }  
            //对字节数组Base64编码  
            BASE64Encoder encoder = new BASE64Encoder();  
            return encoder.encode(data);//返回Base64编码过的字节数组字符串  
        }  
}
