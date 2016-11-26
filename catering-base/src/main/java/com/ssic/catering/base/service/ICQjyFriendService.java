package com.ssic.catering.base.service;

import java.util.List;

import com.ssic.game.common.dto.ImsUsersDto;
import com.ssic.game.common.dto.QjyFriendDto;
import com.ssic.game.common.pageModel.PageHelper;
import com.ssic.game.common.pageModel.Tree;

public interface ICQjyFriendService {

	void insertQjyFriend(QjyFriendDto qjyFriendDto);
	
	 public List<QjyFriendDto> findQJYF(QjyFriendDto qjyFriendDto);
		
     public void insertQJYF(QjyFriendDto qjyFriendDto);
     
     public void updateQJY(QjyFriendDto qjyFriendDto);
     
     public void deleteQJYF(QjyFriendDto qjyFriendDto);
     
     public List<ImsUsersDto> findUser(ImsUsersDto imsUsersDto, PageHelper ph);
     
     public List<ImsUsersDto>  findCaterUser(ImsUsersDto imsUsersDto, PageHelper ph);
     	  
     public int findUserCount(ImsUsersDto imsUsersDto);
     
     public int findCaterUserCount(ImsUsersDto imsUsersDto);
     
     public String findUserPers(String ids);
     
     public List<Tree> userTree(String searchName,String userPerss);
     
     public void grantUser(String UserPerss,String resourceIds);
}
