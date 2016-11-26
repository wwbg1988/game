package com.ssic.shop.manage.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**		
 * <p>Title: UserIntegralExchangeDto </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 朱振	
 * @date 2015年9月18日 下午2:30:05	
 * @version 1.0
 * <p>修改人：朱振</p>
 * <p>修改时间：2015年9月18日 下午2:30:05</p>
 * <p>修改备注：</p>
 */
public class UserIntegralExchangeDto
{
    /**
     * id
     */
    @Getter
    @Setter
    private String id;

    /**
     * 兑换物品id
     */
    @Getter
    @Setter
    private String integralExchangeId;

    /**
     * 商城用户id
     */
    @Getter
    @Setter
    private String carteUserId;

   /**
    * 地址
    */
    @Getter
    @Setter
    private String address;

    /**
     * 手机号码
     */
    @Getter
    @Setter
    private String phoneNumber;

    /**
     * 收货人姓名
     */
    @Getter
    @Setter
    private String consigneeName;

    /**
     * 收货人城市
     */
    @Getter
    @Setter
    private String consigneeCity;

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

