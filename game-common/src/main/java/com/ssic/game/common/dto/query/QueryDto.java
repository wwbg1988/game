/**
 * 
 */
package com.ssic.game.common.dto.query;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**		
 * <p>Title: QueryDto </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年7月28日 下午4:29:39	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年7月28日 下午4:29:39</p>
 * <p>修改备注：</p>
 */
public class QueryDto implements Serializable
{
    @Getter
    @Setter
    private String id;
    
    @Getter
    @Setter
    private String name;
    
    @Getter
    @Setter
    private String groupName;
    
    @Getter
    @Setter
    private String projectId;
    
    @Getter
    @Setter
    private String processId;
    
    @Getter
    @Setter
    private String taskId;
    
    @Getter
    @Setter
    private int formStat;
    
    @Getter
    @Setter
    private String actionId;
    
    
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
    private String projectName;
    @Getter
    @Setter
    private String processName;
    @Getter
    @Setter
    private String taskName;
    @Getter
    @Setter
    private String actionName;
    
}

