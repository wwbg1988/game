/**
 * 
 */
package com.ssic.game.ims.model;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

/**		
 * <p>Title: LoadCompletion </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年7月11日 上午11:23:34	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年7月11日 上午11:23:34</p>
 * <p>修改备注：</p>
 */
public class LoadCompletion
{
    //用户名
    @Getter
    @Setter
    private String userAccount;
    
    //项目编号
    @Getter
    @Setter
    private String projId;
    
    //项目名称
    @Getter
    @Setter
    private String projName;
    
    //流程编号
    @Getter
    @Setter
    private String procId;
    
    //流程姓名
    @Getter
    @Setter
    private String procName;
    //结点编号
    @Getter
    @Setter
    private String taskId;
    
    //结点名称
    @Getter
    @Setter
    private String taskName;
    
    //下一步接口的url地址
    @Getter
    @Setter
    private String actionUrl;
    
    @Getter
    @Setter
    private String formId;
    
    @Getter
    @Setter
    private String procInstanceId;
}

