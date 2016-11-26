
/**   
 * bare_field_name   
 * com.ssic.game.admin.pojo	
 * @return  the bare_field_name 
 */   

package com.ssic.game.common.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**		
 * <p>Title: ActionsUser </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年6月23日 下午8:48:12	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年6月23日 下午8:48:12</p>
 * <p>修改备注：</p>
 */
public class ActionUserDto implements Serializable
{
    //对应表t_ims_action_role
     /**   
     * serialVersionUID: （一句话描述这个变量表示什么）      
     */   
    
    private static final long serialVersionUID = 1L;

    @Getter
    @Setter
    private String id;
    
    @Getter
    @Setter
    private String projId;
    
    @Getter
    @Setter
    private String actionId;
    
    @Getter
    @Setter
    private String userId;
    
    @Getter
    @Setter
    private int stat;
    
    @Getter
    @Setter
    private Date lastUpdateTime;
    
    @Getter
    @Setter
    private Date createTime;
    

}

