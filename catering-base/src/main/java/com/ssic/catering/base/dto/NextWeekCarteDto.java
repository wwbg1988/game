package com.ssic.catering.base.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**		
 * <p>Title: NextWeekCarteDto </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author yuanbin	
 * @date 2015年8月5日 下午1:07:27	
 * @version 1.0
 * <p>修改人：yuanbin</p>
 * <p>修改时间：2015年8月5日 下午1:07:27</p>
 * <p>修改备注：</p>
 */
@ToString
public class NextWeekCarteDto implements Serializable{
    
    
     /**   
     * serialVersionUID: （一句话描述这个变量表示什么）      
     */   
    
    private static final long serialVersionUID = 7461037314915302429L;

    @Getter
    @Setter
    private String id;

    /**
     * 菜品名称
     */
    @Getter
    @Setter
    private String dishName;
    /**
     * 菜单类型ID
     */
    @Getter
    @Setter
    private String carteTypeId;
    /**
     * 创建时间
     */
    @Getter
    @Setter
    private Date createTime;
    /**
     * 菜单所属周期（一年中的第几周）
     */
    @Getter
    @Setter
    private String carteWeek;

    /**
     * 当前日期为一年中的第几周(如:15年8月第一周)
     */
    @Getter
    @Setter
    private String carteWeekDesc;
    /**
     * 最后更新时间
     */
    @Getter
    @Setter
    private Date lastUpdateTime;
    /**
     * 食堂ID
     */
    @Getter
    @Setter
    private String cafetoriumId;
    /**
     * 是否有效
     */
    @Getter
    @Setter
    private Integer stat;

 
}