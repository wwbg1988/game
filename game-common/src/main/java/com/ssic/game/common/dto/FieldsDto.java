/**
 * 
 */
package com.ssic.game.common.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**		
 * <p>Title: FieldsDto </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 刘博	
 * @date 2015年6月24日 上午10:11:02	
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年6月24日 上午10:11:02</p>
 * <p>修改备注：</p>
 */
@ToString
public class FieldsDto implements Serializable
{   
   
    private static final long serialVersionUID = 2002420846762098289L;

    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private String projId;

    @Getter
    @Setter
    private String paramDesc;

    //字段框参数名称
    @Getter
    @Setter
    private String paramName;

   

    @Getter
    @Setter
    private String pattern;

    @Getter
    @Setter
    private Boolean isencrypt;

    @Getter
    @Setter
    private Boolean isuniline;

    @Getter
    @Setter
    private Boolean isdiy;

    @Getter
    @Setter
    private Integer length;

    @Getter
    @Setter
    private Integer height;

    @Getter
    @Setter
    private Integer type;

    @Getter
    @Setter
    private Integer dataType;

    @Getter
    @Setter
    private Boolean isneed;

    @Getter
    @Setter
    private Integer stat;

    @Getter
    @Setter
    private Date lastUpdateTime;

    @Getter
    @Setter
    private Date createTime;
    
    @Getter
    @Setter
    private Integer orderId;
    
    @Getter
    @Setter
    private String procId;
    
    @Getter
    @Setter
    private String formId;
    
    @Getter
    @Setter
    private String formName;
    
    @Getter
    @Setter
    private String otherFormId;
    
    
    @Getter
    @Setter
    private String otherFieldId;
    
    @Getter
    @Setter
    private String refrenceFormName;
    @Getter
    @Setter
    private String refrenceParamName;
    
    @Getter
    @Setter
    private String refrenceParamDesc;
    
 
    @Getter
    @Setter
    private String fieldsCloneId;
    
}

