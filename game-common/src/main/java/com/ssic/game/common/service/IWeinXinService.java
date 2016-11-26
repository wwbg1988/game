package com.ssic.game.common.service;

import com.ssic.game.common.dto.WeixinSignatureDto;



/**		
 * <p>Title: 微信service </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 朱振	
 * @date 2015年10月13日 下午5:09:41	
 * @version 1.0
 * <p>修改人：朱振</p>
 * <p>修改时间：2015年10月13日 下午5:09:41</p>
 * <p>修改备注：</p>
 */
public interface IWeinXinService
{
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
     * @date 2015年10月13日 下午5:15:54	
     * @version 1.0
     * @param appId
     * @param appSecret
     * @param type
     * @param noncestr
     * @param timestamp
     * @param url
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年10月13日 下午5:15:54</p>
     * <p>修改备注：</p>
     */
    WeixinSignatureDto getJSAPISignature(String appId, String appSecret, String noncestr, Long timestamp, String url);
}

