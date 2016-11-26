/**
 * 
 */
package com.ssic.catering.base.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**		
 * <p>Title: CountSensitiveDto </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author pengcheng.yang	
 * @date 2015年8月11日 下午4:17:46	
 * @version 1.0
 * <p>修改人：pengcheng.yang</p>
 * <p>修改时间：2015年8月11日 下午4:17:46</p>
 * <p>修改备注：</p>
 */
public class CountSensitiveDto implements Serializable
{   
    @Getter
    @Setter
    private int sensitiveTotals;
    
    @Getter
    @Setter
    private String cafetoriumId;
    
    @Getter
    @Setter
    private String sensitiveId;
    
    @Getter
    @Setter
    private String sensitiveName;
}

