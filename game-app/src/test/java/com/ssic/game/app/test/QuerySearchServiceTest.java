/**
 * 
 */
package com.ssic.game.app.test;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ssic.game.common.dto.PageHelperDto;
import com.ssic.game.ims.model.FormDataQuerys;
import com.ssic.game.ims.service.IQuerySearchService;

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
public class QuerySearchServiceTest  extends BaseTestCase 
{
    protected static final Log logger = LogFactory.getLog(QuerySearchServiceTest.class);

    @Autowired
    private IQuerySearchService querySearchService;
    

    @Test
    public void findByIdTest()
    {
        FormDataQuerys query = new FormDataQuerys();
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("sprotName", "3");
        query.setFields(map);
        query.setQueryId("89aa98f2-e67a-4540-96fc-a9c6999de820");
        PageHelperDto ph = new PageHelperDto();
        ph.setRows(10);
        ph.setPage(0);
        querySearchService.findBy(query, ph);
    }
}
