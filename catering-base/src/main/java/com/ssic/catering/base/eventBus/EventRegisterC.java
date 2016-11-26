package com.ssic.catering.base.eventBus;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.common.eventbus.AsyncEventBus;
import com.ssic.catering.base.dto.QjyCateringUserDto;
import com.ssic.game.common.dto.LTUserDto;
import com.ssic.game.common.service.impl.LiaoTianServiceImpl;

@Repository
public class EventRegisterC {

    public static final AsyncEventBus eventBus = new AsyncEventBus(Executors.newCachedThreadPool());
 
    @Autowired
    public  EventBusListenerC eventBusListener;

    public AsyncEventBus getQjyEvent()
    {
        eventBus.register(eventBusListener);
        return eventBus;
    }

    public static void main(String[] args) throws UnsupportedEncodingException
    {
        EventRegisterC er = new EventRegisterC();
        er.getQjyEvent().post(new LTUserDto());
        //er.getQjyEvent().post(new QjyCateringUserDto());
        System.err.println(" run now");

    }
}
