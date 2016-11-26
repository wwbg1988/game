/**
 * 
 */
package com.ssic.carteing;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ssic.game.app.service.ISensitiveWarningOperateService;
import com.ssic.game.app.test.BaseTestCase;
import com.ssic.game.common.dto.WorkSearchDto;

/**		
 * <p>Title: SensitiveWarningTest </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年8月12日 下午2:01:19	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年8月12日 下午2:01:19</p>
 * <p>修改备注：</p>
 */
public class SensitiveWarningTest extends BaseTestCase
{

    protected static final Log logger = LogFactory.getLog(SensitiveWarningTest.class);
    @Autowired
    private ISensitiveWarningOperateService sensitiveWarningOperateService;
   
    

    @Test
    public void SensitiveWarningTest()
    {
        String userId = "88a22a02-ddd4-4c54-93dd-d32e9ef72ad0";
        List<WorkSearchDto> list =
            sensitiveWarningOperateService.sensitiveWarningList(userId, null, 0, 20);

        logger.debug("-----------预警历史记录出现次数:--------" + list.size());
    }
}
