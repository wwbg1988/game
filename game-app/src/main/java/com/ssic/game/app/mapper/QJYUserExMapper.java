package com.ssic.game.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ssic.game.app.controller.dto.QJYSendFriDto;
import com.ssic.game.common.dto.QjyFriendDto;

public interface QJYUserExMapper {

	  public List<QJYSendFriDto> findFriends(@Param("qjyFriend")QJYSendFriDto qJYSendFriDto);
	
}
