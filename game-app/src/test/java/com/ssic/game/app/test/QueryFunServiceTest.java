/**
 * 
 */
package com.ssic.game.app.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ssic.game.app.test.BaseTestCase;
import com.ssic.game.common.dto.ActionsDto;
import com.ssic.game.common.dto.query.QueryPage;
import com.ssic.game.common.service.IActionService;
import com.ssic.game.ims.service.IQueryAchieveService;
import com.ssic.game.ims.service.IQuerySearchService;
import com.ssic.util.model.Response;

/**		
 * <p>Title: ActionServiceTest </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author milkteaye	
 * @date 2015年7月2日 下午1:47:31	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年7月2日 下午1:47:31</p>
 * <p>修改备注：</p>
 */
public class QueryFunServiceTest  extends BaseTestCase 
{
    protected static final Log logger = LogFactory.getLog(QueryFunServiceTest.class);
    
    @Autowired
    private IQueryAchieveService queryAchService;

    @Test
    public void findByIdTest()
    {
        Response<QueryPage> result = queryAchService.findQuery("89aa98f2-e67a-4540-96fc-a9c6999de820");
        System.out.println(result.getData());
//        System.out.println(queryAchService.findQuery("1"));
    }
}
