package com.ssic.game.manage.service;

import java.io.UnsupportedEncodingException;

import net.sf.json.JSONObject;

import com.ssic.game.manage.qinjia.QJConnectDto;

public interface IQinJiaService {

	JSONObject sendMessage(QJConnectDto qJConnectDto);
	
	JSONObject  addUser(QJConnectDto qJConnectDto);
	
	JSONObject  addFriend(QJConnectDto qJConnectDto);
	
	JSONObject deletFriend(QJConnectDto qJConnectDto);
	
	JSONObject  getHistory(QJConnectDto qJConnectDto);
	
	JSONObject uploadFile(QJConnectDto qJConnectDto);
	
	JSONObject createGroup(QJConnectDto qJConnectDto) throws UnsupportedEncodingException;
}
