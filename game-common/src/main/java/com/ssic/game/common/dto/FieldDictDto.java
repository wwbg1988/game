/**
 * 
 */
package com.ssic.game.common.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**		
 * <p>Title: FieldDictDto </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 刘博	
 * @date 2015年6月24日 上午10:08:29	
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年6月24日 上午10:08:29</p>
 * <p>修改备注：</p>
 */
public class FieldDictDto implements Serializable{
    
    
    private static final long serialVersionUID = 6934041971522920423L;

    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private String fieldId;

    @Getter
    @Setter
    private String dictId;

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

