package com.ssic.game.common.service;

import java.util.Map;

import com.ssic.game.common.constant.MobileMessageType;
import com.ssic.game.common.dto.MobileMessageSenderDto;

/**		
 * <p>Title: IMobileService </p>
 * <p>Description: 手机短信</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 朱振	
 * @date 2015年10月28日 上午9:31:35	
 * @version 1.0
 * <p>修改人：朱振</p>
 * <p>修改时间：2015年10月28日 上午9:31:35</p>
 * <p>修改备注：</p>
 */
public interface IMobileService
{
   /**
    * 获取并向指定手机发送短信验证码<BR>	 
    * @author 朱振	
    * @date 2015年10月28日 上午11:16:04	
    * @version 1.0
    * @param sender 发送短信所需要的参数
    * @return
    * <p>修改人：朱振</p>
    * <p>修改时间：2015年10月28日 上午11:16:04</p>
    * <p>修改备注：</p>
    */
    boolean sendVerificationCode(MobileMessageSenderDto sender);
    
    /**
     * /**
     * 获取绑定、解除绑定手机的配置信息  
     * @author 朱振   
     * @date 2015年10月30日 上午8:41:31  
     * @version 1.0
     * @param params 数据库t_ctr_param_config
     * @param code 验证码
     * @param mobile 接收验证码的手机号
     * @param type 信息类型，如手机绑定，手机解绑	 
     * @date 2015年10月30日 上午9:14:43	
     * @version 1.0
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年10月30日 上午9:14:43</p>
     * <p>修改备注：</p>
     */
    MobileMessageSenderDto getSender(Map<String, String> params, String code, String mobilePhone, MobileMessageType type);
}

