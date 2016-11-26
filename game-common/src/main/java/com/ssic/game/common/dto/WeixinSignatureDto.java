package com.ssic.game.common.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**		
 * <p>Title: WeixinJsApiSignatureDto </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 朱振	
 * @date 2015年11月10日 下午5:02:18	
 * @version 1.0
 * <p>修改人：朱振</p>
 * <p>修改时间：2015年11月10日 下午5:02:18</p>
 * <p>修改备注：</p>
 */
@ToString
public class WeixinSignatureDto implements Serializable
{

    
     /**   
     * serialVersionUID: （一句话描述这个变量表示什么）      
     */   
    
    private static final long serialVersionUID = -6149709225132956016L;
    
    /**
     * 微信公众号appId
     */
    @Getter
    @Setter
    private String appId;
    
    /**
     * 时间戳
     */
    @Getter
    @Setter
    private Long timestamp;
    
    /**
     * 随机字符串不能大于32位
     */
    @Getter
    @Setter
    private String nonceStr;
    
    /**
     * 签名
     */
    @Getter
    @Setter
    private String signature;
    
    
    /**
     * 要签名的url
     */
    @Getter
    @Setter
    private String url;
    
    /**
     * 签名用到的ticket
     */
    @Getter
    @Setter
    private WeiXinTicketDto ticket;
}

