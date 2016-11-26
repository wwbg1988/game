package com.ssic.game.im.service;

import java.util.Map;

import com.gotye.entity.resp.group.GetGroupsResp;
import com.ssic.game.im.dto.GetGroupsRespDto;
import com.ssic.game.im.dto.QjyImUserDto;

public interface IQjyImService {
	 public Map<String, String> sendMessage(QjyImUserDto qjyImUserDto );
	 
	 Map<String, Object> getGroups();
	 
	 Map<String, Object> findNewGroups(String qjAccount);
	 
	 Map<String, Object> getFriends(String qjAccount);
	 
	 GetGroupsRespDto findRadisGroup(String qjAccount);
	 
	 void deleteRadisGroup(String qjAccount);
	 
	 Map<String, Object> importUsers(QjyImUserDto qjyImUserDto);
	 
	 Map<String, Object> getGroupUserList(String groupId);
}
