package com.ssic.game.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ssic.game.common.dto.ImsUsersDto;
import com.ssic.game.common.dto.PageHelperDto;
import com.ssic.game.common.dto.QjyFriendDto;

public interface QjyFriendExMapper {

	  public List<QjyFriendDto> findQJYF(@Param("qjyfriend") QjyFriendDto qjyFriendDto);
	  
	  public void insertQJYF(@Param("qjyfriend") QjyFriendDto qjyFriendDto);
	  
	  public void updateQJY(@Param("qjyfriend") QjyFriendDto qjyFriendDto);
	  
	  public void deleteQJYF(@Param("qjyfriend") QjyFriendDto qjyFriendDto);
	  
	  public List<ImsUsersDto> findUser(@Param("user") ImsUsersDto imsUsersDto,@Param("page")PageHelperDto ph);
	  
	  public List<ImsUsersDto> findUserAll(@Param("user") ImsUsersDto imsUsersDto);
	  
	  public void deleteByUserid(@Param("qjyfriend") QjyFriendDto qjyFriendDto);
	  
	  public int findUserCount(@Param("user") ImsUsersDto imsUsersDto);
	
	  public List<ImsUsersDto> findCaterUser(@Param("user") ImsUsersDto imsUsersDto,@Param("page")PageHelperDto ph);
	  
	  public int findCaterUserCount(@Param("user") ImsUsersDto imsUsersDto);
	  
	  public List<QjyFriendDto> findUsers();
	  
	  public List<ImsUsersDto> findNotFriend(@Param("userId") String userId);
	  
	  public List<ImsUsersDto> findOtherOne(@Param("user") ImsUsersDto imsUsersDto);
}
