/**
 * 
 */
package com.ssic.game.common.service.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ssic.game.app.test.BaseTestCase;
import com.ssic.game.common.dto.ProcInstantsDto;
import com.ssic.game.common.service.IProcInstanceService;

/**		
 * <p>Title: ProcInstanceServiceTest </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年7月2日 下午1:24:43	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年7月2日 下午1:24:43</p>
 * <p>修改备注：</p>
 */
public class ProcInstanceServiceTest  extends BaseTestCase 
{

    @Autowired
    private IProcInstanceService procInstanceService;

    protected static final Log logger = LogFactory.getLog(ProcInstanceServiceTest.class);
    
    @Test
    public void updateInstanceIdTest() {
	  ProcInstantsDto instance = procInstanceService.findByInstanceId("1");
	  logger.info(instance);
	  procInstanceService.updateProcInstance(instance);
    }

  
    public void findByInstanceIdTest()
    {
        ProcInstantsDto instance = procInstanceService.findByInstanceId("1");
        logger.info(instance);
    }
    

   
    public void findByProcIdTest()
    {
        ProcInstantsDto instance = procInstanceService.findByProcId("cd96de7d-fcd0-4812-8ccf-99bdd2a1ebb7");
        logger.info(instance);
    }
}
