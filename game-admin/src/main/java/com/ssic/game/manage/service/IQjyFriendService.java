package com.ssic.game.manage.service;

import java.util.List;

import com.ssic.game.admin.pageModel.PageHelper;
import com.ssic.game.admin.pageModel.Tree;
import com.ssic.game.common.dto.ImsUsersDto;
import com.ssic.game.common.dto.QjyFriendDto;
import com.ssic.game.common.pojo.ImsUsers;

public interface IQjyFriendService {

	
	     public List<QjyFriendDto> findQJYF(QjyFriendDto qjyFriendDto);
	
	     public void insertQJYF(QjyFriendDto qjyFriendDto);
	     
	     public void updateQJY(QjyFriendDto qjyFriendDto);
	     
	     public void deleteQJYF(QjyFriendDto qjyFriendDto);
	     
	     public List<ImsUsersDto> findUser(ImsUsersDto imsUsersDto, PageHelper ph);
	     	  
	     public int findUserCount(ImsUsersDto imsUsersDto);
	     
	     public String findUserPers(String ids);
	     
	     public List<Tree> userTree(String searchName,String userPerss);
	     
	     public void grantUser(String UserPerss,String resourceIds);
	     
	     public List<QjyFriendDto> findUsers();
	     
	     public List<ImsUsersDto> findNotFriend(String userId);
	     
	     public void addOtherFriend(QjyFriendDto qjyFriendDto);
	     
	     public List<ImsUsersDto> findOtherOne(ImsUsersDto imsUsersDto);
}
