/**
 * 
 */
package com.ssic.ims.service.test;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.util.CollectionUtils;

import com.ssic.game.app.test.BaseTestCase;
import com.ssic.game.common.constant.ActionTypeConstants;
import com.ssic.ims.documents.ActionRecord;
import com.ssic.ims.dto.ActionRecordQuery;
import com.ssic.ims.dto.Condition;
import com.ssic.ims.dto.Opt;
import com.ssic.ims.service.IActionRecordService;
import com.ssic.util.UUIDGenerator;

/**		
 * <p>Title: ActionRecordServiceTest </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author rkzhang	
 * @date 2015年7月9日 下午5:19:57	
 * @version 1.0
 * <p>修改人：rkzhang</p>
 * <p>修改时间：2015年7月9日 下午5:19:57</p>
 * <p>修改备注：</p>
 */
public class ActionRecordServiceTest extends BaseTestCase {

    @Autowired
    private IActionRecordService recordService;
    
    public void optTest() {
	
	for(int i = 0; i < 100; i++) {
	    ActionRecord record = new ActionRecord();
	    record.setActionId(UUIDGenerator.getUUID());
	    record.setActionType(1);
	    record.setCreateTime(new Date());
	    record.setDeptCode("00-03-01");
	    record.setDeptId(UUIDGenerator.getUUID());
	    record.setFormId(UUIDGenerator.getUUID());
	    record.setId(UUIDGenerator.getUUID());
	    record.setLastUpdateTime(new Date());
	    record.setPrevTaskId(UUIDGenerator.getUUID());
	    record.setProcessId(UUIDGenerator.getUUID());
	    record.setProcessInstanceId(UUIDGenerator.getUUID());
	    record.setProjectId(UUIDGenerator.getUUID());
	    record.setStat(1);
	    record.setTaskId(UUIDGenerator.getUUID());
	    record.setType(1);
	    record.setUserAccount("aaa" + i);
	    record.setUserId(UUIDGenerator.getUUID());
	    record.setUserName("张三");
	    recordService.save(record);
	    System.out.println(record);
	}
    }
    
    @Test
    public void findTest() {
	ActionRecordQuery query = new ActionRecordQuery();
//	query.setDeptCode("^00-03.*");
//	query.addCondition(new Condition("createTime", Opt.lt, new Date()));
//	query.addOrder("createTime", Direction.DESC);

    query.setActionType(ActionTypeConstants.ACTION_TTPE_PASS);
  query.addCondition(new Condition("countersign", Opt.gte, 1));
  query.addOrder("createTime", Direction.DESC);
	Page<ActionRecord> page = recordService.findByPage(query, new PageRequest(0,  20));
	
	List<ActionRecord> records = page.getContent();
	if(CollectionUtils.isEmpty(records)) {
	    return;
	}
	
	for(ActionRecord record : records) {
	    System.out.println(record);
	}
	
    }
}

