package com.ssic.game.common.util;


import java.util.Date;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.ssic.game.common.dto.WeiXinAcessTokenDto;
import com.ssic.game.common.dto.WeiXinTicketDto;
import com.ssic.game.common.dto.WeixinSignatureDto;
import com.ssic.util.HttpClientUtil;
import com.ssic.util.StringUtils;
import com.ssic.util.digest.SHACoder;



/**		
 * <p>Title: WeixinUtil </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 朱振	
 * @date 2015年10月8日 下午1:57:15	
 * @version 1.0
 * <p>修改人：朱振</p>
 * <p>修改时间：2015年10月8日 下午1:57:15</p>
 * <p>修改备注：</p>
 */
public class WeiXinUtil
{
    private static final Logger log = Logger.getLogger(WeiXinUtil.class);
    
    /**
     * 获取微信token
     * https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET
     */
    public static String WEIXIN_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token";
    
    /**
     * 获取微信的授权类型grant_type
     * client_credential
     */
    public static String GRANT_TYPE = "client_credential";
    
    /**
     * 微信js授权类型
     */
    public static String JSAPI = "jsapi";
    
    /**
     * 微信支付授权类型
     */
    public static String WXCARD = "wx_card";
    
    
    /**
     * 获取ticket
     * 微信支付https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=wx_card
     * 微信js使用https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi
     */
    public static String GETTICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket";
    

    /**
     * 获取微信公众号全局token access_token<br>
     * 每天调用api获取token的次数是有限的而且token是有时限的（2000次/日）<br>
     * @author 朱振	
     * @date 2015年10月8日 下午4:16:25	
     * @version 1.0
     * @param appID
     * @param appSecret
     * @param isForce true-强制调用;false,当access_token已存在是
     * @return {"access_token":"pWP59hfEIalKfql-NT2NFLUOwLR0JjTTxOG0dbsMw8APr4P4TpMwkj8WSfgho1Y376_48-i1SzqPy5vcNrG4HFJ1-eNcn1T1SMTSbYWCxnM","expires_in":7200}
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年10月8日 下午4:16:25</p>
     * <p>修改备注：</p>
     */
    public static WeiXinAcessTokenDto getAccessToken(String appId, String appSecret)
    {
        log.info("parameters:appID="+appId+";appSecret="+appSecret);
        //参数验证
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
        
        log.info("HttpClientUtil.sendGetRequest:url="+WEIXIN_ACCESS_TOKEN_URL+"?grant_type="+GRANT_TYPE+"&appid="+appId+"&secret="+appSecret);
        
        //记录发送请求时的时间
        Long sendTime = new Date().getTime();
        
       String response = HttpClientUtil.sendGetRequest(WEIXIN_ACCESS_TOKEN_URL+"?grant_type="+GRANT_TYPE+"&appid="+appId+"&secret="+appSecret, null);
       if(StringUtils.isEmpty(response))
       {
           log.error("获取token失败");
           return null;
       }
       
       WeiXinAcessTokenDto result = JSON.parseObject(response,WeiXinAcessTokenDto.class);
       result.setSendTime(sendTime);//设置请求时间
       
       return result;
    }
    
    /**
     * 根据token获取微信jssdk的ticket<BR> 
     * @author 朱振	
     * @date 2015年11月10日 下午1:17:21	
     * @version 1.0
     * @param access_token
     * @param type
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年11月10日 下午1:17:21</p>
     * <p>修改备注：</p>
     */
    public static WeiXinTicketDto getJSAPITicket(String accessToken)
    {
        log.info("parameters:accessToken="+accessToken);
        
        if(StringUtils.isEmpty(accessToken))
        {
            log.error("accessToken不能为空");
            return null;
        }
        
        log.info("HttpClientUtil.sendGetRequest:url="+GETTICKET_URL+"?access_token="+accessToken+"&type="+WeiXinUtil.JSAPI);
        
        //记录发送请求时的时间
        Long sendTime = new Date().getTime();
        
        String response = HttpClientUtil.sendGetRequest(GETTICKET_URL+"?access_token="+accessToken+"&type="+WeiXinUtil.JSAPI, null);
        if(StringUtils.isEmpty(response))
        {
            log.error("获取token失败");
            return null;
        }
        
        
        WeiXinTicketDto result = JSON.parseObject(response,WeiXinTicketDto.class);
        result.setSendTime(sendTime);//设置请求时间
        
        return result;
    }
    
    
    /**
     * 获取签名
     * 签名生成规则如下：
     * 参与签名的字段包括noncestr（随机字符串）, 
     * 有效的jsapi_ticket, 
     * timestamp（时间戳）,
     * url（当前网页的URL，不包含#及其后面部分） 。
     * 对所有待签名参数按照字段名的ASCII 码从小到大排序（字典序）后，
     * 使用URL键值对的格式（即key1=value1&key2=value2…）拼接成字符串string1。
     * 这里需要注意的是所有参数名均为小写字符。对string1作sha1加密，字段名和字段值都采用原始值，不进行URL转义。 
     * jsapi_ticket=sM4AOVdWfPE4DxkXGEs8VMCPGGVi4C3VM0P37wVUCFvkVAy_90u5h9nbSlYy3-Sl-HhTdfl2fzFy1AOcHKP7qg&noncestr=Wm3WZYTPz0wzccnW&timestamp=1414587457&url=http://mp.weixin.qq.com?params=value   
     * @author 朱振	
     * @date 2015年10月13日 下午3:54:37	
     * @version 1.0
     * @param jsapi_ticket  微信js的ticket
     * @param api_ticket  微信支付的ticket
     * @param noncestr    签名随机字符串
     * @param timestamp   签名时间戳
     * @param url   要签名的url
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年10月13日 下午3:54:37</p>
     * <p>修改备注：</p>
     */
    public static WeixinSignatureDto makeJSAPISignature(WeiXinTicketDto jsapi_ticket, String noncestr, Long timestamp, String url)
    {
        log.info("parameters:jsapi_ticket="+jsapi_ticket
                    +"&noncestr="+noncestr+"&timestamp="+timestamp+"&url="+url);
        
        //jsapi_ticket
        if(jsapi_ticket==null||StringUtils.isEmpty(jsapi_ticket.getTicket()))
        {
            log.error("微信js的ticket不能为空");
            return null;
        }
        
        //noncestr
        if(StringUtils.isEmpty(noncestr))
        {
            log.error("签名随机字符串不能为空");
            return null;
        }
        else if(noncestr.length() > 32)
        {
            log.error("签名随机字符串的长度不能大于32");
            return null;
        }
        
        //timestamp
        if(timestamp == null)
        {
            log.error("时间戳不能为空");
            return null;
        }
        
        //url
        if(StringUtils.isEmpty(url))
        {
            log.error("要签名的url不能为空");
            return null;
        }
        
        
        try
        {
            WeixinSignatureDto result = new WeixinSignatureDto();
            result.setSignature(WeiXinUtil.byteArray2HexString(SHACoder.encodeSHA("jsapi_ticket="+jsapi_ticket+"&noncestr="+noncestr+"&timestamp="+timestamp+"&url="+url)));
            result.setTicket(jsapi_ticket);
            
            return result;
        }
        catch (Exception e)
        {
            //签名从sha1字节数组转换为string异常
            log.error(e.getMessage(), e);
            
        }
         
         
        return null;
        
    }
    
    
    /**
     * 字节数组转为16进制字符串
     * @param bytes
     * @return
     */
    private static String byteArray2HexString(byte[] bytes)
    {
        StringBuilder sb = new StringBuilder();
        String tmp = null;
        for (byte b : bytes)
        {
            // 将每个字节与0xFF进行与运算，然后转化为10进制，然后借助于Integer再转化为16进制
            tmp = Integer.toHexString(0xFF & b);
            if (tmp.length() == 1)// 每个字节8为，转为16进制标志，2个16进制位
            {
                tmp = "0" + tmp;
            }
            sb.append(tmp);
        }

        return sb.toString();
    }
}

