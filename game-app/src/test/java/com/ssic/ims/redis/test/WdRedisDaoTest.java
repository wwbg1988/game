package com.ssic.ims.redis.test;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ssic.base.redis.WdRedisDao;
import com.ssic.game.app.test.BaseTestCase;

public class WdRedisDaoTest extends BaseTestCase {

	@Autowired
	private WdRedisDao<TestDto> redisDao;
	
	@Test
	public void testRedisDao(){
	    	for(int i = 1; i <=100 ; i++) {
        		TestDto dto = new TestDto();
        		dto.setPassword("password");
        		dto.setUserName("username");
        		dto.setAge(i);
        		redisDao.setToList(dto);
	    	}
	    	TestDto query = new TestDto();
	    	query.setPassword("password");
	    	query.setUserName("username");
		List<TestDto> g = redisDao.getList(query, TestDto.class);
		System.out.println(g);
	}
}
