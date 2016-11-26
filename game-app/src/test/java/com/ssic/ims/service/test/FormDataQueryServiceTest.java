package com.ssic.ims.service.test;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;

import com.ssic.game.app.test.BaseTestCase;
import com.ssic.game.common.constant.FormTypeConstants;
import com.ssic.ims.documents.FormData;
import com.ssic.ims.dto.Condition;
import com.ssic.ims.dto.FormDataQuery;
import com.ssic.ims.dto.Opt;
import com.ssic.ims.service.IFormDataQueryService;
import com.ssic.util.UUIDGenerator;

public class FormDataQueryServiceTest extends BaseTestCase {

    @Autowired
    private IFormDataQueryService queryService;

    public void findByPageTest() {

	FormDataQuery query = new FormDataQuery();
	//表单数据所属流程, 项目, 表单等ID信息
//	query.setFormId("08e38992-1205-4b60-8363-dc07c09055ff");
//	query.setProcessInstanceId("1");
	
	//表单字段查询条件
	//query.addFieldsCondition(new Condition("name", Opt.gt, "张三"));
	
	//排序条件
	//query.addFieldsOrder("age", Direction.ASC);
	query.setFormStat(FormTypeConstants.FINISHED);
	query.addCondition(new Condition("formstat", Opt.eq, "123"));
	query.addOrder("formstat", Direction.DESC);
	//f003611588664cc4841d9d7e8b62f916
	//8490c84ae23f42c2bd5d4229e0afd3d1
	//8c3a5c77512644f68cf6c96491c446f1

	Page<FormData> page = queryService.findByPage(query, new PageRequest(
		0, 20));

	for (FormData data : page.getContent()) {
	    System.out.println(data);
	}

	long total = page.getTotalElements();
	System.out.println("total : " + total + " list size : "
		+ page.getContent().size());
    }
    

	@Test
	public void findByPageTest2() {
		
		FormDataQuery query = new FormDataQuery();
//		query.addCondition(new Condition("createTime", Opt.lt, new Date()));
//		query.addFieldsCondition(new Condition("age", Opt.gt, 23));
//		query.addOrder("createTime", Direction.DESC);
//		query.addFieldsOrder("age", Direction.ASC);
//		query.addFieldsCondition(new Condition("deptCode",Opt.,"/0101/"));

		
		Page<FormData> page = queryService.findByPage(query, new PageRequest(0,  20));
		
		for(FormData data : page.getContent()) {
			System.out.println(data);
		}
		
		long total = page.getTotalElements();
		System.out.println("total : " + total + " list size : " + page.getContent().size());
	}
}
