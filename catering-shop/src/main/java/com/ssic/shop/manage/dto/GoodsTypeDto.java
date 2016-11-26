/**
 * 
 */
package com.ssic.shop.manage.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**		
 * <p>Title: GoodsTypeDto </p>
 * <p>Description: 商品类型dto</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 刘博	
 * @date 2015年8月27日 上午9:49:01	
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年8月27日 上午9:49:01</p>
 * <p>修改备注：</p>
 */
public class GoodsTypeDto implements Serializable
{

    /**   
    * serialVersionUID: （一句话描述这个变量表示什么）      
    */

    private static final long serialVersionUID = -691654102146783639L;

    /**
    *
    * 商品类型id
    */
    @Getter
    @Setter
    private String id;

    /**
    *
    * 商品类型名称
    */
    @Getter
    @Setter
    private String typeName;

    /**
    *
    * 商品类型图片
    */
    @Getter
    @Setter
    private String icon;

    /**
    *
    * 创建时间
    */
    @Getter
    @Setter
    private Date createTime;

    /**
    *
    * 修改时间
    */
    @Getter
    @Setter
    private Date lastUpdateTime;

    /**
    *
    * 是否有效 1：是；0：否
    */
    @Getter
    @Setter
    private Integer stat;

}
