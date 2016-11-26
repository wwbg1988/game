/**
 * 
 */
package com.ssic.carteing;

import com.ssic.game.app.controller.weixin.WechatController;
import com.ssic.game.app.test.BaseTestCase;
import com.ssic.shop.manage.service.IWeixnUserCafetoriumService;
import com.ssic.util.model.Response;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**		
 * <p>Title: WeixnUserCafetoriumTest </p>
 * <p>Description: 微信用户关注食堂测试类</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 刘博	
 * @date 2015年10月27日 下午4:30:50	
 * @version 1.0
 * <p>修改人:刘博</p>
 * <p>修改时间：2015年10月27日 下午4:30:50</p>
 * <p>修改备注：</p>
 */
public class WeixnUserCafetoriumTest extends BaseTestCase
{
    protected static final Log logger = LogFactory.getLog(WeixnUserCafetoriumTest.class);
    @Autowired
    private IWeixnUserCafetoriumService weixnUserCafetoriumService;
    @Autowired
    private WechatController wechatController;

    @Test
    public void followCafetorium()
    {
        Response<String> resString = wechatController.followCafetorium("333", "03");
        logger.info(resString.getMessage() + "----------------" + resString.getStatus());

    }
}
