/**
 * 
 */
package com.ssic.game.ims.model;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**		
 * <p>Title: ProcessFormRequest </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author rkzhang	
 * @date 2015年6月30日 上午9:38:18	
 * @version 1.0
 * <p>修改人：rkzhang</p>
 * <p>修改时间：2015年6月30日 上午9:38:18</p>
 * <p>修改备注：</p>
 */
@ToString
public class ProcessFormRequest
{

    @Getter
    @Setter
    private String projectId;

    @Getter
    @Setter
    private String userId;

    @Getter
    @Setter
    private String procInstanceId;

    @Getter
    @Setter
    private String formId;

    @Getter
    @Setter
    private String actionId;

    @Getter
    @Setter
    Map<String, Object> fields;
    //指派人id 
    @Getter
    @Setter
    private String assignUserId;
}
