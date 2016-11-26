
/**   
 * bare_field_name   
 * com.ssic.game.admin.pojo	
 * @return  the bare_field_name 
 */   

package com.ssic.game.common.dto;

import java.io.Serializable;
import java.util.Date;

import com.ssic.base.redis.RedisKeyPrefix;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**		
 * <p>Title: Actions </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年6月23日 下午8:29:37	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年6月23日 下午8:29:37</p>
 * <p>修改备注：</p>
 */

@ToString
@RedisKeyPrefix(prefixValue="game.common.dto.ActionsDto:{id}")
public class ActionsDto implements Serializable
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
    private String name;
    
    @Getter
    @Setter
    private String taskId;
    
    @Getter
    @Setter
    private String nextTaskId;
    
    @Getter
    @Setter
    private String projId;
    
    @Getter
    @Setter
    private String procId;
    
    @Getter
    @Setter
    private int type;
    
    @Getter
    @Setter
    private String actionUrl;
    
    @Getter
    @Setter
    private int stat;
    
    @Getter
    @Setter
    private Date lastUpdateTime;
    
    @Getter
    @Setter
    private Date createTime;
    
    @Getter
    @Setter
    private String taskName;
    
    @Getter
    @Setter
    private String nextTaskName;
   
    @Getter
    @Setter
    private String typeName;
    
    @Getter
    @Setter
    private String projName;
    
    @Getter
    @Setter
    private String searchPro;
}

