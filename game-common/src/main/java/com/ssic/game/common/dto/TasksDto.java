package com.ssic.game.common.dto;

import java.io.Serializable;
import java.util.Date;

import com.ssic.base.redis.RedisKeyPrefix;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@RedisKeyPrefix(prefixValue="game.common.dto.TasksDto}")
public class TasksDto implements Serializable{

    private static final long serialVersionUID = 7919565425050156927L;
    
    @Getter
    @Setter
    private String id;
    
    @Getter
    @Setter
    private String name;
    
    @Getter
    @Setter
    private String projId;
    
    @Getter
    @Setter
    private String procId;
    
    @Getter
    @Setter
    private String formId;
    
    @Getter
    @Setter
    private Integer type;
    
    @Getter
    @Setter
    private Integer stat;
    
    @Getter
    @Setter
    private Integer state;
    
    @Getter
    @Setter
    private String preTaskId;
    
    @Getter
    @Setter
    private Integer countersign;
    
    @Getter
    @Setter
    private Date lastUpdateTime;
    
    @Getter
    @Setter
    private Date createTime;
    
    @Getter
    @Setter
    private String typeName;//类型名称
   
    @Getter
    @Setter
    private String statName; //状态名称
    
    @Getter
    @Setter
    private String countersignName;//会签名称
    
    
    @Getter
    @Setter
    private String projName;//项目名称
    @Getter
    @Setter
    private String procName;//流程名称
    @Getter
    @Setter
    private String formName;//表单名称
    
	
}
