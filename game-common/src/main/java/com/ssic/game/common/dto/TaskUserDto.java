
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
public class TaskUserDto implements Serializable
{
    
    private static final long serialVersionUID = 1L;

    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private String fieldId;

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
    private String formId;

    @Getter
    @Setter
    private String userId;

    @Getter
    @Setter
    private Integer stat;

    @Getter
    @Setter
    private Date lastUpdateTime;

    @Getter
    @Setter
    private Date createTime;
}

