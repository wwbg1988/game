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
public class LoadProcessServiceTest extends BaseTestCase
{

    protected static final Log logger = LogFactory.getLog(LoadProcessServiceTest.class);

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
        System.out.println(controller.loadAgency("a8d811c7-72de-43f3-a620-521e66514177", "9a53fc57-9af3-43f3-b7d5-32ed9db1402d","d19be01a-6e7a-41c0-adeb-09239a677db8",null));
    }
}
