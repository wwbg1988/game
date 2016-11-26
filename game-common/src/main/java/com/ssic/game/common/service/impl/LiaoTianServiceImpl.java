package com.ssic.game.common.service.impl;

import java.util.List;

import net.sf.json.JSONObject;

import org.junit.Test;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ssic.game.common.dto.LTUserDto;
import com.ssic.game.common.service.ILiaoTianService;
import com.ssic.util.HttpClientUtil;

@Service
public class LiaoTianServiceImpl implements ILiaoTianService{

	//private static String ltip="10.10.11.111";
	
	//private static String ltip="192.168.1.234";
	private static String ltip="172.16.1.111";
	
	
	
	@Override
	public String addLTUser(LTUserDto lTUserDto) {    //IM创建用户
		// TODO Auto-generated method stub
		String userAccount = lTUserDto.getUserAccount();
		String password = lTUserDto.getPassword();
		System.out.println(userAccount+",,,,"+password);
		String url="http://"+ltip+"/user/regUser.do?userAccount="+userAccount+"&password="+password;
		System.out.println("url="+url);
		String result = HttpClientUtil.sendGetRequest(url, null);
		System.out.println("result="+result);
		JSONObject jsonresult = JSONObject.fromObject(result);
		String state = jsonresult.getString("status");
		System.out.println("state="+state);
		return state;
	}

	@Override
	public String delLTUser(LTUserDto lTUserDto){   //IM删除用户
		String userAccount = lTUserDto.getUserAccount();
		String url="http://"+ltip+"/user/delUser.do?userAccount="+userAccount;
		String result = HttpClientUtil.sendGetRequest(url, null);
		JSONObject jsonresult = JSONObject.fromObject(result);
		String state = jsonresult.getString("status");
		return state;
	}
	
	@Override
	public String EditLTPassWord(LTUserDto lTUserDto){   //IM修改用户密码
		String userAccount = lTUserDto.getUserAccount();
		String newPassword = lTUserDto.getNewPassword();
		String url="http://"+ltip+"/user/changePassword.do?userAccount="+userAccount+"&newPassword="+newPassword;
		String result = HttpClientUtil.sendGetRequest(url, null);
		JSONObject jsonresult = JSONObject.fromObject(result);
		String state = jsonresult.getString("status");
		return state;
	}
	
	@Override
	public String findIsExistByAccount(LTUserDto lTUserDto) {    //根据账号查询用户是否存在    200存在   500不存在
		// TODO Auto-generated method stub
		String userAccount = lTUserDto.getUserAccount();
		String url="http://"+ltip+"/user/getUserByAccount.do?userAccount="+userAccount;
		String result = HttpClientUtil.sendGetRequest(url, null);
		JSONObject jsonresult = JSONObject.fromObject(result);
		String state  = jsonresult.getString("status");
		return state;
	}
	
	@Override
	public String findIsExistByAccountPassword(LTUserDto lTUserDto) {      //根据用户账号密码查询用户是否存在  200存在   500不存在
		// TODO Auto-generated method stub
		String userAccount = lTUserDto.getUserAccount();
		String password = lTUserDto.getPassword();
		String url="http://"+ltip+"/user/getUserByAccountAndPassword.do?userAccount="+userAccount+"&password="+password;
		String result = HttpClientUtil.sendGetRequest(url, null);
		JSONObject jsonresult = JSONObject.fromObject(result);
		String state  = jsonresult.getString("status");
		return state;
	}
	
	@Override
	public String sendMessage(LTUserDto lTUserDto) {      //一个用户为另一些人推送消息
		// TODO Auto-generated method stub
		String from = lTUserDto.getFrom();
		String messageType = lTUserDto.getMessageType();
		String message = "message";
		List<String> tolist = lTUserDto.getTolist();
		String errorCode = "100";
		if(StringUtils.isEmpty(from)){
			return errorCode;
		}
		if(StringUtils.isEmpty(messageType)){
			return errorCode;
		}
		if(StringUtils.isEmpty(message)){
			return errorCode;
		}
		if(tolist==null || tolist.size()==0){
			return errorCode;
		}
		String state="";
		if(tolist!=null && tolist.size()>0){
			for(String toId : tolist){
				String url="http://"+ltip+"/message/send.do?from="+from+"&to="+toId+"&messageType="+messageType+"&message="+message;
				String result = HttpClientUtil.sendGetRequest(url, null);
				JSONObject jsonresult = JSONObject.fromObject(result);
				state  = jsonresult.getString("status");
			}
		}
		return state;
	}


	
	@Test
	public void testAddu() {
		String userAccount="liuaiming";
		String password="e10adc3949ba59abbe56e057f20f883e";
		String newPassword = "e10adc3949ba59abbe56e057f20f883e";
		String from = "yingzong";
		String to="gaoyufeng";
		String messageType = "text";
		
		
		//String url="http://"+ltip+"/user/regUser.do?userAccount="+userAccount+"&password="+password;
		//String url="http://"+ltip+"/user/delUser.do?userAccount="+userAccount;
		//String url="http://"+ltip+"/user/changePassword.do?userAccount="+userAccount+"&newPassword="+newPassword;
		//String url="http://"+ltip+"/user/getUserByAccount.do?userAccount="+userAccount;
		//String url="http://"+ltip+"/user/getUserByAccountAndPassword.do?userAccount="+userAccount+"&password="+password;
//		long star = System.currentTimeMillis();
//		for(int i=0;i<300;i++){
//			String url="http://"+ltip+"/message/send.do?from="+from+"&to="+to+"&messageType="+messageType+"&message="+i;
//			System.out.println(url);
//			String result = HttpClientUtil.sendGetRequest(url, null);
//			System.out.println(result);
//			JSONObject jsonresult = JSONObject.fromObject(result);
//			String state = jsonresult.getString("status");
//			System.out.println("111111111111"+state);
//		}
//		long end = System.currentTimeMillis();
//		System.out.println("运行时间: "+(end-star)+"毫秒");
		
		String url="http://"+ltip+"/message/send.do?from="+from+"&to="+to+"&messageType="+messageType+"&message="+"啦啦啦啦";
		
		System.out.println(url);
		String result = HttpClientUtil.sendGetRequest(url, null);
		System.out.println(result);
		JSONObject jsonresult = JSONObject.fromObject(result);
		String state = jsonresult.getString("status");
		System.out.println("111111111111"+state);
		
		//开启新线程发送消息
		
		
		
	}

	


	
}
