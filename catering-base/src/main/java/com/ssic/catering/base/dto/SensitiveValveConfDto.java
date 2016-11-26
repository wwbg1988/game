/**
 * 
 */
package com.ssic.catering.base.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**		
 * <p>Title: SensitiveValveConfDto </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author pengcheng.yang	
 * @date 2015年8月11日 下午5:21:20	
 * @version 1.0
 * <p>修改人：pengcheng.yang</p>
 * <p>修改时间：2015年8月11日 下午5:21:20</p>
 * <p>修改备注：</p>
 */
public class SensitiveValveConfDto implements Serializable
{
    
     /**   
     * serialVersionUID: （一句话描述这个变量表示什么）      
     */   
    
    private static final long serialVersionUID = 4751080230128664173L;

    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private Integer valveCount;

    @Getter
    @Setter
    private Float valvePercent;

    @Getter
    @Setter
    private Integer level;

    @Getter
    @Setter
    private Date createTime;
    
    @Getter
    @Setter
    private String projId;
    
    @Getter
    @Setter
    private String proName;

    @Getter
    @Setter
    private Date lastUpdateTime;

    @Getter
    @Setter
    private Integer stat;
    
}

