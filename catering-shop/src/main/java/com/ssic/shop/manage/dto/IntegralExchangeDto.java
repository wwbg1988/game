package com.ssic.shop.manage.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * Title: IntegralExchangeDto
 * </p>
 * <p>
 * Description: 类描述
 * </p>
 * <p>
 * Copyright (c) 2015
 * </p>
 * <p>
 * Company: 上海天坊信息科技有限公司
 * </p>
 * 
 * @author 朱振
 * @date 2015年9月18日 下午12:01:41
 * @version 1.0
 *          <p>
 *          修改人：朱振
 *          </p>
 *          <p>
 *          修改时间：2015年9月18日 下午12:01:41
 *          </p>
 *          <p>
 *          修改备注：
 *          </p>
 */
public class IntegralExchangeDto
{
    /**
     * id
     */
    @Getter
    @Setter
    private String id;

    /**
     * 可兑换的物品类型id
     */
    @Getter
    @Setter
    private String exchangeTypeId;

    /**
     * 可兑换的物品名字
     */
    @Getter
    @Setter
    private String exchangeName;

    /**
     * 兑换物品描述
     */
    @Getter
    @Setter
    private String exchangeDescribe;

    /**
     * 兑换物品图片
     */
    @Getter
    @Setter
    private String exchangeIcon;

    /**
     * 兑换物品积分
     */
    @Getter
    @Setter
    private Integer exchangeIntegral;

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
