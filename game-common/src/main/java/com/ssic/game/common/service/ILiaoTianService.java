package com.ssic.game.common.service;

import com.ssic.game.common.dto.LTUserDto;

public interface ILiaoTianService {

	
	   String addLTUser(LTUserDto lTUserDto);
	   
	   String delLTUser(LTUserDto lTUserDto);
	   
	   String EditLTPassWord(LTUserDto lTUserDto);
	   
	   String findIsExistByAccount(LTUserDto lTUserDto);
	   
	   String findIsExistByAccountPassword(LTUserDto lTUserDto);
	   
	   String sendMessage(LTUserDto lTUserDto);
}
