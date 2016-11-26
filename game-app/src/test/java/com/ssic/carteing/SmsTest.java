/**
 * 
 */
package com.ssic.carteing;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.ssic.game.app.test.BaseTestCase;

/**		
 * <p>Title: SmsTest </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年9月11日 下午3:42:17	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年9月11日 下午3:42:17</p>
 * <p>修改备注：</p>
 */
public class SmsTest extends BaseTestCase
{
    protected static final Log logger = LogFactory.getLog(SmsTest.class);

    @Test
    public void smsTestTest()
    {
        String context="0031【天坊云智】";
        //SmsUtil smsUtil = new SmsUtil();
       // String message = smsUtil.sendSms("13023207852", context);
      //  logger.info("输出信息:------------" + message);
    }

}
