/**
 * 
 */
package com.ssic.catering.base.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.ssic.catering.base.pojo.Carte;

import lombok.Getter;
import lombok.Setter;

/**		
 * <p>Title: CarteTypeDto </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年8月4日 上午9:38:10	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年8月4日 上午9:38:10</p>
 * <p>修改备注：</p>
 */
public class CarteTypeDto implements Serializable
{

    /**   
    * serialVersionUID: （一句话描述这个变量表示什么）      
    */

    private static final long serialVersionUID = 637087053820556515L;

    @Getter
    @Setter
    private String id;

    /**
     * 菜单类型名称
     */
    @Getter
    @Setter
    private String carteTypeName;

    /**
     * 最大可选数
     */
    @Getter
    @Setter
    private String upperLimit;

    /**
     * 是否有效：1：是，0：否
     */
    @Getter
    @Setter
    private Integer stat;

    /**
     * 更新时间
     */
    @Getter
    @Setter
    private Date lastUpdateTime;

    /**
     * 创建时间
     */
    @Getter
    @Setter
    private Date createTime;

    /**
     * 所属食堂id
     */
    @Getter
    @Setter
    private String cafetoriumId;
    /**
     * 菜单类型所属项目
     */
    @Getter
    @Setter
    private String projName;
    /**
     * 所属食堂名称
     */
    @Getter
    @Setter
    private String cafetoriumName;

    /**
     * 该类别的菜品集合
     */
    @Getter
    @Setter
    private List<CarteDto> carteList;

    @Getter
    @Setter
    private String icon; //菜单图片

    /**
     * 菜单类型背景
     */
    @Getter
    @Setter
    private String icon2;
}
