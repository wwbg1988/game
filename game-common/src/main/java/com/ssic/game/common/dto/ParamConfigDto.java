package com.ssic.game.common.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class ParamConfigDto implements Serializable
{

    /**
     * serialVersionUID: （一句话描述这个变量表示什么）
     */

    private static final long serialVersionUID = 6410137376658862042L;
    
    /**
     * id
     */
    @Getter
    @Setter
    private String id;
    
    /**
     * 参数名
     */
    @Getter
    @Setter
    private String paramName;

    /**
     * 参数值
     */
    @Getter
    @Setter
    private String paramValue;

    /**
     * 参数描述
     */
    @Getter
    @Setter
    private String paramDescribe;

    /**
     * 创建时间
     */
    @Getter
    @Setter
    private Date createTime;

    /**
     * 更新时间
     */
    @Getter
    @Setter
    private Date lastUpdateTime;

    /**
     * 是否有有效
     */
    @Getter
    @Setter
    private Integer stat;

    /**
     * 参数类型
     */
    @Getter
    @Setter
    private Integer paramType;

    /**
     * 参数所属项目id
     */
    @Getter
    @Setter
    private String projId;
    
    
    /**
     * 参数所属项目
     */
    @Getter
    @Setter
    private String projectName;

}
