/**
 * 
 */
package com.ssic.game.ims.eventBus;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;







import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.common.eventbus.AsyncEventBus;
import com.ssic.game.im.dto.QjyImUserDto;

/**		
 * <p>Title: EventRegister </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Vincent	
 * @date 2015年7月24日 下午4:04:14	
 * @version 1.0
 * <p>修改人：Vincent</p>
 * <p>修改时间：2015年7月24日 下午4:04:14</p>
 * <p>修改备注：</p>
 */
@Repository
public class EventRegister
{
    public static final AsyncEventBus eventBus = new AsyncEventBus(Executors.newCachedThreadPool());
 
    @Autowired
    public  EventBusListener eventBusListener;

    public AsyncEventBus getQjyEvent()
    {
        eventBus.register(eventBusListener);
        return eventBus;
    }

    public static void main(String[] args) throws UnsupportedEncodingException
    {
        EventRegister er = new EventRegister();
 
        er.getQjyEvent().post(new QjyImUserDto());
        System.err.println(" run now");

    }

}
