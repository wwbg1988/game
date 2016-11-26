/**
 * 
 */
package com.ssic.game.ims.model;

import lombok.Getter;
import lombok.Setter;

/**		
 * <p>Title: ActionRecord </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年8月6日 下午1:53:28	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年8月6日 下午1:53:28</p>
 * <p>修改备注：</p>
 */
public class ActionRecordModel
{
    @Getter
    @Setter
    private String userId;
    
    @Getter
    @Setter
    private String projId;
    
    @Getter
    @Setter
    private String taskId;
    @Getter
    
    @Setter
    private String procId;
    
    @Getter
    @Setter
    private String procInsId;
    
    @Getter
    @Setter
    private String formId;
    
    /**     
     * 审批动作:5:审批通过6:审批拒绝.7:未审批
     */
    @Getter
    @Setter
    private String actionType;
    
    @Getter
    @Setter
    private String isCountSigner;
    
}

