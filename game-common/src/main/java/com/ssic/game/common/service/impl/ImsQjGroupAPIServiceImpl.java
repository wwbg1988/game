package com.ssic.game.common.service.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gotye.entity.User;
import com.gotye.entity.req.group.GetGroupMembersReq;
import com.gotye.entity.req.group.GetGroupsReq;
import com.gotye.entity.req.user.AddFriendReq;
import com.gotye.entity.req.user.DelFriendReq;
import com.gotye.entity.req.user.GetFriendsReq;
import com.gotye.entity.req.user.ImportUsersReq;
import com.gotye.entity.resp.group.GetGroupMembersResp;
import com.gotye.entity.resp.group.GetGroupsResp;
import com.gotye.entity.resp.user.AddFriendResp;
import com.gotye.entity.resp.user.DelFriendResp;
import com.gotye.entity.resp.user.GetFriendsResp;
import com.gotye.entity.resp.user.ImportUsersResp;
import com.gotye.remote.PasswordTokenGenerator;
import com.gotye.remote.TempTokenGenerator;
import com.gotye.remote.TokenGenerator;
import com.gotye.remote.proxy.GroupApiProxy;
import com.gotye.remote.proxy.UserApiProxy;
import com.ssic.game.common.dao.ParamConfigDao;
import com.ssic.game.common.dto.ParamConfigDto;
import com.ssic.game.common.mapper.ProcessExMapper;
import com.ssic.game.common.service.IParamConfigService;
import com.ssic.game.common.service.ImsQjGroupAPIService;
import com.ssic.util.UUIDGenerator;

@Service("imsQjGroupAPI")
public class ImsQjGroupAPIServiceImpl implements ImsQjGroupAPIService{


    @Autowired
    @Getter
	private IParamConfigService paramConfigService;
	
	//private static String appkey = "2debe681-ce19-4389-92cb-abc6475ea349";
	private static String appkey = "f5486479-03c4-4a64-bdb9-525fad544ec8";
	public static String url = "http://rest.gotye.com.cn/api";
	private static String email = "cike534222598@qq.com";
	private static String pwd = "Assassin";
	
	//测试账号
//	private static String appkey = "e16b804d-ef14-4745-8df6-b2ab1168785b";
//	public static String url = "http://rest.gotye.com.cn/api";
//	private static String email = "wuweitree@163.com";
//	private static String pwd = "wuwei123";
	
	//t_ctr_param_config param_type   001 亲加云token
	private static int paramType = 001;
	
	private static GroupApiProxy groupApiProxy;
	
	private static UserApiProxy userApiProxy;
	
	private TokenGenerator tokenGenerator ;
	
	public GetGroupsResp getGroups(GetGroupsReq getGroupsReq){
		String tokenId = getToken();
		TokenGenerator tokenGenerator = new TempTokenGenerator(tokenId);
		groupApiProxy = new GroupApiProxy();
		groupApiProxy.setTokenGenerator(tokenGenerator);
		groupApiProxy.setDebug(true);
		GetGroupsResp  getGroupsResp = null ;
		getGroupsReq.setAppkey(appkey);
		getGroupsReq.setCount(null);
		getGroupsReq.setIndex(null);
		getGroupsReq.setScope((byte) 1);
		getGroupsReq.setUserAccount(getGroupsReq.getUserAccount());
		try {
			  getGroupsResp = groupApiProxy.getGroups(getGroupsReq);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return getGroupsResp;
	}
	
	//创建用户
	public ImportUsersResp importUsers(ImportUsersReq importUsersReq){
		String tokenId = getToken();
		TokenGenerator tokenGenerator = new TempTokenGenerator(tokenId);
		userApiProxy = new UserApiProxy();
		userApiProxy.setTokenGenerator(tokenGenerator);
		userApiProxy.setDebug(true);
		importUsersReq.setAppkey(appkey);
		ImportUsersResp importUsersResp =null;
		try {
		  importUsersResp = userApiProxy.importUsers(importUsersReq);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return importUsersResp;
	} 
	
	
	//删除用户好友，需要参数userAccount ，friendAccount
	public DelFriendResp delFriend(DelFriendReq delFriendReq){
		String tokenId = getToken();
		TokenGenerator tokenGenerator = new TempTokenGenerator(tokenId);
		userApiProxy = new UserApiProxy();
		userApiProxy.setTokenGenerator(tokenGenerator);
		userApiProxy.setDebug(true);
		delFriendReq.setAppkey(appkey);
		DelFriendResp delFriendResp = null;
		try {
			delFriendResp = userApiProxy.delFriend(delFriendReq);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return delFriendResp;
	}
	
	//添加好友账户 ,需要添加参数userAccount ，friendAccount
	public AddFriendResp addFriend(AddFriendReq addFriendReq){
		String tokenId = getToken();
		TokenGenerator tokenGenerator = new TempTokenGenerator(tokenId);
		userApiProxy = new UserApiProxy();
		userApiProxy.setTokenGenerator(tokenGenerator);
		userApiProxy.setDebug(true);
		addFriendReq.setAppkey(appkey);
		AddFriendResp addFriendResp = null;
		try {
			addFriendResp = userApiProxy.addFriend(addFriendReq);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return addFriendResp;
	}
	
	//获取群成员
	public  GetGroupMembersResp  GetGroupUserList (GetGroupMembersReq getGroupMembersReq){
		 String tokenId = getToken();
		 TokenGenerator tokenGenerator = new TempTokenGenerator(tokenId);
			groupApiProxy = new GroupApiProxy();
			groupApiProxy.setTokenGenerator(tokenGenerator);
			groupApiProxy.setDebug(true);
		getGroupMembersReq.setGroupId(getGroupMembersReq.getGroupId());
		getGroupMembersReq.setAppkey(appkey);
		GetGroupMembersResp getGroupUserListResp = null;
		try {
			getGroupUserListResp = groupApiProxy.getGroupMembers(getGroupMembersReq);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return getGroupUserListResp;
	}
	
	//获取该用户的好友列表
	public GetFriendsResp getFriends(GetFriendsReq getFriendsReq){
		String tokenId = getToken();
		TokenGenerator tokenGenerator = new TempTokenGenerator(tokenId);
		userApiProxy = new UserApiProxy();
		userApiProxy.setTokenGenerator(tokenGenerator);
		userApiProxy.setDebug(true);
		getFriendsReq.setAppkey(appkey);
		getFriendsReq.setUserAccount(getFriendsReq.getUserAccount());
		getFriendsReq.setCount(500);
		getFriendsReq.setIndex(null);
		GetFriendsResp getFriendsResp = null;
		try {
			getFriendsResp = userApiProxy.getFriends(getFriendsReq);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return getFriendsResp;
	}
	
	public String getToken(){
		ParamConfigDto paramConfigDto = new ParamConfigDto();
		paramConfigDto.setParamType(paramType);
		paramConfigDto.setStat(1);
		List<ParamConfigDto> list= paramConfigService.findBy(paramConfigDto);
		String tokenId = null;
		if(list!=null && list.size()>0){
			tokenId = list.get(0).getParamValue();
			return tokenId;
		}else{
			return tokenId;
		}
	}

	//定时任务，每天把最新的token保存在本地      12个小时去一次最新的token
	public void addToken() {
		System.out.println("-------------开始获取token---------------");
		TokenGenerator tokenGenerator = new PasswordTokenGenerator(url, email, pwd);
		String tokenId=null;
		try {
			tokenId =tokenGenerator.getToken().getAccess_token();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ParamConfigDto paramConfigDto = new ParamConfigDto();
		paramConfigDto.setId(UUIDGenerator.getUUID());
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String time = sdf.format(new Date());
		paramConfigDto.setParamDescribe("亲加云Token: "+time);
		paramConfigDto.setParamName("亲加云Token");
	    paramConfigDto.setParamType(paramType);
	    paramConfigDto.setParamValue(tokenId);
	    paramConfigDto.setStat(1);
	    paramConfigDto.setCreateTime(new Date());
		paramConfigService.insertParam(paramConfigDto);	
	}
	
	
	
	
	
	
}
