package com.ssic.catering.base.service;

import java.util.Map;

import net.sf.json.JSONObject;

import com.ssic.catering.base.dto.QjyCateringUserDto;


public interface IQjyCateringService {

	 public Map<String, String> sendMessage(QjyCateringUserDto  qjyCateringUserDto);
	 
		JSONObject  addFriend(QjyCateringUserDto qjyCateringUserDto);
		
		JSONObject deletFriend(QjyCateringUserDto qjyCateringUserDto);
		
	 	Map<String,Object> createGroup(QjyCateringUserDto qjyCateringUserDto);
	 	
	 	Map<String, Object> modifyGroup(QjyCateringUserDto qjyCateringUserDto);
	 	
	 	Map<String, Object> getGroupUserList(QjyCateringUserDto qjyCateringUserDto);
	 	
	 	Map<String, Object> addGroupMember(QjyCateringUserDto qjyCateringUserDto);
	 	
	 	Map<String, Object> delGroupMember(QjyCateringUserDto qjyCateringUserDto);
	
	 	Map<String, Object> getGroups(QjyCateringUserDto qjyCateringUserDto);
	 	
	 	Map<String, Object> findNewGroups(QjyCateringUserDto qjyCateringUserDto);
	 	
	 	Map<String, Object> createGroupNew(QjyCateringUserDto qjyCateringUserDto);
	 	
	    Map<String, Object> dismissGroup(QjyCateringUserDto qjyCateringUserDto);
	    
	    Map<String, Object> delGroupMemberNew(QjyCateringUserDto qjyCateringUserDto);
	    
	    Map<String, Object> addGroupMemberNew(QjyCateringUserDto qjyCateringUserDto);
}
