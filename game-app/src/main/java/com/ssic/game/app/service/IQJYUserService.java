package com.ssic.game.app.service;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.ssic.game.app.controller.dto.QJYSendFriDto;
import com.ssic.game.common.dto.QjyFriendDto;

public interface IQJYUserService {

	  public List<QJYSendFriDto> findFriends(QJYSendFriDto qJYSendFriDto);
	  
}
