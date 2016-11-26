/**
 * 
 */
package com.ssic.game.common.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**		
 * <p>Title: FiledsCloneDto </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 刘博
 * @date 2015年6月28日 上午9:33:05	
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年6月28日 上午9:33:05</p>
 * <p>修改备注：</p>
 */
public class FieldsCloneDto implements Serializable
{
    
     /**   
     * serialVersionUID: （一句话描述这个变量表示什么）      
     */   
    
    private static final long serialVersionUID = -7272469974026214113L;

    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private String formId;

    @Getter
    @Setter
    private String fieldsId;

    @Getter
    @Setter
    private String paramName;

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

