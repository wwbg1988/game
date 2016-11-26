/**
 * 
 */
package com.ssic.game.ims.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**		
 * <p>Title: formDataQuery </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年7月24日 上午9:22:31	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年7月24日 上午9:22:31</p>
 * <p>修改备注：</p>
 */
public class FormDataModel implements Serializable
{
    @Getter
    @Setter
    private String processInstanceId;
    
    @Getter
    @Setter
    private String procId;
    
    @Getter
    @Setter
    private String projectId;
    
    @Getter
    @Setter
    private int formStat=-1;
    
    @Getter
    @Setter
    private String deptCode;
    
    @Getter
    @Setter
    private String deptId;
    
    @Getter
    @Setter
    private String userId;
    
    @Getter
    @Setter
    private String formId;
    
    @Getter
    @Setter
    private int beginRow;
    
    @Getter
    @Setter
    private int size;
    
    @Getter
    @Setter
    private int countSigner = -1;
    
}

