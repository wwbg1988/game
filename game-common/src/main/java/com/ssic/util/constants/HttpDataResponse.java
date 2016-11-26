/**
 * 
 */
package com.ssic.util.constants;

/**		
 * <p>Title: HttpDataResponse </p>
 * <p>Description: 定义HTTP对外接口常量</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Vincent	
 * @date 2015年6月24日 下午5:00:20	
 * @version 1.0
 */
public interface HttpDataResponse extends DataStatus
{

    /**   
    * NAME_IS_NULL: 登录用户帐号为空      
    */
    static final int NAME_IS_NULL = 10001;

    /**   
    * PWD_IS_NULL: 登录用户密码为空     
    */
    static final int PWD_IS_NULL = 10002;

    /**   
    * NOT_FOUND_USER: 用户不存在      
    */
    static final int NOT_FOUND_USER = 10003;

    /**   
    * SYS_EXCEPTION: 系统异常请稍候再试
    */
    static final int SYS_EXCEPTION = 99999;
}
