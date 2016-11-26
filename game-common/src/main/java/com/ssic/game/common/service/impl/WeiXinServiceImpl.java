package com.ssic.game.common.service.impl;

import java.util.Date;
import java.util.Hashtable;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ssic.game.common.dto.WeiXinAcessTokenDto;
import com.ssic.game.common.dto.WeiXinTicketDto;
import com.ssic.game.common.dto.WeixinSignatureDto;
import com.ssic.game.common.service.IWeinXinService;
import com.ssic.game.common.util.WeiXinApiErrorCode;
import com.ssic.game.common.util.WeiXinUtil;
import com.ssic.util.StringUtils;

/**
 * <p>
 * Title: WeiXinServiceImpl
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
 * @date 2015年10月13日 下午5:17:01
 * @version 1.0
 *          <p>
 *          修改人：朱振
 *          </p>
 *          <p>
 *          修改时间：2015年10月13日 下午5:17:01
 *          </p>
 *          <p>
 *          修改备注：
 *          </p>
 */
@Service
public class WeiXinServiceImpl implements IWeinXinService
{
    private static final Logger log = Logger.getLogger(WeiXinServiceImpl.class);

    // 微信的缓存access_token，jsapi_ticket，jsapi_signature
    private static Map<String, Object> weixinCache = new Hashtable<>();
    
    /**
     * 
     * (non-Javadoc)   
     * @see com.ssic.game.common.service.IWeinXinService#getJSAPISignature(java.lang.String, java.lang.String, java.lang.String, java.lang.Long, java.lang.String)
     */
    @Override
    public WeixinSignatureDto getJSAPISignature(String appId, String appSecret, String noncestr, Long timestamp,
        String url)
    {
        WeixinSignatureDto signatureObject = this.getSignatureByCache(url);
        if(signatureObject == null || StringUtils.isEmpty(signatureObject.getSignature()))
        {
            //判断jsapiTicket是否存在
            WeiXinTicketDto jsapiTicket = this.getJsapiTicketByCache();
            if(jsapiTicket == null || StringUtils.isEmpty(jsapiTicket.getTicket()))
            {
                //判断accessToken是否存在
                WeiXinAcessTokenDto accessToken = this.getAccessTokenByCache();
                if(accessToken == null || StringUtils.isEmpty(accessToken.getAccess_token()))
                {
                    accessToken = this.getWeiXinAcessToken(appId, appSecret);//获取
                }
                
                WeiXinTicketDto _jsapiTicket = this.getWeiXinJsApiTicket(accessToken);
                
                //判断是否可用
                if(_jsapiTicket== null || StringUtils.isEmpty(_jsapiTicket.getTicket()))
                {
                    log.error("生成的jsapiTicket为空");
                    return null;
                }
                
                jsapiTicket = _jsapiTicket;
            }
            
            signatureObject = WeiXinUtil.makeJSAPISignature(jsapiTicket, noncestr, timestamp, url);
            if(signatureObject !=null && !StringUtils.isEmpty(signatureObject.getSignature()))
            {
                //更新缓存
                weixinCache.put(url, signatureObject);
            }
            else
            {
                log.error("生成的签名为空");
                return null;
            }
        }
        
        return signatureObject;
    }
    
    
    private <T> T get(String key, Class<T> clazz)
    {
        if(weixinCache.get(key) != null)
        {
            return clazz.cast(weixinCache.get(key));
        }
        
        return null;
    }
    
    /**
     * 从weixinCache中获取jsapiTicket<BR>    	 
     * @author 朱振	
     * @date 2015年11月10日 下午5:44:09	
     * @version 1.0
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年11月10日 下午5:44:09</p>
     * <p>修改备注：</p>
     */
    private WeiXinTicketDto getJsapiTicketByCache()
    {
        WeiXinTicketDto result = this.get("jsapiTicket", WeiXinTicketDto.class);
        if(result != null && (new Date().getTime()-result.getSendTime()-result.getExpires_in()*1000) < 0)
        {
            return result;
        }
        
        return null;
    }
    
    /**
     * 添加Ticket<BR>	 
     * @author 朱振	
     * @date 2015年11月10日 下午5:54:30	
     * @version 1.0
     * @param ticket
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年11月10日 下午5:54:30</p>
     * <p>修改备注：</p>
     */
    private void addJsapiTicketToCache(WeiXinTicketDto ticket)
    {
        weixinCache.put("jsapiTicket", ticket);
    }
    
    /**
     * 添加token<BR>	 
     * @author 朱振	
     * @date 2015年11月10日 下午5:54:34	
     * @version 1.0
     * @param accessToken
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年11月10日 下午5:54:34</p>
     * <p>修改备注：</p>
     */
    private void addAccessTokenToCache(WeiXinAcessTokenDto accessToken)
    {
        weixinCache.put("accessToken", accessToken);
    }
    
    
    /**
     * 	 
     * @author 朱振	
     * @date 2015年11月10日 下午5:45:45	
     * @version 1.0
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年11月10日 下午5:45:45</p>
     * <p>修改备注：</p>
     */
    private WeiXinAcessTokenDto getAccessTokenByCache()
    {
        WeiXinAcessTokenDto result = this.get("accessToken", WeiXinAcessTokenDto.class);
        if(result != null && (new Date().getTime()-result.getSendTime()-result.getExpires_in()*1000) < 0)
        {
            return result;
        }
        
        return null;
    }
    
    
    /**
     * 从weixinCache中获取签名<BR>      
     * @author 朱振   
     * @date 2015年11月10日 下午4:51:20  
     * @version 1.0
     * @param key
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年11月10日 下午4:51:20</p>
     * <p>修改备注：</p>
     */
    private WeixinSignatureDto getSignatureByCache(String key)
    {
        WeixinSignatureDto result = this.get(key, WeixinSignatureDto.class);
        if(result != null && result.getTicket() != null && (new Date().getTime()-result.getTicket().getSendTime()-result.getTicket().getExpires_in()*1000) < 0)
        {
            return result;
        }
        
        return null;
    }

    
    /**
     * 获取accessToken<BR>	 
     * @author 朱振	
     * @date 2015年11月10日 下午2:01:47	
     * @version 1.0
     * @param appId
     * @param appSecret
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年11月10日 下午2:01:47</p>
     * <p>修改备注：</p>
     */
    private WeiXinAcessTokenDto getWeiXinAcessToken(String appId, String appSecret)
    {
        //从缓存中读取
        WeiXinAcessTokenDto accessTokenObject = this.getAccessTokenByCache();
        if(accessTokenObject == null)
        {
            accessTokenObject = this.getWeiXinAcessToken1(appId, appSecret);
        }
        
        return accessTokenObject;
    }
    
    
    /**
     * 重新获取accessToken，不从缓存中读取<BR>  
     * 这个接口有调用次数限制（2000次/日）<BR>
     * @author 朱振   
     * @date 2015年11月10日 下午2:01:47  
     * @version 1.0
     * @param appId
     * @param appSecret
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年11月10日 下午2:01:47</p>
     * <p>修改备注：</p>
     */
    private WeiXinAcessTokenDto getWeiXinAcessToken1(String appId, String appSecret)
    {
        if(StringUtils.isEmpty(appId))
        {
            log.error("appID不能为空");
            return null;
        }
        
        if(StringUtils.isEmpty(appSecret))
        {
            log.error("appSecret不能为空");
            return null;
        }

        WeiXinAcessTokenDto accessTokenObject = WeiXinUtil.getAccessToken(appId, appSecret);
       
            
        //获取的返回信息不能组装为WeiXinAcessTokenDto对象
        if(accessTokenObject == null)
        {
            log.error("获取的返回信息不能组装为WeiXinAcessTokenDto对象");
            return null;
        }
        
        if(accessTokenObject.getErrcode() == null || accessTokenObject.getErrcode() == 0)
        {
            //成功
            accessTokenObject.setAppId(appId);
            accessTokenObject.setAppSecret(appSecret);
            
            //更新缓存
            this.addAccessTokenToCache(accessTokenObject);
        }
        else
        {
          //将errorcode转换为可识别的错误信息
          accessTokenObject.setErrmsg(WeiXinApiErrorCode.getErroMessage(accessTokenObject.getErrcode()));

          switch (accessTokenObject.getErrcode())
          {
              case -1:
                  accessTokenObject = this.getWeiXinAcessToken1(appId, appSecret);
                  break;

              default:
                  break;
          }
        }
            
        
        return accessTokenObject;
    }
    
    
    /**
     * 获取jsApiTicket<BR>	 
     * @author 朱振	
     * @date 2015年11月10日 下午2:48:01	
     * @version 1.0
     * @param accessToken
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年11月10日 下午2:48:01</p>
     * <p>修改备注：</p>
     */
    private WeiXinTicketDto getWeiXinJsApiTicket(WeiXinAcessTokenDto accessToken)
    {
        WeiXinTicketDto ticket = this.getJsapiTicketByCache();
        
        if(ticket == null)
        {
            ticket = this.getWeiXinJsApiTicket1(accessToken);
        }
        
        return ticket;
    }
    
    /**
     * 重新获取jsapiTicket，不从缓存中读取<BR>  
     * 这个接口有调用次数限制（2000次/日）<BR>
     * @author 朱振	
     * @date 2015年11月10日 下午2:37:28	
     * @version 1.0
     * @param accessToken
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年11月10日 下午2:37:28</p>
     * <p>修改备注：</p>
     */
    private WeiXinTicketDto getWeiXinJsApiTicket1(WeiXinAcessTokenDto accessToken)
    {
        if(accessToken == null || StringUtils.isEmpty(accessToken.getAccess_token()))
        {
            log.error("参数错误WeiXinAcessTokenDto="+accessToken);
            return null;
        }
        
        WeiXinTicketDto ticketObject =  WeiXinUtil.getJSAPITicket(accessToken.getAccess_token());
            
        //获取的返回信息不能组装为WeiXinTicketDto对象
        if(ticketObject == null)
        {
            log.error("获取的返回信息不能组装为WeiXinTicketDto对象");
            return null;
        }
        
        if(ticketObject.getErrcode() == null || ticketObject.getErrcode() == 0)
        {
            //成功
            this.addJsapiTicketToCache(ticketObject);
        }
        else 
        {
            //将errorcode转换为可识别的错误信息
            ticketObject.setErrmsg(WeiXinApiErrorCode.getErroMessage(ticketObject.getErrcode()));

            switch (ticketObject.getErrcode())
            {
                case -1://重试
                    ticketObject = this.getWeiXinJsApiTicket1(accessToken);
                    break;
                case 40001://accessToken 获取access_token时AppSecret错误，或者access_token无效。请开发者认真比对AppSecret的正确性，或查看是否正在为恰当的公众号调用接口
                case 40014://accessToken 不合法的access_token，请开发者认真比对access_token的有效性（如是否过期），或查看是否正在为恰当的公众号调用接口
                case 42001://accessToken access_token超时，请检查access_token的有效期，请参考基础支持-获取access_token中，对access_token的详细机制说明
                case 42002://accessToken refresh_token超时
                    WeiXinAcessTokenDto newAccessToken = this.getWeiXinAcessToken1(accessToken.getAppId(), accessToken.getAppSecret());
                    if(newAccessToken !=null && !StringUtils.isEmpty(newAccessToken.getAccess_token()))
                    {
                        ticketObject = this.getWeiXinJsApiTicket1(newAccessToken);
                    }
  
                    break;
                default:
                    break;
            }
        }
        
        return ticketObject;
    }
}
