/**
 * 
 */
package com.ssic.game.common.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**		
 * <p>Title: FormsFieldsDto </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 刘博	
 * @date 2015年6月24日 上午10:14:56	
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年6月24日 上午10:14:56</p>
 * <p>修改备注：</p>
 */
public class FormsFieldsDto implements Serializable
{
    
    private static final long serialVersionUID = 6139698790707639665L;

    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private String formId;

    @Getter
    @Setter
    private String fieldId;

    @Getter
    @Setter
    private Date lastUpdateTime;

    @Getter
    @Setter
    private Date createTime;
    
    @Getter
    @Setter
    private Integer stat;

}

