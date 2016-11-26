package com.ssic.game.app.controller.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**		
 * <p>Title: ImsUserBindingDto </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 朱振	
 * @date 2015年10月30日 下午3:25:15	
 * @version 1.0
 * <p>修改人：朱振</p>
 * <p>修改时间：2015年10月30日 下午3:25:15</p>
 * <p>修改备注：</p>
 */
@ToString
public class ImsUserBindingDto implements Serializable
{

    
     /**   
     * serialVersionUID: （一句话描述这个变量表示什么）      
     */   
    
    private static final long serialVersionUID = 4019415497167625407L;
    
    /**
     * userId
     */
    @Getter
    @Setter
    private String userId;
    
    /**
     * 微信的绑定状态 0表示未绑定；1表示绑定
     */
    @Getter
    @Setter
    private Integer weixinBinding;
    
    
    /**
     * 手机的绑定状态 0表示未绑定；1表示绑定
     */
    @Getter
    @Setter
    private Integer mobileBinding;
    
    /**
     * 微信昵称
     */
    @Getter
    @Setter
    private String weixinNickname;
    
    /**
     * 绑定的手机号码
     */
    @Getter
    @Setter
    private String mobile;
}

