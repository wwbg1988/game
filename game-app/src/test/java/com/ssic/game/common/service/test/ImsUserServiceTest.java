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
import com.ssic.game.common.dto.ImsUsersDto;
import com.ssic.game.common.service.IImsUserService;


/**		
 * <p>Title: ImsUserServiceTest </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author rkzhang	
 * @date 2015年6月30日 下午2:17:32	
 * @version 1.0
 * <p>修改人：rkzhang</p>
 * <p>修改时间：2015年6月30日 下午2:17:32</p>
 * <p>修改备注：</p>
 */

public class ImsUserServiceTest extends BaseTestCase {
    
    protected static final Log logger = LogFactory.getLog(ImsCoreProcessorController.class);
    
    @Autowired
    private IImsUserService imsUserService;
    
    @Test
    public void findUserTest(){
	ImsUsersDto user = imsUserService.findByUserId("0");
	logger.info("user : " + user);
    }

}

