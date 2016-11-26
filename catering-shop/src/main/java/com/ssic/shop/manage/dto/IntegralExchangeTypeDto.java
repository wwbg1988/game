package com.ssic.shop.manage.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**		
 * <p>Title: IntegralExchangeTypeDto </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 朱振	
 * @date 2015年9月22日 上午9:05:07	
 * @version 1.0
 * <p>修改人：朱振</p>
 * <p>修改时间：2015年9月22日 上午9:05:07</p>
 * <p>修改备注：</p>
 */
@ToString
public class IntegralExchangeTypeDto implements Serializable
{

    
     /**   
     * serialVersionUID: （一句话描述这个变量表示什么）      
     */   
    
    private static final long serialVersionUID = -5054671400872014413L;
    
    /**
     * id
     */
    @Getter
    @Setter
    private String id;
    
    /**
     * cafetoriumId
     */
    @Getter
    @Setter
    private String cafetoriumId;

    /**
     * 兑换物品类型名
     */
    @Getter
    @Setter
    private String name;

    /**
     * 兑换物品的模板页名
     */
    @Getter
    @Setter
    private String target;
    
    /**
     * 创建日期
     */
    @Getter
    @Setter
    private Date createTime;

    /**
     * 结束日期
     */
    @Getter
    @Setter
    private Date lastUpdateTime;

    /**
     * 是否有效
     */
    @Getter
    @Setter
    private Integer stat;

}

