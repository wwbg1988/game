/**
 * 
 */
package com.ssic.game.common.service.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ssic.game.app.test.BaseTestCase;
import com.ssic.game.common.dto.ActionsDto;
import com.ssic.game.common.service.IActionService;

/**		
 * <p>Title: ActionServiceTest </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年7月2日 下午1:47:31	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年7月2日 下午1:47:31</p>
 * <p>修改备注：</p>
 */
public class ActionServiceTest  extends BaseTestCase 
{
    protected static final Log logger = LogFactory.getLog(ActionServiceTest.class);

    @Autowired
    private IActionService actionService;

    @Test
    public void findByIdTest()
    {
        ActionsDto action = actionService.findById("52b3e0b0-43bc-49d7-ae85-61ddeb7dedd9");
        logger.info(action);
    }
}
