package com.ssic.gotye.GotyeApiClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.gotye.config.CodeDefs;
import com.gotye.config.Constants;
import com.gotye.entity.User;
import com.gotye.entity.req.group.AddGroupMemberReq;
import com.gotye.entity.req.group.ChangeGroupOwnerReq;
import com.gotye.entity.req.group.CreateGroupReq;
import com.gotye.entity.req.group.DelGroupMemberReq;
import com.gotye.entity.req.group.DismissGroupReq;
import com.gotye.entity.req.group.GetGroupMembersReq;
import com.gotye.entity.req.group.GetGroupReq;
import com.gotye.entity.req.group.GetGroupsReq;
import com.gotye.entity.req.group.ModifyGroupReq;
import com.gotye.entity.req.msg.GetMsgsReq;
import com.gotye.entity.req.msg.SendMsgReq;
import com.gotye.entity.req.res.UploadFileReq;
import com.gotye.entity.req.room.CreateIMRoomReq;
import com.gotye.entity.req.room.DelIMRoomReq;
import com.gotye.entity.req.room.GetIMRoomReq;
import com.gotye.entity.req.room.GetIMRoomsMemberCountReq;
import com.gotye.entity.req.room.GetIMRoomsReq;
import com.gotye.entity.req.room.ModifyIMRoomReq;
import com.gotye.entity.req.user.AddBlacklistReq;
import com.gotye.entity.req.user.AddFriendReq;
import com.gotye.entity.req.user.DelBlacklistReq;
import com.gotye.entity.req.user.DelFriendReq;
import com.gotye.entity.req.user.DisableSayReq;
import com.gotye.entity.req.user.EnableSayReq;
import com.gotye.entity.req.user.GetBlacklistsReq;
import com.gotye.entity.req.user.GetDisableSayUsersReq;
import com.gotye.entity.req.user.GetFriendsReq;
import com.gotye.entity.req.user.GetUsersReq;
import com.gotye.entity.req.user.ImportUsersReq;
import com.gotye.entity.req.user.ModifyUserPwdReq;
import com.gotye.entity.req.word.GetKeywordReq;
import com.gotye.entity.req.word.SetKeywordReq;
import com.gotye.entity.resp.group.AddGroupMemberResp;
import com.gotye.entity.resp.group.ChangeGroupOwnerResp;
import com.gotye.entity.resp.group.CreateGroupResp;
import com.gotye.entity.resp.group.DelGroupMemberResp;
import com.gotye.entity.resp.group.DismissGroupResp;
import com.gotye.entity.resp.group.GetGroupMembersResp;
import com.gotye.entity.resp.group.GetGroupResp;
import com.gotye.entity.resp.group.GetGroupsResp;
import com.gotye.entity.resp.group.ModifyGroupResp;
import com.gotye.entity.resp.msg.GetMsgsResp;
import com.gotye.entity.resp.msg.SendMsgResp;
import com.gotye.entity.resp.res.UploadFileResp;
import com.gotye.entity.resp.room.CreateIMRoomResp;
import com.gotye.entity.resp.room.DelIMRoomResp;
import com.gotye.entity.resp.room.GetIMRoomResp;
import com.gotye.entity.resp.room.GetIMRoomsMemberCountResp;
import com.gotye.entity.resp.room.GetIMRoomsResp;
import com.gotye.entity.resp.room.ModifyIMRoomResp;
import com.gotye.entity.resp.user.AddBlacklistResp;
import com.gotye.entity.resp.user.AddFriendResp;
import com.gotye.entity.resp.user.DelBlacklistResp;
import com.gotye.entity.resp.user.DelFriendResp;
import com.gotye.entity.resp.user.DisableSayResp;
import com.gotye.entity.resp.user.EnableSayResp;
import com.gotye.entity.resp.user.GetBlacklistsResp;
import com.gotye.entity.resp.user.GetDisableSayUsersResp;
import com.gotye.entity.resp.user.GetFriendsResp;
import com.gotye.entity.resp.user.GetUsersResp;
import com.gotye.entity.resp.user.ImportUsersResp;
import com.gotye.entity.resp.user.ModifyUserPwdResp;
import com.gotye.entity.resp.word.GetKeywordResp;
import com.gotye.entity.resp.word.SetKeywordResp;
import com.gotye.remote.PasswordTokenGenerator;
import com.gotye.remote.TempTokenGenerator;
import com.gotye.remote.TokenGenerator;
import com.gotye.remote.proxy.GroupApiProxy;
import com.gotye.remote.proxy.MsgApiProxy;
import com.gotye.remote.proxy.ResApiProxy;
import com.gotye.remote.proxy.RoomApiProxy;
import com.gotye.remote.proxy.UserApiProxy;
import com.gotye.remote.proxy.WordApiProxy;

public class ApiTest {
	private static String appkey = "bbd83199-de55-4665-a740-7021b42664cd";

	public static String url = "http://rest.gotye.com.cn/api";
	private static String email = "";
	private static String pwd = "";

	private static String head1 = "/9j/4AAQSkZJRgABAQEAYABgAAD/4QAiRXhpZgAATU0AKgAAAAgAAQESAAMAAAABAAEAAAAAAAD//gA8Q1JFQVRPUjogZ2QtanBlZyB2MS4wICh1c2luZyBJSkcgSlBFRyB2NjIpLCBxdWFsaXR5ID0gOTAKAP/bAEMAAgEBAgEBAgICAgICAgIDBQMDAwMDBgQEAwUHBgcHBwYHBwgJCwkICAoIBwcKDQoKCwwMDAwHCQ4PDQwOCwwMDP/bAEMBAgICAwMDBgMDBgwIBwgMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDP/AABEIABsAKgMBIgACEQEDEQH/xAAfAAABBQEBAQEBAQAAAAAAAAAAAQIDBAUGBwgJCgv/xAC1EAACAQMDAgQDBQUEBAAAAX0BAgMABBEFEiExQQYTUWEHInEUMoGRoQgjQrHBFVLR8CQzYnKCCQoWFxgZGiUmJygpKjQ1Njc4OTpDREVGR0hJSlNUVVZXWFlaY2RlZmdoaWpzdHV2d3h5eoOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3uLm6wsPExcbHyMnK0tPU1dbX2Nna4eLj5OXm5+jp6vHy8/T19vf4+fr/xAAfAQADAQEBAQEBAQEBAAAAAAAAAQIDBAUGBwgJCgv/xAC1EQACAQIEBAMEBwUEBAABAncAAQIDEQQFITEGEkFRB2FxEyIygQgUQpGhscEJIzNS8BVictEKFiQ04SXxFxgZGiYnKCkqNTY3ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqCg4SFhoeIiYqSk5SVlpeYmZqio6Slpqeoqaqys7S1tre4ubrCw8TFxsfIycrS09TV1tfY2dri4+Tl5ufo6ery8/T19vf4+fr/2gAMAwEAAhEDEQA/APBtN+GHhL4gfEW41Ozudd+FtlpMUMaa89tcLJEcx2q3Auo3QHe1zZiY7Ixsld/kI2P7j8Kv2b9A+CP7QPhm4+Iuj3/xc0bxH4gt9OufE2pw3Wj2Npfqz/ZEuFvZxHefaI7m2EscilXQKoSdlLPi/tNf8EvfHnwM+F1p8QLK1XXpLPw82qeI9Pd5mi077Irb0O1nlkT7IsI8qO4Ls6TEARqPL+uvgj8LPFfxP+FfhnwF488AXPiDwb4Tubaz0fxLYai2hXFm0AUzXXl74pri3mJjdJEAZkadX3vGHn/Oa2PjGClSqOUHdNaXVrbPR762vrpvqfWRwbqyca1NKSaa87vZrVbbvp5aHSQ/so+BPhzrUi6DoMP2yZ5zNqU7Pc39yZp5Z5d91KWlcvJNISSxyCo6KgXL+B1tN4+0PxDp+oLHcJZ+Ktb0yGUxLD5dvb6lcRQpkcsUjVBuPJAyeTmvo648Grc32+SSNQzbmOCu3uT0r5Z/4J7fEXQ/jx4G+JcOnSXZutJ8calfT+Zb+SDBfzPcW7D+8GUSE5+YEHI6E/Pyc69Gc53bi0772vdfme1GjGhUTppK91ba+mx5P+0r8J4td8S30en3X2TT9NAC3UcgH7wcGQFjgKuepIGATkV82t8NtLmYuvjqGVWOQ6yQKr57gKwUZ9AAPTiv0E/aT/Zws/Hfwj1yzeRY2gT7XDEknzyvGCQpUcspBYbRznaRyBX5iXXwUubS6kiGq3luI3KCJ5bJGjwcbSJJkcEdMMqsO6g5A+jynGOpS9nGXw23PmcRlqo1JVptpzbdkv60Pbfjf/wWV+J3x7+FC+E9V+FnhGzsJEYa1NexzyRXTQyIy7CSVt94ykiPvBSYfOFkNdF8PP8Agvd8VPh3rFnp3jLwD4R1Lw7YyyWss1haXNh5UKgRRBJU3xLHGwycQtuT5BtYBq8J0eST4cfHDw3HodxdaZDcQRTPDBO4iLhYpNwTO0Hco5A6EjoxB+201DUPid8TvBdlrGt+I5ba+1REnS21m6s96/KNuYZEIUg8qMA4GQcDHVmFHL8KklQvGSb+Jp6a7a/n2OLL8djcVo6zUk0rWTW9t9PLocTff8F6f+Fl+E9e0W6+Hd14Zk1y3httNvrK9OoG0EkoS5juY5Ioju8gTFWiDcsoALAkcZ+wt+17a/DX43LrsnxG0TxJ4b1O5OmaxoNjpt1Nqy2kqmWK8YJaBStrcSFWw27EjABhIhre+Jfw407Q/wBo3XtJt5tcXTtM8PeH9VtYH1q8kWC5uGxNIu6UnLenQZOAMnPjvxR8FWGr+FNHhvheahHqHiOzs5lu72a4BjkguXfbvc7GLRod64b5QM44rLD0cDWi1Ri4qaXZ2TV+t9bPvud9XH4qlH981Llk/Jtp26dNNrP7z7X8Wf8ABWD9n3WNQWztfEXiKeaeVbdCvhbU1VpGMQ2gtbjoJMk9AEbnJQP5tef8FAf2cJryV5PF+nSSM5LMdCvWLHPJJ+z8/WvnT41fA3w3LeXNnJb6jcWwMsixzatdyiN1SLDLulO1hk/MMHnrXn2tfAzw3daxdyyW+oNJJM7sx1W75JYk/wDLWsaPDuAlFSjUqq/nH/5EmpxBVbaqUoO3k/8A5I//2Q==";
	private String head2 = "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";

	private static Long groupId;
	private static Long roomId;
	// 帐号会自动导入
	private static String account1 = "test_acount1";
	private static String account2 = "test_acount2";
	private static String account3 = "test_acount3";
	private static String account4 = "test_acount4";

	private static GroupApiProxy groupApiProxy;
	private static RoomApiProxy roomApiProxy;
	private static MsgApiProxy msgApiProxy;
	private static UserApiProxy userApiProxy;
	private static WordApiProxy wordApiProxy;
	private static ResApiProxy resApiProxy;

	private static boolean init = false;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		//TokenGenerator tokenGenerator = new PasswordTokenGenerator(url, email, pwd);
		// 测试时建议把返回的access_token保存。用下面的TokenGenerator测试。
		 TokenGenerator tokenGenerator = new TempTokenGenerator("");

		groupApiProxy = new GroupApiProxy();
		groupApiProxy.setTokenGenerator(tokenGenerator);
		groupApiProxy.setDebug(true);

		roomApiProxy = new RoomApiProxy();
		roomApiProxy.setTokenGenerator(tokenGenerator);
		roomApiProxy.setDebug(true);

		msgApiProxy = new MsgApiProxy();
		msgApiProxy.setTokenGenerator(tokenGenerator);
		msgApiProxy.setDebug(true);

		userApiProxy = new UserApiProxy();
		userApiProxy.setTokenGenerator(tokenGenerator);
		userApiProxy.setDebug(true);

		wordApiProxy = new WordApiProxy();
		wordApiProxy.setTokenGenerator(tokenGenerator);
		wordApiProxy.setDebug(true);

		resApiProxy = new ResApiProxy();
		resApiProxy.setTokenGenerator(tokenGenerator);
		resApiProxy.setDebug(true);

		List<User> users = new ArrayList<User>();
		User user = new User();
		user.setAccount(account1);
		user.setNickname(account1 + "-Nick");
		user.setPwd("123456");
		users.add(user);

		user = new User();
		user.setAccount(account2);
		user.setNickname(account2 + "-Nick");
		user.setPwd("123456");
		users.add(user);

		user = new User();
		user.setAccount(account3);
		user.setNickname(account3 + "-Nick");
		user.setPwd("123456");
		users.add(user);

		user = new User();
		user.setAccount(account4);
		user.setNickname(account4 + "-Nick");
		user.setPwd("123456");
		users.add(user);

		ImportUsersReq importUsersReq = new ImportUsersReq();
		importUsersReq.setUsers(users);
		importUsersReq.setAppkey(appkey);

		ImportUsersResp importUsersResp = userApiProxy.importUsers(importUsersReq);
		Assert.assertTrue(CodeDefs.SUCCESS == importUsersResp.getStatus() || CodeDefs.USER.USER_IS_EXSITS == importUsersResp.getStatus());

		// CreateGroupReq
		CreateGroupReq createGroupReq = new CreateGroupReq();
		createGroupReq.setGroupName("test1");
		createGroupReq.setIsPrivate((byte) 1);
		createGroupReq.setOwnerAccount(account1);
		createGroupReq.setAppkey(appkey);
		createGroupReq.setNeedVerify((byte) 0);
		createGroupReq.setGroupInfo("test test test");
		CreateGroupResp createGroupResp = groupApiProxy.createGroup(createGroupReq);
		Assert.assertNotNull(createGroupResp);
	//	Assert.assertEquals(CodeDefs.SUCCESS, createGroupResp.getStatus());
		groupId = createGroupResp.getEntity().getGroupId();

		
		// AddGroupMemberReq account2
		AddGroupMemberReq addGroupMemberReq = new AddGroupMemberReq();
		addGroupMemberReq.setGroupId(groupId);
		addGroupMemberReq.setUserAccount(account2);
		addGroupMemberReq.setAppkey(appkey);
		AddGroupMemberResp addGroupMemberResp = groupApiProxy.addGroupMember(addGroupMemberReq);
	//	Assert.assertEquals(CodeDefs.SUCCESS, addGroupMemberResp.getStatus());

		//获取群列表
		GetGroupsReq getGroupsReq = new GetGroupsReq();
		getGroupsReq.setAppkey(appkey);
		getGroupsReq.setIndex(null);
		getGroupsReq.setCount(null);
		getGroupsReq.setScope((byte) 1);
		getGroupsReq.setUserAccount(account1);
		GetGroupsResp  getGroupsResp = groupApiProxy.getGroups(getGroupsReq);
		
		// CreateIMRoomReq
		CreateIMRoomReq createIMRoomReq = new CreateIMRoomReq();
		createIMRoomReq.setRoomName("test room1");
		createIMRoomReq.setAppkey(appkey);
		createIMRoomReq.setMaxUserNumber(10);
		CreateIMRoomResp createIMRoomResp = roomApiProxy.createIMRoom(createIMRoomReq);
	//	Assert.assertEquals(CodeDefs.SUCCESS, createIMRoomResp.getStatus());
		roomId = createIMRoomResp.getEntity().getRoomId();
		init = true;
	}


	@AfterClass
	public static void setAfterClass() throws Exception {
		Assert.assertTrue(init);
		// 清理群
		DismissGroupReq dismissGroupReq = new DismissGroupReq();
		dismissGroupReq.setAppkey(appkey);
		dismissGroupReq.setGroupId(groupId);
		// ******************************DismissGroup
		DismissGroupResp dismissGroupResp = groupApiProxy.dismissGroup(dismissGroupReq);
	//	Assert.assertEquals(CodeDefs.SUCCESS, dismissGroupResp.getStatus());

		// 清理聊天室
		DelIMRoomReq deleteIMRoomReq = new DelIMRoomReq();
		deleteIMRoomReq.setAppkey(appkey);
		deleteIMRoomReq.setRoomId(roomId);
		DelIMRoomResp deleteIMRoomResp = roomApiProxy.deleteIMRoom(deleteIMRoomReq);
	//	Assert.assertEquals(CodeDefs.SUCCESS, deleteIMRoomResp.getStatus());

	}

	@Test
	public void resApiTest() throws IOException {
		UploadFileReq uploadFileReq = new UploadFileReq();
		uploadFileReq.setFile(head1);
		uploadFileReq.setType(Constants.FILE_TYPE_IMG);
		UploadFileResp uploadFileResp = resApiProxy.uploadFile(uploadFileReq);
	//	Assert.assertEquals(CodeDefs.SUCCESS, uploadFileResp.getStatus());
		System.out.println(uploadFileResp.getUrl());
	}

	@Test
	public void wordApiTest() throws IOException {
		SetKeywordReq setKeywordReq = new SetKeywordReq();
		setKeywordReq.setAppkey(appkey);
		setKeywordReq.setScope((byte) 1);
		String keyword = "test1,test2";
		setKeywordReq.setKeyword(keyword);
		SetKeywordResp setKeywordResp = wordApiProxy.setKeyword(setKeywordReq);
	//	Assert.assertEquals(CodeDefs.SUCCESS, setKeywordResp.getStatus());
	//	Assert.assertEquals(1, setKeywordResp.getAffectedRows());

		GetKeywordReq getKeywordReq = new GetKeywordReq();
		getKeywordReq.setAppkey(appkey);
		GetKeywordResp getKeywordResp = wordApiProxy.getKeyword(getKeywordReq);
	//	Assert.assertEquals(CodeDefs.SUCCESS, getKeywordResp.getStatus());
		Assert.assertTrue(getKeywordResp.getEntity().contains(keyword));
	}

	@Test
	public void msgApiTest() throws IOException {

		long startTime = System.currentTimeMillis() - 5 * 60 * 1000;
		// SendMsgReq to user
		SendMsgReq sendMsgReq = new SendMsgReq();
		sendMsgReq.setAppkey(appkey);
		sendMsgReq.setSenderAccount(account1);
		sendMsgReq.setReceiverType((byte) 0);
		sendMsgReq.setReceiverIds(new String[] { account2 });
		sendMsgReq.setContent("SendMsgReq user test");
		sendMsgReq.setIsSave((byte) 1);
		sendMsgReq.setExtraData("ex data");
		SendMsgResp sendMsgResp = msgApiProxy.sendMsg(sendMsgReq);
	//	Assert.assertEquals(CodeDefs.SUCCESS, sendMsgResp.getStatus());

		// send img
		sendMsgReq.setContent(head1);
		sendMsgReq.setMsgType((byte) 3);
		sendMsgResp = msgApiProxy.sendMsg(sendMsgReq);
	//	Assert.assertEquals(CodeDefs.SUCCESS, sendMsgResp.getStatus());
		// send voice
		sendMsgReq.setContent("GetFile?FileID=b0cc6b64-e204-427c-a131-461d428b430e");
		sendMsgReq.setVoiceLen(253 * 20);
		sendMsgReq.setMsgType((byte) 6);
		sendMsgResp = msgApiProxy.sendMsg(sendMsgReq);
	//	Assert.assertEquals(CodeDefs.SUCCESS, sendMsgResp.getStatus());

		sendMsgReq.setMsgType((byte) 0);
		// sendMsgReq to room
		sendMsgReq.setReceiverIds(new String[] { roomId + "" });
		sendMsgReq.setContent("SendMsgReq room test");
		sendMsgReq.setReceiverType((byte) 1);
		sendMsgResp = msgApiProxy.sendMsg(sendMsgReq);
	//	Assert.assertEquals(CodeDefs.SUCCESS, sendMsgResp.getStatus());

		// sendMsgReq to group
		sendMsgReq.setReceiverIds(new String[] { groupId + "" });
		sendMsgReq.setContent("SendMsgReq room test");
		sendMsgReq.setReceiverType((byte) 2);
		sendMsgResp = msgApiProxy.sendMsg(sendMsgReq);
	//	Assert.assertEquals(CodeDefs.SUCCESS, sendMsgResp.getStatus());

		// GetMsgsReq user
		GetMsgsReq getMsgsReq = new GetMsgsReq();
		getMsgsReq.setAppkey(appkey);
		getMsgsReq.setReceiverType((byte) 0);
		getMsgsReq.setSenderAccount(account1);
		getMsgsReq.setReceiverId(account2);
		getMsgsReq.setStartTime(startTime);
		GetMsgsResp getMsgsResp = msgApiProxy.getMsgs(getMsgsReq);
	//	Assert.assertEquals(CodeDefs.SUCCESS, getMsgsResp.getStatus());
		Assert.assertTrue(getMsgsResp.getEntities().size() > 0);

		// getMsgsReq room
		getMsgsReq.setReceiverType((byte) 1);
		getMsgsReq.setReceiverId(roomId + "");
		getMsgsReq.setCount(10);
		getMsgsResp = msgApiProxy.getMsgs(getMsgsReq);
	//	Assert.assertEquals(CodeDefs.SUCCESS, getMsgsResp.getStatus());
		Assert.assertTrue(getMsgsResp.getEntities().size() == 1);
		// getMsgsReq group
		getMsgsReq.setReceiverType((byte) 2);
		getMsgsReq.setReceiverId(groupId + "");
		getMsgsReq.setCount(10);
		getMsgsResp = msgApiProxy.getMsgs(getMsgsReq);
	//	Assert.assertEquals(CodeDefs.SUCCESS, getMsgsResp.getStatus());
		Assert.assertTrue(getMsgsResp.getEntities().size() == 1);
	}

	@Test
	public void roomApiTest() throws IOException {
		// 拉聊天室成员
		GetIMRoomsMemberCountReq getIMRoomsMemberCountReq = new GetIMRoomsMemberCountReq();
		getIMRoomsMemberCountReq.setAppkey(appkey);
		getIMRoomsMemberCountReq.setRoomIds(new Long[] { roomId });
		GetIMRoomsMemberCountResp getIMRoomsMemberCountResp = roomApiProxy.getIMRoomsMemberCount(getIMRoomsMemberCountReq);
	//	Assert.assertEquals(CodeDefs.SUCCESS, getIMRoomsMemberCountResp.getStatus());
		Assert.assertEquals(1, getIMRoomsMemberCountResp.getEntities().size());
		Assert.assertEquals(0, getIMRoomsMemberCountResp.getEntities().get(0).getCount().intValue());

		ModifyIMRoomReq modifyIMRoomReq = new ModifyIMRoomReq();
		modifyIMRoomReq.setAppkey(appkey);
		modifyIMRoomReq.setRoomId(roomId);
		modifyIMRoomReq.setRoomName("test modify");
		ModifyIMRoomResp modifyIMRoomResp = roomApiProxy.modifyIMRoom(modifyIMRoomReq);
	//	Assert.assertEquals(CodeDefs.SUCCESS, modifyIMRoomResp.getStatus());

		GetIMRoomReq getIMRoomReq = new GetIMRoomReq();
		getIMRoomReq.setAppkey(appkey);
		getIMRoomReq.setRoomId(roomId);
		GetIMRoomResp getIMRoomResp = roomApiProxy.getIMRoom(getIMRoomReq);
	//	Assert.assertEquals(CodeDefs.SUCCESS, getIMRoomResp.getStatus());
		Assert.assertEquals(modifyIMRoomReq.getRoomName(), getIMRoomResp.getEntity().getRoomName());

		GetIMRoomsReq getIMRoomsReq = new GetIMRoomsReq();
		getIMRoomsReq.setAppkey(appkey);
		getIMRoomsReq.setNames(new String[] { "test modify" });
		GetIMRoomsResp getIMRoomsResp = roomApiProxy.getIMRooms(getIMRoomsReq);
	//	Assert.assertEquals(CodeDefs.SUCCESS, getIMRoomsResp.getStatus());
		Assert.assertTrue(0 < getIMRoomsResp.getEntities().size());
	}

	@Test
	public void groupApiTest() throws IOException {

		// ******************************ModifyGroup
		ModifyGroupReq modifyGroupReq = new ModifyGroupReq();
		modifyGroupReq.setGroupId(groupId);
		modifyGroupReq.setGroupName("test_modify");
		modifyGroupReq.setGroupHead(head2);
		modifyGroupReq.setAppkey(appkey);
		ModifyGroupResp modifyGroupResp = groupApiProxy.modifyGroup(modifyGroupReq);
	//	Assert.assertEquals(CodeDefs.SUCCESS, modifyGroupResp.getStatus());
		Assert.assertEquals("test_modify", modifyGroupResp.getEntity().getGroupName());

		// ******************************GetGroupDetail
		GetGroupReq getGroupDetailReq = new GetGroupReq();
		getGroupDetailReq.setGroupId(groupId);
		getGroupDetailReq.setAppkey(appkey);

		GetGroupResp getGroupDetailResp = groupApiProxy.getGroup(getGroupDetailReq);
	//	Assert.assertEquals(CodeDefs.SUCCESS, getGroupDetailResp.getStatus());
		Assert.assertEquals("test_modify", getGroupDetailResp.getEntity().getGroupName());

		// ******************************AddGroupMember
		AddGroupMemberReq addGroupMemberReq = new AddGroupMemberReq();
		addGroupMemberReq.setGroupId(groupId);
		addGroupMemberReq.setUserAccount(account1);
		addGroupMemberReq.setAppkey(appkey);
		AddGroupMemberResp addGroupMemberResp = groupApiProxy.addGroupMember(addGroupMemberReq);
	//	Assert.assertEquals(CodeDefs.GROUP.USER_HAS_IN_GROUP, addGroupMemberResp.getStatus());

		// ******************************GetGroupUserList
		GetGroupMembersReq getGroupMembersReq = new GetGroupMembersReq();
		getGroupMembersReq.setGroupId(groupId);
		getGroupMembersReq.setAppkey(appkey);
		GetGroupMembersResp getGroupUserListResp = groupApiProxy.getGroupMembers(getGroupMembersReq);
	//	Assert.assertEquals(CodeDefs.SUCCESS, getGroupUserListResp.getStatus());

		// ******************************DelGroupMember
		DelGroupMemberReq delGroupMemberReq = new DelGroupMemberReq();
		delGroupMemberReq.setGroupId(groupId);
		delGroupMemberReq.setUserAccount(account2);
		delGroupMemberReq.setAppkey(appkey);
		DelGroupMemberResp delGroupMemberResp = groupApiProxy.delGroupMember(delGroupMemberReq);
	//	Assert.assertEquals(CodeDefs.SUCCESS, delGroupMemberResp.getStatus());

		getGroupMembersReq.setGroupId(groupId);
		getGroupUserListResp = groupApiProxy.getGroupMembers(getGroupMembersReq);
	//	Assert.assertEquals(CodeDefs.SUCCESS, getGroupUserListResp.getStatus());
		Assert.assertEquals(1, getGroupUserListResp.getEntities().size());

		// 删除创建者
		delGroupMemberReq = new DelGroupMemberReq();
		delGroupMemberReq.setGroupId(groupId);
		delGroupMemberReq.setUserAccount(account1);
		delGroupMemberReq.setAppkey(appkey);
		delGroupMemberResp = groupApiProxy.delGroupMember(delGroupMemberReq);
		Assert.assertEquals(CodeDefs.GROUP.DO_NOT_DEL_OWNER, delGroupMemberResp.getStatus());

		// ******************************ChangeGroupOwner
		ChangeGroupOwnerReq changeGroupOwnerReq = new ChangeGroupOwnerReq();
		changeGroupOwnerReq.setGroupId(groupId);
		changeGroupOwnerReq.setUserAccount(account1);
		changeGroupOwnerReq.setAppkey(appkey);
		ChangeGroupOwnerResp changeGroupOwnerResp = groupApiProxy.changeGroupOwner(changeGroupOwnerReq);
		Assert.assertEquals(CodeDefs.GROUP.IS_OWNER_ALREADY, changeGroupOwnerResp.getStatus());

		addGroupMemberReq.setUserAccount(account2);
		addGroupMemberResp = groupApiProxy.addGroupMember(addGroupMemberReq);
	//	Assert.assertEquals(CodeDefs.SUCCESS, addGroupMemberResp.getStatus());
		// 把群拥有者改为account2
		changeGroupOwnerReq.setUserAccount(account2);
		changeGroupOwnerResp = groupApiProxy.changeGroupOwner(changeGroupOwnerReq);
	//	Assert.assertEquals(CodeDefs.SUCCESS, changeGroupOwnerResp.getStatus());

		// 删除旧的创建者account1
		delGroupMemberReq = new DelGroupMemberReq();
		delGroupMemberReq.setGroupId(groupId);
		delGroupMemberReq.setUserAccount(account1);
		delGroupMemberReq.setAppkey(appkey);
		delGroupMemberResp = groupApiProxy.delGroupMember(delGroupMemberReq);
	//	Assert.assertEquals(CodeDefs.SUCCESS, delGroupMemberResp.getStatus());

	}

	@Test
	public void userApiTest() throws IOException {
		// 添加好友
		AddFriendReq addFriendReq = new AddFriendReq();
		addFriendReq.setAppkey(appkey);
		addFriendReq.setUserAccount(account1);
		addFriendReq.setFriendAccount(account2);
		AddFriendResp addFriendResp = userApiProxy.addFriend(addFriendReq);
	//	Assert.assertEquals(CodeDefs.SUCCESS, addFriendResp.getStatus());

		// 查询好友列表
		GetFriendsReq getFriendsReq = new GetFriendsReq();
		getFriendsReq.setAppkey(appkey);
		getFriendsReq.setUserAccount(account1);
		GetFriendsResp getFriendsResp = userApiProxy.getFriends(getFriendsReq);
	//	Assert.assertEquals(CodeDefs.SUCCESS, getFriendsResp.getStatus());
		Assert.assertTrue(getFriendsResp.getEntities().size() == 1);

		// 添加黑名单
		AddBlacklistReq addBlackReq = new AddBlacklistReq();
		addBlackReq.setAppkey(appkey);
		addBlackReq.setUserAccount(account1);
		addBlackReq.setFriendAccount(account2);
		AddBlacklistResp addBlackResp = userApiProxy.addBlacklist(addBlackReq);
	//	Assert.assertEquals(CodeDefs.SUCCESS, addBlackResp.getStatus());

		// 查询黑名单
		GetBlacklistsReq getBlacklistsReq = new GetBlacklistsReq();
		getBlacklistsReq.setAppkey(appkey);
		getBlacklistsReq.setUserAccount(account1);
		GetBlacklistsResp getBlacklistsResp = userApiProxy.getBlacklists(getBlacklistsReq);
	//	Assert.assertEquals(CodeDefs.SUCCESS, getBlacklistsResp.getStatus());
		Assert.assertTrue(getBlacklistsResp.getEntities().size() == 1);

		// 删除好友
		DelFriendReq delFriendReq = new DelFriendReq();
		delFriendReq.setAppkey(appkey);
		delFriendReq.setUserAccount(account1);
		delFriendReq.setFriendAccount(account2);

		DelFriendResp delFriendResp = userApiProxy.delFriend(delFriendReq);
	//	Assert.assertEquals(CodeDefs.SUCCESS, delFriendResp.getStatus());

		// 删除黑名单
		DelBlacklistReq delBlackReq = new DelBlacklistReq();
		delBlackReq.setAppkey(appkey);
		delBlackReq.setUserAccount(account1);
		delBlackReq.setFriendAccount(account2);
		DelBlacklistResp delBlackResp = userApiProxy.delBlacklist(delBlackReq);
	//	Assert.assertEquals(CodeDefs.SUCCESS, delBlackResp.getStatus());

		// 获取用户列表
		GetUsersReq getUsersReq = new GetUsersReq();
		getUsersReq.setAppkey(appkey);
		getUsersReq.setIndex(0);
		getUsersReq.setCount(10);

		GetUsersResp getUsersResp = userApiProxy.getUsers(getUsersReq);
	//	Assert.assertEquals(CodeDefs.SUCCESS, getUsersResp.getStatus());
		Assert.assertTrue(getUsersResp.getEntities().size() >= 2);
		// 全局禁言
		DisableSayReq disableSayReq = new DisableSayReq();
		disableSayReq.setAppkey(appkey);
		disableSayReq.setUserAccount(account1);
		disableSayReq.setScope((byte) 0);
		disableSayReq.setSecond(10000L);
		DisableSayResp disableSayResp = userApiProxy.disableSay(disableSayReq);
	//	Assert.assertEquals(CodeDefs.SUCCESS, disableSayResp.getStatus());

		// 获取全局禁言列表
		GetDisableSayUsersReq getDisableSayUsersReq = new GetDisableSayUsersReq();
		getDisableSayUsersReq.setAppkey(appkey);
		getDisableSayUsersReq.setScope((byte) 0);

		GetDisableSayUsersResp getDisableSayUsersResp = userApiProxy.getDisableSayUsers(getDisableSayUsersReq);
	//	Assert.assertEquals(CodeDefs.SUCCESS, getDisableSayUsersResp.getStatus());
		Assert.assertTrue(getDisableSayUsersResp.getEntities().size() > 0);

		// 全局取消禁言
		EnableSayReq enableSayReq = new EnableSayReq();
		enableSayReq.setScope((byte) 0);
		enableSayReq.setAppkey(appkey);
		enableSayReq.setUserAccount(account1);
		EnableSayResp cancelDisableSayResp = userApiProxy.enableSay(enableSayReq);
	//	Assert.assertEquals(CodeDefs.SUCCESS, cancelDisableSayResp.getStatus());

		// APP禁言
		disableSayReq.setScope((byte) 1);
		disableSayReq.setAppkey(appkey);
		disableSayReq.setUserAccount(account1);
		disableSayResp = userApiProxy.disableSay(disableSayReq);
	//	Assert.assertEquals(CodeDefs.SUCCESS, disableSayResp.getStatus());

		// 获取APP禁言列表
		getDisableSayUsersReq.setAppkey(appkey);
		getDisableSayUsersReq.setScope((byte) 1);

		getDisableSayUsersResp = userApiProxy.getDisableSayUsers(getDisableSayUsersReq);
	//	Assert.assertEquals(CodeDefs.SUCCESS, getDisableSayUsersResp.getStatus());

		// APP取消禁言
		enableSayReq.setScope((byte) 0);
		enableSayReq.setAppkey(appkey);
		disableSayReq.setUserAccount(account1);
		cancelDisableSayResp = userApiProxy.enableSay(enableSayReq);
	//	Assert.assertEquals(CodeDefs.SUCCESS, disableSayResp.getStatus());

		CreateIMRoomReq createIMRoomReq = new CreateIMRoomReq();
		createIMRoomReq.setRoomName("test room1");
		createIMRoomReq.setAppkey(appkey);
		createIMRoomReq.setMaxUserNumber(10);
		CreateIMRoomResp createIMRoomResp = roomApiProxy.createIMRoom(createIMRoomReq);
	//	Assert.assertEquals(CodeDefs.SUCCESS, createIMRoomResp.getStatus());
		Long roomId = createIMRoomResp.getEntity().getRoomId();

		// ROOM禁言
		disableSayReq.setScope((byte) 2);
		disableSayReq.setRoomId(roomId);
		disableSayResp = userApiProxy.disableSay(disableSayReq);
	//	Assert.assertEquals(CodeDefs.SUCCESS, disableSayResp.getStatus());

		// 获取ROOM禁言列表
		getDisableSayUsersReq.setAppkey(appkey);
		getDisableSayUsersReq.setScope((byte) 2);
		getDisableSayUsersReq.setRoomId(roomId);

		getDisableSayUsersResp = userApiProxy.getDisableSayUsers(getDisableSayUsersReq);
	//	Assert.assertEquals(CodeDefs.SUCCESS, getDisableSayUsersResp.getStatus());

		// ROOM取消禁言
		enableSayReq.setScope((byte) 0);
		enableSayReq.setRoomId(roomId);
		disableSayResp = userApiProxy.disableSay(disableSayReq);
	//	Assert.assertEquals(CodeDefs.SUCCESS, disableSayResp.getStatus());

		ModifyUserPwdReq modifyUserPwdReq = new ModifyUserPwdReq();
		modifyUserPwdReq.setAppkey(appkey);
		modifyUserPwdReq.setUserAccount(account1);
		modifyUserPwdReq.setUserPwd("123123");

		ModifyUserPwdResp modifyUserPwdResp = userApiProxy.modifyUserPwd(modifyUserPwdReq);
	//	Assert.assertEquals(CodeDefs.SUCCESS, modifyUserPwdResp.getStatus());

	}

}
