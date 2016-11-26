package com.ssic.game.common.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;



import com.ssic.game.common.dto.ImsUsersDto;
import com.ssic.game.common.dto.PageHelperDto;
import com.ssic.game.common.dto.QjyFriendDto;
import com.ssic.game.common.mapper.QjyFriendExMapper;

@Repository
public class QjyFriendDao {

	@Autowired
	private QjyFriendExMapper  qjyFriendExMapper;
	
	  public List<QjyFriendDto> findQJYF(QjyFriendDto qjyFriendDto){
		  
		  return qjyFriendExMapper.findQJYF(qjyFriendDto);
	  }
	  
	  public void insertQJYF(QjyFriendDto qjyFriendDto){
		  qjyFriendExMapper.insertQJYF(qjyFriendDto);
	  }
	  
	  public void updateQJY(QjyFriendDto qjyFriendDto){
		  qjyFriendExMapper.updateQJY(qjyFriendDto);
	  }
	  
	  public void deleteQJYF(QjyFriendDto qjyFriendDto){
		  qjyFriendExMapper.deleteQJYF(qjyFriendDto);
	  }
	  
	  public List<ImsUsersDto> findUser(ImsUsersDto imsUsersDto,PageHelperDto ph){
		  int beginRow = (ph.getPage()-1)*ph.getRows();
		  ph.setBeginRow(beginRow);
		  return  qjyFriendExMapper.findUser(imsUsersDto,ph);
	  }
	  
	  public int findUserCount(ImsUsersDto imsUsersDto){
		  return qjyFriendExMapper.findUserCount(imsUsersDto);
	  }
	
	  
	  public List<ImsUsersDto> findUser(ImsUsersDto imsUsersDto){
		
			return  qjyFriendExMapper.findUserAll(imsUsersDto);
		  }
	  
	  public void deleteByUserid(QjyFriendDto qjyFriendDto){
		  qjyFriendExMapper.deleteByUserid(qjyFriendDto);
	  }
	  
	  public List<ImsUsersDto>  findCaterUser(ImsUsersDto imsUsersDto,PageHelperDto ph){
		  int beginRow = (ph.getPage()-1)*ph.getRows();
		  ph.setBeginRow(beginRow);
		  return  qjyFriendExMapper.findCaterUser(imsUsersDto,ph);
	  }
	  
	  public int findCaterUserCount(ImsUsersDto imsUsersDto){
		  return qjyFriendExMapper.findCaterUserCount(imsUsersDto);
	  }
	  
	  public List<QjyFriendDto> findUsers(){
		  return qjyFriendExMapper.findUsers();
	  }
	  
	  public List<ImsUsersDto> findNotFriend(String userId){
		  return qjyFriendExMapper.findNotFriend(userId);
	  }
	  
	  public List<ImsUsersDto> findOtherOne(ImsUsersDto imsUsersDto){
		  return qjyFriendExMapper.findOtherOne(imsUsersDto);
	  }
	  
}
