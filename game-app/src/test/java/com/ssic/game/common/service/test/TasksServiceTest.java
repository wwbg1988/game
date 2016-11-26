/**
 * 
 */
package com.ssic.game.common.service.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ssic.game.app.test.BaseTestCase;
import com.ssic.game.common.dto.TasksDto;
import com.ssic.game.common.service.ITasksService;

/**		
 * <p>Title: TasksServiceTest </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年7月2日 下午3:27:13	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年7月2日 下午3:27:13</p>
 * <p>修改备注：</p>
 */
public class TasksServiceTest extends BaseTestCase
{
    protected static final Log logger = LogFactory.getLog(TasksServiceTest.class);
    @Autowired
    ITasksService taskService;

    @Test
    public void findByLastCreateTimeTest()
    {
        String procId="61049bc8-4710-4174-9b26-0e71c7530d0b";
        TasksDto task = taskService.findByLastCreateTime(procId);
        logger.info(task);
    }
    
    @Test
    public void findByTaskIdTest()
    {
        TasksDto task = taskService.findByTaskId("0572781f-5baf-4566-af16-4dc51fb7170b");
        logger.info(task);
    }
}
