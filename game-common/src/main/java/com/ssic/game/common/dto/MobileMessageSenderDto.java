package com.ssic.game.common.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**		
 * <p>Title: MobilePhoneMessageSenderDto </p>
 * <p>Description: 手机短信发送的dto</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 朱振	
 * @date 2015年10月28日 上午11:35:18	
 * @version 1.0
 * <p>修改人：朱振</p>
 * <p>修改时间：2015年10月28日 上午11:35:18</p>
 * <p>修改备注：</p>
 */
@ToString
public class MobileMessageSenderDto implements Serializable
{
     /**   
     * serialVersionUID: （一句话描述这个变量表示什么）      
     */   
    
    private static final long serialVersionUID = 1195081032884366944L;
    
    
    /**
     * 提供发送手机短信的服务地址
     */
    @Getter
    @Setter
    private String url;
    
    
    /**
     * 提供发送手机短信的账号
     */
    @Getter
    @Setter
    private String account;
    
    
    /**
     * 提供发送手机短信的密码
     */
    @Getter
    @Setter
    private String password;
    
    
    /**
     * （为哪个产品提供发送手机短信）产品编号
     */
    @Getter
    @Setter
    private String product;
    
   /**
    * 提供发送手机短信的扩展码
    */
    @Getter
    @Setter
    private String extno;
    
    /**
     * 手机号
     */
    @Getter
    @Setter
    private String mobile;
    
    /**
     * 短信内容
     */
    @Getter
    @Setter
    private String content;
    
    
    /**
     * 是否需要显示状态
     */
    @Getter
    @Setter
    private boolean needstatus;
}

