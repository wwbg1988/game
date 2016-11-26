/**
 * 
 */
package com.ssic.game.sync.cache.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import com.ssic.game.sync.test.BaseTestCase;

/**		
 * <p>Title: AccountServiceTest </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author rkzhang	
 * @date 2015年6月29日 下午5:01:40	
 * @version 1.0
 * <p>修改人：rkzhang</p>
 * <p>修改时间：2015年6月29日 下午5:01:40</p>
 * <p>修改备注：</p>
 */
public class AccountServiceTest extends BaseTestCase {

    @Autowired
    private AccountService accountService;
    
    @Autowired
    private RedisTemplate<String, Account> redisTemplate;
    
    @Test
    public void testService() {
	accountService.getAccountByName("testName1");
	Account obj = redisTemplate.boundValueOps("com.app.test.account:testName1").get();
	System.out.println(obj);
	accountService.updateAccount(obj);
    }
}

