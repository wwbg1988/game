/**
 * 
 */
package com.ssic.game.common.service.test;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ssic.game.app.controller.ims.ImsController;
import com.ssic.game.app.test.BaseTestCase;
import com.ssic.game.common.dao.ProjectDao;
import com.ssic.game.common.service.DeptUserService;
import com.ssic.game.common.service.IDeptService;
import com.ssic.game.ims.model.LoadCompletionDto;
import com.ssic.util.model.Response;

/**		
 * <p>Title: LoadProcessServiceTest </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年7月11日 下午3:45:09	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年7月11日 下午3:45:09</p>
 * <p>修改备注：</p>
 */
public class FinishProcessServiceTest extends BaseTestCase
{

    protected static final Log logger = LogFactory.getLog(FinishProcessServiceTest.class);

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private DeptUserService deptUserService;

    @Autowired
    private IDeptService deptService;

    @Autowired
    private ImsController controller;

    @Test
    public void loadSubDeptFormDataTest()
    {
//        System.out.println(controller.);
    }
}
