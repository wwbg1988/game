package com.ssic.game.common.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**		
 * <p>Title: WeiXinAcessToken </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 朱振	
 * @date 2015年10月14日 下午1:54:43	
 * @version 1.0
 * <p>修改人：朱振</p>
 * <p>修改时间：2015年10月14日 下午1:54:43</p>
 * <p>修改备注：</p>
 */
@ToString
public class WeiXinAcessTokenDto implements Serializable
{
    public WeiXinAcessTokenDto()
    {
        this.setCreatTime(new Date().getTime());
    }
    
     /**   
     * serialVersionUID: （一句话描述这个变量表示什么）      
     */   
    
    private static final long serialVersionUID = -4572561878212883312L;
    
    
    /**
     * access_token
     */
    @Getter
    @Setter
    private String access_token;
    
    /**
     * expires_in  过期时间（秒）
     */
    @Getter
    @Setter
    private Long expires_in;
    
    /**
     * 接口调用的返回的错误码
     */
    @Getter
    @Setter
    private Integer errcode;
    
    /**
     * 接口调用的返回的错误信息
     */
    @Getter
    @Setter
    private String errmsg;
    
    /**
     * 公众号id
     */
    @Getter
    @Setter
    private String appId;
    
    /**
     * 公众号密码
     */
    @Getter
    @Setter
    private String appSecret;
    
    /**
     * 对象的创建时间（毫秒）
     */
    @Getter
    @Setter
    private Long creatTime;
    
    /**
     * 获取token的发送请求的时间（毫秒）
     */
    @Getter
    @Setter
    private Long sendTime;
}

