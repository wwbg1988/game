package com.ssic.game.app.service.impl;


import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssic.game.app.controller.dto.QJYSendFriDto;
import com.ssic.game.app.dao.QJYUserDao;
import com.ssic.game.app.listener.InitApplicationContext;
import com.ssic.game.app.service.IQJYUserService;


@Service
public class QJYUserServiceImpl implements IQJYUserService{
    
	@Autowired
	private QJYUserDao qJYUserDao;
	
	 protected static final Log logger = LogFactory.getLog(InitApplicationContext.class);
	
	@Override
	public List<QJYSendFriDto> findFriends(QJYSendFriDto qJYSendFriDto) {
		
		return qJYUserDao.findFriends(qJYSendFriDto);
	}

	
}
