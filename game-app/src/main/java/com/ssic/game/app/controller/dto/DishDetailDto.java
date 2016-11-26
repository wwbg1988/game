/**
 * 
 */
package com.ssic.game.app.controller.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**		
 * <p>Title: DishDetailDto </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 刘博	
 * @date 2015年8月28日 下午1:44:10	
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年8月28日 下午1:44:10</p>
 * <p>修改备注：</p>
 */
@SuppressWarnings("serial")
public class DishDetailDto implements Serializable
{

    /**
     * 菜品名称
     */
    @Getter
    @Setter
    private String name;
    /**
     * 菜品所占百分比
     */
    @Getter
    @Setter
    private String value;
    /**
     * 菜品类型id
     */
    @Getter
    @Setter
    private String typeId;
    
    
}

