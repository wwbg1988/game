/**
 * 
 */
package com.ssic.game.app.controller.dto;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSON;

import lombok.Getter;
import lombok.Setter;

/**		
 * <p>Title: DishTypeDto </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 刘博
 * @date 2015年8月28日 下午2:03:20	
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年8月28日 下午2:03:20</p>
 * <p>修改备注：</p>
 */
@SuppressWarnings("serial")
public class DishTypeDto implements Serializable
{

    /**
     * 菜品类型id
     */
    @Getter
    @Setter
    private String typeId;
    /**
     * 菜品类型名称
     */
    @Getter
    @Setter
    private String typeName;
    
    @Getter
    @Setter
    private JSON datas;
}
