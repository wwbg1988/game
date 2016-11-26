/**
 * 
 */
package com.ssic.game.ims.model;

import java.util.Date;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

/**		
 * <p>Title: NewLoadTask </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年8月6日 下午2:17:28	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年8月6日 下午2:17:28</p>
 * <p>修改备注：</p>
 */
public class NewLoadTask
{
    //创建时间
    @Getter
    @Setter
    private Date createTime;
    
    //task名称
    @Getter
    @Setter
    private String taskName;
    
    //是否是提交表单结点 1.不是 2.是
    @Getter
    @Setter
    private int isApproval;
    
    @Getter
    @Setter
    private String approveName;
    
    //1不通过 2通过 3 未审批
    @Getter
    @Setter
    private String stat;
    
//    //字段
//    @Getter
//    @Setter
//    private Map<String,Object> fields;
    
}

