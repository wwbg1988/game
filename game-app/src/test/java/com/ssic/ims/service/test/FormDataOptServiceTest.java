package com.ssic.ims.service.test;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.ssic.game.app.test.BaseTestCase;
import com.ssic.ims.documents.FormData;
import com.ssic.ims.service.IFormDataOptService;
import com.ssic.util.DateUtils;
import com.ssic.util.UUIDGenerator;

@Transactional
public class FormDataOptServiceTest extends BaseTestCase {

	@Autowired
	private IFormDataOptService formDataOptService;
	
	@Test
	public void optTest() {
		String id = UUIDGenerator.getUUID32Bit();
		FormData formData = new FormData();
		formData.setId(id);
		formData.setProjectId(UUIDGenerator.getUUID32Bit());
		formData.setProcessId(UUIDGenerator.getUUID32Bit());
		formData.setTaskId(UUIDGenerator.getUUID32Bit());
		formData.setFormId(UUIDGenerator.getUUID32Bit());
		formData.setPrevTaskId(UUIDGenerator.getUUID32Bit());
		formData.setType(3);
		
		int age = (int)(30 * Math.random());
		formData.setFieldValue("name", "Tim");
		formData.setFieldValue("age", age);
		formData.setFieldValue("birthday", DateUtils.addMonths(new Date(), 0 - age));
		formData.setFieldValue("deptCode", "IT");
		formData.setFieldValue("homeAddress", "淮海路888号");
		formData.setFieldValue("aaa", 11);
		formData.setFieldValue("isTrue", false);
		formDataOptService.save(formData);
		
		formData = formDataOptService.findById(id);
		System.out.println(formData);
		formData.setType(3);
		formData.setFieldValue("name", "Mark");
		formData.setFieldValue("deptCode", "COO");
		formDataOptService.updateById(formData);
		
		formData = formDataOptService.findById(id);
		System.out.println(formData);
	}
}
