/**
 * 
 */
package com.ssic.game.app.controller.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import com.ssic.catering.base.dto.CarteTypeDto;

/**		
 * <p>Title: DishPercentDto </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年8月10日 下午6:22:00	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年8月10日 下午6:22:00</p>
 * <p>修改备注：</p>
 */
@SuppressWarnings("serial")
public class DishPercentDto implements Serializable
{
   
    /**
     * 菜品类型集合
     */
    @Getter
    @Setter
    private List<DishTypeDto> dishTypeList;
    
}
