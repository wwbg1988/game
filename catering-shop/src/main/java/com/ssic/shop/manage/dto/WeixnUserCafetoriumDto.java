/**
 * 
 */
package com.ssic.shop.manage.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**		
 * <p>Title: WeixnUserCafetoriumDto </p>
 * <p>Description: 微信用户食堂关系(关注过的)</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 刘博	
 * @date 2015年10月27日 下午4:03:40	
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年10月27日 下午4:03:40</p>
 * <p>修改备注：</p>
 */
public class WeixnUserCafetoriumDto implements Serializable
{

    /**   
    * serialVersionUID: （一句话描述这个变量表示什么）      
    */

    private static final long serialVersionUID = 7697124375984030103L;

    /**
    *id
    */
    @Getter
    @Setter
    private String id;

    /**
    *微信用户唯一id
    */
    @Getter
    @Setter
    private String openId;

    /**
    *食堂公众号id
    */
    @Getter
    @Setter
    private String cafetoriumId;

    /**
    *创建时间
    */
    @Getter
    @Setter
    private Date createTime;

    /**
    *更新时间 
    */
    @Getter
    @Setter
    private Date lastUpdateTime;

    /**
     * 是否有效：1是；0：否;
     */
    @Getter
    @Setter
    private Integer stat;
    
    /**
     * 是否默认：1是；0：否;
     */
    @Getter
    @Setter
    private Integer isDefault;

}
