/**
 * 
 */
package com.ssic.game.common.service.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ssic.game.app.controller.ims.ImsCoreProcessorController;
import com.ssic.game.app.test.BaseTestCase;
import com.ssic.game.common.dto.FormsDto;
import com.ssic.game.common.service.IFormsService;
import com.ssic.game.ims.model.AchieveFormRequest;
import com.ssic.game.ims.model.TaskData;
import com.ssic.util.model.Response;

/**		
 * <p>Title: FormsServiceTest </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author rkzhang	
 * @date 2015年6月30日 下午4:23:16	
 * @version 1.0
 * <p>修改人：rkzhang</p>
 * <p>修改时间：2015年6月30日 下午4:23:16</p>
 * <p>修改备注：</p>
 */
public class FormsServiceTest extends BaseTestCase {

    protected static final Log logger = LogFactory.getLog(FormsServiceTest.class);
    
    @Autowired
    private ImsCoreProcessorController controller;
    
    @Test
    public void findByFormIdTest() {
        AchieveFormRequest request = new AchieveFormRequest();
        request.setUserId("3456f2ff-4a0b-4f4f-8c0a-11d9274d034c");
        request.setProcInstanceId("cd96de7d-fcd0-4812-8ccf-99bdd2a1ebb7");
        request.setProjectId("3d2a63f4-87c3-4e1a-8112-056ffa0af9d7");
        request.setTaskId("f79d05e3-145c-4bb8-aaa7-6bd4ea5a9923");
        Response<TaskData> data =  controller.achieveTask(request);
    }
}

