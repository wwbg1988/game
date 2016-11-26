/**
 * 
 */
package com.ssic.game.app.controller.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import com.ssic.catering.base.dto.CarteTypeDto;

/**		
 * <p>Title: 下周菜单百分比统计 </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 刘博	
 * @date 2015年8月8日 下午1:48:48	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年8月8日 下午1:48:48</p>
 * <p>修改备注：</p>
 */
@SuppressWarnings("serial")
public class CafetoriumData implements Serializable
{
    /**
     * 食堂ID
     */
    @Getter
    @Setter
    private String id;

    /**
     * 食堂名称
     */
    @Getter
    @Setter
    private String cafeName;
    /**
     * 每个食堂的所属菜单周期
     */
    @Getter
    @Setter
    private String carteWeek;
  
    /**
     * 每个食堂的所属菜单周期描述
     */
    @Getter
    @Setter
    private String carteWeekDesc;
    /**
     * 每个食堂的菜单标题开头
     */
    @Getter
    @Setter
    private String cafeTitleStart;
    /**
     * 每个食堂的菜单标题开头
     */
    @Getter
    @Setter
    private String cafeTitleEnd;
    /**
     * 每个食堂的菜单标题时间
     */
    @Getter
    @Setter
    private String carteTime;

    /**
     * 菜单类型集合
     */
    @Getter
    @Setter
    private List<CarteTypeDto> carteTypeList;

}
