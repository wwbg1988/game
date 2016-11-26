package com.ssic.game.common.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**		
 * <p>Title: WeiXinGetTicketDto </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 朱振	
 * @date 2015年10月14日 下午1:53:56	
 * @version 1.0
 * <p>修改人：朱振</p>
 * <p>修改时间：2015年10月14日 下午1:53:56</p>
 * <p>修改备注：</p>
 */
@ToString
public class WeiXinTicketDto implements Serializable
{
    public WeiXinTicketDto()
    {
        this.setCreatTime(new Date().getTime());
    }
    
     /**   
     * serialVersionUID: （一句话描述这个变量表示什么）      
     */   
    
    private static final long serialVersionUID = -7539982770892736964L;

    /**
     * 错误码 微信
     */
    @Getter
    @Setter
    private Integer errcode;
    
    /**
     * 微信错误
     */
    @Getter
    @Setter
    private String errmsg;
    
    /**
     * ticket
     */
    @Getter
    @Setter
    private String ticket;
    
    /**
     * 过期时间（秒）
     */
    @Getter
    @Setter
    private Long expires_in;
    
    /**
     * 对象的创建时间（毫秒）
     */
    @Getter
    @Setter
    private Long creatTime;
    
    
    /**
     * 获取ticket的发送时间（毫秒）
     */
    @Getter
    @Setter
    private Long sendTime;
}

