package com.ssic.game.common.service;

import com.gotye.entity.req.group.GetGroupMembersReq;
import com.gotye.entity.req.group.GetGroupsReq;
import com.gotye.entity.req.user.AddFriendReq;
import com.gotye.entity.req.user.DelFriendReq;
import com.gotye.entity.req.user.GetFriendsReq;
import com.gotye.entity.resp.group.GetGroupMembersResp;
import com.gotye.entity.resp.group.GetGroupsResp;
import com.gotye.entity.resp.user.AddFriendResp;
import com.gotye.entity.resp.user.DelFriendResp;
import com.gotye.entity.resp.user.GetFriendsResp;

public interface ImsQjGroupAPIService {

	public GetGroupsResp getGroups(GetGroupsReq getGroupsReq);
	
	public String getToken();
	
	public void addToken();
	
	public GetFriendsResp getFriends(GetFriendsReq getFriendsReq);
	
	public DelFriendResp delFriend(DelFriendReq delFriendReq);
	
	public AddFriendResp addFriend(AddFriendReq addFriendReq);
	
	public  GetGroupMembersResp  GetGroupUserList (GetGroupMembersReq getGroupMembersReq);
	
}
