package com.ssic.shop.manage.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**		
 * <p>Title: DinnerSeriesDto </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 朱振	
 * @date 2015年9月16日 上午10:27:04	
 * @version 1.0
 * <p>修改人：朱振</p>
 * <p>修改时间：2015年9月16日 上午10:27:04</p>
 * <p>修改备注：</p>
 */
@ToString
public class DinnerSeriesDto
{
    /**
     * id
     */
    @Getter
    @Setter
    private String id;
    
    /**
     * 食堂id
     */
    @Getter
    @Setter
    private String cafetoriumId;

    /**
     * 套餐名
     */
    @Getter
    @Setter
    private String name;

    /**
     * 大图
     */
    @Getter
    @Setter
    private String defaultImage;

    /**
     * 图1
     */
    @Getter
    @Setter
    private String image1;

    /**
     * 图2
     */
    @Getter
    @Setter
    private String image2;

    /**
     * 图3
     */
    @Getter
    @Setter
    private String image3;

    /**
     * 价格
     */
    @Getter
    @Setter
    private Integer prince;

    /**
     * 最少人数
     */
    @Getter
    @Setter
    private Integer minPerson;

    /**
     * 最多人数
     */
    @Getter
    @Setter
    private Integer maxPerson;

    /**
     * 创建日期
     */
    @Getter
    @Setter
    private Date createTime;

    /**
     * 最后更新日期
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

