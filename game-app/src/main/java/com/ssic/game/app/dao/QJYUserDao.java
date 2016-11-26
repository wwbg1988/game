package com.ssic.game.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssic.game.app.controller.dto.QJYSendFriDto;
import com.ssic.game.app.mapper.QJYUserExMapper;
import com.ssic.game.common.dto.QjyFriendDto;

@Repository
public class QJYUserDao {

	@Autowired
	private QJYUserExMapper qJYUserExMapper;

	
	
	public List<QJYSendFriDto> findFriends(QJYSendFriDto qJYSendFriDto) {
		return qJYUserExMapper.findFriends(qJYSendFriDto);
	}

}
