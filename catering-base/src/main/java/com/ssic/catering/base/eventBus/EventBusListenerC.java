package com.ssic.catering.base.eventBus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.eventbus.Subscribe;
import com.ssic.catering.base.dto.QjyCateringUserDto;
import com.ssic.catering.base.service.IQjyCateringService;
import com.ssic.game.common.dto.LTUserDto;
import com.ssic.game.common.service.ILiaoTianService;


@Service
public class EventBusListenerC {


	@Autowired
	private IQjyCateringService qjyCateringService;
	@Autowired
	private ILiaoTianService liaoTianService;
	
    @Subscribe
    public void onEvent(QjyCateringUserDto event) 
    {
    	qjyCateringService.sendMessage(event);
    }
    @Subscribe
    public void onEventLiaoT(LTUserDto event){
    	liaoTianService.sendMessage(event);
    }
    
    
	
}
