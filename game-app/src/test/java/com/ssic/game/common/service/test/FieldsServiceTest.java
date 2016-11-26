/**
 * 
 */
package com.ssic.game.common.service.test;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ssic.game.app.test.BaseTestCase;
import com.ssic.game.common.dto.FieldsDto;
import com.ssic.game.common.service.IFieldsService;

/**		
 * <p>Title: FieldsServiceTest </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author rkzhang	
 * @date 2015年6月30日 下午5:31:05	
 * @version 1.0
 * <p>修改人：rkzhang</p>
 * <p>修改时间：2015年6月30日 下午5:31:05</p>
 * <p>修改备注：</p>
 */
public class FieldsServiceTest extends BaseTestCase {
    
    protected static final Log logger = LogFactory.getLog(FormsServiceTest.class);

    @Autowired
    private IFieldsService fieldsService;
    
    @Test
    public void findByFormIdTest() {
	List<FieldsDto> fields = fieldsService.findByFormId("08e38992-1205-4b60-8363-dc07c09055ff");
	logger.info(fields);
    }
}

