
/**   
 * bare_field_name   
 * com.ssic.game.admin.dto	
 * @return  the bare_field_name 
 */   

package com.ssic.catering.admin.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**		
 * <p>Title: FieldRight </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年6月23日 下午9:22:37	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年6月23日 下午9:22:37</p>
 * <p>修改备注：</p>
 */
public class FieldRightsDto implements Serializable
{
    
     /**   
     * serialVersionUID: （一句话描述这个变量表示什么）      
     */   
    
    private static final long serialVersionUID = 1L;

    @Getter
    @Setter
    private String id;
    
    @Getter
    @Setter
    private String formId;
    
    @Getter
    @Setter
    private String projId;
    
    @Getter
    @Setter
    private String taskId;
    
    @Getter
    @Setter
    private String fieldId;
    
    @Getter
    @Setter
    private String fieldUserNo;
    
    @Getter
    @Setter
    private int type;
    
    @Getter
    @Setter
    private int stat;
    
    @Getter
    @Setter
    private Date lastTime;
    
    @Getter
    @Setter
    private Date createTime;

}

