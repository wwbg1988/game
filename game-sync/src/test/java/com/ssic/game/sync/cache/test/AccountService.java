/**
 * 
 */
package com.ssic.game.sync.cache.test;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

/**		
 * <p>Title: AccountService </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author rkzhang	
 * @date 2015年6月29日 下午4:51:21	
 * @version 1.0
 * <p>修改人：rkzhang</p>
 * <p>修改时间：2015年6月29日 下午4:51:21</p>
 * <p>修改备注：</p>
 */
@Service
public class AccountService { 
    
    @Cacheable(value="default", key="'com.app.test.account:' + #userName")// 使用了一个缓存名叫 accountCache 
    public Account getAccountByName(String userName) { 

      return getFromDB(userName); 
    } 
    
    //@CacheEvict(value="default",key="#account.getName()")// 清空 accountCache 缓存  
    
    @Caching(evict = {@CacheEvict(value = "default", key="#account.getName()"),
	    @CacheEvict(value = "default", key="'aaa:' + #account.getName()") }) 
    public void updateAccount(Account account) {
      updateDB(account); 
    } 
   
    @CacheEvict(value="default",allEntries=true)// 清空 accountCache 缓存
    public void reload() { 
    } 
   
    private Account getFromDB(String acctName) { 
      System.out.println("real querying db..."+acctName); 
      return new Account(acctName); 
    } 
   
    private void updateDB(Account account) { 
      System.out.println("real update db..."+account.getName()); 
    } 
   
  }