package com.ssic.gotye.GotyeApiClient;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gotye.entity.req.group.AddGroupMemberReq;
import com.gotye.entity.req.group.CreateGroupReq;
import com.gotye.entity.req.group.DelGroupMemberReq;
import com.gotye.entity.req.group.DismissGroupReq;
import com.gotye.entity.req.group.GetGroupsReq;
import com.gotye.entity.resp.group.AddGroupMemberResp;
import com.gotye.entity.resp.group.CreateGroupResp;
import com.gotye.entity.resp.group.DelGroupMemberResp;
import com.gotye.entity.resp.group.DismissGroupResp;
import com.gotye.entity.resp.group.GetGroupsResp;
import com.gotye.remote.PasswordTokenGenerator;
import com.gotye.remote.TempTokenGenerator;
import com.gotye.remote.TokenGenerator;
import com.gotye.remote.proxy.GroupApiProxy;
import com.ssic.game.common.dto.ParamConfigDto;
import com.ssic.game.common.service.IParamConfigService;

@Repository
public class QJGroupApi {
	
	@Autowired
	private IParamConfigService paramConfigService;
	
	private static String appkey = "2debe681-ce19-4389-92cb-abc6475ea349";
	public static String url = "http://rest.gotye.com.cn/api";
	private static String email = "cike534222598@qq.com";
	private static String pwd = "Assassin";
	
	//t_ctr_param_config param_type   001 亲加云token
	private static int paramType = 001;
	
	private static GroupApiProxy groupApiProxy;
	
	public GetGroupsResp getGroups(GetGroupsReq getGroupsReq){
		//TokenGenerator tokenGenerator = new PasswordTokenGenerator(url, email, pwd);
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
		getGroupsReq.setUserAccount("qjy_Terry");
		try {
			  getGroupsResp = groupApiProxy.getGroups(getGroupsReq);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return getGroupsResp;
	}
	
	//创建群
	public CreateGroupResp createGroup(CreateGroupReq createGroupReq){
		String tokenId = getToken();
		TokenGenerator tokenGenerator = new TempTokenGenerator(tokenId);
		groupApiProxy = new GroupApiProxy();
		groupApiProxy.setTokenGenerator(tokenGenerator);
		groupApiProxy.setDebug(true);
		createGroupReq.setGroupName(createGroupReq.getGroupName());
		createGroupReq.setIsPrivate((byte) 1);
		createGroupReq.setOwnerAccount(createGroupReq.getOwnerAccount());
		createGroupReq.setAppkey(appkey);
		createGroupReq.setNeedVerify((byte) 0);
		createGroupReq.setGroupInfo(createGroupReq.getGroupInfo());
		CreateGroupResp createGroupResp = null;
		try {
			createGroupResp = groupApiProxy.createGroup(createGroupReq);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return createGroupResp;
	}
	
	//解散群
	public DismissGroupResp dismissGroup(DismissGroupReq dismissGroupReq){
		String tokenId = getToken();
		TokenGenerator tokenGenerator = new TempTokenGenerator(tokenId);
		groupApiProxy = new GroupApiProxy();
		groupApiProxy.setTokenGenerator(tokenGenerator);
		groupApiProxy.setDebug(true);
		dismissGroupReq.setAppkey(appkey);
		dismissGroupReq.setGroupId(dismissGroupReq.getGroupId());
		DismissGroupResp dismissGroupResp =null;
		try {
		  dismissGroupResp = groupApiProxy.dismissGroup(dismissGroupReq);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dismissGroupResp;
	}
	
	//删除群成员
	public  DelGroupMemberResp  delGroupMember(DelGroupMemberReq delGroupMemberReq){
		String tokenId = getToken();
		TokenGenerator tokenGenerator = new TempTokenGenerator(tokenId);
		groupApiProxy = new GroupApiProxy();
		groupApiProxy.setTokenGenerator(tokenGenerator);
		groupApiProxy.setDebug(true);
		delGroupMemberReq.setAppkey(appkey);
		DelGroupMemberResp delGroupMemberResp = null;
		try {
			delGroupMemberResp = groupApiProxy.delGroupMember(delGroupMemberReq);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return delGroupMemberResp;
	}
	
	//添加群成员
	public AddGroupMemberResp  addGroupMember(AddGroupMemberReq addGroupMemberReq){
		String tokenId = getToken();
		TokenGenerator tokenGenerator = new TempTokenGenerator(tokenId);
		groupApiProxy = new GroupApiProxy();
		groupApiProxy.setTokenGenerator(tokenGenerator);
		groupApiProxy.setDebug(true);
		addGroupMemberReq.setAppkey(appkey);
		AddGroupMemberResp addGroupMemberResp = null;
		try {
			addGroupMemberResp = groupApiProxy.addGroupMember(addGroupMemberReq);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return addGroupMemberResp;
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

}
