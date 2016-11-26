package com.ssic.shop.manage.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**		
 * <p>Title: CarteUserDto </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 朱振	
 * @date 2015年8月27日 上午10:00:05	
 * @version 1.0
 * <p>修改人：朱振</p>
 * <p>修改时间：2015年8月27日 上午10:00:05</p>
 * <p>修改备注：</p>
 */
public class CarteUserDto implements Serializable
{
   
    
    /**   
     * serialVersionUID: （一句话描述这个变量表示什么）      
     */

    private static final long serialVersionUID = 2316320377800665357L;

    /**
     *
     * 商城用户id
     */
    @Getter
    @Setter
    private String id;

    /**
     *
     * 微信唯一标识id
     */
    @Getter
    @Setter
    private String openId;
    
    /**
    *
    * 食堂id
    */
    @Getter
    @Setter
    private String cafetoriumId;

    /**
     *
     * 用户姓名
     */
    @Getter
    @Setter
    private String name;

    /**
     *
     * 用户生日
     */
    @Getter
    @Setter
    private String birthday;

    /**
    *
    * 用户积分
    */
    @Getter
    @Setter
    private Integer integral;

    /**
     *
     * 用户手机号码
     */
    @Getter
    @Setter
    private String phone;

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
     * 是否有效
     */
    @Getter
    @Setter
    private Integer stat;
    
}

