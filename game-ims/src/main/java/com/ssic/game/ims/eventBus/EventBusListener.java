/**
 * 
 */
package com.ssic.game.ims.eventBus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.google.common.eventbus.Subscribe;
import com.ssic.game.im.dto.QjyImUserDto;
import com.ssic.game.im.service.IQjyImService;

/**		
 * <p>Title: QjyEventBusTest </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Vincent	
 * @date 2015年7月24日 下午3:20:36	
 * @version 1.0
 * <p>修改人：Vincent</p>
 * <p>修改时间：2015年7月24日 下午3:20:36</p>
 * <p>修改备注：</p>
 */

@Service
public class EventBusListener
{
 
	@Autowired
	private  IQjyImService iQjyImService;
	
    @Subscribe
    public void onEvent(QjyImUserDto event) 
    {
    	iQjyImService.sendMessage(event);
    }

}
