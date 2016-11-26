/**
 * 
 */
package com.ssic.game.common.util;

import com.ssic.util.constants.DataStatus;

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
    * USER_INFO_SSESSION: 用户SESSION信息名称    
    */
    static final String USER_INFO_SSESSION = "USER_INFO_SSESSION_";

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
     * NOT_FOUND_USER: 用户未登录   
     */
    static final int USER_NOT_ONLINE = 10004;

    /**   
     * USER_NOT_PROJECT: 用户没有项目授权
     */
    static final int USER_NOT_PROJECT = 10005;

    /**   
    * SYS_EXCEPTION: 系统异常请稍候再试
    */
    static final int SYS_EXCEPTION = 99999;
    /**   
    * SYS_EXCEPTION: 亲加云用户信息不存在
    */
    static final int QJYUserDto_NOT_FOUND = 10010;
    /**   
     * SYS_EXCEPTION: 亲加云发送信息失败
     */
    static final int SENDMESSAGE_FAIL = 10011;

    /**   
     * SYS_EXCEPTION: 亲加云发送信息成功
     */
    static final int SENDMESSAGE_SUCCESS = 10012;
    /**   
     * PROJECT_IS_NULL: 项目为空
     */
    static final String PROJECT_IS_NULL = "项目为空";
    /**   
     * FORM_IS_NULL: 表单为空      
     */
    static final String FORM_IS_NULL = "表单为空";
    /**   
     * NOT_FOUND_PROCINSTANCE: 流程实例为空     
     */
    static final String PROCINSTANCE_IS_NULL = "流程实例为空 ";

    /**   
     * PROCINCE_IS_NULL: 流程为空 
     */
    static final String PROCINCE_IS_NULL = "流程为空";

    /**   
     * User_IS_NULL: 用户为空     
     */
    static final String User_IS_NULL = "用户为空";

    /**   
     * Fields_IS_NULL: 字段集合为空     
     */
    static final String Fields_IS_NULL = "字段集合为空 ";

    /**   
    * PARAM_VALUE_IS_NULL: 参数值为空     
    */
    static final String PARAM_VALUE_IS_NULL = "参数值为空";

    /**   
     * FORM_VALID_PASS: 表单验证通过  
     */
    static final String FORM_VALID_PASS = "表单验证通过 ";

    /**   
     * NOT_SUBMIT_MESSAGE: 还没有点提交按钮，不能提交到下一个任务节点
     */
    static final String NOT_SUBMIT_MESSAGE = "还没有点提交按钮，不能提交到下一个任务节点";

    /**   
     * FORM_SUBMIT_FALID: 表单提交失败
     */
    static final String FORM_SUBMIT_FALID = "表单提交失败!";

    /**   
     * FORM_SUBMIT_SUCCESS: 表单提交成功
     */
    static final String FORM_SUBMIT_SUCCESS = "表单提交成功!";

    /**   
     * FORM_SAVE_SUCCESS: 表单保存成功
     */
    static final String FORM_SAVE_SUCCESS = "表单保存成功!";

    /**   
     * PROCINSTANCE_INFO_EXCEPTION: 流程信息异常,action数据错误!
     */
    static final String PROCINSTANCE_INFO_EXCEPTION = "流程信息异常,action数据错误!";

    /**   
     * RETURN_FORM_SUCCESS:  返回表单数据成功
     */
    static final String RETURN_FORM_SUCCESS = " 返回表单数据成功!";

    /**   
     * FORM_DATA_NOT_FOUND: 暂无表单数据
     */
    static final String FORM_DATA_NOT_FOUND = "暂无表单数据!";

    /**   
     * REVERT_FORM_SUCCESS:  回退表单成功
     */
    static final String REVERT_FORM_SUCCESS = " 回退表单成功!";

    /**   
     * REVERT_FORM:  回退表单
     */
    static final String REVERT_FORM = " 回退表单:[";

    /**   
     * SUCCESS:  成功!
     */
    static final String SUCCESS = "]成功";
    
    /**   
     * NOT_FOR_BUSSINESS:  成功!
     */
    static final String NOT_FOR_BUSSINESS="还有未办理的事项，请尽快办理."; 
    
    /**   
     * AUDIT_FAILURE: 审核失败
     */
    static final String AUDIT_FAILURE = "拒绝成功!";
    /**
     * 查询会议记录权限成功
     * */
    static final String CHECK_MEETING_SUCCESS = "200";
    /**
     * 查询会议记录权限失败
     * */
    static final String CHECK_MEETING_FALID = "210";
}
