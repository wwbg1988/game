/**
 * 
 */
package com.ssic.game.ims.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**		
 * <p>Title: CreateProcRequest </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author rkzhang	
 * @date 2015年6月25日 下午2:43:58	
 * @version 1.0
 * <p>修改人：rkzhang</p>
 * <p>修改时间：2015年6月25日 下午2:43:58</p>
 * <p>修改备注：</p>
 */
@ToString
public class CreateProcRequest {
    
    @Getter
    @Setter
    private String projectId;
    
    @Getter
    @Setter
    private String userId;

    @Getter
    @Setter
    private String processId;
    @Getter
    @Setter
    private String procName;
    
}

